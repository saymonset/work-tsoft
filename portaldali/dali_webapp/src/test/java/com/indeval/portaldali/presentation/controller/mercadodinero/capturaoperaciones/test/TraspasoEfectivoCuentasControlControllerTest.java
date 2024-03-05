/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 * 
 * Apr 25, 2008
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.test;

import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.presentation.common.BaseWebTestCase;
import com.indeval.portaldali.presentation.common.constants.ValidadoresConstantes;
import com.indeval.portaldali.presentation.dto.mercadodinero.TraspasoEfecCtasControlDTO;
import com.indeval.portaldali.presentation.validation.DTOValidator;

/**
 * @author Erik Vera Montoya
 * 
 * @version 1.0
 */
public class TraspasoEfectivoCuentasControlControllerTest extends BaseWebTestCase {
    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portaldali.presentation.common.BaseWebTestCase#testDummy()
     */
    @Override
    public void testDummy() {
        TraspasoEfecCtasControlDTO traspaso = new TraspasoEfecCtasControlDTO();
        traspaso.setImporteATraspasar(19.5);

        System.out.println("TraspasoEfectivoCuentasControlControllerTest.testDummy---> Misma Institucion");
        DTOValidator validador = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_TRASPASO_EFECTIVO_CUENTA_CONTROL_MISMA_INSTITUCION);
        ResultadoValidacionDTO resultado = validador.validarDTO(traspaso);
        assertTrue(resultado.isValido());
        if (resultado.isValido()) {
            System.out.println("----> SIN ERRORES");
        } else {
            System.out.println("----> CON ERRORES");
        }

        System.out.println("TraspasoEfectivoCuentasControlControllerTest.testDummy---> Terceros");
        validador = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_TRASPASO_EFECTIVO_CUENTA_CONTROL_TERCEROS);
        resultado = validador.validarDTO(traspaso);
        assertTrue(resultado.isValido());
        if (resultado.isValido()) {
            System.out.println("----> SIN ERRORES");
        } else {
            System.out.println("----> CON ERRORES");
        }
    }

}
