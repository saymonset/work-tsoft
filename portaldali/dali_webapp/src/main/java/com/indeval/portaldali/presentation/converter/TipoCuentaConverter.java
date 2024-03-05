/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;


/**
 * Implementación de un <code>Converter</code> para acoplar el DTO <code>TipoCuentaDTO</code>
 * a un componente de interfaz de usuario.
 * @author Emigdio Hernández
 *
 */
public class TipoCuentaConverter implements Converter {

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent ui, String value)
			throws ConverterException {
		TipoCuentaDTO dto = new TipoCuentaDTO();
		dto.setId(value);
		return dto;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent ui, Object value)
			throws ConverterException {
		
		String resultado = "-1";
		if( value != null){
			
				resultado = ((TipoCuentaDTO)value).getId();
			
		}
		return resultado;
	}

}
