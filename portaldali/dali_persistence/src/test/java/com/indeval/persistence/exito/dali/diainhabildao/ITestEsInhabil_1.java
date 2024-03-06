/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.diainhabildao;

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
public class ITestEsInhabil_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(ITestEsInhabil_1.class);

    /**
     * bean de cInstrumentoDao
     */
	private DiaInhabilDaliDao diaInhabilDaliDao;
	
    /**
     * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        diaInhabilDaliDao = (DiaInhabilDaliDao) getBean("diaInhabilDaliDao");
    }
	
	/**
	 * TestCase para probar exitosamente esInhabil()
	 * @throws Exception
	 */
	public void testEsInhabil_1() throws Exception {
		
		log.info("Entrando a ITestEsInhabil_1.testEsInhabil()");
		
		assertNotNull(diaInhabilDaliDao);
		
		Date fecha = DateUtil.getDate(2008, 3, 17, 0, 0);
		log.debug("Fecha a verificar [" + fecha.toString() + "]");
		
		if(diaInhabilDaliDao.esInhabil(fecha)){
			log.debug("La fecha [" + fecha + "] es inhabil");	
		}
		else {
			log.debug("La fecha [" + fecha + "] es habil");
		}
		
	}

}
