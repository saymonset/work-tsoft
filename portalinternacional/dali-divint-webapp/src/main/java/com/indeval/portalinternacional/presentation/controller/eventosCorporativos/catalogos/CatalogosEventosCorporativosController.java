/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.eventosCorporativos.catalogos;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.constantes.CatalogosConstantes;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Base clase para los controladores de catalogos.
 * 
 * @author Pablo Balderas
 *
 */
public class CatalogosEventosCorporativosController extends ControllerBase {

	/** Indica si debe mostrar los criterios de busqueda */
	private boolean mostrarCriteriosBusqueda; 
	
	/** Indica si debe mostrar el resumen de la busqueda */
	private boolean mostrarResumenBusqueda;
	
	/** Indica si debe mostrar el boton de editar */
	private boolean mostrarBotonEditar;
	
	/** Indica si debe mostrar la generación de reportes */
	private boolean mostrarReportes;

	/** Indica el número de registros a exportar */
	private int totalRegistrosExportacion;
	
	/** Indica si la consulta ha sido ejecutada */
	private boolean consultaEjecutada = false;
	
	/** Lista con los estatus de las listas de distribución */
	private List<SelectItem> comboEstatus;
	
	/** Filtro para buscar por estatus */
	private Long filtroEstatus;
	
	/** Indica si esta registrando o modificando un registro */
	private boolean edicion;
	
	/**
	 * Esta bandera auxiliar se utiliza para el registro o modificación
	 * del estatus del registro de personas, grupos y listas de distribución 
	 */
	private boolean estadoRegistro;
	
	/** Indica el resultado del registro o modificación */
	private boolean resultadoRegMod;
	
	/** Indica el número de registros por página por omisión */
	private final int REGISTROS_X_PAGINA = 10;
	
	/** Lista principal para la funcionalidad de agregar/eliminar elementos */
	private List<SelectItem> listaPrincipal = new ArrayList<SelectItem>();
	
	/** Lista secundaria para la funcionalidad de agregar/eliminar elementos */
	private List<SelectItem> listaSecundaria = new ArrayList<SelectItem>();
	
	/** ID a agregar a la lista de distribución */
	private Integer idAgregar;

	/** ID a eliminar de la lista de distribución */
	private Integer idEliminar;
	
	/** Mensajes para el usuario */
	private String mensajeUsuario;
	
	/**
	 * Inicializa el objeto de paginación
	 */
	public void inicializarPaginacion() {
		paginaVO = new PaginaVO();
		paginaVO.setOffset(0);
		paginaVO.setRegistrosXPag(REGISTROS_X_PAGINA);
		paginaVO.setRegistros(null);
	}
	
	/**
	 * Agrega un elemento de la lista principal a la secundaria.
	 * @param actionEvent Evento generado por faces
	 */
	public void agregarElemento(ActionEvent actionEvent){
		if (null != idAgregar) {
			int tamanioListaPrincipal = listaPrincipal.size();
			SelectItem current = null;
			Integer cc = null;
			for (int i = 0; i < tamanioListaPrincipal; i++) {
				current = (SelectItem) listaPrincipal.get(i);
				cc = (Integer) current.getValue();
				if (cc.equals(idAgregar)) {
					listaSecundaria.add(current);
					listaPrincipal.remove(i);
					break;
				}
			}
		}
		idAgregar = null;
		idEliminar = null;
	}
	
	/**
	 * Elimina un elemento de la lista secundaria y lo reinserta en la principal.
	 * @param actionEvent Evento generado por faces
	 */
	public void eliminarElemento(ActionEvent actionEvent){
		if (null != idEliminar) {
			int tamanioListaSecundaria = listaSecundaria.size();
			SelectItem current = null;
			Integer cc = null;
			for (int i = 0; i < tamanioListaSecundaria; i++) {
				current = (SelectItem) listaSecundaria.get(i);
				cc = (Integer) current.getValue();
				if (cc.equals(getIdEliminar())) {
					listaPrincipal.add(current);
					listaSecundaria.remove(i);
					break;
				}
			}
		}
		idAgregar = null;
		idEliminar = null;
	}
	
	/**
	 * Obtiene una lista de selectItem a partir de una lista
	 * @param listaGrupos Lista a convertir
	 * @return Lista de selectItems
	 */
	@SuppressWarnings("rawtypes")
	public List<SelectItem> obtenerListaSelectItem(List lista) {
		return null;
	}
	
