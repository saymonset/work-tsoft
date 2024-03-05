package com.indeval.portaldali.presentation.jsf.pdf;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

public class PDFIteratorRendererDali extends Renderer {
    public PDFIteratorRendererDali() {

    }

    public void encodeBegin(FacesContext facesContext, UIComponent component)
            throws IOException {
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {
    }

    public boolean getRendersChildren(){
        return true;
    }
    
    public void encodeChildren(FacesContext context, UIComponent component) throws java.io.IOException{
        PDFIteratorComponentDali itComponent = (PDFIteratorComponentDali) component;
        Object[] datos = null;
        Object valor = itComponent.getValue(); 
        if( valor.getClass().isArray() ){
            datos = (Object[])valor;
        }else{
            
            if( valor instanceof java.util.List ) {
                datos = ((List)valor).toArray();
            }else if ( valor instanceof java.util.Collection ){
                datos = ((Collection)valor).toArray();
            }else if(  valor instanceof java.util.Map ){
                datos = ((Map)valor).values().toArray();
            }
        }
        if( datos == null ){
            datos = new Object[1];
            datos[0] = valor;
        }
        
        if (datos != null && itComponent.getChildCount() > 0) {
            Map reqMap  =  context.getExternalContext().getRequestMap();
            for( int i = 0; i < datos.length; i++ ){
                reqMap.put( itComponent.getVar(), datos[i] );
                List children = itComponent.getChildren();
                Iterator it = children.iterator();
                while( it.hasNext() ) {
                    UIComponent row = (UIComponent)it.next();
                    row.encodeBegin(context);
                    row.encodeChildren(context);
                    row.encodeEnd(context);
                }
            }
            reqMap.remove(itComponent.getVar() );
        }
    }
    
}
