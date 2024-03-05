/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 19, 2007
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
import com.indeval.portaldali.middleware.dto.DefinicionDetalleMovimientoDTO;
import com.indeval.portaldali.middleware.dto.DetalleEstadoCuentaPosicionPorBovedaDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaPosicionPorEmisionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.util.DefinicionDetallesMovimientosHelper;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.util.FormatUtil;
import com.indeval.portaldali.presentation.common.constants.CamposPantallaConstantes;
import com.indeval.portaldali.presentation.common.constants.ReportesConstants;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.BackingBeanBase;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl.ConsultaEstadoCuentaDePosiciones;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Backing bean asociado a la pantalla de consulta del estado de cuenta de
 * posiciones de valores. Esta clase se encarga de la invocación a los servicios
 * de negocio relacionados con este estado de cuenta.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 * 
 */
public class ConsultaEstadoCuentaPosicionesBean extends BackingBeanBase {

	/**
	 * Objeto que representa el esquema de consulta
	 */
	private ConsultaEstadoCuentaDePosiciones consultaEstadoCuentaPosiciones;
	/**
	 * Helper que contiene la definición de la operación que corresonde al tipo
	 * de instrucción de un registro contable.
	 */
	private DefinicionDetallesMovimientosHelper definicionDetallesMovimientosHelper = null;

	/**
	 * Indica si la consulta principal ya fue ejecutada
	 */
	private boolean consultaEjecutada = false;

	/**
	 * Indica si la cuenta consultada es nombrada.
	 */
	private boolean cuentaNombrada = false;

	/** Contiene el valor seleccionado de la emisora en el criterio de emisión */
	private String emisoraSeleccionada = null;
	
	private TipoValorDTO instrumentoSeleccionado = new TipoValorDTO();
	
	/**
	 * Indica si se debe de restaurar la pagicación de los resultados
	 * principales de la página
	 */
	private boolean restaurarPaginacionResultados = true;
	
	/** Total de registros contables encontrados */
	private Long totalResultados = null;
	/**
	 * Resultados temporales de la consulta
	 */
	List<EstadoCuentaPosicionPorEmisionDTO> resultados = null;

	/**
	 * Almacena la clave y folio de la institucion
	 */
	private String folioClaveInstitucion;
	
	/** El nombre de la institución con la que se consulta */
	private String nombreInstitucion;
	
	/**
	 * Institucion a buscar cuando el usuario es INDEVAL
	 */
	private InstitucionDTO institucionConsultada = null;
	
	/** Facade para accesar a los catálogos del DALI */
	private ConsultaCatalogosFacade consultaCatalogos;

	/** Contiene el papel mercado por el cual se filtra el mercado */
	private String papelMercado = DaliConstants.PAPEL_MERCADO_TODOS;

	/**
	 * Inicializa los criterios de consulta
	 * 
	 * @param e
	 *            ActionEvent generado durante la solicitud
	 */
	public void limpiar(ActionEvent e) {
		inicializarCriterios();
	}

