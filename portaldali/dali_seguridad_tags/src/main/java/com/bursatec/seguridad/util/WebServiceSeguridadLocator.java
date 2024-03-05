/**
 * 2H Software - Bursatec 
 * Seguridad
 *
 * WebServiceSeguridadLocator.java
 * 06/03/2008
 */
package com.bursatec.seguridad.util;

import com.bursatec.seguridad.middleware.service.WebServiceSeguridadService;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;

/**
 * Clase encargada de localizar e instanciar , en su caso , al web service de seguridad.
 * @author Emigdio Hernández
 *
 */
public class WebServiceSeguridadLocator {
	/**
	 * Obtiene la instancia del cliente del servicio WEB.
	 * Esta función utiliza spring + axis para crear un cliente del servicio web.
	 * Retorna un objeto de axis que implementa dinmicamente la interfaz del servicio web
	 * @return
	 */
	public static WebServiceSeguridadService obtenerServicioWebSeguridad(){
		//TODO: ContextSingletonBeanFactoryLocator.getInstance(arg0)
		return (WebServiceSeguridadService)SpringBeanFactory.getInstance().getBean(SeguridadConstants.WEB_SERVICE_BEAN);
	}
}
