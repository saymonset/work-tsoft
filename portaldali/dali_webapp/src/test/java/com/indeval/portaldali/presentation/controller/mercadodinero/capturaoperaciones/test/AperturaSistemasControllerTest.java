/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 * 
 * Apr 23, 2008
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.test;

import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.presentation.common.BaseWebTestCase;
import com.indeval.portaldali.presentation.common.constants.ValidadoresConstantes;
import com.indeval.portaldali.presentation.dto.mercadodinero.AperturaDeSistemaDTO;
import com.indeval.portaldali.presentation.validation.DTOValidator;

/**
 * Prueba unitaria correspondiente al controller de Apertura de Sistemas en captura de operaciones
 * 
 * @author Erik Vera Montoya
 * 
 * @version 1.0
 */
public class AperturaSistemasControllerTest extends BaseWebTestCase {

    /**
     * método que verifica apertura de sistemas
     * 
     */
    public void testAperturaSistemas() {
        AperturaDeSistemaDTO apertura = new AperturaDeSistemaDTO();
        apertura.getPosicionTraspasante().getCuenta().setCuenta("0009");
        apertura.getCuentaReceptor().getInstitucion().setClaveTipoInstitucion("01");
        apertura.getCuentaReceptor().getInstitucion().setFolioInstitucion("112");
        apertura.getCuentaReceptor().setCuenta("0009");
        apertura.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
        apertura.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
        apertura.getPosicionTraspasante().getEmision().getSerie().setSerie("120409");
        apertura.getPosicionTraspasante().getEmision().setCupon("0005");
        apertura.setCantidadOperada(new Long(112));

        DTOValidator validador = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTUTA_OPERACION_APERTURA_SISTEMAS);
        ResultadoValidacionDTO resultadoValidador = validador.validarDTO(apertura);

        System.out.println("AperturaSistemaControllerTest.testDummy---> sin recepcion");
        assertTrue(resultadoValidador.isValido());
        if (resultadoValidador.isValido()) {
            System.out.println("----> SIN ERRORES");
        } else {
            System.out.println("----> CON ERRORES");
        }

    }

    /**
     * método que verifica apertura de sistemas con recepción
     * 
     */
    public void testAperturaSistemasRecepcion() {
        AperturaDeSistemaDTO apertura = new AperturaDeSistemaDTO();
        apertura.getPosicionTraspasante().getCuenta().getInstitucion().setClaveTipoInstitucion("02");
        apertura.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("112");
        apertura.getPosicionTraspasante().getCuenta().setCuenta("0009");
        apertura.getCuentaReceptor().getInstitucion().setClaveTipoInstitucion("01");
        apertura.getCuentaReceptor().getInstitucion().setFolioInstitucion("112");
        apertura.getCuentaReceptor().setCuenta("0009");
        apertura.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
        apertura.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
        apertura.getPosicionTraspasante().getEmision().getSerie().setSerie("120409");
        apertura.getPosicionTraspasante().getEmision().setCupon("0005");
        apertura.setCantidadOperada(new Long(3333));

        DTOValidator validador = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTUTA_OPERACION_APERTURA_SISTEMAS_RECEPCION);
        ResultadoValidacionDTO resultadoValidador = validador.validarDTO(apertura);

        System.out.println("AperturaSistemaControllerTest.testAperturaSistemasRecepcion---> con recepcion");
        assertTrue(resultadoValidador.isValido());
        if (resultadoValidador.isValido()) {
            System.out.println("----> SIN ERRORES");
        } else {
            System.out.println("----> CON ERRORES");
        }
    }
}
