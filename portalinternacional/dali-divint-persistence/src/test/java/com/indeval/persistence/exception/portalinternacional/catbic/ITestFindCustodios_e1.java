/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exception.portalinternacional.catbic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestFindCustodios_e1 extends BaseDaoTestCase {

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ITestFindCustodios_e1.class);

	/** Dao a probar */
	private CatBicDao catBicDao;

	/**
	 * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
	 */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		catBicDao = (CatBicDao) getBean("catBicDao");
	}

	/**
	 * TestCase para probar CatBicDao.findCustodios()
	 */
	public void testFindCustodios_e1() {

		log.info("Entrando a ITestFindDepositantes_e1.testFindCustodios_e1()");

		assertNotNull(catBicDao);

                AgenteVO[] agenteVO = new AgenteVO[]{new AgenteVO("01", "003", "0307"), new AgenteVO("01", "003", "0315")};
		try {
			catBicDao.findCustodios(agenteVO, null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			log.debug("Error: revisar condicion de validacion del custodio");
			assertTrue(false);
		}
		
	}

}
