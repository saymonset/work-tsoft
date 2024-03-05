/**
 * 2H Software SA de CV
 * 
 * Sistema de consulta de Estado de Cuenta - Indeval
 * 
 */
package com.indeval.portaldali.presentation.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;


/**
 * Implementación de un Converter para transformar un objeto de tipo {@link TipoOperacionDTO} en su
 * representación en cadena y viceversa.
 * 
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public class TipoOperacionConverter implements Converter {
	
	/*
	 * (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value)
			throws ConverterException {
		
		TipoOperacionDTO dto = new TipoOperacionDTO();
		dto.setId(NumberUtils.toLong(value,-1L));
		
		return dto;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value)
			throws ConverterException {
		
		String resultado = "";
		if(value != null) {
			resultado = String.valueOf(((TipoOperacionDTO)value).getId());
		}
		return resultado;
	}
	
}
