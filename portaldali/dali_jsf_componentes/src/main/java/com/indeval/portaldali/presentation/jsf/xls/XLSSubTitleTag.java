package com.indeval.portaldali.presentation.jsf.xls;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

import org.apache.myfaces.shared_impl.taglib.UIComponentTagBase;

public class XLSSubTitleTag extends UIComponentTagBase {

    private String value = new String();
    private String pattern;
    
    public void release() {
        super.release();
        value = null;
        pattern = null;
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        
        XLSSubTitleComponent stComponent = (XLSSubTitleComponent)component; 
        
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        
        if (value != null) {
            if (UIComponentTag.isValueReference(value)) {
                stComponent.setValueBinding("value", application.createValueBinding(value));
            } else {
                stComponent.setValue(value);
            }
        }
        
        if (pattern != null) {
            if (UIComponentTag.isValueReference(pattern)) {
                stComponent.setValueBinding("pattern", application.createValueBinding(pattern));
            } else {
                stComponent.setPattern(pattern);
            }
        }
        
        
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComponentType() {
        return "indeval.XLSSubTitle";
    }

    public String getRendererType() {
        return "indeval.XLSSubTitle";
    }

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
