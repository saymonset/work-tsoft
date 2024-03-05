package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

import org.apache.myfaces.shared_impl.taglib.UIComponentTagBase;

public class PDFSubTitleTagDali extends UIComponentTagBase {

    public String value = new String();
    
    public void release() {
        super.release();
        value = null;
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        
        PDFSubTitleComponentDali stComponent = (PDFSubTitleComponentDali)component; 
        
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        
        if (value != null) {
            if (UIComponentTag.isValueReference(value)) {
                stComponent.setValueBinding("value", application.createValueBinding(value));
            } else {
                stComponent.setValue(value);
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
        return "indeval.PDFSubTitle";
    }

    public String getRendererType() {
        return "indeval.PDFSubTitle";
    }

}
