/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.validador;


import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BEN;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW8BEN2014;

/**
 * Clase padre que involucra todos los formato W9*. En esta clase padre
 * se desarrollan los métodos que son comnes entre todos los W9
 * 
 * @author César Hernández
 *
 */

public abstract class ValidatorFormatW8BEN2014 extends ValidatorBeneficiario<FileTransferFormaW8BEN2014>{
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorFormatService#validaFormato(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
	public void validaFormatoW(Beneficiario beneficiario) throws BusinessException{
		FormatoW8BEN formato = beneficiario.getFormatoW8BEN();
		
		getValidatorService().validaDTONoNulo(formato, " formato W8BEN ");
   	 
		getValidatorService().validarClaveTipo(formato.getForeignTaxIdNumb(), " Foreign tax ID number ");
		
		getValidatorService().validaDTONoNulo(formato.getField3(), " Tipo Beneficiario ");
		getValidatorService().validaDTONoNulo(formato.getField3().getIdCampo(), " Tipo Beneficiario ");
		if(formato.getField3().getIdCampo() <= 0) {
			throw new BusinessException(getErrorResolver().getMessage("J0053", (Object)" Tipo Beneficiario "));
		}
		//getValidatorService().validaDTONoNulo(formato.getField9OptionA(), " 9a ");
		getValidatorService().validaDTONoNulo(formato.getField9OptionACountry(), " 9 Country ");
		
		getValidatorService().validaDTONoNulo(formato.getSigner(), " Signer ");		
		
		
		if(!formato.isDisabledSection10()){
			getValidatorService().validaDTONoNulo(StringUtils.isEmpty(formato.getField10Article())?null:formato.getField10Article(), " 10 Article ");			
			getValidatorService().validaDTONoNulo(StringUtils.isEmpty(formato.getField10Rate())?null:formato.getField10Rate(), " 10 Rate % ");
			Matcher matcher=getPatronPorcentaje().matcher(formato.getField10Rate());
			if(!matcher.find(0)){
				throw new BusinessException(getErrorResolver().getMessage("J0106",(Object)"10 Rate es una cantidad") );
			}
			
			getValidatorService().validaDTONoNulo(StringUtils.isEmpty(formato.getField10Reasons())?null:formato.getField10Reasons(), " 10 Reasons ");
			getValidatorService().validaDTONoNulo(StringUtils.isEmpty(formato.getField10Type())?null:formato.getField10Type(), " 10 Type of income ");
		}
		validaFechaNacimiento(formato.getFechaNacimiento());
       if (!formato.getField3().getIdCampo().equals(1l)) {
    	   getValidatorService().validarClaveTipo(formato.getCapacityActing(), " Capacity in which acting ");
       }
	}

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
	
	public void validaFechaNacimiento(Date fechaNacimiento){
		getValidatorService().validaDTONoNulo(fechaNacimiento, " error en fecha de nacimiento ");
		if(fechaNacimiento != null){
			Calendar cal = Calendar.getInstance();
			int yearActual = cal.get(Calendar.YEAR);			
			cal.setTime(fechaNacimiento);
		    int year = cal.get(Calendar.YEAR);
		    
		    if(year < (yearActual-100) || year >= yearActual){
		    	throw new BusinessException(getErrorResolver().getMessage("J0109"));
		    }
		}
	}
	
}
