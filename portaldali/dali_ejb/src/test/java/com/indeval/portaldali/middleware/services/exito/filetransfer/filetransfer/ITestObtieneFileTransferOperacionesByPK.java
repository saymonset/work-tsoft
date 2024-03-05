/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.filetransfer.filetransfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.filetransfer.FileTransferService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.persistence.model.FileTransferOperaciones;
import com.indeval.portaldali.persistence.model.FileTransferPK;

/**
 * @author Jos&eacute; Avil&eacute;s
 *
 */
public class ITestObtieneFileTransferOperacionesByPK extends BaseITestService {

    /** Servicio de log */
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /** Bean del servicio a ser probado */
    private FileTransferService fileTransferService;
    
    /**
     * @see com.indeval.portaldali.middleware.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (fileTransferService == null) {
            fileTransferService = 
                (FileTransferService) applicationContext.getBean("fileTransferService");
        }        
    }

    /**
     * Test para obtieneFileTransferOperacionesByPK
     * @throws BusinessException 
     */
    public void testObtieneFileTransferOperacionesByPK() throws BusinessException {
        
        log.info("Entrando a ITestObtieneFileTransferOperacionesByPK." +
                "testObtieneFileTransferOperacionesByPK()");
        
        FileTransferPK fileTransferPK = null;
        fileTransferPK = new FileTransferPK();
        fileTransferPK.setIdInst("01");
        //fileTransferPK.setFolioInst("003");
        //fileTransferPK.setTipoReg("");
        //fileTransferPK.setConsec(new BigDecimal(0));
        
        //TODO
//        Object fileTransferOperaciones = 
//            fileTransferService.testObtieneFileTransferOperacionesByPK(fileTransferPK);
//        
//        assertNotNull(fileTransferOperaciones);
//        assertTrue(fileTransferOperaciones instanceof FileTransferOperaciones);
        //TODO

    }

}
