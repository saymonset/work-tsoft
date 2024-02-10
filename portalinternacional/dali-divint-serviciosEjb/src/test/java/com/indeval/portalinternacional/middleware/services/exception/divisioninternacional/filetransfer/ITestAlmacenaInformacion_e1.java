/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exception.divisioninternacional.filetransfer;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService;
import com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.filetransfer.UtilsFileTransfer;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestAlmacenaInformacion_e1 extends BaseITestService {

    /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(ITestAlmacenaInformacion_e1.class);

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
     * TestCase para probar el metodo fileTransferService.almacenaInformacion()
     */
    public void testAlmacenaInformacion_e1() {
    	
    	log.info("Entrando a ITestObtieneCuentaReceptora_e1.testAlmacenaInformacion_e1()");
    	
    	assertNotNull(fileTransferService);
    	
        AgenteVO agenteFirmado = new AgenteVO("02", "061");

        ArrayList<Object> informacion = new ArrayList<Object>();
        informacion.add("0100303071E  C      *     0000                1000TLPR03/jun-200803/jun-200803/jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345");
        informacion.add("0100303071E  C      *     0000                1000TLPE03-jun-200803-jun-200803-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345           0.00MNXHola");
        informacion.add("0100303071E  C      *     0000                1000TCPR03-jun-200803-jun-200803-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345          10.10USD");
        informacion.add("0100303071E  C      *     0000                1000TCPE03-jun-200803-jun-200803-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345          10.10USDabc");

        String tipoProceso = "TD";
        String usuario = "JGAR";
        int offset = 0;

        FileTransferVO fileTransferVO = UtilsFileTransfer.getFileTransferVO(agenteFirmado, tipoProceso, 
        		informacion, offset, usuario);
        
        int proceso = fileTransferService.almacenaInformacion(fileTransferVO);

        assertNotNull(proceso);

        log.debug("Registros Almacenados: " + proceso);

        log.debug("Saliendo de testFileTransferAlmacenaInformacion_1()");
        
    }

}
