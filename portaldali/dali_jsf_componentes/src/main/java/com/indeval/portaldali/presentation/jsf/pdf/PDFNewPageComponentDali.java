package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.component.UIOutput;

import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.IDocument;

public class PDFNewPageComponentDali extends UIOutput implements IPDFComponentDali{

    public PDFNewPageComponentDali(){
        this.setRendererType("indeval.PDFNewPage");
    }
    
    public String getFamily() {
        return "indeval.PDFNewPage";
    }
    
    public IDocument getDocument(){
        return ((IPDFComponentDali)getParent()).getDocument();
    }
   
}
