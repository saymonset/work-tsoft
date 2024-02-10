/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exception.portalinternacional.sicdetalle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portalinternacional.persistence.dao.SicDetalleDao;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestFindDepositantes_e1 extends BaseDaoTestCase {

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ITestFindDepositantes_e1.class);

	/** Dao a probar */
	private SicDetalleDao sicDetalleDao;

	/**
	 * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
	 */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		sicDetalleDao = (SicDetalleDao) getBean("sicDetalleDao");
	}

	/**
	 * TestCase para probar SicDetalleDao.findDepositantes()
	 */
	public void testFindDepositantes_e1() {

		log.info("Entrando a ITestFindDepositantes_e1.testFindDepositantes_e1()");

		assertNotNull(sicDetalleDao);

		AgenteVO[] agenteVO = new AgenteVO[]{new AgenteVO("01", "003", "0307"), new AgenteVO("01", "003", "0315")};

		try {
			sicDetalleDao.findDepositantes(agenteVO, null);
			log.debug("Error: revisar la validacion del depositante");
			assertTrue(false);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

	}

}
