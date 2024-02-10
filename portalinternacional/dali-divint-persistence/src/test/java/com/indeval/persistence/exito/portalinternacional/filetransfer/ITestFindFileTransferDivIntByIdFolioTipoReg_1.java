/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.filetransfer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.util.UtilsLogs;
import com.indeval.portalinternacional.persistence.dao.FileTransferDao;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
@SuppressWarnings({"unchecked"})
public class ITestFindFileTransferDivIntByIdFolioTipoReg_1 extends BaseDaoTestCase {

	private static final Logger log = LoggerFactory.getLogger(ITestFindFileTransferDivIntByIdFolioTipoReg_1.class);
    
    private FileTransferDao fileTransferDao;
    
    protected void onSetUp() throws Exception {
        super.onSetUp();
        fileTransferDao = (FileTransferDao) getBean("fileTransferDao");
    }
    
    public void testFindFileTransferDivIntByIdFolioTipoReg_1() {
        
        log.info("Entrando a ITestFindFileTransferDivIntByIdFolioTipoReg_1." +
        		"testFindFileTransferDivIntByIdFolioTipoReg_1()");
        
        assertNotNull(fileTransferDao);
        
        List listaFileTransfer = fileTransferDao.findFileTransferDivIntByIdFolioTipoReg("01", "003", "TI");
        
        assertNotNull(listaFileTransfer);
        assertTrue(!listaFileTransfer.isEmpty());
        UtilsLogs.logElementosLista(listaFileTransfer);
        
    }
    
}
