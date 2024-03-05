package com.indeval.portaldali.presentation.jsf.pdf;

import java.awt.Color;
import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.presentation.util.PDFConstantes;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;

public class PDFColumnRendererDali extends Renderer {

    private static final String BGCOLOR_NONE = "none";

    public void encodeBegin(final FacesContext facesCtx, final UIComponent com)
            throws IOException {
        try {
            final PDFColumnComponentDali pdfCom = (PDFColumnComponentDali) com;
            if (!pdfCom.isRendered()) {
                return;
            }
            pdfCom.getTable().addCell(getFormattedCell(facesCtx, pdfCom));
        } catch (Exception e) {
            logger.error("In encodeBegin()", e);
        }
    }

    private PdfPCell getFormattedCell(final FacesContext facesContext,
            final PDFColumnComponentDali pdfComponent) {
        final Paragraph par = new Paragraph(pdfComponent.getValueAsString(),
                PDFConstantes.CEL_VALUE_FONT);
        final int align = ((Integer) PDFConstantes.alignments.get(pdfComponent
                .getAlign())).intValue();
        par.setAlignment(align);
        final PdfPCell cell = new PdfPCell(par);
        Color bgColor = null; // NOPMD
        String cellBgColor = null; // NOPMD
        if (pdfComponent.getValueExpression("bgcolor") != null) {
            cellBgColor = (String) pdfComponent.getValueExpression("bgcolor")
                    .getValue(facesContext.getELContext());
        }
        if (cellBgColor != null && cellBgColor.length() > 0
                && !cellBgColor.equalsIgnoreCase(BGCOLOR_NONE)
                && cellBgColor.length() == 6) {
            bgColor = new Color(Integer.parseInt(cellBgColor.substring(0, 2),
                    16), Integer.parseInt(cellBgColor.substring(2, 4), 16),
                    Integer.parseInt(cellBgColor.substring(4), 16));
        }

        if (bgColor == null) {
            if (pdfComponent.isOddRow().booleanValue()) {
                bgColor = PDFConstantes.CEL_ODD_COLOR;
            } else {
                bgColor = PDFConstantes.CEL_PAIR_COLOR;
            }
        }

        cell.setBackgroundColor(bgColor);

        cell.setHorizontalAlignment(align);
        cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
        cell.setColspan(pdfComponent.getCollspan().intValue());
        cell.setBorderColor(PDFConstantes.CEL_BORDER_COLOR);
        cell.setBorderWidth(1);
        return cell;
    }

    private static final Logger logger = LoggerFactory.getLogger(PDFColumnRendererDali.class);  // NOPMD

}
