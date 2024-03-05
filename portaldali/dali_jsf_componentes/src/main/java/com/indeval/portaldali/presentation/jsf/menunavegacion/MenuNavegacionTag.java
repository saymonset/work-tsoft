/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * MenuNavegacionTag.java
 * Mar 12, 2008
 */
package com.indeval.portaldali.presentation.jsf.menunavegacion;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;

import com.indeval.portaldali.presentation.jsf.menunavegacion.constants.MenuNavegacionConstants;

/**
 * Implementación de un Tag Handler para el componente del menú de navegación
 * del portal DALI.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class MenuNavegacionTag extends UIComponentELTag {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.webapp.UIComponentELTag#setProperties(javax.faces.component.UIComponent)
	 */
	@Override
	protected void setProperties(UIComponent component) {
		
		super.setProperties(component);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.webapp.UIComponentTagBase#getComponentType()
	 */
	@Override
	public String getComponentType() {

		return MenuNavegacionConstants.MENU_NAVEGACION_COMPONENT_TYPE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.webapp.UIComponentTagBase#getRendererType()
	 */
	@Override
	public String getRendererType() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.webapp.UIComponentELTag#release()
	 */
	@Override
	public void release() {

		super.release();
	}
}
