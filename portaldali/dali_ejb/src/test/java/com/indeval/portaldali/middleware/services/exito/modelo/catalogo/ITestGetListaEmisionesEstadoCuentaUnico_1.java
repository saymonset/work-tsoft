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
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.persistence.util.DateUtil;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetListaEmisionesEstadoCuentaUnico_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetListaEmisionesEstadoCuentaUnico_1.class);

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
    public void testGetListaEmisionesEstadoCuentaUnico_1() throws BusinessException {

        log.info("Entrando a ITestGetListaEmisionesEstadoCuentaUnico_1." +
                "testGetListaEmisionesEstadoCuentaUnico_1()");
        
        assertNotNull(catalogoService);
        
        Date fechaMovimiento = DateUtil.getDate(2008, Calendar.MARCH, 31, 0, 0);
                
        //TODO
//        PaginaVO paginaVO = catalogoService.getListaEmisionesEstadoCuentaUnico(new AgenteVO(), 
//                new EmisionVO(), fechaMovimiento);
//            
//        assertNotNull(paginaVO);
//        assertNotNull(paginaVO.getRegistros());
//        assertTrue(!paginaVO.getRegistros().isEmpty());
        //TODO

    }

}