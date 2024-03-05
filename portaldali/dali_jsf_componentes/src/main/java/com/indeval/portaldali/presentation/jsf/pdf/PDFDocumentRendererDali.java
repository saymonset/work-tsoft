package com.indeval.portaldali.presentation.jsf.pdf;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.ExtendedDocument;
import com.indeval.portaldali.presentation.util.PDFConstantes;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;

public class PDFDocumentRendererDali extends Renderer {

    public PDFDocumentRendererDali() {
        super();
    }

    private void encodeFooter(PDFDocumentComponentDali component) {
        HeaderFooter footer = new HeaderFooter(new Phrase("P\u00E1gina ",
                PDFConstantes.DOC_FOOTER_FONT), new Phrase("",
                PDFConstantes.DOC_FOOTER_FONT));
        footer.setBorderWidth(0);
        footer.setBorderColorTop(PDFConstantes.DOC_COLOR_INDEVAL);
        footer.setBorderWidthTop(1.5f);
        footer.setAlignment(HeaderFooter.ALIGN_RIGHT);
        ((ExtendedDocument)component.getDocument()).setFooter(footer);
    }

    public void encodeBegin(FacesContext facesContext, UIComponent component)
            throws IOException {
        PDFDocumentComponentDali pdfComponent = (PDFDocumentComponentDali) component;
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Disposition", "attachment; filename=" + pdfComponent.getDocumentName() + ".pdf");
        response.setContentType("application/pdf");
        try {
            Rectangle pageSize = null;
            if (pdfComponent.getLegal().booleanValue()) {
                if (pdfComponent.getLandscape().booleanValue()) {
                    pageSize = PageSize.LEGAL.rotate();
                } else {
                    pageSize = PageSize.LEGAL;
                }
            } else {
                if (pdfComponent.getLandscape().booleanValue()) {
                    pageSize = PageSize.LETTER.rotate();
                } else {
                    pageSize = PageSize.LETTER;
                }
            }
            
            pdfComponent.setDocument(new ExtendedDocument(pageSize,
                    PDFConstantes.DOC_BORDER_LEFT,
                    PDFConstantes.DOC_BORDER_RIGHT,
                    PDFConstantes.DOC_BORDER_TOP,
                    PDFConstantes.DOC_BORDER_BOTTOM));
            encodeFooter(pdfComponent);
            pdfComponent.getWriter().setPageEvent(new PDFHeaderManagerDali(pdfComponent));
            ((ExtendedDocument)pdfComponent.getDocument()).open();           
            
        } catch (Exception e) {
        	e.printStackTrace();
            ((ExtendedDocument)pdfComponent.getDocument()).close();
        }
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {
        PDFDocumentComponentDali pdfComponent = (PDFDocumentComponentDali) component;
        try {
            ((ExtendedDocument)pdfComponent.getDocument()).close();
            ServletOutputStream out = ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).getOutputStream();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ((ExtendedDocument)pdfComponent.getDocument()).close();
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
