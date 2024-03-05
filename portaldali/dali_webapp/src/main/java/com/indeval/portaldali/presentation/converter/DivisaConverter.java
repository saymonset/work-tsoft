/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 7, 2007
 *
 */
package com.indeval.portaldali.presentation.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.dto.DivisaDTO;


/**
 * Implementación de un <code>Converter</code> para acoplar el DTO
 * <code>DivisaDTO</code> a un componente de interfaz de usuario.
 * 
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class DivisaConverter implements Converter {

	/*
	 * (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent ui, String value)
			throws ConverterException {
		DivisaDTO divisaDTO = new DivisaDTO();
		divisaDTO.setId(NumberUtils.toLong(value,-1));
		return divisaDTO;		
	}

	/*
	 * (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent ui, Object value)
			throws ConverterException {
		String resultado = "";
		if (value != null) {
			resultado = String.valueOf(((DivisaDTO) value).getId());
		}
		return resultado;				
	}

}
