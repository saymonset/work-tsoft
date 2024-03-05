package com.indeval.portaldali.presentation.jsf.pdf;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.indeval.portaldali.presentation.util.PDFConstantes;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;

public class PDFGTableRendererDali extends Renderer {
    
    
    public PDFGTableRendererDali() {
        super();
    }

    public void encodeBegin(FacesContext facesContext, UIComponent component)
            throws IOException {
        
        PDFGTableComponentDali tableComp = (PDFGTableComponentDali)component;
        
        List children = tableComp.getChildren();
        int maxCols = 0;
        for( int i = 0; i < tableComp.getChildCount(); i++ ) {
            int cellsInRow = 0;
            PDFGRowComponentDali rowComp = (PDFGRowComponentDali)children.get(i);
            rowComp.setBorder(tableComp.getBorder());
            rowComp.setBorderColor(tableComp.getBordercolor());
            List columnComponents = rowComp.getChildren();
            for( int j = 0; j < rowComp.getChildCount(); j++ ) {
                PDFGColumnComponentDali col = (PDFGColumnComponentDali)columnComponents.get(j);
                col.setBorder(tableComp.getBorder());
                col.setBorderColor(tableComp.getBordercolor());
                cellsInRow = cellsInRow + col.getCollspan().intValue();
            }
            maxCols = Math.max( cellsInRow, maxCols );
        }
        for( int i = 0; i < tableComp.getChildCount(); i++ ) {
            PDFGRowComponentDali rowComp = (PDFGRowComponentDali)children.get(i);
            rowComp.setMaxCols(maxCols);
        }
        
        if( tableComp.getCellwidths() != null ) {
            int cellWidthsNumber = tableComp.getCellwidths().length;
            int totalPercent = 0;
            int percentRestante;
            if( maxCols > cellWidthsNumber ) {
                int[] widthColumnas = new int[maxCols];
                for ( int i = 0; i < cellWidthsNumber; i++ ) {
                    totalPercent += tableComp.getCellwidths()[i];
                    widthColumnas[i] = tableComp.getCellwidths()[i];
                }
                percentRestante = 100 - totalPercent;
                if( percentRestante > 0 ) {
                     int percentPerCol = percentRestante /( maxCols + cellWidthsNumber );
                     for( int i = cellWidthsNumber; i < maxCols; i++ ) {
                         widthColumnas[i] = percentPerCol;
                     }
                }
                tableComp.setCellwidths(widthColumnas);
            }
        }
        
        tableComp.setTable( new PdfPTable( maxCols ) );
        tableComp.getTable().setSpacingAfter(0);
        tableComp.getTable().setSpacingBefore(0);
        int align = ((Integer)PDFConstantes.alignments.get( tableComp.getAlign() )).intValue();
        tableComp.getTable().setHorizontalAlignment(align);
        if( tableComp.getWidthPercentage() != null ) {
            tableComp.getTable().setWidthPercentage(tableComp.getWidthPercentage().floatValue());
        }
        if( tableComp.getCellwidths() != null ) {
            try {
                tableComp.getTable().setWidths(tableComp.getCellwidths());
            }catch( DocumentException e ) {
                e.printStackTrace();
            }
        }
        
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {
        PDFGTableComponentDali tableComp = (PDFGTableComponentDali)component;
        try {
            tableComp.getDocument().add(tableComp.getTable());
        }catch( DocumentException e ) {
            e.printStackTrace();
        }
       
    }

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws java.io.IOException {
        if (component.getChildCount() > 0) {
            Iterator it = component.getChildren().iterator();
            while (it.hasNext()) {
                UIComponent child = (UIComponent) it.next();
                child.encodeBegin(context);
                child.encodeChildren(context);
                child.encodeEnd(context);
            }
        }
    }
}
