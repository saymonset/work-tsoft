package com.indeval.portalinternacional.persistence.dao;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosInDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosTotalesInDTO;

public interface ConsultaSaldoCustodiosDao {
    /**
     * Consulta Saldo Custodio
     * @param consultaSaldoCustodiosInDTO
     * @param paginaVO
     * @return
     * @throws BusinessException
     *
     */
     PaginaVO consultaSaldoCustodio( final ConsultaSaldoCustodiosInDTO consultaSaldoCustodiosInDTO,final PaginaVO paginaVO) throws BusinessException;

     ConsultaSaldoCustodiosTotalesInDTO consultaSaldoCustodioTotales(final ConsultaSaldoCustodiosInDTO criteriosConsulta) throws BusinessException ;
}
