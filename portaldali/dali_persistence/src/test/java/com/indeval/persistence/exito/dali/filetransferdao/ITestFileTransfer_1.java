/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.filetransferdao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.filetransfer.FileTransferDao;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestFileTransfer_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger log = LoggerFactory.getLogger(ITestFileTransfer_1.class);

    /**
     * bean de fileTransferDao
     */
	private FileTransferDao fileTransferDao;
	
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        fileTransferDao = (FileTransferDao) getBean("fileTransferDao");
    }
	
	/**
	 * TestCase para probar exitosamente getFechaActual()
	 * @throws Exception
	 */
	public void testGetFechaActual() throws Exception {
		
		log.info("Entrando a ITestFechaActual_1.testGetFechaActual()");
		
		assertNotNull(fileTransferDao);
		
		
	}

}
