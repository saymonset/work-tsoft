/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.util.utildivint;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.util.UtilDivIntService;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestObtieneCuentaReceptora_1 extends BaseITestService {

    /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(ITestObtieneCuentaReceptora_1.class);

    /** Servicio que sera probado en esta prueba */
    private UtilDivIntService utilDivIntService;

    /**
     * @see com.indeval.portalinternacional.portalinternacional.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (utilDivIntService == null) {
        	utilDivIntService = (UtilDivIntService) applicationContext.getBean("utilDivIntService");
        }
    }
    
    /**
     * TestCase para probar el metodo fileTransferService.almacenaInformacion()
     */
    public void testObtieneCuentaReceptora_1() {
    	
    	log.info("Entrando a ITestObtieneCuentaReceptora_e1.testObtieneCuentaReceptora_1()");
    	
    	assertNotNull(utilDivIntService);
    	
        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("001");
        agenteVO.setCuenta("0030");
    	
    	String cuentaRecep = utilDivIntService.obtieneCuentaReceptora(agenteVO);
    	
    	log.debug("cuentaRecep = [" + cuentaRecep + "]");
    	
    	assertTrue(StringUtils.isNotBlank(cuentaRecep));
        
    }

}
