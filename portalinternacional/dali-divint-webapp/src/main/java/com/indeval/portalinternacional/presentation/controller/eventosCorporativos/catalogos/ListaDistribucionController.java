/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.eventosCorporativos.catalogos;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.ListaDistribucionService;
import com.indeval.portalinternacional.middleware.servicios.constantes.CatalogosConstantes;

import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;

/**
 * Controller para la administración de las listas de distribución.
 * 
 * @author Pablo Balderas
 *
 */
public class ListaDistribucionController extends CatalogosEventosCorporativosController {

	/** Filtro para buscar por nombre de lista de distribución */
	private String nombreListaDistribucion;
	
	/** Filtro para buscar por estatus de lista de distribución */
	private Long estatusLista;
	
	/** Objeto para manejar la paginación. */
	private PaginaVO paginacion;
	
	/** Listas de distribución */
	private List<ListaDistribucion> listasDistribucion;
	
	/** Objeto para agregar o modificar listas de distribución */
	private ListaDistribucion listaDistribucion;
	
	

	/** Servicio para la administración de listas de distribución */
	private ListaDistribucionService listaDistribucionService;
	
	
	
	/** Indica el estado de la lista a registrar o modificar */
	private boolean estadoListaRegMod;
	
	/**
	 * Método para inicializar la pantalla
	 * @return Nulo
	 */
	public String getInit() {
		if(!isConsultaEjecutada()) {
			limpiarConsulta(null);
		}
		return null;
	}

	/**
	 * Método para inicializar la pantalla de alta/modificación
	 * @return Nulo
	 */
	public String getInitAltaModificacion() {
		if(listaDistribucion == null) {
			String idParametro = 
					FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().
						get(CatalogosConstantes.PARAMETRO_ID_LISTA_DISTRIBUCION);
				if(idParametro != null) {
					// Es modificación
					setEdicion(true);
					// Obtiene el detalle de la lista
					Long idListaDistribucion = Long.valueOf(idParametro);
					listaDistribucion = listaDistribucionService.obtenerListaDistribucionPorId(idListaDistribucion);
					
					// Coloca el estado del registro
					setEstadoRegistro(CatalogosConstantes.VALOR_ESTATUS_ACTIVO.equals(listaDistribucion.getInactivo()));
				}
				else {
					// Es nuevo registro
					setEdicion(false);
					// Inicializa el objeto listaDistribucion
					listaDistribucion = new ListaDistribucion();
					// Por omision, la lista debe nacer deshabilitada
					setEstadoRegistro(false);
					
				}
				setResultadoRegMod(false);
		}
		return null;
	}
	
	/**
	 * Método para limpiar la consulta.
	 * @param actionEvent Evento generado por faces.
	 */
	public void limpiarConsulta(ActionEvent actionEvent) {
		nombreListaDistribucion = null;
		setFiltroEstatus(null);
		setPaginaVO(new PaginaVO());
		listasDistribucion = null;
		setConsultaEjecutada(false);		
		setMostrarCriteriosBusqueda(true);
		setMostrarResumenBusqueda(false);
		setMostrarBotonEditar(false);
		setMostrarReportes(false);
		this.estatusLista=CatalogosConstantes.VALOR_TODOS;
	}

	/**
	 * Muestra los criterios de busqueda y oculta el resumen.
	 * @param actionEvent Evento generado por faces.
	 */
	public void editarConsulta(ActionEvent actionEvent) {
		ajustarCriteriosBusqueda();
		//Indicamos que partes de la pantalla deben mostrarse
		setMostrarCriteriosBusqueda(true);
		setMostrarResumenBusqueda(false);
		setMostrarBotonEditar(false);
	}
	
