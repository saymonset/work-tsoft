/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DefinicionDetalleMovimientoDTO;
import com.indeval.portaldali.middleware.dto.DetalleEstadoCuentaSaldoPorBovedaDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaEfectivoPorDivisaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoNombradaDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.util.DefinicionDetallesMovimientosHelper;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.util.FormatUtil;
import com.indeval.portaldali.presentation.common.constants.CamposPantallaConstantes;
import com.indeval.portaldali.presentation.common.constants.ReportesConstants;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.BackingBeanBase;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl.ConsultaEstadoCuentaDeSaldos;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Backing bean que da soporte a las operaciones realizadas en la pantalla de consulta de estado de cuenta operativo para los
 * saldos de efectivo
 * 
 * @author Emigdio
 * @version 1.0
 */
public class ConsultaEstadoCuentaSaldoEfectivoBean extends BackingBeanBase {
	
    /**
     * Modelo de consulta que proporciona acceso a la consulta de estados de cuenta de saldos en efectivo
     */
    private ConsultaEstadoCuentaDeSaldos consultaEstadoCuentaEfectivo = null;

    /**
     * Helper que contiene la definición de la operación que corresonde al tipo de instrucción de un registro contable.
     */
    private DefinicionDetallesMovimientosHelper definicionDetallesMovimientosHelper = null;
    /**
     * Resultados temporales de la consulta
     */
    List<EstadoCuentaEfectivoPorDivisaDTO> resultados = null;
    /**
     * Fachada para la consulta de catálogos
     */
    private ConsultaCatalogosFacade consultaCatalogos = null;
    /**
     * Indica si la consulta principal ya fue ejecutada
     */
    private boolean consultaEjecutada = false;

    /**Total de registros contables encontrados */
	private Long totalResultados = null;
	
	/**
	 * Almacena la clave y folio de la institucion
	 */
	private String folioClaveInstitucion;
	
	/**
	 * Institucion a buscar cuando el usuario es INDEVAL
	 */
	InstitucionDTO institucionConsultada = null;
	
    /**
     * Se ejecutan acciones de inicialización de la consulta, este método se invoca antes de pintar cualquier componente de la
     * pantalla.
     * 
     * @return null
     */

    /**
     * Inicializa los criterios de consulta
     * 
     * @param e ActionEvent generado durante la solicitud
     */
    public void limpiar(ActionEvent e) {
        inicializarCriterios();
    }

    /**
     * Inicializa las variables a utilizar en la pantalla y los criterios de búsqueda
     */
    public String getInit() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        if (getInstitucionActual() != null) {
            consultaEstadoCuentaEfectivo.getCriterioCuenta().setIdInstitucion(getInstitucionActual().getId());
            
            folioClaveInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
            
            // Reiniciar el criterio de naturaleza para el usuario participante
            // para dejar por default la naturaleza : Pasivo
            if (isUsuarioIndeval()) {
                TipoNaturalezaDTO pasivo = new TipoNaturalezaDTO();
                pasivo.setId(TipoNaturalezaDTO.PASIVO);
                consultaEstadoCuentaEfectivo.getCriterioCuenta().getCriterioTipoNaturaleza().setOpcionSeleccionada(pasivo);
            }
        }
               
        if (ctx.getRenderResponse()) {
            if (isConsultaEjecutada()) {

                consultaEstadoCuentaEfectivo.setDivisaSeleccionada(null);
                consultaEstadoCuentaEfectivo.setBovedaSeleccionada(null);
                consultaEstadoCuentaEfectivo.setCuentaSeleccionada(null);
                consultaEstadoCuentaEfectivo.recibirNotificacionResultados(null);
                consultaEstadoCuentaEfectivo.getCriterioDivisa().reestablecerEstadoPaginacion();
            }
            consultaEstadoCuentaEfectivo.getOpcionSeleccionada();
        }
        if (!ctx.getRenderKit().getResponseStateManager().isPostback(ctx)) {
            // si es la primera vez que se visita la página
            inicializarCriterios();
        }

