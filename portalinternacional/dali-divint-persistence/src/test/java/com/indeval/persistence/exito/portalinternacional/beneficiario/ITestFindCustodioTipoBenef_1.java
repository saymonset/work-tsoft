/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.beneficiario;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.CustodioTipoBenef;
import com.indeval.portalinternacional.persistence.dao.CustodioTipoBenefDao;

/**
 * 
 * @author Oscar Garcia Granados
 * 
 */
@SuppressWarnings( { "unchecked" })
public class ITestFindCustodioTipoBenef_1 extends BaseDaoTestCase {

	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindCustodioTipoBenef_1.class);

	/**
	 * Dao que se va a probar
	 */
	private CustodioTipoBenefDao custodioTipoBenefDao;

	/**
	  * 
	  */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		custodioTipoBenefDao = (CustodioTipoBenefDao) getBean("custodioTipoBenefDao");
	}

	/**
	 * @throws ParseException 
	  * 
	  *
	  */
	public void testFindBeneficiario() throws ParseException {
		log.info("Ejecutando prueba testFindBeneficiario()");
		assertNotNull("Dao nulo", custodioTipoBenefDao);

		Double porcentajeRetencion = custodioTipoBenefDao.getPorcentajeRetencion(4030l, 8l);
		assertNotNull("Porcentaje Nulo",porcentajeRetencion);
		assertTrue("Porcentaje incorrecto",porcentajeRetencion == 0);
		log.info("Porcentaje: [" + porcentajeRetencion + "%]");

		List<CustodioTipoBenef> lista = custodioTipoBenefDao.getCustodiosTipoBeneficiario();
		assertNotNull("Lista Nula",lista);
		assertFalse("Lista Vacia",lista.isEmpty());
		for(CustodioTipoBenef ctb : lista) {
			log.info("CustodioTipoBenef: [" + ctb.getIdCuentaNombrada() + "-" + ctb.getTipoBeneficiario().getDescTipoBeneficiario() + "-" + ctb.getPorcentajeRetencion() + "]");
		}

	}

}
