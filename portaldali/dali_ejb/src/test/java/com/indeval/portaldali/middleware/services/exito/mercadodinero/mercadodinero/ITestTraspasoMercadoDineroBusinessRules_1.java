/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.TraspasoMercadoDineroParams;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;


/**
 * Casos de prueba de traspasoMercadoDineroBusinessRules() para Apertura de Sistema
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestTraspasoMercadoDineroBusinessRules_1 extends BaseITestService {
    
    /** Objeto de loggeo de clase */
    private static final Log log = 
        LogFactory.getLog(ITestTraspasoMercadoDineroBusinessRules_1.class);

    /** Inyecci&oacute;n del bean mercadoDineroService */
    private MercadoDineroService mercadoDineroService;

    /**
     * @see com.indeval.portaldali.middleware.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (mercadoDineroService == null) {
            mercadoDineroService = (MercadoDineroService) applicationContext
                    .getBean("mercadoDineroService");
        }
    }

    /**
     * @throws BusinessException
     */
    public void testTraspasoMercadoDineroBusinessRules_1() throws BusinessException {
        
        log.info("Entrando a ITestTraspasoMercadoDineroBusinessRules_1." +
                "testTraspasoMercadoDineroBusinessRules_1()");
        
        TraspasoMercadoDineroParams params = new TraspasoMercadoDineroParams();
        params.setTipoMovimiento("APERTURA");
        params.setIdTipoOperacion("T");
        
        AgenteVO traspasante = new AgenteVO("02","013","6502");
        params.setTraspasante(traspasante);
        
        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("2P");
        emisionVO.setEmisora("PMX0001");
        emisionVO.setSerie("150716");
        emisionVO.setCupon("0005");
        params.setEmision(emisionVO);
        
        AgenteVO receptor = new AgenteVO("01","003","0307");
        params.setReceptor(receptor);
        
        params.setCantidad(BigDecimal.valueOf(1000));
        
        params.setAceptaCargo(true);
        
    }
    
}
