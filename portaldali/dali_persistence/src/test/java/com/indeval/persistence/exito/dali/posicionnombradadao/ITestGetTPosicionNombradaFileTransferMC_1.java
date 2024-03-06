/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.posicionnombradadao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetTPosicionNombradaFileTransferMC_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Log log = 
    	LogFactory.getLog(ITestGetTPosicionNombradaFileTransferMC_1.class);

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
     * TestCase para getTPosicionNombradaFileTransferMC()
     */
    public void testGetTPosicionNombradaFileTransferMC_1(){
    
    	log.info("Entrando a ITestGetTPosicionNombradaFileTransferMC_1." +
    			"testGetTPosicionNombradaFileTransferMC_1()");
    	
    	assertNotNull(tPosicionNombradaDao);
    	
    	TPosicionNombradaParamsPersistence params = 
    		UtilsTPosicionNombrada.getInstanceTPosicionNombradaParamsPersistence();
    	
    	params.setTiposDeValor(new String[]{"1"});
    	params.setEmisora("ALFA");
    	params.setSerie("A");
    	
    	PosicionNombrada tPosicionNombrada = 
    		tPosicionNombradaDao.getTPosicionNombradaFileTransferMC(params);
    	
    	assertNotNull(tPosicionNombrada);
    	UtilsTPosicionNombrada.logTPosicionNombrada(tPosicionNombrada);
    	
    }
    
}
