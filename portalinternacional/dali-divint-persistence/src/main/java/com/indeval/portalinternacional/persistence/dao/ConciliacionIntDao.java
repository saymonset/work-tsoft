/**
 * 
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraMensajeConciliacionInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionIntDTO;

/**
 * @author lmunoz
 *
 */
public interface ConciliacionIntDao extends BaseDao{
	/**
	 * Consulta Conciliaciones Internacionales
	 * @param conciliacion
	 * @param paginaVO
	 * @return
	 * @throws BusinessException
	 */
	public PaginaVO consultaConciliacion(ConciliacionIntDTO conciliacion, PaginaVO paginaVO) throws BusinessException;
	
	/**
	 * Consulta Detalle Conciliaciones Internacionales
	 * @param detalleConciliacion
	 * @param paginaVO
	 * @return
	 * @throws BusinessException
	 */
	public PaginaVO consultaDetalleConciliacion(DetalleConciliacionIntDTO detalleConciliacion, PaginaVO paginaVO)throws BusinessException;
	/**
	 * consulta los mensajes originales de la conciliacion
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public List<BitacoraMensajeConciliacionInt> consultaBitacoraMensajeConciliacionInt(
			Long id)throws BusinessException;

}
