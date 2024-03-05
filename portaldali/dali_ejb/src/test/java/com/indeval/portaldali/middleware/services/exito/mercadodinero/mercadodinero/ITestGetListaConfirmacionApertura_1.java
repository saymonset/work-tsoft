/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.TraspasoMiscFiscalVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;

/**
 * La clase ITestMercadoDineroService tiene como objetivo probar los
 * m&eacute;todos creados en la clase MercadoDineroService
 *
 * @author Agustin Calderon Orduña.
 * @author Claudia Becerril Rejon.
 * @author Erick Navarrete Sevilla.
 * @author Domingo Suarez Torres.
 * @author Jose Guadalupe Aviles.
 * @author Ricardo Caballero.
 * @author Sergio Mena Zamora.
 * @author Salvador Valeriano Lucas.
 */
public class ITestGetListaConfirmacionApertura_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetListaConfirmacionApertura_1.class);

    /** Inyecci&oacute;n del bean mercadoDineroService */
    private MercadoDineroService mercadoDineroService;

    /** Bean de acceso a DateUtilsDao */
    private DateUtilsDao dateUtilsDao;

    /**
     * En el m&eacute;todo onSetUp inicializamos los beans que utilizaremos en
     * la clase de prueba.
     *
     * @throws Exception
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (mercadoDineroService == null) {
            mercadoDineroService = (MercadoDineroService) applicationContext
                    .getBean("mercadoDineroService");
        }
        if (dateUtilsDao == null) {
            dateUtilsDao = (DateUtilsDao) applicationContext.getBean("dateUtilsDao");
        }
    }

    /**
     * TestCase para MercadoDineroDao.getListaConfirmacionApertura()()
     * TODO - El servicio depende de la fecha del dia
     *
     * @throws BusinessException
     */
    public void testGetListaConfirmacionApertura() throws BusinessException {

        log.info("Entrando a ITestGetListaConfirmacionApertura_1.testGetListaConfirmacionApertura()");
        
        assertNotNull(mercadoDineroService);

        boolean aperturaSistema = true; // Pruebas para MD
        boolean cicloPruebas = true;

        while (cicloPruebas) {

            if (aperturaSistema) {
                log.debug("Pruebas para Mercado de Dinero");
            } else {
                log.debug("Pruebas para Mercado de Capitales");
                cicloPruebas = false;
            }

            log.debug("Probando con el agente NULL");
            long startTime = System.currentTimeMillis();
            try {
                mercadoDineroService.getListaConfirmacionApertura(null,
                        aperturaSistema);
                log.debug("Checar Validacion");
                assertTrue(false);
            } catch (BusinessException e) {
                log.debug(e.getMessage());
            }
            long endTime = System.currentTimeMillis();
            double milisegundos = (endTime - startTime);
            log
                    .debug("Milisegundos en responder el servicio GetListaConfirmacionApertura():"
                            + milisegundos);

            log.debug("Probando con el agente VACIO");
            AgenteVO agente = new AgenteVO();
            startTime = System.currentTimeMillis();
            try {
                mercadoDineroService.getListaConfirmacionApertura(agente,
                        aperturaSistema);
                log.debug("Checar Validacion");
                assertTrue(false);
            } catch (BusinessException e) {
                log.debug(e.getMessage());
            }
            endTime = System.currentTimeMillis();
            milisegundos = (endTime - startTime);
            log
                    .debug("Milisegundos en responder el servicio GetListaConfirmacionApertura():"
                            + milisegundos);

            log.debug("Probando solo con el id del agente");
            agente.setId("01");
            startTime = System.currentTimeMillis();
            try {
                mercadoDineroService.getListaConfirmacionApertura(agente,
                        aperturaSistema);
                log.debug("Checar Validacion");
                assertTrue(false);
            } catch (BusinessException e) {
                log.debug(e.getMessage());
            }
            endTime = System.currentTimeMillis();
            milisegundos = (endTime - startTime);
            log
                    .debug("Milisegundos en responder el servicio GetListaConfirmacionApertura():"
                            + milisegundos);

            log.debug("Probando con un agente invalido");
            agente.setId("01");
            agente.setFolio("000");
            startTime = System.currentTimeMillis();
            try {
                mercadoDineroService.getListaConfirmacionApertura(agente,
                        aperturaSistema);
                log.debug("Checar Validacion");
                assertTrue(false);
            } catch (BusinessException e) {
                log.debug(e.getMessage());
            }
            endTime = System.currentTimeMillis();
            milisegundos = (endTime - startTime);
            log
                    .debug("Milisegundos en responder el servicio GetListaConfirmacionApertura():"
                            + milisegundos);

            log.debug("Probando con un agente valido");
            try {
                agente.setId("01");
                agente.setFolio("001");
                startTime = System.currentTimeMillis();
                TraspasoMiscFiscalVO[] arregloTraspasoMiscFiscalVO = mercadoDineroService
                        .getListaConfirmacionApertura(agente, aperturaSistema);
                endTime = System.currentTimeMillis();
                milisegundos = (endTime - startTime);
                log
                        .debug("Milisegundos en responder el servicio GetListaConfirmacionApertura():"
                                + milisegundos);
                assertNotNull(arregloTraspasoMiscFiscalVO);
                log.debug("El arreglo de TraspasoMiscFiscalVO tiene ["
                        + arregloTraspasoMiscFiscalVO.length + "] elementos");
                assertTrue(arregloTraspasoMiscFiscalVO.length > 0);
                for (int i = 0; i < arregloTraspasoMiscFiscalVO.length; i++) {
                    log
                            .debug("Emision==> "
                                    + arregloTraspasoMiscFiscalVO[i]
                                            .getEmision().getIdTipoValor()
                                    + ", "
                                    + arregloTraspasoMiscFiscalVO[i]
                                            .getEmision().getEmisora()
                                    + ", "
                                    + arregloTraspasoMiscFiscalVO[i]
                                            .getEmision().getSerie()
                                    + ", "
                                    + arregloTraspasoMiscFiscalVO[i]
                                            .getEmision().getCupon()
                                    + " cantidad operada==> "
                                    + arregloTraspasoMiscFiscalVO[i]
                                            .getCantidadOperada()
                                    + " fechaLiquidacion==> "
                                    + arregloTraspasoMiscFiscalVO[i]
                                            .getFechaLiquidacion()
                                    + " folioControl==> "
                                    + arregloTraspasoMiscFiscalVO[i].getFolio()
                                    + " cliente==> "
                                    + arregloTraspasoMiscFiscalVO[i]
                                            .getCliente()
                                    + " curp/rfc==> "
                                    + arregloTraspasoMiscFiscalVO[i]
                                            .getCurpRFC()
                                    + " precioAdquisicion==> "
                                    + arregloTraspasoMiscFiscalVO[i]
                                            .getPrecioAdquisicion()
                                    + " fechaAdquisicion==> "
                                    + arregloTraspasoMiscFiscalVO[i]
                                            .getFechaAquisicion()
                                    + " Traspasante==> "
                                    + ReflectionToStringBuilder
                                            .reflectionToString(arregloTraspasoMiscFiscalVO[i]
                                                    .getTraspasante())
                                    + " Receptor ==> "
                                    + ReflectionToStringBuilder
                                            .reflectionToString(arregloTraspasoMiscFiscalVO[i]
                                                    .getReceptor()));
                }
                log.debug("Total de registros==> "
                        + arregloTraspasoMiscFiscalVO.length);
            } catch (BusinessException be) {
                log
                        .debug(be.getMessage()
                                + " - deberia mostrar datos con los criterios de seleccion");
            }

            aperturaSistema = false; // Pruebas para MC

        }

        log
                .debug("Ejecucion exitosa del testEJBGetListaConfirmacionApertura()");

    }

    /**
     * Prueba del servicio getListaConfirmacionApertura2()
     */
    public void testGetListaConfirmacionApertura2() {
        assertNotNull(mercadoDineroService);
        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("001");
        try {
            TraspasoMiscFiscalVO[] resultado = mercadoDineroService
                    .getListaConfirmacionApertura(agenteVO, true);
            for (int i = 0; i < resultado.length; i++) {
                log.debug("LISTA: "
                        + ReflectionToStringBuilder
                                .reflectionToString(resultado[i]));
            }
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

}