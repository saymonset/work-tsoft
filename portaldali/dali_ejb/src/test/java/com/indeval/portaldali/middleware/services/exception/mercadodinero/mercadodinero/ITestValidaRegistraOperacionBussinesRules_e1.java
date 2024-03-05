/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams;
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
public class ITestValidaRegistraOperacionBussinesRules_e1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final LLogger logger = LoggerFactory.getLogger(TestValidaRegistraOperacionBussinesRules_e1.class);

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
     * TestCase para BusinessRulesMercadoDineroServiceImpl
     * .validaRegistraOperacionBussinesRules()
     * Valida solo que los parametros a registrar sean validos.
     *
     * @throws BusinessException
     */
    public void testValidaRegistraOperacionBussinesRules() throws BusinessException {
        
        log.info("Entrando a ITestValidaRegistraOperacionBussinesRules_1." +
                "testValidaRegistraOperacionBussinesRules()");

        assertNotNull(mercadoDineroService);

        RegistraOperacionParams params = new RegistraOperacionParams();

        EmisionVO emision = new EmisionVO();
        emision.setIdTipoValor("BI");
        emision.setEmisora("Y");
        emision.setSerie("Y");
        emision.setCupon("Y");
        params.setEmision(emision);

        AgenteVO traspasante = new AgenteVO();
        traspasante.setId("01");
        traspasante.setFolio("003");
        traspasante.setCuenta("0315");
        params.setTraspasante(traspasante);

        AgenteVO receptor = new AgenteVO();
        receptor.setId("02");
        receptor.setFolio("061");
        receptor.setCuenta("0001");
        params.setReceptor(receptor);

        params.setCantidadOperada(new BigDecimal(1000.00));
        params.setDiasVigentes(new Integer("0"));

        Calendar fechaConcertacion = new GregorianCalendar();
        fechaConcertacion.set(Calendar.DAY_OF_MONTH, 9);
        fechaConcertacion.set(Calendar.MONTH, Calendar.AUGUST);
        fechaConcertacion.set(Calendar.YEAR, 2007);
        params.setFechaConcertacion(fechaConcertacion.getTime());
        params.setIdTasaReferencia("28");
        params.setImporte(new BigDecimal("0"));
        params.setPrecioTitulo(new BigDecimal("0"));
        params.setTasaPremio(new BigDecimal("0"));
        params.setDescripcion("Hola Mundo");
        params.setClaveReporto("V");
        params.setRol("1");

        // --
        params.setBajaLogica("");
        params.setDiasPlazo(new Integer(0));
        params.setDivisa("");
        params.setFechaLiq(new Date());
        params.setFechaRegreso(new Date());
        params.setFechaReporto(new Date());
        params.setIdValorEn("");
        params.setIsin("");
        params.setLiq(new Integer(0));
        params.setNetoEfectivo(new BigDecimal(0));
        params.setPlazoLiquidacion(new Integer(0));
        params.setPlazoReporto(new Integer(0));
        params.setPosicionActual(new Integer(0));
        params.setPrecioVector(new BigDecimal(0));
        params.setPremio(new BigDecimal(0));
        params.setSaldoDisponible(new Integer(0));
        params.setSimulado(new BigInteger("0"));
        params.setSociedad("");
        // --

        long startTime = System.currentTimeMillis();
        String folio = mercadoDineroService.validaRegistraOperacionBusinessRules(params);
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio ValidaRegistraOperacionBussinesRules():"
                + milisegundos);
        assertNotNull(folio);
        log.debug("Folio Operacion: [" + folio + "]");

        log.debug("Ejecucion exitosa del testEJBValidaRegistraOperacionBussinesRules()");

    }

}