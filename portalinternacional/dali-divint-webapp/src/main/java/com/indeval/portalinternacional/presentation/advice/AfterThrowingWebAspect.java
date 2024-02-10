/*
 *Copyright (c) 2005-2007 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.presentation.advice;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.InfrastructureException;

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
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Intercepta todas las operaciones que terminan en excepción en el portal
	 * DALI.
	 * 
	 * @param t la excepción que se origin.
	 */
	@Around("execution(* com.indeval.portalinternacional.middleware.services.*.*Service.*(..))")
	public Object handleException(ProceedingJoinPoint pjp) {
		
		Object returnValue = null;
		ResultadoInvocacionServicioUtil.setExisteError(Boolean.FALSE);
		try{
			returnValue = pjp.proceed();
		} catch(EJBException ejbException) {
			if (ejbException.getCause() != null && ejbException.getCause() instanceof BusinessException) {
				BusinessException bex = (BusinessException) ejbException.getCause();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bex.getMessage(), bex.getMessage()));
                log.error("Error", bex);
				ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
			} else {
				if (ejbException.getCausedByException() != null && ejbException.getCausedByException() instanceof BusinessException) {
					BusinessException bex = (BusinessException) ejbException.getCausedByException();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bex.getMessage(), bex.getMessage()));
                    log.error("Error", bex);
					ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
				} else {
					if(ejbException.getCausedByException() != null && ejbException.getCausedByException() instanceof InfrastructureException) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ejbException.getMessage(), ejbException.getMessage()));
                        log.error("Error", ejbException);
						ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
						throw (InfrastructureException)ejbException.getCausedByException();
					} else {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ejbException.getMessage(), ejbException.getMessage()));
                        log.error("Error", ejbException);
						ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
						throw new InfrastructureException(ejbException.getCausedByException());
					}
				}
			}
		} catch(Throwable t) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, t.getMessage(), t.getMessage()));
            log.error("Error", t);
			ResultadoInvocacionServicioUtil.setExisteError(Boolean.TRUE);
			throw new InfrastructureException(t);
			
		}
		
		return returnValue;
	}
}
