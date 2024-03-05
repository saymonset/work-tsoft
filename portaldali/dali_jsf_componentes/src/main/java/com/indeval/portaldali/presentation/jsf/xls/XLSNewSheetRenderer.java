package com.indeval.portaldali.presentation.jsf.xls;


import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

public class XLSNewSheetRenderer extends Renderer {

    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        XLSNewSheetComponent xlsComponent = ((XLSNewSheetComponent)component);
        String name = null; 
        if( xlsComponent.getValue() != null ){
        	name = xlsComponent.getValue().toString();
        }
        xlsComponent.createNewSheet(name);
    }
}
