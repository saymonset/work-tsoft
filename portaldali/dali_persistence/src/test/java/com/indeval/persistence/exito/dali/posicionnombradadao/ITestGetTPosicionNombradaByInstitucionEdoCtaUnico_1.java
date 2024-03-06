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
public class ITestGetTPosicionNombradaByInstitucionEdoCtaUnico_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Log log = 
    	LogFactory.getLog(ITestGetTPosicionNombradaByInstitucionEdoCtaUnico_1.class);

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
     * TestCase para getListaTPosicionNombradaByInstitucionEdoCtaUnico()
     */
    public void testGetTPosicionNombradaByInstitucionEdoCtaUnico_1() {
    	
		log.info("Entrando a ITestGetTPosicionNombradaByInstitucionEdoCtaUnico_1." +
				"testGetTPosicionNombradaByInstitucionEdoCtaUnico_1()");
    	
    	assertNotNull(tPosicionNombradaDao);
    	
    	TPosicionNombradaParamsPersistence params = 
    		UtilsTPosicionNombrada.getInstanceTPosicionNombradaParamsPersistence();
    	
    	params.setIdInstitucion("02");
    	params.setFolioInstitucion("013");
    	//params.setCuentas(new String[] {"6852"});
    	//params.setFechaMovimiento(DateUtil.getDate(2008,03, 22, 12, 00));
    	params.setTiposDeValor(new String[] {"BI"});
    	params.setEmisora("GOBFED");
    	params.setSerie("080410");
    	    	
    	PageVO pageVO = 
        	tPosicionNombradaDao.getTPosicionNombradaByInstitucionEdoCtaUnico(
        			params);
    	assertNotNull(pageVO);
    	assertNotNull(pageVO.getRegistros());
    	assertTrue(!pageVO.getRegistros().isEmpty());

        log.debug("Registros en la pagina : [" + pageVO.getRegistros().size() + "]");
    	UtilsTPosicionNombrada.logListaTPosicionNombrada(pageVO.getRegistros());
        
    }
	
}
