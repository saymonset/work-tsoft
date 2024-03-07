/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.posicionnombradadao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.vo.PageVO;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetTPosicionNombradaByExample_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Logger log = LoggerFactory.getLogger(ITestGetTPosicionNombradaByExample_1.class);

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
     * TestCase para getTPosicionNombradaByExample()
     */
    public void testGetTPosicionNombradaByExample_1() {
    	
    	log.info("Entrando a ITestGetTPosicionNombradaByExample_1." +
    			"testGetTPosicionNombradaByExample_1()");
    	
    	assertNotNull(tPosicionNombradaDao);
        
        TPosicionNombradaParamsPersistence params = 
            UtilsTPosicionNombrada.getInstanceTPosicionNombradaParamsPersistence();
        
        PosicionNombrada tPosicionNombrada = new PosicionNombrada();

        params.setTPosicionNombrada(tPosicionNombrada);
        params.setExample(true);
    	
    	PageVO pageVO = 
    		tPosicionNombradaDao.getTPosicionNombradaByExample(params);
    	
    	assertNotNull(pageVO);
    	assertNotNull(pageVO.getRegistros());
    	assertTrue(!pageVO.getRegistros().isEmpty());
        
        log.debug("Registros en la pagina : [" + pageVO.getRegistros().size() + "]");
    	UtilsTPosicionNombrada.logListaTPosicionNombrada(pageVO.getRegistros());
    	
    }
	
}
