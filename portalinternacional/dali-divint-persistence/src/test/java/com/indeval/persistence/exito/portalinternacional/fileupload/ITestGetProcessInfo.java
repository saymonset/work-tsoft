/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.fileupload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileUpload;
import com.indeval.portalinternacional.persistence.dao.FileUploadDao;

public class ITestGetProcessInfo extends BaseDaoTestCase {
	
	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestGetProcessInfo.class);
	
	/**
	  * Dao que se va a probar
	 */
	 FileUploadDao fileUploadDao;

	 /**
	  * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
	  */
	 protected void onSetUp() throws Exception {
		 super.onSetUp();
		 fileUploadDao = (FileUploadDao) getBean("fileUploadDao");
	 }
	 
	 /**
	  * TestCase para FileUploadDao.getProcessInfo()
	  *
	  */
	 public void testGetProcessInfo() {
		 
		 log.info("Entrando a ITestGetProcessInfo.testGetProcessInfo()");
	
		 FileUpload fileUpload = new FileUpload();
		 fileUpload.setIdProceso("TTGER");
		 fileUpload = fileUploadDao.getProcessInfo(fileUpload);
 
	 }  

}
