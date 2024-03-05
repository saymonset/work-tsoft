/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.enviooperaciones.enviooperaciones;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
public class ITestGrabaOperacion_1 extends BaseITestService {
	
	/** Objeto de loggeo para ITestEnvioOperacionesService */
    private static final Logger logger = LoggerFactory.getLogger(ITestGrabaOperacion_1.class);
    
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
		
    	log.info("Entrando a ITestGrabaOperacion_1.testGrabaOperacionOracleTraspasoContraPago() ");
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

    	traspasoContraPagoVO.setIdFolioCtaReceptora("020228330");

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
    	
    	log.info("Entrando a ITestGrabaOperacion_1.testGrabaOperacionOracleTraspasoLibrePago() ");

    	Date dia = new Date();
    	Date diaSinHr = dateUtilsDao.getFechaHoraCero(dia);
    	Integer folioControl = new Integer(123);
    	TraspasoLibrePagoVO traspasoLibrePagoVO = new TraspasoLibrePagoVO();
    	traspasoLibrePagoVO.setReferenciaMensaje("790");
    	traspasoLibrePagoVO.setFechaRegistro(dia);
    	traspasoLibrePagoVO.setIdFolioCtaTraspasante("010030307");
    	traspasoLibrePagoVO.setIdFolioCtaReceptora("010010109");
    	traspasoLibrePagoVO.setTipoValor("M");
    	traspasoLibrePagoVO.setEmisora("GOBFED");
    	traspasoLibrePagoVO.setSerie("081224");
    	traspasoLibrePagoVO.setCupon("0000");
    	traspasoLibrePagoVO.setCantidadTitulos(new BigInteger("1000001").longValue());
    	traspasoLibrePagoVO.setFechaLiquidacion(diaSinHr);
    	traspasoLibrePagoVO.setFechaRegistro(dia);
    	traspasoLibrePagoVO.setTipoInstruccion("T");
    	traspasoLibrePagoVO.setReferenciaOperacion(folioControl.toString());
    	Map datosAdicionales = new HashMap();
    	datosAdicionales.put("PRUEBA1", new BigDecimal("1001"));
    	datosAdicionales.put("PRUEBA2", "HOLA MUNDO");
		String origenRegistro = "DIVINT";
    	
		//TODO
//    	envioOperacionesService.grabaOperacion(traspasoLibrePagoVO, folioControl.toString(), false, (HashMap) datosAdicionales, origenRegistro);

    }
    	
}
