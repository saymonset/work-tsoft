/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 03/03/2008
 */
package com.indeval.portaldali.presentation.converter;

import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;

/**
 * Implementación de un {@link Converter} para transformar los valores numricos a cadenas
 * con formato de moneda
 * @author Emigdio
 * @version 1.0
 */
public class FormatoDecimalConverter implements Converter {
	DecimalFormat df = new DecimalFormat(DaliConstants.FORMATO_DECIMAL);
	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext ctx, UIComponent ui, String valor)
			throws ConverterException {
		//método no implementado
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext ctx, UIComponent ui, Object objeto)
			throws ConverterException {
		String res = null;
		if(objeto!=null){
			res =  df.format((Double.parseDouble(objeto.toString())));
		}
		  
		return res;
		
	}

}
