/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.validador;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW9;

/**
 * Clase padre que involucra todos los formato W9*. En esta clase padre
 * se desarrollan los métodos que son comnes entre todos los W9
 * 
 * @author César Hernández
 *
 */

public abstract class ValidatorFormatW9 extends ValidatorBeneficiario<FileTransferFormaW9>{
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorFormatService#validaFormato(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
	public abstract void validaFormatoW(Beneficiario beneficiario) throws BusinessException;

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorFormatService#validaDomicilio(com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio, java.lang.String)
	 */
	public void validaDomicilioBeneficiario(Domicilio domicilio, String tipoDomicilio) throws BusinessException {
		//String tipoDomicilio = " Domicilio W9 ";
		getValidatorService().validaDTONoNulo(domicilio, tipoDomicilio);
		
		getValidatorService().validarClaveTipo(domicilio.getStreet(), "Calle del " + tipoDomicilio);
		getValidatorService().validarClaveTipo(domicilio.getOuterNumber(), "Numero Exterior del " + tipoDomicilio);
		getValidatorService().validarClaveTipo(domicilio.getPostalCode(), "Codigo Postal del " + tipoDomicilio);
		getValidatorService().validarClaveTipo(domicilio.getCityTown(), "Ciudad o Poblado del " + tipoDomicilio);
		getValidatorService().validarClaveTipo(domicilio.getStateProvince(), "Estado o Provincia del " + tipoDomicilio);
	}
	
	
	
}
