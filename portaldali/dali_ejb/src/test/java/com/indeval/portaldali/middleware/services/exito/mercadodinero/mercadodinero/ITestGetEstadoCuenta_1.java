/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.ElementoEstadoCuentaVO;
import com.indeval.portaldali.middleware.services.mercadodinero.EstadoCuentaTotalMDVO;
import com.indeval.portaldali.middleware.services.mercadodinero.GetEstadoCuentaParams;
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
public class ITestGetEstadoCuenta_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetEstadoCuenta_1.class);

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
     * TestCase para el servicio de getEstadoCuenta()
     *
     * @throws BusinessException
     */
    public void testGetEstadoCuenta() throws BusinessException {
        
        log.info("Entrando a ITestGetEstadoCuenta_1.testGetEstadoCuenta()");

        assertNotNull(mercadoDineroService);

        log.debug("Probando con el params NULL");
        GetEstadoCuentaParams params = null;
        long startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.getEstadoCuenta(params);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetEstadoCuenta():"
                + milisegundos);

        log.debug("Probando con el params VACIO");
        params = new GetEstadoCuentaParams();
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.getEstadoCuenta(params);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetEstadoCuenta():"
                + milisegundos);

        log.debug("Probando con el Agente VACIO");
        params.setAgente(new AgenteVO());
        startTime = System.currentTimeMillis();
        try {
            mercadoDineroService.getEstadoCuenta(params);
            log.debug("Checar validacion");
            assertTrue(false);
        } catch (BusinessException e) {
            log.debug(e.getMessage());
        }
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetEstadoCuenta():"
                + milisegundos);

        GregorianCalendar fechaIni = new GregorianCalendar(0, 0, 0);
        fechaIni.set(2007, 0, 16);
        params.setFechaLiquidacion(fechaIni.getTime()); // Obligatorio
        log.debug(params.getFechaLiquidacion());
        log.debug("Probando solo con el Agente (sin cuenta)");
        params.setAgente(new AgenteVO("02", "013", ""));
        log.debug(params.getAgente().getId() + params.getAgente().getFolio());
        // params.setRol(MercadoDineroService.AMBOS_INIC); //Opcional (si no se
        // envia se asume ambos)
        startTime = System.currentTimeMillis();
        EstadoCuentaTotalMDVO resultado = mercadoDineroService
                .getEstadoCuenta(params);
        log.debug(resultado.getTotalSaldoInicial());
        log.debug(resultado.getTotalEntrada());
        log.debug(resultado.getTotalSalida());
        log.debug(resultado.getTotalSaldoFinal());
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetEstadoCuenta():"
                + milisegundos);
        assertNotNull(resultado);
        // log.debug("El arreglo de elementoEstadoCuentaVO tiene [" +
        // resultado.length + "] elementos" );
        // assertTrue(resultado.length > 0);
        // UtilsLog.logElementosArreglo(resultado, true);
        // UtilsLog.logElementosArreglo(resultado, false);
        params.setAgente(new AgenteVO("02", "027"));
        log.debug("Probando solo con el Agente (con cuenta)");
        params.setAgente(new AgenteVO("02", "027", "7921"));
        startTime = System.currentTimeMillis();
        resultado = mercadoDineroService.getEstadoCuenta(params);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetEstadoCuenta():"
                + milisegundos);
        assertNotNull(resultado);
        // log.debug("El arreglo de elementoEstadoCuentaVO tiene [" +
        // resultado.length + "] elementos" );
        // assertTrue(resultado.length > 0);
        // UtilsLog.logElementosArreglo(resultado, true);
        // UtilsLog.logElementosArreglo(resultado, false);

        log.debug("Probando con el Agente y la emision");
        EmisionVO emision = new EmisionVO();
        emision.setIdTipoValor("LD");
        emision.setEmisora("GOBFED");
        emision.setSerie("090806");
        emision.setCupon("0000");
        params.setClaveValor(emision); // Opcional
        startTime = System.currentTimeMillis();
        resultado = mercadoDineroService.getEstadoCuenta(params);
        endTime = System.currentTimeMillis();

        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetEstadoCuenta():"
                + milisegundos);
        assertNotNull(resultado);
        // log.debug("El arreglo de elementoEstadoCuentaVO tiene [" +
        // resultado.length + "] elementos" );
        // assertTrue(resultado.length > 0);
        // UtilsLog.logElementosArreglo(resultado, true);
        // UtilsLog.logElementosArreglo(resultado, false);
        ElementoEstadoCuentaVO[] registers = resultado
                .getElementoEstadoCuentaVO();
        for (int i = 0; i < registers.length; i++) {
            log.debug(registers[i].getSaldoInicial() + ", "
                    + registers[i].getSalidas() + ", "
                    + registers[i].getEntradas() + ", "
                    + registers[i].getSaldoFinal());
        }
        log.debug(resultado.getTotalSaldoInicial());
        log.debug(resultado.getTotalEntrada());
        log.debug(resultado.getTotalSalida());
        log.debug(resultado.getTotalSaldoFinal());
        params.setContraparte(new AgenteVO("02", "022", "8322")); // Opcional

        // String[] tipoOperacion = {"T", "R", "L", "P", "X"}; //Opcional
        // String[] tipoOperacion = {"T"};
        // String[] tipoOperacion = {"X"};

        // params.setTipoOperacion(tipoOperacion);

        startTime = System.currentTimeMillis();
        resultado = mercadoDineroService.getEstadoCuenta(params);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetEstadoCuenta():"
                + milisegundos);
        assertNotNull(resultado);
        // log.debug("El arreglo de elementoEstadoCuentaVO tiene [" +
        // resultado.length + "] elementos" );
        // assertTrue(resultado.length > 0);
        // UtilsLog.logElementosArreglo(resultado, true);
        // UtilsLog.logElementosArreglo(resultado, false);

        log.debug("Ejecucion exitosa del testEJBGetEstadoCuenta()");

    }

}