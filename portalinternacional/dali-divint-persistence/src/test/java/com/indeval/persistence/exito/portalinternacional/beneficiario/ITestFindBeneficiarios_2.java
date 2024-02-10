/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.beneficiario;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;

/**
 * 
 * @author Oscar Garcia Granados
 *
 */
public class ITestFindBeneficiarios_2 extends BaseDaoTestCase{
	
	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindBeneficiarios_2.class);
	
	/**
	  * Dao que se va a probar
	 */
	 private CatBicDao catBicDao;
	 
	 /**
	  * 
	  */
	 protected void onSetUp() throws Exception {
	        super.onSetUp();
	        catBicDao = (CatBicDao) getBean("catBicDao");
	    }
	 
	 /**
	  * 
	  *
	  */
	 @SuppressWarnings("unchecked")
	public void testFindBeneficiario() {
		 
		 log.info("Ejecutando prueba testFindBeneficiario()");
	        
	     assertNotNull(catBicDao);
	    
	     List<Object[]> nombres = catBicDao.findCatBicByName();
	     
	     assertNotNull(nombres);
	     
	     log.info("Tamaño: [" + nombres.size() + "]");
	     
	     for( Object[] n : nombres ) {
	    	 log.info("Custodio: [" + n[0] + "\t" + n[1] + "]");
	     }
		 
	 }

}
