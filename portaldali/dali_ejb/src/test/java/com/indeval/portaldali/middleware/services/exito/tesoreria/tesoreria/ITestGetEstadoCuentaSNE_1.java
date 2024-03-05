/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.tesoreria.tesoreria;

import java.util.Calendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.tesoreria.EstadoCuentaSNEVO;
import com.indeval.portaldali.middleware.services.tesoreria.GetEdoCtaSNEParams;
import com.indeval.portaldali.middleware.services.tesoreria.RegEstadoCuentaSNEVO;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService;
import com.indeval.portaldali.persistence.util.UtilsLog;


/**
 * Clase de Prueba para los metodos de TesoreriaService
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestGetEstadoCuentaSNE_1 extends BaseITestService {

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
     * TestCase para el servicio de getEstadoCuentaSNE()
     *
     * @throws BusinessException
     */
    public void testEJBGetEstadoCuentaSNE() throws BusinessException {

        log.debug("Entrando al metodo testEJBGetEstadoCuentaSNE()");
        assertNotNull(tesoreriaService);

        log.debug("Probando con el params NULL");

        GetEdoCtaSNEParams getEdoCtaSNEparams = new GetEdoCtaSNEParams();

        log.debug("Probando con el params correcto");

        // Se construye la pagina
        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(PaginaVO.TODOS);
        paginaVO.setRegistrosXPag(new Integer(30));

        Calendar calendar = Calendar.getInstance();
        calendar.set(2007, Calendar.NOVEMBER, 30);

        // Se settean los atributos del objeto params
        getEdoCtaSNEparams.setFechaOperacion(calendar.getTime());
        getEdoCtaSNEparams.setAgrupacion(TesoreriaService.CRONOLOGICA);
        getEdoCtaSNEparams.setAgente(new AgenteVO("02", "061", "5323"));
        // AgenteVO contraparte = new AgenteVO();
        // contraparte.setId("02");
        // contraparte.setFolio("013");
        // contraparte.setCuenta("6923");
        // getEdoCtaSNEparams.setContraparte(contraparte);

        getEdoCtaSNEparams.setPagina(paginaVO);

        EmisionVO emisionVO = new EmisionVO();
//        emisionVO.setIdTipoValor("54");
//        emisionVO.setEmisora("APRINB1");
//        emisionVO.setSerie("1");
//        emisionVO.setCupon("0001");
        getEdoCtaSNEparams.setEmision(emisionVO);

        //getEdoCtaSNEparams.setIdCuentasEfectivo("CC");
        
        //long startTime = System.currentTimeMillis();
        EstadoCuentaSNEVO estadoCuentaSNEVO = tesoreriaService
                .getEstadoCuentaSNE(getEdoCtaSNEparams);
        //long endTime = System.currentTimeMillis();
//        double milisegundos = (endTime - startTime);
//        log.debug("Milisegundos en responder el servicio GetEstadoCuentaSNE():"
//                + milisegundos);

        // Se verifica e imprime el objeto retornado por el servicio
        assertNotNull(estadoCuentaSNEVO);
        System.out.println("Imprimiendo el objeto estadoCuentaSNEVO en el TestCase");
        System.out.println("[" + ToStringBuilder.reflectionToString(estadoCuentaSNEVO)
                + "]");

        // Se verifica e imprime la pagina
        assertNotNull(estadoCuentaSNEVO.getPagina());
        System.out.println("Imprimiendo la pagina contenida en el objeto estadoCuentaSNEVO en el TestCase");
        System.out.println("["
                + ToStringBuilder.reflectionToString(estadoCuentaSNEVO
                        .getPagina()) + "]");

        // Se verifica e imprime la lista contenida en la pagina
        assertNotNull(estadoCuentaSNEVO.getPagina().getRegistros());
        assertTrue(estadoCuentaSNEVO.getPagina().getRegistros().size() > 0);
        assertTrue(estadoCuentaSNEVO.getPagina().getRegistros().get(0)
                .getClass().isInstance(new RegEstadoCuentaSNEVO()));

        UtilsLog
                .logElementosLista(estadoCuentaSNEVO.getPagina().getRegistros());

        System.out.println("Ejecucion exitosa de testEJBGetEstadoCuentaSNE()");

    }

}
