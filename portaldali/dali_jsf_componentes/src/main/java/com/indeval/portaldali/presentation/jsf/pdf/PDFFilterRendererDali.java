package com.indeval.portaldali.presentation.jsf.pdf;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.presentation.util.PDFConstantes;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

public class PDFFilterRendererDali extends Renderer {

    
    private Logger logger = LoggerFactory.getLogger( PDFDocumentRendererDali.class );
    
    
    public PDFFilterRendererDali(){
        super();
    }
    
    public void encodeBegin(FacesContext facesContext, UIComponent component)
        throws IOException {
        logger.debug("rendering Filter");
        try{
            PDFFilterComponentDali pdfComponent = (PDFFilterComponentDali) component;
            String valor = "";
            if( pdfComponent.getValue() != null ){
                if( pdfComponent.getConverter() != null ){
                    valor = pdfComponent.getConverter().getAsString(facesContext, pdfComponent, pdfComponent.getValue());
                }else{
                    valor = pdfComponent.getValue().toString();
                }
            }
            Phrase pValue = new Phrase( valor, PDFConstantes.FILTER_VALUE_FONT );
            Phrase pLabel = new Phrase( pdfComponent.getLabel() + ": ", PDFConstantes.FILTER_LABEL_FONT );
            Paragraph par = new Paragraph();
            par.add(pLabel);
            par.add(pValue);
            par.setSpacingAfter(0);
            par.setSpacingBefore(0);
            par.setKeepTogether(true);
            pdfComponent.getDocument().add(par);
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
