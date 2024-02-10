/**
 * 2H Software
 * Bursatec - Seguridad
 */
package com.bursatec.seguridad.presentation.jsf.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.middleware.ejb.CaptchaServiceLocator;
import com.bursatec.seguridad.presentation.jsp.tag.CaptchaComponent;

/**
 * Custom {@link Validator} que utiliza los servicios de validación de Captcha
 * para asegurar que la cadena capturada por el usuario corresponda a la imagen
 * mostrada originalmente.
 * @author Emigdio Hernández
 *
 */
public class CaptchaValidator implements Validator {
	
	
	
	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		
		com.bursatec.seguridad.middleware.ejb.CaptchaService service = null;
		boolean valido = false;
		try{
			
			service = CaptchaServiceLocator.obtenerCaptchaService();
			valido = service.validateResponseForId(((CaptchaComponent)component).getCaptchaId(), value.toString());
				
			
		}catch (Exception e) {
			LoggerFactory.getLogger(CaptchaServiceLocator.class).error("Error al validar el captcha",e);
			throw new ValidatorException(
					new FacesMessage("Id Inválido, no se puede validar una imagen previamente validada  existente",
							"Id Inválido, no se puede validar una imagen previamente validada  inexistente"));
		}
		

		if(!valido){
			((UIInput)component).setValue(null);
			((UIInput)component).setSubmittedValue(null);
			throw new ValidatorException(
					new FacesMessage("El texto capturado no corresponde al texto de la imagen",
							"El texto capturado no corresponde al texto de la imagen"));
		}
		


	}
	

}
