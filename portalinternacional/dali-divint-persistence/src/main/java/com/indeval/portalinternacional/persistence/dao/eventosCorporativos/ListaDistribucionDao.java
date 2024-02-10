/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.eventosCorporativos;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;

/**
 * Interfaz de negocio que define los métodos de acceso a datos para la entidad ListaDistribucion.
 * 
 * @author Pablo Balderas
 *
 */
public interface ListaDistribucionDao extends BaseDao {

	/**
	 * Obtiene una lista de distribución por id.
	 * @param idListaDistribucion Id de la lista a obtener.
	 * @return Lista correspondiente al id.
	 */
	ListaDistribucion obtenerListaDistribucionPorId(Long idListaDistribucion);
	
	/**
	 * Realiza la busqueda paginada de listas de distribución.
	 * @param criterioBusqueda Criterios para realizar la busqueda.
	 * @param paginacion Objeto para realizar la paginación
	 * @return PaginaVO con los resultados de la busqueda y el estado de la paginación.
	 */
	PaginaVO buscarListasDistribucion(ListaDistribucion criterioBusqueda, PaginaVO paginaVO);
	
	/**
	 * Obtiene las listas de distribución activas en el sistema.
	 * @return Listas de distribución activas.
	 */
	List<ListaDistribucion> obtenerListasDistribucionActivas();
	
	/**
	 * @param listaDistribucion
	 * @param gruposXLista 
	 * @return
	 */
	Long crearListaDistribucion(ListaDistribucion listaDistribucion);

	/**
	 * @param listaDistribucion
	 */
	void actualizarListaDistribucion(ListaDistribucion listaDistribucion);

	/**
	 * @param nombre
	 * @return
	 */
	ListaDistribucion obtenerListaDistribucionPorNombre(String nombre);
	/**
	 * trae las listas activas las que pertenece la persona
	 * @param idPersona
	 * @return
	 */
	List<ListaDistribucion> obtenerListasPertenecientes(final Long idPersona);
	/**
	 * trae las listas activas las que NO pertenece la persona
	 * @param idPersona
	 * @return
	 */
	List<ListaDistribucion> obtenerListasNoPertenecientes(final Long idPersona);

}
