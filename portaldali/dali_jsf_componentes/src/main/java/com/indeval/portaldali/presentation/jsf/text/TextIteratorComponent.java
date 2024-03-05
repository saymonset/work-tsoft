package com.indeval.portaldali.presentation.jsf.text;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

public class TextIteratorComponent extends UIOutput {
    
    private String var;

    public TextIteratorComponent(){
        this.setRendererType("indeval.TextIterator");
        
    }
    
    public String getFamily() {
        return "indeval.TextIterator";
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[2];
        state[0] = super.saveState(context);
        state[1] = getVar();
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        setVar( (String)stateArr[1] );
    }
    
    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }


}
