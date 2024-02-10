/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.beneficiario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.persistence.dao.BeneficiarioDao;

/**
 * Test para probar el metodo de getNombresBeneficiariosById del BeneficiarioDao
 * @author Rafael Ibarra Zendejas
 * 
 */
public class ITestFindNombreBeneficiarioById_1 extends BaseDaoTestCase {

	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindNombreBeneficiarioById_1.class);

	/**
	 * Dao que se va a probar
	 */
	private BeneficiarioDao beneficiarioDao;

	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		beneficiarioDao = (BeneficiarioDao) getBean("beneficiarioDao");
	}

	public void testFindBeneficiarioById() {
//		log.info("Ejecutando prueba testFindBeneficiario()");
//		assertNotNull("DAO nulo",beneficiarioDao);
//		List<NombreBeneficiario> beneficiarios = beneficiarioDao.getNombresBeneficiariosById(12024l, 12085l);
//		assertNotNull("Lista Nula", beneficiarios);
//		assertFalse("Lista Vacia", beneficiarios.isEmpty());
//		assertTrue("Tamaño esperado = 62", beneficiarios.size() == 62 );
//		for(NombreBeneficiario beneficiario : beneficiarios) {
//			log.info("Beneficiario: [" + beneficiario + "]");
//		}

	}

}
