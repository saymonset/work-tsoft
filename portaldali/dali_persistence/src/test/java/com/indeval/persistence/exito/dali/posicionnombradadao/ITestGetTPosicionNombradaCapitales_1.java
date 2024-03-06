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
public class ITestGetTPosicionNombradaCapitales_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetTPosicionNombradaCapitales_1.class);

    /**
     * bean de cInstrumentoDao
     */
	private PosicionNombradaDaliDao tPosicionNombradaDao;
	
    /**
     * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        tPosicionNombradaDao = (PosicionNombradaDaliDao) getBean("tPosicionNombradaDao");
    }

    /**
     * TestCase para getTPosicionNombradaCapitales()
     */
    public void testGetTPosicionNombradaCapitales_1() {
    	
    	log.info("Entrando a ITestGetTPosicionNombradaCapitales_1." +
    			"testGetTPosicionNombradaCapitales_1()");
    	
    	assertNotNull(tPosicionNombradaDao);
    	
        TPosicionNombradaParamsPersistence params = 
            UtilsTPosicionNombrada.getInstanceTPosicionNombradaParamsPersistence();
        
        params.setIdInstitucion("01");
        params.setFolioInstitucion("003");
        
        PageVO pageVO = 
            tPosicionNombradaDao.getTPosicionNombradaCapitales(params);
        
        assertNotNull(pageVO);
        assertNotNull(pageVO.getRegistros());
        assertTrue(!pageVO.getRegistros().isEmpty());
        
        log.debug("Registros en la pagina : [" + pageVO.getRegistros().size() + "]");
        UtilsTPosicionNombrada.logListaTPosicionNombrada(pageVO.getRegistros());
        
    }
    
}
