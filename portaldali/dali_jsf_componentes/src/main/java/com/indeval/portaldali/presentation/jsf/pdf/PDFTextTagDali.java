package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

import org.apache.myfaces.shared_impl.taglib.UIComponentTagBase;

public class PDFTextTagDali extends UIComponentTagBase {
    
    private String align;
    private String value;
    
    public void release() {
        super.release();
        align = null;
        value = null;
    }
    
    public String getComponentType() {
        return "indeval.PDFText";
    }

    public String getRendererType() {
        return "indeval.PDFText";
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        
        PDFTextComponentDali textComponent = (PDFTextComponentDali)component; 
        
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        
        if (value != null) {
            if (UIComponentTag.isValueReference(value)) {
                textComponent.setValueBinding("value", application.createValueBinding(value));
            } else {
                textComponent.setValue(value);
            }
        }else{
            textComponent.setValue("");
        }
        
        if (align != null) {
            if (UIComponentTag.isValueReference(align)) {
                textComponent.setAlign(application.createValueBinding(value).getValue(context).toString());
            } else {
                textComponent.setAlign(align);
            }
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }
}
