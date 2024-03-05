/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.CapturaGarantiasParams;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
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
public class ITestCapturaGarantias_e1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestCapturaGarantias_e1.class);

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
     * TestCase para MercadoDineroDao.capturaGarantias()
     *
     * @throws BusinessException
     */
    public void testCapturaGarantias() throws BusinessException {

        log.info("Entrando a ITestCapturaGarantias_1.testCapturaGarantias()");
        
        assertNotNull(mercadoDineroService);

        CapturaGarantiasParams params = new CapturaGarantiasParams();
        params.setCantidadOperada(new BigDecimal(10));
        params.setDescripcion("XXX");
        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("M");
        emisionVO.setEmisora("GOBFED");
        emisionVO.setSerie("361120");
        emisionVO.setCupon("0000");
        params.setEmision(emisionVO);
        params.setPosicionActual(new BigDecimal(10));
        params.setPrecioVector(new BigDecimal(10));
        params.setReceptor(new AgenteVO("01", "001", "0109"));
        params.setSaldoDisponible(new BigDecimal(10));
        params.setTraspasante(new AgenteVO("01", "001", "0109"));

        long startTime = System.currentTimeMillis();
        String folio = mercadoDineroService.capturaGarantias(params);
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio CapturaGarantias():"
                + milisegundos);

        assertNotNull(folio);
        log.debug("Folio Operacion: [" + folio + "]");

        log.debug("Ejecucion exitosa del testEJBCapturaGarantias()");

    }

}