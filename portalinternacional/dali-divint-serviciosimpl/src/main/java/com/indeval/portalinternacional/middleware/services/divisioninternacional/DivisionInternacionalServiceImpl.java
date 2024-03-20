/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.indeval.portalinternacional.middleware.servicios.vo.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.w3c.dom.Document;

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
import com.indeval.portaldali.persistence.dao.common.BitacoraOperacionesDao;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDao;
import com.indeval.portaldali.persistence.dao.common.CuponDao;
import com.indeval.portaldali.persistence.dao.common.EmisionDao;
import com.indeval.portaldali.persistence.dao.common.EstatusEmisionesDao;
import com.indeval.portaldali.persistence.dao.common.InstitucionDao;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDao;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaHDao;
import com.indeval.portaldali.persistence.modelo.BitacoraOperaciones;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Cupon;
import com.indeval.portaldali.persistence.modelo.Depositos;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portaldali.persistence.modelo.Instrumento;
import com.indeval.portaldali.persistence.modelo.PosicionNombrada;
import com.indeval.portaldali.persistence.modelo.Retiros;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.services.util.FactoryDivInt;
import com.indeval.portalinternacional.middleware.services.util.UtilDivIntService;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraMensajeConciliacionInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraMensajeSwift;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraOperacionesSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.Bovedas;
import com.indeval.portalinternacional.middleware.servicios.modelo.CalendarioDerechos;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.ConciliacionInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.Control;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.CustodioBoveda;
import com.indeval.portalinternacional.middleware.servicios.modelo.CustodioTipoBenef;
import com.indeval.portalinternacional.middleware.servicios.modelo.DerechoMensaje;
import com.indeval.portalinternacional.middleware.servicios.modelo.Emisiones;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoDerechoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoMensajeSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusConciliacionInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.LiquidacionParcialMoi;
import com.indeval.portalinternacional.middleware.servicios.modelo.NotificacionAccion;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSicView;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionesConciliacionInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionesConciliacionInt.TipoOperacionConciliacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.REmisionBoveda;
import com.indeval.portalinternacional.middleware.servicios.modelo.RTipoValorCustodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoPagoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.VBitacoraOperacionesSic;
import com.indeval.portalinternacional.middleware.servicios.vo.AgenteArqueoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.AgenteFideicomisoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.AltaCustodioVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ArqueoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.CalendarioEmisionesDeudaExtDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCierreFideicomisoParams;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCierreFideicomisoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCustodiosVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaFideicomisosVO;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.EmisionArqueadaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.EstadisticasFideicomisoPorAgenteVO;
import com.indeval.portalinternacional.middleware.servicios.vo.EstadisticasFideicomisoPorCuentaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.GrabaOperacionParams;
import com.indeval.portalinternacional.middleware.servicios.vo.HistorialBitacoraOperacionesSICDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ListaArqueoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.MovimientoFideicomisoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.PorcentajeAgenteVO;
import com.indeval.portalinternacional.middleware.servicios.vo.PorcentajeArqueoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.PorcentajeCuentaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.TopeCirculanteFidecomisoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalArqueoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalListaArqueoVO;
import com.indeval.portalinternacional.persistence.dao.CalendarioEmisionesDeudaExtDao;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;
import com.indeval.portalinternacional.persistence.dao.ConciliacionIntDao;
import com.indeval.portalinternacional.persistence.dao.CustodioTipoBenefDao;
import com.indeval.portalinternacional.persistence.dao.DepositosDivIntDao;
import com.indeval.portalinternacional.persistence.dao.EmisionesConsultasDao;
import com.indeval.portalinternacional.persistence.dao.EstatusOperacionDao;
import com.indeval.portalinternacional.persistence.dao.OperacionSicDao;
import com.indeval.portalinternacional.persistence.dao.PosicionNombradaDivIntDao;
import com.indeval.portalinternacional.persistence.dao.PosicionNombradaHDivIntDao;
import com.indeval.portalinternacional.persistence.dao.REmisionBovedaDao;
import com.indeval.portalinternacional.persistence.dao.RetirosDivIntDao;
import com.indeval.portalinternacional.persistence.dao.SettlementDisciplineRegimeDao;
import com.indeval.portalinternacional.persistence.dao.SicDetalleDao;
import com.indeval.portalinternacional.persistence.dao.SicEmisionDao;
import com.indeval.portalinternacional.persistence.dao.TipoBeneficiarioDao;
import com.indeval.portalinternacional.persistence.dao.TopeCirculanteDao;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;
import com.indeval.seguridadMensajeria.exception.DigitalSignException;
import com.indeval.seguridadMensajeria.sign.SendMessageService;
import com.indeval.seguridadMensajeria.sign.ValidateSignatureService;
import com.indeval.seguridadMensajeria.util.Constante;
import com.indeval.seguridadMensajeria.util.XMLUtils;
import com.indeval.seguridadMensajeria.vo.ErrorSeguridadMensajeriaVo;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;

