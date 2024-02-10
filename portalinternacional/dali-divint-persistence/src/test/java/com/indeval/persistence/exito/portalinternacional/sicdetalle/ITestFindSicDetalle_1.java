/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.sicdetalle;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.exito.portalinternacional.catbic.ITestFindCatBic_1;
import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.persistence.dao.SicDetalleDao;

/**
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class ITestFindSicDetalle_1 extends BaseDaoTestCase {

	/** Log de clase */
	private static final Logger log = LoggerFactory.getLogger(ITestFindCatBic_1.class);
    
    /** Dao a probar */
    private SicDetalleDao sicDetalleDao;
    
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        sicDetalleDao = (SicDetalleDao) getBean("sicDetalleDao");
    }
    
	/**
	 * TestCase para probar SicDetalleDao.findSicDetalle(AgenteVO[])
	 */
    public void ttestFindSicDetalle_1() {
        
        log.info("Entrando a ITestFindSicDetalle_1.testFindSicDetalle_1()");
        
        assertNotNull(sicDetalleDao);
        AgenteVO[] agenteVO = new AgenteVO[]{new AgenteVO("01", "003", "0307"), new AgenteVO("01", "003", "0315")};
        List lista = sicDetalleDao.findSicDetalle(agenteVO);
        
        assertNotNull(lista);
        assertTrue(!lista.isEmpty());
        for (Iterator iter = lista.iterator(); iter.hasNext();) {
            SicDetalle element = (SicDetalle) iter.next();
            log.info("SicDetalle [" + ReflectionToStringBuilder.toString(element) + "]");
            assertNotNull(element.getCuentaNombrada());
            log.info("SicDetalle.CuentaNombrada [" + ReflectionToStringBuilder.toString(element.getCuentaNombrada()) + "]");
        }
        
    }
    
	/**
	 * TestCase para probar SicDetalleDao.findSicDetalle(AgenteVO)
	 */
    public void ttestFindSicDetalle_2() {
        
        log.info("Entrando a ITestFindSicDetalle_1.testFindSicDetalle_2()");
        
        assertNotNull(sicDetalleDao);
        AgenteVO agenteVO = new AgenteVO("01", "003", "0315");
        List lista = sicDetalleDao.findSicDetalle(agenteVO);
        
        assertNotNull(lista);
        assertTrue(!lista.isEmpty());
        for (Iterator iter = lista.iterator(); iter.hasNext();) {
            SicDetalle element = (SicDetalle) iter.next();
            log.info("SicDetalle [" + ReflectionToStringBuilder.toString(element) + "]");
            assertNotNull(element.getCuentaNombrada());
            log.info("SicDetalle.CuentaNombrada [" + ReflectionToStringBuilder.toString(element.getCuentaNombrada()) + "]");
        }
        
    }
    
	/**
	 * TestCase para probar SicDetalleDao.findSicDetalle(AgenteVO[])
	 */
    public void testFindSicDetalle_3() {
        
        log.info("Entrando a ITestFindSicDetalle_1.testFindSicDetalle_3()");
        
        assertNotNull(sicDetalleDao);
        AgenteVO[] agenteVO = new AgenteVO[]{new AgenteVO("18", "006", "0001")};
        String depositante = "CLEARSTREAM BANKING";
        SicDetalle sicDetalle = sicDetalleDao.findSicDetalle(agenteVO, depositante);
        assertNotNull(sicDetalle);
        assertNotNull(sicDetalle.getCuentaNombrada());
        log.info("SicDetalle [" + ReflectionToStringBuilder.toString(sicDetalle) + "]");
        log.info("SicDetalle.CuentaNombrada [" + ReflectionToStringBuilder.toString(sicDetalle.getCuentaNombrada()) + "]");
        
    }
    
}
