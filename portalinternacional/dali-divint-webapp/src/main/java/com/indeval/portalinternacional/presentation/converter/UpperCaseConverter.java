/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.apache.commons.lang.StringUtils;

/**
 * Coverter para cambiar los textos a mayusculas principalmente
 * en los reportes donde no hay estilos que nos ayudaen a hacer
 * eso.
 * @author Rafael Ibarra
 * @version 1.0
 */
public class UpperCaseConverter implements Converter {

    public Object getAsObject(FacesContext context, UIComponent component, String value)
            throws ConverterException {
        return StringUtils.defaultString(value);
    }

    public String getAsString(FacesContext context, UIComponent component, Object value)
            throws ConverterException {
        String retorno = null;
        if(value != null) {
            retorno = StringUtils.upperCase(value.toString());
        }
        return retorno;
    }

}
