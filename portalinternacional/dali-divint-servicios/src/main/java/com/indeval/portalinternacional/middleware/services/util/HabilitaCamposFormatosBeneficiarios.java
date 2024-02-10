package com.indeval.portalinternacional.middleware.services.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.vo.CamposFormatoW8BEN;
import com.indeval.portalinternacional.middleware.servicios.vo.CamposFormatoW8IMY;
import com.indeval.portalinternacional.middleware.servicios.vo.CamposFormatoW9;

/**
 * 
 * @author Oscar Garcia Granados
 * 
 */
public class HabilitaCamposFormatosBeneficiarios implements Constantes {

	/** Para el registro de log de esta clase */
	private static final Logger log = LoggerFactory.getLogger(HabilitaCamposFormatosBeneficiarios.class);

	/**
	 * 
	 * @param idCustodio
	 * @param idTipoBeneficiario
	 * @return CamposFormatoW8BEN
	 */
	public static CamposFormatoW8BEN definirCamposBloqueadosW8BEN(
			Long idCustodio, Long idTipoBeneficiario) {
		CamposFormatoW8BEN camposFormatoW8BEN = new CamposFormatoW8BEN();
		long idCust = idCustodio.longValue();
		long idTipoBenef = idTipoBeneficiario.longValue();

		/*
		 * AQUI SE DEFINEN LOS CAMPOS QUE SE BLOQUEAN DEPENDIENDO DE LOS
		 * PARAMETROS DE ENTRADA
		 */
		/* Se bloqeuan los campos por tipo de beneficiario */
		if( idCust == CN_CLEARSTREAM || idCust == CN_EUROCLEAR_BANK ) {
			camposFormatoW8BEN.setReferenceNumbers(true);
		}
		
		if (idTipoBenef == PERSONA_FISICA_NO_EUA) {
			camposFormatoW8BEN.bloqueaTodosTOBO();
			camposFormatoW8BEN.setField9a(true);
			camposFormatoW8BEN.setField9aResidencePlace(true);
			camposFormatoW8BEN.setField9c(true);
			camposFormatoW8BEN.setField9d(true);
			camposFormatoW8BEN.setField10Article(true);
			camposFormatoW8BEN.setField10Rate(true);
			camposFormatoW8BEN.setField10Reasons(true);
			camposFormatoW8BEN.setField10TypeOfIncome(true);
			camposFormatoW8BEN.setField11(true);
            camposFormatoW8BEN.setPartIVCapacityInWhichActing(true);
          /* se modifica la validacion y se separan para tener diferentes campos habilitados
           * dependiendo del tipoBenef para Deustche_Bank
           */
		} else if (idTipoBenef == PERSONA_MORAL_NO_EUA) {
			Map<Long, Boolean> field3 = camposFormatoW8BEN.getField3();
			field3.put( 7l, true);
			field3.put( 6l, true);
			field3.put( 5l, true);
			field3.put( 1l, true);
			field3.put( 4l, true);
			field3.put(12l, true);
			camposFormatoW8BEN.setField9a(true);
			camposFormatoW8BEN.setField9aResidencePlace(true);
			camposFormatoW8BEN.setField9c(true);
			camposFormatoW8BEN.setField10Article(true);
			camposFormatoW8BEN.setField10Rate(true);
			camposFormatoW8BEN.setField10Reasons(true);
			camposFormatoW8BEN.setField10TypeOfIncome(true);
			camposFormatoW8BEN.setField11(true);
			/*
			 * validacion para habilitar el Fideicomiso Simple
			 */
		} else if( idTipoBenef == FIDEICOMISO_SIMPLE && idCust == CN_DEUSTCHE_BANK ){
			Map<Long, Boolean> field3 = camposFormatoW8BEN.getField3();
			field3.put( 7l, true);
			field3.put( 6l, true);
			field3.put( 1l, true);
			field3.put( 4l, true);
			field3.put(12l, true);
			camposFormatoW8BEN.setField9a(true);
			camposFormatoW8BEN.setField9aResidencePlace(true);
			camposFormatoW8BEN.setField9c(true);
			camposFormatoW8BEN.setField10Article(true);
			camposFormatoW8BEN.setField10Rate(true);
			camposFormatoW8BEN.setField10Reasons(true);
			camposFormatoW8BEN.setField10TypeOfIncome(true);
			camposFormatoW8BEN.setField11(true);
			/*
			 * validacion para habilitar el Fideicomiso Complejo
			 */
		} else if( idTipoBenef == FIDEICOMISO_COMPLEJO && idCust == CN_DEUSTCHE_BANK ){
			Map<Long, Boolean> field3 = camposFormatoW8BEN.getField3();
			field3.put( 5l, true);
			field3.put( 6l, true);
			field3.put( 1l, true);
			field3.put( 4l, true);
			field3.put(12l, true);
			camposFormatoW8BEN.setField9a(true);
			camposFormatoW8BEN.setField9aResidencePlace(true);
			camposFormatoW8BEN.setField9c(true);
			camposFormatoW8BEN.setField10Article(true);
			camposFormatoW8BEN.setField10Rate(true);
			camposFormatoW8BEN.setField10Reasons(true);
			camposFormatoW8BEN.setField10TypeOfIncome(true);
			camposFormatoW8BEN.setField11(true);
			
		} else if (idTipoBenef == SIEFORE_AFORE) {
			camposFormatoW8BEN.bloqueaTodosTOBO();
			camposFormatoW8BEN.setField9a(true);
			camposFormatoW8BEN.setField9aResidencePlace(true);
			camposFormatoW8BEN.setField9c(true);
			camposFormatoW8BEN.setField10Article(true);
			camposFormatoW8BEN.setField10Rate(true);
			camposFormatoW8BEN.setField10Reasons(true);
			camposFormatoW8BEN.setField10TypeOfIncome(true);
			camposFormatoW8BEN.setField11(true);
		} else if (idTipoBenef == FIDEICOMISO_COMPLEJO) {
			camposFormatoW8BEN.bloqueaTodosTOBO();
			camposFormatoW8BEN.setField9a(true);
			camposFormatoW8BEN.setField9aResidencePlace(true);
			camposFormatoW8BEN.setField9c(true);
			camposFormatoW8BEN.setField10Article(true);
			camposFormatoW8BEN.setField10Rate(true);
			camposFormatoW8BEN.setField10Reasons(true);
			camposFormatoW8BEN.setField10TypeOfIncome(true);
			camposFormatoW8BEN.setField11(true);
		}

		return camposFormatoW8BEN;
	}
	
