package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

import org.apache.myfaces.shared_impl.taglib.UIComponentTagBase;

public class PDFFilterTagDali extends UIComponentTagBase {

    private String label;
    private String value;
    
    public void release() {
        super.release();
        label = null;
        value = null;
    }
    
    public String getComponentType() {
        return "indeval.PDFFilter";
    }

    public String getRendererType() {
        return "indeval.PDFFilter";
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        
        PDFFilterComponentDali filterComponent = (PDFFilterComponentDali)component; 
        
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        
        if (value != null) {
            if (UIComponentTag.isValueReference(value)) {
                filterComponent.setValueBinding("value", application.createValueBinding(value));
            } else {
                filterComponent.setValue(value);
            }
        }else{
            filterComponent.setValue("");
        }
        
        if (label != null) {
            if (UIComponentTag.isValueReference(label)) {
                filterComponent.setLabel(application.createValueBinding(label).getValue(context).toString());
            } else {
                filterComponent.setLabel(label);
            }
        }
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
