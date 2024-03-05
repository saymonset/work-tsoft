/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.ConsultaEstatusOperacionesParams;
import com.indeval.portaldali.middleware.services.mercadodinero.EstatusOperacionesVO;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
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
public class ITestConsultaEstatusOperaciones_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestConsultaEstatusOperaciones_1.class);

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
     * TestCase para MercadoDineroDao.consultaEstatusOperaciones()
     *
     * @throws BusinessException
     */
    public void testConsultaEstatusOperaciones() throws BusinessException {
        
        log.info("Entrando a ITestConsultaEstatusOperaciones_1.testConsultaEstatusOperaciones()");

        assertNotNull(mercadoDineroService);

        log.debug("Probando con el params NULL");
        ConsultaEstatusOperacionesParams params = null;

        long startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaEstatusOperaciones(params);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaEstatusOperaciones():"
                        + milisegundos);

        log.debug("Probando con el params VACIO");
        params = new ConsultaEstatusOperacionesParams();
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaEstatusOperaciones(params);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaEstatusOperaciones():"
                        + milisegundos);

        log.debug("Probando con el params setteado con el Agente VACIO");
        params.setAgenteFirmado(new AgenteVO());
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaEstatusOperaciones(params);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaEstatusOperaciones():"
                        + milisegundos);

        log.debug("Probando con el params setteado con el Agente con clave");
        params.setAgenteFirmado(new AgenteVO("02", "039"));
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaEstatusOperaciones(params);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaEstatusOperaciones():"
                        + milisegundos);

        log
                .debug("Probando con el params setteado con el Agente con clave y la cuenta");
        // params.setCuentaPropia("0009");
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaEstatusOperaciones(params);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaEstatusOperaciones():"
                        + milisegundos);

        log
                .debug("Probando con el params setteado con el Agente con clave, la cuenta "
                        + "y el tipo de operacion");
        String[] tipoOperacion_T = { "T" };
        params.setIdTipoOperacion(tipoOperacion_T);
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaEstatusOperaciones(params);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaEstatusOperaciones():"
                        + milisegundos);

        log
                .debug("Probando con el params setteado con el Agente con clave, la cuenta,"
                        + " el tipo de operacion y el tipo de papel");
        params.setIdTipoPapel(MercadoDineroService.AMBOS_INIC);
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.consultaEstatusOperaciones(params);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaEstatusOperaciones():"
                        + milisegundos);

        log
                .debug("Probando con el objeto params completo y el tipo Operacion T");
        params.setStatus(Constantes.TODOS);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2007, Calendar.FEBRUARY, 02);
        params.setFechaLiquidacion(calendar.getTime());

        startTime = System.currentTimeMillis();
        EstatusOperacionesVO estatusOperacionesVO = mercadoDineroService
                .consultaEstatusOperaciones(params);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaEstatusOperaciones():"
                        + milisegundos);

        assertNotNull(estatusOperacionesVO);
        log.debug("EstatusOperacionesVO = ["
                + ToStringBuilder.reflectionToString(estatusOperacionesVO)
                + "]");
        assertNotNull(estatusOperacionesVO.getRegistros());
        log
                .debug("Hay ["
                        + estatusOperacionesVO.getRegistros().length
                        + "] registros en estatusOperacionesVO para el tipo Operacion T");
        assertTrue(estatusOperacionesVO.getRegistros().length > 0);
        UtilsLog.logElementosArreglo(estatusOperacionesVO.getRegistros(), true);
        UtilsLog
                .logElementosArreglo(estatusOperacionesVO.getRegistros(), false);

        log
                .debug("Probando con el objeto params completo, la emision y el tipo Operacion T");
        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("M");
        emisionVO.setEmisora("GOBFED");
        emisionVO.setSerie("361120");
        emisionVO.setCupon("0000");
        params.setEmision(emisionVO);

        startTime = System.currentTimeMillis();
        estatusOperacionesVO = mercadoDineroService
                .consultaEstatusOperaciones(params);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio ConsultaEstatusOperaciones():"
                        + milisegundos);
        assertNotNull(estatusOperacionesVO);
        log.debug("EstatusOperacionesVO = ["
                + ToStringBuilder.reflectionToString(estatusOperacionesVO)
                + "]");
        assertNotNull(estatusOperacionesVO.getRegistros());
        log
                .debug("Hay ["
                        + estatusOperacionesVO.getRegistros().length
                        + "] registros en estatusOperacionesVO para el tipo Operacion T");
        assertTrue(estatusOperacionesVO.getRegistros().length > 0);
        UtilsLog.logElementosArreglo(estatusOperacionesVO.getRegistros(), true);
        UtilsLog
                .logElementosArreglo(estatusOperacionesVO.getRegistros(), false);

        // log.debug("Probando con el objeto params completo y el tipo Operacion
        // X");
        // String[] tipoOperacion_X = {"X"};
        // params.setIdTipoOperacion(tipoOperacion_X);
        //
        // estatusOperacionesVO =
        // mercadoDineroService.consultaEstatusOperaciones(params);
        // assertNotNull(estatusOperacionesVO);
        // log.debug("EstatusOperacionesVO = [" +
        // ToStringBuilder.reflectionToString(estatusOperacionesVO) + "]");
        // assertNotNull(estatusOperacionesVO.getRegistros());
        // log.debug("Hay [" + estatusOperacionesVO.getRegistros().length +
        // "] registros en estatusOperacionesVO para el tipo Operacion X");
        // assertTrue(estatusOperacionesVO.getRegistros().length>0);
        UtilsLog.logElementosArreglo(estatusOperacionesVO.getRegistros(), true);
        UtilsLog
                .logElementosArreglo(estatusOperacionesVO.getRegistros(), false);

        log.debug("Ejecucion exitosa del testEJBFindMdintranByExample()");

    }
    

    /**
     * TestCase para MercadoDineroDao.consultaEstatusOperaciones()
     *
     * @throws BusinessException
     */
    public void testConsultaEstatusOperacionesSalvador()
            throws BusinessException {
        ConsultaEstatusOperacionesParams params = new ConsultaEstatusOperacionesParams();

        params.setAgenteFirmado(new AgenteVO("02", "061"));
        // String[] tipoOperacion_T = {"T"};
        // params.setIdTipoOperacion(tipoOperacion_T);
        params.setRol(MercadoDineroService.AMBOS_INIC);
//        params.setIdTipoPapel(MercadoDineroService.PAPEL_GUBERNAMENTAL);
        params.setIdTipoPapel(MercadoDineroService.AMBOS_INIC);
        params.setBajaLogica("F");
        Calendar c = Calendar.getInstance();
        c.set(2007, Calendar.AUGUST, 29, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);

        Date fechaLiquidacion = c.getTime();

        Calendar concertacion = Calendar.getInstance();
        concertacion.set(2007, Calendar.AUGUST,27,0,0,0);


        AgenteVO contraparte = new AgenteVO();
        //contraparte.setCuenta("6923");
        params.setContraparte(contraparte);
        params.setFechaLiquidacion(fechaLiquidacion);
        params.setFechaConcertacion(concertacion.getTime());

        try {
            assertNotNull(mercadoDineroService
                    .consultaEstatusOperaciones(params));
            log.debug("Checar validacion");
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }

        EstatusOperacionesVO estatusOperacionesVO = mercadoDineroService
                .consultaEstatusOperaciones(params);

        log.debug("EstatusOperacionesVO = ["
                + ToStringBuilder.reflectionToString(estatusOperacionesVO
                        .getRegistros()[0]) + "]");
        assertNotNull(estatusOperacionesVO.getRegistros());
        log
                .debug("Hay ["
                        + estatusOperacionesVO.getRegistros().length
                        + "] registros en estatusOperacionesVO para el tipo Operacion T");
        assertTrue(estatusOperacionesVO.getRegistros().length > 0);
    }

}