/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadocapitales.mercadocapitales;


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
public class ITestArchivoConciliacionMovimientos_e1 extends BaseITestService {

    /**
     * Servicio a probar.
     */
    private MercadoCapitalesService mercadoCapitales;

    /** Log de bitacora */
    Logger logger = LoggerFactory.getLogger(ITestArchivoConciliacionMovimientos_e1.class);

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
    * Con todos los parametros llegan vacios
    */
   public void testArchivoConciliacionMovimientos_e1()
           throws BusinessException {

       log.debug("Entrando al metodo testEJBArchivoConciliacionMovimientos_e1()");

       assertNotNull(mercadoCapitales);

       log.debug("Probando el servicio cuando todos los parametros llegan vacios");

       try {
           AgenteVO agenteVO = new AgenteVO();
           agenteVO.setId("");
           agenteVO.setFolio("");
           agenteVO.setCuenta("");
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

       }

       log
               .debug("Saliendo del metodo testEJBArchivoConciliacionMovimientos()");

   }

   /**
   *
   * @throws BusinessException
   * Cuando el id llega nulo o vacio
   */
  public void testArchivoConciliacionMovimientos_e2()
          throws BusinessException {

      log.debug("Entrando al metodo testEJBArchivoConciliacionMovimientos_e2()");

      assertNotNull(mercadoCapitales);

      log.debug("Probando el servicio cuando el id llega vacio o nulo");

      try {
          AgenteVO agenteVO = new AgenteVO();
          agenteVO.setId("");
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

      }

      log
              .debug("Saliendo del metodo testEJBArchivoConciliacionMovimientos()");

  }
  /**
  *
  * @throws BusinessException
  * Cuando el folio llega vacio
  */
 public void testArchivoConciliacionMovimientos_e3()
         throws BusinessException {

     log.debug("Entrando al metodo testEJBArchivoConciliacionMovimientos_e3()");

     assertNotNull(mercadoCapitales);

     log.debug("Probando el servicio cuando el folio llega vacio");

     try {
         AgenteVO agenteVO = new AgenteVO();
         agenteVO.setId("01");
         agenteVO.setFolio("");
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

     }

     log
             .debug("Saliendo del metodo testEJBArchivoConciliacionMovimientos()");

 }
 /**
 *
 * @throws BusinessException
 * Con todos los parametros llegan nulos
 */
public void testArchivoConciliacionMovimientos_e4()
        throws BusinessException {

    log.debug("Entrando al metodo testEJBArchivoConciliacionMovimientos_e4()");

    assertNotNull(mercadoCapitales);

    log.debug("Probando el servicio cuando todos los parametros llegan nulos");

    try {
        PaginaVO pagina = mercadoCapitales.archivoConciliacionMovimientos(
                null, null);
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

    }

    log
            .debug("Saliendo del metodo testEJBArchivoConciliacionMovimientos()");

}




}
