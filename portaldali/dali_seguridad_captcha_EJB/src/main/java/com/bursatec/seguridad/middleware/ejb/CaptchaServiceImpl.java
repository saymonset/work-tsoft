/**
 * 2H Software
 * Bursatec - Seguridad
 */
package com.bursatec.seguridad.middleware.ejb;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.bursatec.seguridad.util.CaptchaHelper;

/**
 * Implementación del servicio de generación de ID, generación de captchas y validación
 * del texto capturado por el usaurio.
 * @author Emigdio Hernández
 *
 */
public class CaptchaServiceImpl implements CaptchaService {

	/*
	 * (non-Javadoc)
	 * @see com.bursatec.seguridad.middleware.ejb.CaptchaServiceSessionBeanLocal#generateId()
	 */
	public String generateId() {
		return CaptchaHelper.generateUniqueId();
	}

	/*
	 * (non-Javadoc)
	 * @see com.bursatec.seguridad.middleware.ejb.CaptchaServiceSessionBeanLocal#validateResponseForId(java.lang.String, java.lang.Object)
	 */
	public boolean validateResponseForId(String id, String response) {
		return CaptchaHelper.validateResponseForId(id, response);
	}
	/*
	 * (non-Javadoc)
	 * @see com.bursatec.seguridad.middleware.ejb.CaptchaService#generateCaptchaForId(java.lang.String)
	 */
	public com.bursatec.seguridad.util.ImagenSerializable generateCaptchaForId(String id) {
		com.bursatec.seguridad.util.ImagenSerializable image = CaptchaHelper.generateCaptchaForId(id);
		return image;
	}

}