	/**
	 * 
	 * @param idCustodio
	 * @param ididTipoBenef
	 * @return CamposFormatoW8IMY
	 */
	public static CamposFormatoW8IMY definirCamposBloqueadosW8IMY(
			Long idCustodio, Long idTipoBeneficiario) {
		CamposFormatoW8IMY camposFormatoW8IMY = new CamposFormatoW8IMY();
		long idCust = idCustodio.longValue();
		long idTipoBenef = idTipoBeneficiario.longValue();

		/*
		 * AQUI SE DEFINEN LOS CAMPOS QUE SE BLOQUEAN DEPENDIENDO DE LOS
		 * PARAMETROS DE ENTRADA
		 */
		/* Se bloquean los de campos por tipo de beneficiario */
		if (idTipoBenef == FIDEICOMISO_SIMPLE || idTipoBenef == FIDEICOMISO_SIMPLE_W8IMY) {
			camposFormatoW8IMY.bloquearTodosOBO();
			Map<Long, Boolean> mapaField3 = camposFormatoW8IMY.getField3();
			mapaField3.put(7l, false);
			mapaField3.put(8l, false);
			camposFormatoW8IMY.bloquearTodosCampo6();
			if (idCust == CN_CLEARSTREAM || idCust == CN_EUROCLEAR_BANK) {
				camposFormatoW8IMY.setReferenceNumbers(true);
			}
			camposFormatoW8IMY.bloquearTodosPartes();
		} else if (idTipoBenef == INTERMEDIARIO_CALIFICADO_CRPR) {
			camposFormatoW8IMY.bloquearTodosOBO();
			camposFormatoW8IMY.bloquearTodosCampo6();
			camposFormatoW8IMY.setUsTaxpayerIdentificationNumber(false);
			camposFormatoW8IMY.bloquearTodosPartes();
			camposFormatoW8IMY.setField9a(true);
			camposFormatoW8IMY.setField9b(false);
			camposFormatoW8IMY.setField9bLine(false);
			camposFormatoW8IMY.setField9c(false);
			camposFormatoW8IMY.setField9cLine(false);
		} else if (idTipoBenef == INTERMEDIARIO_CALIFICADO_SRPR) {
			camposFormatoW8IMY.bloquearTodosOBO();
			camposFormatoW8IMY.bloquearTodosCampo6();
			camposFormatoW8IMY.setUsTaxpayerIdentificationNumber(false);
			camposFormatoW8IMY.bloquearTodosPartes();
			camposFormatoW8IMY.setField9a(true);
			camposFormatoW8IMY.setField9b(false);
			camposFormatoW8IMY.setField9bLine(false);
			camposFormatoW8IMY.setField9c(false);
			camposFormatoW8IMY.setField9cLine(false);
		} else if (idTipoBenef == INTERMEDIARIO_NO_CALIFICADO) {
			camposFormatoW8IMY.bloquearTodosOBO();
			camposFormatoW8IMY.setUsTaxpayerIdentificationNumberQIEIN(true);
			camposFormatoW8IMY.bloquearTodosPartes();
			camposFormatoW8IMY.setField10b(false);
		} else if (idTipoBenef == SOCIEDAD_PARTNERSHIP) {
			camposFormatoW8IMY.bloquearTodosOBO();
			camposFormatoW8IMY.setUsTaxpayerIdentificationNumberQIEIN(true);
			if (idCust == CN_CLEARSTREAM || idCust == CN_EUROCLEAR_BANK) {
				camposFormatoW8IMY.setReferenceNumbers(true);
			}
			camposFormatoW8IMY.bloquearTodosPartes();
		}

		return camposFormatoW8IMY;
	}

