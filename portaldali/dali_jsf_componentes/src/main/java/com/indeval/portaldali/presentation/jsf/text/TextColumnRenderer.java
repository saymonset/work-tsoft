package com.indeval.portaldali.presentation.jsf.text;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextColumnRenderer extends Renderer {
	
	private final Logger logger = LoggerFactory.getLogger(TextColumnRenderer.class);
    
    public TextColumnRenderer(){
        super();
        
    }
    
    public boolean getRendersChildren(){
        return true;
    }
    
    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException {
        TextColumnComponent textcomp = (TextColumnComponent)component; 
        if( textcomp.isRendered() ){
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        ServletOutputStream out = response.getOutputStream();
        String value = "";
        int numSpaces = 0;
        
        Boolean escape=Boolean.TRUE;
        
        if(textcomp.getAttributes().get("escape")!=null){
        	escape=new Boolean(textcomp.getAttributes().get("escape").toString());
        }	
        
        
        if( textcomp.getConverter() != null ) {
        	try{
        		value = textcomp.getConverter().getAsString(context, component, textcomp.getValue());
        	}catch ( ConverterException e ){
        		value = textcomp.getValue().toString();
        	}
        }else {
            if( textcomp.getValue() != null ) {           	
                value = textcomp.getValue().toString();
                logger.debug("value ["+value+"]");               
            }
        }    
        
        if( value == null ){
        	value = "";
        }
        
        if(!escape){
        	value =value.replace("\\t", "\t");
        }        
        logger.debug(" value ["+value+"] length ["+value.length()+"] intValue ["+textcomp.getSize().intValue()+"] Align ["+textcomp.getAlign()+"]");
        
        String align=textcomp.getAlign();
        
        if (value.length() > textcomp.getSize().intValue() && !align.equals("NONE")) {
            value = value.substring(0,textcomp.getSize().intValue());
        }else {
            numSpaces = textcomp.getSize().intValue() - value.length();  
        }
        
        
        if(align.equals("LEFT") ) {
            for( int i = 0; i < numSpaces; i++ ) {
                value = value+" ";
            }
        }else  if(align.equals("RIGHT") ) {
            for( int i = 0; i < numSpaces; i++ ) {
                value = " " + value;
            }
        }else  if(align.equals("CENTER") ) {
            for( int i = 0; i < numSpaces; i++ ) {
                value = " " + value;
                i++;
                if( i < numSpaces ) {
                    value = value + " ";
                }
            }
        }
        logger.debug("value to print ["+value+"]");  
        out.print(value);
    }
}
}
