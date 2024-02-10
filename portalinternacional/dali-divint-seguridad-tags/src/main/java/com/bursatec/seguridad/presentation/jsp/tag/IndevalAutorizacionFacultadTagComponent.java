/**
 * 2H Software
 * Bursatec - Indeval
 */
package com.bursatec.seguridad.presentation.jsp.tag;

import java.io.IOException;
import java.util.Set;

import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.middleware.ejb.SeguridadServiceLocator;
import com.bursatec.seguridad.middleware.service.SeguridadException;
import com.bursatec.seguridad.middleware.service.SeguridadService;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;

/**
 * Componente de JSF que implementa la autorización de recursos o secciones en
 * páginas JSF basado en una o ms facultades del usuario. La funcionalidad de este
 * componente es: 1.- Procesar el contenido colocado entre el inicio y el fin de
 * la etiqueta si el usuario cuenta con la o las facultades especificados como
 * parámetro de la etiqueta. 2.- Omitir el contenido colocado entre el inicio y
 * el fin de la etiqueta si el usuario no cuenta con la o las facultades
 * especificados como parámetro.
 * 
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public class IndevalAutorizacionFacultadTagComponent extends HtmlPanelGroup {

	/**
	 * Atributo userInFaculty
	 */
	private String userInFaculty = null;

	/**
	 * Atributo userInAllFaculties
	 */
	private String userInAllFaculties = null;
	/**
	 * Caracter usado para separar las facultades
	 */
	private static final String SEPARADOR_FACULTADES = ",";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponentBase#encodeChildren(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		if (verificarFacultades()) {
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
		super.encodeBegin(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponentBase#encodeChildren(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		super.encodeEnd(context);
	}

	/**
	 * Verifica si el usuario tiene autorización con el o los roles indicados en
	 * la etiqueta.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean verificarFacultades() {

		if (userInFaculty != null && userInAllFaculties != null) {
			throw new RuntimeException("El atributo userInAllFaculties y userInAllFaculties son excluyentes, utilice uno a la vez");
		}

		if (userInFaculty == null && userInAllFaculties == null) {
			throw new RuntimeException("Es requerido proporcionar alguno de los atributos: userInFaculty o  userInAllFaculties");
		}
		String[] facults = null;
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean autorizado = false;
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
		HttpSession session = request.getSession(false);

		SeguridadService seguridadExposedService = SeguridadServiceLocator.obtenerSeguridadExposedService();

		String ticket = (String) session.getAttribute(SeguridadConstants.TICKET_SESION);

		try {
			
			Set<String> facultades = seguridadExposedService.getFacultadesSimples(ticket);
			if (userInFaculty != null) {
				
				autorizado = facultades.contains(userInFaculty);
				
			}

			if (userInAllFaculties != null) {
				facults = userInAllFaculties.split(SEPARADOR_FACULTADES);
				if (facultades != null) {
					autorizado = true;
					for (String f : facults) {
						
						autorizado = facultades.contains(f);
						
						if(!autorizado){
							break;
						}
					}
					
					
				}
			}
		
		} catch (SeguridadException se) {
			Logger log = LoggerFactory.getLogger(this.getClass());
			log.error("Error al verificar facultades en el servicio de seguridad", se);
			String mensajeError = "";
			mensajeError = mensajeError + se.getMessage();
			if (SeguridadConstants.TICKET_EXPIRADO.contains(mensajeError.toLowerCase())||SeguridadConstants.TICKET_DESHABILITADO.contains(mensajeError.toLowerCase())) {
				// El ticket ha expirado
				session.invalidate();
				try {
					ctx.responseComplete();
					response.sendRedirect(request.getContextPath());
				} catch (Exception e) {
					log.error("Ocurrio un error:",e);
				}
			}
		}catch (Exception re) {
			Logger log = LoggerFactory.getLogger(this.getClass());
			log.error("Error al verificar facultades en el servicio de seguridad", re);
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

		return autorizado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIInput#saveState(javax.faces.context.FacesContext)
	 */
	@Override
	public Object saveState(FacesContext facesContext) {
		Object[] values = new Object[3];
		values[0] = super.saveState(facesContext);
		values[1] = userInFaculty;
		values[2] = userInAllFaculties;

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
		userInFaculty = (String) values[1];
		userInAllFaculties = (String) values[2];
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
