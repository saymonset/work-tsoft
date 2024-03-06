/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.posicioncontroladadao;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.PosicionControladaDaliDao;
import com.indeval.portaldali.persistence.dao.common.impl.PosicionControladaDaliDaoImpl;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.persistence.vo.PageVO;
import com.indeval.portaldali.persistence.vo.TPosicionControladaParamsPersistence;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetVencimientosPendientesByInstitucionFechaVencimiento_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Log log = 
    	LogFactory.getLog(ITestGetVencimientosPendientesByInstitucionFechaVencimiento_1.class);

    /**
     * bean de cInstrumentoDao
     */
	private PosicionControladaDaliDaoImpl tPosicionControladaDao;
	
    /**
     * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        tPosicionControladaDao = (PosicionControladaDaliDaoImpl) getBean("tPosicionControladaDao");
    }
	
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    public void testGetVencimientosPendientesByInstitucionFechaVencimiento_1() {
    	
    	log.info("Entrando a ITestGetVencimientosPendientesByInstitucionFechaVencimiento_1." +
    			"testGetVencimientosPendientesByInstitucionFechaVencimiento_1()");
    	
    	assertNotNull(tPosicionControladaDao);
    	
        TPosicionControladaParamsPersistence params = 
            UtilsTPosicionControlada.getInstanceTPosicionControladaParamsPersistence();
        
        params.setIdInstitucion("01");
        params.setFolioInstitucion("001");
        params.setFechaVencimiento(DateUtil.getDate(2008, 9, 25, 0, 0));
    	
        PageVO page = new PageVO();
        page.setRegistrosXPag(5);
        params.setPageVO(page);
        
    	PageVO pageVO = 
    	    tPosicionControladaDao.getVencimientosPendientesPorInstitucionFechaVencimientoAgrupadoPorEmision(params);
    	
    	assertNotNull(pageVO);
    	assertNotNull(pageVO.getRegistros());
    	assertTrue(!pageVO.getRegistros().isEmpty());
        
        log.debug("Registros en la pagina : [" + pageVO.getRegistros().size() + "]");
        UtilsTPosicionControlada.logListaTPosicionControlada(pageVO.getRegistros());
    	
    }
    
}
