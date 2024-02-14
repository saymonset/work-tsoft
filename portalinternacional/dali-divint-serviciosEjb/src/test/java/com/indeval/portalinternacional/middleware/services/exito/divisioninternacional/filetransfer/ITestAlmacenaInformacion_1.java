/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.filetransfer;

import java.util.ArrayList;

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
public class ITestAlmacenaInformacion_1 extends BaseITestService {

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
  /*
  informacion.add("010030030                     US1729671016                1100TLPE12-sep-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345                  Instrucciones especiales - PFI");
  informacion.add("0100303071E  C      *     0000                             102TLPR12-sep-200820-jun-200820-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345                  Instrucciones especiales - SE GRABAN DIRECTO");
  informacion.add("0100303071E  C      *     0000                            1000TLPR12-sep-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345                  Instrucciones especiales - SE GRABAN DIRECTO");
  informacion.add("010030307                     US1729671016                1100TLPR12-sep-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345                  Instrucciones especiales - SE GRABAN DIRECTO");
  informacion.add("0100101091E  C      *     0000US1729671016                2000TLPE12-sep-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345                  Instrucciones especiales");
  informacion.add("010010109                     US1729671016                2100TLPE12-sep-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345                  Instrucciones especiales");
  informacion.add("0100303071E  C      *     0000123456789012                1000TLPR12-sep-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345                  Instrucciones especiales");
  informacion.add("0100303071E  C      *     0000                            2000TLPE12-sep-200809-jun-200806-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345           0.00MXNHola");
  informacion.add("0100303071A  AAUK   N     0000                            2000TCPE12-sep-200809-jun-200806-jun-2008DEUTSCHE BANK AG, NY               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345           0.00MNXHola");
  informacion.add("0100303071A  AAUK   N     0000                            2000TCPR12-sep-200809-jun-200806-jun-2008DEUTSCHE BANK AG, NY               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345           0.00MNXHola");
  informacion.add("0100303071A  AAPL   *     0000                            2000TCPR12-sep-200809-jun-200806-jun-2008DEUTSCHE BANK AG, NY               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345           123 MXNHola");
  informacion.add("0100303071E  C      *     0000                            3000TCPR12-sep-200805-jun-200805-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345          10.10MNX");
  informacion.add("0100303071E  C      *     0000                            4000TCPR12-sep-200805-jun-200805-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345          10.10USD");
  informacion.add("0100303071E  C      *     0000                            5000TCPE12-sep-200805-jun-200805-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345          10.10USDabc");
  informacion.add("0100303071E  C      *     0000                            -6000TCPE05-jun-200802-jun-200806-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345          10.10USD");
  */
    
    /**
     * TestCase para probar el metodo fileTransferService.almacenaInformacion()
     */
//    public void testAlmacenaInformacion_1() {
//    	
//    	log.info("Entrando a ITestObtieneCuentaReceptora_e1.testAlmacenaInformacion_1()");
//    	
//    	assertNotNull(fileTransferService);
//    	
//        AgenteVO agenteFirmado = new AgenteVO("01", "003");
//
//        ArrayList<Object> informacion = new ArrayList<Object>();
//        informacion.add("0100350011   KIMBER B     0000                            1000TLPE18-oct-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789/holam gjfklgjdkfjl/jfgkjdfsklgjsdfl       fffTHE DEPOSITORY TRUST COMPANY                                     abcd/fghij123456/89012345                  Instrucciones especiales - PFI");
//        informacion.add("0100350011   KIMBER B     0000                            1000TLPE18-oct-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789/holam gjfklgjdkfjl/jfgkjdfsklgjsdfl       fffTHE DEPOSITORY TRUST COMPANY                                     abcd/fghij123456/89012345          50.00USDInstrucciones especiales - PFI");
//
//        String tipoProceso = "TI";
//        String usuario = "JGAR";
//        int offset = 0;
//
//        FileTransferVO fileTransferVO = UtilsFileTransfer.getFileTransferVO(agenteFirmado, tipoProceso, 
//        		informacion, offset, usuario);
//        
//        Integer proceso = fileTransferService.almacenaInformacion(fileTransferVO);
//
//        assertNotNull(proceso);
//
//        log.debug("Registros Almacenados: " + proceso);
//
//        log.debug("Saliendo de testFileTransferAlmacenaInformacion_1()");
//        
//    }

