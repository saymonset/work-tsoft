/**
 * 2H Software
 * Bursatec - Seguridad
 */
package com.bursatec.seguridad.presentation.jsp.tag;

import javax.faces.webapp.UIComponentELTag;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;

/**
 * Implementación de componente de etiqueta personalizada que permite el acceso al componente de autorización de recursos.
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public class IndevalAutorizacionTag extends UIComponentELTag {
	
	private String name;
	
	
	/**
	 * Atributo userInRole
	 */
	private String userInRole=null;
	
	/**
	 * Atributo userInAllRoles
	 */
	private String userInAllRoles = null;
	
	/**
	 * Obtiene el campo userInRole
	 * @return  userInRole
	 */
	public String getUserInRole() {
		return userInRole;
	}
	/**
	 * Asigna el valor del campo userInRole
	 * @param userInRole el valor de userInRole a asignar
	 */
	public void setUserInRole(String userInRole) {
		this.userInRole = userInRole;
	}
	/**
	 * Obtiene el campo userInAllRoles
	 * @return  userInAllRoles
	 */
	public String getUserInAllRoles() {
		return userInAllRoles;
	}
	/**
	 * Asigna el valor del campo userInAllRoles
	 * @param userInAllRoles el valor de userInAllRoles a asignar
	 */
	public void setUserInAllRoles(String userInAllRoles) {
		this.userInAllRoles = userInAllRoles;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTagBase#getComponentType()
	 */
	public String getComponentType() { 
		
		return SeguridadConstants.COMPONENT_TYPE_AUTORIZACION_TAG;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTagBase#getRendererType()
	 */
	public String getRendererType() {
		
		return SeguridadConstants.RENDERER_AUTORIZACION_TAG;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
