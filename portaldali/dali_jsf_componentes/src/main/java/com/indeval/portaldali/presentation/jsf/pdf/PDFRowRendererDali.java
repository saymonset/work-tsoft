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

public class PDFRowRendererDali extends Renderer {

    @SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger( PDFRowRendererDali.class );
    
    public PDFRowRendererDali() {
        super();
    }
    
    public boolean getRendersChildren(){
        return true;
    }
    
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException{
        PDFRowComponentDali rowComp = (PDFRowComponentDali) component;
        if( rowComp.isRendered() ) {
            encodeMissingCols( rowComp );
        }
    }
    
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException{
        PDFRowComponentDali rowComp = (PDFRowComponentDali)component;
        if( rowComp.isRendered() ) {
            List columnas = rowComp.getChildren();
            for( int i = 0; i < rowComp.getChildCount(); i++ ){
                UIComponent col = (UIComponent)columnas.get(i);
                col.encodeBegin(context);
                col.encodeChildren(context);
                col.encodeEnd(context);
                
            }
        }
    }
    
    private void encodeMissingCols(PDFRowComponentDali rowComp) {
        Color bgColor = PDFConstantes.EMPTY_CELL_BG_PAIR_COLOR;
        if (rowComp.isOddRow().booleanValue()) {
            bgColor = PDFConstantes.EMPTY_CELL_BG_ODD_COLOR;
        }
        int extraCollspan = getExtraCollspan(rowComp.getMaxCols(), rowComp.getRealNumCols());
               
        logger.trace("extraCollspan: " + extraCollspan);
        
        if (extraCollspan > 0) {        
            PdfPCell cell = new PdfPCell(new Paragraph(" "));
            cell.setBackgroundColor(bgColor);
            cell.setBorderColor(PDFConstantes.EMPTY_CELL_BORDER_COLOR);
            cell.setBorder(PdfPCell.RECTANGLE);
            cell.setBorderWidth(1);
            cell.setColspan(extraCollspan);
            rowComp.getTable().addCell(cell);
        }
    }

    private Integer getExtraCollspan(int maxCols, int realNumCols) {
        logger.trace("MaxCols: " + maxCols); // 5
        logger.trace("RealNumCols: " + realNumCols); // 7
        final Integer extraCollspan;
        if (realNumCols % maxCols == 0) {
            extraCollspan = 0;
        } else {
            extraCollspan = realNumCols-(realNumCols % maxCols);
        }
        logger.trace("extraCollspan: " + extraCollspan);
        return extraCollspan;
    }

}
