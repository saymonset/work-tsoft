/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.PrecioVectorValpreEVO;
import com.indeval.portaldali.middleware.services.mercadodinero.RegistroPrecioVectorValpreEVO;
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
public class ITestGetPrecioVectorValpreE_e1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetPrecioVectorValpreE_e1.class);

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
     * TestCase para getPrecioVectorValpreE()
     *
     * @throws BusinessException
     */
    public void testGetPrecioVectorValpreE() throws BusinessException {
        
        log.info("Entrando a ITestGetPrecioVectorValpreE_1.testGetPrecioVectorValpreE()");

        assertNotNull(mercadoDineroService);
        
        String tv = "M7";
        List precioVectorValpreEVOs = mercadoDineroService
                .getPrecioVectorValpreE(tv);
        assertNotNull(precioVectorValpreEVOs);
        assertFalse(precioVectorValpreEVOs.isEmpty());

        log.debug("RESULTADO: ");
        for (int i = 0; i < precioVectorValpreEVOs.size(); i++) {
            for (int j = 0; j < ((PrecioVectorValpreEVO) precioVectorValpreEVOs
                    .get(i)).getRegistros().size(); j++) {
                log
                        .debug("tv: "
                                + ReflectionToStringBuilder
                                        .reflectionToString(((RegistroPrecioVectorValpreEVO) ((PrecioVectorValpreEVO) precioVectorValpreEVOs
                                                .get(i)).getRegistros().get(j))
                                                .getEmision())
                                + "precio: "
                                + ((RegistroPrecioVectorValpreEVO) ((PrecioVectorValpreEVO) precioVectorValpreEVOs
                                        .get(i)).getRegistros().get(j))
                                        .getPrecioVector());
            }
            log.debug("TotalxTV= "
                    + ((PrecioVectorValpreEVO) precioVectorValpreEVOs.get(i))
                            .getRegistros().size());
            log.debug("SumaPrecio= "
                    + ((PrecioVectorValpreEVO) precioVectorValpreEVOs.get(i))
                            .getTotalPrecioVector());
        }

        log.debug("Saliendo de testGetPrecioVectorValpreE()");
    }

}