/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * Apr 17, 2008
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.test;

import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.presentation.common.BaseWebTestCase;
import com.indeval.portaldali.presentation.common.constants.ValidadoresConstantes;
import com.indeval.portaldali.presentation.dto.mercadodinero.FondeoCtaPropiaDTO;
import com.indeval.portaldali.presentation.validation.DTOValidator;

/**
 * Controller de prueba para verificar el funcionamiento de los validadores para
 * la pantalla de captura de operaciones de fondeo a cuenta propia
 * 
 * @author Marcos Rivas
 * 
 */
public class FondeoCtaPropiaControllerTest extends BaseWebTestCase {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.common.BaseWebTestCase#testDummy()
	 */
	
	public void testOperacionPorPantalla() {
		FondeoCtaPropiaDTO fondeo = new FondeoCtaPropiaDTO();

		fondeo.getPosicionTraspasante().getCuenta().setNumeroCuenta("01");
		fondeo.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("003");
		fondeo.getPosicionTraspasante().getCuenta().setNombreCuenta("4561");
		fondeo.getPosicionTraspasante().getCuenta().getTipoTenencia().setDescripcion("SICT");
		fondeo.getPosicionTraspasante().getCuenta().getInstitucion().setNombreCorto("ACCIVAL TERCEROS TRANSICION");
		fondeo.getPosicionTraspasante().getCuenta().setCuenta("02");
		fondeo.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("044");
		fondeo.getPosicionTraspasante().getCuenta().setNombreCuenta("0102");
		fondeo.getPosicionTraspasante().getCuenta().getTipoTenencia().setDescripcion("TERC");
		fondeo.getPosicionTraspasante().getCuenta().getInstitucion().setNombreCorto("SEGUROS ARGOS - TERCEROS");

		fondeo.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
		fondeo.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
		fondeo.getPosicionTraspasante().getEmision().getSerie().setSerie("151106");
		fondeo.getPosicionTraspasante().getEmision().setCupon("0005");
		fondeo.setDiasVigentes(45);
		fondeo.setCantidadOperada(null);

		DTOValidator validadorfondeo = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTURA_FONDEO_CTA_PROPIA);
		ResultadoValidacionDTO resultado = validadorfondeo.validarDTO(fondeo);

		System.out.println("Error: " + resultado.getMensaje());
		//assertTrue(resultado.isValido());
		System.out.println("Error: " + resultado.getMensaje());

	}
	
	public void testOperacionPorArchivo() {
		FondeoCtaPropiaDTO fondeo = new FondeoCtaPropiaDTO();

//		fondeo.getPosicionTraspasante().getCuenta().setNumeroCuenta("01");
//		fondeo.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("003");
//		fondeo.getPosicionTraspasante().getCuenta().setNombreCuenta("4561");
//		fondeo.getPosicionTraspasante().getCuenta().getTipoTenencia().setDescripcion("SICT");
//		fondeo.getPosicionTraspasante().getCuenta().getInstitucion().setNombreCorto("ACCIVAL TERCEROS TRANSICION");
//		fondeo.getPosicionTraspasante().getCuenta().setCuenta("02");
//		fondeo.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("044");
//		fondeo.getPosicionTraspasante().getCuenta().setNombreCuenta("0102");
//		fondeo.getPosicionTraspasante().getCuenta().getTipoTenencia().setDescripcion("TERC");
//		fondeo.getPosicionTraspasante().getCuenta().getInstitucion().setNombreCorto("SEGUROS ARGOS - TERCEROS");
//
//		fondeo.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
//		fondeo.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
//		fondeo.getPosicionTraspasante().getEmision().getSerie().setSerie("151106");
//		fondeo.getPosicionTraspasante().getEmision().setCupon("0005");
//		fondeo.setDiasVigentes(45);
//		fondeo.setCantidadOperada(null);

		DTOValidator validadorfondeo = (DTOValidator) getBean("validadorCapturaOperacionesFondeoCtaPropiaArchivo");
		ResultadoValidacionDTO resultado = validadorfondeo.validarDTO(fondeo);

		System.out.println("Error: " + resultado.getMensaje());
		//assertTrue(resultado.isValido());
		System.out.println("Error: " + resultado.getMensaje());

	}
	
	
	

}
