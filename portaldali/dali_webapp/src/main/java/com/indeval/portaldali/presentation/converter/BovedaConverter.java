/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;


/**
 * Implementación de un <code>Converter</code> para acoplar el DTO
 * <code>BovedaDTO</code> a un componente de interfaz de usuario.
 * 
 * @author Emigdio Hernández
 * 
 */
public class BovedaConverter implements Converter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent ui, String value)
			throws ConverterException {
		BovedaDTO dto = new BovedaDTO();
		dto.setId(NumberUtils.toLong(value,-1));
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent ui, Object value)
			throws ConverterException {

		String resultado = "-1";
		if (value != null) {
			resultado = String.valueOf(((BovedaDTO) value).getId());
		}
		return resultado;
	}

}
