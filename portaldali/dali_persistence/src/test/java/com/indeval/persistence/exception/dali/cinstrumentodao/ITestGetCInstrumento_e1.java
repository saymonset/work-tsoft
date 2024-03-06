/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exception.dali.cinstrumentodao;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.InstrumentoDaliDao;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetCInstrumento_e1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetCInstrumento_e1.class);

    /**
     * bean de cInstrumentoDao
     */
	private InstrumentoDaliDao cInstrumentoDao;
	
    /**
     * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        cInstrumentoDao = (InstrumentoDaliDao) getBean("cInstrumentoDao");
    }
	
	/**
	 * TestCase para probar una exception en getCInstrumento()
	 * con la TV null
	 * @throws Exception
	 */
	public void testGetCInstrumento_e1() throws Exception {
		
		log.info("Entrando a ITestGetCInstrumento.testGetCInstrumento()");
		
		assertNotNull(cInstrumentoDao);
		
		try {
			cInstrumentoDao.getInstrumento(null);
			log.debug("Checar validacion");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return;	
		}
		assertTrue(false);
		
	}
	
	/**
	 * TestCase para probar una exception en getCInstrumento()
	 * con una TV inexistente
	 * @throws Exception
	 */
	public void testGetCInstrumento_e2() throws Exception {
		
		log.info("Entrando a ITestGetCInstrumento.testGetCInstrumento()");
		
		assertNotNull(cInstrumentoDao);
		
		try {
			cInstrumentoDao.getInstrumento("KKK");
			log.debug("Checar validacion");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return;	
		}
		assertTrue(false);
		
	}

}
