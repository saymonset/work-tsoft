/**
 * 2H Software
 * Bursatec - Indeval
 */
package com.bursatec.seguridad.presentation.jsp.tag;

import java.io.IOException;

import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.bursatec.seguridad.middleware.ejb.SeguridadServiceLocator;
import com.bursatec.seguridad.middleware.service.SeguridadException;
import com.bursatec.seguridad.middleware.service.SeguridadService;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.bursatec.seguridad.vo.RespuestaVO;

/**
 * Componente de JSF que implementa la autorización de recursos o secciones en
 * páginas JSF basado en uno o ms roles del usuario. La funcionalidad de este
 * componente es: 1.- Procesar el contenido colocado entre el inicio y el fin de
 * la etiqueta si el usuario cuenta con el o los roles especificados como
 * parámetro de la etiqueta. 2.- Omitir el contenido colocado entre el inicio y
 * el fin de la etiqueta si el usuario no cuenta con el o los roles
 * especificados como parámetro.
 * 
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public class IndevalAutorizacionTagComponent extends HtmlPanelGroup {

	/**
	 * Atributo name
	 */
	private String name;
	
	/**
	 * Atributo userInRole
	 */
	private String userInRole = null;

	/**
	 * Atributo userInAllRoles
	 */
	private String userInAllRoles = null;
	/**
	 * Caracter usado para separar los roles
	 */
	private static final String SEPARADOR_ROLES = ",";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponentBase#encodeChildren(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		if (verificarRoles()) {
			super.encodeChildren(context);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponentBase#encodeChildren(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		if (verificarRoles()) {
			super.encodeBegin(context);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponentBase#encodeChildren(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		if (verificarRoles()) {
			super.encodeEnd(context);
		}
	}

	/**
	 * Verifica si el usuario tiene autorización con el o los roles indicados en
	 * la etiqueta.
	 * 
	 * @return
	 */
	private boolean verificarRoles() {

		if (userInRole != null && userInAllRoles != null) {
			throw new RuntimeException("El atributo userInRole y userInAllRoles son excluyentes, utilice uno a la vez");
		}

		if (userInRole == null && userInAllRoles == null) {
			throw new RuntimeException("Es requerido proporcionar alguno de los atributos: userInRole o  userInAllRoles");
		}
		String[] roles = null;
		FacesContext ctx = FacesContext.getCurrentInstance();
		RespuestaVO respuesta = null;
		boolean autorizado = false;
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
		HttpSession session = request.getSession(false);

		SeguridadService seguridadService = SeguridadServiceLocator.obtenerSeguridadExposedService();

		String ticket = (String) session.getAttribute(SeguridadConstants.TICKET_SESION);

		try {
			if (userInRole != null) {
				roles = userInRole.split(SEPARADOR_ROLES);
				if (roles != null) {
					autorizado = false;
					for (String rol : roles) {				
				respuesta = seguridadService.autorizaRol(ticket, request.getRemoteAddr(), userInRole);
				if (respuesta != null && respuesta.getTipoRespuesta() != null) {
					autorizado = SeguridadConstants.TIPO_RESPUESTA_AUTORIZADO.equals(respuesta.getTipoRespuesta().toLowerCase());
							if(autorizado){
								break;
							}
						}
					}	
				}
			}
			if (userInAllRoles != null) {
				roles = userInAllRoles.split(SEPARADOR_ROLES);
				if (roles != null) {
					autorizado = true;
					for (String rol : roles) {
						
						respuesta = seguridadService.autorizaRol(ticket, request.getRemoteAddr(),rol == null ? "" : rol.trim());
						autorizado = autorizado && 
						SeguridadConstants.TIPO_RESPUESTA_AUTORIZADO.equals(respuesta.getTipoRespuesta().toLowerCase());
						if(!autorizado){
							break;
						}
					}
				}
			}
		

		} catch (SeguridadException se) {
			Logger log = LoggerFactory.getLogger(this.getClass());
			log.error("Error al verificar roles en el servicio web de seguridad", se);
			String mensajeError = "";
			mensajeError = mensajeError + se.getMessage();
			if (mensajeError.toLowerCase().indexOf(SeguridadConstants.TICKET_EXPIRADO) != -1) {
				// El ticket ha expirado
				session.invalidate();
				try {
					ctx.responseComplete();
					response.sendRedirect(request.getContextPath());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		} catch (Exception re) {
			Logger log = LoggerFactory.getLogger(this.getClass());
			log.error("Error al verificar roles en el servicio web de seguridad", re);
			String mensajeError = "";
			// Verificar si existe una excepción de seguridad
			if (re.getMessage().indexOf(SeguridadException.class.getName()) != -1) {
				mensajeError = mensajeError
						+ re.getMessage().substring(
								re.getMessage().indexOf(SeguridadException.class.getName()) + SeguridadException.class.getName().length() + 1);
			} else {
				mensajeError = mensajeError + re.getMessage();
			}
		}
		/**
		 * Se sube al request el resultado ya sea con el default o con en nombre de la variable indicada
		 */
		String permit="permit"; 
		
		if(StringUtils.isNotBlank(name)){
			permit=name;
		}
		request.setAttribute(permit, autorizado);		
		
		return autorizado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIInput#saveState(javax.faces.context.FacesContext)
	 */
	@Override
	public Object saveState(FacesContext facesContext) {
		Object[] values = new Object[4];
		values[0] = super.saveState(facesContext);
		values[1] = userInRole;
		values[2] = userInAllRoles;
		values[3] = name;

		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIInput#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	@Override
	public void restoreState(FacesContext facesContext, Object state) {
		Object[] values = (Object[]) state;
		super.restoreState(facesContext, values[0]);
		userInRole = (String) values[1];
		userInAllRoles = (String) values[2];
		name = (String) values[3];
		
	}

	/**
	 * Obtiene el campo userInRole
	 * 
	 * @return userInRole
	 */
	public String getUserInRole() {
		return userInRole;
	}

	/**
	 * Asigna el valor del campo userInRole
	 * 
	 * @param userInRole
	 *            el valor de userInRole a asignar
	 */
	public void setUserInRole(String userInRole) {
		this.userInRole = userInRole;
	}

	/**
	 * Obtiene el campo userInAllRoles
	 * 
	 * @return userInAllRoles
	 */
	public String getUserInAllRoles() {
		return userInAllRoles;
	}

	/**
	 * Asigna el valor del campo userInAllRoles
	 * 
	 * @param userInAllRoles
	 *            el valor de userInAllRoles a asignar
	 */
	public void setUserInAllRoles(String userInAllRoles) {
		this.userInAllRoles = userInAllRoles;
	}

	/**
	 * Obtiene en nombre el la variable que se almacenara 
	 * en el request con el resultado de la autorizacion.
	 * 
	 * @return
	 */
	public String getName() { 
		return name;
	}

	/**
	 * Asigna el nombre de la varible con la cual se almacenara 
	 * en el request el resultado de la autorizacion.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
