/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * Apr 15, 2008
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.test;

import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.presentation.common.BaseWebTestCase;
import com.indeval.portaldali.presentation.common.constants.ValidadoresConstantes;
import com.indeval.portaldali.presentation.dto.mercadodinero.ReportoNominalDTO;
import com.indeval.portaldali.presentation.dto.mercadodinero.TraspasoLibrePagoDTO;
import com.indeval.portaldali.presentation.validation.DTOValidator;

/**
 * Controller de prueba para verificar el funcionamiento de los validadores para
 * la pantalla de captura de operaciones de Traspaso libre de pago.
 * 
 * @author Juan Carlos Huizar Moreno
 * 
 */
public class TraspasoLibrePagoControllerTest extends BaseWebTestCase {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.common.BaseWebTestCase#testDummy()
	 */
	public void testDummy() {
		TraspasoLibrePagoDTO traspaso = new TraspasoLibrePagoDTO();

		/* VALIDADOR_CAPTURA_TRASPASO_LIBRE_PAGO */
		traspaso.getPosicionTraspasante().getCuenta().setCuenta("0030");
		traspaso.getPosicionTraspasante().getCuenta().getTipoTenencia().setDescripcion("SICT");
		traspaso.getPosicionTraspasante().getCuenta().getInstitucion().setNombreCorto("ACCIVAL TERCEROS TRANSICION");
		traspaso.getCuentaReceptor().setNumeroCuenta("0102");
		traspaso.getCuentaReceptor().getInstitucion().setFolioInstitucion("044");
		traspaso.getCuentaReceptor().setCuenta("0102");
		traspaso.getCuentaReceptor().getTipoTenencia().setDescripcion("TERC");
		traspaso.getCuentaReceptor().getInstitucion().setNombreCorto("SEGUROS ARGOS - TERCEROS");

		traspaso.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
		traspaso.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
		traspaso.getPosicionTraspasante().getEmision().getSerie().setSerie("151106");
		traspaso.getPosicionTraspasante().getEmision().setCupon("0005");
		traspaso.setCantidadOperada(new Long (1234));

		DTOValidator validadorReporto = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTURA_TRASPASO_LIBRE_PAGO);
		ResultadoValidacionDTO resultado = validadorReporto.validarDTO(traspaso);

		System.out.println("Error: " + resultado.getMensaje());
		//assertTrue(resultado.isValido());
		System.out.println("Error: " + resultado.getMensaje());
		
		traspaso = new TraspasoLibrePagoDTO();
		
		validadorReporto = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTURA_TRASPASO_LIBRE_PAGO+"Archivo");
		 resultado = validadorReporto.validarDTO(traspaso);

		System.out.println("Error: " + resultado.getMensaje());
		//assertTrue(resultado.isValido());
		System.out.println("Error: " + resultado.getMensaje());
	}
}
