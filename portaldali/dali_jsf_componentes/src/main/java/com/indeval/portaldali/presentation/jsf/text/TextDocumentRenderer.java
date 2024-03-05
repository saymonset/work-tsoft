package com.indeval.portaldali.presentation.jsf.text;

import java.io.IOException;
import java.io.PrintWriter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.presentation.jsf.converters.TitleDateConverter;
import com.indeval.portaldali.presentation.util.Utils;

    
public class TextDocumentRenderer extends Renderer {
//	private final Logger logger = LoggerFactory.getLogger(TextDocumentRenderer.class);
	private ServletOutputStream out;
    
    public TextDocumentRenderer() {
        super();
    }
    
    public void encodeBegin(FacesContext facesContext, UIComponent component)
            throws IOException {
        TextDocumentComponent doc = (TextDocumentComponent)component;
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        out = response.getOutputStream();
        
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Disposition", "attachment; filename=" + doc.getDocumentName() + ".txt");
        response.setContentType("text/plain");
        
        out.print(doc.getDocumentTitle());
        out.print(Utils.BRWin);
        out.print(Utils.BRWin);
        TitleDateConverter dtconv = new TitleDateConverter();
        if( doc.getFecha()!=null ) {
            out.print(dtconv.getAsString(facesContext, component, doc.getFecha()));
            out.print(Utils.BRWin);
        }
        if( doc.getInstitucion()!=null ) {
            out.print("Institución:" + doc.getInstitucion() );
            out.print(Utils.BRWin);
        }
        out.print(Utils.BRWin);
    }
    
    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        ServletOutputStream out = response.getOutputStream();
        out.print(Utils.BRWin);
        out.flush();
//        out.close();
    }
    
    public boolean getRendersChildren() {
        return true;
    }
   
}
