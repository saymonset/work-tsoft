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
import com.indeval.portaldali.persistence.model.SaldoNombrada;

/**
 * @author Rafael Ibarra Zendejas
 *
 */
public class ITestGetSaldoNombrada_2 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Logger log = LoggerFactory.getLogger(ITestGetSaldoNombrada_2.class);

    /**
     * bean de saldoNombradaDao
     */
	private SaldoNombradaDaliDao saldoNombradaDao;
	
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        saldoNombradaDao = (SaldoNombradaDaliDao) getBean("saldoNombradaDao");
    }
	
    /**
     * Test Case para getSaldoNombradaDivisa()
     */
    @SuppressWarnings("unchecked")
	public void testGetSaldoNombrada_1() {
    	
    	log.info("Entrando a ITestGetSaldoNombrada_1.testGetSaldoNombrada_1");
    	
    	assertNotNull(saldoNombradaDao);

    	String id = "02", folio = "013";
    	
    	List listaSaldoNombrada = 
    	    saldoNombradaDao.getSaldoNombradaDivisa(id, folio, new BigInteger("16"), new BigInteger("3"));
    	
    	assertNotNull(listaSaldoNombrada);
    	
    	log.info("Tamanio de la lista: [" + listaSaldoNombrada.size() + "]");
    	
    	assertTrue(listaSaldoNombrada.size()==1);
        
    	SaldoNombrada saldo = (SaldoNombrada)listaSaldoNombrada.get(0);
        log.info("Saldo para [" + id + folio + "]: [" + saldo.getSaldoDisponible() + "]");
    	
    }
    
}
