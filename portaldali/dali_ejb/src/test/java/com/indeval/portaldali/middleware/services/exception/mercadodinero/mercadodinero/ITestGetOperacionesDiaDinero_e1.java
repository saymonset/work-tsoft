/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.OperacionDiaDineroParams;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;

/**
 * La clase ITestGetOperacionesDiaDinero_e1 tiene los TestCase
 * que prueban los casos de excepcion del m&eacute;todo getOperacionesDiaDinero()
 * de la clase MercadoDineroService
 *
 * @author Agustin Calderon Orduña.
 * @author Claudia Becerril Rejon.
 * @author Erick Navarrete Sevilla.
 * @author Domingo Suarez Torres.
 * @author Jose Guadalupe Aviles.
 * @author Ricardo Caballero.
 * @author Sergio Mena Zamora.
 * @author Salvador Valeriano Lucas.
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestGetOperacionesDiaDinero_e1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Log log = LogFactory
            .getLog(ITestGetOperacionesDiaDinero_e1.class);

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
            mercadoDineroService = 
                (MercadoDineroService) applicationContext.getBean("mercadoDineroService");
        }
        if (dateUtilsDao == null) {
            dateUtilsDao = (DateUtilsDao) applicationContext.getBean("dateUtilsDao");
        }
    }

    /**
     * Prueba del servicio getOperacionesDiaDinero
     * con el params nulo
     *
     * @throws Exception
     */
    public void testGetOperacionesDiaDinero_e1() throws Exception {
        
        log.info("Entrando a ITestGetOperacionesDiaDinero_e1.testGetOperacionesDiaDinero_e1()");
        assertNotNull(mercadoDineroService);

        try {
            mercadoDineroService.getOperacionesDiaDinero(null);
            log.debug("Checar validaciones");
            assertTrue(false);
        }
        catch (BusinessException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        }

    }
    
    /**
     * Prueba del servicio getOperacionesDiaDinero
     * con el params vacio
     * 
     * @throws Exception
     */
    public void testGetOperacionesDiaDinero_e2() throws Exception {
        
        log.info("Entrando a ITestGetOperacionesDiaDinero_e1.testGetOperacionesDiaDinero_e2()");
        assertNotNull(mercadoDineroService);

        try {
            mercadoDineroService.getOperacionesDiaDinero(new OperacionDiaDineroParams());
            log.debug("Checar validaciones");
            assertTrue(false);
        }
        catch (BusinessException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        }

    }
    
}