/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 * 
 * May 6, 2008
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.test;

import java.util.Date;

import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.presentation.common.BaseWebTestCase;
import com.indeval.portaldali.presentation.common.constants.ValidadoresConstantes;
import com.indeval.portaldali.presentation.dto.mercadodinero.TraspasoMiscelaneaFiscalDTO;
import com.indeval.portaldali.presentation.validation.DTOValidator;

/**
 * Prueba unitaria correspondiente al controller de Traspaso Miscelanea Fiscal en captura de operaciones
 * 
 * @author Erik Vera Montoya
 * 
 * @version 1.0
 */
public class TraspasoMiscelaneaFiscalControllerTest extends BaseWebTestCase {

    /**
     * método para verificar Miscelanea Fiscal
     */
    public void testMiscelaneaFiscal() {
        TraspasoMiscelaneaFiscalDTO tmf = new TraspasoMiscelaneaFiscalDTO();
        tmf.getPosicionTraspasante().getCuenta().setCuenta("0009");
        tmf.getCuentaReceptor().getInstitucion().setClaveTipoInstitucion("01");
        tmf.getCuentaReceptor().getInstitucion().setFolioInstitucion("112");
        tmf.getCuentaReceptor().setCuenta("0009");
        tmf.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
        tmf.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
        tmf.getPosicionTraspasante().getEmision().getSerie().setSerie("120409");
        tmf.getPosicionTraspasante().getEmision().setCupon("0005");
        tmf.setCantidadOperada(new Long(112));
        tmf.setFechaAdquisicion(new Date());
        tmf.setPrecioAdquisicion(new Double(888888));
        tmf.setCliente("rrrr888");
        tmf.setRfcCurp("VEME830224BL2");

        System.out.println("-----> VALIDACION TRASPASO MISCELANEA FISCAL");
        DTOValidator validadorCuenta = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_TRASPASO_MF);
        ResultadoValidacionDTO resultadoValidor = validadorCuenta.validarDTO(tmf);
        assertTrue(resultadoValidor.isValido());
        if (resultadoValidor.isValido()) {
            System.out.println("----> SIN ERRORES");
        } else {
            System.out.println("----> CON ERRORES");
        }

    }

    /**
     * método para verificar Miscelanea Fiscal con Recepción
     */
    public void testMiscelaneaFiscalRecepcion() {
        TraspasoMiscelaneaFiscalDTO tmf = new TraspasoMiscelaneaFiscalDTO();
        tmf.getPosicionTraspasante().getCuenta().getInstitucion().setClaveTipoInstitucion("02");
        tmf.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("112");
        tmf.getPosicionTraspasante().getCuenta().setCuenta("0009");
        tmf.getCuentaReceptor().getInstitucion().setClaveTipoInstitucion("01");
        tmf.getCuentaReceptor().getInstitucion().setFolioInstitucion("112");
        tmf.getCuentaReceptor().setCuenta("0009");
        tmf.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
        tmf.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
        tmf.getPosicionTraspasante().getEmision().getSerie().setSerie("120409");
        tmf.getPosicionTraspasante().getEmision().setCupon("0005");
        tmf.setCantidadOperada(new Long(3333));
        tmf.setFechaAdquisicion(new Date());
        tmf.setPrecioAdquisicion(new Double(888888));
        tmf.setCliente("rrrr888");
        tmf.setRfcCurp("VEME830224BL2");

        System.out.println("-----> VALIDACION TRASPASO MISCELANEA FISCAL CON RECEPCION");
        DTOValidator validadorCuenta = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_TRASPASO_MF_RECEPCION);
        ResultadoValidacionDTO resultadoValidor = validadorCuenta.validarDTO(tmf);
        assertTrue(resultadoValidor.isValido());
        if (resultadoValidor.isValido()) {
            System.out.println("----> SIN ERRORES");
        } else {
            System.out.println("----> CON ERRORES");
        }
    }

}
