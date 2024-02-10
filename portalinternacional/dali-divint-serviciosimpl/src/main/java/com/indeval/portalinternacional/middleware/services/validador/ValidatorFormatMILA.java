/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.validador;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferForma;

/**
 * Clase padre que involucra todos los formato W8*. En esta clase padre
 * se desarrollan los métodos que son comnes entre todos los W8
 * 
 * @author César Hernández
 *
 */


public abstract class ValidatorFormatMILA extends ValidatorBeneficiario<FileTransferForma> {
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorFormatService#validaFormato(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
	public abstract void validaFormatoW(Beneficiario beneficiario);

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorFormatService#validaDomicilio(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
	public void validaDomicilioBeneficiario(Domicilio domicilio, String tipoDomicilio) throws BusinessException {
		
		getValidatorService().validaDTONoNulo(domicilio, tipoDomicilio);
		
		getValidatorService().validarClaveTipo(domicilio.getStreet(), "Calle del " + tipoDomicilio);
		getValidatorService().validarClaveTipo(domicilio.getOuterNumber(), "Numero Exterior del " + tipoDomicilio);
		getValidatorService().validarClaveTipo(domicilio.getPostalCode(), "Codigo Postal del " + tipoDomicilio);
		getValidatorService().validarClaveTipo(domicilio.getCityTown(), "Ciudad o Poblado del " + tipoDomicilio);
		getValidatorService().validarClaveTipo(domicilio.getStateProvince(), "Estado o Provincia del " + tipoDomicilio);
		getValidatorService().validarClaveTipo(domicilio.getCountry(), "Pais del " + tipoDomicilio);
	}
	
	
}
