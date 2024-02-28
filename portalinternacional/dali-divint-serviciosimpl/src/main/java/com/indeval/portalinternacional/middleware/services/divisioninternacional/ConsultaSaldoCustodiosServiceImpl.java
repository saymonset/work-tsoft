package com.indeval.portalinternacional.middleware.services.divisioninternacional;


import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;

import com.indeval.portaldali.persistence.modelo.*;

import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
 ;
import com.indeval.portalinternacional.middleware.servicios.vo.*;
import com.indeval.portalinternacional.persistence.dao.*;

import java.util.*;


/**
 * Implementaci&oacute;n de los servicios para consulta de  saldo custodios
 @author <a href="mailto:oraclefedora@gmail.com">Simon Alberto Rodiguez Pacheco.</a>
 *
 */
public class ConsultaSaldoCustodiosServiceImpl  implements ConsultaSaldoCustodiosService, Constantes {


    private CalendarioEmisionesDeudaExtDao calendarioEmisionesDeudaExtDao;



    private DivisaDaliDao divisaDaliDAO;
    private ConsultaSaldoCustodiosDao consultaSaldoCustodiosDao;
    public List<Boveda> consultaBovedas(Integer tipoBoveda)
            throws BusinessException {

        return calendarioEmisionesDeudaExtDao.consultaBovedas( tipoBoveda);
    }

    public List<DivisaDTO> findDivisaByBovedad(Long idBoveda) throws BusinessException {
        return divisaDaliDAO.findDivisaByBovedad(idBoveda);
    }

    @Override
    public PaginaVO consultaSaldoCustodio(ConsultaSaldoCustodiosInDTO consultaSaldoCustodiosInDTO, PaginaVO paginaVO) throws BusinessException {
        return consultaSaldoCustodiosDao.consultaSaldoCustodio(consultaSaldoCustodiosInDTO, paginaVO);
    }

    @Override
    public ConsultaSaldoCustodiosTotalesInDTO consultaSaldoCustodioTotales(ConsultaSaldoCustodiosInDTO criteriosConsulta) throws BusinessException {
        return consultaSaldoCustodiosDao.consultaSaldoCustodioTotales(criteriosConsulta);
    }

    public DivisaDaliDao getDivisaDaliDAO() {
        return divisaDaliDAO;
    }

    public void setDivisaDaliDAO(DivisaDaliDao divisaDaliDAO) {
        this.divisaDaliDAO = divisaDaliDAO;
    }

    public CalendarioEmisionesDeudaExtDao getCalendarioEmisionesDeudaExtDao() {
        return calendarioEmisionesDeudaExtDao;
    }

    public void setCalendarioEmisionesDeudaExtDao(CalendarioEmisionesDeudaExtDao calendarioEmisionesDeudaExtDao) {
        this.calendarioEmisionesDeudaExtDao = calendarioEmisionesDeudaExtDao;
    }

    public ConsultaSaldoCustodiosDao getConsultaSaldoCustodiosDao() {
        return consultaSaldoCustodiosDao;
    }

    public void setConsultaSaldoCustodiosDao(ConsultaSaldoCustodiosDao consultaSaldoCustodiosDao) {
        this.consultaSaldoCustodiosDao = consultaSaldoCustodiosDao;
    }
}