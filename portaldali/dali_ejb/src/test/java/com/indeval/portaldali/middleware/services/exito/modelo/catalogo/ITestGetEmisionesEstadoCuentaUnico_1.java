/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.modelo.catalogo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadocapitales.EstadoCuentaUnicoParams;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.CatalogoService;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetEmisionesEstadoCuentaUnico_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetEmisionesEstadoCuentaUnico_1.class);

    /** Inyecci&oacute;n del bean mercadoDineroService */
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
     * TestCase para mercadoDineroService.cambiarEstatus()
     *
     * @throws BusinessException
     */
    public void testGetEmisionesEstadoCuentaUnico_1() throws BusinessException {

        log.info("Entrando a ITestGetEmisionesEstadoCuentaUnico_1." +
                "testGetEmisionesEstadoCuentaUnico_1()");
        
        assertNotNull(catalogoService);
        
        EstadoCuentaUnicoParams estadoCuentaUnicoParams = new EstadoCuentaUnicoParams();
        //TODO
        //EmisionVO[] arregloEmisionVO = catalogoService.getEmisionesEstadoCuentaUnico(estadoCuentaUnicoParams);
//        
//        assertNotNull(arregloEmisionVO);
//        assertTrue(arregloEmisionVO.length > 0);
        //TODO
        
    }

}