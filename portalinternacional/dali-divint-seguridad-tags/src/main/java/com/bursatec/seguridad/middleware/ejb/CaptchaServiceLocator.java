/**
 * 2H Software - Bursatec 
 * Seguridad
 *
 * CaptchaServiceLocator.java
 * 06/03/2008
 */
package com.bursatec.seguridad.middleware.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;

/**
 * Oculta la lógica para realizar la localización remota del EJB o Servicio que
 * genera el Captcha utilizado por la seguridad.
 * 
 * @author Emigdio Hernández
 * 
 */
public class CaptchaServiceLocator {
	/**
	 * Localiza y recupera un EJB remoto que implementa la interfaz
	 * {@link CaptchaService}.
	 * 
	 * @return Objeto que implementa la interfaz captcha service, nulo en caso
	 *         de no localizar el objeto
	 */
	public static CaptchaService obtenerCaptchaService() {
		com.bursatec.seguridad.middleware.ejb.CaptchaService service = null;
		try {
			Properties p = new Properties();
			p.load(CaptchaServiceLocator.class.getResourceAsStream(SeguridadConstants.PROPERTIES_FILE));
//			Context ctx = new InitialContext(p);
			Context ctx = new InitialContext();

			Object EBJObject = ctx.lookup(p.getProperty(SeguridadConstants.JNDI_NAME_PROPERTY));

			service = (com.bursatec.seguridad.middleware.ejb.CaptchaService) EBJObject;
		} catch (Exception e) {
			LoggerFactory.getLogger(CaptchaServiceLocator.class).
					error("No se pudo obtener el EJB para la generación de CAPTCHA:" + e.getMessage(), e);
		}

		return service;
	}
}
