/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.topecirculante;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.TopeCirculante;
import com.indeval.portalinternacional.persistence.dao.TopeCirculanteDao;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class ITestTopeCirculanteDao extends BaseDaoTestCase {

    /** Dao a probar */
    private TopeCirculanteDao topeCirculanteDao;
    
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        topeCirculanteDao = (TopeCirculanteDao)getBean("topeCirculanteDao");
    }
    
    /**
     * @throws Exception
     */
    public void testGetTopeCirculante_1() throws Exception {
    	assertNotNull(topeCirculanteDao);
    	TopeCirculante topeCirculante = topeCirculanteDao.getTopeCirculante(new Long(1866));
    	assertNotNull(topeCirculante);
    	System.out.println("------------> "+topeCirculante.getCantidadTitulos());
    }
}
