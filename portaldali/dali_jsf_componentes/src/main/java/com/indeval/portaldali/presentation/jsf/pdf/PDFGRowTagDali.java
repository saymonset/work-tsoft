package com.indeval.portaldali.presentation.jsf.pdf;

import org.apache.myfaces.shared_impl.taglib.UIComponentTagBase;

public class PDFGRowTagDali extends UIComponentTagBase {
    public void release() {
        super.release();
        
    }
    
    public String getComponentType() {
        return "indeval.PDFGridRow";
    }

    public String getRendererType() {
        return "indeval.PDFGridRow";
    }

}
