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

import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.dto.TipoMensajeDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;


/**
 * Implementación de un Converter para transformar un objeto de tipo {@link TipoMensajeDTO} en su
 * representación en cadena y viceversa.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public class TipoMensajeConverter implements Converter {
	
	/*
	 * (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value)
			throws ConverterException {
		
		TipoMensajeDTO dto = new TipoMensajeDTO();
		dto.setIdTipoMensaje(NumberUtils.toInt(value,DaliConstants.VALOR_COMBO_TODOS));
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
			resultado = String.valueOf(((TipoMensajeDTO)value).getIdTipoMensaje());
		}
		return resultado;
	}
	
}
