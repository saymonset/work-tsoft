/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.modelo.catalogo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.CatalogoService;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetListaEmisionesEstadoCuentaSociedadesInversion_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetListaEmisionesEstadoCuentaSociedadesInversion_1.class);

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
    public void testGetListaEmisionesEstadoCuentaSociedadesInversion_1() throws BusinessException {

        log.info("Entrando a ITestGetListaEmisionesEstadoCuentaSociedadesInversion_1." +
                "testGetListaEmisionesEstadoCuentaSociedadesInversion_1()");
        
        assertNotNull(catalogoService);
        
        //TODO
//        PaginaVO paginaVO = catalogoService.getListaEmisionesEstadoCuentaSociedadesInversion(
//                new AgenteVO(), new EmisionVO());
//        assertNotNull(paginaVO);
//        assertNotNull(paginaVO.getRegistros());
//        assertTrue(!paginaVO.getRegistros().isEmpty());
        //TODO
        
    }

}