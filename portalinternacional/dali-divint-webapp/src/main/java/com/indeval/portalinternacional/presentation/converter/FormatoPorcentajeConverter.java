package com.indeval.portalinternacional.presentation.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;

public class FormatoPorcentajeConverter implements Converter{

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object object)throws ConverterException {
		String res = null;
		if(object!=null){
			res =  Constantes.DECIMAL_FORMAT_PORCENTAJE_VIEW.format(((Number)object).doubleValue());			
		}		
		return res;
	}

}
