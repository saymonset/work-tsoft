/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.util;

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
public class ITestGetProcessInfo_1 extends BaseDaoTestCase {
	/** Objeto de loggeo */
	private static final Log log = LogFactory
			.getLog(ITestGetProcessInfo_1.class);

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
		log.info("Entrando a ITestGetProcessInfo_1.testGetProcessInfo()");

		assertNotNull(fileUploadDao);

		FileUpload fileUpload = new FileUpload();

		fileUpload.setIdProceso("0100101");

		assertNotNull(fileUploadDao.getProcessInfo(fileUpload));
	}

}