/**
 * Implementaci&oacute;n de los servicios para divisi&oacute;n internacional
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
@SuppressWarnings({"unchecked"})
public class DivisionInternacionalServiceImpl implements DivisionInternacionalService, Constantes {

    /** Para el registro de log de esta clase */
	private static final Logger log = LoggerFactory.getLogger(DivisionInternacionalServiceImpl.class);

    /**
     * EmisionVO[]
     */
    private static final EmisionVO[] emptyEmisionVOArray = new EmisionVO[0];

    /**
     * CatBic[]
     */
    private static final CatBic[] emptyCatBicArray = new CatBic[0];

    /**
     * SicDetalle[]
     */
    private static final SicDetalle[] emptySicDetalleArray = new SicDetalle[0];

    /**
     * PorcentajeAgenteVO[]
     */
    private static final PorcentajeAgenteVO[] emptyPorcentajeAgenteVOArray = new PorcentajeAgenteVO[0];

    /**
     * PorcentajeCuentaVO[]
     */
    private static final PorcentajeCuentaVO[] emptyPorcentajeCuentaVOArray = new PorcentajeCuentaVO[0];

    /**
     * AgenteVO
     */
    private static final AgenteVO agenteNafinsa = new AgenteVO("02","022","8335");

    /**
     * AgenteVO
     */
    private static final AgenteVO agenteVitro = new AgenteVO("02","022","8345");

    /**
     * AgenteVO
     */
    private static final AgenteVO agenteInbur = new AgenteVO("02","021","7460");

    /**
     * AgenteVO
     */
    private static final AgenteVO agenteBanamex = new AgenteVO("02","061","6960");

    /**
     * AgenteVO
     */
    private static final AgenteVO agenteNafinsaCMA = new AgenteVO("02","022","8347");
    
    /** Emisora para el Fideicomiso de Nafinsa */
    private String EMISORA_CMA = "CMA";
    
    /**
     * Comparador de objetos Long para su ordenamiento
     */
    private static final Comparator<Long> longOrder = new Comparator<Long>() {
		public int compare(Long o1, Long o2) {
			return (o1.compareTo(o2));
		}
    };

    /**
     * Comparador de objetos String para su ordenamiento
     */
    private static final Comparator<String> stringOrder = new Comparator<String>() {
		public int compare(String o1, String o2) {
			return (o1.compareTo(o2));
		}
    };

    /**
     * Comparador de objetos AgenteFideicomisoVO para su ordenamiento
     */
    private static final Comparator<AgenteFideicomisoVO> agenteFideicomisoOrder = new Comparator<AgenteFideicomisoVO>() {
		public int compare(AgenteFideicomisoVO o1, AgenteFideicomisoVO o2) {
			return (o1.compareTo(o2));
		}
    };

    /** Bean de acceso a los servicios de DateUtilService */
    private DateUtilService dateUtilService = null;

    /** Resolvedor de Mensajes */
    private MessageResolver messageResolver = null;

    /** Bean de acceso a los servicios de UtilService */
    private UtilService utilService = null;

    /** Bean de acceso a ValidatorDivInt */
    private ValidatorService validatorService = null;

    /**
     * String
     */
    private String seqFolioControl = null;

    /**
     * BitacoraOperacionesDao
     */
    private BitacoraOperacionesDao bitacoraOperacionesDao = null;

    /**
     * ValidatorDivIntService
     */
    private ValidatorDivIntService validatorDivIntService = null;

    /**
     * OperacionSicDao
     */
    private OperacionSicDao operacionSicDao = null;

    /**
     * CatBicDao
     */
    private CatBicDao catBicDao = null;

    /**
     * SicDetallDao
     */
    private SicDetalleDao sicDetalleDao = null;

    /**
     * SicEmisionDao
     */
    private SicEmisionDao sicEmisionDao = null;

    /**
     * UtilDivIntService
     */
    private UtilDivIntService utilDivIntService = null;

    /**
     * EstatusOperacionDao
     */
    private EstatusOperacionDao estatusOperacionDao = null;

    /**
     * CuponDao
     */
    private CuponDao cuponDao = null;

    /**
     * PosicionNombradaDao
     */
    private PosicionNombradaDao posicionNombradaDao = null;

    /**
     * PosicionNombradaHistDao
     */
    private PosicionNombradaHDao posicionNombradaHDao = null;

    /**
     * InstitucionDao
     */
    private InstitucionDao institucionDao = null;

    /**
     * CuentaNombradaDao
     */
    private CuentaNombradaDao cuentaNombradaDao = null;

    /**
     * EmisionDao
     */
    private EmisionDao emisionDao = null;

    /**
     * DepositosDivIntDao
     */
    private DepositosDivIntDao depositosDivIntDao = null;

    /**
     * RetirosDivIntDao
     */
    private RetirosDivIntDao retirosDivIntDao = null;

    /**
     * PosicionNombradaDivIntDao
     */
    private PosicionNombradaDivIntDao posicionNombradaDivIntDao = null;

    /**
     * TopeCirculanteDao
     */
    private TopeCirculanteDao topeCirculanteDao = null;

    /**
     * PosicionNombradaHistDivIntDao
     */
    private PosicionNombradaHDivIntDao posicionNombradaHDivIntDao = null;

    /**
     *
     */
    private EstatusEmisionesDao estatusEmisionesDao = null;

    private CalendarioEmisionesDeudaExtDao calendarioEmisionesDeudaExtDao;

    private JmsTemplate jmsTemplate;
    
	private ConciliacionIntDao conciliacionIntDao;
    
	private Map<String,String> derechosAutomatizadosDeudaMap;
	private String derechosAutomatizadosDeuda;
	
    private JmsTemplate jmsTemplateMoiConciliaciones;

    private JmsTemplate jmsTemplateSic;
    
    /**
     * Custodios que no envian por Swift, como los custodios MILA.
     */
    private Set<String> custodiosSinEnvioMensaje;
    
    /** Servicio para las operaciones SIC */
    private SicService sicService;

    /**
     * EmisionesConsultasDao
     */
    private EmisionesConsultasDao emisionesConsultasDao;

    /**
     * REmisionBovedaDao
     */
    private REmisionBovedaDao rEmisionBovedaDao;

    /**
     * BovedaService
     */
    private BovedaService bovedaService;
    
    private SignMensaje signMensaje;
   
    private ValidateSignatureService validateSignatureService;
    
    private SendMessageService sendMessageServiceSign;

    /**
     * EmisionDaoInt
     */
    private com.indeval.portalinternacional.persistence.dao.EmisionDao emisionDaoInt = null;

    /**
     * Dao del TipoBeneficiario
     */
    private TipoBeneficiarioDao tipoBeneficiarioDao;

    /**
     * Dao del CustodioTipoBenefDao
     */
    private CustodioTipoBenefDao custodioTipoBenefDao;
    
    /**
     * Dao Settlement Regime
     * @param jmsTemplateSic
     */
    private SettlementDisciplineRegimeDao settlementDisciplineRegimeDao;

	public void setJmsTemplateSic(JmsTemplate jmsTemplateSic) {
		this.jmsTemplateSic = jmsTemplateSic;
	}
	
	
	/**
     * @see com.indeval.portalinternacional.services.divisioninternacional.DivisionInternacionalService#actualizaOperacionSIC(OperacionSic[])
     */
	public int actualizaOperacionSIC(OperacionSic[] operacionSic) throws BusinessException {
	    log.info("\n\n####### DivisionInternacionalServiceImpl :: Entrando a actualizaOperacionSIC()...\n\n");
	    if (operacionSic.length == 0) {
	        throw new BusinessException(messageResolver.getMessage("J0047", (Object)"de operaciones para actualizar"));
	    }
	    int contadorOper = 0;
	    int contadorOperacionesNoEnviadas = 0;
	    for (int i = 0; i < operacionSic.length; i++) {
	        if (operacionSic[i].getIdOperacionesSic() == null) {
	            throw new BusinessException(messageResolver.getMessage("J0026", (Object)"el Id de la operacion - indice[" + 
                                                                       String.valueOf(i+1) + "]"));
	        }
	        //Valida la operacion
	        OperacionSic operacionActual = operacionSic[i];	        
	        operacionActual = this.operacionSicDao.findOperacionByIdOperacion(operacionActual.getIdOperacionesSic());
	        if (operacionActual == null) {
	            throw new BusinessException(this.messageResolver.getMessage("J0026", (Object)"la operacion -- Id[" + 
                     operacionSic[i].getIdOperacionesSic().toString() + "], " + "indice[" + String.valueOf(i+1) + "]"));
	        }
	        if (operacionActual.getEstatusOperacion() == null || operacionActual.getEstatusOperacion().getIdEstatusOperacion() == null) {
	            throw new BusinessException(this.messageResolver.getMessage("J0052"));
	        }
	        
//	        if(operacionActual.getVersion() == null) {
//	        	operacionActual.setVersion(0);
//	        }
	        
	        int oldVersion = operacionActual.getVersion().intValue();	        
	        //Se valida que la operacion sic que se desea liberar (y que sea recepcion) que continue en estado 
	        //confirmado = 5. Si no se encuentra en ese estado NO se envia mensaje a MOI y se continua con la 
	        //siguiente operacion. 
	        if (operacionSic[i].isLibero() && 
                (operacionActual.getTipoMensaje().equals(Constantes.MT_540) || 
                operacionActual.getTipoMensaje().equals(Constantes.MT_541))) {
	            List<Object> dataForComparation = this.operacionSicDao.getDataToCompareForUpdate(
                                                                           operacionActual.getIdOperacionesSic());
	            if (dataForComparation != null) {
	                Long estadoRealActual = (Long) dataForComparation.get(0);
	                Integer versionRealActual = (Integer) dataForComparation.get(1);
//                    versionRealActual = versionRealActual==null?0:versionRealActual;	            	
	                if (!(estadoRealActual.equals(Constantes.ST_OPER_CONFIRMADA))) {
	                	if (!(estadoRealActual.equals(Constantes.ST_OPER_CONFIRMADA_PARCIAL)) || versionRealActual.intValue() != oldVersion) {
		                    log.warn("\n\n####### Se intento liberar una operacion SIC que tiene un estado actual=[" +
		                             estadoRealActual + "] y no [5/Confirmada] / y no [18/Confirmada Parcial] o la version del registro actual=[" + versionRealActual.intValue() + "] " + 
		                             "no corresponde con la version anterior=[" + oldVersion + "]. Operacion con id=[" + 
		                             operacionActual.getIdOperacionesSic() + "] y folioControl=[" + 
		                             operacionActual.getFolioControl() + "].\n\n");
		                    contadorOperacionesNoEnviadas++;
		                    continue;
	                	}
	                } 
	            }
	        }
	        operacionActual.setVersion(oldVersion + 1);
	        this.sicService.actualizarOperacionSic(i, operacionSic[i], operacionActual, dateUtilService.getCurrentDate());
	        contadorOper++;
	    }
	    log.debug("\n\n####### Contador de operaciones enviadas=[" + contadorOper + "]\n\n");
	    log.debug("\n\n####### Contador de operaciones NO enviadas=[" + contadorOperacionesNoEnviadas + "]\n\n");

	    return contadorOper;
	}

    /**
     * @see com.indeval.portalinternacional.services.divisioninternacional.DivisionInternacionalService#insertaOperacionSIC(OperacionSic)
     */
    public void insertaOperacionSIC(OperacionSic operacionSic) throws BusinessException{

    	log.debug("DivisionInternacionalServiceImpl :: Entrando a insertaOperacionSIC()");

        validatorDivIntService.validaOperacionSicCaptura(operacionSic);

        operacionSic.setTipoMensaje(FactoryDivInt.obtieneTipoMensaje(operacionSic.getTipoOperacion(), operacionSic.getTipoTraspaso()));
        if (StringUtils.isBlank(operacionSic.getTipoMensaje()) || TLP_ENTREGA.equals(operacionSic.getTipoMensaje())
                || TLP_RECEPCION.equals(operacionSic.getTipoMensaje())) {
            operacionSic.setImporte(null);
            operacionSic.setDivisa(null);
        }
        operacionSic.setFechaAlta(dateUtilService.getCurrentDate());
        /*
         * 04/09/12
         * modificacion para insertar en bitacora de operaciones
         */
        setBitacora(operacionSic);
        
       /* if(operacionSicDao.save(operacionSic) == null){
            throw new BusinessException(messageResolver.getMessage("J0020", (Object)"OperacionSIC"));
        }*/

    }

    /**
     * @see com.indeval.portalinternacional.services.divisioninternacional.DivisionInternacionalService#businessRulesCapturaOperacion(OperacionSic)
     */
    public OperacionSic businessRulesCapturaOperacion(OperacionSic operacionSic) throws BusinessException {
    	log.info("Entrando a businessRulesCapturaOperacion()");

        validatorDivIntService.validaOperacionSicCaptura(operacionSic);

    	/* Obtiene la cuenta receptora a partir del tipo de cuenta SICP o SICT del agente firmado */
        operacionSic.setCuentaRecep(utilDivIntService.obtieneCuentaReceptora(operacionSic.getCuentaNombrada()));

    	/* Obtiene le tipo de mensaje */
    	operacionSic.setTipoMensaje(
                FactoryDivInt.obtieneTipoMensaje(operacionSic.getTipoOperacion(),
                        operacionSic.getTipoTraspaso()));
        validatorService.validarClaveTipo(operacionSic.getTipoMensaje(), "TipoMensaje");

        /* Valida el custodio por tipo de mensaje */
        CatBic catBic = (CatBic) catBicDao.getByPk(CatBic.class, operacionSic.getCatBic().getIdCatbic());
    	if (catBic == null
                || !validatorDivIntService.esCustodioValido(
                        operacionSic.getTipoMensaje(), catBic.getDetalleCustodio())) {
    	    throw new BusinessException(messageResolver.getMessage("J0012"));
    	}

        /* Inserta estatus de la operacion */
        operacionSic.setEstatusOperacion(new EstatusOperacion());
        if (TCP_RECEPCION.equalsIgnoreCase(operacionSic.getTipoMensaje().trim())) {
            operacionSic.getEstatusOperacion().setIdEstatusOperacion(ST_OPER_PENDIENTE_AUTORIZAR);
        }
        /* Modificacion 04/04/2012 se agrega condicion para estatus retenida dateUtilService.getCurrentDate()
         * Modificacion 21/06/2012 se quita condicion de fecha, todas las entregas se van a operacionesSic*/
        else if((TLP_ENTREGA.equalsIgnoreCase(operacionSic.getTipoMensaje().trim()) || 
        		TCP_ENTREGA.equalsIgnoreCase(operacionSic.getTipoMensaje().trim() ) ) ){
        	operacionSic.getEstatusOperacion().setIdEstatusOperacion(ST_OPER_RETENIDA);
        }
        
        else{
            operacionSic.getEstatusOperacion().setIdEstatusOperacion(ST_OPER_NOTIFICADA);
            
        }

    	/* Obtiene el folio control */
        operacionSic.setFolioControl(utilService.getFolio(seqFolioControl));

    	return operacionSic;

    }

    /**
     * @see com.indeval.portalinternacional.services.divisioninternacional.DivisionInternacionalService#businessRulesCapturaTraspaso(TraspasoLibrePagoVO)
     */
    public BigInteger businessRulesCapturaTraspaso(TraspasoLibrePagoVO tlpVO) throws BusinessException {

    	log.info("Entrando a businessRulesCapturaTraspaso()");

        /* Valida los datos de la operacion */
        validatorDivIntService.validaTraspasoLibrePagoVO(tlpVO);

    	/* Valida el traspaso */
    	StringBuilder error = new StringBuilder();
    	if (!validatorDivIntService.esTraspasoValido(
                new AgenteVO(
                        tlpVO.getIdFolioCtaTraspasante().substring(0, 2),
                        tlpVO.getIdFolioCtaTraspasante().substring(2, 5),
                        tlpVO.getIdFolioCtaTraspasante().substring(5)),
                new AgenteVO(
                        tlpVO.getIdFolioCtaReceptora().substring(0, 2),
                        tlpVO.getIdFolioCtaReceptora().substring(2, 5),
                        tlpVO.getIdFolioCtaReceptora().substring(5)),
                new EmisionVO(tlpVO.getTipoValor(),
                        tlpVO.getEmisora(),
                        tlpVO.getSerie(),
                        tlpVO.getCupon()),
                error)) {
    	    throw new BusinessException(messageResolver.getMessage("J0000", (Object)error.toString()));
    	}

    	/* Obtiene el folio control */
    	return utilService.getFolio(seqFolioControl);

    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#consultaOperaciones(OperacionSic, PaginaVO)
     */
    public PaginaVO consultaOperaciones(OperacionSic operacionSic, PaginaVO paginaVO,
    		Boolean debeDejarLog, Boolean obtenerTotalReg, Boolean rolSic) throws BusinessException {


        this.log.info("Entrando a consultaOperaciones()");
        final List operacionesSic = new ArrayList<OperacionSic>();
        /* Se validan los parametros */
        // validatorDivIntService.validaOperacionSicConsulta(operacionSic);
        /* Valida el objeto PaginaVO */
        paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);
        /* Realiza la consulta */
        paginaVO = this.operacionSicDao.findOperaciones(operacionSic, paginaVO, obtenerTotalReg);
        if (!this.validatorService.validaPagina(paginaVO)) {
            paginaVO.setRegistros(new ArrayList());
        } else {
            for (final Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
                final OperacionSic operSic = new OperacionSic((OperacionSicView) iter.next());
                /* Almacena indicadores de acciones sobre el registro */
                if (operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE_AUTORIZAR && rolSic
                		&& operSic.getDescTipoMensaje().equals(TCP_DESC_RECEPCION)) {
                    operSic.setAutoriza(true);
                }
                if ((operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CONFIRMADA) 
                		|| (operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CONFIRMADA_PARCIAL)) {
                    operSic.setLibera(true);
                    // Se verifica que si el custodio esta en la lista de custodio que no envian
                    // Swift (los de MILA para empezar) y
                    // y la operacion ya esta confirmada. Se habilita el boton de confirmar para que
                    // puedan actualizar el estado
                    // de manera manual
                    if (operSic.getCatBic() != null && this.custodiosSinEnvioMensaje != null
                            && this.custodiosSinEnvioMensaje.contains(operSic.getCatBic().getBicProd())) {
                        operSic.setRegresa(true);
                    }
                }
                /*
                 * se agrega condicion para que no habilite retenidas con fecha liquidacion anterior
                 * ala actual
                 */
                // se modifica para que pueda habilitar operaciones aun cuando la fecha de
                // liquidacion es anterior a la actual
                if (operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_RETENIDA) {
                    operSic.setHabilitar(true);
                }
                /*
                 * if(operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_RETENIDA &&
                 * !(operSic.getFechaLiquidacion().compareTo(dateUtilService.getFechaHoraCero(
                 * dateUtilService.getCurrentDate())) < 0 )){ operSic.setHabilitar(true); }
                 */

                // Se verifica que si el custodio esta en la lista de custodio que no envian Swift
                // (los de MILA para empezar) y
                // y la operacion ya esta enviada. Se habilita el boton de confirmar para que puedan
                // actualizar el estado
                // de manera manual
                if (operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_ENVIADA
                        && operSic.getCatBic() != null && this.custodiosSinEnvioMensaje != null
                        && this.custodiosSinEnvioMensaje.contains(operSic.getCatBic().getBicProd())) {
                    operSic.setConfirma(true);
                }
                if (operSic.getEstatusOperacion().getIdEstatusOperacion() != ST_OPER_LIBERADA && 
                		operSic.getEstatusOperacion().getIdEstatusOperacion() != ST_OPER_ENVIO_LIBERACION && 
                		operSic.getEstatusOperacion().getIdEstatusOperacion() != ST_OPER_MENSAJE_LIBERACION && 
                		operSic.getEstatusOperacion().getIdEstatusOperacion() != ST_OPER_PENDIENTE_LIBERAR && 
                		operSic.getEstatusOperacion().getIdEstatusOperacion() != ST_OPER_CANCELADA && 
                		operSic.getEstatusOperacion().getIdEstatusOperacion() != ST_OPER_ENVIO_CANCELACION && 
                		operSic.getEstatusOperacion().getIdEstatusOperacion() != ST_OPER_CANCEL_SIST &&
                		ST_OPER_CANCEL_USR != operSic.getEstatusOperacion().getIdEstatusOperacion() &&
                	    ST_OPER_EN_RECHAZO != operSic.getEstatusOperacion().getIdEstatusOperacion() &&
                	    ST_OPER_RECHAZADA != operSic.getEstatusOperacion().getIdEstatusOperacion() &&
                	    operSic.getEstatusOperacion().getIdEstatusOperacion() != ST_OPER_LIBERADA_PARCIAL) {
                    operSic.setCancela(true);
                }

                /*
                 * Si es una operacion de cambio de boveda y ademas es una operacion cancelada, esto quiere
                 * decir que se cancelo por SwiftDali por instruccion de custodio, solamente se puede realizar
                 * un Regreso de Posicion; para que MOI la procese y la ponga en el estatus final actual que es
                 * "CANCEL-USR".
                 */
                if (operSic.getCambioBoveda().equals(BigInteger.ONE) && 
                    operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CANCELADA) {
                    operSic.setRegresaPosicion(true);
                    operSic.setCancela(false);
                }

                operacionesSic.add(operSic);
            }
        }

        paginaVO.setRegistros(operacionesSic);

        return paginaVO;
    }
    
    /**
   	 * Servicio que realiza la consulta si el rol es Autorizador
   	 * @author Marco Edgar Valencia Arana, KODE
   	 * @param foliosControl lista de los folios control a buscar en t_bitacora_operaciones_sic
   	 * @param paginaVO la pagina
   	 */
    public PaginaVO consultaOperacionesPorFoliosControlAutorizador(List<BigInteger> foliosControl, PaginaVO paginaVO) throws BusinessException {
    	log.debug("DivisionInternacionalServiceImpl :: consultaOperacionesPorFoliosControlAutorizador");
    	List<BitacoraOperacionesSic> bitacorasEncontradas = new ArrayList<BitacoraOperacionesSic>();
    	List<BigInteger> foliosControlPorAutorizar = new ArrayList<BigInteger>();
    	 /* Valida el objeto PaginaVO */
        paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);      
    	bitacorasEncontradas = operacionSicDao.obtieneBitacoraOperacionSic(foliosControl);      
        for (BitacoraOperacionesSic bitOperSic : bitacorasEncontradas) 
			foliosControlPorAutorizar.add(bitOperSic.getFolioControl());
		
        /* Realiza la consulta */
        if(!foliosControlPorAutorizar.isEmpty())
        	paginaVO = operacionSicDao.findOperacionesPorListaDeFolioControl(foliosControlPorAutorizar, paginaVO);
        else
        	paginaVO.setRegistros(new ArrayList());

        if (!validatorService.validaPagina(paginaVO)) {
            paginaVO.setRegistros(new ArrayList());
        }
        else {
        	 for (Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
                 OperacionSic operSic = (OperacionSic) iter.next();

                 //Julio2017 - Se verifica que unicamente se operen las operaciones que no son de Cambio de Boveda. 
//                 if (operSic.getCambioBoveda().equals(BigInteger.ZERO)) {

                     /* Almacena tipo mensaje de la operacion */
                     if (TCP_ENTREGA.equalsIgnoreCase(operSic.getTipoMensaje())) {
                         operSic.setDescTipoMensaje("ENTREGA CONTRA PAGO");
                     }
                     else if (TLP_ENTREGA.equalsIgnoreCase(operSic.getTipoMensaje())) {
                         operSic.setDescTipoMensaje("ENTREGA LIBRE PAGO");
                     }
                     else if (TCP_RECEPCION.equalsIgnoreCase(operSic.getTipoMensaje())) {
                         operSic.setDescTipoMensaje("RECEPCION CONTRA PAGO");
                     }
                     else if (TLP_RECEPCION.equalsIgnoreCase(operSic.getTipoMensaje())) {
                         operSic.setDescTipoMensaje("RECEPCION LIBRE PAGO");
                     }
                     /* Obtiene el cupon vigente de la operacion */
                     for (BitacoraOperacionesSic bitOperSic : bitacorasEncontradas) 
                         if(bitOperSic.getFolioControl().equals(operSic.getFolioControl()))						
                             operSic.setBitacoraOperacionesSic(bitOperSic);							


                     try {
                         Cupon cupon = cuponDao.findCuponByIdEmision(operSic.getEmision().getIdEmision());
                         operSic.setClaveCupon(cupon != null ? cupon.getClaveCupon() : null);
                     } catch(RuntimeException re) {
                         log.error("Error al obtener Cupon",re);
                         operSic.setClaveCupon("");
                     }

//                 }
        	 }
        }
    	return paginaVO;
    }
    
    
    /**
     * Servicio que realiza la busqueda de folios en t operaciones si el rol es operador
     * @param foliosControl
     * @param paginaVO     
     * @author KODE-PC21 Marco Edgar Valencia, KODE
     * @return la pagina VO
     */    
    public PaginaVO consultaOperacionesPorFoliosControl(List<BigInteger> foliosControl, PaginaVO paginaVO) throws BusinessException {
        log.info("####### Entrando a consultaOperacionesPorFoliosControl()...");
        List<BitacoraOperacionesSic> bitacorasEncontradas = new ArrayList<BitacoraOperacionesSic>();
        /* Valida el objeto PaginaVO */
        paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);

        /* Realiza la consulta */
        paginaVO = operacionSicDao.findOperacionesPorListaDeFolioControl(foliosControl, paginaVO);

        if (!validatorService.validaPagina(paginaVO)) {
            paginaVO.setRegistros(new ArrayList());
        }
        else {
        	bitacorasEncontradas = operacionSicDao.obtieneBitacoraOperacionSic(foliosControl);
        	boolean existenRegistroBitacora = !bitacorasEncontradas.isEmpty() ? true : false; 
            for (Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
                OperacionSic operSic = (OperacionSic) iter.next();

                //Julio2017 - Se verifica que unicamente se operen las operaciones que no son de Cambio de Boveda.
//                if (operSic.getCambioBoveda().equals(BigInteger.ZERO)) {

                    /* Almacena tipo mensaje de la operacion */
                    if (TCP_ENTREGA.equalsIgnoreCase(operSic.getTipoMensaje())) {
                        operSic.setDescTipoMensaje("ENTREGA CONTRA PAGO");
                    }
                    else if (TLP_ENTREGA.equalsIgnoreCase(operSic.getTipoMensaje())) {
                        operSic.setDescTipoMensaje("ENTREGA LIBRE PAGO");
                    }
                    else if (TCP_RECEPCION.equalsIgnoreCase(operSic.getTipoMensaje())) {
                        operSic.setDescTipoMensaje("RECEPCION CONTRA PAGO");
                    }
                    else if (TLP_RECEPCION.equalsIgnoreCase(operSic.getTipoMensaje())) {
                        operSic.setDescTipoMensaje("RECEPCION LIBRE PAGO");
                    }

                    operSic.setCancelo(true);

                    /*
                    if(operSic.getEstatusOperacion().getIdEstatusOperacion().equals(new Long(3)) || 
                		operSic.getEstatusOperacion().getIdEstatusOperacion().equals(new Long(4))){
                	    operSic.setHabilitar(true);
                	    operSic.setEstado("-1");
                    }*/

                    // revisa si existen bitacoras y la relaciona con el folio control
                    if(existenRegistroBitacora){
                        for (BitacoraOperacionesSic bitOperSic : bitacorasEncontradas) 
                            if(bitOperSic.getFolioControl().equals(operSic.getFolioControl())){
                                operSic.setEstado(bitOperSic.getIdEstatusOperacionNuevo().toString());
                                operSic.setCancelo(false);
                                operSic.setBitacoraOperacionesSic(bitOperSic);							
                            }					
                    }

                    /* Obtiene el cupon vigente de la operacion */
                    try {
                        Cupon cupon = cuponDao.findCuponByIdEmision(operSic.getEmision().getIdEmision());
                        operSic.setClaveCupon(cupon != null ? cupon.getClaveCupon() : null);
                    } catch(RuntimeException re) {
                        log.error("Error al obtener Cupon",re);
                        operSic.setClaveCupon("");
                    }
                    
//                }
            }
        }

        return paginaVO;
    }
    
    /**
   	 * Servicio que persiste los cambios de estado de la operacion para el rol de operador
   	 * 
   	 * @author Marco Edgar Valencia Arana, KODE
   	 * @param bitacoraInsert lista de bitacoras a insertar
   	 * @param bitacoraUpdate lista de bitacoras a actualizar
   	 * @return Lista de folios actualzados correctamente
   	 * 
   	 */
    public List<String> insertaCambioEstatusOperador(List<BitacoraOperacionesSic> bitacoraInsert,List<BitacoraOperacionesSic> bitacoraUpdate) throws BusinessException{
    	List<String> foliosActualizados = new ArrayList<String>();
    	for (BitacoraOperacionesSic bitacoraSave : bitacoraInsert) {
			operacionSicDao.save(bitacoraSave);
			foliosActualizados.add(bitacoraSave.getFolioControl().toString());
		}
		for (BitacoraOperacionesSic bitacoraUp : bitacoraUpdate) {
			operacionSicDao.update(bitacoraUp);
			foliosActualizados.add(bitacoraUp.getFolioControl().toString());
		}
		return foliosActualizados;		
    }
    

    /**
   	 * Servicio que persiste los folios cancelados
   	 * 
   	 * @author Marco Edgar Valencia Arana, KODE
   	 * @param bitacorasCanceladas lista de bitacoras a cancelar
   	 * @return la lista de folios que se cancelaron correctamente
   	 */
    public List<String> cancelaSolicitudBitacoraSic(List<BitacoraOperacionesSic> bitacorasCanceladas) throws BusinessException{
    	log.debug("### DivisionInternacionalServiceImpl :: cancelaSolicitudBitacoraSic");
    	List<String> bitacorasCanceladasCorrectamente = new ArrayList<String>();
    	int count = 1;
    	for (BitacoraOperacionesSic bitacoraOperacionesSic : bitacorasCanceladas) {
				operacionSicDao.update(bitacoraOperacionesSic);
				bitacorasCanceladasCorrectamente.add(bitacoraOperacionesSic.getFolioControl().toString());
				count++;
		}
    	return bitacorasCanceladasCorrectamente;
    }
    
    /**
   	 * Servicio que actualiza el motivo de cambio
   	 * 
   	 * @author Marco Edgar Valencia Arana, KODE
   	 * @param bitacorasCanceladas lista de bitacoras a cancelar
   	 * @return la lista de folios que se cancelaron correctamente
   	 */
    public List<String> actualizaMotivoCambioBitacoraSic(List<BitacoraOperacionesSic> motivosActualizadosLst) throws BusinessException{
    	List<String> bitacorasActualizadasCorrectamente = new ArrayList<String>();
    	for (BitacoraOperacionesSic bitacoraOperacionesSic : motivosActualizadosLst) {			
				operacionSicDao.update(bitacoraOperacionesSic);
				bitacorasActualizadasCorrectamente.add(bitacoraOperacionesSic.getFolioControl().toString());			
		}
    	return bitacorasActualizadasCorrectamente;
    }

    /**
     * Previo a realizar el cambio de estado en la operacion SIC y enviar el mensaje de cambio de 
     * estado a MOI, se valida si se trata de un cambio de estado de 8 (PENDIENTE LIBERAR) o de 
     * 9 (LIBERADA) a otro nuevo estado. Esto por la resolucion del incidente 1187130 que incluyo 
     * un control para evitar la duplicidad de las liberaciones.
     * @param operacionSic La operacion SIC verificar.
     */
    private void verificarEstadoPrevioDeLiberacion(OperacionSic operacionSic) {
        log.info("\n\n####### Entrando a verificarEstadoPrevioDeLiberacion()...\n\n");

        boolean eliminaConstraint = false;

        if (operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionAnterior().intValue() == ST_OPER_LIBERADA) {
            if (!(operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().intValue() == ST_OPER_CANCELADA) && 
                !(operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().intValue() == ST_OPER_CANCEL_SIST) &&
                !(operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().intValue() == ST_OPER_CANCEL_USR)) {
                eliminaConstraint = true;
            }
        }
        else if (operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionAnterior().intValue() == ST_OPER_PENDIENTE_LIBERAR) {
            if (!(operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().intValue() == ST_OPER_LIBERADA) && 
                !(operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().intValue() == ST_OPER_CANCELADA) &&
                !(operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().intValue() == ST_OPER_CANCEL_SIST) &&
                !(operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().intValue() == ST_OPER_CANCEL_USR)) {
                    eliminaConstraint = true;
                }
        }
        else if((operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionAnterior().intValue() == ST_OPER_CANCEL_SIST
        		|| operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionAnterior().intValue() == ST_OPER_CANCEL_USR)
        		&& (!(operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().intValue() == ST_OPER_LIBERADA)
        				|| !(operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().intValue() == ST_OPER_PENDIENTE_LIBERAR))){
            // Si esl estatus anterior es: cancel sist o cancel usr, Y el estatus nuevo NO es liberada o pendiente de liberar
        	eliminaConstraint = true;
        }
        else if (operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionAnterior().intValue() == ST_OPER_PENDIENTE_LIBERAR_PRCIAL) {
            if (!(operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().intValue() == ST_OPER_LIBERADA_PARCIAL)) {
                    eliminaConstraint = true;
                }
        }
        if (eliminaConstraint) {
            try {
               this.operacionSicDao.eliminarRegistroControlLiberacionRecepciones(operacionSic.getIdOperacionesSic().longValue(), 
                                                                                 operacionSic.getFolioControl().longValue());
            } catch (DataAccessException daex) {
                daex.printStackTrace();
                if (daex.getMessage() != null) {
                    log.error(daex.getMessage());
                }
                if (daex.getCause() != null) {
                    log.error(daex.getCause().getMessage());
                }
                String mensajeError = 
                        "Error de aceso de datos al intentar eliminar un registro de la tabla T_CONTROL_LIBERACION_INT =[" + 
                        "idOperacionSic=" + operacionSic.getIdOperacionesSic().toString() + "|" + 
                        "folioControl=" + operacionSic.getFolioControl().toString() + "]";
                log.error("\n\n####### " + mensajeError + "\n\n");
                throw new BusinessException(mensajeError);
            } catch (Exception ex) {
                ex.printStackTrace();
                if (ex.getMessage() != null) {
                    log.error(ex.getMessage());
                }
                if (ex.getCause() != null) {
                    log.error(ex.getCause().getMessage());
                }
                String mensajeError = 
                        "Error al intentar eliminar un registro de la tabla T_CONTROL_LIBERACION_INT =[" + 
                        "idOperacionSic=" + operacionSic.getIdOperacionesSic().toString() + "|" + 
                        "folioControl=" + operacionSic.getFolioControl().toString() + "]";
                log.error("\n\n####### " + mensajeError + "\n\n");
                throw new BusinessException(mensajeError);
            }
        }
    }

    /**
   	 * Servicio que actualiza el estado nuevo de la operacion
   	 * 
   	 * @author Marco Edgar Valencia Arana, KODE
   	 * @param operacionSic lista de operaciones a actualizar estado
   	 * @return contador
   	 */
    public void actualizaOperacionSICAutorizador(List<OperacionSic> operacionesSic,String autorizo) throws BusinessException {
        log.debug("### DivisionInternacionalServiceImpl :: actualizaOperacionSICAutorizador"); 
        XStream xstream = new XStream();
		Annotations.configureAliases(xstream, EstadoMensajeSic.class);
		EstadoMensajeSic msg = new EstadoMensajeSic();
		msg.setAccion(EstadoMensajeSic.TipoAccion.CAMBIO_ESTADO);
		
        if (operacionesSic.isEmpty()) 
            throw new BusinessException(messageResolver.getMessage("J0047", (Object)"de operaciones para actualizar"));        

        for (OperacionSic operacionSic : operacionesSic) {
        	if (operacionSic.getIdOperacionesSic() == null) {
                throw new BusinessException(
                        messageResolver.getMessage("J0026", (Object)"el Id de la operacion - Folio[" + operacionSic.getFolioControl().toString() + "]"));
            }
            OperacionSic operacionActual= operacionSic;                     
            operacionActual = operacionSicDao.findOperacionByIdOperacion(operacionActual.getIdOperacionesSic());
            if (operacionActual == null) {
                throw new BusinessException(
                        messageResolver.getMessage("J0026",
                                (Object)"la operacion -- Id[" + operacionSic.getIdOperacionesSic().toString() + "], " +
                                                "Folio[" + operacionSic.getFolioControl().toString() + "]"));
            }
            if (operacionActual.getEstatusOperacion() == null
                    || operacionActual.getEstatusOperacion().getIdEstatusOperacion() == null) {
                throw new BusinessException(messageResolver.getMessage("J0052"));
            }            

            //boolean isConMensaje = operacionSic.isConMensaje();
            boolean isEnvioMensaje548 = true;

            this.verificarEstadoPrevioDeLiberacion(operacionSic);

			switch (operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().intValue()) {
				case 2: // Notificada
					operacionActual.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, ST_OPER_NOTIFICADA));
	            	operacionActual.setFechaNotificacion(dateUtilService.getCurrentDate());              
	                /*if (isConMensaje) {
	                	operacionActual.setMensajeEfectivo("C210");
	                }*/
	                msg.setEstado(EstadoMensajeSic.TipoEstado.AUTORIZADO);
	                if (!TCP_RECEPCION.equalsIgnoreCase(operacionActual.getTipoMensaje()))
	                	isEnvioMensaje548 = false;
					break;
				case 3: // Pendiente
					operacionActual.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().longValue()));
					if(operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_LIBERADA  || operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE_LIBERAR)
						if (TCP_ENTREGA.equalsIgnoreCase(operacionActual.getTipoMensaje()) || TLP_ENTREGA.equalsIgnoreCase(operacionActual.getTipoMensaje()))
							msg.setEstado(EstadoMensajeSic.TipoEstado.PENDIENTE_REGRESO);
					break;
				case 4: // Enviada
					//Cuando aprietan con mensaje esto no es correcto ya que no se envia ninguno
	            	//Se manda un error
	            	/*if(isConMensaje){
	            		throw new BusinessException(
		                        messageResolver.getMessage("J0111",
		                                "la operacion -- Id[" + operacionActual.getIdOperacionesSic().toString() + "], " +
		                                                "Folio[" + operacionActual.getFolioControl().toString() + "]"));
	            	}
	            	else{*/
	            		operacionActual.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, ST_OPER_ENVIADA));
	                	operacionActual.setFecha530(null);					
						msg.setEstado(EstadoMensajeSic.TipoEstado.CONFIRMADO);
						if(operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_LIBERADA  || operacionSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE_LIBERAR)
							if (TCP_ENTREGA.equalsIgnoreCase(operacionActual.getTipoMensaje()) || TLP_ENTREGA.equalsIgnoreCase(operacionActual.getTipoMensaje()))
								msg.setEstado(EstadoMensajeSic.TipoEstado.PENDIENTE_REGRESO);
						
	            	//}			
					break;
				case 5: // Confirmada
					//Cuando aprietan con mensaje esto no es correcto ya que no se envia ninguno
	            	//Se manda un error
	            	/*if(isConMensaje){
	            		throw new BusinessException(
		                        messageResolver.getMessage("J0110",
		                                "la operacion -- Id[" + operacionActual.getIdOperacionesSic().toString() + "], " +
		                                		 "Folio[" + operacionActual.getFolioControl().toString() + "]"));
	            	}
	            	else{*/
	            		operacionActual.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, ST_OPER_CONFIRMADA));
	                	operacionActual.setFecha530(dateUtilService.getCurrentDate());					
						msg.setEstado(EstadoMensajeSic.TipoEstado.CONFIRMADO);    
	            	//}
					break;
				case 8: // Pendiente de liberar
					//TODO Verificar si el cupon es vigente y si no lo es, settear el vigente
	            	operacionActual.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, ST_OPER_PENDIENTE_LIBERAR));
	            	operacionActual.setFechaHora(dateUtilService.getCurrentDate());               
	                /*if (isConMensaje) {
	                	operacionActual.setMensajeEfectivo("C103");
	                }
	                // Graba la operacion en t_bitacora_operaciones para el envio al MAV 
	            	//                setBitacora(operacion); ahora solo se envia el cambio de estado*/
	                msg.setEstado(EstadoMensajeSic.TipoEstado.LIBERAR);
					break;
                                case 9: // Liberada
                                        operacionActual.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().longValue()));
					operacionSic.setStPfi548("LIQ");
                                        operacionSic.setFechaHora(new Date());
                                        operacionSic.setFechaCambio(new Date());
                                        operacionSic.setRefMsjPfi("LIQUIDADA");
                                        isEnvioMensaje548 = false;
					break;
				case 10: // Envio cancelacion
					operacionActual.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, ST_OPER_ENVIO_CANCELACION));
	            	operacionActual.setFecha592(dateUtilService.getCurrentDate());               
	                msg.setEstado(EstadoMensajeSic.TipoEstado.CANCELADO);
					break;
				case 11: // Cancelada
					//if (isConMensaje) {                	
	                	operacionActual.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, ST_OPER_CANCELADA));
	                 	operacionActual.setFecha592(dateUtilService.getCurrentDate());  
	                	operacionActual.setMensajeEfectivo(null); 
	                //}
					msg.setEstado(EstadoMensajeSic.TipoEstado.CANCELADO);
					break;
				case 13: // Habilitada
					operacionActual.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, ST_OPER_HABILITADA));
	        		/** se agrega la fecha en que se habilita la operacion*/
	        		operacionActual.setFechaHoraHabilitada(dateUtilService.getCurrentDate());
	        		operacionActual.setFechaHora(dateUtilService.getCurrentDate());
	        		//msg.setEstado(EstadoMensajeSic.TipoEstado.HABILITAR);xdfg
	        		isEnvioMensaje548 = false;   
	        		/*if (TCP_RECEPCION.equalsIgnoreCase(operacionActual.getTipoMensaje())){
	        			isEnvioMensaje548 = true; 
	        			msg.setEstado(EstadoMensajeSic.TipoEstado.HABILITAR);
	        		}   */
	        		if (TCP_ENTREGA.equalsIgnoreCase(operacionActual.getTipoMensaje()) || TLP_ENTREGA.equalsIgnoreCase(operacionActual.getTipoMensaje())){
						isEnvioMensaje548 = true; 
	        			msg.setEstado(EstadoMensajeSic.TipoEstado.HABILITAR);
					}					
					break;
				case 14: // Cancel-Sist
					operacionActual.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, ST_OPER_CANCEL_SIST));
					operacionActual.setFecha592(dateUtilService.getCurrentDate());
					msg.setEstado(EstadoMensajeSic.TipoEstado.CANCEL_SIST);
					break;
				default:
					// se realiza el update del estado
					// El cambio no aplica para las parcialidades
					if(operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo() == Constantes.ST_OPER_CONFIRMADA_PARCIAL 
							|| operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo() == Constantes.ST_OPER_LIBERADA_PARCIAL
							|| operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo() == Constantes.ST_OPER_REMANENTE_CANCELADO
							|| operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo() == Constantes.ST_OPER_PENDIENTE_LIBERAR_PRCIAL
							|| operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo() == Constantes.ST_OPER_ENVIO_CANCELACION_REMANENTE){
						log.debug("DivisionInternacionalServiceImpl :: El cambio de estatus no aplica para operaciones parciales.");
					} else {
						operacionActual.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().longValue()));
						//msg.setEstado(EstadoMensajeSic.TipoEstado.CANCEL_SIST); // falta saber que tipo de estado se asigna dependiendo del estatus nuevo en caso de llevar mensaje
						isEnvioMensaje548 = false;
						break;
					}
			}			
			msg.setFechaLiquidacion(DateUtils.format(operacionActual.getFechaLiquidacion(), "yyyy-MM-dd HH:mm:ss") );
            msg.setFolioControl(operacionActual.getFolioControl().toString());
            msg.setId(String.valueOf(operacionActual.getIdOperacionesSic())); 
			
			operacionActual.setRefMsjPfi(null);
            operacionActual.setStPfi548(null);
			
			
        	operacionSicDao.update(operacionActual);   
        	BitacoraOperacionesSic bitacora = operacionSic.getBitacoraOperacionesSic();
        	bitacora.setIdEstatusBitacora(new Long(3));
        	bitacora.setFechaSolicitudAutorizador(new Date());
        	bitacora.setCveUsuarioAutorizo(autorizo);
        	operacionSicDao.update(bitacora);		
            	
			
            //Evitamos mandar el mensaje para cuando solamente se regresa de confirmada a enviada
            //crea El mensaje
        	if(isEnvioMensaje548){
        		final String msgTXT = xstream.toXML(msg);
			 	
			 	String msgFirmado = msgTXT;
			 	try {
					if(validateSignatureService.isSignature()) {
						msgFirmado = signMensaje.signMensaje(msgFirmado);
						log.info(Constante.loggerSignature(false, Constante.PORTAL_INTERNAIONAL, Constante.MOI, msgFirmado));
					}
			 	
				 	final String msgToSend = msgFirmado;
				 	
	    			jmsTemplateSic.send(new MessageCreator() {
	    				public Message createMessage(Session session)
	    						throws JMSException {
	    					final Message msgSession = session
	    							.createTextMessage(msgToSend);
	    					return msgSession;
	    				}
	    			}); 
				} catch (DigitalSignException e) {
					log.error(e.getMessage(), e.getCause());
		            ErrorSeguridadMensajeriaVo error = new ErrorSeguridadMensajeriaVo();
		            error.setError(e.getMessage().split("\\|")[0].trim());
		            error.setModulo(Constante.PORTAL_INTERNAIONAL);
		            error.setXml(msgFirmado);
		            
		            Document doc = XMLUtils.convertStringToDocument(msgFirmado);
		            error.setNumeroSerieCertificado(e.getMessage().split("\\|")[1].trim());
		            error.setTipoMensaje(XMLUtils.getStringToElement(doc, Constante.TIPO_MENSAJE));
		            
		            sendMessageServiceSign.sendQueueAlert(error);
				}
        	} 
		}      
    }
    
    /**
   	 * Servicio que actualiza el estado nuevo de la operacion
   	 * 
   	 * @author Marco Edgar Valencia Arana, KODE
   	 * @param operacionSic lista de operaciones a actualizar estado
   	 * @return contador
   	 */
    public void actualizaOperacionSICAutorizadorParcialidades(List<LiquidacionParcialMoi> listaLiquidacionesParciales,String autorizo,
    		OperacionSic operacionSic) throws BusinessException {
    	log.debug("### DivisionInternacionalServiceImpl :: actualizaOperacionSICAutorizador");
        XStream xstream = new XStream();
		Annotations.configureAliases(xstream, EstadoMensajeSic.class);
		EstadoMensajeSic msg = new EstadoMensajeSic();
		msg.setAccion(EstadoMensajeSic.TipoAccion.CAMBIO_ESTADO);
		
        if (listaLiquidacionesParciales.isEmpty()) 
            throw new BusinessException(messageResolver.getMessage("J0047", (Object)"de operaciones para actualizar"));    
        
	        OperacionSic operacionActual = operacionSic;                     
	        operacionActual = operacionSicDao.findOperacionByIdOperacion(operacionActual.getIdOperacionesSic());
	        if (operacionActual == null) {
	            throw new BusinessException(
	                    messageResolver.getMessage("J0026",
	                            (Object)"la operacion -- Id[" + operacionSic.getIdOperacionesSic().toString() + "], " +
	                                            "Folio[" + operacionSic.getFolioControl().toString() + "]"));
	        }
	        if (operacionActual.getEstatusOperacion() == null
	                || operacionActual.getEstatusOperacion().getIdEstatusOperacion() == null) {
	            throw new BusinessException(messageResolver.getMessage("J0052"));
	        } 

	        this.verificarEstadoPrevioDeLiberacion(operacionSic);
	        
	        for (LiquidacionParcialMoi liquidacionParcial : listaLiquidacionesParciales) {
				switch (liquidacionParcial.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().intValue()) {
				case 14: // PENDIENTE LIBERAR PARCIAL
						liquidacionParcial.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, ST_OPER_CANCEL_SIST));
						liquidacionParcial.setFechaConfirmacion(dateUtilService.getCurrentDate());
						liquidacionParcial.setFolioControlLiquidacion(null);
						liquidacionParcial.setIdInstruccionLiquidacion(null);
						liquidacionParcial.setFechaLiquidacion(null);
	//					msg.setEstado(EstadoMensajeSic.TipoEstado.LIBERAR_PARCIAL);
						operacionActual.setFechaHora(dateUtilService.getCurrentDate());
					break;
					case 18: // Confirmada Parcial
						liquidacionParcial.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, ST_OPER_CONFIRMADA_PARCIAL));
						liquidacionParcial.setFechaConfirmacion(dateUtilService.getCurrentDate());
						liquidacionParcial.setFolioControlLiquidacion(null);
						liquidacionParcial.setIdInstruccionLiquidacion(null);
						liquidacionParcial.setFechaLiquidacion(null);
	//					msg.setEstado(EstadoMensajeSic.TipoEstado.CONFIRMADA_PARCIAL);
						operacionActual.setFecha530(dateUtilService.getCurrentDate());	
						break;
					case 19: // Liberada Parcial
						liquidacionParcial.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, ST_OPER_LIBERADA_PARCIAL));
						liquidacionParcial.setFechaConfirmacion(dateUtilService.getCurrentDate());
						liquidacionParcial.setFolioControlLiquidacion(null);
						liquidacionParcial.setIdInstruccionLiquidacion(null);
						liquidacionParcial.setFechaLiquidacion(null);
						msg.setEstado(EstadoMensajeSic.TipoEstado.LIBERAR_PARCIAL);
						operacionActual.setFechaHora(new Date());
						break;
					case 22: // PENDIENTE LIBERAR PARCIAL
						liquidacionParcial.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, ST_OPER_PENDIENTE_LIBERAR_PRCIAL));
						liquidacionParcial.setFechaConfirmacion(dateUtilService.getCurrentDate());
						liquidacionParcial.setFolioControlLiquidacion(null);
						liquidacionParcial.setIdInstruccionLiquidacion(null);
						liquidacionParcial.setFechaLiquidacion(null);
	//					msg.setEstado(EstadoMensajeSic.TipoEstado.LIBERAR_PARCIAL);
						operacionActual.setFechaHora(dateUtilService.getCurrentDate());
						break;
			}
	        settlementDisciplineRegimeDao.actualizaCambioStatusLiquidacionesParciales(liquidacionParcial);
	    	BitacoraOperacionesSic bitacora = liquidacionParcial.getBitacoraOperacionesSic();
	    	bitacora.setIdEstatusBitacora(new Long(3));
	    	bitacora.setFechaSolicitudAutorizador(new Date());
	    	bitacora.setCveUsuarioAutorizo(autorizo);
	    	bitacora.setNumeroLiquidacion(liquidacionParcial.getNumeroLiquidacion());
	    	operacionSicDao.update(bitacora);
	    }
	        
	    List<LiquidacionParcialMoi> lstLiquidacionParcialMoi = getLiqParcialMoi(operacionSic.getFolioControl().longValue());
	    Long idEstatusParcialidad = getEstatusOperacionSicOfParcialidad(lstLiquidacionParcialMoi);
		operacionActual.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, idEstatusParcialidad));
		
		if(idEstatusParcialidad == ST_OPER_CONFIRMADA_PARCIAL) {
			operacionActual.setFecha530(new Date());
		} else if(idEstatusParcialidad == ST_OPER_LIBERADA_PARCIAL || idEstatusParcialidad == ST_OPER_PENDIENTE_LIBERAR_PRCIAL){
			operacionActual.setFechaHora(new Date());
		}
		
		operacionSicDao.update(operacionActual);
    }

    /*
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionlService#obtieneHistorialBitacoraSIC(HistorialBitacoraOperacionesSICDTO,PaginaVO,boolean)
     */
    public PaginaVO obtieneHistorialBitacoraSIC(HistorialBitacoraOperacionesSICDTO hist, PaginaVO paginaVO, boolean seAplicaCambioDeBoveda) throws BusinessException {
    	 log.info("\n\n####### Entrando a obtieneHistorialBitacoraSIC()...");

    	 try {
    	     List<VBitacoraOperacionesSic> historialBitacoras = new ArrayList<VBitacoraOperacionesSic>();        
    	     paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);

    	     paginaVO = operacionSicDao.obtenerHistorialBitacoraOperacionesSic(hist, paginaVO, seAplicaCambioDeBoveda);

    	     if (!validatorService.validaPagina(paginaVO)) {
    	         paginaVO.setRegistros(new ArrayList());
    	     }
    	     return paginaVO;
    	 } catch (Exception e) {
             e.printStackTrace();
             throw new BusinessException("Error al obtener los datos hist\u00F3ricos.");
         }
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#obtieneCustodios(com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO)
     */
    public CatBic[] obtieneCustodios(EmisionVO emisionVO) throws BusinessException {
        log.info("DivisionInternacionalServiceImpl :: Entrando a obtieneCustodios()");

        //Valida la emision
        validatorService.validaDTONoNulo(emisionVO, " emsion ");
        if (!emisionVO.tienePKValida() && StringUtils.isBlank(emisionVO.getIsin())) {
            throw new BusinessException("No existe la PK o el isin de la emision.");
        }
        Emision emision = emisionDao.findEmisionLiberada(emisionVO);
        if (!(emision != null ? Boolean.TRUE : Boolean.FALSE)) {
            throw new BusinessException(messageResolver.getMessage("J0001"));
        }

        List<SicEmision> listaEmisiones = sicEmisionDao.findSicEmisionesByEmision(emisionVO);
        if (listaEmisiones == null || listaEmisiones.isEmpty()) {
            return (emptyCatBicArray);
        }
        List<CatBic> listaCatBic = catBicDao.findCatBic(listaEmisiones);
        if (listaCatBic == null || listaCatBic.isEmpty()) {
            throw new BusinessException(messageResolver.getMessage("J0046", (Object)"de custodios"));
        }
        return listaCatBic.toArray(emptyCatBicArray);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#obtieneCustodioEnBaseAEmision(com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO)
     */
    public CatBic[] obtieneCustodioEnBaseAEmision(EmisionVO emisionVO) throws BusinessException {
        log.info("\n\n####### Entrando a obtieneCustodioEnBaseAEmision()...");

        //Valida la emision
        this.validatorService.validaDTONoNulo(emisionVO, " emsion ");
        if (!emisionVO.tienePKValida() && StringUtils.isBlank(emisionVO.getIsin())) {
            throw new BusinessException("No existe la PK o el isin de la emision.");
        }

        Emision emision = this.emisionDao.findEmisionLiberada(emisionVO);
        if (emision == null) {
            throw new BusinessException(this.messageResolver.getMessage("J0001"));
        }

        List<SicEmision> listaEmisiones = this.sicEmisionDao.findSicEmisionesByEmision(emisionVO);
        if (listaEmisiones == null || listaEmisiones.isEmpty()) {
            return (emptyCatBicArray);
        }

        log.info("\n\n####### id emision = [" + emision.getIdEmision() + "]");
        List<CatBic> listaCatBic = this.catBicDao.findCatBicEnBaseAEmision(emision);
        if (listaCatBic == null || listaCatBic.isEmpty()) {
            throw new BusinessException(this.messageResolver.getMessage("J0046", (Object)"de custodios"));
        }

        return listaCatBic.toArray(emptyCatBicArray);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#obtieneDepositantes(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO)
     */
    public SicDetalle[] obtieneDepositantes(CatBic catBic) throws BusinessException {

        log.info("Entrando a obtieneDepositantes()");

        if (catBic == null) {
            throw new BusinessException(messageResolver.getMessage("J0053", new Object[] {"catBic"}));
        }

        List<SicDetalle> listaSicDetalle = sicDetalleDao.findDepositantes(catBic, Boolean.FALSE);
        if (listaSicDetalle == null || listaSicDetalle.isEmpty()) {
            return (emptySicDetalleArray);
        }

        return listaSicDetalle.toArray(emptySicDetalleArray);
    }

    public  List<String> listaCustodios()   throws BusinessException {
    	log.info("Entrando a obtieneDepositantes()");
    	List<String> listaSic = operacionSicDao.findCustodiosinOperacionSic();
    	
    	return listaSic;
    }
    
    /**
     *
     * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#grabaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO,
     *      String, boolean, HashMap, String)
     */
    public void grabaOperacion(GrabaOperacionParams grabaOperacionParams) throws BusinessException {

        log.info("Entrando a grabaOperacion()");

        validatorService.validaDTONoNulo(grabaOperacionParams, " grabaOperacionParams no existe");
        if(grabaOperacionParams.getTraspasoLibrePagoVO() != null){
        	 validatorService.validaDTONoNulo(grabaOperacionParams.getTraspasoLibrePagoVO(), "TraspsoLibrePagoVO no existe");
             validatorService.validarClaveTipo(grabaOperacionParams.getTraspasoLibrePagoVO().getReferenciaMensaje(), "referenciaMensaje");
             validatorService.validarClaveTipo(grabaOperacionParams.getTraspasoLibrePagoVO().getReferenciaOperacion(), "referenciaOperacion");
             validatorService.validarClaveTipo(grabaOperacionParams.getTraspasoLibrePagoVO().getTipoInstruccion(), "tipoInstruccion");
             if("PORTAL".equals(grabaOperacionParams.getOrigenRegistro())){
            	 setBitacoraTraspasos(grabaOperacionParams); 
             }
             else{
            	 setBitacora(grabaOperacionParams);
             }
             
        }
        else{
        	validatorService.validaDTONoNulo(grabaOperacionParams.getTraspasoContraPagoVO(), "TraspsoContraPagoVO no existe");
            validatorService.validarClaveTipo(grabaOperacionParams.getTraspasoContraPagoVO().getReferenciaMensaje(), "referenciaMensaje");
            validatorService.validarClaveTipo(grabaOperacionParams.getTraspasoContraPagoVO().getReferenciaOperacion(), "referenciaOperacion");
            validatorService.validarClaveTipo(grabaOperacionParams.getTraspasoContraPagoVO().getTipoInstruccion(), "tipoInstruccion");

            setBitacoraContraPago(grabaOperacionParams);

        }

    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#obtieneEstatusOperacion()
     */
    public EstatusOperacion[] obtieneEstatusOperacion() throws BusinessException {

        log.info("Entrando a obtieneEstatusOperacion()");

        List<EstatusOperacion> listaStOper = estatusOperacionDao.findEstatusOperaciones();

        return listaStOper.toArray(new EstatusOperacion[listaStOper.size()]);

    }

    /**
     * Graba la bitacora de la operacion
     *
     * @param grabaOperacionParams
     * @throws BusinessException
     */
    private void setBitacora(GrabaOperacionParams grabaOperacionParams) throws BusinessException {

        log.info("Entrando a setBitacora()");
        
        BitacoraOperaciones bitacoraOperaciones  = new BitacoraOperaciones();
        bitacoraOperaciones.setCargoParticipante(Integer.valueOf(0));
    	bitacoraOperaciones.setTasaFija(Integer.valueOf(0));
    	bitacoraOperaciones.setMarcaCompra(Integer.valueOf(0));
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaTraspasante())
                && grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaTraspasante().trim().length() <= 9) {
            bitacoraOperaciones.setIdTrasp(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaTraspasante().substring(0,2));
            bitacoraOperaciones.setFolioTrasp(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaTraspasante().substring(2,5));
            bitacoraOperaciones.setCuentaTrasp(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaTraspasante().substring(5));
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaReceptora())
                && grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaReceptora().trim().length() <= 9) {
            bitacoraOperaciones.setIdRecep(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaReceptora().substring(0,2));
            bitacoraOperaciones.setFolioRecep(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaReceptora().substring(2,5));
            bitacoraOperaciones.setCuentaRecep(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaReceptora().substring(5));
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoLibrePagoVO().getTipoValor())) {
            bitacoraOperaciones.setTv(grabaOperacionParams.getTraspasoLibrePagoVO().getTipoValor().trim());
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoLibrePagoVO().getEmisora())) {
            bitacoraOperaciones.setEmisora(grabaOperacionParams.getTraspasoLibrePagoVO().getEmisora().trim());
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoLibrePagoVO().getSerie())) {
            bitacoraOperaciones.setSerie(grabaOperacionParams.getTraspasoLibrePagoVO().getSerie().trim());
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoLibrePagoVO().getCupon())) {
            bitacoraOperaciones.setCupon(grabaOperacionParams.getTraspasoLibrePagoVO().getCupon().trim());
        }       
        if (grabaOperacionParams.getTraspasoLibrePagoVO().getCantidadTitulos() != null) {
            bitacoraOperaciones.setCantidadTitulos(
                    BigInteger.valueOf(grabaOperacionParams.getTraspasoLibrePagoVO().getCantidadTitulos().longValue()));
        }
        bitacoraOperaciones.setFechaConcertacion(dateUtilService.getFechaHoraCero(grabaOperacionParams.getTraspasoLibrePagoVO().getFechaRegistro()));        
        bitacoraOperaciones.setFechaLiquidacion(dateUtilService.getFechaHoraCero(grabaOperacionParams.getTraspasoLibrePagoVO().getFechaLiquidacion()));
        bitacoraOperaciones.setFechaVencimiento(dateUtilService.getFechaHoraCero(grabaOperacionParams.getTraspasoLibrePagoVO().getFechaConcertacion()));
        bitacoraOperaciones.setFechaRegistro(grabaOperacionParams.getTraspasoLibrePagoVO().getFechaRegistro());
        bitacoraOperaciones.setFechaHoraAlta(dateUtilService.getCurrentDate());
        bitacoraOperaciones.setFolioControl(
                new BigInteger(grabaOperacionParams.getTraspasoLibrePagoVO().getReferenciaOperacion()));
        bitacoraOperaciones.setReferenciaMensaje(grabaOperacionParams.getTraspasoLibrePagoVO().getReferenciaMensaje().trim());
        bitacoraOperaciones.setReferenciaOperacion(grabaOperacionParams.getTraspasoLibrePagoVO().getReferenciaOperacion().trim());
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoLibrePagoVO().getReferenciaRelacionada())) {
            bitacoraOperaciones.setReferenciaRelacionada(grabaOperacionParams.getTraspasoLibrePagoVO().getReferenciaRelacionada());
        }
        bitacoraOperaciones.setTipoInstruccion(Constantes.TIPO_OPER_TI);        
        bitacoraOperaciones.setDatosAdicionales(utilService.mapaToXml(grabaOperacionParams.getDatosAdicionales()));
        bitacoraOperaciones.setEstatusRegistro(ESTATUS_NO_ENVIADA);       
        bitacoraOperaciones.setMarcaCompra(grabaOperacionParams.isRecepcion() ? UNO_INTEGER : CERO_INTEGER);
        if (StringUtils.isNotBlank(grabaOperacionParams.getIsoFirmado())) {
            bitacoraOperaciones.setOperacionFirmada(grabaOperacionParams.getIsoFirmado());
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getOrigenRegistro())) {
            bitacoraOperaciones.setOrigenRegistro(grabaOperacionParams.getOrigenRegistro().trim());
        }
        Cupon cupon = cuponDao.findCupon(
                new EmisionVO(bitacoraOperaciones.getTv(), bitacoraOperaciones.getEmisora(), bitacoraOperaciones.getSerie(), bitacoraOperaciones.getCupon()));
        bitacoraOperaciones.setTasaFija(Integer.valueOf(0));
        bitacoraOperaciones.setCargoParticipante(Integer.valueOf(0));
        if (cupon != null && cupon.getEmision() != null
                && cupon.getEmision().getInstrumento() != null
                && cupon.getEmision().getInstrumento().getMercado() != null) {
            bitacoraOperaciones.setMercado(cupon.getEmision().getInstrumento().getMercado().getClave());
        }

        if (bitacoraOperacionesDao.save(bitacoraOperaciones) == null) {
            throw new BusinessException(messageResolver.getMessage("J0005"));
        }

    }
    
    /**
     * Graba la bitacora de la operacion
     *
     * @param grabaOperacionParams
     * @throws BusinessException
     */
    private void setBitacoraTraspasos(GrabaOperacionParams grabaOperacionParams) throws BusinessException {

        log.info("Entrando a setBitacora()");

        BitacoraOperaciones bitacoraOperaciones  = new BitacoraOperaciones();
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaTraspasante())
                && grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaTraspasante().trim().length() <= 9) {
            bitacoraOperaciones.setIdTrasp(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaTraspasante().substring(0,2));
            bitacoraOperaciones.setFolioTrasp(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaTraspasante().substring(2,5));
            bitacoraOperaciones.setCuentaTrasp(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaTraspasante().substring(5));
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaReceptora())
                && grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaReceptora().trim().length() <= 9) {
            bitacoraOperaciones.setIdRecep(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaReceptora().substring(0,2));
            bitacoraOperaciones.setFolioRecep(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaReceptora().substring(2,5));
            bitacoraOperaciones.setCuentaRecep(grabaOperacionParams.getTraspasoLibrePagoVO().getIdFolioCtaReceptora().substring(5));
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoLibrePagoVO().getTipoValor())) {
            bitacoraOperaciones.setTv(grabaOperacionParams.getTraspasoLibrePagoVO().getTipoValor().trim());
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoLibrePagoVO().getEmisora())) {
            bitacoraOperaciones.setEmisora(grabaOperacionParams.getTraspasoLibrePagoVO().getEmisora().trim());
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoLibrePagoVO().getSerie())) {
            bitacoraOperaciones.setSerie(grabaOperacionParams.getTraspasoLibrePagoVO().getSerie().trim());
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoLibrePagoVO().getCupon())) {
            bitacoraOperaciones.setCupon(grabaOperacionParams.getTraspasoLibrePagoVO().getCupon().trim());
        }
        if (grabaOperacionParams.getTraspasoLibrePagoVO().getCantidadTitulos() != null) {
            bitacoraOperaciones.setCantidadTitulos(
                    BigInteger.valueOf(grabaOperacionParams.getTraspasoLibrePagoVO().getCantidadTitulos().longValue()));
        }
        bitacoraOperaciones.setFechaLiquidacion(grabaOperacionParams.getTraspasoLibrePagoVO().getFechaLiquidacion());
        bitacoraOperaciones.setFechaRegistro(grabaOperacionParams.getTraspasoLibrePagoVO().getFechaRegistro());
        bitacoraOperaciones.setFolioControl(
                new BigInteger(grabaOperacionParams.getTraspasoLibrePagoVO().getReferenciaOperacion()));
        bitacoraOperaciones.setReferenciaMensaje(grabaOperacionParams.getTraspasoLibrePagoVO().getReferenciaMensaje().trim());
        bitacoraOperaciones.setReferenciaOperacion(grabaOperacionParams.getTraspasoLibrePagoVO().getReferenciaOperacion().trim());
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoLibrePagoVO().getReferenciaRelacionada())) {
            bitacoraOperaciones.setReferenciaRelacionada(grabaOperacionParams.getTraspasoLibrePagoVO().getReferenciaRelacionada());
        }
        bitacoraOperaciones.setTipoInstruccion(Constantes.TIPO_OPER_T);        
        bitacoraOperaciones.setDatosAdicionales(utilService.mapaToXml(grabaOperacionParams.getDatosAdicionales()));
        bitacoraOperaciones.setEstatusRegistro(ESTATUS_NO_ENVIADA);
        bitacoraOperaciones.setFechaConcertacion(dateUtilService.getFechaHoraCero(dateUtilService.getCurrentDate()));
        bitacoraOperaciones.setFechaHoraAlta(dateUtilService.getCurrentDate());
        bitacoraOperaciones.setMarcaCompra(grabaOperacionParams.isRecepcion() ? UNO_INTEGER : CERO_INTEGER);
        if (StringUtils.isNotBlank(grabaOperacionParams.getIsoFirmado())) {
            bitacoraOperaciones.setOperacionFirmada(grabaOperacionParams.getIsoFirmado());
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getOrigenRegistro())) {
            bitacoraOperaciones.setOrigenRegistro(grabaOperacionParams.getOrigenRegistro().trim());
        }
        Cupon cupon = cuponDao.findCupon(
                new EmisionVO(bitacoraOperaciones.getTv(), bitacoraOperaciones.getEmisora(), bitacoraOperaciones.getSerie(), bitacoraOperaciones.getCupon()));
        bitacoraOperaciones.setTasaFija(Integer.valueOf(0));
        bitacoraOperaciones.setCargoParticipante(Integer.valueOf(0));
        if (cupon != null && cupon.getEmision() != null
                && cupon.getEmision().getInstrumento() != null
                && cupon.getEmision().getInstrumento().getMercado() != null) {
            bitacoraOperaciones.setMercado(cupon.getEmision().getInstrumento().getMercado().getClave());
        }

        if (bitacoraOperacionesDao.save(bitacoraOperaciones) == null) {
            throw new BusinessException(messageResolver.getMessage("J0005"));
        }

    }
    
    /**
     * Graba la bitacora de la operacion
     *
     * @param grabaOperacionParams
     * @throws BusinessException
     */
    private void setBitacoraContraPago(GrabaOperacionParams grabaOperacionParams) throws BusinessException {

        log.info("Entrando a setBitacora()");

        BitacoraOperaciones bitacoraOperaciones  = new BitacoraOperaciones();
        bitacoraOperaciones.setCargoParticipante(Integer.valueOf(0));
    	bitacoraOperaciones.setTasaFija(Integer.valueOf(0));
    	bitacoraOperaciones.setMarcaCompra(Integer.valueOf(0));
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoContraPagoVO().getIdFolioCtaTraspasante())
                && grabaOperacionParams.getTraspasoContraPagoVO().getIdFolioCtaTraspasante().trim().length() <= 9) {
            bitacoraOperaciones.setIdTrasp(grabaOperacionParams.getTraspasoContraPagoVO().getIdFolioCtaTraspasante().substring(0,2));
            bitacoraOperaciones.setFolioTrasp(grabaOperacionParams.getTraspasoContraPagoVO().getIdFolioCtaTraspasante().substring(2,5));
            bitacoraOperaciones.setCuentaTrasp(grabaOperacionParams.getTraspasoContraPagoVO().getIdFolioCtaTraspasante().substring(5));
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoContraPagoVO().getIdFolioCtaReceptora())
                && grabaOperacionParams.getTraspasoContraPagoVO().getIdFolioCtaReceptora().trim().length() <= 9) {
            bitacoraOperaciones.setIdRecep(grabaOperacionParams.getTraspasoContraPagoVO().getIdFolioCtaReceptora().substring(0,2));
            bitacoraOperaciones.setFolioRecep(grabaOperacionParams.getTraspasoContraPagoVO().getIdFolioCtaReceptora().substring(2,5));
            bitacoraOperaciones.setCuentaRecep(grabaOperacionParams.getTraspasoContraPagoVO().getIdFolioCtaReceptora().substring(5));
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoContraPagoVO().getTipoValor())) {
            bitacoraOperaciones.setTv(grabaOperacionParams.getTraspasoContraPagoVO().getTipoValor().trim());
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoContraPagoVO().getEmisora())) {
            bitacoraOperaciones.setEmisora(grabaOperacionParams.getTraspasoContraPagoVO().getEmisora().trim());
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoContraPagoVO().getSerie())) {
            bitacoraOperaciones.setSerie(grabaOperacionParams.getTraspasoContraPagoVO().getSerie().trim());
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoContraPagoVO().getCupon())) {
            bitacoraOperaciones.setCupon(grabaOperacionParams.getTraspasoContraPagoVO().getCupon().trim());
        }
        if (grabaOperacionParams.getTraspasoContraPagoVO().getCantidadTitulos() != null) {
            bitacoraOperaciones.setCantidadTitulos(
                    BigInteger.valueOf(grabaOperacionParams.getTraspasoContraPagoVO().getCantidadTitulos().longValue()));
        }
        
        bitacoraOperaciones.setFechaConcertacion(dateUtilService.getFechaHoraCero(grabaOperacionParams.getTraspasoContraPagoVO().getFechaRegistro()));        
        bitacoraOperaciones.setFechaLiquidacion(dateUtilService.getFechaHoraCero(grabaOperacionParams.getTraspasoContraPagoVO().getFechaLiquidacion()));
        bitacoraOperaciones.setFechaVencimiento(dateUtilService.getFechaHoraCero(grabaOperacionParams.getTraspasoContraPagoVO().getFechaConcertacion()));
        bitacoraOperaciones.setFechaRegistro(grabaOperacionParams.getTraspasoContraPagoVO().getFechaRegistro());
        bitacoraOperaciones.setFechaHoraAlta(dateUtilService.getCurrentDate());                
        bitacoraOperaciones.setFolioControl(
                new BigInteger(grabaOperacionParams.getTraspasoContraPagoVO().getReferenciaOperacion()));
        bitacoraOperaciones.setReferenciaMensaje(grabaOperacionParams.getTraspasoContraPagoVO().getReferenciaMensaje().trim());
        bitacoraOperaciones.setReferenciaOperacion(grabaOperacionParams.getTraspasoContraPagoVO().getReferenciaOperacion().trim());
        if (StringUtils.isNotBlank(grabaOperacionParams.getTraspasoContraPagoVO().getReferenciaRelacionada())) {
            bitacoraOperaciones.setReferenciaRelacionada(grabaOperacionParams.getTraspasoContraPagoVO().getReferenciaRelacionada());
        }
        bitacoraOperaciones.setTipoInstruccion(Constantes.TIPO_OPER_VI);              	        		        	      
        bitacoraOperaciones.setDatosAdicionales(utilService.mapaToXml(grabaOperacionParams.getDatosAdicionales()));
        bitacoraOperaciones.setEstatusRegistro(ESTATUS_NO_ENVIADA);        
        bitacoraOperaciones.setMarcaCompra(grabaOperacionParams.isRecepcion() ? UNO_INTEGER : CERO_INTEGER);
        if (StringUtils.isNotBlank(grabaOperacionParams.getIsoFirmado())) {
            bitacoraOperaciones.setOperacionFirmada(grabaOperacionParams.getIsoFirmado());
        }
        if (StringUtils.isNotBlank(grabaOperacionParams.getOrigenRegistro())) {
            bitacoraOperaciones.setOrigenRegistro(grabaOperacionParams.getOrigenRegistro().trim());
        }
        Cupon cupon = cuponDao.findCupon(
                new EmisionVO(bitacoraOperaciones.getTv(), bitacoraOperaciones.getEmisora(), bitacoraOperaciones.getSerie(), bitacoraOperaciones.getCupon()));
        bitacoraOperaciones.setTasaFija(Integer.valueOf(0));
        bitacoraOperaciones.setCargoParticipante(Integer.valueOf(0));
        if (cupon != null && cupon.getEmision() != null
                && cupon.getEmision().getInstrumento() != null
                && cupon.getEmision().getInstrumento().getMercado() != null) {
            bitacoraOperaciones.setMercado(cupon.getEmision().getInstrumento().getMercado().getClave());
        }
        bitacoraOperaciones.setBoveda(grabaOperacionParams.getTraspasoContraPagoVO().getBoveda());
        bitacoraOperaciones.setBovedaEfectivo(grabaOperacionParams.getTraspasoContraPagoVO().getBovedaEfectivo());
        bitacoraOperaciones.setDivisa(grabaOperacionParams.getTraspasoContraPagoVO().getDivisa());
        bitacoraOperaciones.setMonto(grabaOperacionParams.getTraspasoContraPagoVO().getMonto());

        if (bitacoraOperacionesDao.save(bitacoraOperaciones) == null) {
            throw new BusinessException(messageResolver.getMessage("J0005"));
        }

    }

    /**
     * Graba la bitacora de operaci&oacute;n de divisi&oacute;n internacional para el MAV
     *
     * @param operacionSic el objeto de operacion Sic
     * @throws BusinessException
     */
	private void setBitacora(OperacionSic operacionSic) throws BusinessException {
		log.debug("DivisionInternacionalServiceImpl :: setBitacora");
    	BitacoraOperaciones bitacoraOperaciones  = new BitacoraOperaciones();
    	bitacoraOperaciones.setCargoParticipante(Integer.valueOf(0));
    	bitacoraOperaciones.setTasaFija(Integer.valueOf(0));
    	bitacoraOperaciones.setMarcaCompra(Integer.valueOf(0));
    	if (operacionSic.getCantidadTitulos() != null) {
    		bitacoraOperaciones.setCantidadTitulos(operacionSic.getCantidadTitulos());
    	}
    	    	
		bitacoraOperaciones.setIdTrasp(operacionSic.getCuentaNombrada().getInstitucion().getTipoInstitucion() != null ? operacionSic.getCuentaNombrada().getInstitucion().getTipoInstitucion().getClaveTipoInstitucion() : null);
		bitacoraOperaciones.setIdRecep(operacionSic.getCuentaNombrada().getInstitucion().getTipoInstitucion() != null ? operacionSic.getCuentaNombrada().getInstitucion().getTipoInstitucion().getClaveTipoInstitucion() : null);
		bitacoraOperaciones.setFolioTrasp(operacionSic.getCuentaNombrada().getInstitucion().getFolioInstitucion());
		bitacoraOperaciones.setFolioRecep(operacionSic.getCuentaNombrada().getInstitucion().getFolioInstitucion());
    	
	    bitacoraOperaciones.setCuentaRecep(operacionSic.getCuentaNombrada().getCuenta());
	    bitacoraOperaciones.setCuentaTrasp(operacionSic.getCuentaNombrada().getCuenta());
	    
    	
    	if (operacionSic.getEmision() != null && operacionSic.getEmision().getInstrumento() != null) {
    		bitacoraOperaciones.setTv(operacionSic.getEmision().getInstrumento().getClaveTipoValor() != null ? operacionSic.getEmision().getInstrumento().getClaveTipoValor() : null);
    	}
    	if (operacionSic.getEmision() != null && operacionSic.getEmision().getEmisora() != null) {
    		bitacoraOperaciones.setEmisora(operacionSic.getEmision().getEmisora().getClavePizarra() != null ? operacionSic.getEmision().getEmisora().getClavePizarra() : null);
    	}
    	if (operacionSic.getEmision() != null) {
    		bitacoraOperaciones.setSerie(operacionSic.getEmision().getSerie() != null && StringUtils.isNotBlank(operacionSic.getEmision().getSerie()) ? operacionSic.getEmision().getSerie() : null);
    	}
    	if (operacionSic.getEmision() != null) {
    		Cupon cupon = cuponDao.findCuponByIdEmision(operacionSic.getEmision().getIdEmision());
    		bitacoraOperaciones.setCupon(cupon.getClaveCupon());
    	}
    	if (operacionSic.getDivisa() != null) {
    	    bitacoraOperaciones.setDivisa(operacionSic.getDivisa());
    	}
    	/*
    	 * 05/09/012 
    	 * adicion de la insercion de la boveda y la firma 
    	 */
    	if(StringUtils.isNotBlank(operacionSic.getCatBic().getBicProd())){
    		bitacoraOperaciones.setBoveda(operacionSic.getCatBic().getBicProd());
    		bitacoraOperaciones.setBovedaEfectivo(operacionSic.getCatBic().getBicProd());
    	}   	   	
    	
    	if(operacionSic.getIsoFirmado()!=null){
    		bitacoraOperaciones.setOperacionFirmada(operacionSic.getIsoFirmado());
    	}
    	
    	bitacoraOperaciones.setFolioControl(operacionSic.getFolioControl());
    	//BigInteger folioReferenciaMensaje = utilService.getFolio(SEQ_REFERENCIA_MENSAJE);
    	/**se utiliza el mismo valor de la ReferenciaMensaje que se genera en la firma*/
    	bitacoraOperaciones.setReferenciaMensaje(operacionSic.getReferenciaMensaje());   
    	bitacoraOperaciones.setReferenciaOperacion(operacionSic.getFolioControl() != null ? operacionSic.getFolioControl().toString() : null);
        bitacoraOperaciones.setEstatusRegistro(ESTATUS_NO_ENVIADA);
        bitacoraOperaciones.setFechaConcertacion(dateUtilService.getFechaHoraCero(operacionSic.getFechaNotificacion()));
        bitacoraOperaciones.setFechaLiquidacion(dateUtilService.getFechaHoraCero(operacionSic.getFechaLiquidacion()));
        bitacoraOperaciones.setFechaVencimiento(dateUtilService.getFechaHoraCero(operacionSic.getFechaOperacion()));
        
        
        bitacoraOperaciones.setFechaHoraAlta(dateUtilService.getCurrentDate());
        bitacoraOperaciones.setOrigenRegistro(ID_ORIGEN_DIV_INTERNACIONAL);
        bitacoraOperaciones.setMonto(operacionSic.getImporte());
        if (operacionSic.getTipoMensaje() != null) {
        	if (Constantes.MT_543.equals(operacionSic.getTipoMensaje()) || Constantes.MT_541.equals(operacionSic.getTipoMensaje())) {
        		bitacoraOperaciones.setTipoInstruccion(Constantes.TIPO_OPER_VI);
        	}else if (Constantes.MT_540.equals(operacionSic.getTipoMensaje()) || Constantes.MT_542.equals(operacionSic.getTipoMensaje())) {
        		bitacoraOperaciones.setTipoInstruccion(Constantes.TIPO_OPER_TI);
        	}
        	if (Constantes.MT_540.equals(operacionSic.getTipoMensaje()) || Constantes.MT_541.equals(operacionSic.getTipoMensaje())) {
        		bitacoraOperaciones.setMarcaCompra(UNO_INTEGER);
        	}else if(Constantes.MT_542.equals(operacionSic.getTipoMensaje()) || Constantes.MT_543.equals(operacionSic.getTipoMensaje())){
        		bitacoraOperaciones.setMarcaCompra(CERO_INTEGER);        		
        	}	
        }              
        bitacoraOperaciones.setDatosAdicionales(
                utilService.mapaToXml(operacionSic.getDatosAdicionales()));
        if (bitacoraOperacionesDao.save(bitacoraOperaciones) == null) {
            throw new BusinessException(messageResolver.getMessage("J0005"));
        }        
    }

    /**
     * Determina el tipo de alta a partir de la instituci&oacute;n
     * @param agenteVO
     * @return String[]
     */
    private String[] getAltasArqueo(AgenteVO agenteVO) {
        log.info("Entrando a DivisionInternacionalServiceImpl.getAltasArqueo()");
        String[] altas = {null, null, null};

        String clave = agenteVO.getClave();

        if (clave.equalsIgnoreCase(Constantes.NAFINSA)) {
            altas[0] = Constantes.ALTA1NAFINSA;
            altas[1] = Constantes.ALTA2NAFINSA;
            altas[2] = Constantes.ALTA3NAFINSA;

        }
        else if (clave.equalsIgnoreCase(Constantes.BANAMEX)) {
            altas[0] = Constantes.ALTA1BANAMEX;
            altas[1] = Constantes.ALTA2BANAMEX;
            altas[2] = Constantes.ALTA2BANAMEX;
        }
        else if (clave.equalsIgnoreCase(Constantes.INBURSA)) {
            altas[0] = Constantes.ALTA1INBURSA;
            altas[1] = Constantes.ALTA2INBURSA;
            altas[2] = Constantes.ALTA2INBURSA;
        }
        return (altas);

    }

    /**
     * Verifica si la consulta es hist&oacute;rica
     * @param fechaConsulta
     * @return boolean
     */
    private boolean isHistorico(Date fechaConsulta) {
        Date fechaActual = dateUtilService.getCurrentDate();
        fechaActual = DateUtils.preparaFechaConExtremoEnSegundos(fechaActual,true);

        if (fechaConsulta.compareTo(fechaActual) < 0) {
            return (true);
        }
        return (false);
    }

    /**
     * Transforma un arreglo de objetos EmisionVO a cadenas descriptivas de las emisiones
     * @param emisionVOs
     * @return String[]
     */
    private String[] emisionVO2String(EmisionVO[] emisionVOs) {
        String[] emisiones = new String[emisionVOs.length];
        StringBuffer emision = new StringBuffer("");
        for (int i = 0; i < emisionVOs.length; i++) {
            emision.append(emisionVOs[i].getTv());
            emision.append(emisionVOs[i].getEmisora());
            emision.append(emisionVOs[i].getSerie());
            emision.append(emisionVOs[i].getCupon());
            emisiones[i] = emision.toString();
            emision.delete(0, emision.length());
        }
        return emisiones;
    }

    /**
     * Calcula el arqueo de las emisiones proporcionadas
     * @param listaArqueoEmisiones
     * @param emisionVO
     * @param isHistorico
     * @return EmisionArqueadaVO
     * @throws BusinessException
     */
    private EmisionArqueadaVO calculaArqueo(List listaArqueoEmisiones, EmisionVO emisionVO, boolean isHistorico) throws BusinessException {
        log.info("Entrando a DivisionInternacionalServiceImpl.calculaArqueo()");
        EmisionArqueadaVO emisionArqueadaVO = new EmisionArqueadaVO();

        emisionArqueadaVO.setEmision(emisionVO);

        ListaArqueoVO listaArqueoVO = new ListaArqueoVO();
        PorcentajeArqueoVO porcentajeArqueoVO = new PorcentajeArqueoVO();
        TotalListaArqueoVO totalListaArqueoVO = new TotalListaArqueoVO();
        totalListaArqueoVO.setTotalNumAgentes(UtilsVO.BIG_INTEGER_ZERO);
        totalListaArqueoVO.setTotalPosicionActual(UtilsVO.BIG_INTEGER_ZERO);
        totalListaArqueoVO.setTotalValuaMercado(UtilsVO.BIG_DECIMAL_ZERO);
        totalListaArqueoVO.setTotalValuacionNominal(UtilsVO.BIG_DECIMAL_ZERO);
        List agentesVO = new ArrayList();
        Map porcentajeAgentes = new TreeMap();
        Map porcentajeCuentas = new TreeMap();
        StringBuffer llaveAgente = new StringBuffer("");
        StringBuffer llaveCuenta = new StringBuffer("");

        BigInteger totalPosicionActual = null;
        BigDecimal porcentajePosicionActual = null;
        for (Iterator iter = listaArqueoEmisiones.iterator(); iter.hasNext();) {
            Object[] element = (Object[]) iter.next();
            AgenteArqueoVO agenteArqueoVO = new AgenteArqueoVO();
            AgenteVO agenteVO = new AgenteVO(element[0].toString().trim(), element[1].toString().trim(), element[2].toString().trim());
            agenteVO.setNombreCorto(element[8].toString().trim());
            BigInteger posicionActual = (BigInteger)element[7];
            BigDecimal valuaMercado = BigDecimal.ZERO;

            agenteArqueoVO.setAgente(agenteVO);
            agenteArqueoVO.setPosicionActual(posicionActual);
            agenteArqueoVO.setValuaMercado(valuaMercado.setScale(UtilsVO.BIG_INTEGER_DOS.intValue(), BigDecimal.ROUND_HALF_UP));

            if (isHistorico) {
                BigDecimal valorNominal = emisionVO.getValorNominal();
                valorNominal.setScale(UtilsVO.BIG_INTEGER_DOS.intValue(), BigDecimal.ROUND_HALF_UP);
                BigDecimal valuacionNominal = valorNominal.multiply(new BigDecimal(posicionActual));
                agenteArqueoVO.setValuacionNominal(valuacionNominal.setScale(UtilsVO.BIG_INTEGER_DOS.intValue(), BigDecimal.ROUND_HALF_UP));
                totalListaArqueoVO.setTotalValuacionNominal(totalListaArqueoVO.getTotalValuacionNominal().add(valuacionNominal));
            }

            totalListaArqueoVO.setTotalNumAgentes(totalListaArqueoVO.getTotalNumAgentes().add(
                    UtilsVO.BIG_INTEGER_UNO));
            totalListaArqueoVO.setTotalPosicionActual(totalListaArqueoVO.getTotalPosicionActual()
                    .add(posicionActual));
            totalListaArqueoVO.setTotalValuaMercado(totalListaArqueoVO.getTotalValuaMercado().add(
                    valuaMercado));

            agentesVO.add(agenteArqueoVO);

            if (!isHistorico) {
                llaveAgente.delete(0, llaveAgente.length());
                llaveAgente.append(agenteVO.getId());
                llaveAgente.append(":");
                llaveAgente.append(agenteVO.getFolio());
                llaveAgente.append(":");
                llaveAgente.append(agenteVO.getCuenta());

                llaveCuenta.delete(0, llaveCuenta.length());
                if (agenteVO.getCuenta().substring(0, 2).equalsIgnoreCase(Constantes.CUENTA_54)) {
                    llaveCuenta.append(agenteVO.getCuenta().substring(0, 2));
                }
                else {
                    llaveCuenta.append(agenteVO.getCuenta().substring(2));
                }

                totalPosicionActual = (BigInteger) porcentajeAgentes.get(llaveAgente.toString());
                if (totalPosicionActual != null) {
                    porcentajeAgentes.put(llaveAgente.toString(), totalPosicionActual
                            .add(posicionActual));
                }
                else {
                    porcentajeAgentes.put(llaveAgente.toString(), posicionActual);
                }

                totalPosicionActual = (BigInteger) porcentajeCuentas.get(llaveCuenta.toString());
                if (totalPosicionActual != null) {
                    porcentajeCuentas.put(llaveCuenta.toString(), totalPosicionActual
                            .add(posicionActual));
                }
                else {
                    porcentajeCuentas.put(llaveCuenta.toString(), posicionActual);
                }
            }
        }
        if (isHistorico) {
            totalListaArqueoVO.setTotalValuacionNominal(totalListaArqueoVO
                    .getTotalValuacionNominal().setScale(UtilsVO.BIG_INTEGER_DOS.intValue(),
                            BigDecimal.ROUND_HALF_UP));
        }

        totalListaArqueoVO.setTotalValuaMercado(totalListaArqueoVO.getTotalValuaMercado().setScale(
                UtilsVO.BIG_INTEGER_DOS.intValue(), BigDecimal.ROUND_HALF_UP));

        if (!isHistorico) {
            List porcentajesAgentesVO = new ArrayList();
            for (Iterator iter = porcentajeAgentes.keySet().iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                totalPosicionActual = (BigInteger) porcentajeAgentes.get(element);

                PorcentajeAgenteVO porcentajeAgenteVO = new PorcentajeAgenteVO();
                String[] claveAgente = element.split(":");
                AgenteVO agenteVO = new AgenteVO(claveAgente[0], claveAgente[1], claveAgente[2]);
                porcentajeAgenteVO.setAgente(agenteVO);

                if (totalListaArqueoVO.getTotalPosicionActual() == null
                        || totalListaArqueoVO.getTotalPosicionActual().intValue() == 0) {
                    throw new BusinessException(messageResolver.getMessage("J0087"), "J0087");
                }
                porcentajePosicionActual = new BigDecimal(totalPosicionActual.multiply(BigInteger
                        .valueOf(UtilsVO.BIG_INTEGER_ZIEN.intValue()))).divide(new BigDecimal(
                        totalListaArqueoVO.getTotalPosicionActual()), UtilsVO.BIG_INTEGER_DOS
                        .intValue(), BigDecimal.ROUND_HALF_UP);
                porcentajeAgenteVO.setPorcentaje(porcentajePosicionActual);
                porcentajesAgentesVO.add(porcentajeAgenteVO);
            }
            PorcentajeAgenteVO[] porcentajeAgenteVOs = new PorcentajeAgenteVO[porcentajesAgentesVO
                    .size()];
            porcentajeArqueoVO.setPorcentajesAgentes((PorcentajeAgenteVO[]) porcentajesAgentesVO
                    .toArray(porcentajeAgenteVOs));

            List porcentajesCuentasVO = new ArrayList();
            for (Iterator iter = porcentajeCuentas.keySet().iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                totalPosicionActual = (BigInteger) porcentajeCuentas.get(element);

                PorcentajeCuentaVO porcentajeCuentaVO = new PorcentajeCuentaVO();
                porcentajeCuentaVO.setCuenta(element);

                if (totalListaArqueoVO.getTotalPosicionActual() == null
                        || totalListaArqueoVO.getTotalPosicionActual().intValue() == 0) {
                    throw new BusinessException(messageResolver.getMessage("J0087"), "J0087");
                }
                porcentajePosicionActual = new BigDecimal(totalPosicionActual.multiply(BigInteger
                        .valueOf(UtilsVO.BIG_INTEGER_ZIEN.intValue()))).divide(new BigDecimal(
                        totalListaArqueoVO.getTotalPosicionActual()), UtilsVO.BIG_INTEGER_DOS
                        .intValue(), BigDecimal.ROUND_HALF_UP);
                porcentajeCuentaVO.setPorcentaje(porcentajePosicionActual);
                porcentajesCuentasVO.add(porcentajeCuentaVO);
            }
            PorcentajeCuentaVO[] porcentajeCuentaVOs = new PorcentajeCuentaVO[porcentajesCuentasVO.size()];
            porcentajeArqueoVO.setPorcentajesCuentas((PorcentajeCuentaVO[]) porcentajesCuentasVO.toArray(porcentajeCuentaVOs));
        }
        else {
        	porcentajeArqueoVO.setPorcentajesAgentes(emptyPorcentajeAgenteVOArray);
        	porcentajeArqueoVO.setPorcentajesCuentas(emptyPorcentajeCuentaVOArray);
        }

        AgenteArqueoVO[] agenteArqueoVOs = new AgenteArqueoVO[agentesVO.size()];
        listaArqueoVO.setAgentesArqueo((AgenteArqueoVO[]) agentesVO.toArray(agenteArqueoVOs));
        listaArqueoVO.setTotalListaArqueo(totalListaArqueoVO);

        emisionArqueadaVO.setListaArqueos(listaArqueoVO);
        emisionArqueadaVO.setPorcentajesArqueo(porcentajeArqueoVO);

        return (emisionArqueadaVO);
    }

    /**
     * Verifica los totales de arqueo
     * @param totalArqueoVO
     * @param isHistorico
     * @return boolean
     */
    private boolean verificaTotales(TotalArqueoVO totalArqueoVO, boolean isHistorico) {
        if (isHistorico) {
            if (totalArqueoVO.getTotalEmisiones() != null
                    && !totalArqueoVO.getTotalEmisiones().equals(UtilsVO.BIG_INTEGER_ZERO)
                    && totalArqueoVO.getSaldoTotal() != null
                    && !totalArqueoVO.getSaldoTotal().equals(UtilsVO.BIG_DECIMAL_ZERO)
                    && totalArqueoVO.getTotalValuacionNominal() != null
                    && !totalArqueoVO.getTotalValuacionNominal().equals(UtilsVO.BIG_DECIMAL_ZERO)
                    && totalArqueoVO.getTotalValuaMercado() != null
                    && !totalArqueoVO.getTotalValuaMercado().equals(UtilsVO.BIG_DECIMAL_ZERO)) {
                return (false);
            }
        }
        else {
            if (totalArqueoVO.getTotalEmisiones() != null
                    && !totalArqueoVO.getTotalEmisiones().equals(UtilsVO.BIG_INTEGER_ZERO)
                    && totalArqueoVO.getSaldoTotal() != null
                    && !totalArqueoVO.getSaldoTotal().equals(UtilsVO.BIG_DECIMAL_ZERO)) {
                return (false);
            }
        }
        return (true);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getListaEmisionesFideicomisoArqueo(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO, java.util.Date)
     */
    public EmisionVO[] getListaEmisionesFideicomisoArqueo(AgenteVO agenteVO, Date fechaConsulta) throws BusinessException {

    	log.info("Entrando a DivisionInternacionalServiceImpl.getListaEmisionesFideicomisoArqueo()");
        validatorService.validaDTONoNulo(agenteVO, "AgenteVO");
        if (fechaConsulta == null) {
            throw new BusinessException(messageResolver.getMessage("J0053",(Object) "fechaConsulta"), "J0053");
        }
        String[] altas = getAltasArqueo(agenteVO);
        List listaEmisiones = null;
        boolean isHistorico = isHistorico(fechaConsulta);
        if (isHistorico) {
        	listaEmisiones = posicionNombradaHDao.getEmisionesHistoricasFideicomiso(altas[0],altas[1], altas[2], fechaConsulta);
        }
        else {
        	listaEmisiones = posicionNombradaDao.getEmisionesFideicomiso(altas[0], altas[1], altas[2]);
        }

        if (listaEmisiones == null || listaEmisiones.isEmpty()) {
        	return (emptyEmisionVOArray);
        }

        ArrayList listaEmisionesVO = new ArrayList();
        for (Iterator iter = listaEmisiones.iterator(); iter.hasNext();) {
        	EmisionVO emisionVO = new EmisionVO();
        	Object[] element = (Object[]) iter.next();
        	emisionVO.setTv(element[0].toString().trim());
        	emisionVO.setEmisora(element[1].toString().trim());
        	emisionVO.setSerie(element[2].toString().trim());
        	emisionVO.setCupon(element[3].toString().trim());
        	emisionVO.setUltimoHecho(null);
        	emisionVO.setValorNominal((BigDecimal) element[4]);
        	emisionVO.setAlta(element[5].toString().trim());
        	listaEmisionesVO.add(emisionVO);
        }

        EmisionVO[] emisionVOs = new EmisionVO[listaEmisionesVO.size()];

        return (EmisionVO[]) listaEmisionesVO.toArray(emisionVOs);
    }

    /**
     * Obtiene un objeto ArqueoVO vacio
     * @return ArqueoVO
     */
    private ArqueoVO getEmptyArqueoVO() {
    	ArqueoVO arqueoVO = new ArqueoVO();
    	arqueoVO.setDatosEmisiones(new EmisionArqueadaVO[0]);
    	arqueoVO.setListaEmisiones(new EmisionVO[0]);
    	TotalArqueoVO totalArqueoVO = new TotalArqueoVO();
    	totalArqueoVO.setSaldoTotal(BigDecimal.ZERO);
    	totalArqueoVO.setTotalEmisiones(BigInteger.ZERO);
    	totalArqueoVO.setTotalValuacionNominal(BigDecimal.ZERO);
    	totalArqueoVO.setTotalValuaMercado(BigDecimal.ZERO);
    	arqueoVO.setTotalArqueoVO(totalArqueoVO);
    	return (arqueoVO);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getArqueoValores(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO, com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO, com.indeval.portalinternacional.middleware.servicios.vo.TotalArqueoVO, java.util.Date, java.lang.Boolean)
     */
    public ArqueoVO getArqueoValores(AgenteVO agenteVO, EmisionVO emisionVO, TotalArqueoVO totalArqueoVO, Date fechaConsulta, Boolean isExport) throws BusinessException {
        log.info("Entrando a DivisionInternacionalServiceImpl.getArqueoValores()");

        validatorService.validaDTONoNulo(agenteVO, "AgenteVO");

        if (fechaConsulta == null) {
            throw new BusinessException(messageResolver.getMessage("J0053",(Object) "fechaConsulta"), "J0053");
        }

        boolean isHistorico = isHistorico(fechaConsulta);

        boolean calculaTotales = true;

        ArqueoVO arqueoVO = new ArqueoVO();
        EmisionVO[] emisionVOs = null;
        List listaEmisiones = new ArrayList();
        List listaArqueoEmisiones = null;
        List<EmisionVO> listaAux = new ArrayList<EmisionVO>();

        String[] altas = getAltasArqueo(agenteVO);
        String[] listaEmisionesString = null;
        EmisionArqueadaVO[] emisionArqueadaVO = null;

        TotalArqueoVO totalArqueoVO2 = null;

        if (isExport.booleanValue()) {
            emisionVOs = getListaEmisionesFideicomisoArqueo(agenteVO, fechaConsulta);
            if ((emisionVOs == null) || (emisionVOs.length == 0)) {
            	return (getEmptyArqueoVO());
            }
            listaEmisionesString = emisionVO2String(emisionVOs);
            arqueoVO.setListaEmisiones(emisionVOs);

            totalArqueoVO2 = new TotalArqueoVO();
            totalArqueoVO2.setSaldoTotal(UtilsVO.BIG_DECIMAL_ZERO);
            totalArqueoVO2.setTotalValuacionNominal(UtilsVO.BIG_DECIMAL_ZERO);
            totalArqueoVO2.setTotalValuaMercado(UtilsVO.BIG_DECIMAL_ZERO);

            totalArqueoVO2.setTotalEmisiones(BigInteger.valueOf(emisionVOs.length));

            emisionArqueadaVO = new EmisionArqueadaVO[emisionVOs.length];

            for (int i = 0; i < emisionArqueadaVO.length; i++) {
                listaEmisiones.add(listaEmisionesString[i]);
                listaAux.add(emisionVOs[i]);
                emisionVO = emisionVOs[i];

                if (isHistorico) {
                    listaArqueoEmisiones = (List)posicionNombradaHDao.getHistoricoFideicomiso(altas,listaAux,fechaConsulta,Boolean.FALSE);
                }
                else {
                    listaArqueoEmisiones = (List)posicionNombradaDao.getArqueoEmisionesFideicomiso(altas,listaAux,Boolean.FALSE, agenteVO);
                }
                if (listaArqueoEmisiones == null || listaArqueoEmisiones.isEmpty()) {
                    return (getEmptyArqueoVO());
                }

                emisionArqueadaVO[i] = calculaArqueo(listaArqueoEmisiones, emisionVO, isHistorico);
                if (isHistorico) {
                    totalArqueoVO2.setTotalValuacionNominal(totalArqueoVO2
                            .getTotalValuacionNominal().add(
                                    emisionArqueadaVO[i].getListaArqueos().getTotalListaArqueo()
                                            .getTotalValuacionNominal()));
                    totalArqueoVO2.setTotalValuaMercado(totalArqueoVO2.getTotalValuaMercado().add(
                            emisionArqueadaVO[i].getListaArqueos().getTotalListaArqueo()
                                    .getTotalValuaMercado()));
                }
                listaEmisiones.clear();
                listaAux.clear();
            }

            listaEmisiones.clear();
            listaAux.clear();

            for (int i = 0; i < listaEmisionesString.length; i++) {
                listaEmisiones.add(listaEmisionesString[i]);
                listaAux.add(emisionVOs[i]);
            }

            BigInteger totalPosicionActual = null;
            if (isHistorico) {
                totalPosicionActual = (BigInteger)posicionNombradaHDao.getHistoricoFideicomiso(altas, listaAux, fechaConsulta, Boolean.TRUE);
            }
            else {
                totalPosicionActual = (BigInteger)posicionNombradaDao.getArqueoEmisionesFideicomiso(altas,listaAux,Boolean.TRUE, agenteVO);
            }

            totalArqueoVO2.setSaldoTotal(new BigDecimal(totalPosicionActual));

            arqueoVO.setTotalArqueoVO(totalArqueoVO2);

            arqueoVO.setDatosEmisiones(emisionArqueadaVO);
        }
        else {

            if (totalArqueoVO != null) {
                calculaTotales = verificaTotales(totalArqueoVO, isHistorico);
            }

            if (emisionVO == null) {
            	emisionVOs = getListaEmisionesFideicomisoArqueo(agenteVO, fechaConsulta);
            	if ((emisionVOs == null) || (emisionVOs.length == 0)) {
            		return (getEmptyArqueoVO());
            	}
                listaEmisionesString = emisionVO2String(emisionVOs);
                listaEmisiones.add(listaEmisionesString[0]);
                listaAux.add(emisionVOs[0]);
                arqueoVO.setListaEmisiones(emisionVOs);
                emisionVO = emisionVOs[0];
            }
            else {
                listaEmisionesString = emisionVO2String(new EmisionVO[] {emisionVO });
                listaEmisiones.add(listaEmisionesString[0]);
                listaAux.add(emisionVO);
            }

            if (isHistorico) {
                listaArqueoEmisiones = (List)posicionNombradaHDao.getHistoricoFideicomiso(altas,listaAux,fechaConsulta,Boolean.FALSE);
            }
            else {
                listaArqueoEmisiones = (List)posicionNombradaDao.getArqueoEmisionesFideicomiso(altas,listaAux,Boolean.FALSE, agenteVO);
            }
            if (listaArqueoEmisiones == null || listaArqueoEmisiones.isEmpty()) {
                return (getEmptyArqueoVO());
            }

            emisionArqueadaVO = new EmisionArqueadaVO[1];
            emisionArqueadaVO[0] = calculaArqueo(listaArqueoEmisiones, emisionVO, isHistorico);

            arqueoVO.setDatosEmisiones(emisionArqueadaVO);

            if (calculaTotales) {
                listaEmisiones.clear();
                listaAux.clear();
                totalArqueoVO2 = new TotalArqueoVO();
                totalArqueoVO2.setSaldoTotal(UtilsVO.BIG_DECIMAL_ZERO);
                totalArqueoVO2.setTotalValuacionNominal(UtilsVO.BIG_DECIMAL_ZERO);
                totalArqueoVO2.setTotalValuaMercado(UtilsVO.BIG_DECIMAL_ZERO);

                emisionVOs = getListaEmisionesFideicomisoArqueo(agenteVO, fechaConsulta);
                if ((emisionVOs == null) || (emisionVOs.length == 0)) {
                	return (getEmptyArqueoVO());
                }
                totalArqueoVO2.setTotalEmisiones(BigInteger.valueOf(emisionVOs.length));

                listaEmisionesString = emisionVO2String(emisionVOs);

                if (isHistorico) {
                    EmisionArqueadaVO emisionArqueadaVOAux = null;
                    for (int i = 0; i < listaEmisionesString.length; i++) {

                        listaEmisiones.add(listaEmisionesString[i]);
                        listaAux.add(emisionVOs[i]);
                        emisionVO = emisionVOs[i];

                        listaArqueoEmisiones = (List)posicionNombradaHDao.getHistoricoFideicomiso(altas, listaAux, fechaConsulta,Boolean.FALSE);

                        if (listaArqueoEmisiones == null || listaArqueoEmisiones.isEmpty()) {
                            return (getEmptyArqueoVO());
                        }

                        emisionArqueadaVOAux = calculaArqueo(listaArqueoEmisiones, emisionVO,isHistorico);

                        totalArqueoVO2.setTotalValuacionNominal(totalArqueoVO2
                                .getTotalValuacionNominal().add(
                                        emisionArqueadaVOAux.getListaArqueos()
                                                .getTotalListaArqueo().getTotalValuacionNominal()));
                        totalArqueoVO2.setTotalValuaMercado(totalArqueoVO2.getTotalValuaMercado()
                                .add(
                                        emisionArqueadaVOAux.getListaArqueos()
                                                .getTotalListaArqueo().getTotalValuaMercado()));
                        listaEmisiones.clear();
                        listaAux.clear();
                    }
                }

                listaEmisiones.clear();
                listaAux.clear();

                for (int i = 0; i < listaEmisionesString.length; i++) {
                    listaEmisiones.add(listaEmisionesString[i]);
                    listaAux.add(emisionVOs[i]);
                }

                BigInteger totalPosicionActual = null;
                if (isHistorico) {
                    totalPosicionActual = (BigInteger)posicionNombradaHDao.getHistoricoFideicomiso(altas,listaAux, fechaConsulta,Boolean.TRUE);
                }
                else {
                    totalPosicionActual = (BigInteger)posicionNombradaDao.getArqueoEmisionesFideicomiso(altas,listaAux,Boolean.TRUE, agenteVO);
                }
                totalArqueoVO2.setSaldoTotal(new BigDecimal(totalPosicionActual));
                arqueoVO.setTotalArqueoVO(totalArqueoVO2);
            }
            else {
                arqueoVO.setTotalArqueoVO(totalArqueoVO);
            }

        }
        return (arqueoVO);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getTopeCirculanteFidecomiso(com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
     */
    public PaginaVO getTopeCirculanteFidecomiso(EmisionVO emisionVO, PaginaVO paginaVO) throws BusinessException {
        log.info("Entrando a DivisionInternacionalServiceImpl.getTopeCirculanteFidecomiso()");

        if (emisionVO == null) {
            throw new BusinessException(messageResolver.getMessage("J0053", new Object[] {"emisionVO"}));
        }
        if (StringUtils.isBlank(emisionVO.getAlta())) {
        	throw new BusinessException(messageResolver.getMessage("J0053", new Object[] {"emisionVO.alta"}));
        }
        emisionVO.setEmisionExtra(emisionVO.getAlta());
        paginaVO = posicionNombradaDao.getValoresByAgenteFirmadoEmisionAlta(emisionVO, paginaVO);
        return (this.transformTopeCirculanteFidecomiso(paginaVO));
    }

    /**
     * Transforma la lista de emisiones obtenida al objeto valor de salida para la presentaci&oacute;n
     * calculando los valores correspondientes
     * @param paginaVO
     * @return PaginaVO
     * @throws BusinessException
     */
    private PaginaVO transformTopeCirculanteFidecomiso(PaginaVO paginaVO) throws BusinessException {

        log.info("Entrando a DivisionInternacionalServiceImpl.getTopeCirculanteFidecomiso()");

        List<TopeCirculanteFidecomisoVO> registros = new ArrayList<TopeCirculanteFidecomisoVO>(paginaVO.getRegistros().size());
        for (int i = 0; i < paginaVO.getRegistros().size(); i++) {
        	PosicionNombrada posicionNombrada = (PosicionNombrada)paginaVO.getRegistros().get(i);
        	TopeCirculanteFidecomisoVO topeCirculanteFidecomisoVO = new TopeCirculanteFidecomisoVO();
        	topeCirculanteFidecomisoVO.setEmisionVO(ConvertBO2VO.crearEmisionVO(posicionNombrada,null));

        	/* Consultamos el valor del tope circulante correspondiente a la emision actual */
        	/*	se modifica la consulta para conseguir el topeCirculante*/
        	//se busca la posici&oacute;n disponible
        	BigInteger posicionDisponible = posicionNombradaDao.findPosicionDisponibleByIdEmision(posicionNombrada.getCupon().getEmision().getIdEmision());
        	
        	//solo para el caso de CMA sustituye el valor que traiga por la cuenta 8347 en lugar de 8337
        	//pedido por David Uvence 07 de enero 2014
        	if(posicionNombrada.getCupon().getEmision().getEmisora().getClavePizarra().equalsIgnoreCase(EMISORA_CMA))
        	{
        		EmisionVO emision = new EmisionVO(posicionNombrada.getCupon().getEmision().getIsin());
        		posicionDisponible = posicionNombradaDao.findPosicionDisponible(agenteNafinsaCMA, emision);
        	}
        	double topeFideicomiso = (double)0.0;
        	double titulosCirculacion = (double)0.0;
        	double titulosDisponibles = (double)0.0;
        	
        	if(posicionDisponible != null){
        		topeFideicomiso = posicionDisponible.doubleValue();
        		topeCirculanteFidecomisoVO.setTopeFideicomiso(new BigDecimal(topeFideicomiso));
        	}
        	if (posicionNombrada.getPosicionDisponible() != null) {
        		titulosCirculacion = posicionNombrada.getPosicionDisponible().doubleValue();
        		topeCirculanteFidecomisoVO.setTitulosCirculacion(new BigDecimal(titulosCirculacion));
        	}
        	if (topeFideicomiso > titulosCirculacion) {
        		titulosDisponibles = topeFideicomiso - titulosCirculacion;
        		topeCirculanteFidecomisoVO.setTitulosDisponibles(new BigDecimal(titulosDisponibles));
        	}
        	else {
        		topeCirculanteFidecomisoVO.setTitulosDisponibles(new BigDecimal(topeFideicomiso));
        	}
        	registros.add(topeCirculanteFidecomisoVO);
        }
        paginaVO.setRegistros(registros);
        return (paginaVO);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#consultaCustodios(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO)
     */
    public List<ConsultaCustodiosVO> consultaCustodios(AgenteVO agenteVO) throws BusinessException {
        log.info("\n\n####### Entrando a consultaCustodios()...");

    	/* Validamos los parametros */
    	if (agenteVO == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053", new Object[] {"agenteVO"}));
    	}

    	/* Consultamos los datos del custodio */
    	List<CatBic> catBics = catBicDao.findCatBic(new AgenteVO[] {agenteVO});

    	/* Inicializamos la lista de VOs de salida */
    	List<ConsultaCustodiosVO> output = new ArrayList<ConsultaCustodiosVO>(catBics.size());

    	int CODIGO_BANCO_CUSTODIO = 0;
    	int NOMBRE_CORTO_CUSTODIO = 1;
    	int FACTOR_CALCULADO_CUSTODIO = 2;
    	int ID_CUSTODIO = 3;
    	ConsultaCustodiosVO consultaCustodiosVO = null;
    	/* Consultamos los detalles y emisiones de cada custodio */
    	for (int i = 0; i < catBics.size(); i++) {
    		CatBic catBic = catBics.get(i);
            consultaCustodiosVO = new ConsultaCustodiosVO();
        	List<SicDetalle> sicDetalles = sicDetalleDao.findDepositantes(catBic, Boolean.TRUE);
        	List<SicEmision> sicEmisiones = sicEmisionDao.findSicEmisionesByCustodio(catBic, Boolean.TRUE);
        	consultaCustodiosVO.setCatBic(catBic);
            consultaCustodiosVO.setSicDetalles(sicDetalles);
            consultaCustodiosVO.setEmisiones(sicEmisiones);
            List<Object> datosCustodio = this.obtenerDatosCustodio(catBic);
        	if (!datosCustodio.isEmpty() && datosCustodio.size() > 1) {
        	    List<String> tvsCustodio = this.catBicDao.findTiposValorCustodiosByIdCustodio(Long.valueOf(((Integer) datosCustodio.get(ID_CUSTODIO)).longValue()));
                consultaCustodiosVO.setAbreviacionCustodio((String) datosCustodio.get(CODIGO_BANCO_CUSTODIO));
                consultaCustodiosVO.setNombreCorto((String) datosCustodio.get(NOMBRE_CORTO_CUSTODIO));
                consultaCustodiosVO.setFactorCalculado((Integer) datosCustodio.get(FACTOR_CALCULADO_CUSTODIO));
                consultaCustodiosVO.setTiposValorCustodio(tvsCustodio);
        	}
        	else {
                consultaCustodiosVO.setAbreviacionCustodio((String) datosCustodio.get(CODIGO_BANCO_CUSTODIO));
                consultaCustodiosVO.setTiposValorCustodio(null);
        	}
          List<String> cadenasDeFormatosCustodio = this.getCustodioTipoBenefDao().findCadenasDeFormatoByIdCuentaNombrada(catBic.getCuentaNombrada().getIdCuentaNombrada());
          consultaCustodiosVO.setFormatosCustodio(null);
          if (cadenasDeFormatosCustodio != null && !cadenasDeFormatosCustodio.isEmpty()) {
              consultaCustodiosVO.setFormatosCustodio(cadenasDeFormatosCustodio);
          }

          output.add(consultaCustodiosVO);
    	}
    	return (output);
    }

    /**
     * Obtiene el Codigo Banco, el Nombre Corto y el Factor Calculado del Custodio (tabla C_CUSTODIO)
     * correspondiente al CatBic, si no existe un Custodio solo se obtiene la abreviacion del CatBic. 
     * @param catBic El objeto CatBic para obtener el Custodio o la abreviacion.
     * @return Un listado con los datos.
     */
    private List<Object> obtenerDatosCustodio(CatBic catBic) {
        log.info("\n\n####### Entrando a obtenerDatosCustodio()...");
        List<Object> datosCustodio = new ArrayList<Object>();
        List<Custodio> custodios = catBicDao.getCustodiosByIdCatBic(catBic.getIdCatbic());
        if (custodios != null && !custodios.isEmpty()) {
            datosCustodio.add(custodios.get(0).getCodigoBanco());
            datosCustodio.add(custodios.get(0).getNombreCorto());
            datosCustodio.add(custodios.get(0).getFactorCalculado());
            datosCustodio.add(custodios.get(0).getId());
        }
        else {
            datosCustodio.add(catBic.getAbreviacionCustodio());
        }
        return datosCustodio;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#altaCustodio(com.indeval.portalinternacional.middleware.servicios.vo.AltaCustodioVO)
     */
    public void altaCustodio(AltaCustodioVO altaCustodioVO) throws BusinessException {
        log.info("\n\n####### Entrando a altaCustodio()...");

    	/* Validamos los parametros */
    	if (altaCustodioVO == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053", new Object[] {"altaCustodioVO"}));
    	}
    	String camposRequeridos = altaCustodioVO.validate();
    	if (camposRequeridos != null) {
    		throw new BusinessException(messageResolver.getMessage("J0055", new Object[] {camposRequeridos}));
    	}

    	AgenteVO custodio = altaCustodioVO.getCustodio();

    	/* Verificamos la existencia de la institucion */
    	PaginaVO paginaVO = institucionDao.findInstituciones(custodio, null, false);

    	if (paginaVO.getTotalRegistros().intValue() == 0) {
    		throw new BusinessException(messageResolver.getMessage("J0056",
    				new Object[] {custodio.getId(), custodio.getFolio()}));
    	}

    	/* Consultamos la cuenta nombrada */
    	CuentaNombrada cuentaNombrada = cuentaNombradaDao.findCuenta(custodio);

    	if (cuentaNombrada == null) {
    		throw new BusinessException(messageResolver.getMessage("J0057",
    				new Object[] {custodio.getId(), custodio.getFolio(), custodio.getCuenta()}));
    	}

    	/* Consultamos la fecha actual del sistema */
    	Date fechaHora = dateUtilService.getCurrentDate();

    	/* Ensamblamos el registro nuevo */
    	CatBic catBic = new CatBic();
    	catBic.setBicProd(altaCustodioVO.getProduccion());
    	catBic.setBicPrueba(altaCustodioVO.getEntrenamiento());
    	catBic.setCuentaIndeval(altaCustodioVO.getCuentaIndeval());
    	catBic.setCuentaNombrada(cuentaNombrada);
    	// Se setea valor del detalle Custodio obtenida de la pantalla
    	catBic.setDetalleCustodio(altaCustodioVO.getDetalleCustodio());
    	catBic.setFechaHora(fechaHora);
    	catBic.setMoneda(altaCustodioVO.getMoneda());
    	catBic.setPais(altaCustodioVO.getPais());
    	catBic.setStatus(altaCustodioVO.getEstatus());
    	catBic.setMercado(altaCustodioVO.getMercado());
    	catBic.setUsuario(Constantes.APLICACION_DIVINT);
    	catBic.setEstatusRegistro(Constantes.ESTATUS_REGISTRO_VIGENTE);
    	catBic.setActivo(Constantes.CUSTODIO_ACTIVO);
    	catBic.setAbreviacionCustodio(altaCustodioVO.getAbreviacion());

    	/* Guardamos el registro nuevo */
    	Long idCatBic = (Long) this.catBicDao.save(catBic);
    	this.catBicDao.flush();

    	//Se inserta el Custodio de la tabla C_CUSTODIO.
    	Long idCustodio = this.insertarCustodio(catBic, altaCustodioVO);

    	//Se insertan los tipos valor del custodio R_TIPO_VALOR_CUSTODIO
    	this.insertaRelacion_TipoValor_Custodio(idCustodio, altaCustodioVO.getListaTiposValorCustodio());

    	//Se insertan los formatos del custodio a la tabla C_CUSTODIO_TIPO_BENEF
    	this.insertarFormatosDeCustodio(cuentaNombrada.getIdCuentaNombrada(), altaCustodioVO.getListaFormatosCustodio());

    	this.catBicDao.flush();
    	
    	Bovedas boveda = bovedaService.getBovedaByIdCuentaBoveda(cuentaNombrada.getIdCuentaNombrada().intValue());
    	
    	catBicDao.save(new CustodioBoveda(idCustodio, boveda.getIdBoveda()));
    	
    }

    /**
     * Inserta un registro a la tabla C_CUSTODIO_TIPO_BENEF.
     * @param idCuentaNombrada El id de la cuenta nombrada.
     * @param formatos El listado de formatos a insertar.
     */
    private void insertarFormatosDeCustodio(Long idCuentaNombrada, List<String> formatos) {
        log.info("\n\n####### Entrando a insertarFormatosDeCustodio()...");
        CustodioTipoBenef objAInsertar = null;
        if (formatos != null && !formatos.isEmpty()) {
            for (String cadena : formatos) {
                String[] sep = cadena.split("-");
                objAInsertar = new CustodioTipoBenef(this.getTipoBeneficiarioById(Long.valueOf(sep[0])), idCuentaNombrada,
                                                     sep[1], Double.valueOf(sep[2]));
                this.getCustodioTipoBenefDao().save(objAInsertar);
            }
            this.getCustodioTipoBenefDao().flush();
            objAInsertar = null;
        }
    }

    /**
     * Inserta un objeto a la tabla R_TIPO_VALOR_CUSTODIO
     * @param idCustodio El id del custodio.
     * @param altaCustodioVO El objeto donde viene el listado de tipos valor.
     */
    private void insertaRelacion_TipoValor_Custodio(Long idCustodio, List<String> tiposValor) {
        log.info("\n\n####### Entrando a insertaRelacion_TipoValor_Custodio()...");
        RTipoValorCustodio obj = null;
        if (tiposValor != null && !tiposValor.isEmpty()) {
            for (String tv : tiposValor) {
                log.info("\n\n####### tv=[" + tv + "]");
                obj = new RTipoValorCustodio(tv, idCustodio);
                this.catBicDao.save(obj);
            }
            this.catBicDao.flush();
            obj = null;
        }
    }

    /**
     * Inserta un Custodio, tabla C_CUSTODIO. 
     * @param catBic El CatBuc del Custodio.
     * @param altaCustodioVO Objeto del que se toman datos para armar el objeto Custodio.
     * @return El id del custodio.
     */
    private Long insertarCustodio(CatBic catBic, AltaCustodioVO altaCustodioVO) {
        Custodio custodio = new Custodio();
        custodio.setCodigoBanco(altaCustodioVO.getAbreviacion());
        custodio.setDescripcion(altaCustodioVO.getDetalleCustodio());
        custodio.setFactorCalculado(altaCustodioVO.getFactorCalculado());
        custodio.setIdCatBic(catBic);
        custodio.setNombreCorto(altaCustodioVO.getNombreCorto());
        custodio.setParticipante(0);
        Integer id = (Integer) this.catBicDao.save(custodio);
        this.catBicDao.flush();
        return Long.valueOf(id.longValue());
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#altaDepositante(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO, com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO, java.lang.String, java.lang.String)
     */
    public void altaDepositante(CatBic catBic, String bicDepLiq, String idDepLiq, String depLiq) throws BusinessException {

    	/* Validamos los parametros */
    	if (catBic == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"catBic"}));
    	}
    	if (catBic.getCuentaNombrada() == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"catBic.cuentaNombrada"}));
    	}
    	if (StringUtils.isBlank(bicDepLiq)) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"bicDepLiq"}));
    	}
    	if (StringUtils.isBlank(idDepLiq)) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"idDepLiq"}));
    	}
    	if (StringUtils.isBlank(depLiq)) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"depLiq"}));
    	}

    	/* Obtenemos los atributos del agente */
    	AgenteVO agenteVO = ConvertBO2VO.crearAgenteVO(catBic.getCuentaNombrada());

    	/* Verificamos la existencia de la institucion */
    	PaginaVO paginaVO = institucionDao.findInstituciones(agenteVO, null, false);

    	if (paginaVO.getTotalRegistros().intValue() == 0) {
    		throw new BusinessException(messageResolver.getMessage("J0056",
    				new Object[] {agenteVO.getId(), agenteVO.getFolio()}));
    	}

    	/* Consultamos la cuenta nombrada */
    	CuentaNombrada cuentaNombrada = cuentaNombradaDao.findCuenta(agenteVO);

    	if (cuentaNombrada == null) {
    		throw new BusinessException(messageResolver.getMessage("J0057",
    				new Object[] {agenteVO.getId(), agenteVO.getFolio(), agenteVO.getCuenta()}));
    	}

    	/* Validamos la existencia del custodio */
    	List<CatBic> catBics = catBicDao.findCatBic(new AgenteVO[] {agenteVO});

    	if ((catBics == null) || (catBics.isEmpty())) {
    		throw new BusinessException(messageResolver.getMessage("J0058",
    				new Object[] {agenteVO.getId(), agenteVO.getFolio(), agenteVO.getCuenta()}));
    	}

    	/* Consultamos la fecha actual del sistema */
    	Date fechaHora = dateUtilService.getCurrentDate();

    	/* Ensamblamos el registro nuevo */
    	SicDetalle sicDetalle = new SicDetalle();
    	sicDetalle.setAplicacion(Constantes.APLICACION_DIVINT);
    	sicDetalle.setBicDepLiq(bicDepLiq);
    	sicDetalle.setCuentaNombrada(cuentaNombrada);
    	sicDetalle.setDepLiq(depLiq);
    	sicDetalle.setFechaHora(fechaHora);
    	sicDetalle.setIdDepLiq(idDepLiq);
    	sicDetalle.setEstatusRegistro(Constantes.ESTATUS_REGISTRO_VIGENTE);
    	sicDetalle.setCatBic(catBic);

    	/* Guardamos el registro nuevo */
    	sicDetalleDao.save(sicDetalle);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#altaEmision(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO, com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO, java.lang.String)
     */
    public void altaEmision(EmisionVO emisionVO, CatBic catBic, String formaDeOperar) throws BusinessException {

    	/* Validamos los parametros */
    	if (emisionVO == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"emisionVO"}));
    	}
    	if (StringUtils.isBlank(formaDeOperar)) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"formaDeOperar"}));
    	}
    	if (catBic == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"catBic"}));
    	}
    	if (catBic.getIdCatbic() == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"catBic.idCatBic"}));
    	}
    	if (catBic.getCuentaNombrada() == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"catBic.cuentaNombrada"}));
    	}

    	/* Obtenemos los atributos del agente */
    	AgenteVO agenteVO = ConvertBO2VO.crearAgenteVO(catBic.getCuentaNombrada());

    	/* Verificamos la existencia de la institucion */
    	PaginaVO paginaVO = institucionDao.findInstituciones(agenteVO, null, false);

    	if (paginaVO.getTotalRegistros().intValue() == 0) {
    		throw new BusinessException(messageResolver.getMessage("J0056",
    				new Object[] {agenteVO.getId(), agenteVO.getFolio()}));
    	}

    	/* Consultamos la cuenta nombrada */
    	CuentaNombrada cuentaNombrada = cuentaNombradaDao.findCuenta(agenteVO);

    	if (cuentaNombrada == null) {
    		throw new BusinessException(messageResolver.getMessage("J0057",
    				new Object[] {agenteVO.getId(), agenteVO.getFolio(), agenteVO.getCuenta()}));
    	}

    	/* Consultamos la emision */
    	emisionVO.setCupon("0000");
    	Emision emision = emisionDao.findEmisionLiberada(emisionVO);

    	if (emision == null) {
    		throw new BusinessException(messageResolver.getMessage("J0001"));
    	}

		Long idCatBic = catBic.getIdCatbic();
		Long idEmision = emision.getIdEmision();
		Long idCuentaNombrada = cuentaNombrada.getIdCuentaNombrada();
		List<SicEmision> listaEmisiones = sicEmisionDao.findSicEmisionVigenteByIdEmision(idCatBic, idCuentaNombrada,
				idEmision, Constantes.ESTATUS_REGISTRO_VIGENTE, null);
		if (listaEmisiones != null && listaEmisiones.size() > 0) {
			throw new BusinessException("Esta emision ya esta asignada a este custodio");
		}
		/* Consultamos si ya existe el registro */
		List<SicEmision> listaEmisionesCanceladas = sicEmisionDao.findSicEmisionVigenteByIdEmision(idCatBic, idCuentaNombrada,
				idEmision, Constantes.ESTATUS_REGISTRO_CANCELADO, null,formaDeOperar,Constantes.APLICACION_DIVINT);
		
		/* Consultamos la fecha actual del sistema */
    	Date fechaHora = dateUtilService.getCurrentDate();

    	/* Ensamblamos el nuevo registro */
    	SicEmision sicEmision = new SicEmision();
    	
		if (listaEmisionesCanceladas != null && listaEmisionesCanceladas.size() > 0) {
			sicEmision =listaEmisionesCanceladas.get(0);
		}

    	
    	sicEmision.setAplicacion(Constantes.APLICACION_DIVINT);
    	sicEmision.setCuentaNombrada(cuentaNombrada);
    	sicEmision.setEmision(emision);
    	sicEmision.setFechaHora(fechaHora);
    	sicEmision.setFormaOper(formaDeOperar);
    	sicEmision.setEstatusRegistro(Constantes.ESTATUS_REGISTRO_VIGENTE);
    	sicEmision.setCatBic(catBic);

    	/* Guardamos el nuevo registro */
    	try {
    	    sicEmisionDao.guardaActualizaEmision(sicEmision);
    	    sicEmisionDao.flush();
    	} catch (ConstraintViolationException cve) {
			log.error("Error al intentar dar de alta la emision - registro duplicado", cve);
    	    throw new BusinessException("Error al intentar dar de alta la emision - registro duplicado");
    	} catch (DataIntegrityViolationException dive) {
			log.error("Error al intentar dar de alta la emision - integridad de datos", dive);
    	    throw new BusinessException("Error al intentar dar de alta la emision - integridad de datos");
        } catch (Exception e) {
			log.error("Error al intentar dar de alta la emision", e);
            throw new BusinessException("Error al intentar dar de alta la emision");
        }
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getListaCierreFideicomiso(java.util.Date)
     */
    public List<ConsultaCierreFideicomisoVO> getListaCierreFideicomiso(ConsultaCierreFideicomisoParams consultaCierreFideicomisoParams) throws BusinessException {

    	/* Validamos los parametros */
    	if (consultaCierreFideicomisoParams == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053", new Object[] {"consultaCierreFideicomisoParams"}));
    	}
    	String message = consultaCierreFideicomisoParams.validate();
    	if (message != null) {
    		throw new BusinessException(messageResolver.getMessage("J0055", new Object[] {message}));
    	}

    	/* Consultamos las cuentas de fideicomiso */

    	/* NAFINSA */
    	CuentaNombrada cuentaNombrada = cuentaNombradaDao.findCuenta(agenteNafinsa);
    	if (cuentaNombrada == null) {
    		throw new BusinessException(messageResolver.getMessage("J0057",
    				new Object[] {agenteNafinsa.getId(),agenteNafinsa.getFolio(),agenteNafinsa.getCuenta()}));
    	}
    	ConsultaCierreFideicomisoVO voNafinsa = new ConsultaCierreFideicomisoVO(cuentaNombrada);
    	voNafinsa.setSaldoFinalDepositosRetiros(consultaCierreFideicomisoParams.getSaldoInicialNafin().toBigInteger());

    	/* VITRO */
    	cuentaNombrada = cuentaNombradaDao.findCuenta(agenteVitro);
    	if (cuentaNombrada == null) {
    		throw new BusinessException(messageResolver.getMessage("J0057",
    				new Object[] {agenteVitro.getId(),agenteVitro.getFolio(),agenteVitro.getCuenta()}));
    	}
    	ConsultaCierreFideicomisoVO voVitro = new ConsultaCierreFideicomisoVO(cuentaNombrada);
    	voVitro.setSaldoFinalDepositosRetiros(consultaCierreFideicomisoParams.getSaldoInicialVitro().toBigInteger());

    	/* INBURSA */
    	cuentaNombrada = cuentaNombradaDao.findCuenta(agenteInbur);
    	if (cuentaNombrada == null) {
    		throw new BusinessException(messageResolver.getMessage("J0057",
    				new Object[] {agenteInbur.getId(),agenteInbur.getFolio(),agenteInbur.getCuenta()}));
    	}
    	ConsultaCierreFideicomisoVO voInbur = new ConsultaCierreFideicomisoVO(cuentaNombrada);
    	voInbur.setSaldoFinalDepositosRetiros(consultaCierreFideicomisoParams.getSaldoInicialInbur().toBigInteger());

    	/* BANAMEX */
    	cuentaNombrada = cuentaNombradaDao.findCuenta(agenteBanamex);
    	if (cuentaNombrada == null) {
    		throw new BusinessException(messageResolver.getMessage("J0057",
    				new Object[] {agenteBanamex.getId(),agenteBanamex.getFolio(),agenteBanamex.getCuenta()}));
    	}
    	ConsultaCierreFideicomisoVO voBanamex = new ConsultaCierreFideicomisoVO(cuentaNombrada);
    	voBanamex.setSaldoFinalDepositosRetiros(consultaCierreFideicomisoParams.getSaldoInicialBanamex().toBigInteger());

    	/* Inicializamos la lista de salida */
    	List<ConsultaCierreFideicomisoVO> output = new ArrayList<ConsultaCierreFideicomisoVO>(4);
    	output.add(voBanamex);
    	output.add(voInbur);
    	output.add(voNafinsa);
    	output.add(voVitro);

    	/* Contamos el numero de emisiones y establecemos los saldos finales */
    	Date fechaConsulta = consultaCierreFideicomisoParams.getFechaConsulta();
    	boolean isHistorico = this.isHistorico(fechaConsulta);

    	/* BANAMEX */
    	Long idCuentaNombrada = voBanamex.getCuentaNombrada().getIdCuentaNombrada();

    	if (isHistorico) {
    		voBanamex.setTotalEmisionesPosicion(posicionNombradaHDivIntDao.countNumeroEmisionesPosicionPorCuentaNombrada(idCuentaNombrada, false));
    		BigInteger saldo = posicionNombradaHDivIntDao.sumPosicionDisponiblePorCuentaNombrada(idCuentaNombrada, false);
    		voBanamex.setSaldoFinalPosicion((saldo == null)?BigInteger.ZERO:saldo);

    		voBanamex.setTotalEmisionesArqueo(posicionNombradaHDivIntDao.countNumeroEmisionesBanamex());
    		saldo = posicionNombradaHDivIntDao.sumPosicionDisponibleBanamex();
    		voBanamex.setSaldoFinalArqueo((saldo == null)?BigInteger.ZERO:saldo);
    	}
    	else {
    		voBanamex.setTotalEmisionesPosicion(posicionNombradaDivIntDao.countNumeroEmisionesPosicionPorCuentaNombrada(idCuentaNombrada, false));
    		BigInteger saldo = posicionNombradaDivIntDao.sumPosicionDisponiblePorCuentaNombrada(idCuentaNombrada, false);
    		voBanamex.setSaldoFinalPosicion((saldo == null)?BigInteger.ZERO:saldo);

    		voBanamex.setTotalEmisionesArqueo(posicionNombradaDivIntDao.countNumeroEmisionesBanamex());
    		saldo = posicionNombradaDivIntDao.sumPosicionDisponibleBanamex();
    		voBanamex.setSaldoFinalArqueo((saldo == null)?BigInteger.ZERO:saldo);
    	}

    	/* INBURSA */
    	idCuentaNombrada = voInbur.getCuentaNombrada().getIdCuentaNombrada();

    	if (isHistorico) {
    		voInbur.setTotalEmisionesPosicion(posicionNombradaHDivIntDao.countNumeroEmisionesPosicionPorCuentaNombrada(idCuentaNombrada, false));
    		BigInteger saldo = posicionNombradaHDivIntDao.sumPosicionDisponiblePorCuentaNombrada(idCuentaNombrada, false);
    		voInbur.setSaldoFinalPosicion((saldo == null)?BigInteger.ZERO:saldo);

    		voInbur.setTotalEmisionesArqueo(posicionNombradaHDivIntDao.countNumeroEmisionesInbur());
    		saldo = posicionNombradaHDivIntDao.sumPosicionDisponibleInbur();
    		voInbur.setSaldoFinalArqueo((saldo == null)?BigInteger.ZERO:saldo);
    	}
    	else {
    		voInbur.setTotalEmisionesPosicion(posicionNombradaDivIntDao.countNumeroEmisionesPosicionPorCuentaNombrada(idCuentaNombrada, false));
    		BigInteger saldo = posicionNombradaDivIntDao.sumPosicionDisponiblePorCuentaNombrada(idCuentaNombrada, false);
    		voInbur.setSaldoFinalPosicion((saldo == null)?BigInteger.ZERO:saldo);

    		voInbur.setTotalEmisionesArqueo(posicionNombradaDivIntDao.countNumeroEmisionesInbur());
    		saldo = posicionNombradaDivIntDao.sumPosicionDisponibleInbur();
    		voInbur.setSaldoFinalArqueo((saldo == null)?BigInteger.ZERO:saldo);
    	}

    	/* NAFINSA */
    	idCuentaNombrada = voNafinsa.getCuentaNombrada().getIdCuentaNombrada();

    	if (isHistorico) {
    		voNafinsa.setTotalEmisionesPosicion(posicionNombradaHDivIntDao.countNumeroEmisionesPosicionPorCuentaNombrada(idCuentaNombrada, false));
    		BigInteger saldo = posicionNombradaHDivIntDao.sumPosicionDisponiblePorCuentaNombrada(idCuentaNombrada, false);
    		voNafinsa.setSaldoFinalPosicion((saldo == null)?BigInteger.ZERO:saldo);

    		voNafinsa.setTotalEmisionesArqueo(posicionNombradaHDivIntDao.countNumeroEmisionesNafin());
    		saldo = posicionNombradaHDivIntDao.sumPosicionDisponibleNafin();
    		voNafinsa.setSaldoFinalArqueo((saldo == null)?BigInteger.ZERO:saldo);
    	}
    	else {
    		voNafinsa.setTotalEmisionesPosicion(posicionNombradaDivIntDao.countNumeroEmisionesPosicionPorCuentaNombrada(idCuentaNombrada, false));
    		BigInteger saldo = posicionNombradaDivIntDao.sumPosicionDisponiblePorCuentaNombrada(idCuentaNombrada, false);
    		voNafinsa.setSaldoFinalPosicion((saldo == null)?BigInteger.ZERO:saldo);

    		voNafinsa.setTotalEmisionesArqueo(posicionNombradaDivIntDao.countNumeroEmisionesNafin());
    		saldo = posicionNombradaDivIntDao.sumPosicionDisponibleNafin();
    		voNafinsa.setSaldoFinalArqueo((saldo == null)?BigInteger.ZERO:saldo);
    	}

    	/* VITRO */
    	idCuentaNombrada = voVitro.getCuentaNombrada().getIdCuentaNombrada();

    	if (isHistorico) {
    		voVitro.setTotalEmisionesPosicion(posicionNombradaHDivIntDao.countNumeroEmisionesPosicionPorCuentaNombrada(idCuentaNombrada, false));
    		BigInteger saldo = posicionNombradaHDivIntDao.sumPosicionDisponiblePorCuentaNombrada(idCuentaNombrada, false);
    		voVitro.setSaldoFinalPosicion((saldo == null)?BigInteger.ZERO:saldo);

    		voVitro.setTotalEmisionesArqueo(posicionNombradaHDivIntDao.countNumeroEmisionesVitro());
    		saldo = posicionNombradaHDivIntDao.sumPosicionDisponibleVitro();
    		voVitro.setSaldoFinalArqueo((saldo == null)?BigInteger.ZERO:saldo);
    	}
    	else {
    		voVitro.setTotalEmisionesPosicion(posicionNombradaDivIntDao.countNumeroEmisionesPosicionPorCuentaNombrada(idCuentaNombrada, false));
    		BigInteger saldo = posicionNombradaDivIntDao.sumPosicionDisponiblePorCuentaNombrada(idCuentaNombrada, false);
    		voVitro.setSaldoFinalPosicion((saldo == null)?BigInteger.ZERO:saldo);

    		voVitro.setTotalEmisionesArqueo(posicionNombradaDivIntDao.countNumeroEmisionesVitro());
    		saldo = posicionNombradaDivIntDao.sumPosicionDisponibleVitro();
    		voVitro.setSaldoFinalArqueo((saldo == null)?BigInteger.ZERO:saldo);
    	}

    	/* Consultamos los retiros y los depositos por cada fideicomiso */
    	for (int i = 0; i < output.size(); i++) {
    		ConsultaCierreFideicomisoVO vo = output.get(i);
    		idCuentaNombrada = vo.getCuentaNombrada().getIdCuentaNombrada();
    		vo.setTotalTitulosOperados(BigInteger.ZERO);

    		/* Depositos */
    		List<Depositos> depositos = depositosDivIntDao.getDepositosDivInt(idCuentaNombrada, fechaConsulta);
    		if (!depositos.isEmpty()) {
    			vo.getNombreTotal().add("DEP.ADMO");
    			vo.getNumeroDepositosRetiros().add(BigInteger.valueOf((long)depositos.size()));
    			BigInteger titulosOperados = BigInteger.ZERO;
    			for (int j = 0; j < depositos.size(); j++) {
    				Depositos deposito = depositos.get(j);
    				vo.getMovimientosFideicomiso().add(new MovimientoFideicomisoVO(deposito,null));
    				titulosOperados = titulosOperados.add(deposito.getNumeroTitulos());
    				vo.setTotalTitulosOperados(vo.getTotalTitulosOperados().add(deposito.getNumeroTitulos()));
    			}
    			vo.getNumeroTitulosOperados().add(titulosOperados);
				vo.setSaldoFinalDepositosRetiros(vo.getSaldoFinalDepositosRetiros().add(titulosOperados));
    		}

    		/* Retiros */
    		List<Retiros> retiros = retirosDivIntDao.getRetirosDivInt(idCuentaNombrada, fechaConsulta);
    		if (!retiros.isEmpty()) {
    			vo.getNombreTotal().add("RET.ADMO");
    			vo.getNumeroDepositosRetiros().add(BigInteger.valueOf((long)retiros.size()));
    			BigInteger titulosOperados = BigInteger.ZERO;
    			for (int j = 0; j < retiros.size(); j++) {
    				Retiros retiro = retiros.get(j);
    				vo.getMovimientosFideicomiso().add(new MovimientoFideicomisoVO(null,retiro));
    				titulosOperados = titulosOperados.add(retiro.getNumeroTitulos());
    				vo.setTotalTitulosOperados(vo.getTotalTitulosOperados().add(retiro.getNumeroTitulos()));
    			}
    			vo.getNumeroTitulosOperados().add(titulosOperados);
    			vo.setSaldoFinalDepositosRetiros(vo.getSaldoFinalDepositosRetiros().subtract(titulosOperados));
    		}
    		vo.setTotalDepositosRetiros(BigInteger.valueOf((long)(depositos.size()+retiros.size())));
    	}
    	return (output);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getListaSicDetalle(com.indeval.portalinternacional.middleware.servicios.modelo.CatBic, com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO, java.lang.String, java.lang.String, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
     */
    public PaginaVO getListaSicDetalle(CatBic catBic, String bicDepLiq, String idDepLiq, String depLiq, PaginaVO paginaVO) throws BusinessException {
    	if (catBic == null) {
    		throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"catBic"}));
    	}
    	if (catBic.getIdCatbic() == null) {
    		throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"catBic.idCatbic"}));
    	}
		return (sicDetalleDao.findDepositantes(catBic, bicDepLiq, idDepLiq, depLiq, paginaVO));
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getListaSicEmisiones(com.indeval.portalinternacional.middleware.servicios.modelo.CatBic, com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO, java.lang.String, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO getListaSicEmisiones(CatBic catBic, EmisionVO emisionVO, String formaDeOperar, PaginaVO paginaVO) throws BusinessException {
    	if (catBic == null) {
    		throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"catBic"}));
    	}
    	if (catBic.getIdCatbic() == null) {
    		throw new IllegalArgumentException(messageResolver.getMessage("J0053", new Object[] {"catBic.idCatbic"}));
    	}
		return (sicEmisionDao.findSicEmisionesByCustodio(catBic, emisionVO, formaDeOperar, paginaVO));
	}

	/**
	 *  @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getListaSicEmisiones(com.indeval.portalinternacional.middleware.servicios.modelo.CatBic, com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO getListaEmisionesCustodio(CatBic catBic,
			EmisionVO emisionVO, PaginaVO paginaVO) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#obtieneCatBic()
	 */
	public List<Object[]> obtieneAllCatBic() throws BusinessException
	{
		log.info("Entrando a obtieneCatBic()");
		List<Object[]> listCatBic= catBicDao.findAllCatBicByName();
		if (listCatBic == null) {
			listCatBic = new ArrayList<Object[]>();
		}
		return listCatBic;
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#updateCustodio(com.indeval.portalinternacional.middleware.servicios.modelo.CatBic)
	 */
	public void updateCustodio(AltaCustodioVO altaCustodioVO, CatBic catBic) throws BusinessException {
        log.info("\n\n####### Entrando a updateCustodio()...");

    	/* Validamos los parametros */
    	if (altaCustodioVO == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053", new Object[] {"altaCustodioVO"}));
    	}
    	if (catBic == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053", new Object[] {"catBic"}));
    	}
    	if (catBic.getIdCatbic() == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053", new Object[] {"catBic.idCatbic"}));
    	}

    	String camposRequeridos = altaCustodioVO.validate();
    	if (camposRequeridos != null) {
    		throw new BusinessException(messageResolver.getMessage("J0055", new Object[] {camposRequeridos}));
    	}

    	AgenteVO custodio = altaCustodioVO.getCustodio();

    	/* Verificamos la existencia de la institucion */
    	PaginaVO paginaVO = institucionDao.findInstituciones(custodio, null, false);

    	if (paginaVO.getTotalRegistros().intValue() == 0) {
    		throw new BusinessException(messageResolver.getMessage("J0056",
    				new Object[] {custodio.getId(), custodio.getFolio()}));
    	}

    	/* Consultamos la cuenta nombrada */
    	CuentaNombrada cuentaNombrada = cuentaNombradaDao.findCuenta(custodio);

    	if (cuentaNombrada == null) {
    		throw new BusinessException(messageResolver.getMessage("J0057",
    				new Object[] {custodio.getId(), custodio.getFolio(), custodio.getCuenta()}));
    	}

    	/* Actualizamos los valores del registro */
    	catBic.setBicProd(altaCustodioVO.getProduccion());
    	catBic.setBicPrueba(altaCustodioVO.getEntrenamiento());
    	catBic.setCuentaIndeval(altaCustodioVO.getCuentaIndeval());
    	catBic.setCuentaNombrada(cuentaNombrada);
    	catBic.setMoneda(altaCustodioVO.getMoneda());
    	catBic.setPais(altaCustodioVO.getPais());
    	catBic.setStatus(altaCustodioVO.getEstatus());
    	catBic.setMercado(altaCustodioVO.getMercado());
        catBic.setAbreviacionCustodio(altaCustodioVO.getAbreviacion());

    	/* Actualizamos el registro */
    	catBicDao.update(catBic);

        //Si existe el Custodio del CatBic actualizado, se actualiza su Codigo Banco.
        Long idCustodio = this.actualizarCustodioByIdCatBic(catBic.getIdCatbic(), altaCustodioVO);

        //Se actualiza la relacion de tipos valor con el custodio.
        this.actualizarRelacionTiposValorCustodio(idCustodio, altaCustodioVO);

        //Se actualizan los formatos del custodio.
        this.actualizarFormatosDeCustodio(catBic.getCuentaNombrada().getIdCuentaNombrada(), altaCustodioVO.getListaFormatosCustodio());
	}

	/**
	 * Actualiza los formatos del custodio en BD.
	 * @param idCuentaNombrada El id de la cuenta nombrada.
	 * @param formatosDeLaEdicion Los formatos provenientes de la edicion del custodio.
	 */
	private void actualizarFormatosDeCustodio(Long idCuentaNombrada, List<String> formatosDeLaEdicion) {
        log.info("\n\n####### Entrando a actualizarFormatosDeCustodio()...");
        List<CustodioTipoBenef> formatosDeBD = null;

        if (formatosDeLaEdicion != null && !formatosDeLaEdicion.isEmpty()) {
            formatosDeBD = this.getCustodioTipoBenefDao().findCustodioTipoBenefByIdCuentaNombrada(idCuentaNombrada);
            if (formatosDeBD != null && !formatosDeBD.isEmpty()) {
                List<String> formatosNuevos = new ArrayList<String>();
                List<CustodioTipoBenef> formatosEliminar = new ArrayList<CustodioTipoBenef>();
                String compara = "";
                boolean seEncontro = false;
                for (String formatoDeLaEdicion : formatosDeLaEdicion) {
                    seEncontro = false;
                    for (CustodioTipoBenef formatoDeBD : formatosDeBD) {
                        compara = formatoDeBD.getIdCustodioTipoBenef() + "-" + formatoDeBD.getFormato() + "-" + formatoDeBD.getPorcentajeRetencion();
                        if (formatoDeLaEdicion.equals(compara)) {
                            seEncontro = true;
                            break;
                        }
                    }
                    if (!seEncontro) {
                        formatosNuevos.add(formatoDeLaEdicion);
                    }
                }
                for (CustodioTipoBenef formatoDeBD : formatosDeBD) {
                    seEncontro = false;
                    compara = formatoDeBD.getIdCustodioTipoBenef() + "-" + formatoDeBD.getFormato() + "-" + formatoDeBD.getPorcentajeRetencion();
                    for (String formatoDeLaEdicion : formatosDeLaEdicion) {
                        if (compara.equals(formatoDeLaEdicion)) {
                            seEncontro = true;
                            break;
                        }
                    }
                    if (!seEncontro) {
                        formatosEliminar.add(formatoDeBD);
                    }
                }
                if (!formatosEliminar.isEmpty()) {
                    for (CustodioTipoBenef custodioTipoBenef : formatosEliminar) {
                        this.getCustodioTipoBenefDao().delete(custodioTipoBenef);
                    }
                    this.getCustodioTipoBenefDao().flush();
                }
                if (!formatosNuevos.isEmpty()) {
                    this.insertarFormatosDeCustodio(idCuentaNombrada, formatosNuevos);
                }
            }
            else {
                this.insertarFormatosDeCustodio(idCuentaNombrada, formatosDeLaEdicion);
            }
        }
        else {
            formatosDeBD = this.getCustodioTipoBenefDao().findCustodioTipoBenefByIdCuentaNombrada(idCuentaNombrada);
            if (formatosDeBD != null && !formatosDeBD.isEmpty()) {
                for (CustodioTipoBenef custodioTipoBenef : formatosDeBD) {
                    this.getCustodioTipoBenefDao().delete(custodioTipoBenef);
                }
                this.getCustodioTipoBenefDao().flush();
            }
        }
	}

	/**
	 * Realiza la gestion de actualizacion de la relacion de tipos valor del custodio. 
	 * @param idCustodio El id del custodio. 
	 * @param altaCustodioVO Objeto de donde se tomaran los tipos valor a asignar al custodio.
	 */
	private void actualizarRelacionTiposValorCustodio(Long idCustodio, AltaCustodioVO altaCustodioVO) {
        log.info("\n\n####### Entrando a actualizarRelacionTiposValorCustodio()...");
        List<String> tvsCustodioDeEdicion = altaCustodioVO.getListaTiposValorCustodio();
        List<String> tvsCustodioDeBD = null;

	    if (tvsCustodioDeEdicion != null && !tvsCustodioDeEdicion.isEmpty()) {
	        tvsCustodioDeBD = this.catBicDao.findTiposValorCustodiosByIdCustodio(idCustodio);
	        if (tvsCustodioDeBD != null && !tvsCustodioDeBD.isEmpty()) {
	            List<String> tvsCustodioNuevos = new ArrayList<String>();
                List<String> tvsCustodioEliminar = new ArrayList<String>();
	            for (String tvCustodioDeEdicion : tvsCustodioDeEdicion) {
                    if (!tvsCustodioDeBD.contains(tvCustodioDeEdicion)) {
                        tvsCustodioNuevos.add(tvCustodioDeEdicion);
                    }
                }
                for (String tvCustodioDeBD : tvsCustodioDeBD) {
                    if (!tvsCustodioDeEdicion.contains(tvCustodioDeBD)) {
                        tvsCustodioEliminar.add(tvCustodioDeBD);
                    }
                }
                if (!tvsCustodioEliminar.isEmpty()) {
                    int registrosBorrados = this.catBicDao.eliminarTiposValorCustodio(idCustodio, tvsCustodioEliminar);
                    log.info("\n\n####### Registros borrados=[" + registrosBorrados + "]");
                }
                if (!tvsCustodioNuevos.isEmpty()) {
                    this.insertaRelacion_TipoValor_Custodio(idCustodio, tvsCustodioNuevos);
                }
	        }
	        else {
	            this.insertaRelacion_TipoValor_Custodio(idCustodio, tvsCustodioDeEdicion);
	        }
	    }
	    else {
	        tvsCustodioDeBD = this.catBicDao.findTiposValorCustodiosByIdCustodio(idCustodio);
            if (tvsCustodioDeBD != null && !tvsCustodioDeBD.isEmpty()) {
                int registrosBorrados = this.catBicDao.eliminarTiposValorCustodiosByIdCustodio(idCustodio);
                log.info("\n\n####### Registros borrados=[" + registrosBorrados + "]");
            }
	    }
	}

    /**
     * Actualiza el Custodio del CatBic indicado.
     * @param idCatBic El id del CatBic.
     * @param altaCustodioVO El objeto de donde tomar datos.
     */
    private Long actualizarCustodioByIdCatBic(Long idCatBic, AltaCustodioVO altaCustodioVO) {
        log.info("\n\n####### Entrando a actualizarCustodioByIdCatBic()...");
        Long idCustodio = null;
        if (idCatBic != null && idCatBic.longValue() > 0) {
            List<Custodio> custodios = catBicDao.getCustodiosByIdCatBic(idCatBic);
            if (custodios != null && !custodios.isEmpty()) {
                Custodio custodio = custodios.get(0);
                idCustodio = Long.valueOf(((Integer) custodio.getId()).longValue());
                custodio.setCodigoBanco(altaCustodioVO.getAbreviacion());
                custodio.setNombreCorto(altaCustodioVO.getNombreCorto());
                custodio.setFactorCalculado(altaCustodioVO.getFactorCalculado());
                custodio.setDescripcion(altaCustodioVO.getDetalleCustodio());
                this.catBicDao.update(custodio);
                this.catBicDao.flush();
            }
        }

        return idCustodio;
    }

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#updateDepositante(com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle)
	 */
	public void updateDepositante(CatBic catBic, String bicDepLiq, String idDepLiq, String depLiq, SicDetalle sicDetalle) throws BusinessException {
    	/* Validamos los parametros */
    	if (catBic == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"catBic"}));
    	}
    	if (catBic.getCuentaNombrada() == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"catBic.cuentaNombrada"}));
    	}
    	if (StringUtils.isBlank(bicDepLiq)) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"bicDepLiq"}));
    	}
    	if (StringUtils.isBlank(idDepLiq)) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"idDepLiq"}));
    	}
    	if (StringUtils.isBlank(depLiq)) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"depLiq"}));
    	}
    	if (sicDetalle == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"sicDetalle"}));
    	}
    	if (sicDetalle.getIdSicDetalle() == null) {
    		throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"sicDetalle.idSicDetalle"}));
    	}


    	/* Obtenemos los atributos del agente */
    	AgenteVO agenteVO = ConvertBO2VO.crearAgenteVO(catBic.getCuentaNombrada());

    	/* Verificamos la existencia de la institucion */
    	PaginaVO paginaVO = institucionDao.findInstituciones(agenteVO, null, false);

    	if (paginaVO.getTotalRegistros().intValue() == 0) {
    		throw new BusinessException(messageResolver.getMessage("J0056",
    				new Object[] {agenteVO.getId(), agenteVO.getFolio()}));
    	}

    	/* Consultamos la cuenta nombrada */
    	CuentaNombrada cuentaNombrada = cuentaNombradaDao.findCuenta(agenteVO);

    	if (cuentaNombrada == null) {
    		throw new BusinessException(messageResolver.getMessage("J0057",
    				new Object[] {agenteVO.getId(), agenteVO.getFolio(), agenteVO.getCuenta()}));
    	}

    	/* Validamos la existencia del custodio */
    	List<CatBic> catBics = catBicDao.findCatBic(new AgenteVO[] {agenteVO});

    	if ((catBics == null) || (catBics.isEmpty())) {
    		throw new BusinessException(messageResolver.getMessage("J0058",
    				new Object[] {agenteVO.getId(), agenteVO.getFolio(), agenteVO.getCuenta()}));
    	}

    	/* Actualizamos los valores del registro */
    	sicDetalle.setBicDepLiq(bicDepLiq);
    	sicDetalle.setCuentaNombrada(cuentaNombrada);
    	sicDetalle.setDepLiq(depLiq);
    	sicDetalle.setIdDepLiq(idDepLiq);
    	sicDetalle.setCatBic(catBic);

    	/* Actualizamos el registro */
    	sicDetalleDao.update(sicDetalle);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#updateSicEmision(com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision)
	 */
	public void updateSicEmision(EmisionVO emisionVO, CatBic catBic, SicEmision sicEmision, String formaDeOperar) throws BusinessException {
	    /* Validamos los parametros */
	    if (emisionVO == null) {
	        throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"emisionVO"}));
	    }
	    if (StringUtils.isBlank(formaDeOperar)) {
	        throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"formaDeOperar"}));
	    }
	    if (catBic == null) {
	        throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"catBic"}));
	    }
	    if (catBic.getIdCatbic() == null) {
	        throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"catBic.idCatBic"}));
	    }
	    if (catBic.getCuentaNombrada() == null) {
	        throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"catBic.cuentaNombrada"}));
	    }
	    if (sicEmision == null) {
	        throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"sicEmision"}));
	    }
	    if (sicEmision.getIdSicEmisiones() == null) {
	        throw new BusinessException(messageResolver.getMessage("J0053",new Object[] {"sicEmision.idSicEmisiones"}));
	    }

	    /* Obtenemos los atributos del agente */
	    AgenteVO agenteVO = ConvertBO2VO.crearAgenteVO(catBic.getCuentaNombrada());

	    /* Verificamos la existencia de la institucion */
	    PaginaVO paginaVO = institucionDao.findInstituciones(agenteVO, null, false);

	    if (paginaVO.getTotalRegistros().intValue() == 0) {
	        throw new BusinessException(messageResolver.getMessage("J0056",
	                new Object[] {agenteVO.getId(), agenteVO.getFolio()}));
	    }

	    /* Consultamos la cuenta nombrada */
	    CuentaNombrada cuentaNombrada = cuentaNombradaDao.findCuenta(agenteVO);

	    if (cuentaNombrada == null) {
	        throw new BusinessException(messageResolver.getMessage("J0057",
	                new Object[] {agenteVO.getId(), agenteVO.getFolio(), agenteVO.getCuenta()}));
	    }

	    /* Consultamos la emision */
	    emisionVO.setCupon("0000");
	    Emision emision = emisionDao.findEmisionLiberada(emisionVO);

	    if (emision == null) {
	        throw new BusinessException(messageResolver.getMessage("J0001"));
	    }

		Long idCatBic = catBic.getIdCatbic();
		Long idEmision = emision.getIdEmision();
		Long idCuentaNombrada = cuentaNombrada.getIdCuentaNombrada();
		List<SicEmision> listaEmisiones = sicEmisionDao.findSicEmisionVigenteByIdEmision(idCatBic, idCuentaNombrada,
				idEmision, Constantes.ESTATUS_REGISTRO_VIGENTE, sicEmision.getIdSicEmisiones());
		if (listaEmisiones != null && listaEmisiones.size() > 0) {
			throw new BusinessException("Esta emision ya esta asignada a este custodio");
		}

	    /* Actualizamos los valores del registro */
	    sicEmision.setCuentaNombrada(cuentaNombrada);
	    sicEmision.setEmision(emision);
	    sicEmision.setFormaOper(formaDeOperar);
	    sicEmision.setCatBic(catBic);

	    /* Actualizamos el registro */
	    try {
	        sicEmisionDao.update(sicEmision);
	        sicEmisionDao.flush();
	    } catch (ConstraintViolationException cve) {
			log.error("Error al intentar dar de alta la emision - registro duplicado", cve);
	        throw new BusinessException("Error al intentar actualizar la emision - registro duplicado");
	    } catch (DataIntegrityViolationException dive) {
			log.error("Error al intentar actualizar la emision - integridad de datos", dive);
	        throw new BusinessException("Error al intentar actualizar la emision - integridad de datos");
	    } catch (Exception e) {
			log.error("Error al intentar actualizar la emision", e);
	        throw new BusinessException("Error al intentar actualizar la emision");
	    }
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#cancelaCustodio(com.indeval.portalinternacional.middleware.servicios.modelo.CatBic)
	 */
	public void cancelaCustodio(CatBic catBic) throws BusinessException {
		if (catBic == null) {
			throw new BusinessException(messageResolver.getMessage("J0053", new Object[] {"catBic"}));
		}

		/* Obtenemos las SIC emisiones correspondientes al custodio actual */
		List<SicEmision> sicEmisiones = sicEmisionDao.findSicEmisionesByCustodio(catBic, Boolean.FALSE);
		if (!sicEmisiones.isEmpty()) {
			if (sicEmisiones.size() == 1) {
				throw new BusinessException(messageResolver.getMessage("J0059", new Object[] {"existe un registro de emisi\u00f3n asociada vigente"}));
			}
			else {
				throw new BusinessException(messageResolver.getMessage("J0059", new Object[] {"existen "+sicEmisiones.size()+" registros de emisiones asociadas vigentes"}));
			}
		}
		/* Obtenemos los depositantes correspondientes al custodio actual */
		List<SicDetalle> sicDetalles = sicDetalleDao.findDepositantes(catBic, Boolean.FALSE);
		if (!sicDetalles.isEmpty()) {
			if (sicDetalles.size() == 1) {
				throw new BusinessException(messageResolver.getMessage("J0059", new Object[] {"existe un registro de depositante asociado vigente"}));
			}
			else {
				throw new BusinessException(messageResolver.getMessage("J0059", new Object[] {"existen "+sicDetalles.size()+" registros de depositantes asociados vigentes"}));
			}
		}

		/* Actualizamos el estatus del registro */
		catBic.setEstatusRegistro(Constantes.ESTATUS_REGISTRO_CANCELADO);
		catBicDao.update(catBic);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#cancelaDepositante(com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle)
	 */
	public void cancelaDepositante(SicDetalle sicDetalle) throws BusinessException {
		if (sicDetalle == null) {
			throw new BusinessException(messageResolver.getMessage("J0053", new Object[] {"sicDetalle"}));
		}
		/* Actualizamos el estatus del registro */
		sicDetalle.setEstatusRegistro(Constantes.ESTATUS_REGISTRO_CANCELADO);
		sicDetalleDao.update(sicDetalle);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#cancelaSicEmision(com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision)
	 */
	public void cancelaSicEmision(SicEmision sicEmision) throws BusinessException {
		if (sicEmision == null) {
			throw new BusinessException(messageResolver.getMessage("J0053", new Object[] {"sicEmision"}));
		}
		/* Actualizamos el estatus del registro */
		sicEmision.setEstatusRegistro(Constantes.ESTATUS_REGISTRO_CANCELADO);
		sicEmisionDao.update(sicEmision);
	}

	/**
	 * Ensambla un objeto EmisionVO a partir de los datos del rengl&oacute;n
	 * @param row
	 * @return EmisionVO
	 */
	private EmisionVO assembleEmisionFideicomiso(Object[] row) {
		EmisionVO emisionVO = new EmisionVO((String)row[1],(String)row[2],(String)row[3],(String)row[4]);
		emisionVO.setAlta((String)row[11]);
		emisionVO.setValorNominal((BigDecimal)row[5]);
		emisionVO.setUltimoHecho(BigDecimal.ZERO);
		return (emisionVO);
	}

	/**
	 * Ensambla un objeto AgenteFideicomisoVO a partir de los datos del rengl&oacute;n
	 * @param row
	 * @return AgenteFideicomisoVO
	 */
	private AgenteFideicomisoVO assembleAgenteFideicomiso(Object[] row) {
		AgenteFideicomisoVO agenteFideicomisoVO = new AgenteFideicomisoVO((String)row[6],
				(String)row[7], (String)row[8], (String)row[9], (BigInteger)row[10], BigInteger.ZERO);
		return (agenteFideicomisoVO);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#consultaFideicomisos(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO, java.util.Date)
	 */
	public List<ConsultaFideicomisosVO> consultaFideicomisos(AgenteVO agenteVO, Date fechaConsulta) throws BusinessException {

		/* Validamos los parametros */
		if (agenteVO == null) {
			throw new BusinessException(messageResolver.getMessage("J0053", new Object[] {"agenteVO"}));
		}
		if (!agenteVO.tieneClave()) {
			throw new BusinessException(messageResolver.getMessage("J0054"));
		}
		if (fechaConsulta == null) {
			throw new BusinessException(messageResolver.getMessage("J0053", new Object[] {"fechaConsulta"}));
		}

		/* Determinamos si es una consulta historica */
		boolean isHistorico = isHistorico(fechaConsulta);

		/* Consultamos las posiciones del agente */
		List<Object> posicionesNombradas = null;

		if (isHistorico) {
			posicionesNombradas = posicionNombradaHDivIntDao.consultaPosicionesFideicomiso(agenteVO, fechaConsulta);
		}
		else {
			posicionesNombradas = posicionNombradaDivIntDao.consultaPosicionesFideicomiso(agenteVO);
		}
		if (posicionesNombradas == null) {
			throw new BusinessException(messageResolver.getMessage("J0060", new Object[] {"02021, 02022 o 02061"}));
		}

		/* Inicializamos el mapa de agrupacion y las variables para el calculo de los totales */
		Map<Long, ConsultaFideicomisosVO> registros = new TreeMap<Long, ConsultaFideicomisosVO>(longOrder);
		BigInteger saldoTotal = BigInteger.ZERO;
		BigDecimal valuacionNominalTotal = BigDecimal.ZERO;

		/* Procesamos los registros obtenidos agrupando por emision */
		for (int i = 0; i < posicionesNombradas.size(); i++) {
			Object[] row = (Object[])posicionesNombradas.get(i);
			Long idEmision = (Long)row[0];

			/* Obtenemos el registro correspondiente a la emision */
			ConsultaFideicomisosVO consultaFideicomisosVO = registros.get(idEmision);

			/* Si el registro no existe, creamos uno nuevo */
			if (consultaFideicomisosVO == null) {
				consultaFideicomisosVO = new ConsultaFideicomisosVO(assembleEmisionFideicomiso(row));
				registros.put(idEmision, consultaFideicomisosVO);
			}

			/* Agregamos el registro de la posicion/agente correspondiente */
			AgenteFideicomisoVO agenteFideicomisoVO = assembleAgenteFideicomiso(row);
			consultaFideicomisosVO.getAgenteFideicomisoVOs().add(agenteFideicomisoVO);

			/* Calculamos la valuacion nominal de la posicion */
			BigDecimal valorNominal = consultaFideicomisosVO.getEmisionVO().getValorNominal();
			BigDecimal posicionActualDecimal = new BigDecimal(agenteFideicomisoVO.getPosicionActual());
			agenteFideicomisoVO.setValuacionNominal(valorNominal.multiply(posicionActualDecimal).setScale(2, BigDecimal.ROUND_HALF_UP));

			/* Actualizamos los totales globales */
			saldoTotal = saldoTotal.add(agenteFideicomisoVO.getPosicionActual());
			valuacionNominalTotal = valuacionNominalTotal.add(agenteFideicomisoVO.getValuacionNominal());
		}

		/* Inicializamos la salida con registros ordenados por emision */
		List<ConsultaFideicomisosVO> output = new ArrayList<ConsultaFideicomisosVO>();
		Iterator<Long> iteratorEmisiones = registros.keySet().iterator();
		while (iteratorEmisiones.hasNext()) {
			output.add(registros.get(iteratorEmisiones.next()));
		}
		Long totalEmisiones = new Long(output.size());

		/* Calculamos las estadisticas de cada registro y los totales */
		for (int i = 0; i < output.size(); i++) {
			ConsultaFideicomisosVO consultaFideicomisosVO = output.get(i);
			List<AgenteFideicomisoVO> agenteFideicomisoVOs = consultaFideicomisosVO.getAgenteFideicomisoVOs();

			/* Asignamos los totales globales a cada registro */
			consultaFideicomisosVO.setSaldoTotal(saldoTotal);
			consultaFideicomisosVO.setTotalValuacionNominal(valuacionNominalTotal);
			consultaFideicomisosVO.setTotalEmisiones(totalEmisiones);
			consultaFideicomisosVO.setTotalAgentes(new Long(agenteFideicomisoVOs.size()));
			consultaFideicomisosVO.setTotalValuacionMercado(BigInteger.ZERO);

			/* Inicializamos las variables para el calculo de los totales y mapas de agrupacion */
			BigInteger totalPosicion = BigInteger.ZERO;
			BigDecimal totalValuacionNominalActual = BigDecimal.ZERO;

			Map<AgenteFideicomisoVO, BigInteger> agrupadosPorAgente = new TreeMap<AgenteFideicomisoVO, BigInteger>(agenteFideicomisoOrder);
			Map<String, BigInteger> agrupadosPorCuenta = new TreeMap<String, BigInteger>(stringOrder);

			/* Agrupamos los montos de posicion por agente y por prefijo/sufijo de cuenta */
			for (int j = 0; j < agenteFideicomisoVOs.size(); j++) {
				AgenteFideicomisoVO agenteFideicomisoVO = agenteFideicomisoVOs.get(j);
				BigInteger posicionActual = agenteFideicomisoVO.getPosicionActual();
				totalPosicion = totalPosicion.add(posicionActual);
				totalValuacionNominalActual = totalValuacionNominalActual.add(agenteFideicomisoVO.getValuacionNominal());

				/* Calculamos los totales de posicion por agente */
				BigInteger saldoPorAgente = agrupadosPorAgente.get(agenteFideicomisoVO);
				if (saldoPorAgente == null) {
					agrupadosPorAgente.put(agenteFideicomisoVO, posicionActual);
				}
				else {
					agrupadosPorAgente.put(agenteFideicomisoVO, saldoPorAgente.add(posicionActual));
				}

				/* Calculamos los totales de posicion por prefijo/sufijo de cuenta */
				String prefijoSufijoCuenta = agenteFideicomisoVO.getPrefijoSufijoCuenta();
				if (prefijoSufijoCuenta == null) {
					throw new BusinessException(messageResolver.getMessage("J0061",
							new Object[] {agenteFideicomisoVO.getCuenta(),"54","97,98"}));
				}
				BigInteger saldoPorCuenta = agrupadosPorCuenta.get(prefijoSufijoCuenta);
				if (saldoPorCuenta == null) {
					agrupadosPorCuenta.put(prefijoSufijoCuenta,posicionActual);
				}
				else {
					agrupadosPorCuenta.put(prefijoSufijoCuenta,saldoPorCuenta.add(posicionActual));
				}
			}

			/* Asignamos los sub-totales del registro */
			consultaFideicomisosVO.setTotalPosicionActual(totalPosicion);
			consultaFideicomisosVO.setTotalValuacionMercadoActual(BigInteger.ZERO);
			consultaFideicomisosVO.setTotalValuacionNominalActual(totalValuacionNominalActual);

			BigDecimal totalPosicionDecimal = new BigDecimal(totalPosicion);

			/* Calculamos las estadisticas por agente */
			Iterator<String> iteratorPorCuenta = agrupadosPorCuenta.keySet().iterator();
			while (iteratorPorCuenta.hasNext()) {
				String prefijoSufijoCuenta = iteratorPorCuenta.next();
				if (totalPosicionDecimal.equals(BigDecimal.ZERO)) {
					consultaFideicomisosVO.getEstadisticasFideicomisoPorCuentaVO().add(new EstadisticasFideicomisoPorCuentaVO(prefijoSufijoCuenta,BigDecimal.ZERO));
				}
				else {
					BigDecimal totalCuenta = new BigDecimal(agrupadosPorCuenta.get(prefijoSufijoCuenta));
					BigDecimal porcentaje = totalCuenta.multiply(Constantes.BIG_DECIMAL_ZIEN).divide(totalPosicionDecimal,2,BigDecimal.ROUND_HALF_UP);
					consultaFideicomisosVO.getEstadisticasFideicomisoPorCuentaVO().add(new EstadisticasFideicomisoPorCuentaVO(prefijoSufijoCuenta,porcentaje));
				}
			}

			/* Calculamos las estadisticas por prefijo/sufijo de cuenta */
			Iterator<AgenteFideicomisoVO> iteratorPorAgente = agrupadosPorAgente.keySet().iterator();
			while (iteratorPorAgente.hasNext()) {
				AgenteFideicomisoVO agenteFideicomisoVO = iteratorPorAgente.next();
				if (totalPosicionDecimal.equals(BigDecimal.ZERO)) {
					consultaFideicomisosVO.getEstadisticasFideicomisosPorAgenteVO().add(new EstadisticasFideicomisoPorAgenteVO(agenteFideicomisoVO,BigDecimal.ZERO));
				}
				else {
					BigDecimal totalAgente = new BigDecimal(agrupadosPorAgente.get(agenteFideicomisoVO));
					BigDecimal porcentaje = totalAgente.multiply(Constantes.BIG_DECIMAL_ZIEN).divide(totalPosicionDecimal,2,BigDecimal.ROUND_HALF_UP);
					consultaFideicomisosVO.getEstadisticasFideicomisosPorAgenteVO().add(new EstadisticasFideicomisoPorAgenteVO(agenteFideicomisoVO,porcentaje));
				}
			}
		}
		return (output);
	}

	public List<EstatusEmisionesDTO> obtieneEmisionesEstatus() {
		List<EstatusEmisionesDTO> listaEstatus = new ArrayList<EstatusEmisionesDTO>();

		listaEstatus = estatusEmisionesDao.buscarListaEstatusEmisiones();

		return listaEstatus;
	}

	public EstatusEmisionesDTO obtenerEstatusEmisionByPk(BigInteger idEstatusEmisiones) {
		EstatusEmisionesDTO estatusEmisionesDTO = null;

		estatusEmisionesDTO = estatusEmisionesDao.obtenerEstatusEmisionByPk(idEstatusEmisiones);

		return estatusEmisionesDTO;
	}

	public PaginaVO findSicEmisionesByEmisionAndCustodio(EstatusEmisionesDTO estatusEmisionesDTO,
			CatBic catBic, EmisionVO emisionVO, PaginaVO paginaVO, Boolean debeDejarLog){
		paginaVO = sicEmisionDao.findSicEmisionesByEmisionAndCustodio(estatusEmisionesDTO, catBic, emisionVO, paginaVO);

		return paginaVO;
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#findSicEmisionesByEmisionAndCustodioPosicionCero(EstatusEmisionesDTO,CatBic,EmisionVO,PaginaVO)
	 */
    public PaginaVO findSicEmisionesByEmisionAndCustodioPosicionCero(EstatusEmisionesDTO estatusEmisionesDTO,
                                                                     CatBic catBic, 
                                                                     EmisionVO emisionVO, 
                                                                     PaginaVO paginaVO) {
        paginaVO = this.sicEmisionDao.findSicEmisionesByEmisionAndCustodioPosicionCero(estatusEmisionesDTO, 
                                                                                       catBic, 
                                                                                       emisionVO, 
                                                                                       paginaVO);

        return paginaVO;
    }

	/**
	 * @param CalendarioEmisionesDeudaExtDTO parametros de la consulta
	 */
	public PaginaVO consultaCalendarioDerechos(
			CalendarioEmisionesDeudaExtDTO params,PaginaVO pagina) throws BusinessException {
		return calendarioEmisionesDeudaExtDao.consultaCalendarioDerechos(params, pagina);
	}


	public List<EstadoDerechoInt> obtieneEstadosDerechoInt()throws BusinessException {
		return calendarioEmisionesDeudaExtDao.getCatalogoEstadosDerechoInt();
	}

	public List<TipoPagoInt> obtieneTiposPagoInt(Boolean isCAEV) throws BusinessException {
		return calendarioEmisionesDeudaExtDao.getCatalogoTiposPagoInt(isCAEV);
	}

	public List<Custodio> obtieneCatalogoCustodios() throws BusinessException {
		return calendarioEmisionesDeudaExtDao.getCatalogoCustodios();
	}
	/**
	 * Actualiza un estado en la BD
	 */
	public Integer actualizarEstadosDerechoInt(Set<Long> ids,
			Integer nuevoEstado,boolean esSu) throws BusinessException {
		Integer actualizados = null;
		if (ids!=null && ids.size()>0){
			//actualizamos calendario
			actualizados = calendarioEmisionesDeudaExtDao.actualizarEstadosDerechoInt(ids, nuevoEstado);
			if(!esSu) {
				if(nuevoEstado != null && (nuevoEstado == Constantes.CALENDARIO_DERECHOS_AUTORIZADO ||
						nuevoEstado == Constantes.CALENDARIO_DERECHOS_PRELIQUIDADO)){
					//creamos Notificacion
					List<CalendarioDerechos> cals = calendarioEmisionesDeudaExtDao.consultaCalendarioDerechosByIds(ids);
					XStream xstream = new XStream();
					Annotations.configureAliases(xstream, NotificacionAccion.class);
					Annotations.configureAliases(xstream, DerechoMensaje.class);

					NotificacionAccion msg = new NotificacionAccion();
					msg.setAccion(NotificacionAccion.TipoAccion.CAMBIO_ESTADO);

					if(nuevoEstado == Constantes.CALENDARIO_DERECHOS_AUTORIZADO){
						msg.setEstado(NotificacionAccion.TipoEstado.AUTORIZADO);
					}else if(nuevoEstado == Constantes.CALENDARIO_DERECHOS_PRELIQUIDADO){
						msg.setEstado(NotificacionAccion.TipoEstado.PRELIQUIDADO);
					}
					//agregamos los datos del calendario
					for(CalendarioDerechos cd : cals){
						msg.addCalendarioDerecho(cd);
					}


				 	//crea El mensaje
				 	final String msgTXT = xstream.toXML(msg);
				 	
				 	String msgFirmado = msgTXT;
				 	try {
						if(validateSignatureService.isSignature()) {
							msgFirmado = signMensaje.signMensaje(msgFirmado);
							log.info(Constante.loggerSignature(false, Constante.PORTAL_INTERNAIONAL, Constante.MOI, msgFirmado));
						}
				 	
					 	final String msgToSend = msgFirmado;
	
				        jmsTemplate.send(new MessageCreator(){
				            public Message createMessage(Session session) throws JMSException {
				                final Message msg = session.createTextMessage(msgToSend);
				                return msg;
				            }
				        });
					} catch (DigitalSignException e) {
						log.error(e.getMessage(), e.getCause());
			            ErrorSeguridadMensajeriaVo error = new ErrorSeguridadMensajeriaVo();
			            error.setError(e.getMessage().split("\\|")[0].trim());
			            error.setModulo(Constante.PORTAL_INTERNAIONAL);
			            error.setXml(msgFirmado);
			            
			            Document doc = XMLUtils.convertStringToDocument(msgFirmado);
			            error.setNumeroSerieCertificado(e.getMessage().split("\\|")[1].trim());
			            error.setTipoMensaje(XMLUtils.getStringToElement(doc, Constante.TIPO_MENSAJE));
			            
			            sendMessageServiceSign.sendQueueAlert(error);
					}
				}
			}
		}
	return actualizados;
	}

	public void instruyeMensajeRetiro(Long idCalendario, Long idBoveda) {
		//realiza el update con el id boveda
		CalendarioDerechos cal = (CalendarioDerechos)calendarioEmisionesDeudaExtDao.getByPk(CalendarioDerechos.class,idCalendario);
		//Boveda boveda = calendarioEmisionesDeudaExtDao.consultaBoveda(idBoveda);
		//cal.setIdBovedaEfectivo(boveda);
		//calendarioEmisionesDeudaExtDao.update(cal);

		//manda una notificacion al MOI para que cree el mensaje
		XStream xstream = new XStream();
		Annotations.configureAliases(xstream, NotificacionAccion.class);
		Annotations.configureAliases(xstream, DerechoMensaje.class);
		NotificacionAccion msg = new NotificacionAccion();
		msg.setAccion(NotificacionAccion.TipoAccion.MENSAJE_RETIRO);
		msg.addCalendarioDerecho(cal);
		final String msgRetiroXml = xstream.toXML(msg);
		log.debug("XML103====>"+msgRetiroXml);
		
	 	String msgFirmado = msgRetiroXml;
	 	try {
			if(validateSignatureService.isSignature()) {
				msgFirmado = signMensaje.signMensaje(msgFirmado);
				log.info(Constante.loggerSignature(false, Constante.PORTAL_INTERNAIONAL, Constante.MOI, msgFirmado));
			}
	 	
		 	final String msgToSend = msgFirmado;
	
			jmsTemplate.send(new MessageCreator(){
	            public Message createMessage(Session session) throws JMSException {
	                final Message msg = session.createTextMessage(msgToSend);
	                return msg;
	            }
	        });
		} catch (DigitalSignException e) {
			log.error(e.getMessage(), e.getCause());
            ErrorSeguridadMensajeriaVo error = new ErrorSeguridadMensajeriaVo();
            error.setError(e.getMessage().split("\\|")[0].trim());
            error.setModulo(Constante.PORTAL_INTERNAIONAL);
            error.setXml(msgFirmado);
            
            Document doc = XMLUtils.convertStringToDocument(msgFirmado);
            error.setNumeroSerieCertificado(e.getMessage().split("\\|")[1].trim());
            error.setTipoMensaje(XMLUtils.getStringToElement(doc, Constante.TIPO_MENSAJE));
            
            sendMessageServiceSign.sendQueueAlert(error);
		}
	}

	public List<BitacoraMensajeSwift> consultaBitacoraMensajesSwift(Long id)	throws BusinessException {
		return calendarioEmisionesDeudaExtDao.getBitacoraMensajeSwiftbyId(id);
	}

    @Override
    public List<BitacoraMensajeSwiftVO> consultaBitacoraMensajesSwiftVO(Long id) throws BusinessException {
        return calendarioEmisionesDeudaExtDao.getBitacoraMensajeSwiftbyIdVO(id);
    }
    /* Accessors and mutators */

    /**
     * @param errorResolver
     */
    public void setErrorResolver(MessageResolver errorResolver) {
        this.messageResolver = errorResolver;
    }

    /**
     * @param dateUtilService the dateUtilService to set
     */
    public void setDateUtilService(DateUtilService dateUtilService) {
        this.dateUtilService = dateUtilService;
    }

    /**
     * @param utilService the utilService to set
     */
    public void setUtilService(UtilService utilService) {
    	this.utilService = utilService;
    }

    /**
     * @param validatorService the validatorService to set
     */
    public void setValidatorService(ValidatorService validatorService) {
    	this.validatorService = validatorService;
    }

    /**
     * @param seqFolioControl the seqFolioControl to set
     */
    public void setSeqFolioControl(String seqFolioControl) {
        this.seqFolioControl = seqFolioControl;
    }

    /**
     * @param bitacoraOperacionesDao the bitacoraOperacionesDao to set
     */
    public void setBitacoraOperacionesDao(
            BitacoraOperacionesDao bitacoraOperacionesDao) {
        this.bitacoraOperacionesDao = bitacoraOperacionesDao;
    }

    /**
     * @param validatorDivIntService the validatorDivIntService to set
     */
    public void setValidatorDivIntService(ValidatorDivIntService validatorDivIntService) {
        this.validatorDivIntService = validatorDivIntService;
    }

    /**
     * @param operacionSicDao the operacionSicDao to set
     */
    public void setOperacionSicDao(OperacionSicDao operacionSicDao) {
        this.operacionSicDao = operacionSicDao;
    }

    /**
     * @param catBicDao the catBicDao to set
     */
    public void setCatBicDao(CatBicDao catBicDao) {
        this.catBicDao = catBicDao;
    }

    /**
     * @param sicDetalleDao the sicDetalleDao to set
     */
    public void setSicDetalleDao(SicDetalleDao sicDetalleDao) {
        this.sicDetalleDao = sicDetalleDao;
    }

    /**
     * @param sicEmisionDao the sicEmisionDao to set
     */
    public void setSicEmisionDao(SicEmisionDao sicEmisionDao) {
        this.sicEmisionDao = sicEmisionDao;
    }

    /**
     * @param utilDivIntService the utilDivIntService to set
     */
    public void setUtilDivIntService(UtilDivIntService utilDivIntService) {
        this.utilDivIntService = utilDivIntService;
    }

    /**
     * @param estatusOperacionDao the estatusOperacionDao to set
     */
    public void setEstatusOperacionDao(EstatusOperacionDao estatusOperacionDao) {
        this.estatusOperacionDao = estatusOperacionDao;
    }

    /**
     * @param cuponDao the cuponDao to set
     */
    public void setCuponDao(CuponDao cuponDao) {
        this.cuponDao = cuponDao;
    }

	/**
	 * @param posicionNombradaDao
	 */
	public void setPosicionNombradaDao(PosicionNombradaDao posicionNombradaDao) {
		this.posicionNombradaDao = posicionNombradaDao;
	}

	/**
	 * @param posicionNombradaHDao
	 */
	public void setPosicionNombradaHDao(PosicionNombradaHDao posicionNombradaHDao) {
		this.posicionNombradaHDao = posicionNombradaHDao;
	}

	/**
	 * @param institucionDao
	 */
	public void setInstitucionDao(InstitucionDao institucionDao) {
		this.institucionDao = institucionDao;
	}

	/**
	 * @param cuentaNombradaDao
	 */
	public void setCuentaNombradaDao(CuentaNombradaDao cuentaNombradaDao) {
		this.cuentaNombradaDao = cuentaNombradaDao;
	}

	/**
	 * @param emisionDao
	 */
	public void setEmisionDao(EmisionDao emisionDao) {
		this.emisionDao = emisionDao;
	}

	/**
	 * @param depositosDivIntDao
	 */
	public void setDepositosDivIntDao(DepositosDivIntDao depositosDivIntDao) {
		this.depositosDivIntDao = depositosDivIntDao;
	}

	/**
	 * @param retirosDivIntDao
	 */
	public void setRetirosDivIntDao(RetirosDivIntDao retirosDivIntDao) {
		this.retirosDivIntDao = retirosDivIntDao;
	}

	/**
	 * @param posicionNombradaDivIntDao
	 */
	public void setPosicionNombradaDivIntDao(PosicionNombradaDivIntDao posicionNombradaDivIntDao) {
		this.posicionNombradaDivIntDao = posicionNombradaDivIntDao;
	}

	/**
	 * @param topeCirculanteDao
	 */
	public void setTopeCirculanteDao(TopeCirculanteDao topeCirculanteDao) {
		this.topeCirculanteDao = topeCirculanteDao;
	}

	/**
	 * @param posicionNombradaHDivIntDao
	 */
	public void setPosicionNombradaHDivIntDao(PosicionNombradaHDivIntDao posicionNombradaHDivIntDao) {
		this.posicionNombradaHDivIntDao = posicionNombradaHDivIntDao;
	}

	/**
	 * @param estatusEmisionesDao the estatusEmisionesDao to set
	 */
	public void setEstatusEmisionesDao(EstatusEmisionesDao estatusEmisionesDao) {
		this.estatusEmisionesDao = estatusEmisionesDao;
	}

	/**
	 * @param calendarioEmisionesDeudaExtDao the calendarioEmisionesDeudaExtDao to set
	 */
	public void setCalendarioEmisionesDeudaExtDao(
			CalendarioEmisionesDeudaExtDao calendarioEmisionesDeudaExtDao) {
		this.calendarioEmisionesDeudaExtDao = calendarioEmisionesDeudaExtDao;
	}

	/**
	 * @param jmsTemplate the jmsTemplate to set
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public CalendarioDerechos consultaCalendarioDerechosById(Long id)
			throws BusinessException {
		return (CalendarioDerechos)calendarioEmisionesDeudaExtDao.getByPk(CalendarioDerechos.class,id);
	}

	public List<Boveda> consultaBovedas(Integer tipoBoveda)
			throws BusinessException {

		return calendarioEmisionesDeudaExtDao.consultaBovedas( tipoBoveda);
	}

	/*
		 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#consultaOperacionSicById(java.math.BigInteger)
		 */
		public OperacionSic consultaOperacionSicById(BigInteger id)
				throws BusinessException {
			log.info("Entrando a consultaOperaciones()");
	        OperacionSic operSic = operacionSicDao.findOperacionByFolioControl(id);
	        if (operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE_AUTORIZAR) {
	            operSic.setAutoriza(true);
	        }
	        if (operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CONFIRMADA) {
	            operSic.setLibera(true);
	        }
			if (operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_ENVIADA &&
					operSic.getCatBic() != null &&
					StringUtils.equals(operSic.getCatBic().getBicProd(), BIC_CODE_CHILE)) {
	            operSic.setConfirma(true);
	        }
	        if (operSic.getEstatusOperacion().getIdEstatusOperacion() != ST_OPER_LIBERADA
	                && operSic.getEstatusOperacion().getIdEstatusOperacion() != ST_OPER_ENVIO_LIBERACION
	                && operSic.getEstatusOperacion().getIdEstatusOperacion() != ST_OPER_MENSAJE_LIBERACION
	                && operSic.getEstatusOperacion().getIdEstatusOperacion() != ST_OPER_PENDIENTE_LIBERAR
	                && operSic.getEstatusOperacion().getIdEstatusOperacion() != ST_OPER_CANCELADA
	                && operSic.getEstatusOperacion().getIdEstatusOperacion() != ST_OPER_ENVIO_CANCELACION) {
	            operSic.setCancela(true);
	        }

	        /* Almacena tipo mensaje de la operacion */
	        if (TCP_ENTREGA.equalsIgnoreCase(operSic.getTipoMensaje())) {
	            operSic.setDescTipoMensaje("ENTREGA CONTRA PAGO");
	        }
	        else if (TLP_ENTREGA.equalsIgnoreCase(operSic.getTipoMensaje())) {
	            operSic.setDescTipoMensaje("ENTREGA LIBRE PAGO");
	        }
	        else if (TCP_RECEPCION.equalsIgnoreCase(operSic.getTipoMensaje())) {
	            operSic.setDescTipoMensaje("RECEPCION CONTRA PAGO");
	        }
	        else if (TLP_RECEPCION.equalsIgnoreCase(operSic.getTipoMensaje())) {
	            operSic.setDescTipoMensaje("RECEPCION LIBRE PAGO");
	        }

	        /* Almacena la fecha de acutalizacion correspondiente al estatus */
	        if (operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE_AUTORIZAR
	                || operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_NOTIFICADA
	                || operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE) {
	            operSic.setFechaActualizacion(operSic.getFechaNotificacion());
	        }
	        if (operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_ENVIADA) {
	            operSic.setFechaActualizacion(operSic.getFecha520());
	        }
	        if (operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CONFIRMADA
	        		|| operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CONFIRMADA_PARCIAL) {
	            operSic.setFechaActualizacion(operSic.getFecha530());
	        }
	        if (operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_ENVIO_CANCELACION
	                || operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_CANCELADA) {
	            operSic.setFechaActualizacion(operSic.getFecha592());
	        }
	        if (operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_MENSAJE_LIBERACION
	                || operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_ENVIO_LIBERACION
	                || operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_PENDIENTE_LIBERAR
	                || operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_LIBERADA
	                || operSic.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_LIBERADA_PARCIAL) {
	            operSic.setFechaActualizacion(operSic.getFechaHora());
	        }
	        /* Obtiene el cupon vigente de la operacion */
	        try {
	            Cupon cupon = cuponDao.findCuponByIdEmision(operSic.getEmision().getIdEmision());
	            operSic.setClaveCupon(cupon != null ? cupon.getClaveCupon() : null);
	        } catch(RuntimeException re) {
	        	log.error("Error al obtener Cupon",re);
	        	operSic.setClaveCupon("");
	        }
			return operSic;
	}

public List<Control> obtieneEstadosMensajeria(String id)
			throws BusinessException {
		
		return calendarioEmisionesDeudaExtDao.obtieneEstadosMensajeria(id); 
	}

    /**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#consultaConciliacion(ConciliacionIntDTO conciliacion,PaginaVO paginaVO)
	 */
	public PaginaVO consultaConciliacion(ConciliacionIntDTO conciliacion,PaginaVO paginaVO) throws BusinessException {
		
		return conciliacionIntDao.consultaConciliacion(conciliacion, paginaVO);
	}

	/**
	 * @param conciliacionIntDao the conciliacionIntDao to set
	 */
	public void setConciliacionIntDao(ConciliacionIntDao conciliacionIntDao) {
		this.conciliacionIntDao = conciliacionIntDao;
	}

	/**
	 * @param jmsTemplateMoiConciliaciones the jmsTemplateMoiConciliaciones to set
	 */
	public void setJmsTemplateMoiConciliaciones(
			JmsTemplate jmsTemplateMoiConciliaciones) {
		this.jmsTemplateMoiConciliaciones = jmsTemplateMoiConciliaciones;
	}
	
	/**
	 * Manda una conciliacion al MOI para realizar una conciliacion
	 * @param idConciliacion
	 */
	public void instruyeConciliacion(Long idConciliacion) {	
		log.debug("DivisionInternacionalServiceImpl :: instruyeConciliacion");
		//marca el estado como enviado
		ConciliacionInt conc = (ConciliacionInt)conciliacionIntDao.getByPk(ConciliacionInt.class, idConciliacion);
		
		// Si se encuentra procesando, no se envia el msj al Moi
		if(conc != null && conc.getInProgress() != null && conc.getInProgress()){
			log.debug("El MOI continua procesando el idConciliacion: " + idConciliacion);
			return;
		}

		EstatusConciliacionInt enviado=(EstatusConciliacionInt)conciliacionIntDao.getByPk(EstatusConciliacionInt.class, 2l);
		conc.setEstatusConciliacion(enviado);
		conc.setInProgress(Boolean.TRUE);
		conciliacionIntDao.flush();

		//manda una notificacion al MOIConciliacion para que concilie
		XStream xstream = new XStream();
		Annotations.configureAliases(xstream, OperacionesConciliacionInt.class);
		
		OperacionesConciliacionInt op = new OperacionesConciliacionInt();
		op.setOperacion(TipoOperacionConciliacion.CONCILIA);
		op.setIdConciliacion(idConciliacion);
		
		final String msgRetiroXml = xstream.toXML(op);
		log.debug("XMLConciliacion====>"+msgRetiroXml);
		
		jmsTemplateMoiConciliaciones.send(new MessageCreator(){
            public Message createMessage(Session session) throws JMSException {
                final Message msg = session.createTextMessage(msgRetiroXml);
                return msg;
            }
        });
				
	}
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#consultaDetalleConciliacion(DetalleConciliacionIntDTO conciliacion,PaginaVO paginaVO)
	 */
	public PaginaVO consultaDetalleConciliacion(DetalleConciliacionIntDTO detalleConciliacion, PaginaVO paginaVO)
			throws BusinessException {
		return conciliacionIntDao.consultaDetalleConciliacion(detalleConciliacion, paginaVO);
	}
	/**
	 * muestra los mensajes de la conciliacion
	 * @param id id de la conciliacion
	 */
	public List<BitacoraMensajeConciliacionInt> consultaBitacoraMensajeConciliacionInt(
			Long id) throws BusinessException {
		
		return conciliacionIntDao.consultaBitacoraMensajeConciliacionInt(id);
	}
	/**
	 * genera el reporte de auditoria
	 */
	public void generaReporteAuditoriaConciliacion(Long idConciliacion)
			throws BusinessException {
		//manda una notificacion al MOIConciliacion para que concilie
		XStream xstream = new XStream();
		Annotations.configureAliases(xstream, OperacionesConciliacionInt.class);
		
		OperacionesConciliacionInt op = new OperacionesConciliacionInt();
		op.setOperacion(TipoOperacionConciliacion.REPORTE_AUDITORIA);
		op.setIdConciliacion(idConciliacion);
		
		final String msgRetiroXml = xstream.toXML(op);
		log.debug("XMLConciliacion====>"+msgRetiroXml);
		
		jmsTemplateMoiConciliaciones.send(new MessageCreator(){
            public Message createMessage(Session session) throws JMSException {
                final Message msg = session.createTextMessage(msgRetiroXml);
                return msg;
            }
        });		
	}


	public Map<String, String> getDerechosAutomatizadosDeudaMap() {			
		if(derechosAutomatizadosDeudaMap == null){
			Map<String,String> mapa = new HashMap<String,String>();
			String[] tiposValor = derechosAutomatizadosDeuda.split(",");
			for(String tv : tiposValor){
				mapa.put(tv, tv);
			}
			this.derechosAutomatizadosDeudaMap = mapa;
		}
		
		return this.derechosAutomatizadosDeudaMap;
			
	}
	
	
	/**
	 * Regresa la bitacora swift filtrando por el id
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public List<BitacoraMensajeSwift> consultaBitacoraMensajesSwiftByHist(Long id)	throws BusinessException {
		return calendarioEmisionesDeudaExtDao.getBitacoraMensajeSwiftbyIdHist(id);
	}

    @Override
    public List<BitacoraMensajeSwiftVO> getBitacoraMensajeSwiftbyIdHistVO(Long id) throws BusinessException {
        return calendarioEmisionesDeudaExtDao.getBitacoraMensajeSwiftbyIdHistVO(id);
    }

    /**
     * Regresa la bitacora swift filtrando por el id
     * @param id
     * @return
     * @throws BusinessException
     */
    public List<BitacoraMensajeSwiftVO> consultaBitacoraMensajesSwiftByHistVO(Long id)	throws BusinessException {
        return calendarioEmisionesDeudaExtDao.getBitacoraMensajeSwiftbyIdHistVO(id);
    }


    /**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#realizarCambioDeBoveda(List<SicEmision>,long)
	 */
	public void realizarCambioDeBoveda(List<SicEmision> listaEmisiones, long idCuentaBoveda) throws BusinessException {
        
        try {
            //Se obtiene primero el idBoveda usando el idCuentaBoveda que realmente es el ID_CUENTA_NOMBRADA de C_CATBIC.
            Bovedas bov = this.bovedaService.getBovedaByIdCuentaBoveda(Integer.valueOf(idCuentaBoveda+""));
            //Se procede a los cambios necesarios.
            this.cambiaBovedaAEmision(listaEmisiones, bov.getIdBoveda().longValue());
            this.cambiaBovedaARelacionEmisionBoveda(listaEmisiones, bov.getIdBoveda().longValue());
            this.cambiaCustodioAEmision(listaEmisiones, idCuentaBoveda);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(messageResolver.getMessage("J0141"));
        }
	}

	private void cambiaCustodioAEmision(List<SicEmision> listaEmisiones, long idCuentaNombradaCatBic) throws BusinessException {
        CatBic nuevoCatbic = this.catBicDao.getCatBicEntityByIdCuentaNombrada(idCuentaNombradaCatBic);
        SicEmision sicEmisionExistenteConNuevoCatbic = null;
        for (SicEmision se : listaEmisiones) {
            try {
                sicEmisionExistenteConNuevoCatbic = this.sicEmisionDao.findSicEmisionVigente(idCuentaNombradaCatBic, 
                                                                                             se.getEmision().getIdEmision(), 
                                                                                             nuevoCatbic.getIdCatbic());
                if (sicEmisionExistenteConNuevoCatbic != null) {
                    //Pone en vigente, se actualiza fecha y con los mismos datos del actual SicEmisiones de la iteracion.
                    sicEmisionExistenteConNuevoCatbic.setFormaOper(se.getFormaOper());
                    sicEmisionExistenteConNuevoCatbic.setFechaHora(this.dateUtilService.getCurrentDate());
                    sicEmisionExistenteConNuevoCatbic.setAplicacion(se.getAplicacion());
                    sicEmisionExistenteConNuevoCatbic.setEstatusRegistro(Constantes.ESTATUS_REGISTRO_VIGENTE);
                    this.sicEmisionDao.guardaActualizaEmision(sicEmisionExistenteConNuevoCatbic);
                    this.sicEmisionDao.flush();
                }
                else {
                    //Nueva entidad SicEmision con idCuentaNombradaCatBic, se.getEmision().getIdEmision(), nuevoCatbic.getIdCatbic()
                    SicEmision sicEmision = new SicEmision();
                    CuentaNombrada cn = new CuentaNombrada();
                    sicEmision.setCuentaNombrada(cn);
                    sicEmision.getCuentaNombrada().setIdCuentaNombrada(idCuentaNombradaCatBic);
                    Emision emi = new Emision();
                    sicEmision.setEmision(emi);
                    sicEmision.getEmision().setIdEmision(se.getEmision().getIdEmision());
                    sicEmision.setFormaOper(se.getFormaOper());
                    sicEmision.setFechaHora(this.dateUtilService.getCurrentDate());
                    sicEmision.setAplicacion(se.getAplicacion());
                    sicEmision.setCatBic(nuevoCatbic);
                    sicEmision.setEstatusRegistro(Constantes.ESTATUS_REGISTRO_VIGENTE);
                    this.sicEmisionDao.guardaActualizaEmision(sicEmision);
                    this.sicEmisionDao.flush();
                }

                //Poner en CANCELADO el actual SicEmisiones del listado
                se.setFechaHora(this.dateUtilService.getCurrentDate());
                se.setEstatusRegistro(Constantes.ESTATUS_REGISTRO_CANCELADO);
                this.sicEmisionDao.guardaActualizaEmision(se);
                this.sicEmisionDao.flush();
            } catch (ConstraintViolationException cve) {
                log.error("Error al intentar dar de alta la entidad SicEmision - registro duplicado", cve);
                throw new BusinessException("Error al intentar dar de alta la entidad SicEmision - registro duplicado");
            } catch (DataIntegrityViolationException dive) {
                log.error("Error al intentar dar de alta la entidad SicEmision - integridad de datos", dive);
                throw new BusinessException("Error al intentar dar de alta la entidad SicEmision - integridad de datos");
            } catch (Exception e) {
                log.error("Error al intentar dar de alta la entidad SicEmision", e);
                throw new BusinessException("Error al intentar dar de alta la entidad SicEmision");
            }
	    }
	}

    private void cambiaBovedaARelacionEmisionBoveda(List<SicEmision> listaEmisiones, long idBoveda) throws BusinessException {
        try {
            long idEmision = 0;
            for (SicEmision sem : listaEmisiones) {
                idEmision = sem.getEmision().getIdEmision();
                List<REmisionBoveda> remisionesbovedas = this.rEmisionBovedaDao.getByIdEmision(idEmision);
                if (remisionesbovedas == null || remisionesbovedas.size() == 0) {
                    throw new BusinessException(messageResolver.getMessage("J0142"));
                }
                for (REmisionBoveda reb : remisionesbovedas) {
                    reb.setIdBoveda(idBoveda);
                    this.rEmisionBovedaDao.update(reb);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(messageResolver.getMessage("J0144"));
        }
    }

	private void cambiaBovedaAEmision(List<SicEmision> listaEmisiones, long idBoveda) throws BusinessException {
        try {
            Emisiones emision = null;
            for (SicEmision sem : listaEmisiones) {
                emision = (Emisiones) this.emisionesConsultasDao.getById(sem.getEmision().getIdEmision());
                emision.setIdBoveda(Integer.valueOf(idBoveda+""));
                this.emisionesConsultasDao.update(emision);
                emision = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(messageResolver.getMessage("J0143"));
        }
	}

    /*
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#validarExistenciaTipoValor(String)
     */
	public boolean validarExistenciaTipoValor(String tv) throws BusinessException {
        log.info("\n\n####### Entrando a validarExistenciaTipoValor()...");
        log.info("\n\n####### Tipo Valor a validar=[" + tv + "]");
	    try {
	        Instrumento instrumento = this.getEmisionDaoInt().findInstrumentoByTv(tv);
	        return (instrumento != null ? true : false);
	    } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Error al validar la existencia del tipo valor [" + tv + "].");
	    }
	}

	/*
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getTiposBeneficiario()
	 */
    public List<TipoBeneficiario> getTiposBeneficiario() throws BusinessException {
        log.info("\n\n####### Entrando a getTiposBeneficiario()...");
        
        try {
            return this.getTipoBeneficiarioDao().getTiposBeneficiario();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Error al obtener los tipos de beneficiarios.");
        }
    }

    /*
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getTipoBeneficiarioById(Long)
     */
    public TipoBeneficiario getTipoBeneficiarioById(Long id) throws BusinessException {
        log.info("\n\n####### Entrando a getTipoBeneficiarioById()...");

        try {
            return ((TipoBeneficiario) this.getTipoBeneficiarioDao().getByPk(TipoBeneficiario.class, id));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Error al obtener el tipo de beneficiario con id=[" + id + "].");
        }
    }

    /*
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#validarNuevaBovedaEnEmision(Long, Long)
     */
    public boolean validarNuevaBovedaEnEmision(Long idEmision, Long idBoveda) throws BusinessException {
        log.info("\n\n####### Entrando a validarNuevaBovedaEnEmision()...");

        try {
            Emision emisionBD = (Emision) this.emisionDaoInt.getByPk(Emision.class, idEmision);
            List<REmisionBoveda> rEmisionBovedas = this.rEmisionBovedaDao.getByIdEmision(idEmision);
            boolean bovedaEncontradaEnREmision = false;
            for (REmisionBoveda rEmisionBoveda : rEmisionBovedas) {
                if (rEmisionBoveda.getIdBoveda().equals(idBoveda)) {
                    bovedaEncontradaEnREmision = true;
                    break;
                }
            }
            if (emisionBD.getIdBoveda().equals(idBoveda) && bovedaEncontradaEnREmision) {
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Error al validar que el cambio de la B\u00F3veda [" + idBoveda + 
                                        "] est\u00E1 aplicado en la Emisi\u00F3n [" + idEmision + "]" + 
                                        " y en su relaci\u00F3n Emisi\u00F3n-B\u00F3veda.");
        }
    }

    /*
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#existePosicionEnCuenta5001Indeval(Long, Long, Long)
     */
    public boolean existePosicionEnCuenta5001Indeval(Long idOperacionSic, Long idBoveda, Long idEmision) 
            throws BusinessException {
        boolean estaPosicionTotalEnCuenta5001Indeval = false;
        try {
            Long posicionTotalOperacionSicEntregaCambioBoveda = this.sicEmisionDao.getPosicionTotalOperacionSicCambioBoveda(idOperacionSic);
            Long idCuentaNombrada5001Indeval = this.sicEmisionDao.getIdCuentaNombrada5001Indeval();
            Long posicionDisponibleCta5001Indeval = this.sicEmisionDao.getPosicionDisponibleCta5001Indeval(
                    idCuentaNombrada5001Indeval, idBoveda, idEmision);
            if (posicionTotalOperacionSicEntregaCambioBoveda.equals(posicionDisponibleCta5001Indeval)) {
                estaPosicionTotalEnCuenta5001Indeval = true;
            }

            return estaPosicionTotalEnCuenta5001Indeval;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Error al validar la existencia de la posici\u00F3n en la cuenta 5001 de Indeval.");
        }
    }

    /*
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#actualizaOperacionSICCambioBovedaAutorizador(List<OperacionSic>, String)
     */
    public void actualizaOperacionSICCambioBovedaAutorizador(List<OperacionSic> operacionesSic, String autorizo) throws BusinessException {
        log.info("\n\n####### Entrando a actualizaOperacionSICCambioBovedaAutorizador()...");
        XStream xstream = new XStream();
        Annotations.configureAliases(xstream, EstadoMensajeSic.class);
        EstadoMensajeSic msg = new EstadoMensajeSic();
        msg.setAccion(EstadoMensajeSic.TipoAccion.CAMBIO_ESTADO_CAMBIO_BOVEDA);

        if (operacionesSic.isEmpty()) {
            throw new BusinessException(messageResolver.getMessage("J0047", 
                                        (Object)"de operaciones para actualizar"));
        }

        for (OperacionSic operacionSic : operacionesSic) {
            if (operacionSic.getIdOperacionesSic() == null) {
                throw new BusinessException(messageResolver.getMessage("J0026", 
                                            (Object)"el Id de la operacion - Folio[" + 
                                                operacionSic.getFolioControl().toString() + "]"));
            }
            OperacionSic operacionActual = operacionSic;
            operacionActual = operacionSicDao.findOperacionByIdOperacion(operacionActual.getIdOperacionesSic());
            if (operacionActual == null) {
                throw new BusinessException(messageResolver.getMessage("J0026", (Object)"la operacion -- Id[" + 
                                            operacionSic.getIdOperacionesSic().toString() + "], " +
                                            "Folio[" + operacionSic.getFolioControl().toString() + "]"));
            }
            if (operacionActual.getEstatusOperacion() == null || 
                operacionActual.getEstatusOperacion().getIdEstatusOperacion() == null) {
                throw new BusinessException(messageResolver.getMessage("J0052"));
            }
            if (operacionActual.getEstatusOperacion().getIdEstatusOperacion().equals(ST_OPER_LIBERADA)) {
                operacionActual.setRefMsjPfi(null);
                operacionActual.setStPfi548(null);
                operacionActual.setFechaHora(null);
                operacionActual.setFechaCambio(null);
            }

            boolean enviaMensajeAMOI = false;

            switch (operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().intValue()) {
                case 1: // PENDIENTE AUTORIZAR
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, 
                                                                           ST_OPER_PENDIENTE_AUTORIZAR));
                    operacionActual.setFechaNotificacion(dateUtilService.getCurrentDate()); 
                    break;
                case 2: // NOTIFICADA
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, ST_OPER_NOTIFICADA));
                    operacionActual.setFechaNotificacion(dateUtilService.getCurrentDate()); 
                    break;
                case 3: // PENDIENTE
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, ST_OPER_PENDIENTE));
                    operacionActual.setFechaNotificacion(dateUtilService.getCurrentDate()); 
                    break;
                case 4: // ENVIADA
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, ST_OPER_ENVIADA));
                    operacionActual.setFecha520(dateUtilService.getCurrentDate()); 
                    operacionActual.setFecha530(null); 
                    break;
                case 5: // CONFIRMADA
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, ST_OPER_CONFIRMADA));
                    operacionActual.setFecha530(dateUtilService.getCurrentDate()); 
                    break;
                case 6: // MENSAJE LIBERACION
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, 
                                                                           ST_OPER_MENSAJE_LIBERACION));
                    operacionActual.setFechaHora(dateUtilService.getCurrentDate()); 
                    break;
                case 7: // ENVIO LIBERACION
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, 
                                                                           ST_OPER_ENVIO_LIBERACION));
                    operacionActual.setFechaHora(dateUtilService.getCurrentDate()); 
                    break;
                case 8: // PENDIENTE LIBERAR
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, 
                                                                           ST_OPER_PENDIENTE_LIBERAR));
                    operacionActual.setFechaHora(dateUtilService.getCurrentDate()); 
                    break;
                case 9: // LIBERADA
                    if (operacionActual.getTipoMensaje().equals(TLP_RECEPCION)) {
                        log.info("\n\n####### Entrando a cambio de estado LIBERADA con TLP_RECEPCION...");
                        enviaMensajeAMOI = true;
                        msg.setEstado(EstadoMensajeSic.TipoEstado.LIBERAR);
                    }
                    else {
                        operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                               EstatusOperacion.class, ST_OPER_LIBERADA));
                        operacionSic.setFechaHora(new Date());
                        operacionSic.setFechaCambio(new Date());
                        operacionSic.setStPfi548("LIQ");
                        operacionSic.setRefMsjPfi("LIQUIDADA");
                    }
                    break;
                case 10: // ENVIO CANCELACION
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, 
                                                                           ST_OPER_ENVIO_CANCELACION));
                    operacionActual.setFecha592(dateUtilService.getCurrentDate());
                    break;
                case 11: // CANCELADA
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, ST_OPER_CANCELADA));
                    operacionActual.setFecha592(dateUtilService.getCurrentDate());
                    operacionActual.setMensajeEfectivo(null);
                    break;
                case 12: // RETENIDA
                    if (operacionActual.getTipoMensaje().equals(TLP_ENTREGA)) {
                        log.info("\n\n####### Entrando a cambio de estado RETENIDA con TLP_ENTREGA...");
                        enviaMensajeAMOI = true;
                        msg.setEstado(EstadoMensajeSic.TipoEstado.RETENIDO);
                    }
                    else {
                        operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                               EstatusOperacion.class, ST_OPER_RETENIDA));
                        operacionActual.setFechaNotificacion(this.dateUtilService.getCurrentDate());
                    }
                    break;
                case 13: // HABILITADA
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, ST_OPER_HABILITADA));
                    operacionActual.setFechaHoraHabilitada(dateUtilService.getCurrentDate());
                    operacionActual.setFechaHora(dateUtilService.getCurrentDate());
                    break;
                case 14: // CANCEL-SIST
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, ST_OPER_CANCEL_SIST));
                    operacionActual.setFecha592(dateUtilService.getCurrentDate());
                    break;
                case 15: // EN_RECHAZO
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, ST_OPER_EN_RECHAZO));
                    operacionActual.setFechaHora(dateUtilService.getCurrentDate());
                    break;
                case 16: // RECHAZADA
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, ST_OPER_RECHAZADA));
                    operacionActual.setFechaHora(dateUtilService.getCurrentDate());
                    break;
                case 17: // CANCEL-USR
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                                                                           EstatusOperacion.class, ST_OPER_CANCEL_USR));
                    operacionActual.setFecha592(dateUtilService.getCurrentDate());
                    break;
                default:
                    // se realiza el update del estado
                    operacionActual.setEstatusOperacion((EstatusOperacion) this.estatusOperacionDao.getByPk(
                            EstatusOperacion.class, 
                            operacionSic.getBitacoraOperacionesSic().getIdEstatusOperacionNuevo().longValue()));
                    operacionActual.setFechaNotificacion(this.dateUtilService.getCurrentDate());
                    break;
            }

            if (!enviaMensajeAMOI) {
                this.operacionSicDao.update(operacionActual);
                this.operacionSicDao.flush();
            }

            BitacoraOperacionesSic bitacora = operacionSic.getBitacoraOperacionesSic();
            bitacora.setIdEstatusBitacora(new Long(3));
            bitacora.setFechaSolicitudAutorizador(new Date());
            bitacora.setCveUsuarioAutorizo(autorizo);
            this.operacionSicDao.update(bitacora);    
            this.operacionSicDao.flush();   

            if (enviaMensajeAMOI) {
                msg.setFechaLiquidacion(DateUtils.format(operacionActual.getFechaLiquidacion(), "yyyy-MM-dd HH:mm:ss") );
                msg.setFolioControl(operacionActual.getFolioControl().toString());
                msg.setId(String.valueOf(operacionActual.getIdOperacionesSic()));
                final String msgTXT = xstream.toXML(msg);

                String msgFirmado = msgTXT;
                try {
                    if (this.validateSignatureService.isSignature()) {
                        msgFirmado = this.signMensaje.signMensaje(msgFirmado);
                        log.info(Constante.loggerSignature(false, Constante.PORTAL_INTERNAIONAL, Constante.MOI, msgFirmado));
                    }
                
                    final String msgToSend = msgFirmado;
                    
                    this.jmsTemplateSic.send(new MessageCreator() {
                        public Message createMessage(Session session) throws JMSException {
                            final Message msgSession = session.createTextMessage(msgToSend);
                            return msgSession;
                        }
                    }); 
                } catch (DigitalSignException e) {
                    log.error(e.getMessage(), e.getCause());
                    ErrorSeguridadMensajeriaVo error = new ErrorSeguridadMensajeriaVo();
                    error.setError(e.getMessage().split("\\|")[0].trim());
                    error.setModulo(Constante.PORTAL_INTERNAIONAL);
                    error.setXml(msgFirmado);

                    Document doc = XMLUtils.convertStringToDocument(msgFirmado);
                    error.setNumeroSerieCertificado(e.getMessage().split("\\|")[1].trim());
                    error.setTipoMensaje(XMLUtils.getStringToElement(doc, Constante.TIPO_MENSAJE));

                    this.sendMessageServiceSign.sendQueueAlert(error);
                }
            } 
        }      
    }

	/**
	 * @return the derechosAutomatizadosDeuda
	 */
	public String getDerechosAutomatizadosDeuda() {
		return derechosAutomatizadosDeuda;
	}


	/**
	 * NOTA: SI SE NECESITA AGREGAR UN TIPO DE DERECHO A AUTOMATIZACION
	 * AGREGARLO EN LA BD CON LA LLAVE moi.deuda.derechosAutomatizados
	 * @param derechosAutomatizadosDeuda the derechosAutomatizadosDeuda to set
	 */
	public void setDerechosAutomatizadosDeuda(String derechosAutomatizadosDeuda) {
		this.derechosAutomatizadosDeuda = derechosAutomatizadosDeuda;
	}	

	/**
	 * Custodios para los cuales no tienen envio de mensajes a traves de la red
	 * Swift y que es necesario actualizar su estado de manera manual. Para que
	 * de esa manera se cambie el estado a confirmado.
	 * 
	 * En esta primera implementacion, se ponen los custodios MILA de los depositantes
	 * de Peru, Chile y Colombia. Cuando se vayan agregando a la red Swift te cambiara la 
	 * lista.
	 * 
	 * @param custodiosSinEnvioMensaje - Custodios que no envian mensajes Swift
	 */
	public void setCustodiosSinEnvioMensaje(Set<String> custodiosSinEnvioMensaje) {
		this.custodiosSinEnvioMensaje = custodiosSinEnvioMensaje;
	}

	/**
	 * Metodo para establecer el atributo sicService
	 * @param sicService El valor del atributo sicService a establecer.
	 */
	public void setSicService(SicService sicService) {
		this.sicService = sicService;
	}

    public void setEmisionesConsultasDao(EmisionesConsultasDao emisionesConsultasDao) {
        this.emisionesConsultasDao = emisionesConsultasDao;
    }

    public void setBovedaService(BovedaService bovedaService) {
        this.bovedaService = bovedaService;
    }

    public void setrEmisionBovedaDao(REmisionBovedaDao rEmisionBovedaDao) {
        this.rEmisionBovedaDao = rEmisionBovedaDao;
    }

	public void setSignMensaje(SignMensaje signMensaje) {
		this.signMensaje = signMensaje;
	}

	public void setValidateSignatureService(ValidateSignatureService validateSignatureService) {
		this.validateSignatureService = validateSignatureService;
	}
	
	public void setSendMessageServiceSign(SendMessageService sendMessageServiceSign) {
		this.sendMessageServiceSign = sendMessageServiceSign;
	}

    public com.indeval.portalinternacional.persistence.dao.EmisionDao getEmisionDaoInt() {
        return emisionDaoInt;
    }

    public void setEmisionDaoInt(com.indeval.portalinternacional.persistence.dao.EmisionDao emisionDaoInt) {
        this.emisionDaoInt = emisionDaoInt;
    }

    public TipoBeneficiarioDao getTipoBeneficiarioDao() {
        return tipoBeneficiarioDao;
    }

    public void setTipoBeneficiarioDao(TipoBeneficiarioDao tipoBeneficiarioDao) {
        this.tipoBeneficiarioDao = tipoBeneficiarioDao;
    }

    public CustodioTipoBenefDao getCustodioTipoBenefDao() {
        return custodioTipoBenefDao;
    }

    public void setCustodioTipoBenefDao(CustodioTipoBenefDao custodioTipoBenefDao) {
        this.custodioTipoBenefDao = custodioTipoBenefDao;
    }


	@Override
	public List<LiquidacionParcialMoi> getLiqParcialMoi(Long folioControl) {
		log.debug("DivisionInternacionalServiceImpl :: getLiqParcialMoi");
		return settlementDisciplineRegimeDao.getLiqParcialMoi(folioControl);
	}
	
	@Override
	public PaginaVO getLiqParcialMoi(PaginaVO paginaVO, Long folioControl) {
		log.debug("DivisionInternacionalServiceImpl :: getLiqParcialMoi(paginaVO , folioControl)");
		paginaVO =  settlementDisciplineRegimeDao.getLiqParcialMoi(paginaVO, folioControl);
		//Si encontro resultados, obtiene el total de montos
		if (paginaVO != null && paginaVO.getRegistros() != null && paginaVO.getRegistros().size() > 0) {
			paginaVO.getValores().put("totalMonto", settlementDisciplineRegimeDao.getTotalLiquidacionesParciales(folioControl, Boolean.FALSE));
			paginaVO.getValores().put("totalMontoEfectivo", settlementDisciplineRegimeDao.getTotalLiquidacionesParciales(folioControl, Boolean.TRUE));
		}
		return paginaVO;
	}
	
	@Override
	public PaginaVO getLiqParcialMoiWithBitacora(PaginaVO paginaVO, List<BigInteger> folioControl) {
		log.debug("DivisionInternacionalServiceImpl :: getLiqParcialMoiWithBitacora: " + folioControl);
		List<BitacoraOperacionesSic> bitacorasEncontradas = operacionSicDao.obtieneBitacoraOperacionSic(folioControl);
		List<LiquidacionParcialMoi> lstLiquidacionParcialMoi = new ArrayList<LiquidacionParcialMoi>();
		for (BitacoraOperacionesSic bitacoraOperacionesSic : bitacorasEncontradas) {
			LiquidacionParcialMoi liqParcialMoi = settlementDisciplineRegimeDao.getLiqParcialMoiByFolioControlAndParcialidad(bitacoraOperacionesSic.getFolioControl().longValue(), bitacoraOperacionesSic.getNumeroLiquidacion());
			if(liqParcialMoi != null){
				liqParcialMoi.setBitacoraOperacionesSic(bitacoraOperacionesSic);
				lstLiquidacionParcialMoi.add(liqParcialMoi);
			}
		}
		
		paginaVO.setRegistros(lstLiquidacionParcialMoi);
		paginaVO.setTotalRegistros(lstLiquidacionParcialMoi.size());
		return paginaVO;
	}
	
	@Override
	public PaginaVO getLiqParcialMoiChangeStatus(PaginaVO paginaVO, Long folioControl) {
		log.debug("DivisionInternacionalServiceImpl :: getLiqParcialMoiChangeStatus");
		paginaVO =  settlementDisciplineRegimeDao.getLiqParcialMoi(paginaVO, folioControl);
		//Si encontro resultados, obtiene el total de montos
		if (paginaVO != null && paginaVO.getRegistros() != null && paginaVO.getRegistros().size() > 0) {
			List<LiquidacionParcialMoi> lstLiquidacionParcialMoi = new ArrayList<LiquidacionParcialMoi>();
            for (final Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
            	LiquidacionParcialMoi liquidacionParcial = (LiquidacionParcialMoi) iter.next();
            	List<BitacoraOperacionesSic> bitacoraOperacionSic = operacionSicDao.obtieneBitacoraOperacionSic(folioControl, liquidacionParcial.getNumeroLiquidacion());
            	if(bitacoraOperacionSic != null && bitacoraOperacionSic.size() == 1){
            		liquidacionParcial.setCancelo(false);
            		liquidacionParcial.setBitacoraOperacionesSic(bitacoraOperacionSic.get(0));
            	}
            	lstLiquidacionParcialMoi.add(liquidacionParcial);
            }
            
            paginaVO.setRegistros(lstLiquidacionParcialMoi);
		}
		return paginaVO;
	}

	@Override
	public void actualizaLiquidacionesParciales(Long folioControl, Long numeroLiquidacion, Long idEstatusOperacion) {
		log.debug("DivisionInternacionalServiceImpl :: liberarLiquidacionesParciales");
		settlementDisciplineRegimeDao.actualizaLiquidacionesParciales(folioControl, numeroLiquidacion, idEstatusOperacion);
	}
	
	public Long getEstatusOperacionSicOfParcialidad(List<LiquidacionParcialMoi> lstLiquidacionParcial){
		log.debug("UtilMoiSic :: getEstatusOperionSicForParcialidad");
		Long idEstatusOperacion = null;
			int statusPendLiberarParcial = 0;
			for (LiquidacionParcialMoi liquidacionParcialMoi : lstLiquidacionParcial) {
				if(liquidacionParcialMoi.getEstatusOperacion().getIdEstatusOperacion().equals(ST_OPER_CONFIRMADA_PARCIAL)){
					return ST_OPER_CONFIRMADA_PARCIAL;
				} else if(liquidacionParcialMoi.getEstatusOperacion().getIdEstatusOperacion().equals(ST_OPER_PENDIENTE_LIBERAR_PRCIAL)){
					statusPendLiberarParcial++;
				}
			}
			
			if(statusPendLiberarParcial > 0){
				idEstatusOperacion = ST_OPER_PENDIENTE_LIBERAR_PRCIAL;
			} else {
				idEstatusOperacion = ST_OPER_LIBERADA_PARCIAL;
			}
		
		return idEstatusOperacion;
	}


	@Override
	public Boolean actualizaParcialidadOperacionSIC(OperacionSic operacionSic) throws BusinessException {
	    log.info("DivisionInternacionalServiceImpl :: actualizaParcialidadOperacionSIC");
        if (operacionSic.getIdOperacionesSic() == null) {
            throw new BusinessException(messageResolver.getMessage("J0026", (Object)"el Id de la operacion"));
        }
        
        //Valida la operacion
        OperacionSic operacionActual = operacionSic;
        log.info("OperacionSIC :: " + operacionActual.toString());
        
        operacionActual = this.operacionSicDao.findOperacionByIdOperacion(operacionActual.getIdOperacionesSic());
        if (operacionActual == null) {
            throw new BusinessException(this.messageResolver.getMessage("J0026", (Object)"la operacion -- Id[" + operacionSic.getIdOperacionesSic().toString() + "]"));
        }
        if (operacionActual.getEstatusOperacion() == null || operacionActual.getEstatusOperacion().getIdEstatusOperacion() == null) {
            throw new BusinessException(this.messageResolver.getMessage("J0052"));
        }
        
        int oldVersion = operacionActual.getVersion().intValue();
        //Se valida que la operacion sic que se desea liberar (y que sea recepcion) que continue en estado 
        //confirmado = 5. Si no se encuentra en ese estado NO se envia mensaje a MOI y se continua con la 
        //siguiente operacion. 
        Boolean isLiberaOperacion = Boolean.TRUE;
        if (operacionSic.isLibero() && 
            (operacionActual.getTipoMensaje().equals(Constantes.MT_540) || 
            operacionActual.getTipoMensaje().equals(Constantes.MT_541))) {
            List<Object> dataForComparation = this.operacionSicDao.getDataToCompareForUpdate(operacionActual.getIdOperacionesSic());
            if (dataForComparation != null) {
                Long estadoRealActual = (Long) dataForComparation.get(0);
                Integer versionRealActual = (Integer) dataForComparation.get(1);      	
                if (!(estadoRealActual.equals(Constantes.ST_OPER_CONFIRMADA_PARCIAL))) {
                	if (!(estadoRealActual.equals(Constantes.ST_OPER_LIBERADA_PARCIAL)) || versionRealActual.intValue() != oldVersion) {
                        log.warn("\n\n####### ST_OPER_CONFIRMADA Se intento liberar una parcialidad de la operacion SIC que tiene un estado actual=[" +
                                estadoRealActual + "] y no [18/Confirmada Parcial] / o [19/Liberada Parcial]" +
                                "la version del registro actual=[" + versionRealActual.intValue() + "] " + 
                                "no corresponde con la version anterior=[" + oldVersion + "]. Operacion con id=[" + 
                                operacionActual.getIdOperacionesSic() + "] y folioControl=[" + 
                                operacionActual.getFolioControl() + "].\n\n");
                        isLiberaOperacion = Boolean.FALSE;
                	}
                } 
            }
        }
        
        if(isLiberaOperacion){
            operacionActual.setVersion(oldVersion + 1);
            this.sicService.actualizarOperacionSic(1, operacionSic, operacionActual, dateUtilService.getCurrentDate());
        }

    	log.info("Se procede a liberar la parcialidad: " + isLiberaOperacion);
	    return isLiberaOperacion;
	}


	/**
	 * @param settlementDisciplineRegimeDao the settlementDisciplineRegimeDao to set
	 */
	public void setSettlementDisciplineRegimeDao(SettlementDisciplineRegimeDao settlementDisciplineRegimeDao) {
		this.settlementDisciplineRegimeDao = settlementDisciplineRegimeDao;
	}


	@Override
	public Long findOperacionesParcialesPendientes(Long idCuentaNombrada) {
		log.debug("DivisionInternacionalServiceImpl :: findOperacionesParcialesPendientes");
		return operacionSicDao.findOperacionesParcialesPendientes(idCuentaNombrada);
	}


	@Override
	public void updateStatusOperacionWithParcialidad(List<LiquidacionParcialMoi> lstLiquidacionParcialMoi, OperacionSic operacionSic) {
		log.debug("DivisionInternacionalServiceImpl :: updateStatusOperacionWithParcialidad");
		Long idEstatusOperacion = null;
		int statusPendLiberarParcial = 0;
		for (LiquidacionParcialMoi liquidacionParcialMoi : lstLiquidacionParcialMoi) {
			if(liquidacionParcialMoi.getEstatusOperacion().getIdEstatusOperacion().equals(ST_OPER_CONFIRMADA_PARCIAL)){
				idEstatusOperacion = ST_OPER_CONFIRMADA_PARCIAL;
				break;
			} else if(liquidacionParcialMoi.getEstatusOperacion().getIdEstatusOperacion().equals(ST_OPER_PENDIENTE_LIBERAR_PRCIAL)){
				statusPendLiberarParcial++;
				break;
			}
		}
		
		if(idEstatusOperacion != null && idEstatusOperacion == ST_OPER_CONFIRMADA_PARCIAL){
			log.debug("UtilMoiSic :: updateStatusOperacionWithParcialidad ST_OPER_CONFIRMADA_PARCIAL");
		} else if(statusPendLiberarParcial > 0){
			idEstatusOperacion = ST_OPER_PENDIENTE_LIBERAR_PRCIAL;
		} else {
			int liberadaParcial = 0;
			for (LiquidacionParcialMoi liquidacionParcialMoi : lstLiquidacionParcialMoi) {
				if(liquidacionParcialMoi.getEstatusOperacion().getIdEstatusOperacion() == ST_OPER_LIBERADA_PARCIAL){
					liberadaParcial++;
				}
			}
			if(operacionSic != null && operacionSic.getRemanente() != null && operacionSic.getRemanente().longValue() == 0l
					&& liberadaParcial == lstLiquidacionParcialMoi.size()){
				idEstatusOperacion = ST_OPER_LIBERADA;
			} else {
				idEstatusOperacion = ST_OPER_LIBERADA_PARCIAL;				
			}
		}

		operacionSic.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, idEstatusOperacion));
		operacionSicDao.update(operacionSic);
		operacionSicDao.flush();
	}

}
