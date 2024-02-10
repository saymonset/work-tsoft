/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.bursatec.seguridad.middleware.ejb;

import com.bursatec.seguridad.util.ImagenSerializable;

/**
 * @author Emigdio
 *
 */

public interface CaptchaService {
	/**
	 * Genera un ID único para la creación de un nuevo captcha
	 * @return ID único
	 */
	public String generateId();
	/**
	 * Valida la respuesta del usuario ante la presentaci&oacute;n de un captcha
	 * @param id Identificador del captcha
	 * @param response Respuesta ante el captcha del usuario
	 * @return true en caso de que coincida la respuesta, false en otro caso
	 */
	public boolean validateResponseForId(String id,String response);
	
	/**
	 * Genera una imagen que representa un captcha para un ID en espec&iacute;fico
	 * @param id ID &uacute;nico del captcha
	 * @return Imagen generada para mostrar al usuario
	 */
	public ImagenSerializable generateCaptchaForId(String id);
}
