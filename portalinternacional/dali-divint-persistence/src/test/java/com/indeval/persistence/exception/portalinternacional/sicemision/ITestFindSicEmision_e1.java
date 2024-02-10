/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exception.portalinternacional.sicemision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.persistence.dao.SicEmisionDao;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestFindSicEmision_e1 extends BaseDaoTestCase {

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ITestFindSicEmision_e1.class);

	/** Dao a probar */
	private SicEmisionDao sicEmisionDao;

	/**
	 * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
	 */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		sicEmisionDao = (SicEmisionDao) getBean("sicEmisionDao");
	}

	/**
	 * TestCase para probar SicEmisionDao.findSicEmision()
	 */
	public void testFindSicEmision_e1() {

		log.info("Entrando a ITestFindSicEmision_e1.testFindSicEmision_e1()");

		assertNotNull(sicEmisionDao);
		
//		EmisionVO emisionVO = new EmisionVO();
//		SicEmision sicEmision = null; //sicEmisionDao.findSicEmision(emisionVO);
//		assertNotNull(sicEmision);
//		assertNotNull(sicEmision.getCuentaNombrada());
//		AgenteVO agenteVO = ConvertBO2VO.crearAgenteVO(sicEmision.getCuentaNombrada());
//		assertNotNull(agenteVO);
//		agenteVO.tieneClaveValida();
	}

}
