/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 03/03/2008
 */
package com.indeval.portaldali.presentation.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.indeval.portaldali.middleware.dto.TipoValorDTO;


/**
 * Implementación de un converter para transformar un DTO de tipo {@link TipoValorDTO} en
 * una representación en cadena y viceversa.
 * Tomando como base la clave de tipo de valor
 * @author José Antonio Huizar Moreno
 * @version 1.0
 */
public class TipoValorClaveTipoValorConverter implements Converter {

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value)
			throws ConverterException {
		
		TipoValorDTO tipoValorDTO = new TipoValorDTO();
		
		tipoValorDTO.setClaveTipoValor(value);
		
		return tipoValorDTO;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value)
			throws ConverterException {
		
		String resultado = null;
		
		if(value != null) {
			resultado = ((TipoValorDTO)value).getClaveTipoValor();
		}
		
		return resultado;
	}

}
