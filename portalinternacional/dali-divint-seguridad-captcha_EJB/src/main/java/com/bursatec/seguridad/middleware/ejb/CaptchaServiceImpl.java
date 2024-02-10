/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.bursatec.seguridad.middleware.ejb;

import com.bursatec.seguridad.util.CaptchaHelper;

/**
 * Implementaci&oacute;n del servicio de generaci&oacute;n de ID, generaci&oacute;n de captchas y validaci&oacute;n
 * del texto capturado por el usaurio.
 * @author Emigdio Hernández
 *
 */
public class CaptchaServiceImpl implements CaptchaService {

	/**
	 * @see com.bursatec.seguridad.middleware.ejb.CaptchaServiceSessionBeanLocal#generateId()
	 */
	public String generateId() {
		return CaptchaHelper.generateUniqueId();
	}

	/**
	 * @see com.bursatec.seguridad.middleware.ejb.CaptchaServiceSessionBeanLocal#validateResponseForId(java.lang.String, java.lang.Object)
	 */
	public boolean validateResponseForId(String id, String response) {
		return CaptchaHelper.validateResponseForId(id, response);
	}
	/**
	 * @see com.bursatec.seguridad.middleware.ejb.CaptchaService#generateCaptchaForId(java.lang.String)
	 */
	public com.bursatec.seguridad.util.ImagenSerializable generateCaptchaForId(String id) {
		com.bursatec.seguridad.util.ImagenSerializable image = CaptchaHelper.generateCaptchaForId(id);
		return image;
	}

}
