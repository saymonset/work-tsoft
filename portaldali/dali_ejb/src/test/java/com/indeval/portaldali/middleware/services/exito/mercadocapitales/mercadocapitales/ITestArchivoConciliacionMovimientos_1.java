/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadocapitales.mercadocapitales;


import java.util.Iterator;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadocapitales.ArchivoConciliacionMovimientosVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * Clase de prueba para el servicio de Garantias.
 *
 * @author Igor Mejia
 */
public class ITestArchivoConciliacionMovimientos_1 extends BaseITestService {

    /**
     * Servicio a probar.
     */
    private MercadoCapitalesService mercadoCapitales;

    /** Log de bitacora */
    Logger logger = LoggerFactory.getLogger(ITestArchivoConciliacionMovimientos_1.class);

    /**
     * Inicializa lo que la prueba necesita para su ejecucion.
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (mercadoCapitales == null) {
            mercadoCapitales = (MercadoCapitalesService) applicationContext
                    .getBean("mercadoCapitalesService");
        }
    }


    /**
    *
    * @throws BusinessException
    * Con todos los parametros llenos
    */
   public void testArchivoConciliacionMovimientos_1()
           throws BusinessException {

       log.debug("Entrando al metodo testEJBArchivoConciliacionMovimientos_1()");

       assertNotNull(mercadoCapitales);

       log.debug("Probando el servicio con id, cuenta, folio");

       try {
           AgenteVO agenteVO = new AgenteVO();
           agenteVO.setId("01");
           agenteVO.setFolio("003");
           agenteVO.setCuenta("0307");
           PaginaVO pagina = mercadoCapitales.archivoConciliacionMovimientos(
                   agenteVO, null);
           assertNotNull(pagina);
           assertNotNull(pagina.getRegistros());
           assertFalse(pagina.getRegistros().isEmpty());
           for (Iterator iter = pagina.getRegistros().iterator(); iter
                   .hasNext();) {
               ArchivoConciliacionMovimientosVO element = (ArchivoConciliacionMovimientosVO) iter
                       .next();
               log.debug("Registro: "
                               + ReflectionToStringBuilder
                                       .reflectionToString(element));
           }
           log.debug("Total de registros: " + pagina.getTotalRegistros());
       } catch (BusinessException be) {
           log.debug(be.getMessage());
           System.out.println(be.getMessage());

       }

       log
               .debug("Saliendo del metodo testEJBArchivoConciliacionMovimientos()");

   }





}
