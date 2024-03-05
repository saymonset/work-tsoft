/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 * 
 * Apr 30, 2008
 */
package com.indeval.portaldali.presentation.controller.traspasosadministrativos;

import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.presentation.common.BaseWebTestCase;
import com.indeval.portaldali.presentation.common.constants.ValidadoresConstantes;
import com.indeval.portaldali.presentation.dto.traspasosadministrativos.TraspasosAdministrativosDTO;
import com.indeval.portaldali.presentation.validation.DTOValidator;

/**
 * @author Erik Vera Montoya
 * 
 * @version 1.0
 */
public class TraspasosAdministrativosControllerTest extends BaseWebTestCase {
    /**
     * método para verificar las validaciones de traspasos administrativos.
     */
    public void testTraspasosAdministrativos() {
        TraspasosAdministrativosDTO traspasos = new TraspasosAdministrativosDTO();

        traspasos.getPosicionTraspasante().getCuenta().getInstitucion().setClaveTipoInstitucion("02");
        traspasos.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("112");
        traspasos.getPosicionTraspasante().getCuenta().setNombreCuenta("0003");
        traspasos.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
        traspasos.getPosicionTraspasante().getEmision().getSerie().setId(111111);
        traspasos.getPosicionTraspasante().getEmision().setCupon("0005");
        traspasos.getCuentaReceptor().getInstitucion().setClaveTipoInstitucion("01");
        traspasos.getCuentaReceptor().getInstitucion().setFolioInstitucion("112");
        traspasos.getCuentaReceptor().setCuenta("0009");
        traspasos.setCantidadOperada(new Long(3333));
        traspasos.setPrecioTitulo(new Double(10));
        traspasos.getPosicionTraspasante().getCuenta().setCuenta("1234");
        traspasos.getPosicionTraspasante().getCuenta().getTipoTenencia().setDescripcion("PRO1");
        traspasos.getPosicionTraspasante().getCuenta().getInstitucion().setNombreCorto("NOMBRECORTO");
        traspasos.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
        traspasos.getPosicionTraspasante().getEmision().getSerie().setSerie("120409");
        traspasos.getCuentaReceptor().getTipoTenencia().setDescripcion("PRO2");
        traspasos.getCuentaReceptor().getInstitucion().setNombreCorto("NOMBRECORTO");
        traspasos.setPlazoLiquidacionHoras(12);

        // VALIDACION
        System.out.println("-----> VALIDACION TRASPASOS ADMINISTRATIVOS ");
        DTOValidator validadorCuenta = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_TRASPASOS_ADMINISTRATIVOS);

        // TODO
        ResultadoValidacionDTO resultadoValidor = validadorCuenta.validarDTO(traspasos);
        assertTrue(resultadoValidor.isValido());
        if (resultadoValidor.isValido()) {
            System.out.println("----> SIN ERRORES");
        } else {
            System.out.println("----> CON ERRORES");
        }
    }

}
