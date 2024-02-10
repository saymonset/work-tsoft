/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.validador;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW8BEN2014;

/**
 * Implementa las validaciones del formato W8IMY
 * 
 * @author César Hernández
 *
 */
public class ValidatorFormatW8IMYServiceImpl extends ValidatorFormatW8 implements Constantes {
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorFormatW8#validaFormato(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
	@Override
	public void validaFormatoW(Beneficiario beneficiario) {
		FormatoW8IMY formato = beneficiario.getFormatoW8IMY();
		
		getValidatorService().validaDTONoNulo(formato, " formato W8IMY ");

		getValidatorService().validaDTONoNulo(formato.getField3(), " Tipo Entidad ");
		getValidatorService().validaDTONoNulo(formato.getField3().getIdCampo(), " Tipo Entidad ");
		if(formato.getField3().getIdCampo() <= 0) {
			throw new BusinessException(getErrorResolver().getMessage("J0053", (Object)" Tipo Entidad "));
		}
		
		if (beneficiario.getTipoBeneficiario().getIdTipoBeneficiario() == FIDEICOMISO_SIMPLE ||
				beneficiario.getTipoBeneficiario().getIdTipoBeneficiario() == FIDEICOMISO_SIMPLE_W8IMY) {
			
			getValidatorService().validarClaveTipo(formato.getForeignTaxIdNumb(), " Foreign tax identifying number ");
		} else if (beneficiario.getTipoBeneficiario().getIdTipoBeneficiario() == INTERMEDIARIO_CALIFICADO_CRPR ||
				beneficiario.getTipoBeneficiario().getIdTipoBeneficiario() == INTERMEDIARIO_CALIFICADO_SRPR) {
			getValidatorService().validarClaveTipo(formato.getTaxpayerIdNumb(), " U.S. taxpayer identification number ");
			if (formato.getField9OptionB()) {
				getValidatorService().validarClaveTipo(formato.getField9OptionBLine(), " 9b ");
			}
			if (formato.getField9OptionC()) {
				getValidatorService().validarClaveTipo(formato.getField9OptionCLine(), " 9c ");
			}
		}

		getValidatorService().validaDTONoNulo(formato.getField9OptionA(), " 9a ");
		getValidatorService().validaDTONoNulo(formato.getField9OptionB(), " 9b ");
		getValidatorService().validaDTONoNulo(formato.getField9OptionC(), " 9c ");
		getValidatorService().validaDTONoNulo(formato.getField10OptionA(), " 10a ");
		getValidatorService().validaDTONoNulo(formato.getField10OptionB(), " 10b ");
		getValidatorService().validaDTONoNulo(formato.getField11(), " 11 ");
		getValidatorService().validaDTONoNulo(formato.getField12(), " 12 ");
		getValidatorService().validaDTONoNulo(formato.getField13(), " 13 ");
		getValidatorService().validaDTONoNulo(formato.getField14(), " 14 ");
		getValidatorService().validaDTONoNulo(formato.getField15(), " 15 ");	
	}

	
}
