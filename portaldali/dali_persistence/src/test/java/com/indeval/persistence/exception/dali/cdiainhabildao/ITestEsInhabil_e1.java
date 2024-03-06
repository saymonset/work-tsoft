/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exception.dali.cdiainhabildao;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao;
import com.indeval.portaldali.persistence.util.DateUtil;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestEsInhabil_e1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(ITestEsInhabil_e1.class);

    /**
     * bean de cInstrumentoDao
     */
	private DiaInhabilDaliDao diaInhabilDaliDao;
	
    /**
     * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        
        diaInhabilDaliDao = (DiaInhabilDaliDao) getBean("diaInhabilDao");
    }
	
	/**
	 * TestCase para probar excepcion de esInhabil()
	 * con la fecha null
	 * @throws Exception
	 */
	public void testEsInhabil_e1() throws Exception {
		
		logger.info("Entrando a ITestEsInhabil_1.testEsInhabil()");
		
		assertNotNull(diaInhabilDaliDao);

		try {
			diaInhabilDaliDao.esInhabil(null);
			logger.debug("Checar validacion ");
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			
		}
		
	}
		
	/**
	 * TestCase para probar esInhabil() con una fecha habil
	 * @throws Exception
	 */
	public void testEsInhabil_e2() throws Exception {
		
		logger.info("Entrando a ITestEsInhabil_1.testEsInhabil()");
		
		assertNotNull(diaInhabilDaliDao);
		
		Date fecha = DateUtil.getDate(2008, 05, 17, 0, 0);
		logger.debug("Fecha a verificar [" + fecha.toString() + "]");
		
		if(diaInhabilDaliDao.esInhabil(fecha)){
			logger.debug("La fecha [" + fecha + "] es inhabil");	
		}
		else {
			logger.debug("La fecha [" + fecha + "] es habil");
		}
		
	}

}
