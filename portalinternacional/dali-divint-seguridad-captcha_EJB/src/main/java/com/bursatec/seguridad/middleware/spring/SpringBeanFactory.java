/**
 * 2H Software
 * Bursatec - Seguridad
 */
package com.bursatec.seguridad.middleware.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Fbrica de Beans de Spring
 * @author Emigdio Hernández
 *
 */
public class SpringBeanFactory {
	
	private static String CONFIG_LOCATION = "com/bursatec/seguridad/middleware/spring/applicationContext.xml";
	/**
	 * Instancia del singleton
	 */
	private static SpringBeanFactory factory = null;
	
	/**
	 * Application context de spring
	 */
	private ApplicationContext context = null; 
	
	private SpringBeanFactory(){
		context = new ClassPathXmlApplicationContext(CONFIG_LOCATION);
	}
	/**
	 * Obtiene la instancia del singleton
	 * @return Instancia del singleton
	 */
	public static synchronized SpringBeanFactory  getInstance(){
		if(factory == null){
			factory = new SpringBeanFactory();
		}
		return factory;
	}
	/**
	 * Obtiene un bean de la fbrica de spring
	 * @param id
	 * @return Bean de spring obenido
	 */
	public Object getBean(String id){
		return context.getBean(id);
	}
	
}
