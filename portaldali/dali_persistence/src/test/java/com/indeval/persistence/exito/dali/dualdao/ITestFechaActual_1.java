/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.dualdao;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.DualDaliDao;
import com.indeval.portaldali.persistence.util.DateUtil;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestFechaActual_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger log = LoggerFactory.getLogger(ITestFechaActual_1.class);

    /**
     * bean de dualDaliDao
     */
	private DualDaliDao dualDaliDao;
	
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        dualDaliDao = (DualDaliDao) getBean("dualDaliDao");
    }
	
	/**
	 * TestCase para probar exitosamente getFechaActual()
	 * @throws Exception
	 */
	public void testGetFechaActual() throws Exception {
		
		log.info("Entrando a ITestFechaActual_1.testGetFechaActual()");
		
		assertNotNull(dualDaliDao);
		
		Date fecha = DateUtil.getDate(2008, 3, 17, 0, 0);
		log.debug("Fecha a verificar [" + fecha.toString() + "]");
		
        Date fechaActual = dualDaliDao.getFechaActual();
        
        assertNotNull(fechaActual);
		
		log.debug("La fecha actual es [" + fechaActual + "]");
		
	}

}
