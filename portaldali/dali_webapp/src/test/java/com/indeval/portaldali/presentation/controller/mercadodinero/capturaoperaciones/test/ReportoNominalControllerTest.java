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
import com.indeval.portaldali.presentation.validation.DTOValidator;

/**
 * Controller de prueba para verificar el funcionamiento de los validadores para
 * la pantalla de captura de operaciones de reporto nominal y sus diferentes
 * opciones de compra.
 * 
 * @author Juan Carlos Huizar Moreno
 * 
 */
public class ReportoNominalControllerTest extends BaseWebTestCase {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.common.BaseWebTestCase#testDummy()
	 */
	public void testDummy() {
		ReportoNominalDTO reporto = new ReportoNominalDTO();

		
		/* VALIDADOR_CAPTURA_OPERACIONES_REPORTO_NOMINAL_MD  "PROVADA" */
		reporto.getPosicionTraspasante().getCuenta().setNumeroCuenta("02");
		reporto.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("034");
		reporto.getPosicionTraspasante().getCuenta().setCuenta("0070");
		reporto.getPosicionTraspasante().getCuenta().getTipoTenencia().setDescripcion("SICT");
		reporto.getPosicionTraspasante().getCuenta().getInstitucion().setNombreCorto("ACCIVAL TERCEROS TRANSICION");	
		
		reporto.getCuentaReceptor().setNumeroCuenta("01");
		reporto.getCuentaReceptor().getInstitucion().setFolioInstitucion("044");
		reporto.getCuentaReceptor().setCuenta("0101");
		reporto.getCuentaReceptor().getTipoTenencia().setDescripcion("TERC");
		reporto.getCuentaReceptor().getInstitucion().setNombreCorto("SEGUROS ARGOS - TERCEROS");	
		
		reporto.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
		reporto.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
		reporto.getPosicionTraspasante().getEmision().getSerie().setSerie("151106");
		reporto.getPosicionTraspasante().getEmision().setCupon("0005");
		
		reporto.setPlazoRepDias(27);
		reporto.setTasaPremio(1231.0);
		reporto.setCantidadOperada(new Long (123));
		reporto.setPrecioTitulo(20273.12);
		

		/* VALIDADOR_CAPTURA_OPERACIONES_REPORTO_NOMINAL_FV "PROVADA" 
		  reporto.getPosicionTraspasante().getCuenta().setNumeroCuenta("02");
		  reporto.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("034");
		  reporto.getPosicionTraspasante().getCuenta().setNombreCuenta("0030");
		  reporto.getPosicionTraspasante().getCuenta().getTipoTenencia().setDescripcion("SICT");
		  reporto.getPosicionTraspasante().getCuenta().getInstitucion().setNombreCorto("ACCIVAL TERCEROS TRANSICION");
		  
		  reporto.getCuentaReceptor().setNumeroCuenta("01");
		  reporto.getCuentaReceptor().getInstitucion().setFolioInstitucion("001");
		  reporto.getCuentaReceptor().setNombreCuenta("0081");
		  reporto.getCuentaReceptor().getTipoTenencia().setDescripcion("SVAR");
		  reporto.getCuentaReceptor().getInstitucion().setNombreCorto("BANORTE - PROPIA");
		  
		  reporto.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
		  reporto.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BCM0001");
		  reporto.getPosicionTraspasante().getEmision().getSerie().setSerie("161027");
		  reporto.getPosicionTraspasante().getEmision().setCupon("0005");
		  reporto.setPlazoRepDias(2);
		  reporto.setTasaPremio(1231.0);
		  reporto.setCantidadOperada(123.3);
		  reporto.setPrecioTitulo(20273.12);
		 */ 
		 

		/*  VALIDADOR_CAPTURA_OPERACIONES_REPORTO_NOMINAL_COMPRA_FV  MD "PROVADAS}" 
		  reporto.getPosicionTraspasante().getCuenta().setNumeroCuenta("04");
		  reporto.getPosicionTraspasante().getCuenta().getInstitucion().setFolioInstitucion("044");
		  reporto.getPosicionTraspasante().getCuenta().setCuenta("0030");
		  reporto.getPosicionTraspasante().getCuenta().getTipoTenencia().setDescripcion("SICT");
		  reporto.getPosicionTraspasante().getCuenta().getInstitucion().setNombreCorto("ACCIVAL TERCEROS TRANSICION");
		  reporto.getCuentaReceptor().setNumeroCuenta("0102");
		  reporto.getCuentaReceptor().setCuenta("0102");
		  reporto.getCuentaReceptor().getTipoTenencia().setDescripcion("TERC");
		  reporto.getCuentaReceptor().getInstitucion().setNombreCorto("SEGUROS ARGOS - TERCEROS");
		  
		  reporto.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor("2P");
		  reporto.getPosicionTraspasante().getEmision().getEmisora().setDescripcion("BANOB01");
		  reporto.getPosicionTraspasante().getEmision().getSerie().setSerie("151106");
		  reporto.getPosicionTraspasante().getEmision().setCupon("0005");
		  reporto.setPlazoRepDias(2760); 
		  reporto.setTasaPremio(1231.0);
		  reporto.setCantidadOperada(123.3);
		  reporto.setPrecioTitulo(20273.12);
		 */

		DTOValidator validadorReporto = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTURA_OPERACIONES_REPORTO_NOMINAL_MD);
		ResultadoValidacionDTO resultado = validadorReporto.validarDTO(reporto);
		
		System.out.println("Error: " + resultado.getMensaje());
		//assertTrue(resultado.isValido());
			System.out.println("Error: " + resultado.getMensaje());
			
			
		validadorReporto = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_CAPTURA_OPERACIONES_REPORTO_NOMINAL_MD+"Archivo");
		resultado = validadorReporto.validarDTO(reporto);	
		System.out.println("Error: " + resultado.getMensaje());
		//assertTrue(resultado.isValido());
			System.out.println("Error: " + resultado.getMensaje());
		
	}

}
