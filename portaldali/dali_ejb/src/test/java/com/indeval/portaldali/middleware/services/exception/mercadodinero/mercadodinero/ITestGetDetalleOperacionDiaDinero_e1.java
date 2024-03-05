/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.TraspasoDineroParams;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;

/**
 * La clase ITestGetDetalleOperacionDiaDinero_e1 tiene los TestCase
 * que prueban los casos de excepcion del m&eacute;todo getDetalleOperacionDiaDinero()
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
public class ITestGetDetalleOperacionDiaDinero_e1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Log log = LogFactory
            .getLog(ITestGetDetalleOperacionDiaDinero_e1.class);

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
     * Prueba del servicio getDetalleOperacionDiaDinero
     * con el parametro nulo
     *
     * @throws Exception
     */
    public void testGetDetalleOperacionDiaDinero_e1() throws Exception {

        log.info("Entrando a " +
                "ITestGetDetalleOperacionDiaDinero_e1.testGetDetalleOperacionDiaDinero_e1()");
        assertNotNull(mercadoDineroService);
        
        try {
            mercadoDineroService.getDetalleOperacionDiaDinero(null);
            log.debug("Checar validaciones");
            assertTrue(false);
        }
        catch (BusinessException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
        
    }

    /**
     * Prueba del servicio getDetalleOperacionDiaDinero
     * con el parametro vacio
     *
     * @throws Exception
     */
    public void testGetDetalleOperacionDiaDinero_e2() throws Exception {

        log.info("Entrando a " +
                "ITestGetDetalleOperacionDiaDinero_e1.testGetDetalleOperacionDiaDinero_e1()");
        assertNotNull(mercadoDineroService);
        
        try {
            mercadoDineroService.getDetalleOperacionDiaDinero(new TraspasoDineroParams());
            log.debug("Checar validaciones");
            assertTrue(false);
        }
        catch (BusinessException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    /**
     * Prueba del servicio getDetalleOperacionDiaDinero
     * con el parametro solo con el id
     *
     * @throws Exception
     */
    public void testGetDetalleOperacionDiaDinero_e3() throws Exception {

        log.info("Entrando a " +
                "ITestGetDetalleOperacionDiaDinero_e1.testGetDetalleOperacionDiaDinero_e1()");
        assertNotNull(mercadoDineroService);
        
        TraspasoDineroParams td = new TraspasoDineroParams();
        td.setIdInst("02");
        
        try {
            mercadoDineroService.getDetalleOperacionDiaDinero(td);
            log.debug("Checar validaciones");
            assertTrue(false);
        }
        catch (BusinessException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    /**
     * Prueba del servicio getDetalleOperacionDiaDinero
     * con el parametro con el id y el folio 
     *
     * @throws Exception
     */
    public void testGetDetalleOperacionDiaDinero_e4() throws Exception {

        log.info("Entrando a " +
                "ITestGetDetalleOperacionDiaDinero_e1.testGetDetalleOperacionDiaDinero_e1()");
        assertNotNull(mercadoDineroService);
        
        TraspasoDineroParams td = new TraspasoDineroParams();
        td.setIdInst("02");
        td.setFolioInst("040");
        
        try {
            mercadoDineroService.getDetalleOperacionDiaDinero(td);
            log.debug("Checar validaciones");
            assertTrue(false);
        }
        catch (BusinessException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    /**
     * Prueba del servicio getDetalleOperacionDiaDinero
     * con el parametro con el id, folio y cuenta (sin la llaveFolio)
     *
     * @throws Exception
     */
    public void testGetDetalleOperacionDiaDinero_e5() throws Exception {

        log.info("Entrando a " +
                "ITestGetDetalleOperacionDiaDinero_e1.testGetDetalleOperacionDiaDinero_e1()");
        assertNotNull(mercadoDineroService);
        
        TraspasoDineroParams td = new TraspasoDineroParams();
        td.setIdInst("02");
        td.setFolioInst("040");
        td.setCuenta("9300");
        
        try {
            mercadoDineroService.getDetalleOperacionDiaDinero(td);
            log.debug("Checar validaciones");
            assertTrue(false);
        }
        catch (BusinessException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    /**
     * Prueba del servicio getDetalleOperacionDiaDinero
     * solo con la llaveFolio
     *
     * @throws Exception
     */
    public void testGetDetalleOperacionDiaDinero_e6() throws Exception {

        log.info("Entrando a " +
                "ITestGetDetalleOperacionDiaDinero_e1.testGetDetalleOperacionDiaDinero_e1()");
        assertNotNull(mercadoDineroService);
        
        TraspasoDineroParams td = new TraspasoDineroParams();
        td.setLlaveFolio("070626012772");
        
        try {
            mercadoDineroService.getDetalleOperacionDiaDinero(td);
            log.debug("Checar validaciones");
            assertTrue(false);
        }
        catch (BusinessException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
        
    }

    /**
     * Prueba del servicio getDetalleOperacionDiaDinero
     * solo con la fecha
     *
     * @throws Exception
     */
    public void testGetDetalleOperacionDiaDinero_e7() throws Exception {

        log.info("Entrando a " +
                "ITestGetDetalleOperacionDiaDinero_e1.testGetDetalleOperacionDiaDinero_e1()");
        assertNotNull(mercadoDineroService);
        
        TraspasoDineroParams td = new TraspasoDineroParams();

        Calendar c = Calendar.getInstance();
        c.set(2007, Calendar.JUNE, 26, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);

        td.setFechaLiquidacion(c.getTime());

        try {
            mercadoDineroService.getDetalleOperacionDiaDinero(td);
            log.debug("Checar validaciones");
            assertTrue(false);
        }
        catch (BusinessException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
}