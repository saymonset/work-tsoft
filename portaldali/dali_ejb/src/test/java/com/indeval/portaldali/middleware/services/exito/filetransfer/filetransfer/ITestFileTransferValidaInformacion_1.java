/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.filetransfer.filetransfer;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.filetransfer.FileTransferService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;

/**
 * La clase ITestFileTransferAlmacenaInformacion tiene como objetivo probar los
 * metodos de almacenamiento creados en la clase FileTransferService
 * 
 * @author Jose Guadalupe Aviles.
 */
public class ITestFileTransferValidaInformacion_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestFileTransferValidaInformacion_1.class);

    /** Inyecci&oacute;n del bean mercadoDineroService */
    private FileTransferService fileTransferService;

    /** Bean de acceso a DateUtilsDao */
    private DateUtilsDao dateUtilsDao;

    /**
     * En el m&eacute;todo onSetUp inicializamos los beans que utilizaremos en
     * la clase de prueba.
     * 
     * @throws Exception
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (fileTransferService == null) {
            fileTransferService = (FileTransferService) applicationContext
                    .getBean("fileTransferService");
        }
        if (dateUtilsDao == null) {
            dateUtilsDao = (DateUtilsDao) applicationContext.getBean("dateUtilsDao");
        }
    }

    /**
     * Test para fileTransferCargaArchivo
     * @throws Exception 
     */
    public void testFileTransferValidaInformacion_1() throws Exception {

        log.debug("Entrando a testFileTransferValidaInformacion_1()");

        assertNotNull(fileTransferService);

        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("003");

        String tipoProceso = "TD";
        int offset = 0;
        
        BigInteger idBoveda = new BigInteger("0");

        int validacion = fileTransferService.validaInformacion(agenteVO, tipoProceso, idBoveda, offset);

        assertNotNull(validacion);

        log.debug("Registros validados: " + validacion);
        log.debug("Saliendo de testFileTransferValidaInformacion_1()");

    }

}