package com.indeval.persistence.exito.portalinternacional.custodiotipobenef;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.persistence.dao.CustodioTipoBenefDao;

public class ITestFindByIdCatBic_2 extends BaseDaoTestCase {

	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindByIdCatBic_2.class);

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
	  * 
	  *
	  */
	public void testFindFormato() {
		log.info("Ejecutando prueba testFindFormato()");
		
		String formato = custodioTipoBenefDao.findFormato(4034l, 8l);
		
		log.info("formato: [" + formato + "] ");
	}
}
