package com.indeval.portaldali.presentation.jsf.xls;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.apache.poi.hssf.usermodel.HSSFSheet;

public class XLSRowRenderer extends Renderer {
    public XLSRowRenderer() {
        super();
    }
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        XLSRowComponent rowComp = (XLSRowComponent)component;
        HSSFSheet sheet = rowComp.getCurrentSheet();
        rowComp.setRow( sheet.createRow(rowComp.getNextRow()) );
    }
    
    public boolean getRendersChildren(){
        return true;
    }
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException{
        
    }
    
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException{
        XLSRowComponent rowComp = (XLSRowComponent)component;
        if( rowComp.isRendered() ) {
            List columnas = rowComp.getChildren();
            int numCell = 0;
            for( int i = 0; i < rowComp.getChildCount(); i++ ){
                XLSColumnComponent col = (XLSColumnComponent)columnas.get(i);
                col.setOddRow(rowComp.isOddRow());
                col.setCellNumber(new Integer(numCell));
                col.encodeBegin(context);
                col.encodeChildren(context);
                col.encodeEnd(context);
                if( col.isRendered() ){
                	numCell++;
                }
            }
        }
    }

}
