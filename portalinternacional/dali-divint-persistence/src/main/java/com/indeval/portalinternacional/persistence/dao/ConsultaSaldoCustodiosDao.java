package com.indeval.portalinternacional.persistence.dao;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosInDTO;

public interface ConsultaSaldoCustodiosDao {
    /**
     * Consulta Saldo Custodio
     * @param consultaSaldoCustodiosInDTO
     * @param paginaVO
     * @return
     * @throws BusinessException
     */
    public PaginaVO consultaSaldoCustodio(ConsultaSaldoCustodiosInDTO consultaSaldoCustodiosInDTO, PaginaVO paginaVO) throws BusinessException;
}
