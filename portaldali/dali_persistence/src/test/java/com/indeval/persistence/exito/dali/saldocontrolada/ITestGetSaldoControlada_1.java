/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.saldocontrolada;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.SaldoControladaDaliDao;
import com.indeval.portaldali.persistence.util.UtilsLog;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetSaldoControlada_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Log log = 
    	LogFactory.getLog(ITestGetSaldoControlada_1.class);

    /**
     * bean de cInstrumentoDao
     */
	private SaldoControladaDaliDao saldoControladaDaliDao;
	
    /**
     * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        saldoControladaDaliDao = (SaldoControladaDaliDao) getBean("saldoControladaDaliDao");
    }
	
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    public void testGetSaldoControlada_1() {
    	
    	log.info("Entrando a ITestGetSaldoControlada_1.testGetSaldoControlada_1");
    	
    	assertNotNull(saldoControladaDaliDao);

    	List listaSaldoNombrada = 
    	    saldoControladaDaliDao.getSaldoControlada("02", "013",  new BigInteger("1"));
    	
    	assertNotNull(listaSaldoNombrada);
    	assertTrue(!listaSaldoNombrada.isEmpty());
        
        log.debug("Cantidad de Registros : [" + listaSaldoNombrada.size() + "]");
        UtilsLog.logElementosLista(listaSaldoNombrada);
    	
    }
    
}
