/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.PosturaPrestamistaVO;
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
public class ITestGetPosturaPrestamista_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestGetPosturaPrestamista_1.class);

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
     * Test para getPosturaPrestamista - Valpre-e
     *
     * @throws BusinessException
     */
    public void testGetPosturaPrestamista() throws BusinessException {

        log.info("Entrando a ITestGetPosturaPrestamista_1.testGetPosturaPrestamista()");

        assertNotNull(mercadoDineroService);

        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("02");
        agenteVO.setFolio("013");

        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("BI");
        emisionVO.setEmisora("GOBFED");
        emisionVO.setSerie("070614");
        emisionVO.setCupon("0000");

        PosturaPrestamistaVO posturaPrestamistaVO = mercadoDineroService
                .getPosturaPrestamista(agenteVO, emisionVO);

        assertNotNull(posturaPrestamistaVO);

        log.debug("Totales: "
                + ReflectionToStringBuilder
                        .reflectionToString(posturaPrestamistaVO));

        log.debug("Saliendo de testGetPosturaPrestamista()");

    }

}