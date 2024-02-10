/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.beneficiario;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaBeneficiariosParam;
import com.indeval.portalinternacional.middleware.servicios.vo.NombreBeneficiario;
import com.indeval.portalinternacional.persistence.dao.BeneficiarioDao;

/**
 * 
 * @author Oscar Garcia Granados
 * 
 */
@SuppressWarnings( { "unchecked" })
public class ITestFindNombresBeneficiarios_1 extends BaseDaoTestCase {

	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindNombresBeneficiarios_1.class);

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

	public void testFindNombresBeneficiarios() {
		log.info("Ejecutando prueba testFindNombresBeneficiarios()");
		assertNotNull("Dao Nulo",beneficiarioDao);

        List<NombreBeneficiario> lista = beneficiarioDao.getNombresBeneficiarios(new ConsultaBeneficiariosParam());

        assertNotNull("Lista Nula",lista);
        assertFalse("Lista Vacia",lista.isEmpty());

        for( NombreBeneficiario nombreBeneficiario : lista ) {
            log.info(nombreBeneficiario.toString());
        }

	}

}
