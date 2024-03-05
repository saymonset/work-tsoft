/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.indeval.portaldali.middleware.services.common.util.FormatUtil;


/**
 * Implementación de un {@link Converter} para transformar los valores numricos a cadenas
 * con formato de número entero sin decimales y con separador de miles
 * @author Emigdio
 * @version 1.0
 */
public class FormatoNumeroEnteroConverter implements Converter {

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext ctx, UIComponent ui, String valor)
			throws ConverterException {
		Number numero = null;
		try{
			numero = FormatUtil.FORMATO_ENTERO.parse(valor);
		}catch (Exception e) {
			// TODO: handle exception
		}
		if(numero != null){
			numero = new Long(numero.longValue());
		}
		
		return numero;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext ctx, UIComponent ui, Object objeto)
			throws ConverterException {
		String res = null;
		
		if(objeto!=null){
			try{
				res =  FormatUtil.numberIntToString(Double.parseDouble(objeto.toString()));
			}catch(Exception e){
				
			}
			
		}
		
		
		return  res;
	}

}
