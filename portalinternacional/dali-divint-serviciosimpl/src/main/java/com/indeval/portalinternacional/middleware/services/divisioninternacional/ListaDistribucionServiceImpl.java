/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ListaDistribucionDao;

/**
 * Implementación de servicios para Listas de Distribución
 * 
 * @author amoralesm
 *
 */
public class ListaDistribucionServiceImpl implements ListaDistribucionService {

	ListaDistribucionDao listaDistribucionDao;

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ListaDistribucionService#crearListaDistribucion(com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion, java.util.List)
	 */
	public ListaDistribucion crearListaDistribucion(
			ListaDistribucion listaDistribucion) {

		Long idlistaDistribucion = listaDistribucionDao.crearListaDistribucion(listaDistribucion);
		
		ListaDistribucion listaNueva = obtenerListaDistribucionPorId(idlistaDistribucion);		
	
		return listaNueva;
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ListaDistribucionService#modificarListaDistribucion(com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion)
	 */
	public ListaDistribucion modificarListaDistribucion(
			ListaDistribucion listaDistribucion) {
		
		Long idListaDistribucion = listaDistribucion.getIdLista();
		listaDistribucionDao.actualizarListaDistribucion(listaDistribucion);
		
		ListaDistribucion listaActualizada = obtenerListaDistribucionPorId(idListaDistribucion);
		return listaActualizada;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ListaDistribucionService#obtenerListaDistribucionPorId(java.lang.Long)
	 */
	public ListaDistribucion obtenerListaDistribucionPorId(
			Long idListaDistribucion) {
		return listaDistribucionDao.obtenerListaDistribucionPorId(idListaDistribucion);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ListaDistribucionService#buscarListasDistribucion(com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO buscarListasDistribucion(
			ListaDistribucion criterioBusqueda, PaginaVO paginaVO) {
		return listaDistribucionDao.buscarListasDistribucion(criterioBusqueda, paginaVO);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ListaDistribucionService#obtenerListasDistribucionActivas()
	 */
	public List<ListaDistribucion> obtenerListasDistribucionActivas() {
		return listaDistribucionDao.obtenerListasDistribucionActivas();
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ListaDistribucionService#obtenerListaDistribucionPorNombre(java.lang.String)
	 */
	public ListaDistribucion obtenerListaDistribucionPorNombre(String nombre) {
		return listaDistribucionDao.obtenerListaDistribucionPorNombre(nombre);
	}
	
	/**
	 * @param listaDistribucionDao
	 */
	public void setListaDistribucionDao(ListaDistribucionDao listaDistribucionDao) {
		this.listaDistribucionDao = listaDistribucionDao;
	}

}
