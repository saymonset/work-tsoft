package com.indeval.portaldali.presentation.jsf.text;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class TextColumnComponent extends UIOutput {
    
    private Object header = null;
    private Object footer = null;
    private String align = "LEFT";
    private Integer size = new Integer(1);
    
    
    public TextColumnComponent(){
        this.setRendererType("indeval.TextColumn");
    }
    
    public String getFamily() {
        return "indeval.TextColumn";
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[5];
        state[0] = super.saveState(context);
        state[1] = header;
        state[2] = align;
        state[3] = footer;
        state[4] = size;
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        header = (String)stateArr[1];
        align = (String)stateArr[2];
        footer = (String)stateArr[3];
        size = (Integer)stateArr[4];
    }
    
    
    public String getAlign() {
        return align;
    }



    public Object getHeader() {
        if( header == null ) {
            try {
                ValueBinding binding = getValueBinding("header");
                if( binding != null ) {
                    return binding.getValue(FacesContext.getCurrentInstance());
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
             
        }
        return header;

    }



    public void setAlign(String align) {
        if( align != null && ( align.toUpperCase().equals("CENTER") ||
                align.toUpperCase().equals("RIGHT") ||
                align.toUpperCase().equals("LEFT") ||
                align.toUpperCase().equals("NONE") )){
            this.align = align.toUpperCase();
        }
    }



    public void setHeader(Object header) {
        this.header = header;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Object getFooter() {
        if( footer == null ) {
            try {
                ValueBinding binding = getValueBinding("footer");
                if( binding != null ) {
                    return binding.getValue(FacesContext.getCurrentInstance());
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return footer;
    }

    public void setFooter(Object footer) {
        this.footer = footer;
    }
    
}
