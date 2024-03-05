package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

public class PDFGColumnTagDali extends UIComponentTag {
    
    private String align;
    private String valign;
    private String collspan;
    private String bgcolor;
    private String rendered;
    
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        
        PDFGColumnComponentDali columnComponent = (PDFGColumnComponentDali)component; 
        
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        
        if (collspan != null) {
            if (UIComponentTag.isValueReference(collspan)) {
                String stCollspan  = application.createValueBinding(collspan).getValue(context).toString();
                Integer collspan = new Integer( stCollspan );
                columnComponent.setCollspan( collspan );
            } else {
                columnComponent.setCollspan(new Integer( collspan ));
            }
        }
        if (align != null) {
            if (UIComponentTag.isValueReference(align)) {
                columnComponent.setAlign(application.createValueBinding(align).getValue(context).toString());
            } else {
                columnComponent.setAlign(align);
            }
        }
        if (valign != null) {
            if (UIComponentTag.isValueReference(valign)) {
                columnComponent.setValign(application.createValueBinding(valign).getValue(context).toString());
            } else {
                columnComponent.setValign(valign);
            }
        }
        if (bgcolor != null) {
            if (UIComponentTag.isValueReference(bgcolor)) {
                columnComponent.setBgcolor(application.createValueBinding(bgcolor).getValue(context).toString());
            } else {
                columnComponent.setBgcolor(bgcolor);
            }
        }
        if( rendered != null ) {
            if (UIComponentTag.isValueReference(rendered)) {
                Boolean renderedData = (Boolean)application.createValueBinding(rendered).getValue(context);
                columnComponent.setRendered(renderedData.booleanValue());
            } else {
                Boolean bool = new Boolean(rendered);
                columnComponent.setRendered(bool.booleanValue());
            }
        }

    }
    
    
    public String getComponentType() {
        return "indeval.PDFGridColumn";
    }

    public String getRendererType() {
        return "indeval.PDFGridColumn";
    }

    public String getAlign() {
        return align;
    }

    public String getCollspan() {
        return collspan;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setCollspan(String collspan) {
        this.collspan = collspan;
    }
    
    public String getValign() {
        return valign;
    }


    public void setValign(String valign) {
        this.valign = valign;
    }


    public String getBgcolor() {
        return bgcolor;
    }


    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }


    public void release() {
        super.release();
        align = null;
        valign = null;
        collspan = null;
        bgcolor = null;
        rendered = null;
    }

    public String getRendered() {
        return rendered;
    }


    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

}

