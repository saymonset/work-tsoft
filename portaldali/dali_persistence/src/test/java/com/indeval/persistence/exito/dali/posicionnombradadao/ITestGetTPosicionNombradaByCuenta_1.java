/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.posicionnombradadao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetTPosicionNombradaByCuenta_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetTPosicionNombradaByCuenta_1.class);

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
     * TestCase para getTPosicionNombradaByCuenta()
     */
    public void testGetTPosicionNombradaByCuenta_1() {
    	
    	log.info("Entrando a ITestGetTPosicionNombradaByCuenta_1." +
    			"testGetTPosicionNombradaByCuenta_1()");
    	
    	assertNotNull(tPosicionNombradaDao);
    	
    	TPosicionNombradaParamsPersistence params = 
    		UtilsTPosicionNombrada.getInstanceTPosicionNombradaParamsPersistence();
        params.setIdInstitucion("01");
        params.setFolioInstitucion("014");
    	params.setCuentas(new String[]{"1800"});
    	
    	List listaTPosicionNombrada = 
    		tPosicionNombradaDao.getTPosicionNombradaByCuenta(params);
    	
    	assertNotNull(listaTPosicionNombrada);
    	assertTrue(!listaTPosicionNombrada.isEmpty());
    	UtilsTPosicionNombrada.logListaTPosicionNombrada(listaTPosicionNombrada);
    	
    }
    
}