	/**
	 * Método para realizar la búsqueda de las listas de distribución de acuerdo
	 * a los parámetros capturados por el usuario.
	 * @param actionEvent Evento generado por faces.
	 */
	public void buscarListasDistribucion(ActionEvent actionEvent) {
		//Inicializa la paginación
		inicializarPaginacion();
		ejecutarConsulta();
		//Indica que la consulta ha sido ejecutada
		setConsultaEjecutada(true);
		setMostrarCriteriosBusqueda(false);
		setMostrarResumenBusqueda(true);
		setMostrarBotonEditar(true);
		//Indica si debe mostrar la generación de reportes
		setMostrarReportes(listasDistribucion != null && !listasDistribucion.isEmpty());
		//Ajusta los criterios de busqueda
		ajustarCriteriosDeBusqueda();
	}
	
	/**
	 * Método que realiza la consulta de las grupos después del registro o modificación.
	 * @param actionEvent Evento generado por faces.
	 */
	public void cerrarVentanaAltaModificacion(ActionEvent actionEvent) {
		//Inicializa la paginación
		inicializarPaginacion();
		//Resea los campos de búsqueda
		nombreListaDistribucion = null;
		setFiltroEstatus(-1L);
		ejecutarConsulta();
		//Indica que la consulta ha sido ejecutada
		setConsultaEjecutada(true);
		setMostrarCriteriosBusqueda(false);
		setMostrarResumenBusqueda(true);
		setMostrarBotonEditar(true);
		//Indica si debe mostrar la generación de reportes
		setMostrarReportes(listasDistribucion != null && !listasDistribucion.isEmpty());
		//Ajusta los criterios de busqueda
		ajustarCriteriosDeBusqueda();
		//Coloca el mensaje de exito o error
		if(isResultadoRegMod()) {
			addMessageFromProperties("msgOperacionExitosa", FacesMessage.SEVERITY_INFO);
		}
	}

	
	/**
	 * Genera los reportes de Consulta de Emisiones.
	 * @param actionEvent Evento generado por faces.
	 */
	@SuppressWarnings("unchecked")
	public void generarReportes(ActionEvent actionEvent) {	
		
		reiniciarEstadoPeticion();
		paginaVO = new PaginaVO();
		paginaVO.setOffset(0);
		paginaVO.setRegistrosXPag(PaginaVO.TODOS);
		paginaVO.setRegistros(null);
		paginaVO = listaDistribucionService.buscarListasDistribucion(crearCriterioBusqueda(), paginaVO);
		listasDistribucion = paginaVO.getRegistros();
		//Coloca el total de resultados
		setTotalRegistrosExportacion(listasDistribucion.size());
	}
	
