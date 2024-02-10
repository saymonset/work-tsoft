package com.indeval.persistence.exception.portalinternacional.formatow8imy;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY;
import com.indeval.portalinternacional.persistence.dao.FormatoW8IMYDao;

public class ITestFindFormatoW8IMY_e1 extends BaseDaoTestCase {

	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindFormatoW8IMY_e1.class);
	
	/**
	  * Dao que se va a probar
	 */
	 FormatoW8IMYDao formatoW8IMYDao;

	 /**
	  * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
	  */
	 protected void onSetUp() throws Exception {
		 super.onSetUp();
		 formatoW8IMYDao = (FormatoW8IMYDao) getBean("formatoW8IMYDao");
	 }
	 
	 /**
	  * 
	  *
	  */
	 public void testFindFormatoW8IMY()
	 {
	   log.info("Entrando a testFindFormatoW8IMY()");
	   Beneficiario beneficiario=new Beneficiario();
	   FormatoW8IMY formatoW8IMY=formatoW8IMYDao.findFormatoW8IMY(beneficiario);
	   log.debug("FormatoW8IMY [" + ReflectionToStringBuilder.toString(formatoW8IMY) + "]");
	   System.out.println("FormatoW8IMY [" + ReflectionToStringBuilder.toString(formatoW8IMY) + "]");	 
	 }  
}
