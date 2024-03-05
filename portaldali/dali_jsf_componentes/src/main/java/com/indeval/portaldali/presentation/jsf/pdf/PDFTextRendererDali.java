package com.indeval.portaldali.presentation.jsf.pdf;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.presentation.util.PDFConstantes;
import com.lowagie.text.Paragraph;

public class PDFTextRendererDali extends Renderer {
    
    private Logger logger = LoggerFactory.getLogger(PDFDocumentRendererDali.class);

    public PDFTextRendererDali() {
        super();
    }

    public void encodeBegin(FacesContext facesContext, UIComponent component)
        throws IOException {
        logger.debug("rendering text");
        try {
            PDFTextComponentDali pdfComponent = (PDFTextComponentDali) component;
            String valor = "";
            if (pdfComponent.getValue() != null){
                if( pdfComponent.getConverter() != null ){
                    valor = pdfComponent.getConverter().getAsString(facesContext, pdfComponent, pdfComponent.getValue());
                }else{
                    valor = pdfComponent.getValue().toString();
                }
            }
            Paragraph par = new Paragraph(valor, PDFConstantes.TEXT_FONT);
            int align = ((Integer) PDFConstantes.alignments.get(pdfComponent.getAlign()))
                    .intValue();
            par.setAlignment(align);

            pdfComponent.getDocument().add(par);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }
}
