package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.util.ConvertBO2VO;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.services.util.UtilService;
import com.indeval.portaldali.middleware.services.util.ValidatorService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.dto.EstatusEmisionesDTO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.middleware.servicios.util.UtilsVO;
import com.indeval.portaldali.persistence.dao.common.*;
import com.indeval.portaldali.persistence.modelo.*;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.services.util.FactoryDivInt;
import com.indeval.portalinternacional.middleware.services.util.UtilDivIntService;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.*;
import com.indeval.portalinternacional.middleware.servicios.vo.*;
import com.indeval.portalinternacional.persistence.dao.*;
import com.indeval.portalinternacional.persistence.dao.EmisionDao;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;
import com.indeval.seguridadMensajeria.exception.DigitalSignException;
import com.indeval.seguridadMensajeria.sign.SendMessageService;
import com.indeval.seguridadMensajeria.sign.ValidateSignatureService;
import com.indeval.seguridadMensajeria.util.Constante;
import com.indeval.seguridadMensajeria.util.XMLUtils;
import com.indeval.seguridadMensajeria.vo.ErrorSeguridadMensajeriaVo;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.w3c.dom.Document;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;


/**
 * Implementaci&oacute;n de los servicios para consulta de  saldo custodios
 @author <a href="mailto:oraclefedora@gmail.com">Simon Alberto Rodiguez Pacheco.</a>
 *
 */
public class ConsultaSaldoCustodiosServiceImpl  implements ConsultaSaldoCustodiosService, Constantes {


    private CalendarioEmisionesDeudaExtDao calendarioEmisionesDeudaExtDao;



    private DivisaDaliDao divisaDaliDAO;
    public List<Boveda> consultaBovedas(Integer tipoBoveda)
            throws BusinessException {

        return calendarioEmisionesDeudaExtDao.consultaBovedas( tipoBoveda);
    }

    public List<DivisaDTO> findDivisaByBovedad(Long idBoveda) throws BusinessException {
        return divisaDaliDAO.findDivisaByBovedad(idBoveda);
    }

    @Override
    public PaginaVO consultaSaldoCustodio(ConsultaSaldoCustodiosInDTO consultaSaldoCustodiosInDTO, PaginaVO paginaVO) throws BusinessException {
        return divisaDaliDAO.consultaSaldoCustodio(consultaSaldoCustodiosInDTO, paginaVO);
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
}