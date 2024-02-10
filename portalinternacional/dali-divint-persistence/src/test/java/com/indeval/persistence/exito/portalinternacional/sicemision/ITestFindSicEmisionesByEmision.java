/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.sicemision;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.persistence.dao.SicEmisionDao;

/**
 * Clase de prueba para findSicEmisionesByEmision()
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class ITestFindSicEmisionesByEmision extends BaseDaoTestCase {

	private static final Logger log = LoggerFactory.getLogger(ITestFindSicEmisionesByEmision.class);
    
    private SicEmisionDao sicEmisionDao;
    
    protected void onSetUp() throws Exception {
        super.onSetUp();
        sicEmisionDao = (SicEmisionDao) getBean("sicEmisionDao");
    }
    
    /**
     * Test Case para SicEmisionDao.findSicEmisionesByEmision()
     */
    public void testFindSicEmisionesByEmision_1() {
        
        log.info("Entrando a ITestFindSicEmisionesByEmision.testFindSicEmisionesByEmision_1()");
        
        assertNotNull(sicEmisionDao);
        
        EmisionVO emisionVO = new EmisionVO(); 
        emisionVO.setIsin("US1729671016");
        List<SicEmision> lista = sicEmisionDao.findSicEmisionesByEmision(emisionVO);
        assertNotNull(lista);
        assertTrue("No existe la lista de SicEmision", !lista.isEmpty());
        log.debug("Total de registros = " + lista.size());
        for (Iterator iter = lista.iterator(); iter.hasNext();) {
            SicEmision element = (SicEmision) iter.next();
            assertNotNull(element);
            log.debug("SicEmision [" + ReflectionToStringBuilder.toString(element) + "]");
            assertNotNull(element.getCuentaNombrada());
            log.debug("SicEmision.CuentaNombrada [" + ReflectionToStringBuilder.toString(element.getCuentaNombrada()) + "]");
            assertNotNull(element.getEmision());
            log.debug("SicEmision.Emision [" + ReflectionToStringBuilder.toString(element.getEmision()) + "]");
        }
       
    }
    
    /**
     * Test Case para SicEmisionDao.findSicEmisionesByEmision()
     */
    public void testFindSicEmisionesByEmision_2() {
        
        log.info("Entrando a ITestFindSicEmisionesByEmision.testFindSicEmisionesByEmision_2()");
        
        assertNotNull(sicEmisionDao);
        
        EmisionVO emisionVO = new EmisionVO("1E", "C", "*", "0000");
        List<SicEmision> lista = sicEmisionDao.findSicEmisionesByEmision(emisionVO);
        assertNotNull(lista);
        assertTrue("No existe la lista de SicEmision", !lista.isEmpty());
        log.debug("Total de registros = " + lista.size());
        for (Iterator iter = lista.iterator(); iter.hasNext();) {
            SicEmision element = (SicEmision) iter.next();
            assertNotNull(element);
            log.debug("SicEmision [" + ReflectionToStringBuilder.toString(element) + "]");
            assertNotNull(element.getCuentaNombrada());
            log.debug("SicEmision.CuentaNombrada [" + ReflectionToStringBuilder.toString(element.getCuentaNombrada()) + "]");
            assertNotNull(element.getEmision());
            log.debug("SicEmision.Emision [" + ReflectionToStringBuilder.toString(element.getEmision()) + "]");
        }
       
    }

    /**
     * Test Case para SicEmisionDao.findSicEmisionesByCustodio()
     */
    public void testFindSicEmisionesByCustodio_1() {
        assertNotNull(sicEmisionDao);
        CatBic catBic = new CatBic();
        catBic.setIdCatbic(new Long(5));
        
        PaginaVO paginaVO = sicEmisionDao.findSicEmisionesByCustodio(catBic, null, "FA", null);
        assertNotNull(paginaVO);
        assertNotNull(paginaVO.getRegistros());
        assertFalse(paginaVO.getRegistros().isEmpty());
        System.out.println("---------------> registros encontrados ("+paginaVO.getTotalRegistros()+")");
    }

    /**
     * Test Case para SicEmisionDao.findSicEmisionesByCustodio()
     */
    public void testFindSicEmisionesByCustodio_2() {
        assertNotNull(sicEmisionDao);
        CatBic catBic = new CatBic();
        catBic.setIdCatbic(new Long(5));
        
        PaginaVO paginaVO = sicEmisionDao.findSicEmisionesByCustodio(catBic, null, null, null);
        assertNotNull(paginaVO);
        assertNotNull(paginaVO.getRegistros());
        assertFalse(paginaVO.getRegistros().isEmpty());
        System.out.println("---------------> registros encontrados ("+paginaVO.getTotalRegistros()+")");
    }

    /**
     * Test Case para SicEmisionDao.findSicEmisionesByCustodio()
     */
    public void testFindSicEmisionesByCustodio_3() {
        assertNotNull(sicEmisionDao);
        CatBic catBic = new CatBic();
        catBic.setIdCatbic(new Long(5));
        EmisionVO emisionVO = new EmisionVO("D7","ABBE684","120905",null);
        PaginaVO paginaVO = sicEmisionDao.findSicEmisionesByCustodio(catBic, emisionVO, "FA", null);
        assertNotNull(paginaVO);
        assertNotNull(paginaVO.getRegistros());
        assertFalse(paginaVO.getRegistros().isEmpty());
        System.out.println("---------------> registros encontrados ("+paginaVO.getTotalRegistros()+")");
    }

    /**
     * Test Case para SicEmisionDao.findSicEmisionesByCustodio()
     */
    public void testFindSicEmisionesByCustodio_4() {
        assertNotNull(sicEmisionDao);
        CatBic catBic = new CatBic();
        catBic.setIdCatbic(new Long(5));
        EmisionVO emisionVO = new EmisionVO(null,null,null,null);
        PaginaVO paginaVO = sicEmisionDao.findSicEmisionesByCustodio(catBic, emisionVO, "FA", null);
        assertNotNull(paginaVO);
        assertNotNull(paginaVO.getRegistros());
        assertFalse(paginaVO.getRegistros().isEmpty());
        System.out.println("---------------> registros encontrados ("+paginaVO.getTotalRegistros()+")");
    }
    
}
