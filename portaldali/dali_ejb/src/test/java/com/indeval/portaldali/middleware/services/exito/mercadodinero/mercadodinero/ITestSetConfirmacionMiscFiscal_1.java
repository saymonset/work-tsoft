/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.util.UtilsLog;


/**
 * La clase ITestMercadoDineroService tiene como objetivo probar los m&eacute;todos creados en
 * la clase MercadoDineroService
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
public class ITestSetConfirmacionMiscFiscal_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestSetConfirmacionMiscFiscal_1.class);

    /** Inyecci&oacute;n del bean mercadoDineroService */
    private MercadoDineroService mercadoDineroService;

    /** Bean de acceso a DateUtilsDao */
    private DateUtilsDao dateUtilsDao;

    /**
     * En el m&eacute;todo onSetUp inicializamos los beans que utilizaremos en la clase de
     * prueba.
     *
     * @throws Exception
     */
    protected void onSetUp() throws Exception {

        super.onSetUp();

        if (mercadoDineroService == null) {

            mercadoDineroService = (MercadoDineroService) applicationContext.getBean("mercadoDineroService");

        }

        if (dateUtilsDao == null) {

            dateUtilsDao = (DateUtilsDao) applicationContext.getBean("dateUtilsDao");

        }

    }

    /**
     * Prueba del servicio SetConfirmacionMiscFiscal
     *
     * @throws Exception
     */
    public void testSetConfirmacionMiscFiscal() throws Exception {

        log.info("Entrando a ITestSetConfirmacionMiscFiscal_1.testSetConfirmacionMiscFiscal()");

        assertNotNull(mercadoDineroService);

        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("001");

        Integer[] folios = { new Integer(3381), new Integer(3382)
                            };
        Integer[] foliosConfirmados = mercadoDineroService.setConfirmacionMiscFiscal(agenteVO,
                                                                                     folios);

        UtilsLog.logElementosArreglo(foliosConfirmados, true);

    }

}
