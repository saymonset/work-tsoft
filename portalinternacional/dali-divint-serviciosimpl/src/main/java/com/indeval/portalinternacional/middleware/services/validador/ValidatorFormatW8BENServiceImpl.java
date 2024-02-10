/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.validador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BEN;

/**
 * Implementa las validaciones propias del formato W8BEN
 * 
 * @author César Hernández
 *
 */
public class ValidatorFormatW8BENServiceImpl extends ValidatorFormatW8{

	/** Para el registro de log de esta clase */
	private static final Logger log = LoggerFactory.getLogger(ValidatorFormatW8BENServiceImpl.class);
  
    
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorFormatService#validaFormato(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
	public void validaFormatoW(Beneficiario beneficiario) throws BusinessException {
		FormatoW8BEN formato = beneficiario.getFormatoW8BEN();
		
		getValidatorService().validaDTONoNulo(formato, " formato W8BEN ");
   	 
		getValidatorService().validarClaveTipo(formato.getForeignTaxIdNumb(), " Foreign tax ID number ");
	   	 
		getValidatorService().validaDTONoNulo(formato.getField3(), " Tipo Beneficiario ");
		getValidatorService().validaDTONoNulo(formato.getField3().getIdCampo(), " Tipo Beneficiario ");
		if(formato.getField3().getIdCampo() <= 0) {
			throw new BusinessException(getErrorResolver().getMessage("J0053", (Object)" Tipo Beneficiario "));
		}
		getValidatorService().validaDTONoNulo(formato.getField9OptionA(), " 9a ");
		getValidatorService().validaDTONoNulo(formato.getField9OptionACountry(), " 9a Country ");
		getValidatorService().validaDTONoNulo(formato.getField9OptionB(), " 9b ");
	   	getValidatorService().validaDTONoNulo(formato.getField9OptionC(), " 9c ");
	   	getValidatorService().validaDTONoNulo(formato.getField9OptionD(), " 9d ");
	   	getValidatorService().validaDTONoNulo(formato.getField9OptionE(), " 9e ");
	   	getValidatorService().validaDTONoNulo(formato.getField11(), " 11 ");

       if (!formato.getField3().getIdCampo().equals(1l)) {
    	   getValidatorService().validarClaveTipo(formato.getCapacityActing(), " Capacity in which acting ");
		
		   /*
		    * se elimina la validacion del texto aceptado para este campo, con todas las combinaciones de custodios
		    */
		   //String capacity = formato.getCapacityActing().toUpperCase();
		   /* se elimina de esta condicion a Deustche_Bank */
		   /*if(!custodio.equals(Constantes.CN_DEUSTCHE_BANK)){
		   	String actoresInvalidos[] = {"LEGAL","APODERADO","REPRESENTANTE","REP."};
		       for (String actorInvalido : actoresInvalidos) {
		           if (capacity.contains(actorInvalido)) {
		               throw new BusinessException(errorResolver.getMessage("J0071"));
		           }
		       }
		   }*/
       }
       
       
	}


	
	

	


	
	

}
