/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadodinero.mercadodinero;

import java.util.Calendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.OperacionDiaDetalleDineroVO;
import com.indeval.portaldali.middleware.services.mercadodinero.TraspasoDineroParams;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;

/**
 * La clase ITestGetDetalleOperacionDiaDinero_1 tiene los TestCase
 * que prueban los casos de exito del m&eacute;todo getDetalleOperacionDiaDinero()
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
public class ITestGetDetalleOperacionDiaDinero_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Log log = LogFactory
            .getLog(ITestGetDetalleOperacionDiaDinero_1.class);

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
     *
     * @throws Exception
     */
    public void testGetDetalleOperacionDiaDinero_1() throws Exception {

        log.info("Entrando a " +
                "ITestGetDetalleOperacionDiaDinero_1.testGetDetalleOperacionDiaDinero_1()");
        
        assertNotNull(mercadoDineroService);
        
        TraspasoDineroParams td = new TraspasoDineroParams();

        td.setIdInst("02");
        td.setFolioInst("040");
        td.setCuenta("9300");
        td.setLlaveFolio("070626012772");

        Calendar c = Calendar.getInstance();
        c.set(2007, Calendar.JUNE, 26, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);

        td.setFechaLiquidacion(c.getTime());

        OperacionDiaDetalleDineroVO odddVO = mercadoDineroService
                .getDetalleOperacionDiaDinero(td);

        assertNotNull(odddVO);

        log.debug("Resultado : " + ToStringBuilder.reflectionToString(odddVO));
    }
    
}