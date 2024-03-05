package com.indeval.portaldali.presentation.jsf.pdf;
import java.net.URL;

import javax.faces.context.FacesContext;

import com.indeval.portaldali.presentation.jsf.converters.TitleDateConverter;
import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.ExtendedDocument;
import com.indeval.portaldali.presentation.util.PDFConstantes;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDFHeaderManagerDali extends PdfPageEventHelper {
    

    /** The PdfPTable that will be added as the footer of the document. */
    protected PdfPTable headerTable;
     
    public PDFHeaderManagerDali( PDFDocumentComponentDali component ) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
        headerTable = new PdfPTable(2);
        headerTable.setSpacingAfter(0);
        headerTable.setSpacingBefore(0);
          
        float width = ((ExtendedDocument)component.getDocument()).getPageSize().getRight() - 
                      ((ExtendedDocument)component.getDocument()).getPageSize().getLeft() - 
                        PDFConstantes.DOC_BORDER_RIGHT - PDFConstantes.DOC_BORDER_LEFT;
        
        headerTable.setTotalWidth( width );
		if (component.getImageResource() != null) {
			try {
				URL url = this.getClass().getResource(component.getImageResource());
				Image logo = Image.getInstance(url);
				logo.setWidthPercentage(90);
				PdfPCell cell = new PdfPCell(logo);
				cell.setFixedHeight(45);
				cell.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
				cell.setVerticalAlignment(Paragraph.ALIGN_TOP);
				cell.setColspan(1);
				cell.setBorderWidth(0);
				cell.setBorderColorBottom(PDFConstantes.DOC_COLOR_INDEVAL);
				cell.setBorderWidthBottom(1.5f);
				headerTable.addCell(cell);
			} catch (Exception ex) {
				logger.error("Error al poner la imagen", ex);
			}
		}
		if (component.getDocumentTitle() != null) {
			Phrase parInst = new Phrase(component.getDocumentTitle(), PDFConstantes.DOC_HEADER_FONT);
			PdfPCell cell = new PdfPCell(parInst);
			cell.setPaddingBottom(6);
			cell.setHorizontalAlignment(Paragraph.ALIGN_RIGHT);
			cell.setVerticalAlignment(Paragraph.ALIGN_BOTTOM);
			cell.setColspan(1);
			cell.setBorderWidth(0);
			cell.setBorderColorBottom(PDFConstantes.DOC_COLOR_INDEVAL);
			cell.setBorderWidthBottom(1.5f);
			headerTable.addCell(cell);
		}
		float paddingTop = 5;
        if (component.getInstitucion() != null) {
            Phrase parInst = new Phrase(component.getInstitucion(),PDFConstantes.TEXT_FONT);
            PdfPCell cell = new PdfPCell(parInst);
			cell.setPaddingTop(paddingTop);
            cell.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
            cell.setVerticalAlignment(Paragraph.ALIGN_TOP);
            cell.setColspan(component.getFecha() == null ? 2 : 1);
            cell.setBorderWidth(0);
            headerTable.addCell(cell);
        }
        if (component.getFecha() != null) {
            TitleDateConverter dtConv = new TitleDateConverter();
            String stFecha = dtConv.getAsString(FacesContext.getCurrentInstance(), component, component.getFecha());
            Paragraph parFecha = new Paragraph(stFecha,PDFConstantes.TEXT_FONT);
            parFecha.setAlignment(Paragraph.ALIGN_RIGHT);
            PdfPCell cell = new PdfPCell(parFecha);
			cell.setPaddingTop(paddingTop);
            cell.setHorizontalAlignment(Paragraph.ALIGN_RIGHT);
            cell.setVerticalAlignment(Paragraph.ALIGN_TOP);
            cell.setColspan(component.getInstitucion() == null ? 2 : 1);
            cell.setBorderWidth(0);
            headerTable.addCell(cell);
        }
    }

    public void onStartPage(PdfWriter writer, Document document) {
    	try {				
			writer.flush();
			Paragraph par = new Paragraph(" ");
			par.setSpacingAfter(0);
			par.setSpacingBefore(0);
			document.add(par);
		} catch (DocumentException e) {
			e.printStackTrace();
		}	    	
    }
        
    public void onEndPage(PdfWriter writer, Document document) {
        float top = document.top() + 39;
		headerTable.writeSelectedRows(0, -1, document.leftMargin(), top, writer.getDirectContent());
    }
}