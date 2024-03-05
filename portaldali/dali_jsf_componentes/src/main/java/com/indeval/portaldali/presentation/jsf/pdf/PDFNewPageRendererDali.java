package com.indeval.portaldali.presentation.jsf.pdf;


import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.ExtendedDocument;

public class PDFNewPageRendererDali extends Renderer {

    
    private Logger logger = LoggerFactory.getLogger( PDFDocumentRendererDali.class );
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        try{
            IPDFComponentDali pdfComponent = ((IPDFComponentDali)component); 
            ((ExtendedDocument)pdfComponent.getDocument()).newPage();
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        
    }

}
