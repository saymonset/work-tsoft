/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.sicemision;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.persistence.dao.SicEmisionDao;

/**
 * @author Rafael Ibarra
 *
 */
public class ITestFindSicEmisionesVigenteByIdEmision extends BaseDaoTestCase {

    
    /**
     * SicEmisionDao 
     */
    private SicEmisionDao sicEmisionDao = null;

    private static final Logger log = LoggerFactory.getLogger(ITestFindSicEmisionesVigenteByIdEmision.class);
    
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
	@Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        sicEmisionDao = (SicEmisionDao) getBean("sicEmisionDao");
    }
    
    /**
     * @throws Exception
     */
    public void testFindSicEmisionesByAgente_1() throws Exception {
    	assertNotNull(sicEmisionDao);
    	List<SicEmision> sicEmisiones = sicEmisionDao.findSicEmisionVigenteByIdEmision(
				7l, 4034l, 1492l, "VIGENTE", 1693l);
    	assertNotNull("No hay emision", sicEmisiones);
    	assertTrue("Hay emision", sicEmisiones.isEmpty());
    }
    
}
