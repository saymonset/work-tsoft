package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.dto.CustodiosDepositantesDto;
import com.indeval.portalinternacional.middleware.servicios.vo.MensajeSecuenciaVO;

/**
 * Interfaz de servicio para las operaciones relacionadas con consulta custodios depositantes
 */
public interface EmisionesCustodiosDepositantesService {
	
	/**
	 * Realiza la consulta con paginaci&oacute;n de emisiones custodios depositantes
	 * @param params
	 * @param paginaVO
	 * @return PaginaVO
	 */
	public PaginaVO consultarEmisionesCustodiosDepositantes(final CustodiosDepositantesDto params, PaginaVO paginaVO) throws BusinessException;

	/**
	 * Realiza la consulta para xml de emisiones custodios depositantes
	 * @param params
	 * @param paginaVO
	 * @return List<MensajeSecuenciaVO>
	 */
	public List<MensajeSecuenciaVO> consultarEmisionesCustodiosDepositantesXml(final CustodiosDepositantesDto params, PaginaVO paginaVO) throws Exception;
}
