package com.indeval.persistence.exito.portalinternacional.formatow8ben;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BEN;
import com.indeval.portalinternacional.persistence.dao.FormatoW8BENDao;

public class ITestFindFormatoW8BEN_1 extends BaseDaoTestCase {
	
	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindFormatoW8BEN_1.class);
	
	/**
	  * Dao que se va a probar
	 */
	 FormatoW8BENDao formatoW8BENDao;

	 /**
	  * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
	  */
	 protected void onSetUp() throws Exception {
		 super.onSetUp();
		 formatoW8BENDao = (FormatoW8BENDao) getBean("formatoW8BENDao");
	 }
	 
	 /**
	  * 
	  *
	  */
	 public void testFindFormatoW8BEN()
	 {
	   log.info("Entrando a testFindFormatoW8BEN()");
	   Beneficiario beneficiario=new Beneficiario();
	   FormatoW8BEN formatoW8BENPrueba= new FormatoW8BEN();
	   formatoW8BENPrueba.setIdCamposFormatoW8ben(new Long(2));
	   beneficiario.setFormatoW8BEN(formatoW8BENPrueba);
	   FormatoW8BEN formatoW8BEN=formatoW8BENDao.findFormatoW8BEN(beneficiario);
	   log.debug("FormatoW8BEN [" + ReflectionToStringBuilder.toString(formatoW8BEN) + "]");
	   System.out.println("FormatoW8BEN [" + ReflectionToStringBuilder.toString(formatoW8BEN) + "]");	 
	 }  

}
