package com.indeval.portaldali.presentation.jsf.converters;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.ConverterTag;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

public class SimpleDateConverterTag extends ConverterTag {

    /**
     * 
     */
    public static final long serialVersionUID = 1L;

    private String pattern;
    private String converterID = "com.indeval.SimpleDate";
    @SuppressWarnings("deprecation")
    public SimpleDateConverterTag() {
        super();
        setConverterId(converterID);
    }
    public String getPattern() {
        return pattern;
    }
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
    
    protected Converter createConverter() throws JspException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        Converter converter;
        if (UIComponentTag.isValueReference(converterID)){
            ValueBinding vb = facesContext.getApplication().createValueBinding(converterID);
            converter = application.createConverter((String)vb.getValue(facesContext)); 
        }
        else{
            converter = application.createConverter(converterID);
        }
        ((SimpleDateConverter)converter).setPattern(pattern);
        return converter;
    }
    
}
