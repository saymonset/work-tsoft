/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 03/03/2008
 */
package com.indeval.portalinternacional.presentation.converter;

import java.math.BigInteger;
import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * Implementación de un {@link Converter} para transformar los valores numricos a cadenas
 * con formato de moneda
 * @author Emigdio
 * @version 1.0
 */
public class FormatoBigIntegerSepararMilesConverter implements Converter {
	DecimalFormat df = new DecimalFormat("###,##0");
	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext ctx, UIComponent ui, String valor)
			throws ConverterException {
		Number numero = null;
		BigInteger res = null;
		try{
			numero = df.parse(valor);
		}catch (Exception e) {
			try{
				res = new BigInteger(valor);
			}catch (Exception ex) {
				
			}
		}
		if(numero != null){
			res = new BigInteger(numero.toString());
		}
		
		return res;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext ctx, UIComponent ui, Object objeto)
			throws ConverterException {
		String res = null;
		if(objeto!=null){
			res =  df.format((Long.parseLong(objeto.toString())));
		}
		  
		return res;
		
	}

}
