package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoMulticarga;
import com.indeval.portalinternacional.middleware.servicios.modelo.MulticargaInternacional;
import com.indeval.portalinternacional.middleware.servicios.modelo.RegistroMulticarga;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoOperacionMulticarga;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.MulticargaVO;

public interface MulticargaInternacionalDao extends BaseDao{
	
	/**
	 * Realiza la consulta por nombre del Archivo
	 * @param String nombre del Archivo
	 * @return MulticargaInternacional o null si no existe 
	 * */	
	public MulticargaInternacional consultaByNombreArchivo(String nombreArchivo);
	
	/**
	 * Obtiene los valores del Catalogo Estado_Multicarga
	 * @return List<EstadoMulticarga>
	 * */
	public List<EstadoMulticarga> getCatalogoEstadoMulticarga();
	
	/**
	 * Obtiene los valores del Catalogo Tipo_Operacion_Multicarga
	 * @return List<TipoOperacionMulticarga> 
	 * */
	public List<TipoOperacionMulticarga> getCatalogoTipoOperacionMulticarga();
	
	/**
	 * Realiza la consulta de las operaciones Multicarga que cumplan con los criterios de busqueda
	 * @param MulticargaVO,PaginaVO
	 * @return PaginaVO
	 * @throws BusinessException 
	 * */
	public PaginaVO consultaOperacionesMulticarga(MulticargaVO multicargaVO,PaginaVO paginaVO) throws BusinessException;
	
	/**
	 * Realiza la consulta a Registros_Multicarga por el idMulticarga
	 * @param Long idMulticarga
	 * @return RegistroMulticarga o null si no existe
	 * @throws BusinessException 
	 * */
	public RegistroMulticarga consultaRegistrosByIdMulticarga(Long idMulticarga) throws BusinessException;
	
	/**
	 * Realiza la consulta a Multicarga_Internacional por el idMulticarga
	 * @param Long idMulticarga
	 * @return MulticargaInternacional o null si no existe  
	 * @throws BusinessException
	 * */
	public MulticargaInternacional consultaMulticargaByIdMulticarga(Long idMulticarga) throws BusinessException;

}
