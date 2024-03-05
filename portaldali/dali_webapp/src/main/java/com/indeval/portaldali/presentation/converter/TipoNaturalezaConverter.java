/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;


/**
 * Implementación de un <code>Converter</code> para acoplar el DTO <code>TipoNaturalezaDTO</code>
 * a un componente de interfaz de usuario.
 * @author Emigdio Hernández
 *
 */
public class TipoNaturalezaConverter implements Converter {

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent ui, String value)
			throws ConverterException {
		TipoNaturalezaDTO dto = new TipoNaturalezaDTO();
		dto.setId(value);
		return dto;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent ui, Object value)
			throws ConverterException {
		String resultado = "";
		if( value != null){
			
			resultado = ((TipoNaturalezaDTO)value).getId();
			
		}
		return resultado;
	}

}
