package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.IDocument;

public class PDFFilterComponentDali extends UIOutput implements IPDFComponentDali {
    private String label = "";
    
    public PDFFilterComponentDali(){
        this.setRendererType("indeval.PDFFilter");
    }
    
    public String getFamily() {
        return "indeval.PDFFilter";
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[2];
        state[0] = super.saveState(context);
        state[1] = getLabel();
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        setLabel((String)stateArr[1]);
    }

    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }

    public IDocument getDocument() {
        return ((IPDFComponentDali)this.getParent()).getDocument();
    }
    
}
