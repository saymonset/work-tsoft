/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.depositos;

import java.util.Calendar;
import java.util.List;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.modelo.Depositos;
import com.indeval.portalinternacional.persistence.dao.DepositosDivIntDao;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class ITestDepositosDivInt extends BaseDaoTestCase {

    /** Dao a probar */
    private DepositosDivIntDao depositosDivIntDao;
    
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        depositosDivIntDao = (DepositosDivIntDao) getBean("depositosDivIntDao");
    }
        
    /**
     * @throws Exception
     */
    public void testGetDepositosDivInt_1() throws Exception {
    	
    	assertNotNull(depositosDivIntDao);
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2008, 8, 8);
    	List<Depositos> result = depositosDivIntDao.getDepositosDivInt(new Long(3222),calendar.getTime());
    	assertNotNull(result);
    	assertFalse(result.isEmpty());
    	System.out.println("------------> total de registros encontrados ("+result.size()+")");
    }
    
}
