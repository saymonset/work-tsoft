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
import com.indeval.portaldali.persistence.util.UtilsLog;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetListaEmisionesDinero_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetListaEmisionesDinero_1.class);

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
    public void testGetListaEmisionesDinero_1() throws BusinessException {

        log.info("Entrando a ITestGetListaEmisionesDinero_1.testGetListaEmisionesDinero_1()");

        assertNotNull(catalogoService);
        
        AgenteVO agenteVO = new AgenteVO("01","003","0307");
        EmisionVO emisionVO = new EmisionVO();
        BigInteger idBoveda = new BigInteger("0");
        
        EmisionVO[] arregloEmisionVO = catalogoService.getListaEmisionesDinero(agenteVO, emisionVO, idBoveda);
        
        assertNotNull(arregloEmisionVO);
        assertTrue(arregloEmisionVO.length > 0);
        
        UtilsLog.logElementosArreglo(arregloEmisionVO, true);
        UtilsLog.logElementosArreglo(arregloEmisionVO, false);
        
    }
    
    /**
     * TestCase para catalogoService
     *
     * @throws BusinessException
     */
    public void testGetListaEmisionesDinero_2() throws BusinessException {

        log.info("Entrando a ITestGetListaEmisionesDinero_1.testGetListaEmisionesDinero_1()");

        assertNotNull(catalogoService);
        
        AgenteVO agenteVO = new AgenteVO("01","003","0307");
        EmisionVO emisionVO = new EmisionVO();
        BigInteger idBoveda = new BigInteger("0");
        
        PaginaVO paginaVO = catalogoService.getListaEmisionesDinero(agenteVO, emisionVO, idBoveda, new PaginaVO());
        
        assertNotNull(paginaVO);
        assertNotNull(paginaVO.getRegistros());        
        assertTrue(!paginaVO.getRegistros().isEmpty());
        
        UtilsLog.logElementosLista(paginaVO.getRegistros());
        
    }

}