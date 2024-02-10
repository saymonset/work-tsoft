/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.converter;

import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * Converter para decimales largos
 * @author Pablo Balderas
 */
public class FormatoDecimalLargoConverter implements Converter {
	DecimalFormat df = new DecimalFormat("#####0.0##########");
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
