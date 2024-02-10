/**
 * 2H Software
 * Bursatec - Seguridad
 */
package com.bursatec.seguridad.util;

import java.awt.image.BufferedImage;
import java.util.UUID;

import com.bursatec.seguridad.middleware.spring.SpringBeanFactory;
import com.octo.captcha.service.CaptchaService;

/**
 * Clase Helper encargada del manejo de los captcha
 * @author Emigdio Hernández
 */
public class CaptchaHelper {
	
	private static String CAPTCHA_SERVICE_ID = "imageCaptchaService";
	/**
	 * Genera un ID único, basado en el generador de identificadores de java UUID
	 * @return String que representa al ID generado
	 */
	public static String generateUniqueId(){
		return UUID.randomUUID().toString();
	
	}
	/**
	 * Genera una imagen que representa un captcha para un ID en específico
	 * @param id ID único del captcha
	 * @return Imagen generada para mostrar al usuario
	 */
	public static ImagenSerializable generateCaptchaForId(String id){
		 CaptchaService captchaServ =  (CaptchaService)SpringBeanFactory.getInstance().getBean(CAPTCHA_SERVICE_ID);
		 ImagenSerializable img = new ImagenSerializable();
		 img.setImage((BufferedImage)captchaServ.getChallengeForID(id));
		 return img;
	}
	
	/**
	 * Valida la respuesta del usuario ante la presentación de un captcha
	 * @param id Identificador del captcha
	 * @param response Respuesta ante el captcha del usuario
	 * @return true en caso de que coincida la respuesta, false en otro caso
	 */
	public static boolean validateResponseForId(String id,String response){
		CaptchaService captchaServ =  (CaptchaService)SpringBeanFactory.getInstance().getBean(CAPTCHA_SERVICE_ID);
		
		return  captchaServ.validateResponseForID(id, response);
	}
	
}