	/**
	 * Obtiene el valor del atributo nombreInstitucion
	 *
	 * @return el valor del atributo nombreInstitucion
	 */
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	/**
	 * Establece el valor del atributo nombreInstitucion
	 *
	 * @param nombreInstitucion el valor del atributo nombreInstitucion a establecer
	 */
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}
	
	/**
	 * Asigna el participante predeterminado en la consulta.
	 * 
	 * @return <code>null</code>, este método no requiere de un valor de retorno.
	 */
	public String getInitParticipante() {
		
		folioClaveInstitucion = nombreInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
		nombreInstitucion = folioClaveInstitucion;
		consultaEjecutada = false;
		inicializarCriterios();
		
		return null;
	}
	
	/**
	 * Asigna las opciones predeterminadas para cuando se carga la página por
	 * primerva vez.
	 * 
	 * @return nulo, este método no requiere retornar un valor
	 */
	public String getInit() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (getInstitucionActual() != null) {
			consultaEstadoCuentaPosiciones.getCriterioCuenta().setIdInstitucion(getInstitucionActual().getId());
			nombreInstitucion = folioClaveInstitucion;
			// Reiniciar el criterio de naturaleza para el usuario participante
			// para dejar por default la naturaleza : Pasivo
			if (isUsuarioIndeval()) {
				TipoNaturalezaDTO pasivo = new TipoNaturalezaDTO();
				pasivo.setId(TipoNaturalezaDTO.PASIVO);
				consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioNaturaleza().setOpcionSeleccionada(pasivo);
				
				if(StringUtils.isBlank(nombreInstitucion) && StringUtils.isBlank(folioClaveInstitucion)) {
					nombreInstitucion = DaliConstants.DESCRIPCION_TODOS;
				}
			}
			else {
				folioClaveInstitucion = nombreInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
			}
		}

		if (ctx.getRenderResponse()) {
			if (isConsultaEjecutada()) {

				consultaEstadoCuentaPosiciones.setEmisionSeleccionada(null);
				consultaEstadoCuentaPosiciones.setBovedaSeleccionada(null);
				consultaEstadoCuentaPosiciones.setCuentaSeleccionada(null);
				consultaEstadoCuentaPosiciones.recibirNotificacionResultados(null);
				consultaEstadoCuentaPosiciones.getCriterioEmision().reestablecerEstadoPaginacion();
			}
			consultaEstadoCuentaPosiciones.getOpcionSeleccionada();

		}

		if (!ctx.getRenderKit().getResponseStateManager().isPostback(ctx)) {
			// si es la primera vez que se visita la página
			inicializarCriterios();
		}

		// Prepara la pantalla para precarga los datos de una consulta de estado
		// de cuenta partiendo de la pantallad de posición

		if (ctx.getExternalContext().getRequestParameterMap().get("idPosicion") != null) {
			cargarValoresConsultaEstadoCuenta(ctx.getExternalContext().getRequestParameterMap().get("idPosicion"),
				FormatUtil.stringToDate(ctx.getExternalContext().getRequestParameterMap().get("fechaFinal")),
				ctx.getExternalContext().getRequestParameterMap().get("tipoCuenta"));
			buscarEstadoCuentaPosiciones(null);
		}

		consultaEstadoCuentaPosiciones.setDebeDejarBitacora(false);
		
		return null;
	}
	
	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de tipos
	 * de valor.
	 * 
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con los tipos de valor encontrados
	 */
	public List<TipoValorDTO> buscarTiposValorPorPrefijo(Object prefijo) {
		return consultaCatalogos.getConsultaTipoValorService().buscarTiposDeValoresPorMercadoYPrefijo(
				consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().getOpcionSeleccionada(), String.valueOf(prefijo));
	}
	
	/**
	 * Inicializar los criterios de la consulta
	 */
	private void inicializarCriterios() {
		setConsultaEjecutada(false);
		
		consultaEstadoCuentaPosiciones.setCriterioFechaFinal(new Date());
		consultaEstadoCuentaPosiciones.setCriterioFechaInicial(new Date());
		consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioNaturaleza().setOpcionSeleccionada(null);
		consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioTipoCuenta().setOpcionSeleccionada(null);
		consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().setOpcionSeleccionada(null);

		consultaEstadoCuentaPosiciones.getCriterioCuenta().setOpcionSeleccionada(null);
		consultaEstadoCuentaPosiciones.getCriterioBoveda().setOpcionSeleccionada(null);

		consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().setOpcionSeleccionada(null);
		consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioTipoValor().setOpcionSeleccionada(null);
		consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioEmisora().setOpcionSeleccionada(null);
		consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioSerie().setOpcionSeleccionada(null);
		consultaEstadoCuentaPosiciones.getCriterioEmision().setIsin(null);
		consultaEstadoCuentaPosiciones.getCriterioEmision().setOpcionSeleccionada(null);
		emisoraSeleccionada = null;
		instrumentoSeleccionado = new TipoValorDTO();
		
		consultaEstadoCuentaPosiciones.getCriterioBoveda().reestablecerEstadoPaginacion();
		consultaEstadoCuentaPosiciones.getCriterioEmision().reestablecerEstadoPaginacion();
		consultaEstadoCuentaPosiciones.getCriterioCuenta().reestablecerEstadoPaginacion();
		
		consultaEstadoCuentaPosiciones.reestablecerEstadoPaginacion();
		if (consultaEstadoCuentaPosiciones.getCriterioCuenta().isTodos()) {
			consultaEstadoCuentaPosiciones.getCriterioCuenta().toggleTodos();
		}
		if (consultaEstadoCuentaPosiciones.getCriterioEmision().isTodos()) {
			consultaEstadoCuentaPosiciones.getCriterioEmision().toggleTodos();
		}
		if (!consultaEstadoCuentaPosiciones.getCriterioBoveda().isTodos()) {
			consultaEstadoCuentaPosiciones.getCriterioBoveda().toggleTodos();
		}

	}

	/**
	 * Indica si existe un parámetro en el mapa del request que indica si la
	 * consulta fue invocada desde la pantalla de consulta de posición.
	 * 
	 * @return True si la pantalla fue invocada desde la consulta de posición,
	 *         false en otro caso
	 */
	public boolean isNavegacionPosicion() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		return ctx.getExternalContext().getRequestParameterMap().get("idPosicion") != null;
	}

	/**
	 * Carga los valores iniciales con los que se consulta el estado de cuenta
	 * partiendo de un id de posición y una fecha final
	 * 
	 * @param idPosicionString
	 *            Identificador de la posición
	 * @param fechaFinal
	 *            Fecha final para la consulta
	 * @param tipoCuenta
	 *            tipo de cuenta de la consulta
	 */
	private void cargarValoresConsultaEstadoCuenta(String idPosicionString, Date fechaFinal, String tipoCuenta) {
		long idPosicion = 0;
		idPosicion = NumberUtils.toLong(idPosicionString, -1);
		PosicionDTO posicion = 
			consultaEstadoCuentaPosiciones.getConsultaEstadoCuentaPosicionService().buscarPosicionPorId(
				fechaFinal,idPosicion, tipoCuenta);
		if (posicion != null) {
			consultaEstadoCuentaPosiciones.setCriterioFechaFinal(fechaFinal);
			consultaEstadoCuentaPosiciones.setCriterioFechaInicial(fechaFinal);
			consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioNaturaleza().setOpcionSeleccionada(
					posicion.getCuenta().getTipoTenencia().getTipoNaturaleza());
			consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioTipoCuenta().setOpcionSeleccionada(
					posicion.getCuenta().getTipoTenencia().getTipoCuenta());
			consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().setOpcionSeleccionada(posicion.getCuenta().getTipoTenencia());
			consultaEstadoCuentaPosiciones.getCriterioCuenta().setOpcionSeleccionada(posicion.getCuenta());
			consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().setOpcionSeleccionada(
					posicion.getEmision().getTipoValor().getMercado());
			consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioTipoValor().setOpcionSeleccionada(posicion.getEmision().getTipoValor());
			consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioEmisora().setOpcionSeleccionada(posicion.getEmision().getEmisora());
			emisoraSeleccionada = posicion.getEmision().getEmisora().getDescripcion();
			consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioSerie().setOpcionSeleccionada(posicion.getEmision().getSerie());
			consultaEstadoCuentaPosiciones.getCriterioEmision().setIsin(posicion.getEmision().getIsin());
			consultaEstadoCuentaPosiciones.getCriterioBoveda().setOpcionSeleccionada(posicion.getBoveda());
			//Se agrega línea para setear la institucion
			folioClaveInstitucion = nombreInstitucion =
				posicion.getCuenta().getInstitucion().getClaveTipoInstitucion() + 
				posicion.getCuenta().getInstitucion().getFolioInstitucion(); 
		}
	}

	/**
	 * Prepara los valores para la ejecución de la consulta de posiciones
	 * @param e ActionEvent generado durante la petición
	 */
	public void buscarEstadoCuentaPosiciones(ActionEvent e) {
		boolean fechasValidas = 
			validarFechaObligatoria(consultaEstadoCuentaPosiciones.getCriterioFechaInicial(), false, CamposPantallaConstantes.campoFechaInicial) &&
			validarFechaObligatoria(consultaEstadoCuentaPosiciones.getCriterioFechaFinal(), false, CamposPantallaConstantes.campoFechaFinal);
		fechasValidas = fechasValidas && validarFechaFinalVsFechaInicial(
			consultaEstadoCuentaPosiciones.getCriterioFechaInicial(), consultaEstadoCuentaPosiciones.getCriterioFechaFinal());
		if(fechasValidas) {
			nombreInstitucion = folioClaveInstitucion;
			if (consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioTipoCuenta().getOpcionSeleccionada().getDescripcion()
					.equals("NOMBRADA")) {
				cuentaNombrada = Boolean.TRUE;
			}
			
			consultaEstadoCuentaPosiciones.setCuentaSeleccionada(null);
			consultaEstadoCuentaPosiciones.setEmisionSeleccionada(null);
			consultaEstadoCuentaPosiciones.setBovedaSeleccionada(null);
			
			if (consultaEstadoCuentaPosiciones.getCriterioCuenta().isTodos()) {
				consultaEstadoCuentaPosiciones.getCriterioCuenta().toggleTodos();
			}
			if (consultaEstadoCuentaPosiciones.getCriterioEmision().isTodos()) {
				consultaEstadoCuentaPosiciones.getCriterioEmision().toggleTodos();
			}
			if (!consultaEstadoCuentaPosiciones.getCriterioBoveda().isTodos()) {
				consultaEstadoCuentaPosiciones.getCriterioBoveda().toggleTodos();
			}
			
			consultaEstadoCuentaPosiciones.getCriterioEmision().getEstadoPaginacion().setNumeroPagina(1);
			consultaEstadoCuentaPosiciones.getCriterioEmision().getEstadoPaginacion().setTotalResultados(0);
			
			consultaEstadoCuentaPosiciones.getCriterioCuenta().getEstadoPaginacion().setNumeroPagina(1);
			consultaEstadoCuentaPosiciones.getCriterioCuenta().getEstadoPaginacion().setTotalResultados(0);
			
			consultaEstadoCuentaPosiciones.setDebeDejarBitacora(true);
			consultaEstadoCuentaPosiciones.recibirNotificacionResultados(null);
			consultaEstadoCuentaPosiciones.setDebeDejarBitacora(false);
			
			consultaEstadoCuentaPosiciones.getCriterioEmision().reestablecerEstadoPaginacion();
			consultaEstadoCuentaPosiciones.getCriterioCuenta().reestablecerEstadoPaginacion();
			consultaEstadoCuentaPosiciones.getCriterioBoveda().reestablecerEstadoPaginacion();
			
			consultaEstadoCuentaPosiciones.reestablecerEstadoPaginacion();
			consultaEstadoCuentaPosiciones.setColumnaOrdenada(null);
			consultaEstadoCuentaPosiciones.setOrdenAscendente(true);
			
			if (folioClaveInstitucion != null && folioClaveInstitucion.length() == 2) {
				consultaEstadoCuentaPosiciones.getCuentaSeleccionada().getInstitucion().setClaveTipoInstitucion(folioClaveInstitucion);
			}
			setConsultaEjecutada(true);
		}
		else {
			consultaEjecutada = false;
		}
	}
	
	/**
	 * Obtiene una lista con las páginas que actualmente retorna la consulta
	 * principal de estado de cuenta
	 * 
	 * @return Lista de páginas obtenidas
	 */
	public List<Integer> getListaPaginasResultado() {
		List<Integer> paginas = new ArrayList<Integer>();

		FacesContext ctx = FacesContext.getCurrentInstance();
		// Si se estn mostrando resultados de la consulta
		if (ctx.getRenderResponse() && isConsultaEjecutada()) {
			// se llena una lista con las páginas de la consulta
			for (int i = 1; i <= consultaEstadoCuentaPosiciones.getCriterioEmision().getEstadoPaginacion().getTotalPaginas(); i++) {
				paginas.add(i);
			}
		}

		return paginas;
	}
	
	/**
	 * Atiene el evento de cambio de elemento seleccionado del criterio de papel
	 * mercado.
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void cambioSelectPapelMercado(ActionEvent e) {
		MercadoDTO mercadosTodos = new MercadoDTO();
		mercadosTodos.setDescripcion("TODOS");
		if(papelMercado.equals(DaliConstants.PAPEL_MERCADO_TODOS)) {
			mercadosTodos.setId(DaliConstants.VALOR_COMBO_TODOS);
		} else {
			if(papelMercado.equals(DaliConstants.PAPEL_MERCADO_DINERO)) {
				mercadosTodos.setId(DaliConstants.ID_MERCADO_DINERO);
			} else {
				mercadosTodos.setId(DaliConstants.ID_MERCADO_CAPITALES);
			}
		}
		consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().setOpcionSeleccionada(mercadosTodos);
		consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioTipoValor().setOpcionSeleccionada(null);
		
	}
	
	/**
	 * Obtiene la descripción del atributo papelMercado.
	 * 
	 * @return la descripción del atributo papelMercado.
	 */
	public String getDescripcionPapelMercado() {
		String resultado = "";
		if (DaliConstants.PAPEL_MERCADO_TODOS.equals(papelMercado)) {
			resultado = DaliConstants.DESCRIPCION_TODOS;
		} else {
			if (DaliConstants.PAPEL_MERCADO_DINERO.equals(papelMercado)) {
				resultado = DaliConstants.DESCRIPCION_MERCADO_DINERO;
			} else {
				resultado = DaliConstants.DESCRIPCION_MERCADO_CAPITALES;
			}
		}

		return resultado;
	}
	
	/**
	 * Obtiene la descripción del papel de Mercado.
	 * 
	 * @return la descripción del papel de Mercado.
	 */
	public String getDescripcionSubPapelMercado() {
		String resultado = "";
		if (DaliConstants.PAPEL_MERCADO_TODOS.equals(papelMercado)) {
			resultado = DaliConstants.DESCRIPCION_TODOS;
		} else {
			if (DaliConstants.PAPEL_MERCADO_DINERO.equals(papelMercado)) {
				if(DaliConstants.ID_MERCADO_DINERO == consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().getOpcionSeleccionada().getId()) {
					resultado = DaliConstants.DESCRIPCION_TODOS;
				} else {
					resultado = consultaEstadoCuentaPosiciones.getCriterioEmision().getOpcionSeleccionada().getTipoValor().getMercado().getDescripcion();
				}
			} else {
				resultado = DaliConstants.DESCRIPCION_TODOS;
			}
		}

		return resultado;
	}
	
	/**
	 * Esta función es invocada durane el evento de cambio en el control de
	 * naturaleza de la cuenta
	 * 
	 * @param e
	 *            Evento generado durante la petición
	 */
	public void cambioSelectNaturaleza(ActionEvent e) {
		consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().setOpcionSeleccionada(null);
		consultaEstadoCuentaPosiciones.getCriterioCuenta().setOpcionSeleccionada(null);
	}

	public void cambioSelectTipoCuenta(ActionEvent e) {
		consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().setOpcionSeleccionada(null);
		consultaEstadoCuentaPosiciones.getCriterioCuenta().setOpcionSeleccionada(null);
	}

	public void cambioSelectTipoTenencia(ActionEvent e) {

		consultaEstadoCuentaPosiciones.getCriterioCuenta().setOpcionSeleccionada(null);
	}

	public void cambioSelectMercado(ActionEvent e) {

		consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioTipoValor().setOpcionSeleccionada(null);
		consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioSerie().setOpcionSeleccionada(null);
	}

	public void cambioSelectTipoValor(ActionEvent e) {

		consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioSerie().setOpcionSeleccionada(null);
	}

	public void cambioCuenta(ActionEvent e) {

	}

	/**
	 * Inicializa los resultados de consulta
	 * 
	 * @return null
	 */
	public String getInitConsulta() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (ctx.getRenderResponse() && consultaEjecutada) {
			resultados = consultaEstadoCuentaPosiciones.getPaginaDeResultados();
			if (TipoCuentaConstants.TIPO_NOMBRADA.equals(consultaEstadoCuentaPosiciones.getCriterioCuenta().getOpcionSeleccionada().getTipoTenencia()
					.getTipoCuenta().getId())) {
				asignarPantallaDetalle(resultados);
			}
			totalResultados = consultaEstadoCuentaPosiciones.getConsultaEstadoCuentaPosicionService().obtenerProyeccionDeRegistrosContablesDePosiciones(
					consultaEstadoCuentaPosiciones.getCriterioConsulta());
			//Le indica al garbage collector que puede pasar.
			pasarGarbageCollector();
		}
		else {
			resultados = new ArrayList<EstadoCuentaPosicionPorEmisionDTO>();
		}
		return null;
	}

	/**
	 * Libera los recursos de la consulta
	 * 
	 * @return null
	 */
	public String getLimpiarConsulta() {
		resultados = null;
		return null;
	}

	/**
	 * Realiza la consulta al controlador de consulta de posición. La consulta
	 * es realiza siempre y cuando el ciclo de vida de la solicitud est en la
	 * fase de respuesta al cliente.
	 * 
	 * @return Lista con los resultados de la consulta de posiciones
	 */
	public List<EstadoCuentaPosicionPorEmisionDTO> getResultadosEstadoCuentaPosicion() {

		return resultados;

	}

	/**
	 * Recorre los resultados de la consulta de estado de cuenta y asigna a cada
	 * registro contable la ruta de la pantalla de detalle que debe mostrar.
	 * 
	 * @param listaEmisiones
	 *            Lista de registros contable agrupados por emisión y bóveda.
	 */

	private void asignarPantallaDetalle(List<EstadoCuentaPosicionPorEmisionDTO> listaEmisiones) {
		DefinicionDetalleMovimientoDTO definicionDetalle = null;

		for (EstadoCuentaPosicionPorEmisionDTO estadoCuentaPosicionPorEmisionDTO : listaEmisiones) {
			for (DetalleEstadoCuentaPosicionPorBovedaDTO detalleEstadoCuentaPosicionPorBovedaDTO : estadoCuentaPosicionPorEmisionDTO.getDetallesBoveda()) {
				if (detalleEstadoCuentaPosicionPorBovedaDTO.getRegistrosContablesNombradas() != null) {

					for (RegistroContablePosicionNombradaDTO registroContablePosicionNombradaDTO : detalleEstadoCuentaPosicionPorBovedaDTO
							.getRegistrosContablesNombradas()) {
						definicionDetalle = definicionDetallesMovimientosHelper.buscarDefinicionDetalleMovimiento(registroContablePosicionNombradaDTO
								.getOperacion().getInstruccionLiquidacion().getTipoInstruccion().getIdTipoInstruccion());
						if (definicionDetalle != null) {
							registroContablePosicionNombradaDTO.setRutaPantallaDetalle(definicionDetalle.getRutaPantallaDetalleMovimiento());
							registroContablePosicionNombradaDTO.setAltoPantallaDetalle(definicionDetalle.getAltoPantalla());
							registroContablePosicionNombradaDTO.setAnchoPantallaDetalle(definicionDetalle.getAnchoPantalla());
						}

					}

				}
			}
		}

	}

	/**
	 * Invoca las funciones de navegación de página de la consulta principal de
	 * la pantalla
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void navegarPorResultados(ActionEvent e) {
		String navegacion = (String) e.getComponent().getAttributes().get("navegacion");

		if (navegacion != null && navegacion.length() > 0) {
			try {
				consultaEstadoCuentaPosiciones.getCriterioEmision().getClass().getMethod(navegacion)
						.invoke(consultaEstadoCuentaPosiciones.getCriterioEmision());
			} catch (Exception ex) {
				logger.error("Error de invocación de método al navega por los resultados principales", ex);
			}
			restaurarPaginacionResultados = false;
		}

	}

	/**
	 * Determina si se debe de restaurar la paginación de los resultados
	 * principaless. Se debe restaurar una paginación despus de realizar
	 * cualquier cambio que afecte la cantidad de resultados.
	 * 
	 * @return <code>null</code>
	 */
	public String getRestaurarPaginacion() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (restaurarPaginacionResultados && ctx.getRenderResponse()) {

			consultaEstadoCuentaPosiciones.reestablecerEstadoPaginacion();
		}
		restaurarPaginacionResultados = true;
		return null;
	}

	/**
	 * Busca emisoras en el catálogo cuya descripción comiencen con el prefijo
	 * proporcionado.
	 * 
	 * @param prefijo
	 *            el prefijo a consultar en la BD.
	 * @return una lista con objetos de tipo {@link EmisoraDTO} con todas las
	 *         coincidencias encontradas.
	 */
	public List<EmisoraDTO> buscarEmisorasPorPrefijo(Object value) {
		List<EmisoraDTO> emisoras = new ArrayList<EmisoraDTO>();
		String prefijoAjustado = "";
		if (value != null) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
		}

		if (value != null) {
			emisoras = consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioEmisora().buscarEmisorasPorPrefijo(prefijoAjustado);
		}
		return emisoras;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de bóveda
	 * 
	 * @return Lista con las opciones válidas del criterio de bóveda
	 */
	public List<SelectItem> getOpcionesComboBoveda() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		BovedaDTO bovedaTodos = new BovedaDTO();
		bovedaTodos.setId(-1);
		bovedaTodos.setDescripcion("TODAS");

		opcionesCombo.add(new SelectItem(bovedaTodos, bovedaTodos.getDescripcion()));

		for (BovedaDTO boveda : consultaEstadoCuentaPosiciones.getCriterioBoveda().getResultados()) {
			opcionesCombo.add(new SelectItem(boveda, boveda.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Naturaleza
	 * 
	 * @return Lista con las opciones válidas del criterio de Naturaleza
	 */
	public List<SelectItem> getOpcionesComboNaturaleza() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		for (TipoNaturalezaDTO nat : consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioNaturaleza().getResultados()) {
			opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Tipo de Cuenta
	 * 
	 * @return Lista con las opciones válidas del criterio de Tipo de Cuenta
	 */
	public List<SelectItem> getOpcionesComboTipoCuenta() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		for (TipoCuentaDTO nat : consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioTipoCuenta().getResultados()) {
			opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Tipo Tenencia
	 * 
	 * @return Lista con las opciones válidas del criterio de tipo Tenencia
	 */
	public List<SelectItem> getOpcionesComboTipoTenencia() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		TipoTenenciaDTO tenenciatodos = new TipoTenenciaDTO();
		tenenciatodos.setIdTipoCuenta(-1);
		tenenciatodos.setDescripcion("TODAS");

		opcionesCombo.add(new SelectItem(tenenciatodos, tenenciatodos.getDescripcion()));

		for (TipoTenenciaDTO nat : consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getResultados()) {
			opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Cuenta
	 * 
	 * @return Lista con las opciones válidas del criterio de Cuenta
	 */
	public List<SelectItem> getOpcionesComboCuenta() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		CuentaDTO cuentaTodos = new CuentaDTO();
		cuentaTodos.setNumeroCuenta("-1");
		cuentaTodos.setDescripcion("TODAS");

		opcionesCombo.add(new SelectItem(cuentaTodos, cuentaTodos.getDescripcion()));

		for (CuentaDTO nat : consultaEstadoCuentaPosiciones.getCriterioCuenta().getResultados()) {
			opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Mercado
	 * 
	 * @return Lista con las opciones válidas del criterio de Mercado
	 */
	public List<SelectItem> getOpcionesComboMercado() {
		List<SelectItem> resultado = null;
		if (StringUtils.isBlank(papelMercado) || DaliConstants.PAPEL_MERCADO_TODOS.equals(papelMercado)) {
			resultado = consultaCatalogos.getSelectItemMercadoTodos();
		} else {
			if (DaliConstants.PAPEL_MERCADO_DINERO.equals(papelMercado)) {
				resultado = consultaCatalogos.getSelectItemsDeMercadoDinero();
			} else {
				resultado = consultaCatalogos.getSelectItemsDeMercadoCapitales();
			}
		}
		return resultado;
	}

	/**
	 * Obtiene el atributo emisoraSeleccionada
	 * 
	 * @return el emisoraSeleccionada
	 */
	public String getEmisoraSeleccionada() {
		return emisoraSeleccionada;
	}

	/**
	 * Establece el atributo emisoraSeleccionada
	 * 
	 * @param emisoraSeleccionada
	 *            el emisoraSeleccionada a establecer
	 */
	public void setEmisoraSeleccionada(String emisoraSeleccionada) {
		this.emisoraSeleccionada = emisoraSeleccionada;
				
		EmisoraDTO emisora = null;
		if(StringUtils.isNotBlank(emisoraSeleccionada)) {
			emisora = consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioEmisora().getConsultaEmisoraService().buscarEmisoraPorDescripcion(
				emisoraSeleccionada.toUpperCase());
			if (emisora == null) {
				emisora = new EmisoraDTO();
				// Valor para indicar que va a ir a buscar una emisora inexistente
				emisora.setId(DaliConstants.VALOR_COMBO_NINGUNO);
				emisora.setDescripcion(emisoraSeleccionada);
			}
		}
		consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioEmisora().setOpcionSeleccionada(emisora);
	
		//EmisoraDTO emisora = consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioEmisora().getConsultaEmisoraService().buscarEmisoraPorDescripcion(
		//		emisoraSeleccionada);
		//if (emisora != null || (emisoraSeleccionada != null && emisoraSeleccionada.length() == 0)) {
		//	consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioEmisora().setOpcionSeleccionada(emisora);
		//}
	}
	
	public TipoValorDTO getInstrumentoSeleccionado() {
		return instrumentoSeleccionado;
	}

	public void setInstrumentoSeleccionado(TipoValorDTO instrumentoSeleccionado) {
		this.instrumentoSeleccionado = instrumentoSeleccionado;
		MercadoDTO mercadoSeleccionado = consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().getOpcionSeleccionada();
		if(instrumentoSeleccionado != null) {
			consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioTipoValor().setOpcionSeleccionada(instrumentoSeleccionado);
		} 
		consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().setOpcionSeleccionada(mercadoSeleccionado);
		consultaEstadoCuentaPosiciones.getCriterioEmision().getCriterioTipoValor().getOpcionSeleccionada().setMercado(mercadoSeleccionado);
	}

	/**
	 * método para generar un reporte con formato XLS o PDF
	 * 
	 * @return cadena de para ejecutar el action para genera el reporte
	 */
	public void generarReportes(ActionEvent e) {
		reiniciarEstadoPeticion();
		resultados = consultaEstadoCuentaPosiciones.getResultados();
		this.setResultados(resultados);
		totalResultados = 0L;
		for(EstadoCuentaPosicionPorEmisionDTO emision : resultados) {
			for(DetalleEstadoCuentaPosicionPorBovedaDTO boveda : emision.getDetallesBoveda()){
				if( consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioTipoCuenta().
						getOpcionSeleccionada().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA) ){
					totalResultados += boveda.getRegistrosContablesControladas().size();
				}
				else {
					totalResultados += boveda.getRegistrosContablesNombradas().size();
				}
			}
		}
		//Le indica al garbage collector que puede pasar.
		pasarGarbageCollector();
	}

	/**
	 * @return the resultados
	 */
	public List<EstadoCuentaPosicionPorEmisionDTO> getResultados() {
		return resultados;
	}

	/**
	 * @param resultados
	 *            the resultados to set
	 */
	public void setResultados(List<EstadoCuentaPosicionPorEmisionDTO> resultados) {
		this.resultados = resultados;
	}

	/**
	 * Genera un mapa con los parámetros de la consulta de posiciones.
	 * 
	 * @return Map mapa con los parámetros de la consulta
	 */
	@Override
	protected Map<String, Object> llenarParametros() {
		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put(ReportesConstants.CUENTA_PARAMETER, consultaEstadoCuentaPosiciones.getCuentaSeleccionada().getDescripcion());
		parametros.put(ReportesConstants.NATURALEZA_PARAMETER, consultaEstadoCuentaPosiciones.getCuentaSeleccionada().getTipoTenencia().getTipoNaturaleza()
				.getDescripcion());
		parametros.put(ReportesConstants.TIPO_CUENTA_PARAMETER, consultaEstadoCuentaPosiciones.getCuentaSeleccionada().getTipoTenencia().getTipoCuenta()
				.getDescripcion());
		parametros.put(ReportesConstants.TIPO_TENENCIA_PARAMETER, consultaEstadoCuentaPosiciones.getCuentaSeleccionada().getTipoTenencia().getDescripcion());
		parametros.put(ReportesConstants.TIPO_MERCADO_PARAMETER, consultaEstadoCuentaPosiciones.getCriterioEmision().getOpcionSeleccionada().getTipoValor()
				.getMercado().getDescripcion());
		parametros.put(ReportesConstants.ISIN_PARAMETER, consultaEstadoCuentaPosiciones.getCriterioEmision().getOpcionSeleccionada().getIsin());
		parametros.put(ReportesConstants.TV_PARAMETER, consultaEstadoCuentaPosiciones.getCriterioEmision().getOpcionSeleccionada().getTipoValor()
				.getClaveTipoValor());
		parametros.put(ReportesConstants.EMISORA_PARAMETER, consultaEstadoCuentaPosiciones.getCriterioEmision().getOpcionSeleccionada().getEmisora()
				.getDescripcion());
		parametros.put(ReportesConstants.SERIE_PARAMETER, consultaEstadoCuentaPosiciones.getCriterioEmision().getOpcionSeleccionada().getSerie()
				.getDescripcion());
		parametros.put(ReportesConstants.CUPON_PARAMETER, consultaEstadoCuentaPosiciones.getCriterioEmision().getOpcionSeleccionada().getCupon());
		parametros.put(ReportesConstants.BOVEDA_PARAMETER, consultaEstadoCuentaPosiciones.getCriterioBoveda().getOpcionSeleccionada().getDescripcion());
		parametros.put(ReportesConstants.FECHA_INICIAL_PARAMETER, consultaEstadoCuentaPosiciones.getCriterioFechaInicial());
		parametros.put(ReportesConstants.FECHA_FINAL_PARAMETER, consultaEstadoCuentaPosiciones.getCriterioFechaFinal());

		return parametros;
	}

	/**
	 * Busca cuentas en el catálogo cuyo número de cuenta comiencen con el
	 * prefijo proporcionado.
	 * 
	 * @param prefijo
	 *            el prefijo a consultar en la BD.
	 * @return una lista con objetos de tipo {@link CuentaDTO} con todas las
	 *         coincidencias encontradas.
	 */
	public List<CuentaDTO> buscarCuentasPorPrefijo(Object value) {
		List<CuentaDTO> cuentas = new ArrayList<CuentaDTO>();

		String prefijoAjustado = "";
		if (value != null) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
		}

		if (value != null && consultaEstadoCuentaPosiciones.getCriterioCuenta().getIdInstitucion() != -1) {

			CuentaDTO criterio = new CuentaDTO();

			if (!isUsuarioIndeval()) {
				criterio.setNumeroCuenta(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion() + prefijoAjustado);
				criterio.setInstitucion(getInstitucionActual());
				folioClaveInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion() + prefijoAjustado;
			} else {
				criterio.setNumeroCuenta(getFolioClaveInstitucion() + prefijoAjustado);
				criterio.setInstitucion(institucionConsultada);
			}

			criterio.setTipoTenencia(consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getOpcionSeleccionada());
			
			if(folioClaveInstitucion.length() > 2) {
				cuentas = consultaEstadoCuentaPosiciones.getCriterioCuenta().getConsultaCuentaService().buscarCuentasPorFragmentoNumeroCuenta(criterio);
			}

			for (CuentaDTO cuentaDTO : cuentas) {
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
		List<CuentaDTO> resultados = null;
		if (consultaEstadoCuentaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getOpcionSeleccionada().getIdTipoCuenta() > 0
				&& getInstitucionActual().getId() > 0) {
			resultados = consultaEstadoCuentaPosiciones.getCriterioCuenta().getResultados();
		}

		// Si existe solo una cuenta, colocarla como predeterminada
		if (resultados != null && resultados.size() == 1) {
			consultaEstadoCuentaPosiciones.getCriterioCuenta().setOpcionSeleccionada(resultados.get(0));
		}
		cuenta = consultaEstadoCuentaPosiciones.getCriterioCuenta().getOpcionSeleccionada().getNumeroCuenta().equals("-1") ? "TODAS"
				: consultaEstadoCuentaPosiciones.getCriterioCuenta().getOpcionSeleccionada().getNumeroCuenta();
		// Si existe institución activa se deben de omitir los caracteres del
		// folio y el tipo de institución
		if (getInstitucionActual().getId() > 0) {
			cuenta = cuenta.replace(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion(), StringUtils.EMPTY);
		}
		return cuenta;
	}

	/**
	 * Establece el valor del atributo cuentaSeleccionada
	 * 
	 * @param cuentaSeleccionada
	 *            el valor del atributo cuentaSeleccionada a establecer
	 */
	public void setCuentaSeleccionada(String cuentaSeleccionada) {
		consultaEstadoCuentaPosiciones.getCriterioCuenta().setOpcionSeleccionada(null);
		if (cuentaSeleccionada != null && cuentaSeleccionada.length() > 0) {
			if (!"TODAS".equals(cuentaSeleccionada) && !"TODA".equals(cuentaSeleccionada)) {
				if (!isUsuarioIndeval()) {
					cuentaSeleccionada = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion() + cuentaSeleccionada;
				}
				else if(cuentaSeleccionada.length() == 4) {						
					cuentaSeleccionada = getFolioClaveInstitucion() + cuentaSeleccionada;
				}
				CuentaDTO cuenta = new CuentaDTO();
				cuenta.setNumeroCuenta(cuentaSeleccionada);
				consultaEstadoCuentaPosiciones.getCriterioCuenta().getOpcionSeleccionada().setNumeroCuenta(cuentaSeleccionada);
				consultaEstadoCuentaPosiciones.getCriterioCuenta().getOpcionSeleccionada().setDescripcion(cuentaSeleccionada);
			}
		}
	}

	/**
	 * Obtiene el valor del atributo consultaEstadoCuentaPosiciones
	 * 
	 * @return el valor del atributo consultaEstadoCuentaPosiciones
	 */
	public ConsultaEstadoCuentaDePosiciones getConsultaEstadoCuentaPosiciones() {
		return consultaEstadoCuentaPosiciones;
	}

	/**
	 * Establece el valor del atributo consultaEstadoCuentaPosiciones
	 * 
	 * @param consultaEstadoCuentaPosiciones
	 *            el valor del atributo consultaEstadoCuentaPosiciones a
	 *            establecer.
	 */
	public void setConsultaEstadoCuentaPosiciones(ConsultaEstadoCuentaDePosiciones consultaEstadoCuentaPosiciones) {
		this.consultaEstadoCuentaPosiciones = consultaEstadoCuentaPosiciones;
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
	 * @param consultaEjecutada
	 *            el valor de consultaEjecutada a asignar
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	/**
	 * Obtiene el campo restaurarPaginacionResultados
	 * 
	 * @return restaurarPaginacionResultados
	 */
	public boolean isRestaurarPaginacionResultados() {
		return restaurarPaginacionResultados;
	}

	/**
	 * Asigna el valor del campo restaurarPaginacionResultados
	 * 
	 * @param restaurarPaginacionResultados
	 *            el valor de restaurarPaginacionResultados a asignar
	 */
	public void setRestaurarPaginacionResultados(boolean restaurarPaginacionResultados) {
		this.restaurarPaginacionResultados = restaurarPaginacionResultados;
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
	 * @param definicionDetallesMovimientosHelper
	 *            el valor de definicionDetallesMovimientosHelper a asignar
	 */
	public void setDefinicionDetallesMovimientosHelper(DefinicionDetallesMovimientosHelper definicionDetallesMovimientosHelper) {
		this.definicionDetallesMovimientosHelper = definicionDetallesMovimientosHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#ejecutarConsultaReporte()
	 */
	@Override
	protected Collection<? extends Object> ejecutarConsultaReporte() {
		return (Collection<? extends Object>) consultaEstadoCuentaPosiciones.getResultados();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#getNombreReporte()
	 */
	@Override
	protected String getNombreReporte() {
		return ReportesConstants.REPORTE_ESTADO_CUENTA_POSICIONES;
	}

	/**
	 * Establece el valor del atributo folioClaveInstitucion
	 * 
	 * @param folioClaveInstitucion
	 *            el valor del atributo folioClaveInstitucion a establecer.
	 */
	public void setFolioClaveInstitucion(String folioClaveInstitucion) {
		this.folioClaveInstitucion = folioClaveInstitucion;

		if (folioClaveInstitucion.length() >= 5) {
			this.folioClaveInstitucion = folioClaveInstitucion.substring(0, 5);

			institucionConsultada = getConsultaInstitucionService().buscarInstitucionPorClaveYFolio(this.folioClaveInstitucion);
			if (institucionConsultada != null) {
				consultaEstadoCuentaPosiciones.getCriterioCuenta().setIdInstitucion(institucionConsultada.getId());

			}
		} else {
			consultaEstadoCuentaPosiciones.getCriterioCuenta().setIdInstitucion(-1);
			if (folioClaveInstitucion.length() == 2) {
				consultaEstadoCuentaPosiciones.getCriterioCuenta().getOpcionSeleccionada().getInstitucion().setClaveTipoInstitucion(folioClaveInstitucion);
			}
		}
	}

	/**
	 * Obtiene el valor del atributo cuentaNombrada
	 * 
	 * @return el valor del atributo cuentaNombrada
	 */
	public boolean isCuentaNombrada() {
		return cuentaNombrada;
	}

	/**
	 * Establece el valor del atributo cuentaNombrada
	 * 
	 * @param cuentaNombrada
	 *            el valor del atributo cuentaNombrada a establecer
	 */
	public void setCuentaNombrada(boolean cuentaNombrada) {
		this.cuentaNombrada = cuentaNombrada;
	}

	/**
	 * Obtiene el valor del atributo totalResultados
	 * 
	 * @return el valor del atributo totalResultados
	 */
	public Long getTotalResultados() {
		return totalResultados;
	}

	/**
	 * Establece el valor del atributo totalResultados
	 * 
	 * @param totalResultados
	 *            el valor del atributo totalResultados a establecer
	 */
	public void setTotalResultados(Long totalResultados) {
		this.totalResultados = totalResultados;
	}

	/**
	 * Obtiene el valor del atributo institucionConsultada
	 * 
	 * @return el valor del atributo institucionConsultada
	 */
	public InstitucionDTO getInstitucionConsultada() {
		return institucionConsultada;
	}

	/**
	 * Establece el valor del atributo institucionConsultada
	 * 
	 * @param institucionConsultada
	 *            el valor del atributo institucionConsultada a establecer
	 */
	public void setInstitucionConsultada(InstitucionDTO institucionConsultada) {
		this.institucionConsultada = institucionConsultada;
	}

	/**
	 * Obtiene el valor del atributo consultaCatalogos
	 * 
	 * @return el valor del atributo consultaCatalogos
	 */
	public ConsultaCatalogosFacade getConsultaCatalogos() {
		return consultaCatalogos;
	}

	/**
	 * Establece el valor del atributo consultaCatalogos
	 * 
	 * @param consultaCatalogos
	 *            el valor del atributo consultaCatalogos a establecer
	 */
	public void setConsultaCatalogos(ConsultaCatalogosFacade consultaCatalogos) {
		this.consultaCatalogos = consultaCatalogos;
	}

	/**
	 * Obtiene el valor del atributo papelMercado
	 * 
	 * @return el valor del atributo papelMercado
	 */
	public String getPapelMercado() {
		return papelMercado;
	}

	/**
	 * Establece el valor del atributo papelMercado
	 * 
	 * @param papelMercado
	 *            el valor del atributo papelMercado a establecer
	 */
	public void setPapelMercado(String papelMercado) {
		this.papelMercado = papelMercado;
	}

	/**
	 * Obtiene el valor del atributo folioClaveInstitucion
	 * 
	 * @return el valor del atributo folioClaveInstitucion
	 */
	public String getFolioClaveInstitucion() {
		return folioClaveInstitucion;
	}

}
