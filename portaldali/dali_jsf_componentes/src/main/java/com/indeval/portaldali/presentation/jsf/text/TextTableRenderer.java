package com.indeval.portaldali.presentation.jsf.text;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.indeval.portaldali.presentation.util.Utils;


public class TextTableRenderer extends Renderer {

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        HttpServletResponse response = (HttpServletResponse) context
                .getExternalContext().getResponse();
        ServletOutputStream out = response.getOutputStream();
        TextTableComponent tablecomp = (TextTableComponent) component;
        List rowChildren = tablecomp.getChildren();
        for (int j = 0; j < tablecomp.getChildCount(); j++) {
            TextRowComponent rowComp = (TextRowComponent) rowChildren.get(j);
            if( rowComp.hasHeaders() ) {
                List columnChildren = rowComp.getChildren();
                for (int k = 0; k < rowComp.getChildCount(); k++) {
                    TextColumnComponent colComp = (TextColumnComponent) columnChildren.get(k);
                    if( colComp.isRendered() ){
                    String value = "";
                    int numSpaces = 0;
                    if (colComp.getHeader() != null) {
                        value = colComp.getHeader().toString();
                    }
                    if (value.length() > colComp.getSize().intValue()) {
                        value = value.substring(0, colComp.getSize().intValue());
                    } else {
                        numSpaces = colComp.getSize().intValue() - value.length();
                    }
                    for (int i = 0; i < numSpaces; i++) {
                        value = " " + value;
                        i++;
                        if (i < numSpaces) {
                            value = value + " ";
                        }
                    }
                    out.print(value);
                }
                }
                out.print(Utils.BRWin);
            }
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        HttpServletResponse response = (HttpServletResponse) context
                .getExternalContext().getResponse();
        ServletOutputStream out = response.getOutputStream();
        TextTableComponent tablecomp = (TextTableComponent) component;
        List rowChildren = tablecomp.getChildren();
        for (int j = 0; j < tablecomp.getChildCount(); j++) {
            TextRowComponent rowComp = (TextRowComponent) rowChildren.get(j);
            if( rowComp.hasFooters() ) {
                List columnChildren = rowComp.getChildren();
                for (int k = 0; k < rowComp.getChildCount(); k++) {
                    TextColumnComponent colComp = (TextColumnComponent) columnChildren
                            .get(k);
                    String value = "";
                    int numSpaces = 0;
                    if (colComp.getFooter() != null) {
                        if (colComp.getConverter() != null) {
                        	try{
                        		value = colComp.getConverter().getAsString(context,
                        				component, colComp.getFooter());
                        	}catch ( ConverterException e ){
                        		value = colComp.getFooter().toString();
                        	}
                        } else {
                            value = colComp.getFooter().toString();
                        }
                    }
                    if (value.length() > colComp.getSize().intValue()) {
                        value = value.substring(0, colComp.getSize().intValue());
                    } else {
                        numSpaces = colComp.getSize().intValue() - value.length();
                    }
                    if (colComp.getAlign().equals("LEFT")) {
                        for (int i = 0; i < numSpaces; i++) {
                            value = value + " ";
                        }
                    }
                    if (colComp.getAlign().equals("RIGHT")) {
                        for (int i = 0; i < numSpaces; i++) {
                            value = " " + value;
                        }
                    }
                    if (colComp.getAlign().equals("CENTER")) {
                        for (int i = 0; i < numSpaces; i++) {
                            value = " " + value;
                            i++;
                            if (i < numSpaces) {
                                value = value + " ";
                            }
                        }
                    }
                    out.print(value);
                }
                out.print(Utils.BRWin);
            }
        }
//        out.print(Utils.BRWin);
    }

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null)
            throw new NullPointerException("context");
        if (component == null)
            throw new NullPointerException("component");

        TextTableComponent ttComp = (TextTableComponent) component;
        Object[] datos = null;
        Object valor = ttComp.getValue();
        if (valor != null) {
            if (valor.getClass().isArray()) {
                datos = (Object[]) valor;
            } else {

                if (valor instanceof java.util.List) {
                    datos = ((List) valor).toArray();
                } else if (valor instanceof java.util.Collection) {
                    datos = ((Collection) valor).toArray();
                } else if (valor instanceof java.util.Map) {
                    datos = ((Map) valor).values().toArray();
                }
            }
        }
        if (datos == null) {
            datos = new Object[1];
            datos[0] = valor;
        }

        if (ttComp.getChildCount() > 0) {
            Map reqMap = context.getExternalContext().getRequestMap();
            for (int i = 0; i < datos.length; i++) {
                if (ttComp.getVar() != null) {
                    reqMap.put(ttComp.getVar(), datos[i]);
                }
                super.encodeChildren(context, component);
            }
            if (ttComp.getVar() != null) {
                reqMap.remove(ttComp.getVar());
            }
        }
    }

}
