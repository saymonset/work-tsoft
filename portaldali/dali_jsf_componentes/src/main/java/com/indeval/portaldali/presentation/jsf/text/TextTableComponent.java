package com.indeval.portaldali.presentation.jsf.text;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

public class TextTableComponent extends UIOutput{
    
    private String var;

    public TextTableComponent(){
        this.setRendererType("indeval.TextTable");
        
    }
    
    public String getFamily() {
        return "indeval.TextTable";
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[2];
        state[0] = super.saveState(context);
        state[1] = var;
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        var = (String)stateArr[1];
    }
    
    
    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }



}
