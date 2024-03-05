/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.util.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.util.UtilServices;

/**
 * Clase de prueba para el servicio validaFase() de UtilService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestValidaFase_1 extends BaseITestService {

    private UtilServices utilService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @see com.indeval.portaldali.middleware.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {

        super.onSetUp();

        if (utilService == null) {

            utilService = (UtilServices) applicationContext.getBean("utilService");

        }

    }

    /**
     * 
     * @throws BusinessException
     */
    public void testValidaFase() throws BusinessException {

        log.debug("Entrando al metodo testValidaFase()");
        
        assertNotNull(utilService);

        Map mapaFasesValidas = new HashMap();
        mapaFasesValidas.put("1", "ABIERTO");
        mapaFasesValidas.put("2", "ABIERTO");
        mapaFasesValidas.put("3", "ABIERTO");
        mapaFasesValidas.put("4", "ABIERTO");
        mapaFasesValidas.put("5", "ABIERTO");
        mapaFasesValidas.put("6", "ABIERTO");
        mapaFasesValidas.put("7", "ABIERTO");
        mapaFasesValidas.put("8", "ABIERTO");
        mapaFasesValidas.put("9", "ABIERTO");
        mapaFasesValidas.put("10", "ABIERTO");
        mapaFasesValidas.put("11", "ABIERTO");
        mapaFasesValidas.put("12", "ABIERTO");
        mapaFasesValidas.put("13", "ABIERTO");
        mapaFasesValidas.put("14", "ABIERTO");

        int faseValida = 0;

        try {

            faseValida = utilService.validaFase(mapaFasesValidas);

        }
        catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        String buffer = (faseValida > 0) ? "ABIERTA" : "CERRADA";
        log.debug("La operacion esta " + buffer + " para la fase " + faseValida);
        log.debug("La operacion esta " + buffer + " para la fase " + faseValida);

    }

}
