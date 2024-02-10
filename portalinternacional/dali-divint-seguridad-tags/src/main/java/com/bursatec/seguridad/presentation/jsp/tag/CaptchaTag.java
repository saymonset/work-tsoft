/**
 * 2H Software
 * Bursatec - Seguridad
 */
package com.bursatec.seguridad.presentation.jsp.tag;

import javax.faces.webapp.UIComponentELTag;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;

/**
 * Implementación de una etiqueta de JSF para el acceso al componente de generación
 * de imgenes Captcha para la autenticación de un usuario al inicio de sesión.
 * @author Emigdio Hernández
 *
 */
public class CaptchaTag extends UIComponentELTag {

	
	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTagBase#getComponentType()
	 */
	public String getComponentType() {
		return SeguridadConstants.COMPONENT_TYPE_CAPTCHA_TAG;
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTagBase#getRendererType()
	 */
	@Override
	public String getRendererType() {
		return SeguridadConstants.RENDERER_CAPTCHA_TAG;
	}

	
}
