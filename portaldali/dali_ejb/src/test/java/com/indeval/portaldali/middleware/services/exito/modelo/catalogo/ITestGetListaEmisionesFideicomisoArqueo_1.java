/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.modelo.catalogo;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.CatalogoService;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.persistence.util.DateUtil;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetListaEmisionesFideicomisoArqueo_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetListaEmisionesFideicomisoArqueo_1.class);

    /** Inyecci&oacute;n del bean catalogoService */
    private CatalogoService catalogoService;

    /**
     * @see com.indeval.portaldali.middleware.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (catalogoService == null) {
            catalogoService = (CatalogoService) applicationContext.getBean("catalogoService");
        }
    }

    /**
     * TestCase para catalogoService
     *
     * @throws BusinessException
     */
    public void testGetListaEmisionesFideicomisoArqueo_1() throws BusinessException {
        
        log.info("Entrando a ITestGetListaEmisionesFideicomisoArqueo_1." +
                "testGetListaEmisionesFideicomisoArqueo_1()");
        
        assertNotNull(catalogoService);
        
        Date fechaConsulta = DateUtil.getDate(2008, Calendar.MARCH, 31, 0, 0);
        
        //TODO
//        EmisionVO[] emisionVO = catalogoService.getListaEmisionesFideicomisoArqueo(new AgenteVO(), 
//                fechaConsulta);
//        
//        assertNotNull(emisionVO);
//        assertTrue(emisionVO.length > 0);
//        assertTrue(emisionVO[0] instanceof EmisionVO);
        //TODO
        
    }

}