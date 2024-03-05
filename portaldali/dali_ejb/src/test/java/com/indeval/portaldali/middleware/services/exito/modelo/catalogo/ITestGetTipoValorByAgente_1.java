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


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetTipoValorByAgente_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetTipoValorByAgente_1.class);

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
     *
     * @throws BusinessException
     */
    public void testGetTipoValorByAgente_1() throws BusinessException {
        
        log.info("Entrando a ITestGetTipoValorByAgente_1.testGetTipoValorByAgente_1()");
        
        assertNotNull(catalogoService);
        
        AgenteVO agenteVO = new AgenteVO();
        //TODO
//        String[] tiposValor = catalogoService.getTipoValorByAgente(agenteVO, "");
//        
//        assertNotNull(tiposValor);
//        assertTrue(tiposValor.length > 0);
        //TODO
        
    }

}