package com.indeval.portaldali.presentation.jsf.xls;

import org.apache.myfaces.shared_impl.taglib.UIComponentTagBase;

public class XLSNewSheetTag extends UIComponentTagBase {

    public void release() {
        super.release();
    }
    
    public String getComponentType() {
        return "indeval.XLSNewSheet";
    }

    public String getRendererType() {
        return "indeval.XLSNewSheet";
    }

}
