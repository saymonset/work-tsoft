/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

import java.math.BigDecimal;
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
public class ITestColocacionPrimariaRecompra_e1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestColocacionPrimariaRecompra_e1.class);

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
     * TestCase para mercadoDinero.ColocacionPrimariaRecompra() Prueba para
     * Recepcion Traspasos de Fondos Service
     *
     * @throws BusinessException
     */
    public void testColocacionPrimariaRecompra() throws BusinessException {
        
        log.info("Entrando a ITestColocacionPrimariaRecompra_1.testColocacionPrimariaRecompra()");

        assertNotNull(mercadoDineroService);

        RegistraOperacionParams params = new RegistraOperacionParams();

        EmisionVO emision = new EmisionVO();
        emision.setIdTipoValor("BI");
        emision.setEmisora("Y");
        emision.setSerie("Y");
        emision.setCupon("Y");
        params.setEmision(emision);

        AgenteVO traspasante = new AgenteVO();
        traspasante.setId("02");
        traspasante.setFolio("013");
        traspasante.setCuenta("5000");
        params.setTraspasante(traspasante);

        AgenteVO receptor = new AgenteVO();
        receptor.setId("01");
        receptor.setFolio("014");
        receptor.setCuenta("5001");
        params.setReceptor(receptor);

        params.setCantidadOperada(new BigDecimal(1000.00));
        params.setDiasVigentes(new Integer("0"));

        Calendar fechaConcertacion = new GregorianCalendar();
        fechaConcertacion.set(Calendar.DAY_OF_MONTH, 17);
        fechaConcertacion.set(Calendar.MONTH, 0);
        fechaConcertacion.set(Calendar.YEAR, 2007);
        params.setFechaConcertacion(fechaConcertacion.getTime());
        params.setIdTasaReferencia("28");
        params.setImporte(new BigDecimal("0"));
        params.setPrecioTitulo(new BigDecimal("0"));
        params.setTasaPremio(new BigDecimal("0"));
        params.setDescripcion("Chavamon");
        // probando el tipo de operacion colocacion primaria
        params.setClaveReporto("J");
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
        // params.setSimulado(new Integer(0));
        params.setSociedad("");
        // --

        String folio = mercadoDineroService
                .getColocacionPrimariaRecompra(params);
        assertNotNull(folio);
        log.debug("Folio Operacion: [" + folio + "]");

    }

}