/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.util;

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
public class ITestGetLock_1 extends BaseDaoTestCase {
	/** Objeto de loggeo */
	private static final Logger log = LoggerFactory.getLogger(ITestGetLock_1.class);

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
		log.info("Entrando a ITestGetLock_1.testGetLock()");

		assertNotNull(fileUploadDao);

		FileUpload fileUpload = new FileUpload();

		fileUpload.setIdProceso("0100101");
		fileUpload.setUsuario("Pedro");
		fileUpload.setAbort('0');
		fileUpload.setPorcentaje(new BigDecimal(10));
		fileUpload.setStatus("Cargando");

		assertTrue(fileUploadDao.getLock(fileUpload));
	}

}
