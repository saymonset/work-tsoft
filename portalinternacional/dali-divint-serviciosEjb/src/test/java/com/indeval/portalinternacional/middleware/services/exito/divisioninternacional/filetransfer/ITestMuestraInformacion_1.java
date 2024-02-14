/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.filetransfer;

import java.util.Iterator;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService;
import com.indeval.portalinternacional.middleware.servicios.vo.CampoArchivoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;
import com.indeval.portalinternacional.middleware.servicios.vo.RegistroProcesadoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
@SuppressWarnings({"unchecked"})
public class ITestMuestraInformacion_1 extends BaseITestService {

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
     * TestCase para probar el metodo fileTransferService.muestraInformacion()
     */
    public void testMuestraInformacion_1() {
    	
    	log.info("Entrando a ITestMuestraInformacion_1.ITestMuestraInformacion_1()");

    	assertNotNull(fileTransferService);
    	
        AgenteVO agenteFirmado = new AgenteVO("01", "003");
        String tipoProceso = "TI";
        
        FileTransferVO fileTransferVO = UtilsFileTransfer.getFileTransferVO(agenteFirmado, tipoProceso);

        try {
			TotalesProcesoVO totalesProcesoVO = fileTransferService.muestraInformacion(fileTransferVO);
			assertNotNull(totalesProcesoVO);
			
			if(totalesProcesoVO != null){
				assertNotNull(totalesProcesoVO.getPaginaVO());
				assertNotNull(totalesProcesoVO.getPaginaVO().getRegistros());
				log.debug("Totales: " + ReflectionToStringBuilder.toString(totalesProcesoVO));
				for (Iterator iter = totalesProcesoVO.getPaginaVO().getRegistros().iterator(); iter.hasNext();) {
				    RegistroProcesadoVO registro = (RegistroProcesadoVO) iter.next();
				    log.debug("Registro: " + ReflectionToStringBuilder.reflectionToString(registro));
				    for (Iterator iterator = registro.getDatos().iterator(); iterator.hasNext(); ) {
				        CampoArchivoVO campoArchivoVO = (CampoArchivoVO) iterator.next();
				        log.debug("Datos: " + ReflectionToStringBuilder.reflectionToString(campoArchivoVO));
				    }
				    for (int i = 0; registro.getMensajesError() != null && i < registro.getMensajesError().length; i++) {
				        log.debug("Mensajes Error: " + registro.getMensajesError()[i]);
				    }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
		}


        log.debug("Saliendo de testFileTransferMuestraInformacion_1()");
        
    }

}
