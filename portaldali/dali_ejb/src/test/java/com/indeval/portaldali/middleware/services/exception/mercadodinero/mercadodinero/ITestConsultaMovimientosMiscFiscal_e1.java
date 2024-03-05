/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.ConsultaMovimientosMiscFiscalParams;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
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
public class ITestConsultaMovimientosMiscFiscal_e1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestConsultaMovimientosMiscFiscal_e1.class);

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
     * TestCase para MercadoDineroDao.consultaMovimientosMiscFiscal()()
     *
     * @throws BusinessException
     */
    public void testConsultaMovimientosMiscFiscal() throws BusinessException {
        
        log.info("Entrando a ITestConsultaMovimientosMiscFiscal_1.testConsultaMovimientosMiscFiscal()");

        assertNotNull(mercadoDineroService);

        // log.debug("Probando con el params nulo");
        ConsultaMovimientosMiscFiscalParams params = null;
        long startTime = System.currentTimeMillis();
        // try {
        // mercadoDineroService.consultaMovimientosMiscFiscal(params);
        // log.debug("Checar validacion");
        // assertTrue(false);
        // }
        // catch (BusinessException e) {
        // log.debug(e.getMessage());
        // }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        // log.debug("Milisegundos en responder el servicio
        // ConsultaEstatusGarantias():" +
        // milisegundos);

        // log.debug("Probando con el params vacio");
        params = new ConsultaMovimientosMiscFiscalParams();
        // startTime = System.currentTimeMillis();
        // try {
        // mercadoDineroService.consultaMovimientosMiscFiscal(params);
        // log.debug("Checar validacion");
        // assertTrue(false);
        // }
        // catch (BusinessException e) {
        // log.debug(e.getMessage());
        // }
        // endTime = System.currentTimeMillis();
        // milisegundos = (endTime - startTime);
        // log.debug("Milisegundos en responder el servicio
        // ConsultaEstatusGarantias():" +
        // milisegundos);

        // log.debug("Probando con el params solo con el mercado Dinero");
        // params.setMercado(Boolean.TRUE);
        //
        // Calendar calendar = Calendar.getInstance();
        // calendar.set(2006, Calendar.OCTOBER, 24, 0, 0, 0);
        // params.setFechaConsulta(calendar.getTime());
        // startTime = System.currentTimeMillis();
        // try {
        // mercadoDineroService.consultaMovimientosMiscFiscal(params);
        // log.debug("Checar validacion");
        // assertTrue(false);
        // }
        // catch (BusinessException e) {
        // log.debug(e.getMessage());
        // }
        // endTime = System.currentTimeMillis();
        // milisegundos = (endTime - startTime);
        // log.debug("Milisegundos en responder el servicio
        // ConsultaEstatusGarantias():" +
        // milisegundos);
        //
        // log.debug("Probando con el params con el mercado Dinero y el agente
        // firmado");
        params.setIdAgenteFirmado("01");
        params.setFolioAgenteFirmado("003");
        params.setPaginaVO(new PaginaVO());
        // startTime = System.currentTimeMillis();
        // PaginaVO paginaMovimientosMiscFiscalVO =
        // mercadoDineroService.consultaMovimientosMiscFiscal(params);
        // endTime = System.currentTimeMillis();
        // milisegundos = (endTime - startTime);
        // log.debug("Milisegundos en responder el servicio
        // ConsultaEstatusGarantias():" +
        // milisegundos);
        // assertNotNull(paginaMovimientosMiscFiscalVO);
        // log.debug("La paginaMovimientosMiscFiscalVO tiene: [" +
        // paginaMovimientosMiscFiscalVO.getTotalRegistros() + "] elementos");
        // assertTrue(paginaMovimientosMiscFiscalVO.getTotalRegistros().intValue()
        // > 0);
        // assertNotNull(paginaMovimientosMiscFiscalVO.getRegistros());
        // UtilsLog.logElementosLista(paginaMovimientosMiscFiscalVO.getRegistros(),
        // true);

        //
        log
                .debug("Probando con el params con el mercado Capital y el agente firmado");
        params.setMercado(Boolean.FALSE);
        PaginaVO paginaMovimientosMiscFiscalVO = mercadoDineroService
                .consultaMovimientosMiscFiscal(params);

        assertNotNull(paginaMovimientosMiscFiscalVO);
        log.debug("La paginaMovimientosMiscFiscalVO tiene: ["
                + paginaMovimientosMiscFiscalVO.getTotalRegistros()
                + "] elementos");
        assertTrue(paginaMovimientosMiscFiscalVO.getTotalRegistros().intValue() > 0);
        assertNotNull(paginaMovimientosMiscFiscalVO.getRegistros());
        log.debug("Resultado");
        UtilsLog.logElementosLista(
                paginaMovimientosMiscFiscalVO.getRegistros(), true);

        log
                .debug("Ejecucion exitosa del testEJBConsultaMovimientosMiscFiscal()");

    }

}