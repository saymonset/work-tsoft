/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.beneficiario;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.persistence.dao.BeneficiarioDao;

/**
 * 
 * @author Oscar Garcia Granados
 * 
 */
@SuppressWarnings( { "unchecked" })
public class ITestFindBeneficiariosUOI_1 extends BaseDaoTestCase {

	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindBeneficiariosUOI_1.class);

	/**
	 * Dao que se va a probar
	 */
	private BeneficiarioDao beneficiarioDao;

	/**
	  * 
	  */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		beneficiarioDao = (BeneficiarioDao) getBean("beneficiarioDao");
	}

	/**
	 * @throws ParseException 
	  * 
	  *
	  */
	public void testFindBeneficiario() throws ParseException {

		log.info("Ejecutando prueba testFindBeneficiario()");

		assertNotNull(beneficiarioDao);

		Long count = beneficiarioDao.findBeneficiarioByUOI("MXBCBIAZ00");
		
		log.info("Numero: [" + count + "]");
		
		assertNotNull("Hay coincidencias",count);
		
		assertFalse("Hay coincidencias",count > 0);

	}

}
