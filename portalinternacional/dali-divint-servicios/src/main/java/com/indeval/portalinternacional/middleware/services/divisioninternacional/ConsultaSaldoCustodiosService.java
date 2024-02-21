package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.dto.EstatusEmisionesDTO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.*;
import com.indeval.portalinternacional.middleware.servicios.vo.*;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
