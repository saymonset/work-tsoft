/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * Apr 14, 2008
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.test;

import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.presentation.common.BaseWebTestCase;
import com.indeval.portaldali.presentation.common.constants.ValidadoresConstantes;
import com.indeval.portaldali.presentation.dto.mercadodinero.VentaDTO;
import com.indeval.portaldali.presentation.validation.DTOValidator;

/**
 * Prueba unitaria correspondiente al controller de venta en captura de operaciones
 * 
 * @author Erik Vera Montoya
 * 
 */
public class VentaControllerTest extends BaseWebTestCase {

    /**
     * método para verificar las validación con la combinación mismo día
     */
    public void testMismoDia() {
        VentaDTO ventaTest = new VentaDTO();
        ventaTest.getPosicionTraspasante().getCuenta().getInstitucion().setClaveTipoInstitucion("02");
        ventaTest.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("112");
        ventaTest.getPosicionTraspasante().getCuenta().setNombreCuenta("0003");
        ventaTest.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
        ventaTest.getPosicionTraspasante().getEmision().getSerie().setId(111111);
        ventaTest.getPosicionTraspasante().getEmision().setCupon("0005");
        ventaTest.getCuentaReceptor().getInstitucion().setClaveTipoInstitucion("01");
        ventaTest.getCuentaReceptor().getInstitucion().setFolioInstitucion("112");
        ventaTest.getCuentaReceptor().setCuenta("0009");
        ventaTest.setCantidadOperada(new Long(3333));
        ventaTest.setPrecioTitulo(new Double(10));
        ventaTest.getPosicionTraspasante().getCuenta().setCuenta("1234");
        ventaTest.getPosicionTraspasante().getCuenta().getTipoTenencia().setDescripcion("PRO1");
        ventaTest.getPosicionTraspasante().getCuenta().getInstitucion().setNombreCorto("NOMBRECORTO");
        ventaTest.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
        ventaTest.getPosicionTraspasante().getEmision().getSerie().setSerie("120409");
        ventaTest.getCuentaReceptor().getTipoTenencia().setDescripcion("PRO2");
        ventaTest.getCuentaReceptor().getInstitucion().setNombreCorto("NOMBRECORTO");
        ventaTest.setPlazoLiquidacionHoras(12);

        /*
         * VALIDACION MISMO DIA
         */

        System.out.println("-----> VALIDACION MISMO DIA ");
        DTOValidator validadorCuenta = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTURA_OPERACIONES_VENTA_MD);
        ResultadoValidacionDTO resultadoValidor = validadorCuenta.validarDTO(ventaTest);
       // assertTrue(resultadoValidor.isValido());
        if (resultadoValidor.isValido()) {
            System.out.println("----> SIN ERRORES");
        } else {
            System.out.println("----> CON ERRORES");
        }
        
        System.out.println("-----> VALIDACION MISMO DIA ARCHIVO ");
        
