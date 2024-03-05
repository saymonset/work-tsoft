/**
 * Bursatec - INDEVAL
 * Portal DALI
 *
 * AfterThrowingWebAspect.java
 * May 15, 2008
 */
package com.indeval.portaldali.presentation.advice;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;
import com.indeval.portaldali.presentation.util.ResultadoInvocacionServicioUtil;

/**
 * Implementación de un aspecto interceptor para el manejo de excepciones de
 * negocio de la capa de servicios de negocio.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
@Aspect
public class AfterThrowingWebAspect {

	/**
	 * Intercepta todas las operaciones que terminan en excepción en el portal
	 * DALI.
	 * 
	 * @param t
	 *            la excepción que se origin.
	 */
	@Around("execution(* com.indeval.portaldali.middleware.services.*.*Service.*(..))")
	public Object handleException(ProceedingJoinPoint pjp) {
		
		Object returnValue = null;
		ResultadoInvocacionServicioUtil.setExisteError(Boolean.FALSE);
		try{
			returnValue = pjp.proceed();
		} catch(EJBException ejbException) {
			if (ejbException.getCause() != null && ejbException.getCause() instanceof BusinessException) {
				BusinessException bex = (BusinessException) ejbException.getCause();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bex.getMessage(), bex.getMessage()));
				ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
			} else {
				if (ejbException.getCausedByException() != null && ejbException.getCausedByException() instanceof BusinessException) {
					BusinessException bex = (BusinessException) ejbException.getCausedByException();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bex.getMessage(), bex.getMessage()));
					ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
				} else {
					if(ejbException.getCausedByException() != null && ejbException.getCausedByException() instanceof InfrastructureException) {
						throw (InfrastructureException)ejbException.getCausedByException();
					} else {
						throw new InfrastructureException(ejbException.getCausedByException());
					}
				}
			}
		} catch(Throwable t) {
			throw new InfrastructureException(t);
		}
		
		return returnValue;
	}
}
