/**
 * 
 */
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraConfirmacion;
import com.indeval.portalinternacional.middleware.servicios.vo.ConfirmacionIntDTO;

/**
 * @author César Hernández
 *
 */
public interface ConfirmacionesIntDao extends BaseDao {
	
	/**
	 * Consulta las confirmaciones
	 * @param confirmacionIntDTO
	 * @param paginaVO
	 * @return
	 * @throws BusinessException
	 */
	PaginaVO consultaConfirmaciones(ConfirmacionIntDTO confirmacionIntDTO, final PaginaVO paginaVO) throws BusinessException;
	
	/**
	 * Consulta la bitacora de confirmacion
	 * @param idConfirmacion
	 * @return
	 */
	BitacoraConfirmacion consultaBitacoraConfirmacion(final Long idConfirmacion);
	
	/**
	 * Agrega o actualiza los comentarios dali para un registro de confirmacion
	 * @param idConfirmacion
	 * @param comentariosDali
	 */
	void agregaActualizaComentariosDali(final Long idConfirmacion, final String comentariosDali);
	
	/**
	 * Actualiza el id folio para un registro en especifico
	 * @param idConfirmacion
	 * @param idFolio
	 */
	void agregaActualizaIdFolio(final Long idConfirmacion, final String idFolio);
	
	/**
	 * Clcula la suma total de creditos
	 * @param dto
	 * @return
	 */
	Double obtieneSumaTotalCreditos(final ConfirmacionIntDTO dto);
	
	/**
	 * Clcula la suma total de debitos
	 * @param dto
	 * @return
	 */
	Double obtieneSumaTotalDebitos(final ConfirmacionIntDTO dto);
	
	/**
	 * obtiene el calendario de derechos por isin
	 */
	Long consultaCalendarioDerechosByIsin(final String isin);
	
}