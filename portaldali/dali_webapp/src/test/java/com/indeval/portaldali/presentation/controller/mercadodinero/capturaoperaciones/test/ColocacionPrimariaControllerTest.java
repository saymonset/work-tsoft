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
import com.indeval.portaldali.presentation.dto.mercadodinero.ColocacionPrimariaDTO;
import com.indeval.portaldali.presentation.validation.DTOValidator;

/**
 * Controller de prueba para verificar el funcionamiento de los validadores para
 * la pantalla de captura de operaciones de fondeo a cuenta propia
 * 
 * @author Marcos Rivas
 * 
 */
public class ColocacionPrimariaControllerTest extends BaseWebTestCase {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.common.BaseWebTestCase#testDummy()
	 */
	@Override
	public void testDummy() {
		ColocacionPrimariaDTO colocacion = new ColocacionPrimariaDTO();

		
		colocacion.getPosicionTraspasante().getCuenta().setNumeroCuenta("01");
		colocacion.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("003");
		colocacion.getPosicionTraspasante().getCuenta().setNombreCuenta("4561");
		colocacion.getPosicionTraspasante().getCuenta().getTipoTenencia().setDescripcion("SICT");
		colocacion.getPosicionTraspasante().getCuenta().getInstitucion().setNombreCorto("ACCIVAL TERCEROS TRANSICION");
		colocacion.getCuentaReceptor().setNumeroCuenta("02");
		colocacion.getCuentaReceptor().getInstitucion().setFolioInstitucion("044");
		colocacion.getCuentaReceptor().setNombreCuenta("0102");
		colocacion.getCuentaReceptor().getTipoTenencia().setDescripcion("TERC");
		colocacion.getCuentaReceptor().getInstitucion().setNombreCorto("SEGUROS ARGOS - TERCEROS");

		colocacion.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
		colocacion.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
		colocacion.getPosicionTraspasante().getEmision().getSerie().setSerie("151106");
		colocacion.getPosicionTraspasante().getEmision().setCupon("0005");
		colocacion.setDiasVigentes(45);
		colocacion.setCantidadOperada(123L);
		colocacion.setPrecioTitulo(287273.12);

		DTOValidator validadorcolocacion = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTURA_OPERACIONES_COLOCACION_PRIMARIA_COMPRA_FV);
		
		
		ResultadoValidacionDTO resultado = validadorcolocacion.validarDTO(colocacion);

		System.out.println("Error: " + resultado.getMensaje());
		//assertTrue(resultado.isValido());
		System.out.println("Error: " + resultado.getMensaje());
		
		validadorcolocacion = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTURA_OPERACIONES_COLOCACION_PRIMARIA_COMPRA_FV+"Archivo");
		
		
		resultado = validadorcolocacion.validarDTO(colocacion);

		System.out.println("Error: " + resultado.getMensaje());
		assertTrue(resultado.isValido());
		System.out.println("Error: " + resultado.getMensaje());

	}

}
