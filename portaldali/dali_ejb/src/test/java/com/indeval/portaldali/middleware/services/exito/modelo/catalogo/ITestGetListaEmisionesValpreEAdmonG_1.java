/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.modelo.catalogo;

import java.math.BigInteger;

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
public class ITestGetListaEmisionesValpreEAdmonG_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetListaEmisionesValpreEAdmonG_1.class);

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
     * @throws BusinessException
     */
    public void testGetListaEmisionesValpreEAdmonG_1() throws BusinessException {

        log.info("Entrando a ITestGetListaEmisionesValpreEAdmonG_1." +
                "testGetListaEmisionesValpreEAdmonG_1()");
        
        assertNotNull(catalogoService);
        BigInteger idBoveda = new BigInteger("0");
        
        PaginaVO paginaVO = catalogoService.getListaEmisionesValpreEAdmonG(new AgenteVO(), new EmisionVO(), 
                idBoveda, new PaginaVO());
        
        assertNotNull(paginaVO);
        assertNotNull(paginaVO.getRegistros());
        assertTrue(!paginaVO.getRegistros().isEmpty());
        
    }

}