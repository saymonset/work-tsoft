/**
 * 
 */
package com.indeval.portaldali.presentation.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;


/**
 * @author Too
 *
 */
public class TipoTenenciaConverter implements Converter {

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value)
			throws ConverterException {
		
		TipoTenenciaDTO tipoTenenciaDTO = new TipoTenenciaDTO();
		
		tipoTenenciaDTO.setIdTipoCuenta(NumberUtils.toLong(value,-1));
		
		return tipoTenenciaDTO;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value)
			throws ConverterException {
		
		String resultado = "-1";
		if( value != null){
			
			resultado = String.valueOf(((TipoTenenciaDTO)value).getIdTipoCuenta());
			
		}
		return resultado;
	}

}
