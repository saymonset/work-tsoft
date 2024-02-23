package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;

import com.indeval.portalinternacional.middleware.servicios.vo.*;



import java.util.List;


/**
 *
 * @author <a href="mailto:oraclefedora@gmail.com">Simon Alberto Rodiguez Pacheco.</a>
 */
public interface ConsultaSaldoCustodiosService  {


    /**
     * obtiene el catalogo de Bovedas
     * @param soloInternacional solo obtiene las bovedas de internacional
     * @return
     * @throws BusinessException
     */
    public List<Boveda> consultaBovedas(Integer tipoBoveda)throws BusinessException;




    List<DivisaDTO> findDivisaByBovedad(Long idBoveda) throws BusinessException;
    public PaginaVO consultaSaldoCustodio(ConsultaSaldoCustodiosInDTO consultaSaldoCustodiosInDTO,PaginaVO paginaVO) throws BusinessException ;

}