	/**
	 * Método para obtener el atributo mostrarCriteriosBusqueda
	 * @return El atributo mostrarCriteriosBusqueda
	 */
	public boolean isMostrarCriteriosBusqueda() {
		return mostrarCriteriosBusqueda;
	}

	/**
	 * Método para establecer el atributo mostrarCriteriosBusqueda
	 * @param mostrarCriteriosBusqueda El valor del atributo mostrarCriteriosBusqueda a establecer.
	 */
	public void setMostrarCriteriosBusqueda(boolean mostrarCriteriosBusqueda) {
		this.mostrarCriteriosBusqueda = mostrarCriteriosBusqueda;
	}

	/**
	 * Método para obtener el atributo mostrarResumenBusqueda
	 * @return El atributo mostrarResumenBusqueda
	 */
	public boolean isMostrarResumenBusqueda() {
		return mostrarResumenBusqueda;
	}

	/**
	 * Método para establecer el atributo mostrarResumenBusqueda
	 * @param mostrarResumenBusqueda El valor del atributo mostrarResumenBusqueda a establecer.
	 */
	public void setMostrarResumenBusqueda(boolean mostrarResumenBusqueda) {
		this.mostrarResumenBusqueda = mostrarResumenBusqueda;
	}

	/**
	 * Método para obtener el atributo mostrarBotonEditar
	 * @return El atributo mostrarBotonEditar
	 */
	public boolean isMostrarBotonEditar() {
		return mostrarBotonEditar;
	}

	/**
	 * Método para establecer el atributo mostrarBotonEditar
	 * @param mostrarBotonEditar El valor del atributo mostrarBotonEditar a establecer.
	 */
	public void setMostrarBotonEditar(boolean mostrarBotonEditar) {
		this.mostrarBotonEditar = mostrarBotonEditar;
	}

	/**
	 * Método para obtener el atributo mostrarReportes
	 * @return El atributo mostrarReportes
	 */
	public boolean isMostrarReportes() {
		return mostrarReportes;
	}

	/**
	 * Método para establecer el atributo mostrarReportes
	 * @param mostrarReportes El valor del atributo mostrarReportes a establecer.
	 */
	public void setMostrarReportes(boolean mostrarReportes) {
		this.mostrarReportes = mostrarReportes;
	}

	/**
	 * Método para obtener el atributo totalRegistrosExportacion
	 * @return El atributo totalRegistrosExportacion
	 */
	public int getTotalRegistrosExportacion() {
		return totalRegistrosExportacion;
	}

	/**
	 * Método para establecer el atributo totalRegistrosExportacion
	 * @param totalRegistrosExportacion El valor del atributo totalRegistrosExportacion a establecer.
	 */
	public void setTotalRegistrosExportacion(int totalRegistrosExportacion) {
		this.totalRegistrosExportacion = totalRegistrosExportacion;
	}
	
	/**
	 * Método para obtener el atributo consultaEjecutada
	 * @return El atributo consultaEjecutada
	 */
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	/**
	 * Método para establecer el atributo consultaEjecutada
	 * @param consultaEjecutada El valor del atributo consultaEjecutada a establecer.
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}
	
	/**
	 * Método para obtener el atributo estatusListaDistribucion
	 * @return El atributo estatusListaDistribucion
	 */
	public List<SelectItem> getComboEstatus() {
		comboEstatus = new ArrayList<SelectItem>();
		comboEstatus.add(
			new SelectItem(CatalogosConstantes.VALOR_TODOS, CatalogosConstantes.TODOS));
		comboEstatus.add(
			new SelectItem(CatalogosConstantes.VALOR_ESTATUS_ACTIVO, CatalogosConstantes.ESTATUS_ACTIVO));
		comboEstatus.add(
			new SelectItem(CatalogosConstantes.VALOR_ESTATUS_INACTIVO, CatalogosConstantes.ESTATUS_INACTIVO));
		return comboEstatus;
	}

	/**
	 * Método para establecer el atributo estatusListaDistribucion
	 * @param estatusListaDistribucion El valor del atributo estatusListaDistribucion a establecer.
	 */
	public void setComboEstatus(List<SelectItem> comboEstatus) {
		this.comboEstatus = comboEstatus;
	}

	/**
	 * Método para obtener el atributo estadoRegistro
	 * @return El atributo estadoRegistro
	 */
	public boolean isEstadoRegistro() {
		return estadoRegistro;
	}

