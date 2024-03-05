/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 03/03/2008
 */
package com.indeval.portaldali.presentation.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;

/**
 * Implementación de un Converter para transformar un objeto de tipo {@link Date} en su
 * representación en cadena y viceversa.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public class FormatoFechaCortoConverter implements Converter {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat(DaliConstants.FORMATO_FECHA_CORTO);
	/*
	 * (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value)
			throws ConverterException {
		
		java.util.Date dto = null;
		try{
			dto = sdf.parse(value);
		}catch (ParseException e) {
			
		}
		
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
			
				resultado = sdf.format(value);
			
		}
		return resultado;
	}
	
}
