/**
 * Sistema SAVAR
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Aug 14, 2008
 */
package com.indeval.portaldali.middleware.services;

import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

/**
 * Clase que extiende a {@link SpringBeanAutowiringInterceptor} para especificar el nombre de la fábrica de Spring a utilizar.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class SpringBeanAutowiringInterceptorDali extends SpringBeanAutowiringInterceptor {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor#getBeanFactoryLocatorKey(java.lang.Object)
	 */
	@Override
	protected String getBeanFactoryLocatorKey(Object target) {
		
		return "indeval.dali.factory";
	}
}
