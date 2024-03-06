/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.posicionnombradadao;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.persistence.vo.PageVO;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetDetalleEmisiones_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetDetalleEmisiones_1.class);

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
     * TestCase para getDetalleEmisiones_1()
     */
    public void testGetDetalleEmisiones_1() {
    	
    	log.info("Entrando a ITestGetDetalleEmisiones_1.testGetDetalleEmisiones_1()");
    	
    	assertNotNull(tPosicionNombradaDao);
    	TPosicionNombradaParamsPersistence params = 
    		UtilsTPosicionNombrada.getInstanceTPosicionNombradaParamsPersistence();
        
        params.setIdInstitucion("01");
        params.setFolioInstitucion("003");
    	
    	PosicionNombrada tPosicionNombrada = new PosicionNombrada();
    	params.setTPosicionNombrada(tPosicionNombrada);
    	
    	Date fechaInicio = DateUtil.getDate(2008, 01, 01, 12, 00);
    	params.setFechaInicio(fechaInicio);
    	
    	Date fechaFin = DateUtil.getDate(2008, 03, 30, 12, 00);
    	params.setFechaFin(fechaFin);
    	
    	PageVO pageVO = tPosicionNombradaDao.getDetalleEmisiones(params);
    	
    	assertNotNull(pageVO);
    	assertNotNull(pageVO.getRegistros());
    	assertTrue(!pageVO.getRegistros().isEmpty());
        
        log.debug("Registros en la pagina : [" + pageVO.getRegistros().size() + "]");
    	UtilsTPosicionNombrada.logListaTPosicionNombrada(pageVO.getRegistros());
    	
    }
    
}
