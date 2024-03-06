/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exception.dali.util;

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
public class ITestGetProcessInfo_e1 extends BaseDaoTestCase {
	/** Objeto de loggeo */
	private static final Logger logger = LoggerFactory
			.getLogger(ITestGetProcessInfo_e1.class);

	/**
	 * bean de fileUploadDao
	 */
	private FileUploadDao fileUploadDao;

	/**
	 * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
	 */
	protected void onSetUp() {
		super.onSetUp();
		fileUploadDao = (FileUploadDao) getBean("fileUploadDao");
	}

	public void testGetProcessInfo() {
		logger.info("Entrando a ITestGetProcessInfo_e1.testGetProcessInfo()");

		assertNotNull(fileUploadDao);

		FileUpload fileUpload = new FileUpload();

		fileUpload.setIdProceso("0100105");

		assertNull(fileUploadDao.getProcessInfo(fileUpload));
	}

}
