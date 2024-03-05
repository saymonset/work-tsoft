package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.IDocument;

public class PDFTextComponentDali extends UIOutput implements IPDFComponentDali {
    private String align = "LEFT";
    
    public PDFTextComponentDali(){
        this.setRendererType("indeval.PDFText");
    }
    
    public String getFamily() {
        return "indeval.PDFText";
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[2];
        state[0] = super.saveState(context);
        state[1] = getAlign();
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        setAlign((String)stateArr[1]);
    }
    
    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        if( align != null && ( align.toUpperCase().equals("CENTER") ||
                align.toUpperCase().equals("RIGHT") ||
                align.toUpperCase().equals("LEFT"))){
            this.align = align.toUpperCase();
        }
    }

    public IDocument getDocument() {
        return ((IPDFComponentDali)this.getParent()).getDocument();
    }
    
}
