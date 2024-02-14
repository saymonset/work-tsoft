/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.divisioninternacional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;

/**
 * @author javiles
 *
 */
public class ITestBusinessRulesCatpuraTraspaso_1 extends BaseITestService {

    /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(ITestBusinessRulesCatpuraTraspaso_1.class);

    /** Servicio que sera probado en esta prueba */
    private DivisionInternacionalService divisionInternacionalService;

    /**
     * @see com.indeval.portalinternacional.portalinternacional.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (divisionInternacionalService == null) {
            divisionInternacionalService = (DivisionInternacionalService) applicationContext
                    .getBean("divisionInternacionalService");
        }
    }
    
    /**
     * TestCase para probar divisionInternacionalService.businessRulesCapturaTraspaso()
     */
    public void testBusinessRulesCapturaTraspaso_Dinero()  {
    	
        log.info("Entrando a ITestBusinessRulesCatpuraTraspaso_1.testBusinessRulesCapturaTraspaso_Dinero()");
        
        assertNotNull(divisionInternacionalService);
        
//        CapturaTraspasoParams capturaTraspasoParams = new CapturaTraspasoParams(); 
//        capturaTraspasoParams.setAgenteVOtraspasante(new AgenteVO());
//        capturaTraspasoParams.getAgenteVOtraspasante().setId("01");
//        capturaTraspasoParams.getAgenteVOtraspasante().setFolio("003");
//        capturaTraspasoParams.getAgenteVOtraspasante().setCuenta("0307");
//        capturaTraspasoParams.getAgenteVOtraspasante().setNombreCorto("ACCVAL");
//        capturaTraspasoParams.setAgenteVOReceptor(new AgenteVO());
//        capturaTraspasoParams.getAgenteVOReceptor().setId("01");
//        capturaTraspasoParams.getAgenteVOReceptor().setFolio("001");
//        capturaTraspasoParams.getAgenteVOReceptor().setCuenta("0109");
//        capturaTraspasoParams.getAgenteVOReceptor().setNombreCorto("CBBNORTE");
//        capturaTraspasoParams.setEmisionVO(new EmisionVO());
//        capturaTraspasoParams.getEmisionVO().setIdTipoValor("M");
//        capturaTraspasoParams.getEmisionVO().setEmisora("GOBFED");
//        capturaTraspasoParams.getEmisionVO().setSerie("081224");
//        capturaTraspasoParams.getEmisionVO().setCupon("0000");
//        capturaTraspasoParams.setCantidadOperada(new BigInteger("1000001"));
//        capturaTraspasoParams.setSaldoDisponible(new BigDecimal("156462252"));
//        capturaTraspasoParams.setPosicionActual(new BigDecimal("1789866565"));
//        try {
//            CapturaTraspasoVO capturaTraspasoVO = divisionInternacionalService.businessRulesCapturaTraspaso(capturaTraspasoParams);
//            log.debug("capturaTraspasoVO [" + ReflectionToStringBuilder.toString(capturaTraspasoVO) + "]");
//        }
//        catch (BusinessException e) {
//            e.printStackTrace();
//        }
    }
    
