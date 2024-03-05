/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.modelo.catalogo;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.CatalogoService;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetListaEmisionesTesoreria_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetListaEmisionesTesoreria_1.class);

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
     * TestCase para catalogoService
     *
     * @throws BusinessException
     */
    public void testGetListaEmisionesTesoreria_1() throws BusinessException {

        log.info("Entrando a ITestGetListaEmisionesTesoreria_1.testGetListaEmisionesTesoreria_1()");
        
        assertNotNull(catalogoService);
        BigInteger idBoveda = new BigInteger("0");
        
        PaginaVO paginaVO = catalogoService.getListaEmisionesTesoreria(new EmisionVO(), idBoveda,
                Boolean.TRUE, true, new PaginaVO());
        assertNotNull(paginaVO);
        assertNotNull(paginaVO.getRegistros());
        assertTrue(!paginaVO.getRegistros().isEmpty());

    }

}