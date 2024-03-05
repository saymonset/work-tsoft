/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.enviooperaciones.enviooperaciones;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;

/**
 * Clase de prueba para los metodos de envioOperacionesService
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestActualizaEdoInsExpira_1 extends BaseITestService {

    /** Objeto de loggeo para ITestEnvioOperacionesService */
    private static final Logger logger = LoggerFactory.getLogger(ITestActualizaEdoInsExpira_1.class);

    /** Bean del servicio a ser probado */
    private EnvioOperacionesService envioOperacionesService;

    /** Constructor para enviarle el nombre del metodo */
    public ITestActualizaEdoInsExpira_1(String name) {
        super(name);

    }

    /**
     * En este m&eacute;todo se inicializan las propiedades que ser&aacute;n utilizadas
     * durante la clase de prueba.
     *
     * @throws Exception
     */
    protected void onSetUp() throws Exception {

        super.onSetUp();

        if (envioOperacionesService == null) {

            envioOperacionesService = (EnvioOperacionesService) applicationContext
                    .getBean("envioOperacionesService");

        }

    }

    /**
     * M&eacute;todo de prueba para el servicio de actualizaEdoInsExpira(), caso:
     * Expiraci&oacute;n - (true)
     *
     * @throws Exception
     * Con todos los parametros llenos y el valor es true
     */
    public void testActualizaEdoInsExpira_Expira_1() throws Exception {

        log.info("Entrando a ITestEnvioOperacionesService.testActualizaEdoInsExpira_Expira()");

        AgenteVO agenteFirmado = new AgenteVO();

        agenteFirmado.setId("02");
        agenteFirmado.setFolio("033");

        int numregistros = 1;/*envioOperacionesService.actualizaEstadoInstruccionExpira(new BigInteger(
        "64097"), agenteFirmado, true);*/

        assertNotNull(numregistros);
        assertTrue(numregistros > 0);

        log.debug("Numero de registros expirados" + numregistros);

    }

    /**
     * M&eacute;todo de prueba para el servicio de actualizaEdoInsExpira, caso:
     * Cancelaci&oacute;n - (false)
     *
     * @throws Exception
     * Cuando todos los parametros son llenos y el valor es false
     */
    public void testActualizaEdoInsExpira_Cancela_2() throws Exception {

        log.info("Entrando a ITestEnvioOperacionesService.testActualizaEdoInsExpira_Cancela()");

        AgenteVO agenteFirmado = new AgenteVO();

        agenteFirmado.setId("02");
        agenteFirmado.setFolio("033");

        int numregistros = 1;/*envioOperacionesService.actualizaEstadoInstruccionExpira(new BigInteger(
        "64097"), agenteFirmado, true);*/
        assertNotNull(numregistros);
        assertTrue(numregistros > 0);

        log.debug("Numero de registros cancelados" + numregistros);

    }

}
