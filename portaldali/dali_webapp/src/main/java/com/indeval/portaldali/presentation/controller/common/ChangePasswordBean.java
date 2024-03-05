/*
 *Copyright (c) 2010 Bursatec. All Rights Reserved
 */
package com.indeval.portaldali.presentation.controller.common;


import java.rmi.RemoteException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.middleware.ejb.SeguridadServiceLocator;
import com.bursatec.seguridad.middleware.service.SeguridadException;
import com.bursatec.seguridad.middleware.service.SeguridadService;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;



/**
 * Backing bean para el cambio de password de usuario
 * 
 * @author Rafael Ibarra Zendejas
 */
public class ChangePasswordBean  {
	/** Indica la clave de usuario */
	private String usuario;
	/** Indica el password actual */
	private String passwordActual;
	/** Indica el password nuevo */
	private String passwordNuevo;
	/** Mensaje de error  */
	private String mensajeError = null;
	/** Bean para el inicio de sesion */
	private InicioSesionBean inicioSesionBean;
	/** BAndera que indica el exito en el cambio del password */
	private boolean exitoCambioPassword;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 
	 * @return
	 */
	public String changePassword() {
		String destino = null;
		mensajeError = null;
		exitoCambioPassword = false;
		
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		
		SeguridadService seguridadExposedService = SeguridadServiceLocator.obtenerSeguridadExposedService();
		if (seguridadExposedService == null) {
			mensajeError = "No se pudo localizar el servicio de Seguridad.";
			destino = null;
		} else {
				String ticket = (String) session.getAttribute(SeguridadConstants.TICKET_SESION);
				try {
					seguridadExposedService.cambiarPassword(ticket, passwordActual, passwordNuevo, passwordNuevo);
					
					inicioSesionBean = (InicioSesionBean)ctx.getApplication() .createValueBinding("#{inicioSesionBean}").getValue(ctx);
					
					inicioSesionBean.logout();
					
					request.setAttribute("Mensaje", "Su contrase\u00F1a se ha modificado exitosamente.");
					
					destino = "changeOk";
				}catch (SeguridadException se) {
					String errorMessage = se.getMessage();
					logger.error("Error de seguridad", se);
					if (StringUtils.isNotBlank(errorMessage)) {
						mensajeError = errorMessage;
					}
					else {
						mensajeError = "Usuario o contrase\u00F1a incorrectos.";
					}
					if (mensajeError.equalsIgnoreCase("El password del ticket esta expirado.")) {
						mensajeError = "El password ha caducado";
					}
					destino = null;
				}catch (Exception re) {
					logger.error("Error al iniciar sesi\u00F3n en el servicio web de seguridad", re);
					mensajeError = "Error al iniciar sesi\u00F3n en el servicio de seguridad";
					destino = null;
				}
		}
		return destino;
	}

	public String regresar() throws RemoteException, SeguridadException {
		return "inicio";
	}
	
	/**
	 * Indica la clave de usuario
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Indica el password actual
	 * @return the passwordActual
	 */
	public String getPasswordActual() {
		return passwordActual;
	}

	/**
	 * Indica el password actual
	 * @param passwordActual the passwordActual to set
	 */
	public void setPasswordActual(String passwordActual) {
		this.passwordActual = passwordActual;
	}

	/**
	 * Indica el password nuevo
	 * @return the passwordNuevo
	 */
	public String getPasswordNuevo() {
		return passwordNuevo;
	}

	/**
	 * Indica el password nuevo
	 * @param passwordNuevo the passwordNuevo to set
	 */
	public void setPasswordNuevo(String passwordNuevo) {
		this.passwordNuevo = passwordNuevo;
	}

	/**
	 * Mensaje de error
	 * @return the mensajeError
	 */
	public String getMensajeError() {
		return mensajeError;
	}

	/**
	 * Mensaje de error
	 * @param mensajeError the mensajeError to set
	 */
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	/**
	 * Bean para el inicio de sesion
	 * @return the inicioSesionBean
	 */
	public InicioSesionBean getInicioSesionBean() {
		return inicioSesionBean;
	}

	/**
	 * Bean para el inicio de sesion
	 * @param inicioSesionBean the inicioSesionBean to set
	 */
	public void setInicioSesionBean(InicioSesionBean inicioSesionBean) {
		this.inicioSesionBean = inicioSesionBean;
	}

	/**
	 * BAndera que indica el exito en el cambio del password
	 * @return the exitoCambioPassword
	 */
	public boolean isExitoCambioPassword() {
		return exitoCambioPassword;
	}

	/**
	 * BAndera que indica el exito en el cambio del password
	 * @param exitoCambioPassword the exitoCambioPassword to set
	 */
	public void setExitoCambioPassword(boolean exitoCambioPassword) {
		this.exitoCambioPassword = exitoCambioPassword;
	}
	
	
	
}
