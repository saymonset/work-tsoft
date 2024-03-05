/**
 * 2H Software - Bursatec
 * Sistema de Consulta de Estados de Cuenta
 *
 * ConsultaSaldosEfectivoBean.java
 * Dec 9, 2007
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

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.services.common.util.FormatUtil;
import com.indeval.portaldali.middleware.services.modelo.BusinessDataException;
import com.indeval.portaldali.presentation.common.constants.CamposPantallaConstantes;
import com.indeval.portaldali.presentation.common.constants.ReportesConstants;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.BackingBeanBase;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl.ConsultaDeSaldos;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Backing bean asociado a la pantalla de consulta de saldos de efectivo. Esta clase se encarga de la invocación a los servicios
 * de negocio relacionados con la consulta de saldos de efectivo.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 */
public class ConsultaSaldosEfectivoBean extends BackingBeanBase {
    /**
     * Objeto que representa un esquema de consulta de saldos
     */
    private ConsultaDeSaldos consultaSaldos = null;
    /**
     * Indica si se tiene que volver a iniciar la configuración de la paginación
     */
    private boolean restaurarPaginacionResultados = true;
    /**
     * Objeto donde se almacena el resumen de la consulta
     */
    private SaldoEfectivoDTO resumenSaldo = null;
    /**
     * Objeto donde se almacena el resultado de los totales de consulta
     */
    private Map<String, Number> resumenConsulta = null;
    /**
     * Lista donde se almacenan temporalmente los resultados de la consulta
     */
    List<SaldoEfectivoDTO> resultados = null;
    /**
     * Fachada para la consulta de catálogos
     */
    private ConsultaCatalogosFacade consultaCatalogos = null;
    
    /**
	 * Almacena la clave y folio de la institucion
	 */
	private String folioClaveInstitucion;
	
	/** El número de registros encontrados para los reportes */
	private int registrosEncontrados;
	
	/**
	 * Institucion a buscar cuando el usuario es INDEVAL
	 */
	InstitucionDTO institucion = null;

   /**
     * Obtiene las opciones a mostrar en el combo de cuenta
     * 
     * @return Lista de {@link SelectItem} que sirve para llenar el combo de cuentas
     */
    public List<SelectItem> getOpcionesComboCuenta() {
        List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

        CuentaEfectivoDTO cuentaTodos = new CuentaEfectivoDTO();
        cuentaTodos.setNumeroCuenta("-1");
        cuentaTodos.setDescripcion("-- TODAS --");

        opcionesCombo.add(new SelectItem(cuentaTodos, cuentaTodos.getDescripcion()));

        for (CuentaEfectivoDTO nat : consultaSaldos.getCriterioCuenta().getResultados()) {
            opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
        }

        return opcionesCombo;
    }

    /**
     * Obtiene el resultado de la consulta de saldos de efectivo a travs del modelo de consulta.
     * 
     * @return una lista con los resultado de la consulta.
     */
    public List<SaldoEfectivoDTO> getResultadosConsultaSaldo() {

        return resultados;
    }

    /**
	 * Obtiene el valor del atributo registrosEncontrados
	 *
	 * @return el valor del atributo registrosEncontrados
	 */
	public int getRegistrosEncontrados() {
		return registrosEncontrados;
	}

	/**
	 * Establece el valor del atributo registrosEncontrados
	 *
	 * @param registrosEncontrados el valor del atributo registrosEncontrados a establecer
	 */
	public void setRegistrosEncontrados(int registrosEncontrados) {
		this.registrosEncontrados = registrosEncontrados;
	}

