/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.util.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.indeval.portaldali.middleware.services.modelo.AgenteVO;

/**
 * Clase de prueba para el servicio dateDiff() de UtilService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestAgenteVOEquals_1 extends AbstractDependencyInjectionSpringContextTests {


    private Logger logger = LoggerFactory.getLogger(ITestAgenteVOEquals_1.class);

    /**
     * Prueba para comparar AgenteVOs
     * 
     * @throws Exception
     */
    public void testAgenteEquals_1() throws Exception {
        
        log.info("Entrando a ITestAgenteVOEquals_1.testAgenteEquals_1()");
        
        AgenteVO agenteVO1 = new AgenteVO();
        AgenteVO agenteVO2 = new AgenteVO();
        assertFalse(agenteVO1.toResumeString() + " == " + agenteVO2.toResumeString(), agenteVO1.equals(agenteVO2));
                
    }
    
    /**
     * Prueba para comparar AgenteVOs
     * 
     * @throws Exception
     */
    public void testAgenteEquals_2() throws Exception {
        
        log.info("Entrando a ITestAgenteVOEquals_1.testAgenteEquals_2()");
        
        AgenteVO agenteVO1 = new AgenteVO();
        agenteVO1.setId("01");
        
        AgenteVO agenteVO2 = new AgenteVO();
        agenteVO2.setId("01");
        
        assertFalse(agenteVO1.toResumeString() + " == " + agenteVO2.toResumeString(), agenteVO1.equals(agenteVO2));
                
    }
    
    /**
     * Prueba para comparar AgenteVOs
     * 
     * @throws Exception
     */
    public void testAgenteEquals_3() throws Exception {
        
        log.info("Entrando a ITestAgenteVOEquals_1.testAgenteEquals_3()");
        
        AgenteVO agenteVO1 = new AgenteVO();
        agenteVO1.setId("01");
        agenteVO1.setFolio("001");
        
        AgenteVO agenteVO2 = new AgenteVO();
        agenteVO2.setId("01");
        agenteVO2.setFolio("003");
        
        assertFalse(agenteVO1.toResumeString() + " == " + agenteVO2.toResumeString(), agenteVO1.equals(agenteVO2));
                
    }
    
    /**
     * Prueba para comparar AgenteVOs
     * 
     * @throws Exception
     */
    public void testAgenteEquals_4() throws Exception {
        
        log.info("Entrando a ITestAgenteVOEquals_1.testAgenteEquals_4()");
        
        AgenteVO agenteVO1 = new AgenteVO();
        agenteVO1.setId("01");
        agenteVO1.setFolio("001");
        
        AgenteVO agenteVO2 = new AgenteVO();
        agenteVO2.setId("01");
        agenteVO2.setFolio("001");
        
        assertFalse(agenteVO1.toResumeString() + " == " + agenteVO2.toResumeString(), agenteVO1.equals(agenteVO2));
                
    }
    
    /**
     * Prueba para comparar AgenteVOs
     * 
     * @throws Exception
     */
    public void testAgenteEquals_5() throws Exception {
        
        log.info("Entrando a ITestAgenteVOEquals_1.testAgenteEquals_5()");
        
        AgenteVO agenteVO1 = new AgenteVO();
        agenteVO1.setId("01");
        agenteVO1.setFolio("001");
        agenteVO1.setCuenta("0020");
        
        AgenteVO agenteVO2 = new AgenteVO();
        agenteVO2.setId("01");
        agenteVO2.setFolio("001");
        agenteVO1.setCuenta("0200");
        
        assertFalse(agenteVO1.toResumeString() + " == " + agenteVO2.toResumeString(), agenteVO1.equals(agenteVO2));
                
    }
    
    /**
     * Prueba para comparar AgenteVOs
     * 
     * @throws Exception
     */
    public void testAgenteEquals_6() throws Exception {
        
        log.info("Entrando a ITestAgenteVOEquals_1.testAgenteEquals_6()");
        
        AgenteVO agenteVO1 = new AgenteVO();
        agenteVO1.setId("01");
        agenteVO1.setFolio("001");
        agenteVO1.setCuenta("0020");
        
        AgenteVO agenteVO2 = new AgenteVO();
        agenteVO2.setId("01");
        agenteVO2.setFolio("001");
        agenteVO2.setCuenta("0020");
        
        assertTrue(agenteVO1.toResumeString() + " == " + agenteVO2.toResumeString(), agenteVO1.equals(agenteVO2));
                
    }

}
