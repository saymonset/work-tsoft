/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.posicionnombradadao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao;
import com.indeval.portaldali.persistence.util.UtilsLog;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetEmisoraByTv_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetEmisoraByTv_1.class);

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
     * TestCase para getEmisoraByTv()
     */
    public void testGetEmisoraByTv_1() {
    	
    	log.info("Entrando a ITestGetEmisoraByTv_1.testGetEmisoraByTv_1()");
    	
    	assertNotNull(tPosicionNombradaDao);
    	
    	List listaEmisoras = tPosicionNombradaDao.getEmisoraByTV("BI");
    	
    	assertNotNull(listaEmisoras);
    	assertTrue(!listaEmisoras.isEmpty());
    	
    	log.debug("Se imprime una muestra de los resultados obtenidos...");
    	UtilsLog.logElementosLista(listaEmisoras);
    	
    }
    
}
