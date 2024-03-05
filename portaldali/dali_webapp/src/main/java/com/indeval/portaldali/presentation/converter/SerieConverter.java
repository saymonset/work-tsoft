/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.SerieDTO;


/**
 * Implementación de un <code>Converter</code> para acoplar el DTO
 * <code>SerieDTO</code> a un componente de interfaz de usuario.
 * 
 * @author Emigdio Hernández
 * 
 */
public class SerieConverter implements Converter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent ui, String value)
			throws ConverterException {
		SerieDTO dto = new SerieDTO();
		dto.setSerie(StringUtils.isNotBlank(value) ? value.toUpperCase() : value);
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

		String resultado = "";
		if (value != null && ((SerieDTO) value).getSerie()!=null) {
			resultado = String.valueOf(((SerieDTO) value).getSerie().toUpperCase());
		}
		return resultado;
	}

}