	/**
	 * Método para establecer el atributo estadoRegistro
	 * @param estadoRegistro El valor del atributo estadoRegistro a establecer.
	 */
	public void setEstadoRegistro(boolean estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	/**
	 * Método para obtener el atributo filtroEstatus
	 * @return El atributo filtroEstatus
	 */
	public Long getFiltroEstatus() {
		return filtroEstatus;
	}

	/**
	 * Método para establecer el atributo filtroEstatus
	 * @param filtroEstatus El valor del atributo filtroEstatus a establecer.
	 */
	public void setFiltroEstatus(Long filtroEstatus) {
		this.filtroEstatus = filtroEstatus;
	}
	
	/**
	 * Obtiene la descripción del estatus de la lista segun su valor numérico.
	 * @return Descripción del estatus de la lista.
	 */
	public String getDescripcionEstatus() {
		String descripcion = null;
		if(CatalogosConstantes.VALOR_TODOS.equals(filtroEstatus)) {
			descripcion = CatalogosConstantes.TODOS;
		}
		else if(CatalogosConstantes.VALOR_ESTATUS_ACTIVO.equals(filtroEstatus)) {
			descripcion = CatalogosConstantes.ESTATUS_ACTIVO;
		}
		else if(CatalogosConstantes.VALOR_ESTATUS_INACTIVO.equals(filtroEstatus)) {
			descripcion = CatalogosConstantes.ESTATUS_INACTIVO;
		}
		return descripcion;
	}

	/**
	 * Método para obtener el atributo edicion
	 * @return El atributo edicion
	 */
	public boolean isEdicion() {
		return edicion;
	}

	/**
	 * Método para establecer el atributo edicion
	 * @param edicion El valor del atributo edicion a establecer.
	 */
	public void setEdicion(boolean edicion) {
		this.edicion = edicion;
	}

	/**
	 * Método para obtener el atributo resultadoRegMod
	 * @return El atributo resultadoRegMod
	 */
	public boolean isResultadoRegMod() {
		return resultadoRegMod;
	}

	/**
	 * Método para establecer el atributo resultadoRegMod
	 * @param resultadoRegMod El valor del atributo resultadoRegMod a establecer.
	 */
	public void setResultadoRegMod(boolean resultadoRegMod) {
		this.resultadoRegMod = resultadoRegMod;
	}

	/**
	 * Método para obtener el atributo idAgregar
	 * @return El atributo idAgregar
	 */
	public Integer getIdAgregar() {
		return idAgregar;
	}

	/**
	 * Método para establecer el atributo idAgregar
	 * @param idAgregar El valor del atributo idAgregar a establecer.
	 */
	public void setIdAgregar(Integer idAgregar) {
		this.idAgregar = idAgregar;
	}

	/**
	 * Método para obtener el atributo idEliminar
	 * @return El atributo idEliminar
	 */
	public Integer getIdEliminar() {
		return idEliminar;
	}

	/**
	 * Método para establecer el atributo idEliminar
	 * @param idEliminar El valor del atributo idEliminar a establecer.
	 */
	public void setIdEliminar(Integer idEliminar) {
		this.idEliminar = idEliminar;
	}

	/**
	 * Método para obtener el atributo listaPrincipal
	 * @return El atributo listaPrincipal
	 */
	public List<SelectItem> getListaPrincipal() {
		return listaPrincipal;
	}

	/**
	 * Método para establecer el atributo listaPrincipal
	 * @param listaPrincipal El valor del atributo listaPrincipal a establecer.
	 */
	public void setListaPrincipal(List<SelectItem> listaPrincipal) {
		this.listaPrincipal = listaPrincipal;
	}

	/**
	 * Método para obtener el atributo listaSecundaria
	 * @return El atributo listaSecundaria
	 */
	public List<SelectItem> getListaSecundaria() {
		return listaSecundaria;
	}

	/**
	 * Método para establecer el atributo listaSecundaria
	 * @param listaSecundaria El valor del atributo listaSecundaria a establecer.
	 */
	public void setListaSecundaria(List<SelectItem> listaSecundaria) {
		this.listaSecundaria = listaSecundaria;
	}

	/**
	 * Método para obtener el atributo mensajeUsuario
	 * @return El atributo mensajeUsuario
	 */
	public String getMensajeUsuario() {
		return mensajeUsuario;
	}

	/**
	 * Método para establecer el atributo mensajeUsuario
	 * @param mensajeUsuario El valor del atributo mensajeUsuario a establecer.
	 */
	public void setMensajeUsuario(String mensajeUsuario) {
		this.mensajeUsuario = mensajeUsuario;
	}
	
}
