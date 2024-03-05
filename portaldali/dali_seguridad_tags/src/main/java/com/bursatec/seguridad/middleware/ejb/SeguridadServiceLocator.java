/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * SeguridadServiceLocator.java
 * Mar 7, 2008
 */
package com.bursatec.seguridad.middleware.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.middleware.service.SeguridadService;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;

/**
 * Implementación de un Service Locator para encontrar los servicios de seguridad expuestos por el módulo
 * de seguridad de BURSATEC.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public class SeguridadServiceLocator {
	
	/**  */
	private static SeguridadService seguridadService = null;

	/**
	 * Localiza el servicio de seguridad expuesto por el módulo de seguridad de BURSATEC.
	 * 
	 * @return el servicio de seguridad expuesto por el módulo de seguridad de BURSATEC.
	 */
	public static SeguridadService obtenerSeguridadExposedService() {
		
			try {
				if(seguridadService == null) {
					
					Properties p = new Properties();
					p.load(SeguridadServiceLocator.class.getResourceAsStream(SeguridadConstants.SEGURIDAD_PROPERTIES_FILE));
					Context ctx = new InitialContext();				
					seguridadService = (SeguridadService)ctx.lookup(p.getProperty(SeguridadConstants.JNDI_NAME_PROPERTY));

				}
			}
			catch (Exception e) {
				LoggerFactory.getLogger(SeguridadServiceLocator.class).error("No se pudo obtener el EJB para la seguridad de BURSATEC:" + e.getMessage(), e);
			}
			return seguridadService;
	}
}
