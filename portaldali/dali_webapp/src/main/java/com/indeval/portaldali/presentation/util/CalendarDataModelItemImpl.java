/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * CalendarDataModelItemImpl.java
 * Apr 26, 2008
 */
package com.indeval.portaldali.presentation.util;

import org.richfaces.model.CalendarDataModelItem;


/**
 * Implementación del modelo de datos de un elemento del calendario de RichFaces
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class CalendarDataModelItemImpl implements CalendarDataModelItem {

	/** Los datos relacionados con el elemento */
	private Object data;

	/** La clase de estilo asociada con el elemento */
	private String styleClass;

	/** El tooltip que desplegar el elemento */
	private Object toolTip;

	/** El día representado por este elemento */
	private int day;

	/** Indica si el elemento estar habilitado o no */
	private boolean enabled = true;

	/**
	 * Obtiene el campo data
	 * 
	 * @return data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Asigna el campo data
	 * 
	 * @param data
	 *            el valor de data a asignar
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * Obtiene el campo styleClass
	 * 
	 * @return styleClass
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * Asigna el campo styleClass
	 * 
	 * @param styleClass
	 *            el valor de styleClass a asignar
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * Obtiene el campo toolTip
	 * 
	 * @return toolTip
	 */
	public Object getToolTip() {
		return toolTip;
	}

	/**
	 * Asigna el campo toolTip
	 * 
	 * @param toolTip
	 *            el valor de toolTip a asignar
	 */
	public void setToolTip(Object toolTip) {
		this.toolTip = toolTip;
	}

	/**
	 * Obtiene el campo day
	 * 
	 * @return day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Asigna el campo day
	 * 
	 * @param day
	 *            el valor de day a asignar
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * Obtiene el campo enabled
	 * 
	 * @return enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Asigna el campo enabled
	 * 
	 * @param enabled
	 *            el valor de enabled a asignar
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richfaces.model.CalendarDataModelItem#hasToolTip()
	 */
	public boolean hasToolTip() {

		return getToolTip() != null;
	}
}
