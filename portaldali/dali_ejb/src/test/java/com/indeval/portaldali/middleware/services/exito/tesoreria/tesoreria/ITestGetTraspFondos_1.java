/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.tesoreria.tesoreria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService;
import com.indeval.portaldali.persistence.util.UtilsLog;


/**
 * Clase de Prueba para los metodos de TesoreriaService
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestGetTraspFondos_1 extends BaseITestService {

    private LiquidacionDecretosService liquidacionDecretosService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private TesoreriaService tesoreriaService;
    
    
    /**
     * @see com.indeval.portaldali.middleware.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {

        super.onSetUp();

        if (tesoreriaService == null) {
            tesoreriaService = (TesoreriaService) applicationContext
                    .getBean("tesoreriaService");
        }
        if(liquidacionDecretosService==null){
        	liquidacionDecretosService= (LiquidacionDecretosService) applicationContext
            .getBean("decretosEjercicioDerechos");

        }

    }

    /**
     * TestCase para tesoreriaService.getTraspFondos() TODO - Depende de precondiciones.
     * Recupera registros del dia TestCase para el servicio de testEJBGetTraspFondos()
     *
     * @throws BusinessException
     */
    public void testEJBGetTraspFondos() throws BusinessException {

        log.debug("Entrando al metodo testEJBGetTraspFondos()");
        assertNotNull(tesoreriaService);

        log.debug("Probando con todo los parametros NULL");

        long startTime = System.currentTimeMillis();

        try {

            tesoreriaService.getTraspFondos(null, null, null);
            log.debug("Checar validacion");
            assertTrue(false);

        } catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetTraspFondos():" + milisegundos);

        log.debug("Probando solo con el Agente VACIO");

        AgenteVO agente = new AgenteVO();
        startTime = System.currentTimeMillis();

        try {

            tesoreriaService.getTraspFondos(agente, null, null);
            log.debug("Checar validacion");
            assertTrue(false);

        } catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetTraspFondos():" + milisegundos);

        log.debug("Probando solo con el Agente con id y folio");
        agente = new AgenteVO("01", "022");
        startTime = System.currentTimeMillis();

        try {

            tesoreriaService.getTraspFondos(agente, null, null);
            log.debug("Checar validacion");
            assertTrue(false);

        } catch (BusinessException e) {

            log.debug(e.getMessage());

        }

        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetTraspFondos():" + milisegundos);

        log.debug("Probando con el Agente y con el Tipo de Operacion (sin pagina)");

        String idTipoOperacion = TesoreriaService.DEPOSITOS_TODOS;
        startTime = System.currentTimeMillis();

        PaginaVO paginaVO = tesoreriaService.getTraspFondos(agente, idTipoOperacion, null);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetTraspFondos():" + milisegundos);
        assertNotNull(paginaVO);
        assertNotNull(paginaVO.getRegistros());
        assertFalse(paginaVO.getRegistros().isEmpty());
        UtilsLog.logElementosLista(paginaVO.getRegistros(), true);
        UtilsLog.logElementosLista(paginaVO.getRegistros(), false);

        log.debug("Probando con todos los parametros");
        paginaVO = new PaginaVO();
        paginaVO.setRegistrosXPag(new Integer(10));
        startTime = System.currentTimeMillis();
        paginaVO = tesoreriaService.getTraspFondos(agente, idTipoOperacion, paginaVO);
        endTime = System.currentTimeMillis();
        milisegundos = (endTime - startTime);
        log.debug("Milisegundos en responder el servicio GetTraspFondos():" + milisegundos);
        assertNotNull(paginaVO);
        assertNotNull(paginaVO.getRegistros());
        assertTrue(paginaVO.getRegistros().size() > 0);
        UtilsLog.logElementosLista(paginaVO.getRegistros(), true);
        UtilsLog.logElementosLista(paginaVO.getRegistros(), false);

        log.debug("Ejecucion exitosa de testEJBGetTraspFondos()");

    }

}
