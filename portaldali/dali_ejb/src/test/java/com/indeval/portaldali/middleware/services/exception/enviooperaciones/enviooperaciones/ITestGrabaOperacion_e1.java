/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.enviooperaciones.enviooperaciones;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Clase de prueba para la grabacion de bitacora_oeraciones de oracle
 * @author Sergio Mena
 *
 */
public class ITestGrabaOperacion_e1 extends BaseITestService {

	/** Objeto de loggeo para ITestEnvioOperacionesService */
    private static final Logger logger = LoggerFactory.getLogger(ITestGrabaOperacion_e1.class);
    
    /** Bean del servicio a ser probado */
    private EnvioOperacionesService envioOperacionesService;
    
    /** Bean de acceso a DateUtilsDao */
    private DateUtilsDao dateUtilsDao;
    
    /** Bean para mercado de dinero */
    private MercadoDineroService mercadoDineroService;
    
    /** Bean para utilService */
    private UtilServices utilService;
    
    /**
     * En este método se inicializan las propiedades que serán utilizadas
     * durante la clase de prueba.
     * 
     * @throws Exception
     */
    protected void onSetUp() throws Exception {

        super.onSetUp();

        if (envioOperacionesService == null) {
            envioOperacionesService = 
                (EnvioOperacionesService) applicationContext.getBean("envioOperacionesService");
        }
        if (dateUtilsDao == null) {
            dateUtilsDao = (DateUtilsDao) applicationContext.getBean("dateUtilsDao");
        }
        if (mercadoDineroService == null) {
            mercadoDineroService = 
                (MercadoDineroService) applicationContext.getBean("mercadoDineroService");
        }
        if (utilService == null) {
            utilService = 
                (UtilServices) applicationContext.getBean("utilService");
        }

    }
    
    /**
     * Metodo que prueba la grabacion de bitacora_operaciones con un traspaso contra pago en oracle
     * @throws Exception
     */
    public void testGrabaOperacionOracleTraspasoContraPago() throws Exception {
		
    	log.info("Entrando a ITestGrabaOperacion_e1.testGrabaOperacionOracleTraspasoContraPago()");
    	
    	TraspasoContraPagoVO traspasoContraPagoVO = new TraspasoContraPagoVO();

    	traspasoContraPagoVO.setCantidadTitulos(new BigInteger("1").longValue());

    	traspasoContraPagoVO.setCupon("0023");
    	traspasoContraPagoVO.setDivisa("MX");
    	traspasoContraPagoVO.setEmisora("AMX");

    	Date dia = new Date();

    	traspasoContraPagoVO.setFechaConcertacion(
                dateUtilsDao.getFechaHoraCero(dateUtilsDao.getDateFechaDB()));
    	traspasoContraPagoVO.setFechaLiquidacion(dia);

    	traspasoContraPagoVO.setFechaRegistro(dia);

    	traspasoContraPagoVO.setPrecio(new BigDecimal("100.00"));

    	traspasoContraPagoVO.setSerie("L");
    	traspasoContraPagoVO.setTasaNegociada(new Double(2));
    	traspasoContraPagoVO.setTasaReferencia(new Double(3));
    	traspasoContraPagoVO.setTipoValor("1");
    	traspasoContraPagoVO.setMonto(new BigDecimal("100.00"));

    	traspasoContraPagoVO.setReferenciaRelacionada(null);

    	traspasoContraPagoVO.setIdFolioCtaReceptora("020228330000");

    	traspasoContraPagoVO.setIdFolioCtaTraspasante("010091206");
    	traspasoContraPagoVO.setTipoInstruccion("V");

    	traspasoContraPagoVO.setReferenciaMensaje(null);
    	traspasoContraPagoVO.setReferenciaOperacion(null);

        BigInteger folioControl = utilService.getFolio("SEQ_FOLIO_FEC2");
//TODO
//    	envioOperacionesService.grabaOperacion(traspasoContraPagoVO,folioControl.toString(), false);
	}
    
    
    /**
     * Metodo que prueba la grabacion de bitacora_operaciones con un traspaso libre de pago en oracle
     * @throws Exception
     */
    public void testGrabaOperacionOracleTraspasoLibrePago() throws Exception {
    	
    	log.info("Entrando a ITestGrabaOperacion_e1.testGrabaOperacionOracleTraspasoLibrePago()");

    	TraspasoLibrePagoVO traspasoLibrePagoVO = new TraspasoLibrePagoVO();
    	
    	Date dia = new Date();
    	
    	traspasoLibrePagoVO.setReferenciaMensaje(null);
    	traspasoLibrePagoVO.setFechaRegistro(dia);
    	
    	traspasoLibrePagoVO.setIdFolioCtaTraspasante("01009120600");
    	
    	traspasoLibrePagoVO.setIdFolioCtaReceptora("020228330");

    	traspasoLibrePagoVO.setTipoValor("1");
    	traspasoLibrePagoVO.setEmisora("AMX");
    	traspasoLibrePagoVO.setSerie("L");
    	traspasoLibrePagoVO.setCupon("0023");
    	traspasoLibrePagoVO.setCantidadTitulos(new BigInteger("1").longValue());
    	traspasoLibrePagoVO.setFechaLiquidacion(dia);
    	traspasoLibrePagoVO.setFechaRegistro(dia);
    	traspasoLibrePagoVO.setTipoInstruccion("V");
    	traspasoLibrePagoVO.setReferenciaOperacion(null);
    	traspasoLibrePagoVO.setReferenciaRelacionada(null);
    	traspasoLibrePagoVO.setCargoParticipante(false);
    	
        BigInteger folioControl = utilService.getFolio("SEQ_FOLIO_FEC2");

        //TODO
//    	envioOperacionesService.grabaOperacion(traspasoLibrePagoVO,folioControl.toString(), false, null, null);
    	
	}

}
