/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.portalinternacional.retiros;

import java.util.Calendar;
import java.util.List;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.modelo.Retiros;
import com.indeval.portalinternacional.persistence.dao.RetirosDivIntDao;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class ITestRetirosDivInt extends BaseDaoTestCase {

    /** Dao a probar */
    private RetirosDivIntDao retirosDivIntDao;
    
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        retirosDivIntDao = (RetirosDivIntDao) getBean("retirosDivIntDao");
    }
    
    /**
     * @throws Exception
     */
    public void testGetRepositosDivInt_1() throws Exception {
    	
    	assertNotNull(retirosDivIntDao);
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2008, 8, 8);
    	List<Retiros> result = retirosDivIntDao.getRetirosDivInt(new Long(3318),calendar.getTime());
    	assertNotNull(result);
    	assertFalse(result.isEmpty());
    	System.out.println("------------> total de registros encontrados ("+result.size()+")");
    }
    
}
