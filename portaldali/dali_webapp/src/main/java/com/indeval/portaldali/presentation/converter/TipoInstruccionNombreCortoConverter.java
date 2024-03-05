/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 24/02/2008
 */
package com.indeval.portaldali.presentation.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;


/**
 * Implementación de un Converter para transformar un objeto de tipo {@link TipoInstruccionDTO} en su
 * representación en cadena y viceversa.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public class TipoInstruccionNombreCortoConverter implements Converter {
	
	/*
	 * (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value)
			throws ConverterException {
		
		TipoInstruccionDTO dto = new TipoInstruccionDTO();
		dto.setNombreCorto(value);
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
			resultado = ((TipoInstruccionDTO)value).getNombreCorto();
		}
		return resultado;
	}
	
}