	/**
	 * Guarda una lista de distribución en el sistema.
	 * @param actionEvent Evento generado por faces.
	 */
	public String guardarListaDistribucion() {
		setResultadoRegMod(false);
		//Valida los datos capturados por el usuario
		if(validarListaDistribucion()) {
			//Coloca el estado de la lista
			this.listaDistribucion.setInactivo(
				isEstadoRegistro() ? CatalogosConstantes.VALOR_ESTATUS_ACTIVO : CatalogosConstantes.VALOR_ESTATUS_INACTIVO);
			//Pasa el nombre y la descripción a mayusculas
			listaDistribucion.setNombre(listaDistribucion.getNombre().toUpperCase());
			
			if(StringUtils.isNotEmpty(listaDistribucion.getDescripcion())){
				listaDistribucion.setDescripcion(listaDistribucion.getDescripcion().toUpperCase());
			}
			//Se trata de una modificación
			if(isEdicion()) {
				
				listaDistribucion = listaDistribucionService.modificarListaDistribucion(listaDistribucion);
			}
			//Se trata de un registro
			else {
				
				listaDistribucion = listaDistribucionService.crearListaDistribucion(listaDistribucion);
			}
			//Indicamos si la operación fue correcta.
			setResultadoRegMod(listaDistribucion != null);
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.presentation.controller.common.ControllerBase#ejecutarConsulta()
	 */
	@SuppressWarnings("unchecked")
	public String ejecutarConsulta() {
		ajustarCriteriosBusqueda();
		//Realizamos la busqueda
		paginaVO = listaDistribucionService.buscarListasDistribucion(crearCriterioBusqueda(), paginaVO);
		if(paginaVO != null && paginaVO.getRegistros() != null && !paginaVO.getRegistros().isEmpty()) {
			listasDistribucion = paginaVO.getRegistros();
		}
		else {
			listasDistribucion = new ArrayList<ListaDistribucion>();
		}
		//Coloca el total de paginas
		setTotalPaginas(paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag());
		if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0) {
			setTotalPaginas(getTotalPaginas() + 1);
		}
		setTotalPaginas((getTotalPaginas() <= 0) ? 1 : getTotalPaginas());
		return null;
		
	}
	
	/**
	 * Obtiene la descripción del estatus de la lista segun su valor numérico.
	 * @return Descripción del estatus de la lista.
	 */
	public String getDescripcionEstatus() {
		String descripcion = null;
		
		if(CatalogosConstantes.VALOR_TODOS.equals(estatusLista)) {
			descripcion = CatalogosConstantes.TODOS;
		}
		else if(CatalogosConstantes.VALOR_ESTATUS_ACTIVO.equals(estatusLista)) {
			descripcion = CatalogosConstantes.ESTATUS_ACTIVO;
		}
		else if(CatalogosConstantes.VALOR_ESTATUS_INACTIVO.equals(estatusLista)) {
			descripcion = CatalogosConstantes.ESTATUS_INACTIVO;
		}
		return descripcion;
	}
	
	
	
	
	/**
	 * Método que valida los valores de la lista de distribución a registrar o modificar.
	 * @return true si la validación es correcta; false en caso contrario.
	 */
	private boolean validarListaDistribucion() {
		boolean valido = true;
		//Valida el nombre
		if(StringUtils.isEmpty(listaDistribucion.getNombre())) {
			addMessageFromProperties("msgErrorCampoObligatorio", new Object[]{"Nombre Lista"}, FacesMessage.SEVERITY_ERROR);
			valido = false;
		}
		//Valida que el correo no este dado de alta
		ListaDistribucion listaPorNombre = listaDistribucionService.obtenerListaDistribucionPorNombre(listaDistribucion.getNombre());
		if(isEdicion()) {
			if(listaPorNombre != null && !listaDistribucion.getIdLista().equals(listaPorNombre.getIdLista())) {
				addMessageFromProperties("msgErrorListaExistente", FacesMessage.SEVERITY_ERROR);
				valido = false;
			}
		}
		else if(listaPorNombre != null && listaPorNombre.getNombre().equals(listaDistribucion.getNombre())) {
			addMessageFromProperties("msgErrorListaExistente", FacesMessage.SEVERITY_ERROR);
			valido = false;
		}
		
		if(StringUtils.isEmpty(this.listaDistribucion.getDescripcion())){
			addMessageFromProperties("msgErrorCampoObligatorio", new Object[]{"Descripci\u00F3n"},FacesMessage.SEVERITY_ERROR);
			valido = false;
		}
		return valido;
	}
	
	/**
	 * Ajusta los criterios de busqueda para colocar "TODOS" si no capturo nada.
	 */
	private void ajustarCriteriosDeBusqueda() {
		//Ajusta los criterios de busqueda
		nombreListaDistribucion = StringUtils.isEmpty(nombreListaDistribucion) ? CatalogosConstantes.TODOS : nombreListaDistribucion.toUpperCase();
	}
	
	/**
	 * Crea el criterio de busqueda para grupos
	 * @return Objeto con el criterio de busqueda.
	 */
	private ListaDistribucion crearCriterioBusqueda() {
		ajustarCriteriosBusqueda();

		//Creamos el criterio de busqueda
		ListaDistribucion criterioLista = new ListaDistribucion();
		//Colocamos los criterios capturados por el usuario
		criterioLista.setNombre(StringUtils.isNotEmpty(nombreListaDistribucion) ? nombreListaDistribucion.toUpperCase() : null);
		criterioLista.setInactivo(estatusLista);

		//Regresamos el criterio creado
		return criterioLista;
	}

	/**
	 * Ajusta los criterios de busqueda
	 */
	private void ajustarCriteriosBusqueda() {
		if(CatalogosConstantes.TODOS.equals(nombreListaDistribucion)) {
			nombreListaDistribucion = null;
		}
	}

	/**
	 * Método para obtener el atributo nombreListaDistribucion
	 * @return El atributo nombreListaDistribucion
	 */
	public String getNombreListaDistribucion() {
		return nombreListaDistribucion;
	}

	/**
	 * Método para establecer el atributo nombreListaDistribucion
	 * @param nombreListaDistribucion El valor del atributo nombreListaDistribucion a establecer.
	 */
	public void setNombreListaDistribucion(String nombreListaDistribucion) {
		this.nombreListaDistribucion = nombreListaDistribucion;
	}

	/**
	 * Método para obtener el atributo estatusLista
	 * @return El atributo estatusLista
	 */
	public Long getEstatusLista() {
		return estatusLista;
	}

	/**
	 * Método para establecer el atributo estatusLista
	 * @param estatusLista El valor del atributo estatusLista a establecer.
	 */
	public void setEstatusLista(Long estatusLista) {
		this.estatusLista = estatusLista;
	}

	/**
	 * Método para obtener el atributo paginacion
	 * @return El atributo paginacion
	 */
	public PaginaVO getPaginacion() {
		return paginacion;
	}

	/**
	 * Método para establecer el atributo paginacion
	 * @param paginacion El valor del atributo paginacion a establecer.
	 */
	public void setPaginacion(PaginaVO paginacion) {
		this.paginacion = paginacion;
	}

	/**
	 * Método para obtener el atributo listasDistribucion
	 * @return El atributo listasDistribucion
	 */
	public List<ListaDistribucion> getListasDistribucion() {
		return listasDistribucion;
	}

	/**
	 * Método para establecer el atributo listasDistribucion
	 * @param listasDistribucion El valor del atributo listasDistribucion a establecer.
	 */
	public void setListasDistribucion(List<ListaDistribucion> listasDistribucion) {
		this.listasDistribucion = listasDistribucion;
	}

	/**
	 * Método para obtener el atributo listaDistribucion
	 * @return El atributo listaDistribucion
	 */
	public ListaDistribucion getListaDistribucion() {
		return listaDistribucion;
	}

	/**
	 * Método para establecer el atributo listaDistribucion
	 * @param listaDistribucion El valor del atributo listaDistribucion a establecer.
	 */
	public void setListaDistribucion(ListaDistribucion listaDistribucion) {
		this.listaDistribucion = listaDistribucion;
	}

	

	/**
	 * Método para obtener el atributo listaDistribucionService
	 * @return El atributo listaDistribucionService
	 */
	public ListaDistribucionService getListaDistribucionService() {
		return listaDistribucionService;
	}

	/**
	 * Método para establecer el atributo listaDistribucionService
	 * @param listaDistribucionService El valor del atributo listaDistribucionService a establecer.
	 */
	public void setListaDistribucionService(
			ListaDistribucionService listaDistribucionService) {
		this.listaDistribucionService = listaDistribucionService;
	}

	/**
	 * Método para obtener el atributo estadoListaRegMod
	 * @return El atributo estadoListaRegMod
	 */
	public boolean isEstadoListaRegMod() {
		return estadoListaRegMod;
	}

	/**
	 * Método para establecer el atributo estadoListaRegMod
	 * @param estadoListaRegMod El valor del atributo estadoListaRegMod a establecer.
	 */
	public void setEstadoListaRegMod(boolean estadoListaRegMod) {
		this.estadoListaRegMod = estadoListaRegMod;
	}

}
