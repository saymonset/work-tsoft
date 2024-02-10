/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 03/03/2008
 */
package com.indeval.portalinternacional.presentation.converter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.indeval.portaldali.persistence.util.constants.DaliConstants;

/**
 * Implementación de un {@link Converter} para transformar los valores numricos a cadenas
 * con formato de moneda
 * @author Emigdio
 * @version 1.0
 */
public class FormatoDecimalConverter implements Converter {
	// Cambio Multidivisas
	DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
	DecimalFormat df = new DecimalFormat(DaliConstants.FORMATO_DECIMAL, symbols);
	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext ctx, UIComponent ui, String valor)
		throws ConverterException {
		if (valor == null || valor.trim().isEmpty()) {
			return null;
		}

		Number number;
		try {
			String input = valor.replace(",", "");
			String regex = "^\\d+(?:\\.\\d{1,2})?$";

			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(input);

			number = matcher.matches() ? df.parse(input) : null;
		} catch (ParseException e) {
			throw new ConverterException("No se pudo convertir la cadena a número.", e);
		}
		return number;
	}
// Fin Cambio Multidivisas
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