        // Prepara la pantalla para precarga los datos de una consulta de estado de cuenta partiendo de la pantallad de posición

        if (ctx.getExternalContext().getRequestParameterMap().get("idSaldo") != null) {
            cargarValoresConsultaEstadoCuenta(ctx.getExternalContext().getRequestParameterMap().get("idSaldo"),
            	FormatUtil.stringToDate(ctx.getExternalContext().getRequestParameterMap().get("fechaFinal")),
            	ctx.getExternalContext().getRequestParameterMap().get("tipoCuenta"));
            buscarEstadoCuentaEfectivo(null);
        }
        consultaEstadoCuentaEfectivo.setDebeDejarBitacora(false);
        return null;
    }

    /**
     * Inicializa los criterios de búsqueda a sus valores por default
     */
    private void inicializarCriterios() {
        setConsultaEjecutada(false);
        consultaEstadoCuentaEfectivo.setCriterioFechaInicial(new Date());
        consultaEstadoCuentaEfectivo.setCriterioFechaFinal(new Date());
        consultaEstadoCuentaEfectivo.getCriterioCuenta().getCriterioTipoCuenta().setOpcionSeleccionada(null);
        consultaEstadoCuentaEfectivo.getCriterioCuenta().getCriterioTipoNaturaleza().setOpcionSeleccionada(null);
        consultaEstadoCuentaEfectivo.getCriterioCuenta().setOpcionSeleccionada(null);
        consultaEstadoCuentaEfectivo.getCriterioBoveda().setOpcionSeleccionada(null);
        consultaEstadoCuentaEfectivo.getCriterioDivisa().setOpcionSeleccionada(MXP);
        consultaEstadoCuentaEfectivo.getCriterioBoveda().reestablecerEstadoPaginacion();
        consultaEstadoCuentaEfectivo.getCriterioDivisa().reestablecerEstadoPaginacion();
        consultaEstadoCuentaEfectivo.getCriterioCuenta().reestablecerEstadoPaginacion();
        consultaEstadoCuentaEfectivo.reestablecerEstadoPaginacion();
        if (consultaEstadoCuentaEfectivo.getCriterioCuenta().isTodos()) {
            consultaEstadoCuentaEfectivo.getCriterioCuenta().toggleTodos();
        }
        if (consultaEstadoCuentaEfectivo.getCriterioDivisa().isTodos()) {
            consultaEstadoCuentaEfectivo.getCriterioDivisa().toggleTodos();
        }
        if (!consultaEstadoCuentaEfectivo.getCriterioBoveda().isTodos()) {
            consultaEstadoCuentaEfectivo.getCriterioBoveda().toggleTodos();
        }

    }

    /**
     * Indica si existe un parámetro en el mapa del request que indica si la consulta fue invocada desde la pantalla de consulta
     * de posición.
     * 
     * @return True si la pantalla fue invocada desde la consulta de posición, false en otro caso
     */
    public boolean isNavegacionPosicion() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        return ctx.getExternalContext().getRequestParameterMap().get("idSaldo") != null;
    }

    /**
     * Carga los valores iniciales con los que se consulta el estado de cuenta partiendo de un id de saldo y una fecha final
     * 
     * @param idSaldoString Identificador del saldo
     * @param fechaFinal Fecha final para la consulta
     * @param tipoCuenta tipo de cuenta de la consulta
     */
    private void cargarValoresConsultaEstadoCuenta(String idSaldoString, Date fechaFinal, String tipoCuenta) {
        long idSaldo = 0;
        idSaldo = NumberUtils.toLong(idSaldoString, -1);

        SaldoEfectivoDTO saldo = consultaEstadoCuentaEfectivo.getConsultaEstadoCuentaEfectivoService().buscarSaldoEfectivoPorId(
        	fechaFinal, idSaldo, tipoCuenta);
        
        if (saldo != null) {
            consultaEstadoCuentaEfectivo.setCriterioFechaFinal(fechaFinal);
            consultaEstadoCuentaEfectivo.setCriterioFechaInicial(fechaFinal);
            consultaEstadoCuentaEfectivo.getCriterioCuenta().getCriterioTipoNaturaleza().setOpcionSeleccionada(
                    saldo.getCuenta().getTipoNaturaleza());
            consultaEstadoCuentaEfectivo.getCriterioCuenta().getCriterioTipoCuenta().setOpcionSeleccionada(
                    saldo.getCuenta().getTipoCuenta());

            consultaEstadoCuentaEfectivo.getCriterioCuenta().setOpcionSeleccionada(saldo.getCuenta());

            consultaEstadoCuentaEfectivo.getCriterioDivisa().setOpcionSeleccionada(saldo.getDivisa());

            consultaEstadoCuentaEfectivo.getCriterioBoveda().setOpcionSeleccionada(saldo.getBoveda());
        }

    }

    /**
     * Prepara los criterios para la ejecución de la consulta de resultados de estado de cuenta de saldo en efectivo dependiendo
     * de los valores de criterios seleccionados
     * 
     * @param e ActionEvent generado durante la petición
     */
    public void buscarEstadoCuentaEfectivo(ActionEvent e) {
		boolean fechasValidas = 
			validarFechaObligatoria(consultaEstadoCuentaEfectivo.getCriterioFechaInicial(), false, CamposPantallaConstantes.campoFechaInicial) &&
			validarFechaObligatoria(consultaEstadoCuentaEfectivo.getCriterioFechaFinal(), false, CamposPantallaConstantes.campoFechaFinal);
		fechasValidas = fechasValidas && validarFechaFinalVsFechaInicial(
			consultaEstadoCuentaEfectivo.getCriterioFechaInicial(), consultaEstadoCuentaEfectivo.getCriterioFechaFinal());
		if(fechasValidas) {
			consultaEstadoCuentaEfectivo.setDivisaSeleccionada(null);
			consultaEstadoCuentaEfectivo.setBovedaSeleccionada(null);
			consultaEstadoCuentaEfectivo.setCuentaSeleccionada(null);
			
			if (consultaEstadoCuentaEfectivo.getCriterioCuenta().isTodos()) {
				consultaEstadoCuentaEfectivo.getCriterioCuenta().toggleTodos();
			}
			if (consultaEstadoCuentaEfectivo.getCriterioDivisa().isTodos()) {
				consultaEstadoCuentaEfectivo.getCriterioDivisa().toggleTodos();
			}
			if (!consultaEstadoCuentaEfectivo.getCriterioBoveda().isTodos()) {
				consultaEstadoCuentaEfectivo.getCriterioBoveda().toggleTodos();
			}
			
			consultaEstadoCuentaEfectivo.getCriterioDivisa().getEstadoPaginacion().setNumeroPagina(1);
			consultaEstadoCuentaEfectivo.getCriterioDivisa().getEstadoPaginacion().setTotalResultados(0);
			
			consultaEstadoCuentaEfectivo.getCriterioBoveda().getEstadoPaginacion().setNumeroPagina(1);
			consultaEstadoCuentaEfectivo.getCriterioBoveda().getEstadoPaginacion().setTotalResultados(0);
			
			consultaEstadoCuentaEfectivo.getCriterioCuenta().getEstadoPaginacion().setNumeroPagina(1);
			consultaEstadoCuentaEfectivo.getCriterioCuenta().getEstadoPaginacion().setTotalResultados(0);
			
			consultaEstadoCuentaEfectivo.setDebeDejarBitacora(true);
			consultaEstadoCuentaEfectivo.recibirNotificacionResultados(null);
			consultaEstadoCuentaEfectivo.setDebeDejarBitacora(false);
			
			if(folioClaveInstitucion != null && folioClaveInstitucion.length() == 2) {
				consultaEstadoCuentaEfectivo.getCuentaSeleccionada().getInstitucion().setClaveTipoInstitucion(folioClaveInstitucion);
			}
			
			consultaEstadoCuentaEfectivo.getCriterioDivisa().reestablecerEstadoPaginacion();
			consultaEstadoCuentaEfectivo.getCriterioBoveda().reestablecerEstadoPaginacion();
			consultaEstadoCuentaEfectivo.getCriterioCuenta().reestablecerEstadoPaginacion();
			
			consultaEstadoCuentaEfectivo.reestablecerEstadoPaginacion();
			
			consultaEstadoCuentaEfectivo.setColumnaOrdenada(null);
			consultaEstadoCuentaEfectivo.setOrdenAscendente(true);
			
			setConsultaEjecutada(true);
		}
		else {
			consultaEjecutada = false;
		}
    }

    /**
     * Invoca las funciones de navegación de página de la consulta principal de la pantalla
     * 
     * @param e Evento generado durante la solicitud
     */
    public void navegarPorResultados(ActionEvent e) {
        String navegacion = (String) e.getComponent().getAttributes().get("navegacion");

        if (navegacion != null && navegacion.length() > 0) {
            try {
                consultaEstadoCuentaEfectivo.getCriterioDivisa().getClass().getMethod(navegacion).invoke(
                        consultaEstadoCuentaEfectivo.getCriterioDivisa());
            } catch (Exception ex) {
                logger.error(
                        "Error de invocación de método al navega por los resultados principales", ex);
            }

        }

    }

    /**
     * Obtiene una lista con las páginas que actualmente retorna la consulta principal de estado de cuenta
     * 
     * @return Lista de páginas obtenidas
     */
    public List<Integer> getListaPaginasResultado() {
        List<Integer> paginas = new ArrayList<Integer>();

        FacesContext ctx = FacesContext.getCurrentInstance();
        // Si se estn mostrando resultados de la consulta
        if (ctx.getRenderResponse() && isConsultaEjecutada()) {
            // se llena una lista con las páginas de la consulta
            for (int i = 1; i <= consultaEstadoCuentaEfectivo.getCriterioDivisa().getEstadoPaginacion().getTotalPaginas(); i++) {
                paginas.add(i);
            }
        }

        return paginas;
    }

    /**
     * Inicializa la consulta
     * 
     * @return null
     */
    public String getInitConsulta() {
		logger.warn("Método getInitConsulta. Criterios de consulta: ");
		logger.warn("Fecha inicio: " + consultaEstadoCuentaEfectivo.getCriterioFechaInicial());
		logger.warn("Fecha final: " + consultaEstadoCuentaEfectivo.getCriterioFechaFinal());

        FacesContext ctx = FacesContext.getCurrentInstance();

        if (ctx.getRenderResponse()) {
            resultados = consultaEstadoCuentaEfectivo.getPaginaDeResultados();
            if (TipoCuentaConstants.TIPO_NOMBRADA.equals(consultaEstadoCuentaEfectivo.getCriterioCuenta().getOpcionSeleccionada()
                    .getTipoCuenta().getId())) {
                asignarPantallaDetalle(resultados);
            }
            totalResultados = consultaEstadoCuentaEfectivo.getConsultaEstadoCuentaEfectivoService().
            					obtenerProyeccionDeRegistrosContablesDeSaldos(consultaEstadoCuentaEfectivo.getCriterioConsulta());

            //Sugiere que pase el garbage collector
            pasarGarbageCollector();
        }
        else {
            resultados = new ArrayList<EstadoCuentaEfectivoPorDivisaDTO>();
        }
        return null;
    }

    /**
     * método para generar un reporte con formato XLS o PDF
     * 
     * @return cadena de para ejecutar el action para genera el reporte
     */
    public void generarReportes(ActionEvent e) {
    	reiniciarEstadoPeticion();
		resultados = consultaEstadoCuentaEfectivo.getResultados();
        //Sugiere que pase el garbage collector
        pasarGarbageCollector();
    }

    /**
     * Libera los recursos de la consulta
     */
    public String getLimpiarConsulta() {
        resultados = null;
        return null;

    }

    /**
     * Realiza la consulta al controlador de consulta de efectivo. La consulta es realiza siempre y cuando el ciclo de vida de la
     * solicitud est en la fase de respuesta al cliente.
     * 
     * @return Lista con los resultados de la consulta de efectivo
     */
    public List<EstadoCuentaEfectivoPorDivisaDTO> getResultadosEstadoCuentaEfectivo() {

        return resultados;
    }

    /**
     * Recorre los resultados de la consulta de estado de cuenta y asigna a cada registro contable la ruta de la pantalla de
     * detalle que debe mostrar.
     * 
     * @param listaEmisiones Lista de registros contable agrupados por emisión y bóveda.
     */

    private void asignarPantallaDetalle(List<EstadoCuentaEfectivoPorDivisaDTO> listaEmisiones) {
        DefinicionDetalleMovimientoDTO definicionDetalle = null;

        for (EstadoCuentaEfectivoPorDivisaDTO estadoCuentaPosicionPorDivisaDTO : listaEmisiones) {
            for (DetalleEstadoCuentaSaldoPorBovedaDTO detalleEstadoCuentaSaldoPorBovedaDTO : estadoCuentaPosicionPorDivisaDTO
                    .getRegistrosContablesPorBoveda()) {
                if (detalleEstadoCuentaSaldoPorBovedaDTO.getRegistrosContablesNombradas() != null) {

                    for (RegistroContableSaldoNombradaDTO registroContableNombradaDTO : detalleEstadoCuentaSaldoPorBovedaDTO
                            .getRegistrosContablesNombradas()) {
                        definicionDetalle = definicionDetallesMovimientosHelper
                                .buscarDefinicionDetalleMovimiento(registroContableNombradaDTO.getIdTipoTipoInstruccion());
                        if (definicionDetalle != null) {
                            registroContableNombradaDTO.setRutaPantallaDetalle(definicionDetalle
                                    .getRutaPantallaDetalleMovimiento());
                            registroContableNombradaDTO.setAltoPantallaDetalle(definicionDetalle.getAltoPantalla());
                            registroContableNombradaDTO.setAnchoPantallaDetalle(definicionDetalle.getAnchoPantalla());
                        }

                    }

                }
            }
        }

    }

    /**
     * Obtiene las opciones en forma de una lista de {@link SelectItem} para llenar el combo de criterio de Naturaleza
     * 
     * @return Lista con las opciones válidas del criterio de Naturaleza
     */
    public List<SelectItem> getOpcionesComboNaturaleza() {
        List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

        for (TipoNaturalezaDTO nat : this.consultaEstadoCuentaEfectivo.getCriterioCuenta().getCriterioTipoNaturaleza()
                .getResultados()) {
            opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
        }

        return opcionesCombo;
    }

    /**
     * Obtiene las opciones disponibles en el catalogo de bovedas
     * 
     * @return Lista de SelectItem con las bovedas existentes en la base
     */
    public List<SelectItem> getOpcionesComboBoveda() {
        List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

        BovedaDTO bovedaTodos = new BovedaDTO();
        bovedaTodos.setId(-1);
        bovedaTodos.setDescripcion("TODAS");

        opcionesCombo.add(new SelectItem(bovedaTodos, bovedaTodos.getDescripcion()));

        for (BovedaDTO boveda : consultaEstadoCuentaEfectivo.getCriterioBoveda().getResultados()) {
            opcionesCombo.add(new SelectItem(boveda, boveda.getDescripcion()));
        }

        return opcionesCombo;
    }

    /**
     * Obtiene las opciones para las cuentas
     * 
     * @return Lista de SelectItem con las cuentas existentes en la base
     */
    public List<SelectItem> getOpcionesComboTipoCuenta() {
        List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

        for (TipoCuentaDTO nat : consultaEstadoCuentaEfectivo.getCriterioCuenta().getCriterioTipoCuenta().getResultados()) {
            opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
        }

        return opcionesCombo;
    }

    /**
     * Obtiene las opciones disponibles en el catalogo de divisas
     * 
     * @return Lista de SelectItem con las divisas existentes en la base
     */
    public List<SelectItem> getOpcionesComboDivisa() {
    	return consultaCatalogos.getSelectItemsTipoDivisa();
    }

    /**
     * Obtiene las opciones a mostrar en el combo de cuenta
     * 
     * @return Lista de {@link SelectItem} que sirve para llenar el combo de cuentas
     */
    public List<SelectItem> getOpcionesComboCuenta() {
        List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

        CuentaEfectivoDTO cuentaTodos = new CuentaEfectivoDTO();
        cuentaTodos.setNumeroCuenta("-1");
        cuentaTodos.setDescripcion("TODAS");

        opcionesCombo.add(new SelectItem(cuentaTodos, cuentaTodos.getDescripcion()));

        for (CuentaEfectivoDTO nat : consultaEstadoCuentaEfectivo.getCriterioCuenta().getResultados()) {
            opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
        }

        return opcionesCombo;
    }

    public void cambioSelectNaturaleza(ActionEvent e) {
        consultaEstadoCuentaEfectivo.getCriterioCuenta().setOpcionSeleccionada(null);

    }

    public void cambioSelectTipoCuenta(ActionEvent e) {
        consultaEstadoCuentaEfectivo.getCriterioCuenta().setOpcionSeleccionada(null);
    }

    public void cambioSelectBoveda(ActionEvent e) {

    }

    public void cambioSelectDivisa(ActionEvent e) {

    }

    /**
     * Busca cuentas en el catálogo cuyo número de cuenta comiencen con el prefijo proporcionado.
     * 
     * @param prefijo el prefijo a consultar en la BD.
     * @return una lista con objetos de tipo {@link CuentaEfectivoDTO} con todas las coincidencias encontradas.
     */
    public List<CuentaEfectivoDTO> buscarCuentasPorPrefijo(Object value) {
        List<CuentaEfectivoDTO> cuentas = new ArrayList<CuentaEfectivoDTO>();
        
        String prefijoAjustado = "";
    	if (value != null) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
		}

    	if (value != null && consultaEstadoCuentaEfectivo.getCriterioCuenta().getIdInstitucion() != -1) {

            CuentaEfectivoDTO criterio = consultaEstadoCuentaEfectivo.getCriterioCuenta().getOpcionSeleccionada();
            
            if (!isUsuarioIndeval()) {
                criterio.setNumeroCuenta(getInstitucionActual().getClaveTipoInstitucion()
                        + getInstitucionActual().getFolioInstitucion() + prefijoAjustado);
            } else {
                criterio.setNumeroCuenta(getFolioClaveInstitucion() + prefijoAjustado);
            }
            if(folioClaveInstitucion.length() > 2) {
            	cuentas = consultaEstadoCuentaEfectivo.getCriterioCuenta().getConsultaCuentaService()
            			.buscarCuentasPorFragmentoNumeroCuenta(criterio);
            }
            
            for (CuentaEfectivoDTO cuentaDTO : cuentas) {
                cuentaDTO.setDescripcion(cuentaDTO.getDescripcion().substring(5));
            }
            
        }
        return cuentas;
    }

    /**
     * método para obtener el atributo cuentaSeleccionada
     * 
     * @return el atributo cuentaSeleccionada
     */
    public String getCuentaSeleccionada() {
        String cuenta = null;
        List<CuentaEfectivoDTO> resultados = null;
        if (getInstitucionActual().getId() > 0) {
            resultados = consultaEstadoCuentaEfectivo.getCriterioCuenta().getResultados();
        }

        // Si existe solo una cuenta, colocarla como predeterminada
        if (resultados != null && resultados.size() == 1) {
            consultaEstadoCuentaEfectivo.getCriterioCuenta().setOpcionSeleccionada(resultados.get(0));
        }
        cuenta = consultaEstadoCuentaEfectivo.getCriterioCuenta().getOpcionSeleccionada().getNumeroCuenta().equals("-1") ? "TODAS"
                : consultaEstadoCuentaEfectivo.getCriterioCuenta().getOpcionSeleccionada().getNumeroCuenta();
        // Si existe institución activa se deben de omitir los caracteres del folio y el tipo de institución
        if (getInstitucionActual().getId() > 0) {
            cuenta = cuenta.replace(getInstitucionActual().getClaveTipoInstitucion()
                    + getInstitucionActual().getFolioInstitucion(), StringUtils.EMPTY);
        }
        return cuenta;
    }

    /**
     * Establece el valor del atributo cuentaSeleccionada
     * 
     * @param cuentaSeleccionada el valor del atributo cuentaSeleccionada a establecer
     */
    public void setCuentaSeleccionada(String cuentaSeleccionada) {

        consultaEstadoCuentaEfectivo.getCriterioCuenta().setOpcionSeleccionada(null);
        if (cuentaSeleccionada != null && cuentaSeleccionada.length() > 0) {
            if (!"TODAS".equals(cuentaSeleccionada) && !"TODA".equals(cuentaSeleccionada)) {
            	if (!isUsuarioIndeval()) {
					cuentaSeleccionada = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion() + cuentaSeleccionada;
				} else {
					cuentaSeleccionada = getFolioClaveInstitucion() + cuentaSeleccionada;
				}
                CuentaDTO cuenta = new CuentaDTO();
                cuenta.setNumeroCuenta(cuentaSeleccionada);
                consultaEstadoCuentaEfectivo.getCriterioCuenta().getOpcionSeleccionada().setNumeroCuenta(cuentaSeleccionada);
                consultaEstadoCuentaEfectivo.getCriterioCuenta().getOpcionSeleccionada().setDescripcion(cuentaSeleccionada);
            }

        }
    }

    /**
     * Obtiene el campo consultaEstadoCuentaEfectivo
     * 
     * @return consultaEstadoCuentaEfectivo
     */
    public ConsultaEstadoCuentaDeSaldos getConsultaEstadoCuentaEfectivo() {
        return consultaEstadoCuentaEfectivo;
    }

    /**
     * Genera un mapa con los parámetros de la consulta de posiciones.
     * 
     * @return Map mapa con los parámetros de la consulta
     */
    @Override
    protected Map<String, Object> llenarParametros() {
        Map<String, Object> parametros = new HashMap<String, Object>();

        parametros.put(ReportesConstants.CUENTA_PARAMETER, consultaEstadoCuentaEfectivo.getCuentaSeleccionada().getDescripcion());
        parametros.put(ReportesConstants.NATURALEZA_PARAMETER, consultaEstadoCuentaEfectivo.getCuentaSeleccionada()
                .getTipoNaturaleza().getDescripcion());
        parametros.put(ReportesConstants.TIPO_CUENTA_PARAMETER, consultaEstadoCuentaEfectivo.getCuentaSeleccionada()
                .getTipoCuenta().getDescripcion());
        parametros.put(ReportesConstants.BOVEDA_PARAMETER, consultaEstadoCuentaEfectivo.getCriterioBoveda()
                .getOpcionSeleccionada().getDescripcion());
        parametros.put(ReportesConstants.DIVISA_PARAMETER, consultaEstadoCuentaEfectivo.getCriterioDivisa()
                .getOpcionSeleccionada().getDescripcion());
        parametros.put(ReportesConstants.FECHA_INICIAL_PARAMETER, consultaEstadoCuentaEfectivo.getCriterioFechaInicial());
        parametros.put(ReportesConstants.FECHA_FINAL_PARAMETER, consultaEstadoCuentaEfectivo.getCriterioFechaFinal());

        return parametros;
    }

    /**
     * Asigna el valor del campo consultaEstadoCuentaEfectivo
     * 
     * @param consultaEstadoCuentaEfectivo el valor de consultaEstadoCuentaEfectivo a asignar
     */
    public void setConsultaEstadoCuentaEfectivo(ConsultaEstadoCuentaDeSaldos consultaEstadoCuentaEfectivo) {
        this.consultaEstadoCuentaEfectivo = consultaEstadoCuentaEfectivo;
    }

    /**
     * Obtiene el campo consultaEjecutada
     * 
     * @return consultaEjecutada
     */
    public boolean isConsultaEjecutada() {
        return consultaEjecutada;
    }

    /**
     * Asigna el valor del campo consultaEjecutada
     * 
     * @param consultaEjecutada el valor de consultaEjecutada a asignar
     */
    public void setConsultaEjecutada(boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

    /**
     * Obtiene el campo definicionDetallesMovimientosHelper
     * 
     * @return definicionDetallesMovimientosHelper
     */
    public DefinicionDetallesMovimientosHelper getDefinicionDetallesMovimientosHelper() {
        return definicionDetallesMovimientosHelper;
    }

    /**
     * Asigna el valor del campo definicionDetallesMovimientosHelper
     * 
     * @param definicionDetallesMovimientosHelper el valor de definicionDetallesMovimientosHelper a asignar
     */
    public void setDefinicionDetallesMovimientosHelper(DefinicionDetallesMovimientosHelper definicionDetallesMovimientosHelper) {
        this.definicionDetallesMovimientosHelper = definicionDetallesMovimientosHelper;
    }

    @Override
    protected Collection<? extends Object> ejecutarConsultaReporte() {
        return (Collection<? extends Object>) consultaEstadoCuentaEfectivo.getResultados();
    }

    @Override
    protected String getNombreReporte() {
        return ReportesConstants.REPORTE_ESTADO_CUENTA_SALDOS;
    }

	/**
	 * @return the consultaCatalogos
	 */
	public ConsultaCatalogosFacade getConsultaCatalogos() {
		return consultaCatalogos;
	}

	/**
	 * @param consultaCatalogos the consultaCatalogos to set
	 */
	public void setConsultaCatalogos(ConsultaCatalogosFacade consultaCatalogos) {
		this.consultaCatalogos = consultaCatalogos;
	}

	/**
	 * @return the totalResultados
	 */
	public Long getTotalResultados() {
		return totalResultados;
	}

	/**
	 * @param totalResultados the totalResultados to set
	 */
	public void setTotalResultados(Long totalResultados) {
		this.totalResultados = totalResultados;
	}

	public String getFolioClaveInstitucion() {
		return folioClaveInstitucion;
	}

	public void setFolioClaveInstitucion(String folioClaveInstitucion) {
		this.folioClaveInstitucion = folioClaveInstitucion;
		
		if(folioClaveInstitucion.length() >= 5) {
			this.folioClaveInstitucion = folioClaveInstitucion.substring(0,5);
			
			institucionConsultada = getConsultaInstitucionService().buscarInstitucionPorClaveYFolio(this.folioClaveInstitucion);
			if(institucionConsultada != null) {
				consultaEstadoCuentaEfectivo.getCriterioCuenta().setIdInstitucion(institucionConsultada.getId());
				
			} 
		}
		else {
			consultaEstadoCuentaEfectivo.getCriterioCuenta().setIdInstitucion(-1);
			if(folioClaveInstitucion.length() == 2) {
				consultaEstadoCuentaEfectivo.getCriterioCuenta().getOpcionSeleccionada().getInstitucion().setClaveTipoInstitucion(folioClaveInstitucion);
			}
		}
	}

	public InstitucionDTO getInstitucionConsultada() {
		return institucionConsultada;
	}

	public void setInstitucionConsultada(InstitucionDTO institucionConsultada) {
		this.institucionConsultada = institucionConsultada;
	}
	
	

}
