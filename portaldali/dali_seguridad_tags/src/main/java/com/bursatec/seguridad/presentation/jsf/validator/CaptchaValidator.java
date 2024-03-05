/**
 * Bursatec - SAVAR
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.bursatec.seguridad.presentation.jsf.validator;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.middleware.ejb.CaptchaServiceLocator;
import com.bursatec.seguridad.presentation.constants.SeguridadMensajesConstantes;
import com.bursatec.seguridad.presentation.jsp.tag.CaptchaComponent;

/**
 * Custom {@link Validator} que utiliza los servicios de validación de Captcha
 * para asegurar que la cadena capturada por el usuario corresponda a la imagen
 * mostrada originalmente.
 * 
 * @author 
 */
public class CaptchaValidator implements Validator {
	
	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		com.bursatec.seguridad.middleware.ejb.CaptchaService service = null;
		boolean valido = false;
		
		final ExpressionFactory expr = 
			FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
		try {
			service = CaptchaServiceLocator.obtenerCaptchaService();
			final ValueExpression valExp = 
				expr.createValueExpression(FacesContext.getCurrentInstance().getELContext(), "#{inicioSesionBean.mostrarCaptcha}", Object.class);
			final ValueExpression valExp2 = 
				expr.createValueExpression(FacesContext.getCurrentInstance().getELContext(), "#{inicioSesionBean.noValidaCaptcha}", Object.class);
			Boolean mostrarCaptcha = (Boolean)valExp.getValue(FacesContext.getCurrentInstance().getELContext());
			Boolean noValidaCaptcha = (Boolean)valExp2.getValue(FacesContext.getCurrentInstance().getELContext());
			
			
			
			if(!mostrarCaptcha || Boolean.TRUE == noValidaCaptcha ) {
				valido = true;
			}
			else if(component instanceof CaptchaComponent) {
				valido = service.validateResponseForId(((CaptchaComponent)component).getCaptchaId(), value.toString());
			}
			
		}
		catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error("Error al validar el captcha",e);
			throw new ValidatorException(
				new FacesMessage(SeguridadMensajesConstantes.ID_CAPTCHA_INVALIDO,
					SeguridadMensajesConstantes.ID_CAPTCHA_INVALIDO), e);
		}
		if(!valido){
			if( component instanceof UIInput ) {
				((UIInput)component).setValue(null);
				((UIInput)component).setSubmittedValue(null);
			}
			//Si hay un mensaje de error, lo borra
			final ValueExpression valExpMensajeError = 
				expr.createValueExpression(FacesContext.getCurrentInstance().getELContext(), "#{inicioSesionBean.mensajeError}", Object.class);
			String mensajeError = (String) valExpMensajeError.getValue(FacesContext.getCurrentInstance().getELContext());
			if(StringUtils.isNotBlank(mensajeError)) {				
				valExpMensajeError.setValue(FacesContext.getCurrentInstance().getELContext(), null);
			}
			throw new ValidatorException(
				new FacesMessage(SeguridadMensajesConstantes.TEXTO_NO_CORRESPONDE_IMAGEN,
					SeguridadMensajesConstantes.TEXTO_NO_CORRESPONDE_IMAGEN));
		}
	}
}
