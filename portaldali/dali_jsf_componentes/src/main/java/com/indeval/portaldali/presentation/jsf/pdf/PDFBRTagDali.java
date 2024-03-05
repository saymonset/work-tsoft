package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_impl.taglib.UIComponentTagBase;

public class PDFBRTagDali extends UIComponentTagBase{
    public String getComponentType() {
        return "indeval.PDFText";
    }

    public String getRendererType() {
        return "indeval.PDFText";
    }



    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        PDFTextComponentDali textComponent = (PDFTextComponentDali)component; 
        textComponent.setValue(" ");
        textComponent.setAlign("LEFT");

    }
    
    public void release() {
        super.release();
    }
}
