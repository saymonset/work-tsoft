package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.component.UIOutput;

import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.IDocument;

public class PDFSubTitleComponentDali extends UIOutput implements IPDFComponentDali {

    public PDFSubTitleComponentDali(){
        this.setRendererType("indeval.PDFSubTitle");
    }
    
    public String getFamily() {
        return "indeval.PDFSubTitle";
    }
    
    public IDocument getDocument() {
        return  ((IPDFComponentDali)getParent()).getDocument();
    }

}