    /**
     * TestCase para probar divisionInternacionalService.businessRulesCapturaTraspaso()
     */
    public void testBusinessRulesCapturaTraspaso_Fideicomiso()  {

    	log.info("Entrando a ITestBusinessRulesCatpuraTraspaso_1.testBusinessRulesCapturaTraspaso_Fideicomiso()");
    	
        assertNotNull(divisionInternacionalService);
        
//        CapturaTraspasoParams capturaTraspasoParams = new CapturaTraspasoParams(); 
//        capturaTraspasoParams.setAgenteVOtraspasante(new AgenteVO());
//        capturaTraspasoParams.getAgenteVOtraspasante().setId("01");
//        capturaTraspasoParams.getAgenteVOtraspasante().setFolio("001");
//        capturaTraspasoParams.getAgenteVOtraspasante().setCuenta("0198");
//        capturaTraspasoParams.getAgenteVOtraspasante().setNombreCorto("BANORTE-TERCEROS TRANSICION");
//        capturaTraspasoParams.setAgenteVOReceptor(new AgenteVO());
//        capturaTraspasoParams.getAgenteVOReceptor().setId("01");
//        capturaTraspasoParams.getAgenteVOReceptor().setFolio("003");
//        capturaTraspasoParams.getAgenteVOReceptor().setCuenta("0307");
//        capturaTraspasoParams.getAgenteVOReceptor().setNombreCorto("ACCVAL");
//        capturaTraspasoParams.setEmisionVO(new EmisionVO());
//        capturaTraspasoParams.getEmisionVO().setIdTipoValor("1");
//        capturaTraspasoParams.getEmisionVO().setEmisora("ICA");
//        capturaTraspasoParams.getEmisionVO().setSerie("*");
//        capturaTraspasoParams.getEmisionVO().setCupon("0001");
//        capturaTraspasoParams.setCantidadOperada(new BigInteger("100"));
//        capturaTraspasoParams.setSaldoDisponible(new BigDecimal("300"));
//        capturaTraspasoParams.setPosicionActual(new BigDecimal("1256487"));
//        try {
//            CapturaTraspasoVO capturaTraspasoVO = divisionInternacionalService.businessRulesCapturaTraspaso(capturaTraspasoParams);
//            log.debug("capturaTraspasoVO [" + ReflectionToStringBuilder.toString(capturaTraspasoVO) + "]");
//        }
//        catch (BusinessException e) {
//            e.printStackTrace();
//        }
    }
    
    /**
     * TestCase para probar divisionInternacionalService.businessRulesCapturaTraspaso()
     */
    public void testBusinessRulesCapturaTraspaso_SinFideicomiso()  {
        
    	log.info("Entrando a ITestBusinessRulesCatpuraTraspaso_1.testBusinessRulesCapturaTraspaso_SinFideicomiso()");
        
    	assertNotNull(divisionInternacionalService);
        
//        CapturaTraspasoParams capturaTraspasoParams = new CapturaTraspasoParams(); 
//        capturaTraspasoParams.setAgenteVOtraspasante(new AgenteVO());
//        capturaTraspasoParams.getAgenteVOtraspasante().setId("01");
//        capturaTraspasoParams.getAgenteVOtraspasante().setFolio("001");
//        capturaTraspasoParams.getAgenteVOtraspasante().setCuenta("0109");
//        capturaTraspasoParams.getAgenteVOtraspasante().setNombreCorto("CBBANORTE");
//        capturaTraspasoParams.setAgenteVOReceptor(new AgenteVO());
//        capturaTraspasoParams.getAgenteVOReceptor().setId("01");
//        capturaTraspasoParams.getAgenteVOReceptor().setFolio("003");
//        capturaTraspasoParams.getAgenteVOReceptor().setCuenta("0307");
//        capturaTraspasoParams.getAgenteVOReceptor().setNombreCorto("ACCVAL");
//        capturaTraspasoParams.setEmisionVO(new EmisionVO());
//        capturaTraspasoParams.getEmisionVO().setIdTipoValor("1");
//        capturaTraspasoParams.getEmisionVO().setEmisora("ALFA");
//        capturaTraspasoParams.getEmisionVO().setSerie("A");
//        capturaTraspasoParams.getEmisionVO().setCupon("0022");
//        capturaTraspasoParams.setCantidadOperada(new BigInteger("1000001"));
//        capturaTraspasoParams.setSaldoDisponible(new BigDecimal("121235645"));
//        capturaTraspasoParams.setPosicionActual(new BigDecimal("12233322225"));
//        try {
//            CapturaTraspasoVO capturaTraspasoVO = divisionInternacionalService.businessRulesCapturaTraspaso(capturaTraspasoParams);
//            log.debug("capturaTraspasoVO [" + ReflectionToStringBuilder.toString(capturaTraspasoVO) + "]");
//        }
//        catch (BusinessException e) {
//            e.printStackTrace();
//        }
    }
    
}
