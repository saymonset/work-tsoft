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
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * Clase de prueba para el servicio de Garantias.
 *
 * @author Igor Mejia
 */
public class ITestArchivoConciliacionDetalle_e1 extends BaseITestService {

    /**
     * Servicio a probar.
     */
    private MercadoCapitalesService mercadoCapitales;

    /** Log de bitacora */
    Logger logger = LoggerFactory.getLogger(ITestArchivoConciliacionDetalle_e1.class);

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
   * Cuando todos los parametros llegan vacios
   */
  public void testArchivoConciliacionDetalle_e1() throws BusinessException {

      log.debug("Entrando al metodo testEJBArchivoConciliacionDetalle_e1()");

      assertNotNull(mercadoCapitales);

      log.debug("Probando el servicio cuando todos los parametros llegan vacios");

      try {
          AgenteVO agenteVO = new AgenteVO();
          EmisionVO emisionVO = new EmisionVO();
          agenteVO.setId("");
          agenteVO.setFolio("");
          agenteVO.setCuenta("");

          PaginaVO pagina = mercadoCapitales.archivoConciliacionDetalle(
                  agenteVO, emisionVO, null);
          assertNotNull(pagina);
          assertNotNull(pagina.getRegistros());
          assertFalse(pagina.getRegistros().isEmpty());
          for (Iterator iter = pagina.getRegistros().iterator(); iter
                  .hasNext();) {
              ArchivoConciliacionMovimientosVO element = (ArchivoConciliacionMovimientosVO) iter
                      .next();
              log
                      .debug("Registro: "
                              + ReflectionToStringBuilder
                                      .reflectionToString(element));
          }
          log.debug("Total de registros: " + pagina.getTotalRegistros());
      } catch (BusinessException be) {
          log.debug(be.getMessage());
      }

      log.debug("Saliendo del metodo testEJBArchivoConciliacionDetalle()");

  }

  /**
  *
  * @throws BusinessException
  * Cuando el id llega vacio
  */
 public void testArchivoConciliacionDetalle_e2() throws BusinessException {

     log.debug("Entrando al metodo testEJBArchivoConciliacionDetalle_e2()");

     assertNotNull(mercadoCapitales);

     log.debug("Probando el servicio cuando el id llega vacio o nulo");

     try {
         AgenteVO agenteVO = new AgenteVO();
         agenteVO.setId("");
         agenteVO.setFolio("003");
         agenteVO.setCuenta("0307");
         EmisionVO emisionVO = new EmisionVO();
         emisionVO.setIdTipoValor("1A");
         emisionVO.setEmisora("BP");
         emisionVO.setSerie("N");
         emisionVO.setCupon("0000");
         PaginaVO pagina = mercadoCapitales.archivoConciliacionDetalle(
                 agenteVO, emisionVO, null);
         assertNotNull(pagina);
         assertNotNull(pagina.getRegistros());
         assertFalse(pagina.getRegistros().isEmpty());
         for (Iterator iter = pagina.getRegistros().iterator(); iter
                 .hasNext();) {
             ArchivoConciliacionMovimientosVO element = (ArchivoConciliacionMovimientosVO) iter
                     .next();
             log
                     .debug("Registro: "
                             + ReflectionToStringBuilder
                                     .reflectionToString(element));
         }
         log.debug("Total de registros: " + pagina.getTotalRegistros());
     } catch (BusinessException be) {
         log.debug(be.getMessage());
     }

     log.debug("Saliendo del metodo testEJBArchivoConciliacionDetalle()");

 }
 /**
 *
 * @throws BusinessException
 * Cuando el folio llega vacio
 */
public void testArchivoConciliacionDetalle_e3() throws BusinessException {

    log.debug("Entrando al metodo testEJBArchivoConciliacionDetalle_e3()");

    assertNotNull(mercadoCapitales);

    log.debug("Probando el servicio cuando el folio llega vacio o nulo");

    try {
        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("");
        agenteVO.setCuenta("0307");
        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("1A");
        emisionVO.setEmisora("BP");
        emisionVO.setSerie("N");
        emisionVO.setCupon("0000");
        PaginaVO pagina = mercadoCapitales.archivoConciliacionDetalle(
                agenteVO, emisionVO, null);
        assertNotNull(pagina);
        assertNotNull(pagina.getRegistros());
        assertFalse(pagina.getRegistros().isEmpty());
        for (Iterator iter = pagina.getRegistros().iterator(); iter
                .hasNext();) {
            ArchivoConciliacionMovimientosVO element = (ArchivoConciliacionMovimientosVO) iter
                    .next();
            log
                    .debug("Registro: "
                            + ReflectionToStringBuilder
                                    .reflectionToString(element));
        }
        log.debug("Total de registros: " + pagina.getTotalRegistros());
    } catch (BusinessException be) {
        log.debug(be.getMessage());
    }

    log.debug("Saliendo del metodo testEJBArchivoConciliacionDetalle()");

}
/**
*
* @throws BusinessException
* Cuando todos los parametros son nulos
*/
public void testArchivoConciliacionDetalle_e4() throws BusinessException {

   log.debug("Entrando al metodo testEJBArchivoConciliacionDetalle_e4()");

   assertNotNull(mercadoCapitales);

   log.debug("Probando el servicio cuando todo los parametros son nulos");

   try {
       PaginaVO pagina = mercadoCapitales.archivoConciliacionDetalle(
               null, null, null);
       assertNotNull(pagina);
       assertNotNull(pagina.getRegistros());
       assertFalse(pagina.getRegistros().isEmpty());
       for (Iterator iter = pagina.getRegistros().iterator(); iter
               .hasNext();) {
           ArchivoConciliacionMovimientosVO element = (ArchivoConciliacionMovimientosVO) iter
                   .next();
           log
                   .debug("Registro: "
                           + ReflectionToStringBuilder
                                   .reflectionToString(element));
       }
       log.debug("Total de registros: " + pagina.getTotalRegistros());
   } catch (BusinessException be) {
       log.debug(be.getMessage());

   }

   log.debug("Saliendo del metodo testEJBArchivoConciliacionDetalle()");

}




}
