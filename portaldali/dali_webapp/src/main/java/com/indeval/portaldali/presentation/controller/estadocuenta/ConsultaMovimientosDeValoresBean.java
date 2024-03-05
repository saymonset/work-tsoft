/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Jan 03, 2008
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
import com.indeval.portaldali.middleware.dto.DefinicionDetalleMovimientoDTO;
import com.indeval.portaldali.middleware.dto.DetalleEstadoCuentaPosicionPorBovedaDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaPosicionPorEmisionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.util.DefinicionDetallesMovimientosHelper;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.persistence.dao.common.TipoInstruccionDaliDAO;
import com.indeval.portaldali.presentation.common.constants.CamposPantallaConstantes;
import com.indeval.portaldali.presentation.common.constants.ReportesConstants;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.BackingBeanBase;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl.ConsultaMovimientosValor;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Backing bean asociado a la pantalla de consulta de movimientos de valores.
 * Esta clase se encarga de la invocación a los servicios de negocio
 * relacionados con esta consulta de movimientos.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 * 
 */
public class ConsultaMovimientosDeValoresBean extends BackingBeanBase {
	/**
	 * Objeto que representa el esquema de consulta
	 */
	private ConsultaMovimientosValor consultaMovimientos;
	/**
	 * Helper que contiene la definición de la operación que corresonde al tipo
	 * de instrucción de un registro contable.
	 */
	private DefinicionDetallesMovimientosHelper definicionDetallesMovimientosHelper = null;
	/**
	 * Indica si la consulta principal ya fue ejecutada
	 */
	private boolean consultaEjecutada = false;

	/** Contiene el valor seleccionado de la emisora en el criterio de emisión */
	private String emisoraSeleccionada = null;
	
	
	private TipoValorDTO instrumentoSeleccionado = new TipoValorDTO();
	
	/**
	 * Nombre de la institucion contraparte seleccionada
	 */
	private String institucionContraparteSeleccionada = null;
	/**
	 * Resultados temporales de la consulta
	 */
	List<EstadoCuentaPosicionPorEmisionDTO> resultados = null;

	/** Los resultados temporales de la consulta para el reporte en formato XLS */
	List<RegistroContablePosicionNombradaDTO> resultadosNombradasReporteXls = new ArrayList<RegistroContablePosicionNombradaDTO>();

	/** Los resultados temporales de la consulta para el reporte en formato XLS */
	List<RegistroContablePosicionControladaDTO> resultadosControladasReporteXls = new ArrayList<RegistroContablePosicionControladaDTO>();

	/** Facade para accesar a los catálogos del DALI */
	private ConsultaCatalogosFacade consultaCatalogos;

	/** Contiene el papel mercado por el cual se filtra el mercado */
	private String papelMercado = DaliConstants.PAPEL_MERCADO_TODOS;

	/** DAO para la consulta del catálogo de tipos de instrucción */
	private TipoInstruccionDaliDAO tipoInstruccionDaliDAO;

	/**
	 * Institucion a buscar cuando el usuario es INDEVAL
	 */
	InstitucionDTO institucionConsultadaContraparte = null;

	/**
	 * Indica si se debe de restaurar la pagicación de los resultados
	 * principales de la página
	 */
	private boolean restaurarPaginacionResultados = true;
	/**
	 * 
	 */
	private Long totalResultados = null;

	/**
	 * Almacena la clave y folio de la institucion
	 */
	private String folioClaveInstitucion;

	/** El nombre de la institución a consultar */
	private String nombreInstitucion;

	/**
	 * Institucion a buscar cuando el usuario es INDEVAL
	 */
	InstitucionDTO institucionConsultada = null;

	/**
	 * Constructor predeterminado para realizar tareas de inicialzación
	 */

	/**
	 * Inicializa los criterios de consulta
	 * 
	 * @param e
	 *            ActionEvent generado durante la solicitud
	 */
	public void limpiar(ActionEvent e) {
		inicializarCriterios();
		folioClaveInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
		nombreInstitucion = folioClaveInstitucion;
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
	 * Asigna el participante predeterminado en la consulta.
	 * 
	 * @return <code>null</code>, este método no requiere de un valor de retorno.
	 */
	public String getInitParticipante() {
		
		folioClaveInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
		nombreInstitucion = folioClaveInstitucion;
		consultaEjecutada = false;
		inicializarCriterios();
		
		return null;
	}
	
	/**
	 * Establece el valor del atributo nombreInstitucion
	 * 
	 * @param nombreInstitucion
	 *            el valor del atributo nombreInstitucion a establecer
	 */
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
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
			consultaMovimientos.getCriterioCuenta().setIdInstitucion(getInstitucionActual().getId());
			nombreInstitucion = folioClaveInstitucion;
			
			// Reiniciar el criterio de naturaleza para el usuario participante
			// para dejar por default la naturaleza : Pasivo
			if (isUsuarioIndeval()) {
				TipoNaturalezaDTO pasivo = new TipoNaturalezaDTO();
				pasivo.setId(TipoNaturalezaDTO.PASIVO);
				consultaMovimientos.getCriterioCuenta().getCriterioTipoTenencia().getCriterioNaturaleza().setOpcionSeleccionada(pasivo);
				
				if(StringUtils.isBlank(nombreInstitucion) && StringUtils.isBlank(folioClaveInstitucion)) {
					nombreInstitucion = DaliConstants.DESCRIPCION_TODOS;
				}
			} else {
				nombreInstitucion = folioClaveInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
			}
		}

