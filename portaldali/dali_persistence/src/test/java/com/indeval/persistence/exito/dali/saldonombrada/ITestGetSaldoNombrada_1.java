/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.saldonombrada;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.SaldoNombradaDaliDao;
import com.indeval.portaldali.persistence.util.UtilsLog;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetSaldoNombrada_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Log log = 
    	LogFactory.getLog(ITestGetSaldoNombrada_1.class);

    /**
     * bean de cInstrumentoDao
     */
	private SaldoNombradaDaliDao saldoNombradaDaliDao;
	
    /**
     * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        saldoNombradaDaliDao = (SaldoNombradaDaliDao) getBean("saldoNombradaDaliDao");
    }
	
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    public void testGetSaldoNombrada_1() {
    	
    	log.info("Entrando a ITestGetSaldoNombrada_1.testGetSaldoNombrada_1");
    	
    	assertNotNull(saldoNombradaDaliDao);

    	List listaSaldoNombrada = 
    	    saldoNombradaDaliDao.getSaldoNombrada("02", "013", new BigInteger("1"));
    	
    	assertNotNull(listaSaldoNombrada);
    	assertTrue(!listaSaldoNombrada.isEmpty());
        
        log.debug("Cantidad de Registros : [" + listaSaldoNombrada.size() + "]");
        UtilsLog.logElementosLista(listaSaldoNombrada);
    	
    }
    
}
