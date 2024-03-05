package com.indeval.portaldali.presentation.jsf.text;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.myfaces.shared_impl.taglib.UIComponentTagBase;

public class TextTableTag extends UIComponentTagBase {

    private Logger logger = LoggerFactory.getLogger(TextTableTag.class);
    private String var;
    private String value;
    
   

    public void release() {
        super.release();
        var = null;
        value = null;
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        TextTableComponent tableComponent = (TextTableComponent)component; 
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        
        if (var != null) {
            tableComponent.setVar(var);
        }
        if (value != null) {
            if (UIComponentTag.isValueReference(value)) {
                tableComponent.setValueBinding("value", application.createValueBinding(value));
            } else {
                System.out.println("no se encontro la lista");
                logger.error("no se encontro la lista");
            }
        }
    }
    
    
    public String getComponentType() {
        return "indeval.TextTable";
    }

    public String getRendererType() {
        return "indeval.TextTable";
    }

  

    public String getValue() {
        return value;
    }

  
    public void setValue(String value) {
        this.value = value;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
    
   
}
