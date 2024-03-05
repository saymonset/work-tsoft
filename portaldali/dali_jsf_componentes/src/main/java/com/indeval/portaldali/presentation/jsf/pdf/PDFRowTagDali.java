package com.indeval.portaldali.presentation.jsf.pdf;


import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

import org.apache.myfaces.shared_impl.taglib.UIComponentTagBase;

public class PDFRowTagDali extends UIComponentTagBase {
    private String rendered;
    public void release() {
        super.release();
        
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        
        PDFRowComponentDali rowComponent = (PDFRowComponentDali)component; 
        
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        
        if (rendered != null) {
            if (UIComponentTag.isValueReference(rendered)) {
                rowComponent.setValueBinding("rendered", application.createValueBinding(rendered));
            } else {
                rowComponent.setRendered((new Boolean(rendered)).booleanValue());
            }
        }else {
            rowComponent.setRendered(true);
        }
    }
    
    public String getComponentType() {
        return "indeval.PDFRow";
    }

    public String getRendererType() {
        return "indeval.PDFRow";
    }

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

}
