/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.catbic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;

/**
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class ITestFindCatBic_1 extends BaseDaoTestCase {

	/** Log de clase */
	private static final Logger log = LoggerFactory.getLogger(ITestFindCatBic_1.class);
    
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
    public void testFindCatBic_1() {
        
        log.info("Ejecutando prueba testFindCatBic_1()");
        
        assertNotNull(catBicDao);
        AgenteVO[] agenteVO = new AgenteVO[]{new AgenteVO("01", "003", "0307"), new AgenteVO("01", "003", "0315")};
        List<CatBic> lista = catBicDao.findCatBic(agenteVO);
        assertNotNull("No existe la lista resultante", lista);
        assertTrue("No hay registros para mostrar", !lista.isEmpty());
        for (Iterator iter = lista.iterator(); iter.hasNext();) {
            CatBic element = (CatBic) iter.next();
            assertNotNull("No existe el elmento", element);
            log.info("CatBic [" + ReflectionToStringBuilder.toString(element) + "]");
            assertNotNull("No existe la cuenta nombrada del elemento", element.getCuentaNombrada());
            log.info("CatBic.CuentaNombrada [" + ReflectionToStringBuilder.toString(element.getCuentaNombrada()) + "]");
        }
        
    }
    
    /**
     * TestCase para probar CatBicDao.findCatBic(AgenteVO[], String)
     */
    public void testFindCatBic_2() {
        
        log.info("Ejecutando prueba testFindCatBic_2()");
        
        assertNotNull(catBicDao);
        AgenteVO[] agenteVO = new AgenteVO[]{new AgenteVO("01", "003", "0307"), new AgenteVO("01", "003", "0315")};
        String custodio = "CLEARSTREAM";
        List<CatBic> lista = catBicDao.findCatBic(agenteVO, custodio);
        assertNotNull("No existe la lista resultante", lista);
        assertTrue("No hay registros para mostrar", !lista.isEmpty());
        for (Iterator iter = lista.iterator(); iter.hasNext();) {
            CatBic element = (CatBic) iter.next();
            assertNotNull("No existe el elmento", element);
            log.info("CatBic [" + ReflectionToStringBuilder.toString(element) + "]");
            assertNotNull("No existe la cuenta nombrada del elemento", element.getCuentaNombrada());
            log.info("CatBic.CuentaNombrada [" + ReflectionToStringBuilder.toString(element.getCuentaNombrada()) + "]");
        }
        
    }
    
    /**
     * TestCase para probar CatBicDao.findCatBic()
     */
    
    public void testFindCatBic_3() {
        
        log.info("Ejecutando prueba testFindCatBic_3()");
        assertNotNull(catBicDao);
        List<CatBic> catbics= catBicDao.findCatBic();
		for(int i=0;i<catbics.size();i++)
    	{
    		CatBic element = catbics.get(i);
 		    assertNotNull(element);
 	        log.debug("CatBic [" + ReflectionToStringBuilder.toString(element) + "]"); 	        
    	}		
    }

    /**
     * @throws Exception
     */
    public void testFindCatBic_4() throws Exception {
    	assertNotNull(catBicDao);
    	List<SicEmision> sicEmisiones = new ArrayList<SicEmision>();
    	
    	CatBic catBic = new CatBic();
    	catBic.setIdCatbic(new Long(1));
    	SicEmision sicEmision = new SicEmision();
    	sicEmision.setCatBic(catBic);
    	sicEmisiones.add(sicEmision);

    	catBic = new CatBic();
    	catBic.setIdCatbic(new Long(1));
    	sicEmision = new SicEmision();
    	sicEmision.setCatBic(catBic);
    	sicEmisiones.add(sicEmision);

    	catBic = new CatBic();
    	catBic.setIdCatbic(new Long(1062));
    	sicEmision = new SicEmision();
    	sicEmision.setCatBic(catBic);
    	sicEmisiones.add(sicEmision);
    	
    	List<CatBic> catBics = catBicDao.findCatBic(sicEmisiones);
    	assertNotNull(catBics);
    	assertFalse(catBics.isEmpty());
    	System.out.println("------------> registros encontrados ("+catBics.size()+")");
    }
}
