package com.indeval.persistence.exito.portalinternacional.custodiotipobenef;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.persistence.dao.CustodioTipoBenefDao;

public class ITestFindByIdCatBic_1 extends BaseDaoTestCase {

	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindByIdCatBic_1.class);

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
	public void testFindByIdCatBic() {
		log.info("Ejecutando prueba testFindByIdCatBic()");
		List<Object[]> lista = custodioTipoBenefDao.findByIdCatBic(4032l);
		
		log.info("Tamaño Lista: [" + lista.size() + "] ");
	}
}
