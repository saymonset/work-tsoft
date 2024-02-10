/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portalinternacional.presentation.converter;

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
public class FormatoNumeroConverter implements Converter {

    /**
     * Formato de numrico para las cantidades enteras de posición
     */
    public static DecimalFormat FORMATO_ENTERO = new DecimalFormat("###,##0");
	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext ctx, UIComponent ui, String valor)
			throws ConverterException {
		Number numero = null;
		try{
			numero = FORMATO_ENTERO.parse(valor);
		}catch (Exception e) {
			// TODO: handle exception
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
		        res =  FORMATO_ENTERO.format((Double.parseDouble(objeto.toString())));
		    }catch (Exception e) {
                // TODO: handle exception
            }
			
		}
		  
		return res;
		
	}

}