	/**
     * Inicializa la variable de resultados de consulta
     * 
     * @return null
     */
    public String getInitConsulta() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        if (isUsuarioIndeval()|| (!StringUtils.isEmpty(folioClaveInstitucion))){
        
	        if (ctx.getRenderResponse()) {
	            resultados = consultaSaldos.getPaginaDeResultados();
	            resumenSaldo = new SaldoEfectivoDTO();
	            double saldoTotal = 0;
	            double saldoDisponibleTotal = 0;
	            double saldoNoDisponibleTotal = 0;
	
	            for (SaldoEfectivoDTO saldoEfectivoDTO : resultados) {
	                saldoTotal += saldoEfectivoDTO.getSaldo();
	                saldoDisponibleTotal += saldoEfectivoDTO.getSaldoDisponible() != null ? saldoEfectivoDTO.getSaldoDisponible() : 0;
	                saldoNoDisponibleTotal += saldoEfectivoDTO.getSaldoNoDisponible();
	            }
	            resumenSaldo.setSaldo(saldoTotal);
	            resumenSaldo.setSaldoDisponible(saldoDisponibleTotal);
	            resumenSaldo.setSaldoNoDisponible(saldoNoDisponibleTotal);
	           	resumenConsulta = consultaSaldos.getConsultaSaldosEfectivoService().obtenerTotalesConsultaSaldo(consultaSaldos.getOpcionSeleccionada());
	            
	            
	
	        } else {
	            resultados = new ArrayList<SaldoEfectivoDTO>();
	        }
        }else{
        	throw new BusinessDataException("Ocurri\u00f3 un error en los permisos de consulta", "error con la instituci\u00f3n del participante");
        }
        return null;
    }

    /**
     * Limpia la variable de resultados de consulta
     * 
     * @return null
     */
    public String getLimpiarConsulta() {
        resultados = null;
        return null;
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
     * Inicializa los criterios de consulta
     * 
     * @param e ActionEvent generado durante la solicitud
     */
    public void limpiar(ActionEvent e) {
        inicializarCriterios();
    }

    /**
     * Indica si la consulta principal ya fue ejecutada
     */
    private boolean consultaEjecutada = false;

    /**
     * Obtiene las opciones en forma de una lista de {@link SelectItem} para llenar el combo de criterio de Naturaleza
     * 
     * @return Lista con las opciones válidas del criterio de Naturaleza
     */
    public List<SelectItem> getOpcionesComboNaturaleza() {
        List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

        for (TipoNaturalezaDTO nat : this.consultaSaldos.getCriterioCuenta().getCriterioTipoNaturaleza().getResultados()) {
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

        for (BovedaDTO boveda : consultaSaldos.getCriterioBoveda().getResultados()) {
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

        for (TipoCuentaDTO nat : consultaSaldos.getCriterioCuenta().getCriterioTipoCuenta().getResultados()) {
            opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
        }

        return opcionesCombo;
    }

    public void cambioSelectNaturaleza(ActionEvent e) {
        consultaSaldos.getCriterioCuenta().setOpcionSeleccionada(null);

    }

    public void cambioSelectTipoCuenta(ActionEvent e) {
        consultaSaldos.getCriterioCuenta().setOpcionSeleccionada(null);
    }

    public void cambioSelectBoveda(ActionEvent e) {

    }

    public void cambioSelectDivisa(ActionEvent e) {

    }

    public void ordenar(ActionEvent e) {

        if (e.getComponent().getId().equals("sortCuenta") || e.getComponent().getId().equals("sortSaldo")
                || e.getComponent().getId().equals("sortDivisa") || e.getComponent().getId().equals("sortBoveda")) {
            if (e.getComponent().getId().equals(consultaSaldos.getColumnaOrdenada())) {
                consultaSaldos.setOrdenAscendente(!consultaSaldos.isOrdenAscendente());
            } else {
                consultaSaldos.setOrdenAscendente(true);
            }
            consultaSaldos.setColumnaOrdenada(e.getComponent().getId());

        }
    }

    /**
     * Prepara los criterios para la ejecución de la consulta de resultados de saldos dependiendo de los valores de criterios
     * seleccionados
     * 
     * @param e ActionEvent generado durante la petición
     */
    public void buscarSaldosEfectivo(ActionEvent e) {
    	if(validarFechaObligatoria(consultaSaldos.getCriterioFecha(), false, CamposPantallaConstantes.campoFecha)) {
            consultaSaldos.setDivisaSeleccionada(null);
            consultaSaldos.setBovedaSeleccionada(null);
            consultaSaldos.setCuentaSeleccionada(null);
            if (!consultaSaldos.getCriterioCuenta().isTodos()) {
                consultaSaldos.getCriterioCuenta().toggleTodos();
            }
            if (!consultaSaldos.getCriterioDivisa().isTodos()) {
                consultaSaldos.getCriterioDivisa().toggleTodos();
            }
            if (!consultaSaldos.getCriterioBoveda().isTodos()) {
                consultaSaldos.getCriterioBoveda().toggleTodos();
            }

            consultaSaldos.recibirNotificacionResultados(null);
            
            if(folioClaveInstitucion != null && folioClaveInstitucion.length() == 2) {
            	consultaSaldos.getOpcionSeleccionada().getCuenta().getInstitucion().setClaveTipoInstitucion(folioClaveInstitucion);
            }

            consultaSaldos.getCriterioDivisa().reestablecerEstadoPaginacion();
            consultaSaldos.getCriterioBoveda().reestablecerEstadoPaginacion();
            consultaSaldos.getCriterioCuenta().reestablecerEstadoPaginacion();
            
            consultaSaldos.setDebeLogearProyeccion(true);
            consultaSaldos.reestablecerEstadoPaginacion();
            consultaSaldos.setDebeLogearProyeccion(false);
            restaurarPaginacionResultados = false;

            consultaSaldos.setColumnaOrdenada("sortCuenta");
            consultaSaldos.setOrdenAscendente(true);

            setConsultaEjecutada(true);
            resumenConsulta = null;
    	}
    	else {
    		consultaEjecutada = false;
    	}
    }

    /**
     * Asigna las opciones predeterminadas para cuando se carga la página por primerva vez.
     * 
     * @return nulo, este Metodo no requiere retornar un valor
     */
    public String getInit() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        if (getInstitucionActual() != null) {
            consultaSaldos.getCriterioCuenta().setIdInstitucion(getInstitucionActual().getId());
            // Reiniciar el criterio de naturaleza para el usuario participante
            // para dejar por default la naturaleza : Pasivo
            if (getInstitucionActual().getId() > 0) {
                TipoNaturalezaDTO pasivo = new TipoNaturalezaDTO();
                pasivo.setId(TipoNaturalezaDTO.PASIVO);
                consultaSaldos.getCriterioCuenta().getCriterioTipoNaturaleza().setOpcionSeleccionada(pasivo);
            }
        }
        if(isUsuarioIndeval()) {
        	folioClaveInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
        	
        }
        else {
        	folioClaveInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
        	institucion = getConsultaInstitucionService().buscarInstitucionPorClaveYFolio(folioClaveInstitucion);
        	consultaSaldos.getCriterioCuenta().setIdInstitucion(institucion.getId());
        }
        if (ctx.getRenderResponse()) {
            if (isConsultaEjecutada()) {

                consultaSaldos.setDivisaSeleccionada(null);
                consultaSaldos.setBovedaSeleccionada(null);
                consultaSaldos.setCuentaSeleccionada(null);
            }
            consultaSaldos.getOpcionSeleccionada();
        }
        if (!ctx.getRenderKit().getResponseStateManager().isPostback(ctx)) {
            // si es la primera vez que se visita la página
            inicializarCriterios();
        }

        return null;
    }

    /**
     * Inicializa los criterios de consulta
     */
    private void inicializarCriterios() {
        setConsultaEjecutada(false);
        consultaSaldos.setCriterioFecha(new Date());
        consultaSaldos.getCriterioCuenta().getCriterioTipoCuenta().setOpcionSeleccionada(null);
        consultaSaldos.getCriterioCuenta().getCriterioTipoNaturaleza().setOpcionSeleccionada(null);
        consultaSaldos.getCriterioCuenta().setOpcionSeleccionada(null);
        consultaSaldos.getCriterioBoveda().setOpcionSeleccionada(null);
        consultaSaldos.getCriterioDivisa().setOpcionSeleccionada(MXP);
        consultaSaldos.getCriterioBoveda().reestablecerEstadoPaginacion();
        consultaSaldos.getCriterioDivisa().reestablecerEstadoPaginacion();
        consultaSaldos.getCriterioCuenta().reestablecerEstadoPaginacion();
        consultaSaldos.reestablecerEstadoPaginacion();
        if (!consultaSaldos.getCriterioCuenta().isTodos()) {
            consultaSaldos.getCriterioCuenta().toggleTodos();
        }
        if (!consultaSaldos.getCriterioDivisa().isTodos()) {
            consultaSaldos.getCriterioDivisa().toggleTodos();
        }
        if (!consultaSaldos.getCriterioBoveda().isTodos()) {
            consultaSaldos.getCriterioBoveda().toggleTodos();
        }

    }

    /**
     * Determina si se debe de restaurar la paginación de los resultados principaless. Se debe restaurar una paginación despus de
     * realizar cualquier cambio que afecte la cantidad de resultados.
     * 
     * @return <code>null</code>
     */
    public String getRestaurPaginacion() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        if (restaurarPaginacionResultados && ctx.getRenderResponse()) {
        	consultaSaldos.setDebeLogearProyeccion(false);
            consultaSaldos.reestablecerEstadoPaginacion();
        }
        restaurarPaginacionResultados = true;
        return null;
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

        if (value != null && consultaSaldos.getCriterioCuenta().getIdInstitucion() != -1) {

            CuentaEfectivoDTO criterio = consultaSaldos.getCriterioCuenta().getOpcionSeleccionada();
            if(!isUsuarioIndeval()) {
                criterio.setNumeroCuenta(getInstitucionActual().getClaveTipoInstitucion()
                        + getInstitucionActual().getFolioInstitucion() + prefijoAjustado);
            } else {
                criterio.setNumeroCuenta(getFolioClaveInstitucion() + prefijoAjustado);
            }
            
            if(folioClaveInstitucion.length() > 2) {
            	cuentas = consultaSaldos.getCriterioCuenta().getConsultaCuentaService().buscarCuentasPorFragmentoNumeroCuenta(
                    criterio);
            }
            
            for (CuentaEfectivoDTO cuentaDTO : cuentas) {
                cuentaDTO.setDescripcion(cuentaDTO.getDescripcion().substring(5));
            }
            
        }
        return cuentas;
    }

    /**
     * Invoca las funciones de navegación de página de la consulta principal de la pantalla
     * 
     * @param e Evento generado durante la solicitud
     */
    public void navegarPorResultados(ActionEvent e) {
        String navegacion = (String) e.getComponent().getAttributes().get("navegacion");

        try {
            consultaSaldos.getClass().getMethod(navegacion).invoke(consultaSaldos);
        } catch (Exception ex) {
            logger.error("Error de invocación de Metodo al navega por los resultados principales", ex);
        }

        restaurarPaginacionResultados = false;
    }

    /**
     * Genera un mapa con los parámetros de la consulta de posiciones.
     * 
     * @return Map mapa con los parámetros de la consulta
     */
    @Override
    protected Map<String, Object> llenarParametros() {
        Map<String, Object> parametros = new HashMap<String, Object>();

        parametros.put(ReportesConstants.CUENTA_PARAMETER, consultaSaldos.getCuentaSeleccionada().getDescripcion());
        parametros.put(ReportesConstants.NATURALEZA_PARAMETER, consultaSaldos.getCuentaSeleccionada().getTipoNaturaleza()
                .getDescripcion());
        parametros.put(ReportesConstants.TIPO_CUENTA_PARAMETER, consultaSaldos.getCuentaSeleccionada().getTipoCuenta()
                .getDescripcion());
        parametros.put(ReportesConstants.DIVISA_PARAMETER, consultaSaldos.getDivisaSeleccionada().getDescripcion());
        parametros.put(ReportesConstants.BOVEDA_PARAMETER, consultaSaldos.getBovedaSeleccionada().getDescripcion());
        parametros.put(ReportesConstants.FECHA_PARAMETER, consultaSaldos.getCriterioFecha());
        parametros.put(ReportesConstants.RESULTADOS_PARAMETER, consultaSaldos.getResultados());
        return parametros;
    }
    
    /**
     * Metodo para obtener el atributo cuentaSeleccionada
     * 
     * @return el atributo cuentaSeleccionada
     */
    public String getCuentaSeleccionada() {

        String cuenta = null;
        List<CuentaEfectivoDTO> resultados = null;
        if (getInstitucionActual().getId() > 0) {
            resultados = consultaSaldos.getCriterioCuenta().getResultados();
        }

        // Si existe solo una cuenta, colocarla como predeterminada
        if (resultados != null && resultados.size() == 1) {
            consultaSaldos.getCriterioCuenta().setOpcionSeleccionada(resultados.get(0));
        }
        cuenta = consultaSaldos.getCriterioCuenta().getOpcionSeleccionada().getNumeroCuenta().equals("-1") ? "TODAS"
                : consultaSaldos.getCriterioCuenta().getOpcionSeleccionada().getNumeroCuenta();
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

        consultaSaldos.getCriterioCuenta().setOpcionSeleccionada(null);
        if (cuentaSeleccionada != null && cuentaSeleccionada.length() > 0) {
            if (!"TODAS".equals(cuentaSeleccionada) && !"TODA".equals(cuentaSeleccionada)) {
            	if (!isUsuarioIndeval()) {
					cuentaSeleccionada = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion() + cuentaSeleccionada;
				} else {
					cuentaSeleccionada = getFolioClaveInstitucion() + cuentaSeleccionada;
				}
                CuentaDTO cuenta = new CuentaDTO();
                cuenta.setNumeroCuenta(cuentaSeleccionada);
                consultaSaldos.getCriterioCuenta().getOpcionSeleccionada().setNumeroCuenta(cuentaSeleccionada);
                consultaSaldos.getCriterioCuenta().getOpcionSeleccionada().setDescripcion(cuentaSeleccionada);
            }

        }

    }

    /**
     * Metodo para generar un reporte con formato XLS o PDF
     * @return cadena de para ejecutar el action para genera el reporte
     */
    public void generarReportes(ActionEvent e) {
    	reiniciarEstadoPeticion();
		resultados = consultaSaldos.getResultados();
		if (resultados != null) {
			registrosEncontrados = resultados.size();
		}
    }

    /**
     * Metodo para obtener el atributo consultaSaldos
     * 
     * @return the consultaSaldos
     */
    public ConsultaDeSaldos getConsultaSaldos() {
        return consultaSaldos;
    }

    /**
     * Metodo para establecer el atributo consultaSaldos
     * 
     * @param consultaSaldos the consultaSaldos to set
     */
    public void setConsultaSaldos(ConsultaDeSaldos consultaSaldos) {
        this.consultaSaldos = consultaSaldos;
    }

    /**
     * Metodo para obtener el atributo consultaEjecutada
     * 
     * @return the consultaEjecutada
     */
    public boolean isConsultaEjecutada() {
        return consultaEjecutada;
    }

    /**
     * Metodo para establecer el atributo consultaEjecutada
     * 
     * @param consultaEjecutada the consultaEjecutada to set
     */
    public void setConsultaEjecutada(boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

    /**
     * Obtiene el campo resumenSaldo
     * 
     * @return resumenSaldo
     */
    public SaldoEfectivoDTO getResumenSaldo() {
        return resumenSaldo;
    }

    /**
     * Asigna el valor del campo resumenSaldo
     * 
     * @param resumenSaldo el valor de resumenSaldo a asignar
     */
    public void setResumenSaldo(SaldoEfectivoDTO resumenSaldo) {
        this.resumenSaldo = resumenSaldo;
    }

    @Override
    protected Collection<? extends Object> ejecutarConsultaReporte() {
        List<Object> datosReporte = new ArrayList<Object>();
        datosReporte.add(StringUtils.EMPTY);
        return datosReporte;
    }

    @Override
    protected String getNombreReporte() {

        return ReportesConstants.REPORTE_CONSULTA_SALDOS;
    }

    /**
     * Obtiene el valor del campo resultados
     * @return el valor de resultados
     */
    public List<SaldoEfectivoDTO> getResultados() {
        return resultados;
    }

    /**
     * Asigna el campo resultados
     * @param resultados el valor de resultados a asignar
     */
    public void setResultados(List<SaldoEfectivoDTO> resultados) {
        this.resultados = resultados;
    }

	/**
	 * @return the resumenConsulta
	 */
	public Map<String, Number> getResumenConsulta() {
		return resumenConsulta;
	}

	/**
	 * @param resumenConsulta the resumenConsulta to set
	 */
	public void setResumenConsulta(Map<String, Number> resumenConsulta) {
		this.resumenConsulta = resumenConsulta;
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
	
	public String getFolioClaveInstitucion() {
		return folioClaveInstitucion;
	}

	public void setFolioClaveInstitucion(String folioClaveInstitucion) {
		this.folioClaveInstitucion = folioClaveInstitucion;
		
		if(folioClaveInstitucion.length() >= 5) {
			this.folioClaveInstitucion = folioClaveInstitucion.substring(0,5);
			
			institucion = getConsultaInstitucionService().buscarInstitucionPorClaveYFolio(this.folioClaveInstitucion);
			if(institucion != null) {
				consultaSaldos.getCriterioCuenta().setIdInstitucion(institucion.getId());
			}	
		}
		else {
			consultaSaldos.getCriterioCuenta().setIdInstitucion(-1);
			if(folioClaveInstitucion.length() == 2) {
				consultaSaldos.getCriterioCuenta().getOpcionSeleccionada().getInstitucion().setClaveTipoInstitucion(folioClaveInstitucion);
			}
		}
	}
	
	/**
	 * Obtiene el criterio fecha como cadena.
	 * @return Criterio fecha como cadena.
	 */
	public String getCriterioFechaStr() {
		return FormatUtil.DateToShortString(consultaSaldos.getCriterioFecha());
	}
}
