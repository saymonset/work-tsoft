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
public class ITestAgregaDiasHabiles_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger log = LoggerFactory.getLogger(ITestAgregaDiasHabiles_1.class);

    /**
     * bean de cInstrumentoDao
     */
	private DiaInhabilDaliDao diaInhabilDaliDao;
	
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        diaInhabilDaliDao = (DiaInhabilDaliDao) getBean("diaInhabilDaliDao");
    }
		
	/**
	 * TestCase para probar exitosamente esInhabil()
	 * @throws Exception
	 */
	public void testAgregaDiasHabiles_1() throws Exception {
		
		log.info("Entrando a ITestAgregaDiasHabiles_1.testAgregaDiasHabiles_1()");
		
		assertNotNull(diaInhabilDaliDao);
		
		Date fecha = DateUtil.getDate(2008, 03, 31, 0, 0);
		log.debug("Fecha a verificar [" + fecha.toString() + "]");
		
		Date fechaRetorno = diaInhabilDaliDao.agregaDiasHabiles(fecha, 0);
        assertNotNull(fechaRetorno);
        
        log.debug("La fecha recuperada es: [" + fechaRetorno + "]");   
		
	}

}
