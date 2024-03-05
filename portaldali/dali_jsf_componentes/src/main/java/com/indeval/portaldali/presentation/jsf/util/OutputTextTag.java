package com.indeval.portaldali.presentation.jsf.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.shared_impl.taglib.html.HtmlOutputTextTagBase;

public class OutputTextTag extends HtmlOutputTextTagBase {

    private final String COMPONENT_TYPE = "indeval.outputText";
    private final String RENDER_TYPE = "indeval.outputText";
    
    private String length;
    private String error;
    private String pattern;
    private String header;
    
    public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getComponentType() {
        return COMPONENT_TYPE;
    }

    public String getRendererType() {
        return RENDER_TYPE;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();
        if( length != null && length.trim().length()!= 0){
	        if( isValueReference(length) ){
	        	ValueBinding vbLength = context.getApplication().createValueBinding(length);
	        	component.setValueBinding("length", vbLength);
	        }
	        else{
	        	component.getAttributes().put("length", new Integer(length));
	        }
        }
        if( error != null && error.trim().length()!= 0){
	        if( isValueReference(error) ){
	        	ValueBinding vbError = context.getApplication().createValueBinding(error);
	        	component.setValueBinding("error", vbError);
	        }
	        else{
	        	component.getAttributes().put("error", new Boolean(error));
	        }
    	}
        if( pattern != null && pattern.trim().length()!= 0){
	        if( isValueReference(pattern) ){
	        	ValueBinding vbPattern = context.getApplication().createValueBinding(pattern);
	        	component.setValueBinding("pattern", vbPattern);
	        }
	        else{
	        	component.getAttributes().put("pattern", pattern);
	        }
    	}
        if( header != null && header.trim().length()!= 0){
	        if( isValueReference(header) ){
	        	ValueBinding vbHeader = context.getApplication().createValueBinding(header);
	        	component.setValueBinding("header", vbHeader);
	        }
	        else{
	        	component.getAttributes().put("header", header);
	        }
    	}
        
        
    }

    public void release() {
        super.release();
        length = null;
        error = null;
        pattern = null;
        header = null;
        
    }

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}



    
    
}
