/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;

/**
 * Interfaz de negocio que define los métodos para la administración de las listas de distribución
 * para eventos corporativos.
 * 
 * @author Pablo Balderas
 *
 */
public interface ListaDistribucionService {

	/**
	 * Crea una lista de distribución en el sistema.
	 * @param listaDistribucion Lista a crear.
	 * @param gruposXLista 
	 * @return Lista creada.
	 */
	ListaDistribucion crearListaDistribucion(ListaDistribucion listaDistribucion);
	
	/**
	 * Modifica una lista de distribución en el sistema.
	 * @param listaDistribucion Lista a modificar.
	 * @return Lista modificada.
	 */
	ListaDistribucion modificarListaDistribucion(ListaDistribucion listaDistribucion);
	
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
	 * @param nombre
	 * @return
	 */
	ListaDistribucion obtenerListaDistribucionPorNombre(String nombre);
	
}
