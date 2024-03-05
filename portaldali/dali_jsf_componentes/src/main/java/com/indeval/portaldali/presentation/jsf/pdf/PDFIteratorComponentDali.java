package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.IDocument;

public class PDFIteratorComponentDali extends UIOutput implements IPDFComponentDali {
    
    private String var;

    public PDFIteratorComponentDali(){
        this.setRendererType("indeval.PDFIterator");
        
    }
    
    public String getFamily() {
        return "indeval.PDFIterator";
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[2];
        state[0] = super.saveState(context);
        state[1] = getVar();
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        setVar( (String)stateArr[1] );
    }
    
    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public IDocument getDocument() {
        return ((IPDFComponentDali)this.getParent()).getDocument();
    }


}
