/**
 * 2H Software
 * Bursatec - Seguridad
 */
package com.bursatec.seguridad.presentation.jsp.tag;

import javax.faces.webapp.UIComponentELTag;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;

/**
 * Implementación de componente de etiqueta personalizada que permite el acceso al componente de autorización de recursos por facultades.
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public class IndevalAutorizacionFacultadTag extends UIComponentELTag {
	
	
	/**
	 * Atributo userInRole
	 */
	private String userInFaculty=null;
	
	/**
	 * Atributo userInAllRoles
	 */
	private String userInAllFaculties = null;
	

	/*
	 * (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTagBase#getComponentType()
	 */
	public String getComponentType() { 
		
		return SeguridadConstants.COMPONENT_TYPE_AUTORIZACION_FACULTAD_TAG;
	}
	/*
	 * (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTagBase#getRendererType()
	 */
	public String getRendererType() {
		
		return SeguridadConstants.RENDERER_AUTORIZACION_TAG;
	}
	public String getUserInFaculty() {
		return userInFaculty;
	}
	public void setUserInFaculty(String userInFaculty) {
		this.userInFaculty = userInFaculty;
	}
	public String getUserInAllFaculties() {
		return userInAllFaculties;
	}
	public void setUserInAllFaculties(String userInAllFaculties) {
		this.userInAllFaculties = userInAllFaculties;
	}
	
	

}
