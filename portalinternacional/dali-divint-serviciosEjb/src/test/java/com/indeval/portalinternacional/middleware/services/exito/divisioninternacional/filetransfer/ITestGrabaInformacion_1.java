/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.filetransfer;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
@SuppressWarnings({"unchecked"})
public class ITestGrabaInformacion_1 extends BaseITestService {

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
     * TestCase para probar el metodo fileTransferService.grabaInformacion()
     */
    public void testGrabaInformacion_1() {
    	
    	log.info("Entrando a ITestGrabaInformacion_1.testGrabaInformacion_1()");
    	
    	assertNotNull(fileTransferService);
    	
        AgenteVO agenteFirmado = new AgenteVO();
        agenteFirmado.setId("01");
        agenteFirmado.setFolio("003");
        String tipoProceso = "TI";
        FileTransferVO fileTransferVO = UtilsFileTransfer.getFileTransferVO(agenteFirmado, tipoProceso);
        fileTransferVO.setConsecProtocolo(new HashMap());
        fileTransferService.grabaInformacion(fileTransferVO);
        log.debug("Se grabo la informacion con exito");

        log.debug("Saliendo de testGrabaInformacion()");

        
    }

}
