/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.filetransfer.filetransfer;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.filetransfer.FileTransferService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;

/**
 * La clase ITestFileTransferAlmacenaInformacion tiene como objetivo probar los
 * metodos de almacenamiento creados en la clase FileTransferService
 * 
 * @author Jose Guadalupe Aviles.
 */
public class ITestFileTransferAlmacenaInformacion_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestFileTransferAlmacenaInformacion_1.class);

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
     * 
     * @throws BusinessException
     *             Cuando todos los parametros van llenos y son traspasos de
     *             mercado de dinero
     */
    public void testFileTransferAlmacenaInformacion_1() throws BusinessException {

        log.debug("Entrando a testFileTransferAlmacenaInformacion_1()");

        assertNotNull(fileTransferService);

        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("003");

        ArrayList informacion = new ArrayList();
        informacion.add("     1013501010410101010030315BI  GOBFED 0907300000                 009R00207-Oct-200805-Oct-2008         00029024000          1000000000000PE1");
        informacion.add("     1013501010410101010030315BI  GOBFED 0907300000                 009E00507-Oct-200805-Oct-2008         00029024000          1000000000000PE1");
        
        String tipoProceso = "TD";
        String usuario = "JGAR";
        int offset = 0;

        int proceso = fileTransferService.almacenaInformacion(agenteVO, tipoProceso, informacion,
                offset, usuario);

        assertNotNull(proceso);

        log.debug("Registros Almacenados: " + proceso);

        log.debug("Saliendo de testFileTransferAlmacenaInformacion_1()");

    }

}