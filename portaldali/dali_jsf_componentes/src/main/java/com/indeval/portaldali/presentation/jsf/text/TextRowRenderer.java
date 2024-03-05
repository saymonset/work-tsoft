package com.indeval.portaldali.presentation.jsf.text;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.indeval.portaldali.presentation.util.Utils;


public class TextRowRenderer extends Renderer {
    
    public boolean getRendersChildren(){
        return true;
    }
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException{
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        ServletOutputStream out = response.getOutputStream();
        out.print(Utils.BRWin);
    }
    

}
