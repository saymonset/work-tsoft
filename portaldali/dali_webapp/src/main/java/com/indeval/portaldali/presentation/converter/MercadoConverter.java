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

import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;


/**
 * Implementación de un Converter para transformar un objeto de tipo {@link MercadoDTO} en su
 * representación en cadena y viceversa.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 */
public class MercadoConverter implements Converter {
	
	/*
	 * (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value)
			throws ConverterException {
		
		MercadoDTO mercadoDTO = new MercadoDTO();
		mercadoDTO.setId(NumberUtils.toLong(value,DaliConstants.VALOR_COMBO_TODOS));
		return mercadoDTO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value)
			throws ConverterException {
		
		String resultado = "";
		if(value != null) {
			resultado = String.valueOf(((MercadoDTO)value).getId());
		}
		return resultado;
	}
	
}
