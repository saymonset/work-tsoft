/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.diainhabildao;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao;
import com.indeval.portaldali.persistence.util.UtilsLog;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestFindDiasInhabilesByMonthYear_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(ITestFindDiasInhabilesByMonthYear_1.class);

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
	public void testFindDiasInhabilesByMonthYear_1() throws Exception {
		
	    log.info("Ejecutando prueb testFindDiasInhabilesByMonthYear_1()");

	    assertNotNull(diaInhabilDaliDao);

	    List listaDiasInhabiles = diaInhabilDaliDao.findDiasInhabilesByMonthYear(Calendar.OCTOBER, 2008);

	    assertNotNull("No existe la lista de dias inhabiles", listaDiasInhabiles);
	    assertTrue("No hay días inhabiles", !listaDiasInhabiles.isEmpty());
	    
	    log.debug("Se imprime la lista de dias inhabiles: ");
	    UtilsLog.logElementosLista(listaDiasInhabiles);
	    
	}

}
