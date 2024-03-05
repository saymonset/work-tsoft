/**
 * 2H Software
 * Bursatec - Seguridad
 */
package com.bursatec.seguridad.presentation.jsp.tag;

import java.io.IOException;

import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.bursatec.seguridad.middleware.ejb.CaptchaService;
import com.bursatec.seguridad.middleware.ejb.CaptchaServiceLocator;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;

/**
 * Implementacion de un componente de entrada de texto para capturar el texto
 * que el usuario esta leyendo de una imagen generada por el servicio de
 * captcha.
 * 
 * @author Emigdio Hernandez
 * 
 */
public class CaptchaComponent extends HtmlInputText {

	// beanName = "beanName" mappedName )
	private String captchaId = null;

	private CaptchaService captchaService = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponentBase#encodeChildren(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

		captchaService = CaptchaServiceLocator.obtenerCaptchaService();

		if (captchaService != null) {
			captchaId = captchaService.generateId();
		}
		request.setAttribute(SeguridadConstants.CAPTCHA_GENERATED_ID, captchaId);

		super.encodeBegin(context);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponentBase#encodeChildren(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeEnd(FacesContext context) throws IOException {

		super.encodeEnd(context);
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		request.removeAttribute(SeguridadConstants.CAPTCHA_GENERATED_ID);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIInput#saveState(javax.faces.context.FacesContext)
	 */
	@Override
	public Object saveState(FacesContext facesContext) {
		Object[] values = new Object[2];
		values[0] = super.saveState(facesContext);
		values[1] = captchaId;

		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIInput#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	@Override
	public void restoreState(FacesContext facesContext, Object state) {
		Object[] values = (Object[]) state;
		super.restoreState(facesContext, values[0]);
		captchaId = (String) values[1];
	}

	/**
	 * Obtiene el campo captchaId
	 * 
	 * @return captchaId
	 */
	public String getCaptchaId() {
		return captchaId;
	}

	/**
	 * Asigna el valor del campo captchaId
	 * 
	 * @param captchaId
	 *            el valor de captchaId a asignar
	 */
	public void setCaptchaId(String captchaId) {
		this.captchaId = captchaId;
	}

	/**
	 * Obtiene el campo captchaService
	 * 
	 * @return captchaService
	 */
	public CaptchaService getCaptchaService() {
		return captchaService;
	}

	/**
	 * Asigna el valor del campo captchaService
	 * 
	 * @param captchaService
	 *            el valor de captchaService a asignar
	 */

	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

}
