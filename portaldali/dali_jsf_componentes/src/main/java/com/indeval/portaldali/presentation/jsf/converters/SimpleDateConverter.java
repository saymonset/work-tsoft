package com.indeval.portaldali.presentation.jsf.converters;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleDateConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(SimpleDateConverter.class);
	private final String DEFAULT_PATTERN = "dd/MM/yyyy";
	private String pattern;
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        if( value == null || value.equals("") ) {
            return null;
        }
        SimpleDateFormat sdc = new SimpleDateFormat(getPattern());
        try {
            return sdc.parse( value );
        }catch (Exception e) {
            throw new ConverterException();
        }
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException {
        if( value == null ) {
            return "";
        }
        if( !(value instanceof java.util.Date || value instanceof java.lang.Long) ) {
            return "";
        }
              
        SimpleDateFormat sdc = new SimpleDateFormat(getPattern());
        
        try {
            return sdc.format(value);
        }catch (Exception e) {
            throw new ConverterException();
        }
    }

    public String getPattern() {
    	if( pattern == null || pattern.equals("") ){
    		return DEFAULT_PATTERN;
    	}
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    
    public static void main(String[] args) {
        SimpleDateConverter sdc = new SimpleDateConverter(  );
        sdc.setPattern("dd/MM/yy");
        logger.debug(String.valueOf(sdc.getAsObject(null, null, "01-01-2005")));
        logger.debug(String.valueOf(sdc.getAsObject(null, null, "01-06-2005")));
        logger.debug(sdc.getAsString(null, null, new Date()));
    }

}
