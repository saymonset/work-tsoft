/**
 * Portal DALI
 * 
 * 2H Software SA
 *
 * MenuNavegacionConstants.java 
 *
 * Creado el: Sep 24, 2008
 */
package com.indeval.portaldali.presentation.jsf.menunavegacion.constants;

/**
 * Constantes para el componente del menú de navegación.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public interface MenuNavegacionConstants {
	
	/** Llave para identificar el menú de navegación del usuario en sesión */
	String MENU_NAVEGACION_SESSION_KEY = "menuUsuario";

	/**
	 * El nombre de la familia de componentes JSF a la que pertenece el
	 * componente del menú de navegación
	 */
	String MENU_NAVEGACION_FAMILY = "menuNavegacionFamily";

	/**
	 * Contiene el nombre del atributo al que se har referencia en el
	 * componente del menú de navegación
	 */
	String MENU_NAVEGACION_RAIZ_ATTRIBUTE = "menuNavegacionRaiz";

	/** El tipo de componente del menú de navegación */
	String MENU_NAVEGACION_COMPONENT_TYPE = "PortalDaliMenuNavegacionTag";
}
