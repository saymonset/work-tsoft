/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.sicemision;

import java.util.List;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.persistence.dao.SicEmisionDao;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class ITestFindSicEmisionesByAgente extends BaseDaoTestCase {

    
    /**
     * SicEmisionDao 
     */
    private SicEmisionDao sicEmisionDao = null;
    
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        sicEmisionDao = (SicEmisionDao) getBean("sicEmisionDao");
    }
    
    /**
     * @throws Exception
     */
    public void testFindSicEmisionesByAgente_1() throws Exception {
    	
    	assertNotNull(sicEmisionDao);
    	AgenteVO agenteVO = new AgenteVO("18","002","0001");
    	List<SicEmision> sicEmisiones = sicEmisionDao.findSicEmisionesByAgente(agenteVO);
    	assertNotNull(sicEmisiones);
    	assertFalse(sicEmisiones.isEmpty());
    	System.out.println("----------> registros encontrados ("+sicEmisiones.size()+")");
    }
    
}
