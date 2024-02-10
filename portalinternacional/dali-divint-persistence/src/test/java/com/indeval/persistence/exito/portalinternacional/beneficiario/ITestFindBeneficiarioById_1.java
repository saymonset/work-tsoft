/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.beneficiario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.persistence.dao.BeneficiarioDao;

/**
 * 
 * @author Oscar Garcia Granados
 * 
 */
public class ITestFindBeneficiarioById_1 extends BaseDaoTestCase {

	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindBeneficiarioById_1.class);

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
	  * 
	  *
	  */
	public void testFindBeneficiarioById() {

		log.info("Ejecutando prueba testFindBeneficiario()");

		assertNotNull(beneficiarioDao);

		Beneficiario beneficiario = beneficiarioDao.findBeneficiarioById(12044l);
		
		assertNotNull(beneficiario);
		
		log.info("Beneficiario: [" + beneficiario + "]");

	}

}
