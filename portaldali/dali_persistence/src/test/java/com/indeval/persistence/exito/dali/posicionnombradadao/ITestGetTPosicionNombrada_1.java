/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.posicionnombradadao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao;
import com.indeval.portaldali.persistence.vo.PageVO;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetTPosicionNombrada_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Logger log = LoggerFactory.getLogger(ITestGetTPosicionNombrada_1.class);

    /**
     * bean de cInstrumentoDao
     */
	private PosicionNombradaDaliDao tPosicionNombradaDao;
	
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        tPosicionNombradaDao = (PosicionNombradaDaliDao) getBean("tPosicionNombradaDao");
    }
    
    /**
     * TestCase para testGetTPosicionNombrada()
     */
    public void testGetTPosicionNombrada_1() {
    	
    	log.info("Entrando a ITestGetTPosicionNombrada_1.testGetTPosicionNombrada_1()");
    	
    	assertNotNull(tPosicionNombradaDao);
    	
    	TPosicionNombradaParamsPersistence params = 
    		UtilsTPosicionNombrada.getInstanceTPosicionNombradaParamsPersistence();
    	params.setIdInstitucion("01");
    	params.setFolioInstitucion("003");
    	
    	PageVO pageVO = tPosicionNombradaDao.getTPosicionNombrada(params);
    	
    	assertNotNull(pageVO);
    	assertNotNull(pageVO.getRegistros());
    	assertTrue(!pageVO.getRegistros().isEmpty());
    	
        log.debug("Registros en la pagina : [" + pageVO.getRegistros().size() + "]");
    	UtilsTPosicionNombrada.logListaTPosicionNombrada(pageVO.getRegistros());
    	    	
    }
    
    /**
     * TestCase para testGetTPosicionNombrada()
     */
    public void testGetTPosicionNombrada_2() {
        
        log.info("Entrando a ITestGetTPosicionNombrada_1.testGetTPosicionNombrada_1()");
        
        assertNotNull(tPosicionNombradaDao);
        
        TPosicionNombradaParamsPersistence params = 
            UtilsTPosicionNombrada.getInstanceTPosicionNombradaParamsPersistence();
        params.setConsulta("BY_INSTITUCION");
        params.setIdInstitucion("01");
        params.setFolioInstitucion("003");
        params.setCuentas(new String[] {"0307"});
        params.setTiposDeValor(new String[]{"BI"});
        params.setEmisora("GOBFED");
        params.setSerie("080410");
        params.setCupon("0000");
        params.setMercado("MD");
        
        PageVO pageVO = tPosicionNombradaDao.getTPosicionNombrada(params);
        
        assertNotNull(pageVO);
        assertNotNull(pageVO.getRegistros());
        assertTrue(!pageVO.getRegistros().isEmpty());
        
        log.debug("Registros en la pagina : [" + pageVO.getRegistros().size() + "]");
        UtilsTPosicionNombrada.logListaTPosicionNombrada(pageVO.getRegistros());
                
    }
    
    /**
     * TestCase para testGetTPosicionNombrada()
     */
    public void testGetTPosicionNombrada_3() {
        
        log.info("Entrando a ITestGetTPosicionNombrada_1.testGetTPosicionNombrada_1()");
        
        assertNotNull(tPosicionNombradaDao);
        
        TPosicionNombradaParamsPersistence params = 
            UtilsTPosicionNombrada.getInstanceTPosicionNombradaParamsPersistence();
        params.setConsulta("BY_MERCADO");
        params.setTiposDeValor(new String[]{"BI"});
        params.setEmisora("GOBFED");
        params.setSerie("080410");
        params.setCupon("0000");
        params.setMercado("PG");
        
        PageVO pageVO = tPosicionNombradaDao.getTPosicionNombrada(params);
        
        assertNotNull(pageVO);
        assertNotNull(pageVO.getRegistros());
        assertTrue(!pageVO.getRegistros().isEmpty());
        
        log.debug("Registros en la pagina : [" + pageVO.getRegistros().size() + "]");
        UtilsTPosicionNombrada.logListaTPosicionNombrada(pageVO.getRegistros());
                
    }
    
    /**
     * TestCase para testGetTPosicionNombrada()
     */
    public void testGetTPosicionNombrada_4() {
        
        log.info("Entrando a ITestGetTPosicionNombrada_1.testGetTPosicionNombrada_1()");
        
        assertNotNull(tPosicionNombradaDao);
        
        TPosicionNombradaParamsPersistence params = 
            UtilsTPosicionNombrada.getInstanceTPosicionNombradaParamsPersistence();
        params.setConsulta("BY_MERCADO");
        params.setTiposDeValor(new String[]{"BI"});
        params.setEmisora("GOBFED");
        params.setSerie("080410");
        params.setCupon("0000");
        params.setMercado("PB");
        
        PageVO pageVO = tPosicionNombradaDao.getTPosicionNombrada(params);
        
        assertNotNull(pageVO);
        assertNotNull(pageVO.getRegistros());
        assertTrue(!pageVO.getRegistros().isEmpty());
        
        log.debug("Registros en la pagina : [" + pageVO.getRegistros().size() + "]");
        UtilsTPosicionNombrada.logListaTPosicionNombrada(pageVO.getRegistros());
                
    }
    
    /**
     * TestCase para testGetTPosicionNombrada()
     */
    public void testGetTPosicionNombrada_5() {
        
        log.info("Entrando a ITestGetTPosicionNombrada_1.testGetTPosicionNombrada_1()");
        
        assertNotNull(tPosicionNombradaDao);
        
        TPosicionNombradaParamsPersistence params = 
            UtilsTPosicionNombrada.getInstanceTPosicionNombradaParamsPersistence();
        params.setConsulta("BY_MERCADO");
        params.setTiposDeValor(new String[]{"BI"});
        params.setEmisora("GOBFED");
        params.setSerie("080410");
        params.setCupon("0000");
        params.setMercado("PD");
        
        PageVO pageVO = tPosicionNombradaDao.getTPosicionNombrada(params);
        
        assertNotNull(pageVO);
        assertNotNull(pageVO.getRegistros());
        assertTrue(!pageVO.getRegistros().isEmpty());
        
        log.debug("Registros en la pagina : [" + pageVO.getRegistros().size() + "]");
        UtilsTPosicionNombrada.logListaTPosicionNombrada(pageVO.getRegistros());
                
    }
    
	
}
