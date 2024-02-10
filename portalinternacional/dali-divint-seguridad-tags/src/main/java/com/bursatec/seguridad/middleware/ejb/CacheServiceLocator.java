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

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.commons.cache.services.DistCacheService;

/**
 * Implementación de un Service Locator para encontrar los servicios de cache expuestos 
 *
 *
 */
public class CacheServiceLocator {
	
	/**  */
	private static DistCacheService distCacheService = null;

	/**
	 * Localiza el servicio de seguridad expuesto por el módulo de seguridad de BURSATEC.
	 * 
	 * @return el servicio de seguridad expuesto por el módulo de seguridad de BURSATEC.
	 */
	public static DistCacheService obtenerCacheService() {
		
			try {
				if(distCacheService == null) {
					
					Properties p = new Properties();
					p.load(CacheServiceLocator.class.getResourceAsStream(SeguridadConstants.CACHE_PROPERTIES_FILE));
					Context ctx = new InitialContext();				
					distCacheService = (DistCacheService)ctx.lookup(p.getProperty(SeguridadConstants.JNDI_NAME_PROPERTY));

				}
			}
			catch (Exception e) {
				LoggerFactory.getLogger(CacheServiceLocator.class).error("No se pudo obtener el EJB para la seguridad de BURSATEC:" + e.getMessage(), e);
			}
			return distCacheService;
	}
}
