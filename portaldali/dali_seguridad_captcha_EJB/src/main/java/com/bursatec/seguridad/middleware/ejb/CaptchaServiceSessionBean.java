/**
 * 2H Software
 * Bursatec - Seguridad
 */
package com.bursatec.seguridad.middleware.ejb;

import javax.ejb.Remote;
import javax.ejb.Stateless;
/**
 * SLSB para la generación de identificadores únicos, generacion de imgenes de captcha y 
 * validación de respuesta del usuario.
 * Este SLSB se caracteriza por instalarse en un sólo server para garantizar la generación
 * única de identificadores.
 * @author Emigdio Hernández
 *
 */
@Stateless (name="CaptchaServiceBean",mappedName="CaptchaServiceHome")
@Remote (CaptchaService.class)
public class CaptchaServiceSessionBean extends CaptchaServiceImpl implements  CaptchaService {

}
