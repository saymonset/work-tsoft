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
import com.indeval.portaldali.middleware.services.mercadocapitales.ArchivoConciliacionVO;
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
public class ITestMercadoCapitalesService_e1 extends BaseITestService {

    /**
     * Servicio a probar.
     */
    private MercadoCapitalesService mercadoCapitales;

    /** Log de bitacora */
    Logger logger = LoggerFactory.getLogger(ITestMercadoCapitalesService_e1.class);

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
     * Prueba el archivo de conciliacion para un agente firmado (id, folio,
     * cuenta)
     * Con todos los parametros son vacios
     * @throws BusinessException
     */
    public void testArchivoConciliacion_e1() throws BusinessException {

        log.debug("Entrando al metodo testEJBArchivoConciliacion()");

        assertNotNull(mercadoCapitales);

        log.debug("Probando con agente - id, folio, cuenta");

        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("");
        agenteVO.setFolio("");
        PaginaVO paginaVO = new PaginaVO();
        EmisionVO emisionVO = new EmisionVO();

        PaginaVO pagina = mercadoCapitales.archivoConciliacion(agenteVO,
        		emisionVO, paginaVO);
        assertNotNull(pagina);
        assertNotNull(pagina.getRegistros());
        assertFalse(pagina.getRegistros().isEmpty());

        for (Iterator iter = pagina.getRegistros().iterator(); iter
        .hasNext();) {
        	ArchivoConciliacionVO element = (ArchivoConciliacionVO) iter
        	.next();
        	log
        	.debug("Registro: "
        			+ ReflectionToStringBuilder
        			.reflectionToString(element));
        }
        log.debug("Total de registros: " + pagina.getTotalRegistros());

        log.debug("Saliendo del metodo testEJBArchivoConciliacion()");

    }

    /**
     * Prueba el archivo de conciliacion para un agente firmado (id, folio,
     * cuenta)
     * Con todos el id viene nulo o vacio
     * @throws BusinessException
     */
    public void testArchivoConciliacion_e2() throws BusinessException {

        log.debug("Entrando al metodo testEJBArchivoConciliacion()");

        assertNotNull(mercadoCapitales);

        log.debug("Probando con agente - id, folio, cuenta");

        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("");
        agenteVO.setFolio("003");
        PaginaVO paginaVO = new PaginaVO();
        EmisionVO emisionVO = new EmisionVO();

        PaginaVO pagina = mercadoCapitales.archivoConciliacion(agenteVO,
        		emisionVO, paginaVO);
        assertNotNull(pagina);
        assertNotNull(pagina.getRegistros());
        assertFalse(pagina.getRegistros().isEmpty());

        for (Iterator iter = pagina.getRegistros().iterator(); iter
        .hasNext();) {
        	ArchivoConciliacionVO element = (ArchivoConciliacionVO) iter
        	.next();
        	log
        	.debug("Registro: "
        			+ ReflectionToStringBuilder
        			.reflectionToString(element));
        }
        log.debug("Total de registros: " + pagina.getTotalRegistros());

        log.debug("Saliendo del metodo testEJBArchivoConciliacion()");

    }

    /**
     * Prueba el archivo de conciliacion para un agente firmado (id, folio,
     * cuenta)
     * Cuando el folio viene nulo o vacio
     * @throws BusinessException
     */
    public void testArchivoConciliacion_e3() throws BusinessException {

        log.debug("Entrando al metodo testEJBArchivoConciliacion()");

        assertNotNull(mercadoCapitales);

        log.debug("Probando con agente - id, folio, cuenta");

        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("");
        PaginaVO paginaVO = new PaginaVO();
        EmisionVO emisionVO = new EmisionVO();

        PaginaVO pagina = mercadoCapitales.archivoConciliacion(agenteVO,
        		emisionVO, paginaVO);
        assertNotNull(pagina);
        assertNotNull(pagina.getRegistros());
        assertFalse(pagina.getRegistros().isEmpty());

        for (Iterator iter = pagina.getRegistros().iterator(); iter
        .hasNext();) {
        	ArchivoConciliacionVO element = (ArchivoConciliacionVO) iter
        	.next();
        	log
        	.debug("Registro: "
        			+ ReflectionToStringBuilder
        			.reflectionToString(element));
        }
        log.debug("Total de registros: " + pagina.getTotalRegistros());

        log.debug("Saliendo del metodo testEJBArchivoConciliacion()");

    }

    /**
     * Prueba el archivo de conciliacion para un agente firmado (id, folio,
     * cuenta)
     * Con todos los parametros llegan nulos
     * @throws BusinessException
     */
    public void testArchivoConciliacion_e4() throws BusinessException {

        log.debug("Entrando al metodo testEJBArchivoConciliacion()");

        assertNotNull(mercadoCapitales);

        log.debug("Probando con agente - id, folio, cuenta");


        PaginaVO pagina = mercadoCapitales.archivoConciliacion(null,
        		null, null);
        assertNotNull(pagina);
        assertNotNull(pagina.getRegistros());
        assertFalse(pagina.getRegistros().isEmpty());

        for (Iterator iter = pagina.getRegistros().iterator(); iter
        .hasNext();) {
        	ArchivoConciliacionVO element = (ArchivoConciliacionVO) iter
        	.next();
        	log
        	.debug("Registro: "
        			+ ReflectionToStringBuilder
        			.reflectionToString(element));
        }
        log.debug("Total de registros: " + pagina.getTotalRegistros());

        log.debug("Saliendo del metodo testEJBArchivoConciliacion()");

    }






    /**
    *
    * @throws BusinessException
    * Con todos los parametros llegan vacios
    */
   public void testArchivoConciliacionMovimientos_e1()
           throws BusinessException {

       log.debug("Entrando al metodo testEJBArchivoConciliacionMovimientos()");

       assertNotNull(mercadoCapitales);

       log.debug("Probando el servicio");

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

      log.debug("Entrando al metodo testEJBArchivoConciliacionMovimientos()");

      assertNotNull(mercadoCapitales);

      log.debug("Probando el servicio");

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

     log.debug("Entrando al metodo testEJBArchivoConciliacionMovimientos()");

     assertNotNull(mercadoCapitales);

     log.debug("Probando el servicio");

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

    log.debug("Entrando al metodo testEJBArchivoConciliacionMovimientos()");

    assertNotNull(mercadoCapitales);

    log.debug("Probando el servicio");

    try {
        AgenteVO agenteVO = new AgenteVO();
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


   /**
   *
   * @throws BusinessException
   * Cuando todos los parametros llegan vacios
   */
  public void testArchivoConciliacionDetalle_e1() throws BusinessException {

      log.debug("Entrando al metodo testEJBArchivoConciliacionDetalle()");

      assertNotNull(mercadoCapitales);

      log.debug("Probando el servicio");

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

     log.debug("Entrando al metodo testEJBArchivoConciliacionDetalle()");

     assertNotNull(mercadoCapitales);

     log.debug("Probando el servicio");

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

    log.debug("Entrando al metodo testEJBArchivoConciliacionDetalle()");

    assertNotNull(mercadoCapitales);

    log.debug("Probando el servicio");

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

   log.debug("Entrando al metodo testEJBArchivoConciliacionDetalle()");

   assertNotNull(mercadoCapitales);

   log.debug("Probando el servicio");

   try {
       AgenteVO agenteVO = new AgenteVO();
       EmisionVO emisionVO = new EmisionVO();

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