/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 03/03/2008
 */
package com.indeval.portalinternacional.presentation.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementación de un {@link Converter} para transformar los valores numricos a cadenas
 * con formato de moneda
 * @author Emigdio
 * @version 1.0
 */
public class FormatoMonedaChileConverter implements Converter {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	DecimalFormat df = new DecimalFormat("$ ###,##0.####");
	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext ctx, UIComponent ui, String valor)
			throws ConverterException {
		BigDecimal numero = BigDecimal.ZERO;
        df.setParseBigDecimal(true);
        if(StringUtils.isNotBlank(valor)) {
            try{
                numero = (BigDecimal)df.parse(valor);
            }catch (Exception e) {
                log.error("Error al convertir el numero con le formater: [" + valor + "]", e);
                try{
                    numero = new BigDecimal(valor);
                }catch (Exception ex) {
                    log.error("Error al convertir el numero como BigDecimal: [" + valor + "]", ex);
                }
            }
            if(numero != null) {
                numero = numero.setScale(2, RoundingMode.HALF_EVEN);
            }
        }
		return numero;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext ctx, UIComponent ui, Object objeto)
			throws ConverterException {
		String res = "";
		if(objeto instanceof BigDecimal){
            BigDecimal numero = (BigDecimal) objeto;
            numero = numero.setScale(2, RoundingMode.HALF_EVEN);
			res =  df.format(numero);
		}
		  
		return res;
		
	}

}
