/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.PosicionValorGarantiaVO;
import com.indeval.portaldali.middleware.services.mercadodinero.PosicionValoresParams;
import com.indeval.portaldali.middleware.services.mercadodinero.PosicionValoresVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.util.UtilsLog;

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
public class ITestConsultaPosicionValores_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestConsultaPosicionValores_1.class);

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
     * TestCase para el metodo consultaPosicionValores()
     *
     * @throws BusinessException
     */
    public void testConsultaPosicionValores() throws BusinessException {
        
        log.info("Entrando a ITestConsultaPosicionValores_1.testConsultaPosicionValores()");

        assertNotNull(mercadoDineroService);

        log.debug("Probando con el params NULL");
        PosicionValoresParams params = null;
        long startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaPosicionValores(params);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaPosicionValores():"
                        + milisegundos);

        log.debug("Probando con el params VACIO");
        params = new PosicionValoresParams();
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaPosicionValores(params);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaPosicionValores():"
                        + milisegundos);

        log.debug("Buscando todos los registros...");
        params = factoryPosicionValoresParams(1);
        PaginaVO pagina = new PaginaVO();
        params.setPaginaVO(pagina);
        startTime = System.currentTimeMillis();
        PaginaVO paginaRetornada = mercadoDineroService
                .consultaPosicionValores(params);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaPosicionValores():"
                        + milisegundos);
        assertNotNull(paginaRetornada);
        assertNotNull(paginaRetornada.getRegistros());
        assertTrue(paginaRetornada.getRegistros().size() > 0);
        log.debug("Numero de Registros x Pagina : ["
                + paginaRetornada.getRegistrosXPag() + "]");
        log.debug("Total de Registros disponibles : ["
                + paginaRetornada.getTotalRegistros() + "]");
        assertTrue(paginaRetornada.getRegistros().get(0).getClass().isInstance(
                new PosicionValoresVO()));
        UtilsLog.logElementosLista(paginaRetornada.getRegistros(), true);
        UtilsLog.logElementosLista(paginaRetornada.getRegistros(), false);

        log.debug("Ejecucion exitosa del testEJBConsultaPosicionValores()");

    }

    /**
     * TestCase para el metodo consultaPosicionValores() solicitando los
     * registros del 0 al 29
     *
     * @throws BusinessException
     */
    public void testConsultaPosicionValores029() throws BusinessException {

        log.debug("Entrando al testEJBConsultaPosicionValores029()");
        assertNotNull(mercadoDineroService);

        PosicionValoresParams params = factoryPosicionValoresParams(1);

        log.debug("Buscando los 30 primeros registros...");
        PaginaVO pagina = new PaginaVO();
        pagina.setRegistrosXPag(new Integer(30));
        params.setPaginaVO(pagina);
        long startTime = System.currentTimeMillis();
        PaginaVO paginaRetornada = mercadoDineroService
                .consultaPosicionValores(params);
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaPosicionValores():"
                        + milisegundos);
        assertNotNull(paginaRetornada);
        assertNotNull(paginaRetornada.getRegistros());
        assertTrue(paginaRetornada.getRegistros().size() > 0);
        log.debug("Numero de Registros x Pagina : ["
                + paginaRetornada.getRegistrosXPag() + "]");
        log.debug("Total de Registros disponibles : ["
                + paginaRetornada.getTotalRegistros() + "]");
        assertTrue(paginaRetornada.getRegistros().get(0).getClass().isInstance(
                new PosicionValoresVO()));
        UtilsLog.logElementosLista(paginaRetornada.getRegistros(), true);
        UtilsLog.logElementosLista(paginaRetornada.getRegistros(), false);

        // Para ver la emision
        PosicionValoresVO posicionValoresVO = (PosicionValoresVO) paginaRetornada
                .getRegistros().get(0);
        log.debug("["
                + ToStringBuilder.reflectionToString(posicionValoresVO
                        .getEmision()) + "]");

        log.debug("Ejecucion exitosa del testEJBConsultaPosicionValores029()");

    }

    /**
     * Factory para el PosicionValoresParams requerido en los testCase de
     * consultaPosicionValores()
     *
     * @throws BusinessException
     */
    private PosicionValoresParams factoryPosicionValoresParams(int i) {

        log.debug("Entrando al factoryPosicionValoresParams()");

        PosicionValoresParams params = new PosicionValoresParams();

        EmisionVO emisionVO = new EmisionVO();
        switch (i) {
            case 4:
                emisionVO.setCupon("0009");
            case 3:
                emisionVO.setSerie("120809");
            case 2:
                emisionVO.setEmisora("CBIC001");
            case 1:
                emisionVO.setIdTipoValor("2U");
            default:
                params.setClaveValor(emisionVO);
                params.setConsultante(new AgenteVO("01", "001", "0109"));
                break;
        }

        params.setFecha(dateUtilsDao.getDateFechaDB());
        params.setIdTipoPapel("");

        return params;
    }

    /**
     * TestCase para el servicio de consultaPosicionValoresGarantias()
     *
     * @throws BusinessException
     */
    public void testConsultaPosicionValoresGarantias()
            throws BusinessException {

        log.debug("Entrando a testEJBConsultaPosicionValoresGarantias()");
        assertNotNull(mercadoDineroService);

        log.debug("Probando con los parametros NULL");
        long startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaPosicionValoresGarantias(null, null,
                    null);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaPosicionValoresGarantias():"
                        + milisegundos);

        log.debug("Probando solo con la emision VACIA");
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaPosicionValoresGarantias(
                    new EmisionVO(), null, null);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaPosicionValoresGarantias():"
                        + milisegundos);

        log.debug("Probando solo con la emision y el Agente VACIOS");
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaPosicionValoresGarantias(
                    new EmisionVO(), null, new AgenteVO());
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaPosicionValoresGarantias():"
                        + milisegundos);

        log
                .debug("Probando con el TipoPapel, pero con la emision y el Agente VACIOS");
        String idTipoPapel = "A"; // Debe llevar PB, PG o A
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaPosicionValoresGarantias(
                    new EmisionVO(), "A", new AgenteVO());
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaPosicionValoresGarantias():"
                        + milisegundos);

        log.debug("Probando con todos los parametros");
        EmisionVO emision = new EmisionVO();
        emision.setIdTipoValor("IP");
        // emision.setEmisora("BPAS"); //Opcional
        // emision.setSerie("080117"); //Opcional
        // emision.setCupon("0000"); //Opcional

        AgenteVO agente = new AgenteVO("02", "014");
        startTime = System.currentTimeMillis();
        PosicionValorGarantiaVO[] arregloPosicionValorGarantia = mercadoDineroService
                .consultaPosicionValoresGarantias(emision, idTipoPapel, agente);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaPosicionValoresGarantias():"
                        + milisegundos);
        assertNotNull(arregloPosicionValorGarantia);
        log.debug("El arreglo de PosicionValorGarantiaVO tiene ["
                + arregloPosicionValorGarantia.length + "] elementos");
        assertTrue(arregloPosicionValorGarantia.length > 0);
        UtilsLog.logElementosArreglo(arregloPosicionValorGarantia, true);
        UtilsLog.logElementosArreglo(arregloPosicionValorGarantia, false);

        log
                .debug("Ejecucion exitosa del testEJBConsultaPosicionValoresGarantias()");

    }

}