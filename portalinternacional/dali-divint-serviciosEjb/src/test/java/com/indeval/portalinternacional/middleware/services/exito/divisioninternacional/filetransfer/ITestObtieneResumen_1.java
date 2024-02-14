/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.filetransfer;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ResumenVO;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestObtieneResumen_1 extends BaseITestService {

    /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    /** Servicio que sera probado en esta prueba */
    private FileTransferService fileTransferService;

    /**
     * @see com.indeval.portalinternacional.portalinternacional.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (fileTransferService == null) {
        	fileTransferService = (FileTransferService) applicationContext.getBean("fileTransferService");
        }
    }
    
    /**
     * TestCase para probar fileTransferService.obtieneResumen()
     */
    public void testObtieneResumen_1() {
    	
    	log.info("Entrando a ITestObtieneResumen_1.testObtieneResumen_1()");
    	
    	assertNotNull(fileTransferService);
    	
        AgenteVO agenteFirmado = new AgenteVO("02", "061");
        String tipoProceso = "TS";
        FileTransferVO fileTransferVO = UtilsFileTransfer.getFileTransferVO(agenteFirmado, tipoProceso);
        
        try {
            ResumenVO resumenVO = fileTransferService.obtieneResumen(fileTransferVO);
            assertNotNull(resumenVO);
            log.debug("Resumen: " + ReflectionToStringBuilder.toString(resumenVO));
        }
        catch (BusinessException e) {
            log.debug(e.getMessage());
        }

        log.debug("Saliendo de testObtieneResumen()");
        
    }

}