		if (ctx.getRenderResponse()) {
			if (isConsultaEjecutada()) {

				consultaMovimientos.setEmisionSeleccionada(null);
				consultaMovimientos.setBovedaSeleccionada(null);
				consultaMovimientos.setCuentaSeleccionada(null);
				consultaMovimientos.recibirNotificacionResultados(null);
				consultaMovimientos.getCriterioEmision().reestablecerEstadoPaginacion();
			}
			consultaMovimientos.getOpcionSeleccionada();

		}

		if (!ctx.getRenderKit().getResponseStateManager().isPostback(ctx)) {
			// si es la primera vez que se visita la página
			inicializarCriterios();
		}

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
				consultaMovimientos.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().getOpcionSeleccionada(), String.valueOf(prefijo));
	}

	/**
	 * Inicializa los criterios de búsqueda
	 */
	private void inicializarCriterios() {
		setConsultaEjecutada(false);
		consultaMovimientos.setCriterioFechaFinal(new Date());
		consultaMovimientos.setCriterioFechaInicial(new Date());
		consultaMovimientos.setBusquedaFechaAplicacion(false);
		consultaMovimientos.setBusquedaFechaConcertacion(false);
		consultaMovimientos.getCriterioCuenta().getCriterioTipoTenencia().getCriterioNaturaleza().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioCuenta().getCriterioTipoTenencia().getCriterioTipoCuenta().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioCuenta().getCriterioTipoTenencia().setOpcionSeleccionada(null);

		consultaMovimientos.getCriterioCuenta().setOpcionSeleccionada(null);

		consultaMovimientos.getCriterioCuentaContraparte().getCriterioTipoTenencia().getCriterioNaturaleza().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioCuentaContraparte().getCriterioTipoTenencia().getCriterioTipoCuenta().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioCuentaContraparte().getCriterioTipoTenencia().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioCuentaContraparte().setIdInstitucion(-1);

		consultaMovimientos.getCriterioCuentaContraparte().setOpcionSeleccionada(null);

		consultaMovimientos.getCriterioBoveda().setOpcionSeleccionada(null);

		consultaMovimientos.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioEmision().getCriterioTipoValor().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioEmision().getCriterioEmisora().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioEmision().getCriterioSerie().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioEmision().setIsin(null);
		emisoraSeleccionada = null;
		instrumentoSeleccionado = new TipoValorDTO();
		institucionContraparteSeleccionada = null;
		consultaMovimientos.getCriterioEmision().setOpcionSeleccionada(null);

		consultaMovimientos.getCriterioTipoInstruccion().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioTipoOperacion().setOpcionSeleccionada(null);
		consultaMovimientos.setCriterioFolioInstruccion(null);

		consultaMovimientos.setCriterioRolContraparte("-1");
		consultaMovimientos.setCriterioRolParticipante("-1");

		consultaMovimientos.getCriterioBoveda().reestablecerEstadoPaginacion();

		consultaMovimientos.getCriterioEmision().reestablecerEstadoPaginacion();

		consultaMovimientos.getCriterioCuenta().reestablecerEstadoPaginacion();
		consultaMovimientos.reestablecerEstadoPaginacion();
		if (consultaMovimientos.getCriterioCuenta().isTodos()) {
			consultaMovimientos.getCriterioCuenta().toggleTodos();
		}
		if (consultaMovimientos.getCriterioEmision().isTodos()) {
			consultaMovimientos.getCriterioEmision().toggleTodos();
		}
		if (!consultaMovimientos.getCriterioBoveda().isTodos()) {
			consultaMovimientos.getCriterioBoveda().toggleTodos();
		}

	}

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de tipos
	 * de instrucción.
	 * 
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con los tipos de instrucciones encontradas
	 */
	public List<TipoInstruccionDTO> buscarTipoInstruccionPorPrefijo(Object prefijo) {
		return tipoInstruccionDaliDAO.buscarTiposDeInstruccionPorIdsValores(prefijo.toString());
	}

	/**
	 * Prepara los valores para la ejecución de la consulta de posiciones
	 * 
	 * @param e
	 *            ActionEvent generado durante la petición
	 */
	public void buscarMovimientos() {
		boolean fechasValidas = 
			validarFechaObligatoria(consultaMovimientos.getCriterioFechaInicial(), false, CamposPantallaConstantes.campoFechaInicial) &&
			validarFechaObligatoria(consultaMovimientos.getCriterioFechaFinal(), false, CamposPantallaConstantes.campoFechaFinal);
		fechasValidas = fechasValidas && validarFechaFinalVsFechaInicial(
			consultaMovimientos.getCriterioFechaInicial(), consultaMovimientos.getCriterioFechaFinal());
		if(fechasValidas) {
			nombreInstitucion = folioClaveInstitucion;
			consultaMovimientos.setCuentaSeleccionada(null);
			consultaMovimientos.setEmisionSeleccionada(null);
			consultaMovimientos.setBovedaSeleccionada(null);
			if (consultaMovimientos.getCriterioCuenta().isTodos()) {
				consultaMovimientos.getCriterioCuenta().toggleTodos();
			}
			if (consultaMovimientos.getCriterioEmision().isTodos()) {
				consultaMovimientos.getCriterioEmision().toggleTodos();
			}
			if (!consultaMovimientos.getCriterioBoveda().isTodos()) {
				consultaMovimientos.getCriterioBoveda().toggleTodos();
			}
			consultaMovimientos.getCriterioCuenta().setResultadosPorPagina(1);
			consultaMovimientos.getCriterioCuenta().setDebePaginar(true);
			
			consultaMovimientos.getCriterioEmision().getEstadoPaginacion().setNumeroPagina(1);
			consultaMovimientos.getCriterioEmision().getEstadoPaginacion().setTotalResultados(0);
			
			consultaMovimientos.getCriterioCuenta().getEstadoPaginacion().setNumeroPagina(1);
			consultaMovimientos.getCriterioCuenta().getEstadoPaginacion().setTotalResultados(0);
			
			consultaMovimientos.recibirNotificacionResultados(null);
			
			if (folioClaveInstitucion != null && folioClaveInstitucion.length() == 2) {
				consultaMovimientos.getCuentaSeleccionada().getInstitucion().setClaveTipoInstitucion(folioClaveInstitucion);
			}
			
			if (institucionContraparteSeleccionada != null && institucionContraparteSeleccionada.length() == 2) {
				consultaMovimientos.getCriterioCuentaContraparte().getOpcionSeleccionada().getInstitucion().setClaveTipoInstitucion(
						institucionContraparteSeleccionada);
			}
			
			consultaMovimientos.getCriterioEmision().reestablecerEstadoPaginacion();
			consultaMovimientos.getCriterioCuenta().reestablecerEstadoPaginacion();
			consultaMovimientos.getCriterioBoveda().reestablecerEstadoPaginacion();
			
			consultaMovimientos.reestablecerEstadoPaginacion();
			consultaMovimientos.setColumnaOrdenada(null);
			consultaMovimientos.setOrdenAscendente(true);
			
			setConsultaEjecutada(true);
			
			totalResultados = null;
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
			for (int i = 1; i <= consultaMovimientos.getCriterioEmision().getEstadoPaginacion().getTotalPaginas(); i++) {
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
		if (papelMercado.equals(DaliConstants.PAPEL_MERCADO_TODOS)) {
			mercadosTodos.setId(DaliConstants.VALOR_COMBO_TODOS);
		} else {
			if (papelMercado.equals(DaliConstants.PAPEL_MERCADO_DINERO)) {
				mercadosTodos.setId(DaliConstants.ID_MERCADO_DINERO);
			} else {
				mercadosTodos.setId(DaliConstants.ID_MERCADO_CAPITALES);
			}
		}
		consultaMovimientos.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().setOpcionSeleccionada(mercadosTodos);
		consultaMovimientos.getCriterioEmision().getCriterioTipoValor().setOpcionSeleccionada(null);

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
	 * Esta función es invocada durane el evento de cambio en el control de
	 * naturaleza de la cuenta
	 * 
	 * @param e
	 *            Evento generado durante la petición
	 */
	public void cambioSelectNaturaleza(ActionEvent e) {
		consultaMovimientos.getCriterioCuenta().getCriterioTipoTenencia().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioCuenta().setOpcionSeleccionada(null);
	}

	public void cambioSelectTipoCuenta(ActionEvent e) {
		consultaMovimientos.getCriterioCuenta().getCriterioTipoTenencia().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioCuenta().setOpcionSeleccionada(null);
	}

	public void cambioSelectTipoTenencia(ActionEvent e) {

		consultaMovimientos.getCriterioCuenta().setOpcionSeleccionada(null);
	}

	public void cambioSelectTipoTenenciaContraparte(ActionEvent e) {

		consultaMovimientos.getCriterioCuentaContraparte().setOpcionSeleccionada(null);
	}

	public void cambioSelectMercado(ActionEvent e) {

		consultaMovimientos.getCriterioEmision().getCriterioTipoValor().setOpcionSeleccionada(null);
		consultaMovimientos.getCriterioEmision().getCriterioSerie().setOpcionSeleccionada(null);
	}

	public void cambioSelectTipoValor(ActionEvent e) {

		consultaMovimientos.getCriterioEmision().getCriterioSerie().setOpcionSeleccionada(null);
	}

	public void cambioCuenta(ActionEvent e) {

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
	 * Inicializa la consulta de movimientos de valores
	 * 
	 * @return null
	 */
	public String getInitConsulta() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (ctx.getRenderResponse() && consultaEjecutada) {

			totalResultados = consultaMovimientos.getConsultaMovimientosValorService().obtenerProyeccionDeMovimientosDeValor(
					consultaMovimientos.getCriterioConsulta(), consultaMovimientos.isDebeDejarBitacora());
			
			consultaMovimientos.setDebeDejarBitacora(false);

			resultados = consultaMovimientos.getPaginaDeResultados();

			if (TipoCuentaConstants.TIPO_NOMBRADA.equals(consultaMovimientos.getCriterioCuenta().getOpcionSeleccionada().getTipoTenencia().getTipoCuenta()
					.getId())) {
				asignarPantallaDetalle(resultados);
			}

			//Le indica al garbage collector que puede pasar.
			pasarGarbageCollector();
		}
		else {
			resultados = new ArrayList<EstadoCuentaPosicionPorEmisionDTO>();
		}
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
				consultaMovimientos.getCriterioEmision().getClass().getMethod(navegacion).invoke(consultaMovimientos.getCriterioEmision());
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

			consultaMovimientos.reestablecerEstadoPaginacion();
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

		String prefijoAjustado = "";
		if (value != null) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
		}
		List<EmisoraDTO> emisoras = new ArrayList<EmisoraDTO>();
		if (value != null) {
			emisoras = consultaMovimientos.getCriterioEmision().getCriterioEmisora().buscarEmisorasPorPrefijo(prefijoAjustado);
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

		for (BovedaDTO boveda : consultaMovimientos.getCriterioBoveda().getResultados()) {
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

		for (TipoNaturalezaDTO nat : consultaMovimientos.getCriterioCuenta().getCriterioTipoTenencia().getCriterioNaturaleza().getResultados()) {
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

		for (TipoCuentaDTO nat : consultaMovimientos.getCriterioCuenta().getCriterioTipoTenencia().getCriterioTipoCuenta().getResultados()) {
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

		for (TipoTenenciaDTO nat : consultaMovimientos.getCriterioCuenta().getCriterioTipoTenencia().getResultados()) {
			opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Tipo Tenencia de la Contraparte
	 * 
	 * @return Lista con las opciones válidas del criterio de tipo Tenencia
	 */
	public List<SelectItem> getOpcionesComboTipoTenenciaContraparte() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		TipoTenenciaDTO tenenciatodos = new TipoTenenciaDTO();
		tenenciatodos.setIdTipoCuenta(-1);
		tenenciatodos.setDescripcion("TODAS");

		opcionesCombo.add(new SelectItem(tenenciatodos, tenenciatodos.getDescripcion()));

		for (TipoTenenciaDTO nat : consultaMovimientos.getCriterioCuentaContraparte().getCriterioTipoTenencia().getResultados()) {
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

		for (CuentaDTO nat : consultaMovimientos.getCriterioCuenta().getResultados()) {
			opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Cuenta de la contraparte
	 * 
	 * @return Lista con las opciones válidas del criterio de Cuenta de
	 *         contraparte
	 */
	public List<SelectItem> getOpcionesComboCuentaContraparte() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		CuentaDTO cuentaTodos = new CuentaDTO();
		cuentaTodos.setNumeroCuenta("-1");
		cuentaTodos.setDescripcion("TODAS");

		opcionesCombo.add(new SelectItem(cuentaTodos, cuentaTodos.getDescripcion()));

		for (CuentaDTO nat : consultaMovimientos.getCriterioCuentaContraparte().getResultados()) {
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
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio tipo de instruccion
	 * 
	 * @return Lista con las opciones válidas del criterio de tipo de
	 *         instrucción
	 */
	public List<SelectItem> getOpcionesComboTipoInstruccion() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		TipoInstruccionDTO todosDto = new TipoInstruccionDTO();
		todosDto.setIdTipoInstruccion(-1L);
		todosDto.setNombreCorto("TODOS");

		opcionesCombo.add(new SelectItem(todosDto, todosDto.getNombreCorto()));
		String descripcion = null;
		for (TipoInstruccionDTO dto : consultaMovimientos.getCriterioTipoInstruccion().getResultados()) {
			if (dto.getDescripcion() != null && dto.getDescripcion().length() >= 20) {
				descripcion = dto.getDescripcion().substring(0, 20) + "...";
			} else {
				descripcion = dto.getDescripcion();
			}
			opcionesCombo.add(new SelectItem(dto, dto.getNombreCorto() + " - " + descripcion));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio tipo de operación
	 * 
	 * @return Lista con las opciones válidas del criterio de tipo de operación
	 */
	public List<SelectItem> getOpcionesComboTipoOperacion() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		TipoOperacionDTO todosDto = new TipoOperacionDTO();
		todosDto.setId(-1L);
		todosDto.setClaveTipoOperacion("TODOS");

		opcionesCombo.add(new SelectItem(todosDto, todosDto.getClaveTipoOperacion()));

		for (TipoOperacionDTO dto : consultaMovimientos.getCriterioTipoOperacion().getResultados()) {
			opcionesCombo.add(new SelectItem(dto, dto.getClaveTipoOperacion()));
		}

		return opcionesCombo;
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
			emisora = consultaMovimientos.getCriterioEmision().getCriterioEmisora().getConsultaEmisoraService().buscarEmisoraPorDescripcion(
				emisoraSeleccionada.toUpperCase());
			if (emisora == null) {
				emisora = new EmisoraDTO();
				// Valor para indicar que va a ir a buscar una emisora inexistente
				emisora.setId(DaliConstants.VALOR_COMBO_NINGUNO);
				emisora.setDescripcion(emisoraSeleccionada);
			}
		}
		consultaMovimientos.getCriterioEmision().getCriterioEmisora().setOpcionSeleccionada(emisora);
		
		
		/*EmisoraDTO emisora = consultaMovimientos.getCriterioEmision().getCriterioEmisora().getConsultaEmisoraService().buscarEmisoraPorDescripcion(
				emisoraSeleccionada.toUpperCase());
		if (emisora != null || (emisoraSeleccionada != null && emisoraSeleccionada.length() == 0)) {
			consultaMovimientos.getCriterioEmision().getCriterioEmisora().setOpcionSeleccionada(emisora);
		}*/
	}
	
	
	public TipoValorDTO getInstrumentoSeleccionado() {
		return instrumentoSeleccionado;
	}

	public void setInstrumentoSeleccionado(TipoValorDTO instrumentoSeleccionado) {
		this.instrumentoSeleccionado = instrumentoSeleccionado;
		MercadoDTO mercadoSeleccionado = consultaMovimientos.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().getOpcionSeleccionada();
		if(instrumentoSeleccionado != null) {
			consultaMovimientos.getCriterioEmision().getCriterioTipoValor().setOpcionSeleccionada(instrumentoSeleccionado);
		} 
		consultaMovimientos.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().setOpcionSeleccionada(mercadoSeleccionado);
		consultaMovimientos.getCriterioEmision().getCriterioTipoValor().getOpcionSeleccionada().setMercado(mercadoSeleccionado);
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
	 * Obtiene el campo consultaMovimientos
	 * 
	 * @return consultaMovimientos
	 */
	public ConsultaMovimientosValor getConsultaMovimientos() {
		return consultaMovimientos;
	}

	/**
	 * Asigna el valor del campo consultaMovimientos
	 * 
	 * @param consultaMovimientos
	 *            el valor de consultaMovimientos a asignar
	 */
	public void setConsultaMovimientos(ConsultaMovimientosValor consultaMovimientos) {
		this.consultaMovimientos = consultaMovimientos;
	}

	/**
	 * Obtiene el campo institucionContraparteSeleccionada
	 * 
	 * @return institucionContraparteSeleccionada
	 */
	public String getInstitucionContraparteSeleccionada() {
		return institucionContraparteSeleccionada;
	}

	/**
	 * Asigna el valor del campo institucionContraparteSeleccionada
	 * 
	 * @param institucionContraparteSeleccionada
	 *            el valor de institucionContraparteSeleccionada a asignar
	 */
	public void setInstitucionContraparteSeleccionada(String institucionContraparteSeleccionada) {
		this.institucionContraparteSeleccionada = institucionContraparteSeleccionada;

		if (institucionContraparteSeleccionada.length() >= 5) {
			this.institucionContraparteSeleccionada = institucionContraparteSeleccionada.substring(0, 5);

			institucionConsultadaContraparte = getConsultaInstitucionService().buscarInstitucionPorClaveYFolio(this.institucionContraparteSeleccionada);
			if (institucionConsultadaContraparte != null) {
				consultaMovimientos.getCriterioCuentaContraparte().setIdInstitucion(institucionConsultadaContraparte.getId());

			}
		} else {
			consultaMovimientos.getCriterioCuentaContraparte().setIdInstitucion(-1);
			if (institucionContraparteSeleccionada.length() == 2) {
				consultaMovimientos.getCriterioCuentaContraparte().getOpcionSeleccionada().getInstitucion().setClaveTipoInstitucion(folioClaveInstitucion);
			}
		}
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
	public List<CuentaDTO> buscarCuentasContrapartePorPrefijo(Object value) {
		List<CuentaDTO> cuentas = new ArrayList<CuentaDTO>();

		String prefijoAjustado = "";
		if (value != null) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
		}

		if (value != null) {

			CuentaDTO criterio = new CuentaDTO();
			InstitucionDTO institucionDTO = new InstitucionDTO();
			institucionDTO.setId(consultaMovimientos.getCriterioCuentaContraparte().getIdInstitucion());

			criterio.setNumeroCuenta(institucionContraparteSeleccionada + prefijoAjustado);
			criterio.setTipoTenencia(consultaMovimientos.getCriterioCuentaContraparte().getCriterioTipoTenencia().getOpcionSeleccionada());
			criterio.setInstitucion(institucionDTO);
			if (institucionContraparteSeleccionada.length() > 2) {
				cuentas = consultaMovimientos.getCriterioCuentaContraparte().getConsultaCuentaService().buscarCuentasPorFragmentoNumeroCuenta(criterio);
			}
		}
		return cuentas;
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

		if (value != null && consultaMovimientos.getCriterioCuenta().getIdInstitucion() != -1) {

			CuentaDTO criterio = new CuentaDTO();

			if (!isUsuarioIndeval()) {
				criterio.setNumeroCuenta(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion() + prefijoAjustado);
				criterio.setInstitucion(getInstitucionActual());
			} else {
				criterio.setNumeroCuenta(getFolioClaveInstitucion() + prefijoAjustado);
				criterio.setInstitucion(institucionConsultada);
			}

			criterio.setTipoTenencia(consultaMovimientos.getCriterioCuenta().getCriterioTipoTenencia().getOpcionSeleccionada());

			if (folioClaveInstitucion.length() > 2) {
				cuentas = consultaMovimientos.getCriterioCuenta().getConsultaCuentaService().buscarCuentasPorFragmentoNumeroCuenta(criterio);
			}

			for (CuentaDTO cuentaDTO : cuentas) {
				cuentaDTO.setDescripcion(cuentaDTO.getDescripcion().substring(5));
			}

		}

		return cuentas;
	}

	/**
	 * Obtiene el campo cuentaContraparteSeleccionada
	 * 
	 * @return cuentaContraparteSeleccionada
	 */
	public String getCuentaContraparteSeleccionada() {
		return consultaMovimientos.getCriterioCuentaContraparte().getOpcionSeleccionada().getDescripcion();
	}

	/**
	 * Asigna el valor del campo cuentaContraparteSeleccionada
	 * 
	 * @param cuentaContraparteSeleccionada
	 *            el valor de cuentaContraparteSeleccionada a asignar
	 */
	public void setCuentaContraparteSeleccionada(String cuentaContraparteSeleccionada) {
		CuentaDTO cuenta = new CuentaDTO();
		cuenta.setNumeroCuenta(cuentaContraparteSeleccionada);
		consultaMovimientos.getCriterioCuentaContraparte().setOpcionSeleccionada(cuenta);
		cuentaContraparteSeleccionada = ("TODA".equals(cuentaContraparteSeleccionada)) ? "TODAS" : cuentaContraparteSeleccionada ;
		if ("-1".equals(consultaMovimientos.getCriterioCuentaContraparte().getOpcionSeleccionada().getNumeroCuenta()) && cuentaContraparteSeleccionada != null
				&& cuentaContraparteSeleccionada.length() > 0 && !"TODAS".equals(cuentaContraparteSeleccionada)) {
			consultaMovimientos.getCriterioCuentaContraparte().getOpcionSeleccionada().setNumeroCuenta(cuentaContraparteSeleccionada);
			consultaMovimientos.getCriterioCuentaContraparte().getOpcionSeleccionada().setDescripcion(cuentaContraparteSeleccionada);
		}

	}

	/**
	 * Obtiene el campo cuentaContraparteSeleccionada
	 * 
	 * @return cuentaContraparteSeleccionada
	 */
	public String getCuentaSeleccionada() {
		String cuenta = null;
		List<CuentaDTO> resultados = null;
		if (consultaMovimientos.getCriterioCuenta().getCriterioTipoTenencia().getOpcionSeleccionada().getIdTipoCuenta() > 0
				&& getInstitucionActual().getId() > 0) {
			resultados = consultaMovimientos.getCriterioCuenta().getResultados();
		}

		// Si existe solo una cuenta, colocarla como predeterminada
		if (resultados != null && resultados.size() == 1) {
			consultaMovimientos.getCriterioCuenta().setOpcionSeleccionada(resultados.get(0));
		}
		cuenta = consultaMovimientos.getCriterioCuenta().getOpcionSeleccionada().getNumeroCuenta().equals("-1") ? "TODAS" : consultaMovimientos
				.getCriterioCuenta().getOpcionSeleccionada().getNumeroCuenta();
		// Si existe institución activa se deben de omitir los caracteres del
		// folio y el tipo de institución
		if (getInstitucionActual().getId() > 0) {
			cuenta = cuenta.replace(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion(), StringUtils.EMPTY);
		}
		return cuenta;
	}

	/**
	 * Asigna el valor del campo cuentaContraparteSeleccionada
	 * 
	 * @param cuentaContraparteSeleccionada
	 *            el valor de cuentaContraparteSeleccionada a asignar
	 */
	public void setCuentaSeleccionada(String cuentaSeleccionada) {
		consultaMovimientos.getCriterioCuenta().setOpcionSeleccionada(null);
		if (cuentaSeleccionada != null && cuentaSeleccionada.length() > 0) {
			cuentaSeleccionada = ("TODA".equals(cuentaSeleccionada)) ? "TODAS" : cuentaSeleccionada ;
			if (!"TODAS".equals(cuentaSeleccionada)) {
				if (!isUsuarioIndeval()) {
					cuentaSeleccionada = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion() + cuentaSeleccionada;
				} else {
					cuentaSeleccionada = getFolioClaveInstitucion() + cuentaSeleccionada;
				}
				CuentaDTO cuenta = new CuentaDTO();
				cuenta.setNumeroCuenta(cuentaSeleccionada);
				consultaMovimientos.getCriterioCuenta().getOpcionSeleccionada().setNumeroCuenta(cuentaSeleccionada);
				consultaMovimientos.getCriterioCuenta().getOpcionSeleccionada().setDescripcion(cuentaSeleccionada);
			}

		}

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

	/**
	 * Genera un mapa con los parámetros de la consulta de posiciones.
	 * 
	 * @return Map mapa con los parámetros de la consulta
	 */
	@Override
	protected Map<String, Object> llenarParametros() {
		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put(ReportesConstants.CUENTA_PARTICIPANTE_PARAMETER, consultaMovimientos.getCuentaSeleccionada().getDescripcion());
		parametros.put(ReportesConstants.NATURALEZA_PARTICIPANTE_PARAMETER, consultaMovimientos.getCuentaSeleccionada().getTipoTenencia().getTipoNaturaleza()
				.getDescripcion());
		parametros.put(ReportesConstants.TIPO_CUENTA_PARTICIPANTE_PARAMETER, consultaMovimientos.getCuentaSeleccionada().getTipoTenencia().getTipoCuenta()
				.getDescripcion());
		parametros.put(ReportesConstants.TIPO_TENENCIA_PARTICIPANTE_PARAMETER, consultaMovimientos.getCuentaSeleccionada().getTipoTenencia().getDescripcion());
		parametros.put(ReportesConstants.ROL_PARTICIPANTE_PARAMETER, consultaMovimientos.getDescripcionRolParticipante());

		parametros.put(ReportesConstants.CUENTA_CONTRAPARTE_PARAMETER, consultaMovimientos.getCriterioCuentaContraparte().getOpcionSeleccionada()
				.getDescripcion());
		parametros.put(ReportesConstants.NATURALEZA_CONTRAPARTE_PARAMETER, consultaMovimientos.getCriterioCuentaContraparte().getOpcionSeleccionada()
				.getTipoTenencia().getTipoNaturaleza().getDescripcion());
		parametros.put(ReportesConstants.TIPO_CUENTA_CONTRAPARTE_PARAMETER, consultaMovimientos.getCriterioCuentaContraparte().getOpcionSeleccionada()
				.getTipoTenencia().getTipoCuenta().getDescripcion());
		parametros.put(ReportesConstants.TIPO_TENENCIA_CONTRAPARTE_PARAMETER, consultaMovimientos.getCriterioCuentaContraparte().getOpcionSeleccionada()
				.getTipoTenencia().getDescripcion());
		parametros.put(ReportesConstants.ROL_CONTRAPARTE_PARAMETER, consultaMovimientos.getDescripcionRolContraparte());

		parametros.put(ReportesConstants.TIPO_MERCADO_PARAMETER, consultaMovimientos.getCriterioEmision().getOpcionSeleccionada().getTipoValor().getMercado()
				.getDescripcion());
		parametros.put(ReportesConstants.ISIN_PARAMETER, consultaMovimientos.getCriterioEmision().getOpcionSeleccionada().getIsin());
		parametros.put(ReportesConstants.TV_PARAMETER, consultaMovimientos.getCriterioEmision().getOpcionSeleccionada().getTipoValor().getClaveTipoValor());
		parametros.put(ReportesConstants.EMISORA_PARAMETER, consultaMovimientos.getCriterioEmision().getOpcionSeleccionada().getEmisora().getDescripcion());
		parametros.put(ReportesConstants.SERIE_PARAMETER, consultaMovimientos.getCriterioEmision().getOpcionSeleccionada().getSerie().getDescripcion());
		parametros.put(ReportesConstants.CUPON_PARAMETER, consultaMovimientos.getCriterioEmision().getOpcionSeleccionada().getCupon());

		parametros.put(ReportesConstants.BOVEDA_PARAMETER, consultaMovimientos.getCriterioBoveda().getOpcionSeleccionada().getDescripcion());
		parametros.put(ReportesConstants.FECHA_INICIAL_PARAMETER, consultaMovimientos.getCriterioFechaInicial());
		parametros.put(ReportesConstants.FECHA_FINAL_PARAMETER, consultaMovimientos.getCriterioFechaFinal());

		parametros.put(ReportesConstants.TIPO_INSTRUCCION_PARAMETER, consultaMovimientos.getCriterioTipoInstruccion().getOpcionSeleccionada().getNombreCorto());
		parametros.put(ReportesConstants.TIPO_OPERACION_PARAMETER, consultaMovimientos.getCriterioTipoOperacion().getOpcionSeleccionada()
				.getClaveTipoOperacion());
		parametros.put(ReportesConstants.FOLIO_PARAMETER, consultaMovimientos.getCriterioFolioInstruccion() != null ? consultaMovimientos
				.getCriterioFolioInstruccion() : "");

		return parametros;
	}

	/**
	 * método para generar un reporte con formato XLS o PDF
	 * 
	 * @return cadena de para ejecutar el action para genera el reporte
	 */
	public void generarReportes(ActionEvent e) {
		reiniciarEstadoPeticion();
		String reporte = (String) e.getComponent().getAttributes().get("reporte");
		if ("consultaMovimientosValorXls".equals(reporte)||"consultaMovimientosValorTxt".equals(reporte)) {
			if (TipoCuentaConstants.TIPO_CONTROLADA.equals(consultaMovimientos.getCuentaSeleccionada().getTipoTenencia().getTipoCuenta().getId())) {
				resultadosControladasReporteXls = consultaMovimientos.getResultadosControladasSinAgrupar();
				totalResultados = Long.valueOf(resultadosControladasReporteXls.size());
			} else {
				resultadosNombradasReporteXls = consultaMovimientos.getResultadosNombradasSinAgrupar();
				totalResultados = Long.valueOf(resultadosNombradasReporteXls.size());
			}
		}
		else {
			resultados = consultaMovimientos.getResultados();
			totalResultados = 0L;
			for(EstadoCuentaPosicionPorEmisionDTO emision : resultados) {
				for(DetalleEstadoCuentaPosicionPorBovedaDTO boveda : emision.getDetallesBoveda()){
					if( consultaMovimientos.getCriterioCuenta().getCriterioTipoTenencia().getCriterioTipoCuenta().
							getOpcionSeleccionada().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA) ){
						totalResultados += boveda.getRegistrosContablesControladas().size();
					} else {
						totalResultados += boveda.getRegistrosContablesNombradas().size();
					}
				}
			}
		}

		//Le indica al garbage collector que puede pasar.
		pasarGarbageCollector();
	}

	@Override
	protected Collection<? extends Object> ejecutarConsultaReporte() {
		Collection<? extends Object> regreso = (Collection<? extends Object>) consultaMovimientos.getResultados();
		if(logger.isInfoEnabled() && regreso != null)
		{
			logger.info(this.getClass().getName() + "[Registros:ejecutarConsultaReporte]" + regreso.size());
		}
		return regreso;
	}

	@Override
	protected String getNombreReporte() {
		return ReportesConstants.REPORTE_CONSULTA_MOVIMIENTOS_VALORES;
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
	 *            el valor del atributo totalResultados a establecer.
	 */
	public void setTotalResultados(Long totalResultados) {
		this.totalResultados = totalResultados;
	}

	/**
	 * Obtiene el valor del atributo folioClaveInstitucion
	 * 
	 * @return el valor del atributo folioClaveInstitucion
	 */
	public String getFolioClaveInstitucion() {
		return folioClaveInstitucion;
	}

	/**
	 * Obtiene el valor del atributo resultadosNombradasReporteXls
	 * 
	 * @return el valor del atributo resultadosNombradasReporteXls
	 */
	public List<RegistroContablePosicionNombradaDTO> getResultadosNombradasReporteXls() {
		if(logger.isInfoEnabled() && resultadosNombradasReporteXls != null)
		{
			logger.info(this.getClass().getName() + "[Registros nombradas]" + resultadosNombradasReporteXls.size());
		}
		return resultadosNombradasReporteXls;
	}

	/**
	 * Establece el valor del atributo resultadosNombradasReporteXls
	 * 
	 * @param resultadosNombradasReporteXls
	 *            el valor del atributo resultadosNombradasReporteXls a
	 *            establecer.
	 */
	public void setResultadosNombradasReporteXls(List<RegistroContablePosicionNombradaDTO> resultadosNombradasReporteXls) {
		this.resultadosNombradasReporteXls = resultadosNombradasReporteXls;
	}

	/**
	 * Obtiene el valor del atributo resultadosControladasReporteXls
	 * 
	 * @return el valor del atributo resultadosControladasReporteXls
	 */
	public List<RegistroContablePosicionControladaDTO> getResultadosControladasReporteXls() {
		if(logger.isInfoEnabled() && resultadosNombradasReporteXls != null)
		{
			logger.info(this.getClass().getName() + "[Registros controladas]" + resultadosNombradasReporteXls.size());
		}
		return resultadosControladasReporteXls;
	}

	/**
	 * Establece el valor del atributo resultadosControladasReporteXls
	 * 
	 * @param resultadosControladasReporteXls
	 *            el valor del atributo resultadosControladasReporteXls a
	 *            establecer.
	 */
	public void setResultadosControladasReporteXls(List<RegistroContablePosicionControladaDTO> resultadosControladasReporteXls) {
		this.resultadosControladasReporteXls = resultadosControladasReporteXls;
	}

	/**
	 * Establece el valor del atributo folioClaveInstitucion
	 * 
	 * @param folioClaveInstitucion
	 *            el valor del atributo folioClaveInstitucion a establecer.
	 */
	public void setFolioClaveInstitucion(String folioClaveInstitucion) {
		this.folioClaveInstitucion = folioClaveInstitucion;
		this.nombreInstitucion = folioClaveInstitucion;
		if (folioClaveInstitucion.length() >= 5) {
			this.folioClaveInstitucion = folioClaveInstitucion.substring(0, 5);

			institucionConsultada = getConsultaInstitucionService().buscarInstitucionPorClaveYFolio(this.folioClaveInstitucion);
			if (institucionConsultada != null) {
				consultaMovimientos.getCriterioCuenta().setIdInstitucion(institucionConsultada.getId());

			}
		} else {
			consultaMovimientos.getCriterioCuenta().setIdInstitucion(-1);
			if (folioClaveInstitucion.length() == 2) {
				consultaMovimientos.getCriterioCuenta().getOpcionSeleccionada().getInstitucion().setClaveTipoInstitucion(folioClaveInstitucion);
			}
		}
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
	 *            el valor del atributo institucionConsultada a establecer.
	 */
	public void setInstitucionConsultada(InstitucionDTO institucionConsultada) {
		this.institucionConsultada = institucionConsultada;
	}

	/**
	 * Obtiene el valor del atributo tipoInstruccionDaliDAO
	 * 
	 * @return el valor del atributo tipoInstruccionDaliDAO
	 */
	public TipoInstruccionDaliDAO getTipoInstruccionDAO() {
		return tipoInstruccionDaliDAO;
	}

	/**
	 * Establece el valor del atributo tipoInstruccionDaliDAO
	 * 
	 * @param tipoInstruccionDaliDAO
	 *            el valor del atributo tipoInstruccionDaliDAO a establecer
	 */
	public void setTipoInstruccionDAO(TipoInstruccionDaliDAO tipoInstruccionDaliDAO) {
		this.tipoInstruccionDaliDAO = tipoInstruccionDaliDAO;
	}

	public InstitucionDTO getInstitucionConsultadaContraparte() {
		return institucionConsultadaContraparte;
	}

	public void setInstitucionConsultadaContraparte(InstitucionDTO institucionConsultadaContraparte) {
		this.institucionConsultadaContraparte = institucionConsultadaContraparte;
	}

}
