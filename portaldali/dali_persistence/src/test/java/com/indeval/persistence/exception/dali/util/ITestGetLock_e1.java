/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exception.dali.util;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.util.FileUploadDao;
import com.indeval.portaldali.persistence.model.FileUpload;

/**
 * 
 * @author csanchez
 * 
 */
public class ITestGetLock_e1 extends BaseDaoTestCase {
	/** Objeto de loggeo */
	private static final Logger logger = LoggerFactory.getLogger(ITestGetLock_e1.class);

	/**
	 * bean de fileUploadDao
	 */
	private FileUploadDao fileUploadDao;

	/**
	 * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
	 */
	protected void onSetUp() {
		super.onSetUp();
		fileUploadDao = (FileUploadDao) getBean("fileUploadDao");
	}

	public void testGetLock() {
		logger.info("Entrando a ITestGetLock_e1.testGetLock()");

		assertNotNull(fileUploadDao);

		FileUpload fileUpload = new FileUpload();

		fileUpload.setIdProceso("0100104");
		fileUpload.setUsuario("Pedro");
		fileUpload.setAbort('0');
		fileUpload.setPorcentaje(new BigDecimal(10));
		fileUpload.setStatus("Cargando");

		assertFalse(fileUploadDao.getLock(fileUpload));
	}

}
