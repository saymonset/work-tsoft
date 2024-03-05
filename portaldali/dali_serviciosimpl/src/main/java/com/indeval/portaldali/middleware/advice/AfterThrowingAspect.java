/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * AfterThrowingAspect.java
 * May 15, 2008
 */
package com.indeval.portaldali.middleware.advice;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import com.indeval.portaldali.middleware.services.modelo.BusinessDataException;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;

/**
 * Implementación de un aspecto interceptor para el manejo de excepciones en el
 * portal DALI.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
@Aspect
public class AfterThrowingAspect {

	/**
	 * Intercepta todas las operaciones que terminan en excepción en el portal
	 * DALI.
	 * 
	 * @param t
	 *            la excepción que se origin.
	 */
	@AfterThrowing(pointcut = "execution(* com.indeval.portaldali.middleware.services..*.*(..))", throwing = "t")
	public void handleException(Throwable t) {
		if (t instanceof BusinessException || t instanceof BusinessDataException || t instanceof InfrastructureException) {
			return;
		}

		throw new InfrastructureException(t);
	}
}
