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
import com.indeval.portaldali.middleware.services.mercadodinero.OperacionDiaDineroParams;
import com.indeval.portaldali.middleware.services.mercadodinero.OperacionDiaDineroVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;

/**
 * La clase ITestGetOperacionesDiaDinero_1 tiene los TestCase
 * que prueban los casos de exito del m&eacute;todo getOperacionesDiaDinero()
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
public class ITestGetOperacionesDiaDinero_1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Log log = LogFactory
            .getLog(ITestGetOperacionesDiaDinero_1.class);

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
     * solo con el agente
     *
     * @throws Exception
     */
    public void testGetOperacionesDiaDinero_1() throws Exception {
        
        log.info("Entrando a ITestGetOperacionesDiaDinero_1.testGetOperacionesDiaDinero_1()");
        
        assertNotNull(mercadoDineroService);

        OperacionDiaDineroParams operacionDiaDineroParams = new OperacionDiaDineroParams();

        AgenteVO agenteVO = new AgenteVO("01","003");
        
        operacionDiaDineroParams.setAgenteVO(agenteVO);
        
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
    
    /**
     * Prueba del servicio getOperacionesDiaDinero
     * con el parametro completo
     *
     * @throws Exception
     */
    public void testGetOperacionesDiaDinero_2() throws Exception {
        
        log.info("Entrando a ITestGetOperacionesDiaDinero_1.testGetOperacionesDiaDinero_2()");
        
        assertNotNull(mercadoDineroService);
        
        OperacionDiaDineroParams operacionDiaDineroParams = new OperacionDiaDineroParams();

        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("003");

        Calendar inicial = Calendar.getInstance();
        inicial.set(2007, Calendar.NOVEMBER, 8, 0, 0, 0);
        inicial.set(Calendar.MILLISECOND, 0);

        Calendar cFinal = Calendar.getInstance();
        cFinal.set(2007, Calendar.NOVEMBER, 8, 0, 0, 0);
        cFinal.set(Calendar.MILLISECOND, 0);

        String[] tipoOperacion = { "V", "R", "T", "X", "Y" };

        operacionDiaDineroParams.setAgenteVO(agenteVO);
        operacionDiaDineroParams.setFechaInicial(inicial.getTime());
        operacionDiaDineroParams.setFechaFinal(cFinal.getTime());
        operacionDiaDineroParams.setTipoOperacion(tipoOperacion);
        operacionDiaDineroParams.setManeraExtraccion("COMPRADOR");
        operacionDiaDineroParams.setEstatusOperacion("FIRME");

        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("BI");
        emisionVO.setSerie("070802");

        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(new Integer(0));
        paginaVO.setRegistrosXPag(new Integer(30));

        operacionDiaDineroParams.setEmisionVO(emisionVO);

        operacionDiaDineroParams.setPaginaVO(paginaVO);

        OperacionDiaDineroVO oper = (OperacionDiaDineroVO) mercadoDineroService
                .getOperacionesDiaDinero(operacionDiaDineroParams);

        assertNotNull(oper);
        assertNotNull(oper.getTotalMovimientos());
        assertTrue(oper.getTotalMovimientos().intValue() > 0);
        log.debug("El numero total de registros es: "
                + oper.getTotalMovimientos().intValue());

        for (int i = 0; i < oper.getPaginaVO().getRegistros().size(); i++) {
            log.debug("El registro "
                    + i
                    + " es: "
                    + ToStringBuilder.reflectionToString(oper.getPaginaVO()
                            .getRegistros().get(i)));
        }

        log.debug("El numero total de titulos es: "
                + oper.getTotalTitulos().intValue());
    }
    
}