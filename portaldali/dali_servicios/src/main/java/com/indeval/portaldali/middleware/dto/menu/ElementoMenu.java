/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ElementoMenu.java
 * Mar 10, 2008
 */
package com.indeval.portaldali.middleware.dto.menu;

import java.io.Serializable;
import java.util.List;

/**
 * DTO que representa un elemento del menú de navegación
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class ElementoMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	/** El identificador del elemento del menú */
	private String id = null;

	/** La etiqueta con la que se presenta un elemento en el menú de navegación */
	private String etiqueta = null;

	/** El url al cual dirigir este elemento del menú de navegación */
	private String url = null;

	/**
	 * El url del cono que ser presentado junto con la etiqueta del menú de
	 * navegación
	 */
	private String urlIcono = null;

	/**
	 * La lista de los nombres de los roles que pueden tener acceso a este
	 * elemento del menú
	 */
	private List<String> roles = null;

	/**
	 * La lista de los nombres de las facultades que pueden tener acceso a este
	 * elemento del menú
	 */
	private List<String> facultades = null;

	/**
	 * Una lista con los sub-elementos que confoman a este elemento del menú de
	 * navegación
	 */
	private List<ElementoMenu> subElementosMenu = null;

	/**
	 * Obtiene el campo id
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Asigna el campo id
	 * 
	 * @param id
	 *            el valor de id a asignar
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Obtiene el campo subElementosMenu
	 * 
	 * @return subElementosMenu
	 */
	public List<ElementoMenu> getSubElementosMenu() {
		return subElementosMenu;
	}

	/**
	 * Asigna el campo subElementosMenu
	 * 
	 * @param subElementosMenu
	 *            el valor de subElementosMenu a asignar
	 */
	public void setSubElementosMenu(List<ElementoMenu> subElementosMenu) {
		this.subElementosMenu = subElementosMenu;
	}

	/**
	 * Obtiene el campo etiqueta
	 * 
	 * @return etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * Asigna el campo etiqueta
	 * 
	 * @param etiqueta
	 *            el valor de etiqueta a asignar
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * Obtiene el campo url
	 * 
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Asigna el campo url
	 * 
	 * @param url
	 *            el valor de url a asignar
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Obtiene el campo urlIcono
	 * 
	 * @return urlIcono
	 */
	public String getUrlIcono() {
		return urlIcono;
	}

	/**
	 * Asigna el campo urlIcono
	 * 
	 * @param urlIcono
	 *            el valor de urlIcono a asignar
	 */
	public void setUrlIcono(String urlIcono) {
		this.urlIcono = urlIcono;
	}

	/**
	 * Obtiene el campo roles
	 * 
	 * @return roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * Asigna el campo roles
	 * 
	 * @param roles
	 *            el valor de roles a asignar
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	/**
	 * Obtiene el campo facultades
	 * 
	 * @return facultades
	 */
	public List<String> getFacultades() {
		return facultades;
	}

	/**
	 * Asigna el campo facultades
	 * 
	 * @param facultades
	 *            el valor de facultades a asignar
	 */
	public void setFacultades(List<String> facultades) {
		this.facultades = facultades;
	}

	/**
	 * Clona este elemento del menú sin clonar sus subelementos.
	 * 
	 * @return una copia exacta de este elemento del menú pero sin sus subelementos.
	 */
	public ElementoMenu clonar() {
		
		ElementoMenu elemento = new ElementoMenu();
		
		elemento.setEtiqueta(etiqueta);
		elemento.setFacultades(facultades);
		elemento.setId(id);
		elemento.setRoles(roles);
		elemento.setUrl(url);
		elemento.setUrlIcono(urlIcono);
		
		return elemento;
	}
}
