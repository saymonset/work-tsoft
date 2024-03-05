/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * MenuNavegacionHelper.java
 * Mar 12, 2008
 */
package com.indeval.portaldali.presentation.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.middleware.ejb.SeguridadServiceLocator;
import com.bursatec.seguridad.middleware.service.SeguridadException;
import com.bursatec.seguridad.middleware.service.SeguridadService;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.presentation.jsf.menunavegacion.vo.ElementoMenu;

/**
 * Helper para ayudar a filtrar el menú de navegación de acuerdo a los roles y
 * facultades simples de un usuario del portal.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class MenuNavegacionHelper {
	
	private final static Logger logger = LoggerFactory.getLogger(MenuNavegacionHelper.class);
	
	/**
	 * Obtiene el menú de navegación de un usuario de acuerdo a los privilegios
	 * del mismo.
	 * 
	 * @param elementoRaiz
	 *            el elemento raíz del menú de navegación.
	 * @param usuario
	 *            el usuario en cuesti el cual inici sesión en el sistema.
	 * @param ticket
	 *            el ticket otorgado por el mecanismo de seguridad del sistema.
	 * @return Un elemento raíz que representa el menú de navegación al cual
	 *         tiene acceso el usuario.
	 */
	@SuppressWarnings("unchecked")
	public static ElementoMenu obtenerMenuNavegacionDeUsuario(ElementoMenu elementoRaiz, String ticket) {

		SeguridadService seguridadExposedService = SeguridadServiceLocator.obtenerSeguridadExposedService();
		List<String> roles = null;
		Set<String> facultades = null;
		ElementoMenu menuNavegacion = null;

		try {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			
			roles = seguridadExposedService.getNombresRolesPorPerfil(ticket);			
			facultades = seguridadExposedService.getFacultadesSimples(ticket);
			logger.debug("Roles para el tiket ["+ticket+"]  ["+roles+"]");
			logger.debug("Facultades para el tiket ["+ticket+"]  ["+facultades+"]");		
			
			HashMap<String, String> mapFacultades= new  HashMap<String, String>();
			HashMap<String, String> mapRoles= new  HashMap<String, String>();
			
			if (roles != null){
				for (String elem : roles) {
					mapRoles.put(elem, elem);				
				}
			}
			
			if(facultades!=null){				
				for (String elem : facultades) {
					mapFacultades.put(elem, elem);
				}		
			}	
			
			session.removeAttribute(SeguridadConstants.ROLES_SESION);
			session.setAttribute(SeguridadConstants.ROLES_SESION, mapRoles);
			session.removeAttribute(SeguridadConstants.FACULTADES_SESION);
			session.setAttribute(SeguridadConstants.FACULTADES_SESION, mapFacultades);
			
			
			
		}  catch (SeguridadException e) {
			logger.error("Ocurrió un error al iniciar sesión en el sistema.", e);
		}  catch (Exception e) {
			logger.error("Error al invocar el EJB de seguridad.", e);
		}

		menuNavegacion = filtrarMenuNavegacion(elementoRaiz, roles, facultades);

		return menuNavegacion;
	}

	/**
	 * Filtra el menú de navegación del portal DALI de acuerdo a los roles y
	 * facultades de un usuario del portal.
	 * 
	 * @param elementoRaiz
	 *            el elemento que contiene la definición del menú del portal.
	 * @param roles
	 *            una lista con el nombre de los roles asignados al usuario
	 *            actual.
	 * @param facultades
	 *            un conjunto con los nombres de las facultades simples
	 *            asignadas al usuario actual.
	 * @return un elemento que contiene la definición del menú para el usuario
	 *         actual.
	 */
	public static ElementoMenu filtrarMenuNavegacion(ElementoMenu elementoRaiz, List<String> roles, Set<String> facultades) {

		ElementoMenu menuFiltrado = null;

		for (String rol : elementoRaiz.getRoles()) {
			if (roles.contains(rol)) {
				menuFiltrado = elementoRaiz.clonar();
				break;
			}
		}

		if (menuFiltrado == null && elementoRaiz.getFacultades() != null && facultades != null) {
			for (String facultad : elementoRaiz.getFacultades()) {
				if (facultades.contains(facultad)) {
					menuFiltrado = elementoRaiz.clonar();
					break;
				}
			}
		}

		if (menuFiltrado != null && elementoRaiz.getSubElementosMenu() != null) {
			for (ElementoMenu elementoMenu : elementoRaiz.getSubElementosMenu()) {
				ElementoMenu submenuFiltrado = filtrarMenuNavegacion(elementoMenu, roles, facultades);
				if (submenuFiltrado != null) {
					if (menuFiltrado.getSubElementosMenu() == null) {
						menuFiltrado.setSubElementosMenu(new ArrayList<ElementoMenu>());
					}
					menuFiltrado.getSubElementosMenu().add(submenuFiltrado);
				}
			}
		}

		return menuFiltrado;
	}
}
