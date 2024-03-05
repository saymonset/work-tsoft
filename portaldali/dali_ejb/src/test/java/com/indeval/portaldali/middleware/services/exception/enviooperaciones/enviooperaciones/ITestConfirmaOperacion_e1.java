/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.enviooperaciones.enviooperaciones;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;

/**
 * Clase de prueba para los metodos de envioOperacionesService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestConfirmaOperacion_e1 extends BaseITestService {

    /** Objeto de loggeo para ITestConfirmaOperacion_1 */
    private static final Logger logger = LoggerFactory.getLogger(ITestConfirmaOperacion_e1.class);

    /** Bean del servicio a ser probado */
    private EnvioOperacionesService envioOperacionesService;

    /** Constructor para enviarle el nombre del metodo */
    public ITestConfirmaOperacion_e1(String name) {
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
     * M&eacute;todo de prueba para el servicio confirmaOperacion , cuando no se
     * encuentra una operaci&oacute;n de match
     * 
     * @throws Exception
     */
    public void testConfirmaOperacionFalla() throws Exception {

        log.info("Entrando al metodo testConfirmaOperacion");

        BigInteger idBitacoraMatch = new BigInteger("64800");
        AgenteVO agenteFirmado = new AgenteVO("01", "006");
        try {
           // envioOperacionesService.confirmaOperacionMatch(idBitacoraMatch, agenteFirmado, null, null,null);
            log.debug("Verificar validacion");
            Assert.isTrue(false);
        }
        catch (BusinessException e) {
            e.printStackTrace();
        }

    }

    /**
     * M&eacute;todo de prueba para el servicio confirmaOperacion, cuando el agente no
     * tiene permiso para confirmar la operacion
     * 
     * @throws Exception
     */
    public void testConfirmaOperacionSinPermiso() throws Exception {

        log.info("Entrando al metodo testConfirmaOperacion");

        BigInteger idBitacoraMatch = new BigInteger("65749");
        AgenteVO agenteFirmado = new AgenteVO("02", "015");
        try {
           // envioOperacionesService.confirmaOperacionMatch(idBitacoraMatch, agenteFirmado,null,null,null);
            log.debug("Verificar validacion");
            Assert.isTrue(false);
        }
        catch (BusinessException e) {
            e.printStackTrace();
        }

    }

}
