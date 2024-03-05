/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * CalendarBean.java
 * Apr 26, 2008
 */
package com.indeval.portaldali.presentation.controller.common;

import org.richfaces.model.CalendarDataModel;



/**
 * Implementación de un backing bean para proporcionar el modelo de datos del calendario
 * de rich faces a las pantallas del portal DALI.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public class CalendarBean extends ControllerBase {
	
	/** Modelo de datos para el calendario de RichFaces */
	private CalendarDataModel calendarDataModel = null;

	/**
	 * Obtiene el campo calendarDataModel
	 * @return  calendarDataModel
	 */
	public CalendarDataModel getCalendarDataModel() {
		return calendarDataModel;
	}

	/**
	 * Asigna el campo calendarDataModel
	 * @param calendarDataModel el valor de calendarDataModel a asignar
	 */
	public void setCalendarDataModel(CalendarDataModel calendarDataModel) {
		this.calendarDataModel = calendarDataModel;
	}
	
}
