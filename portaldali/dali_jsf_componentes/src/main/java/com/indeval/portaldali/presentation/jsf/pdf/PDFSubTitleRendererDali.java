package com.indeval.portaldali.presentation.jsf.pdf;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.presentation.util.PDFConstantes;
import com.lowagie.text.Paragraph;

public class PDFSubTitleRendererDali extends Renderer {
    Logger logger = LoggerFactory.getLogger(PDFSubTitleRendererDali.class); 

    public PDFSubTitleRendererDali() {
        super();
    }

    public void encodeBegin(FacesContext facesContext, UIComponent component)
            throws IOException {
        try{
            PDFSubTitleComponentDali subTitleComponent = (PDFSubTitleComponentDali) component;
            String valor = "";
            if( subTitleComponent.getValue() != null ){
                if( subTitleComponent.getConverter() != null ){
                    valor = subTitleComponent.getConverter().getAsString(facesContext, subTitleComponent, subTitleComponent.getValue());
                }else {
                    valor = subTitleComponent.getValue().toString();
                }
            }
            Paragraph par = new Paragraph( valor, PDFConstantes.SUBTITLE_TEXT_FONT );
            par.setSpacingAfter(5);
            subTitleComponent.getDocument().add(par);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
