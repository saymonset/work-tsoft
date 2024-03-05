package com.indeval.portaldali.presentation.jsf.pdf;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.presentation.util.PDFConstantes;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;

public class PDFGRowRendererDali extends Renderer {

    private Logger logger = LoggerFactory.getLogger( PDFGRowComponentDali.class );
    
    public PDFGRowRendererDali() {
        super();
    }
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        logger.debug("encoding row");
        
        
    }
    
    public boolean getRendersChildren(){
        return true;
    }
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException{
        PDFGRowComponentDali rowComp = (PDFGRowComponentDali)component;
        encodeMissingCols( rowComp );
    }
    
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException{
        PDFGRowComponentDali rowComp = (PDFGRowComponentDali)component;
        if( rowComp.isRendered() ) {
            List columnas = rowComp.getChildren();
            for( int i = 0; i < rowComp.getChildCount(); i++ ){
                UIComponent col = (UIComponent)columnas.get(i);
                if( col.isRendered() ) {
                    col.encodeBegin(context);
                    col.encodeChildren(context);
                    col.encodeEnd(context);
                }
            }
        }
    }
    
    public void encodeMissingCols( PDFGRowComponentDali rowComp ){
        int extraCollspan = rowComp.getMaxCols() - rowComp.getRealNumCols();
        if (extraCollspan > 0) {
            PdfPCell cell = new PdfPCell(new Paragraph(" "));
            
            if( rowComp.getBorderColor() != null ) {
                String colorValue = rowComp.getBorderColor().substring(1);
                if( colorValue.length() == 6 ) {
                    Color color = new Color( Integer.parseInt(colorValue, 16) );
                    cell.setBorderColor( color );
                }
            }else {
                cell.setBorderColor( PDFConstantes.GRID_BORDER_COLOR);
            }
            cell.setBorder(PdfPCell.RECTANGLE);
            cell.setBorderWidth( rowComp.getBorder().intValue() );
            cell.setColspan(extraCollspan);
            rowComp.getTable().addCell(cell);
        }
    }
    
    
}