        validadorCuenta = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTURA_OPERACIONES_VENTA_MD+"Archivo");
        resultadoValidor = validadorCuenta.validarDTO(ventaTest);
        if (resultadoValidor.isValido()) {
            System.out.println("----> SIN ERRORES");
        } else {
            System.out.println("----> CON ERRORES");
        }
    }

    /**
     * método para verificar las validación con la combinación mismo día compra en directo
     */
    public void testMismoDiaCompraDirecto() {
        VentaDTO ventaTest = new VentaDTO();
        ventaTest.getPosicionTraspasante().getCuenta().getInstitucion().setClaveTipoInstitucion("02");
        ventaTest.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("112");
        ventaTest.getPosicionTraspasante().getCuenta().setNombreCuenta("0003");
        ventaTest.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
        ventaTest.getPosicionTraspasante().getEmision().getSerie().setId(111111);
        ventaTest.getPosicionTraspasante().getEmision().setCupon("0005");
        ventaTest.getCuentaReceptor().getInstitucion().setClaveTipoInstitucion("01");
        ventaTest.getCuentaReceptor().getInstitucion().setFolioInstitucion("112");
        ventaTest.getCuentaReceptor().setCuenta("0009");
        ventaTest.setCantidadOperada(new Long(3333));
        ventaTest.setPrecioTitulo(new Double(10));
        ventaTest.getPosicionTraspasante().getCuenta().setCuenta("1234");
        ventaTest.getPosicionTraspasante().getCuenta().getTipoTenencia().setDescripcion("PRO1");
        ventaTest.getPosicionTraspasante().getCuenta().getInstitucion().setNombreCorto("NOMBRECORTO");
        ventaTest.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
        ventaTest.getPosicionTraspasante().getEmision().getSerie().setSerie("120409");
        ventaTest.getCuentaReceptor().getTipoTenencia().setDescripcion("PRO2");
        ventaTest.getCuentaReceptor().getInstitucion().setNombreCorto("NOMBRECORTO");
        ventaTest.setPlazoLiquidacionHoras(null);

        /*
         * VALIDACION MISMO DIA COMPRA EN DIRECTO
         */
        System.out.println("-----> VALIDACION MISMO DIA COMPRA EN DIRECTO ");
        DTOValidator validadorCuenta = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTURA_OPERACIONES_VENTA_MD_CD);
        ResultadoValidacionDTO resultadoValidor = validadorCuenta.validarDTO(ventaTest);
        //assertTrue(resultadoValidor.isValido());
        if (resultadoValidor.isValido()) {
            System.out.println("----> SIN ERRORES");
        } else {
            System.out.println("----> CON ERRORES");
        }
        
        System.out.println("-----> VALIDACION MISMO DIA COMPRA EN DIRECTO ARCHIVO ");
        validadorCuenta = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTURA_OPERACIONES_VENTA_MD_CD+"Archivo");
        resultadoValidor = validadorCuenta.validarDTO(ventaTest);
        if (resultadoValidor.isValido()) {
            System.out.println("----> SIN ERRORES");
        } else {
            System.out.println("----> CON ERRORES");
        }
    }

    /**
     * método para verificar las validación con la combinación fecha valor
     */
    public void testFechaValor() {
        VentaDTO ventaTest = new VentaDTO();
        ventaTest.getPosicionTraspasante().getCuenta().getInstitucion().setClaveTipoInstitucion("02");
        ventaTest.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("112");
        ventaTest.getPosicionTraspasante().getCuenta().setNombreCuenta("0003");
        ventaTest.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
        ventaTest.getPosicionTraspasante().getEmision().getSerie().setId(111111);
        ventaTest.getPosicionTraspasante().getEmision().setCupon("0005");
        ventaTest.getCuentaReceptor().getInstitucion().setClaveTipoInstitucion("01");
        ventaTest.getCuentaReceptor().getInstitucion().setFolioInstitucion("112");
        ventaTest.getCuentaReceptor().setCuenta("0009");
        ventaTest.setCantidadOperada(new Long(3333));
        ventaTest.setPrecioTitulo(new Double(10));
        ventaTest.getPosicionTraspasante().getCuenta().setCuenta("1234");
        ventaTest.getPosicionTraspasante().getCuenta().getTipoTenencia().setDescripcion("PRO1");
        ventaTest.getPosicionTraspasante().getCuenta().getInstitucion().setNombreCorto("NOMBRECORTO");
        ventaTest.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
        ventaTest.getPosicionTraspasante().getEmision().getSerie().setSerie("120409");
        ventaTest.getCuentaReceptor().getTipoTenencia().setDescripcion("PRO2");
        ventaTest.getCuentaReceptor().getInstitucion().setNombreCorto("NOMBRECORTO");
        ventaTest.setPlazoLiquidacionHoras(12);

        /*
         * VALIDACION FECHA VALOR
         */
        System.out.println("-----> VALIDACION FECHA VALOR");
        DTOValidator validadorCuenta = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTURA_OPERACIONES_VENTA_FV);
        ResultadoValidacionDTO resultadoValidor = validadorCuenta.validarDTO(ventaTest);
        //assertTrue(resultadoValidor.isValido());
        if (resultadoValidor.isValido()) {
            System.out.println("----> SIN ERRORES");
        } else {
            System.out.println("----> CON ERRORES");
        }
    }

    /**
     * método para verificar las validación con la combinación fecha valor compra en directo
     */
    public void testFechaValorCompraDirecto() {
        VentaDTO ventaTest = new VentaDTO();
        ventaTest.getPosicionTraspasante().getCuenta().getInstitucion().setClaveTipoInstitucion("02");
        ventaTest.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("112");
        ventaTest.getPosicionTraspasante().getCuenta().setNombreCuenta("0003");
        ventaTest.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
        ventaTest.getPosicionTraspasante().getEmision().getSerie().setId(111111);
        ventaTest.getPosicionTraspasante().getEmision().setCupon("0005");
        ventaTest.getCuentaReceptor().getInstitucion().setClaveTipoInstitucion("01");
        ventaTest.getCuentaReceptor().getInstitucion().setFolioInstitucion("112");
        ventaTest.getCuentaReceptor().setCuenta("0009");
        ventaTest.setCantidadOperada(new Long(3333));
        ventaTest.setPrecioTitulo(new Double(10));
        ventaTest.getPosicionTraspasante().getCuenta().setCuenta("1234");
        ventaTest.getPosicionTraspasante().getCuenta().getTipoTenencia().setDescripcion("PRO1");
        ventaTest.getPosicionTraspasante().getCuenta().getInstitucion().setNombreCorto("NOMBRECORTO");
        ventaTest.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
        ventaTest.getPosicionTraspasante().getEmision().getSerie().setSerie("120409");
        ventaTest.getCuentaReceptor().getTipoTenencia().setDescripcion("PRO2");
        ventaTest.getCuentaReceptor().getInstitucion().setNombreCorto("NOMBRECORTO");
        ventaTest.setPlazoLiquidacionHoras(12);

        /*
         * VALIDACION FECHA VALOR COMPRA EN DIRECTO
         */
        System.out.println("-----> VALIDACION FECHA VALOR COMPRA EN DIRECTO ");
        DTOValidator validadorCuenta = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTURA_OPERACIONES_VENTA_FV_CD);
        ResultadoValidacionDTO resultadoValidor = validadorCuenta.validarDTO(ventaTest);
      ///  assertTrue(resultadoValidor.isValido());
        if (resultadoValidor.isValido()) {
            System.out.println("----> SIN ERRORES");
        } else {
            System.out.println("----> CON ERRORES");
        }
    }
}
