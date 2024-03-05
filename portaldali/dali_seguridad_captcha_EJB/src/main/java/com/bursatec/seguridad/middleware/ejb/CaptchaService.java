/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.bursatec.seguridad.middleware.ejb;

import java.awt.image.BufferedImage;

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
	 * Valida la respuesta del usuario ante la presentación de un captcha
	 * @param id Identificador del captcha
	 * @param response Respuesta ante el captcha del usuario
	 * @return true en caso de que coincida la respuesta, false en otro caso
	 */
	public boolean validateResponseForId(String id,String response);
	
	/**
	 * Genera una imagen que representa un captcha para un ID en específico
	 * @param id ID único del captcha
	 * @return Imagen generada para mostrar al usuario
	 */
	public ImagenSerializable generateCaptchaForId(String id);
}
