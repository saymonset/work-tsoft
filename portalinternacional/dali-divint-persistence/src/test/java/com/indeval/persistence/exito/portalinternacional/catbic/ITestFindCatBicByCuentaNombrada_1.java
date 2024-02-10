/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.catbic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;

/**
 * @author javiles
 *
 */
public class ITestFindCatBicByCuentaNombrada_1 extends BaseDaoTestCase {

	/** Log de clase */
	private static final Logger log = LoggerFactory.getLogger(ITestFindCatBicByCuentaNombrada_1.class);
    
    /** Dao a probar */
    private CatBicDao catBicDao;
    
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        catBicDao = (CatBicDao) getBean("catBicDao");
    }
    
    /**
     * TestCase para probar CatBicDao.findCatBic(AgenteVO[])
     */
    public void testFindCatBicCuentaNombrada_1() {
        
        log.info("Ejecutando prueba testFindCatBicCuentaNombrada_1()");
        
        assertNotNull(catBicDao);
        
        Integer numero = catBicDao.findCatBicByCuentaNombrada(4032l);
        
        assertNotNull(numero);
        assertTrue(numero>0);
        
        log.info("Numero de registros: [" + numero + "]");
        
    }
    
}