    /**
     * TestCase para probar el metodo fileTransferService.almacenaInformacion()
     */
    public void testAlmacenaInformacion_2() {
    	
    	log.info("Entrando a ITestObtieneCuentaReceptora_e1.testAlmacenaInformacion_1()");
    	
    	assertNotNull(fileTransferService);
    	
        AgenteVO agenteFirmado = new AgenteVO("01", "003");

        ArrayList<Object> informacion = new ArrayList<Object>();
        informacion.add("0100312341E  C      *     0000                            1000TLPE17-oct-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789/holam gjfklgjdkfjl/jfgkjdfsklgjsdfl       fffTHE DEPOSITORY TRUST COMPANY                                     abcd/fghij123456/89012345                  Instrucciones especiales - PFI");
        informacion.add("010030030                     US1729671016                1100TLPE17-oct-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345                  Instrucciones especiales - PFI");
        informacion.add("0100303071E  C      *     0000                             102TLPR17-oct-200820-jun-200820-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345                  Instrucciones especiales - SE GRABAN DIRECTO");
        informacion.add("0100303071E  C      *     0000                            1000TLPR17-oct-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345                  Instrucciones especiales - SE GRABAN DIRECTO");
        informacion.add("010030307                     US1729671016                1100TLPR17-oct-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345                  Instrucciones especiales - SE GRABAN DIRECTO");
        informacion.add("0100101091E  C      *     0000US1729671016                2000TLPE17-oct-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345                  Instrucciones especiales");
        informacion.add("010010109                     US1729671016                2100TLPE17-oct-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345                  Instrucciones especiales");
        informacion.add("0100303071E  C      *     0000123456789012                1000TLPR17-oct-200806-jun-200802-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345                  Instrucciones especiales");
        informacion.add("0100303071E  C      *     0000                            2000TLPE18-oct-200809-jun-200806-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345           0.00MXNHola");
        informacion.add("0100303071A  AAUK   N     0000                            2000TCPE15-sep-200809-jun-200806-jun-2008DEUTSCHE BANK AG, NY               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345           0.00MNXHola");
        informacion.add("0100303071A  AAUK   N     0000                            2000TCPR17-oct-200809-jun-200806-jun-2008DEUTSCHE BANK AG, NY               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345           0.00MNXHola");
        informacion.add("0100303071A  AAPL   *     0000                            2000TCPR17-oct-200809-jun-200806-jun-2008DEUTSCHE BANK AG, NY               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345           123 MXNHola");
        informacion.add("0100303071E  C      *     0000                            3000TCPR17-oct-200805-jun-200805-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345          10.10MNXHola");
        informacion.add("0100303071E  C      *     0000                            4000TCPR17-oct-200805-jun-200805-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345          10.10USD");
        informacion.add("0100303071E  C      *     0000                            5000TCPE17-oct-200805-jun-200805-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345          10.10USDabc");
        informacion.add("0100303071E  C      *     0000                            -6000TCPE17-oct-200802-jun-200806-jun-2008THE BANK OF NEW YORK               123456789012345                              abcdefghijTHE DEPOSITORY TRUST COMPANY                                     abcdefghij123456789012345          10.10USD");

        String tipoProceso = "TI";
        String usuario = "JGAR";
        int offset = 0;

        FileTransferVO fileTransferVO = UtilsFileTransfer.getFileTransferVO(agenteFirmado, tipoProceso, 
        		informacion, offset, usuario);
        
        Integer proceso = fileTransferService.almacenaInformacion(fileTransferVO);

        assertNotNull(proceso);

        log.debug("Registros Almacenados: " + proceso);

        log.debug("Saliendo de testFileTransferAlmacenaInformacion_1()");
        
    }

}
