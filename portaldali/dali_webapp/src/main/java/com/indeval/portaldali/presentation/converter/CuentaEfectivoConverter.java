/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 10, 2007
 *
 */
package com.indeval.portaldali.presentation.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;


/**
 * 
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class CuentaEfectivoConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent ui, String value) {
		CuentaEfectivoDTO dto = new CuentaEfectivoDTO();
		dto.setNumeroCuenta(value);
		return dto;
	}

	public String getAsString(FacesContext context, UIComponent ui, Object value) {
		String resultado = "";
		if (value != null) {
			resultado = String.valueOf(((CuentaEfectivoDTO) value).getNumeroCuenta());
		}
		return resultado;
	}

}
