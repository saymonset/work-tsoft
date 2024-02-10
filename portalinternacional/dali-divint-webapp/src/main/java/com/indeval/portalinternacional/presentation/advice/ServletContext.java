/**
 * Sistema Internacional de Cotizaciones
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Oct 28, 2008
 */
package com.indeval.portalinternacional.presentation.advice;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.myfaces.webapp.StartupServletContextListener;

/**
 * Clase para volver habilitar el hot-deploy en el contexto de Faces
 * 
 * @author Erik Vera Montoya.
 * @version 1.0
 * 
 */
public class ServletContext implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(StartupServletContextListener.class.getName());
		buffer.append(".FACES_INIT_DONE");
		servletContextEvent.getServletContext().setAttribute(buffer.toString(), Boolean.FALSE);

	}

	public void contextInitialized(ServletContextEvent arg0) {
		
	}

}
