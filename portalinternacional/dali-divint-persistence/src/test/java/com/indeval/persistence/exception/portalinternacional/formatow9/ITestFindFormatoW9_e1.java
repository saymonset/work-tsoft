package com.indeval.persistence.exception.portalinternacional.formatow9;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW9;
import com.indeval.portalinternacional.persistence.dao.FormatoW9Dao;

public class ITestFindFormatoW9_e1 extends BaseDaoTestCase {
	
	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindFormatoW9_e1.class);
	
	/**
	  * Dao que se va a probar
	 */
	 FormatoW9Dao formatoW9Dao;

	 /**
	  * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
	  */
	 protected void onSetUp() throws Exception {
		 super.onSetUp();
		 formatoW9Dao = (FormatoW9Dao) getBean("formatoW9Dao");
	 }
	 
	 /**
	  * 
	  *
	  */
	 public void testFindFormatoW9()
	 {
	   log.info("Entrando a testFindFormatoW9()");
	   Beneficiario beneficiario=new Beneficiario();
	   FormatoW9 formatoW9=formatoW9Dao.findFormatoW9(beneficiario);
	   log.debug("FormatoW9 [" + ReflectionToStringBuilder.toString(formatoW9) + "]");
	   System.out.println("FormatoW9 [" + ReflectionToStringBuilder.toString(formatoW9) + "]");	   
	 } 
}
