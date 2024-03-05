package com.indeval.portaldali.presentation.jsf.pdf;

import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.PDFExtendedCell;
import com.indeval.portaldali.presentation.util.PDFConstantes;

public class PDFGColumnRendererDali extends Renderer {

    public PDFGColumnRendererDali() {
        super();

    }

    public void encodeBegin(FacesContext facesContext, UIComponent component)
            throws IOException {
        try {
            PDFGColumnComponentDali pdfComponent = (PDFGColumnComponentDali) component;
            if(!pdfComponent.isRendered()) {
                return;
            }
            pdfComponent.setCell(new PDFExtendedCell());
            Integer align = (Integer) PDFConstantes.alignments.get(pdfComponent.getAlign());
            Integer valign = (Integer) PDFConstantes.valignments.get(pdfComponent.getValign());
            pdfComponent.getCell().setHorizontalAlignment(align.intValue());
            pdfComponent.getCell().setVerticalAlignment(valign.intValue());
            pdfComponent.getCell().setColspan( pdfComponent.getCollspan().intValue());
            if( pdfComponent.getBorderColor() != null ) {
                String colorValue = pdfComponent.getBorderColor().substring(1);
                if( colorValue.length() == 6 ) {
                    Color color = new Color( Integer.parseInt(colorValue, 16) );
                    pdfComponent.getCell().setBorderColor( color );
                }
            }else {
                pdfComponent.getCell().setBorderColor( PDFConstantes.GRID_BORDER_COLOR);
            }
            pdfComponent.getCell().setBorderWidth( pdfComponent.getBorder().intValue() );
            if( pdfComponent.getBgcolor() != null ) {
                String colorValue = pdfComponent.getBgcolor().substring(1);
                if( colorValue.length() == 6 ) {
                    Color color = new Color( Integer.parseInt(colorValue, 16) );
                    pdfComponent.getCell().setBackgroundColor(color);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws java.io.IOException {
        PDFGColumnComponentDali pdfComponent = (PDFGColumnComponentDali) component;
        if(!pdfComponent.isRendered()) {
            return;
        }
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

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {
        
        PDFGColumnComponentDali pdfComponent = (PDFGColumnComponentDali) component;
        if(!pdfComponent.isRendered()) {
            return;
        }
        pdfComponent.getTable().addCell(pdfComponent.getCell());
    }

}