	/**
	 * 
	 * @param idCustodio
	 * @param ididTipoBenef
	 * @return CamposFormatoW9
	 */
	public static CamposFormatoW9 definirCamposBloqueadosW9(Long idCustodio, Long idTipoBeneficiario) {
		CamposFormatoW9 camposFormatoW9 = new CamposFormatoW9();
		if (idTipoBeneficiario == PERSONA_FISICA_EUA) {
			camposFormatoW9.setBussinessName(true);
			camposFormatoW9.bloquearTodosTOBO();
			camposFormatoW9.setExemptPayee(false);
			camposFormatoW9.setListAccountNumbers(false);
			camposFormatoW9.setSsn(false);
			camposFormatoW9.setEmployerIdNumb(true);
			camposFormatoW9.setExemptPayeeCode(true);
			camposFormatoW9.setExemptionFromFatca(true);
		} else if (idTipoBeneficiario == PERSONA_MORAL_EUA) {
			camposFormatoW9.setBussinessName(false);
			camposFormatoW9.bloquearTodosTOBO();
			Map<Long, Boolean> field3 = camposFormatoW9.getField3();
			field3.put(2l, Boolean.FALSE);
			field3.put(4l, Boolean.FALSE);
			//se habilitan estos radios por el cambio en el requerimiento
			field3.put(3l, Boolean.FALSE);
			field3.put(5l, Boolean.FALSE);
			field3.put(6l, Boolean.FALSE);
			field3.put(7l, Boolean.FALSE);
			
			camposFormatoW9.setExemptPayee(true);
			camposFormatoW9.setListAccountNumbers(false);
			camposFormatoW9.setSsn(true);
			camposFormatoW9.setEmployerIdNumb(false);
			camposFormatoW9.setExemptPayeeCode(false);
			camposFormatoW9.setExemptionFromFatca(false);
		}
		return camposFormatoW9;
	}

}
