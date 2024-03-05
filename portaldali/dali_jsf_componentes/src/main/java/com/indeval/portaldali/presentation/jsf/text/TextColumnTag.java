package com.indeval.portaldali.presentation.jsf.text;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

public class TextColumnTag extends UIComponentTag {
    
    private String header;
    private String align;
    private String value;
    private String size;
    private String footer;
    private String rendered;
    
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        
        TextColumnComponent columnComponent = (TextColumnComponent)component; 
        
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        
        if (size != null) {
            if (UIComponentTag.isValueReference(size)) {
                String stSize  = application.createValueBinding(size).getValue(context).toString();
                Integer intSize = new Integer( stSize );
                columnComponent.setSize( intSize );
            } else {
                columnComponent.setSize(new Integer( size ));
            }
        }
        if (align != null) {
            if (UIComponentTag.isValueReference(align)) {
                columnComponent.setAlign(application.createValueBinding(align).getValue(context).toString());
            } else {
                columnComponent.setAlign(align);
            }
        }
        if (header != null) {
            if (UIComponentTag.isValueReference(header)) {
                columnComponent.setValueBinding("header", application.createValueBinding(header));
            } else {
                columnComponent.setHeader(header);
            }
        }
        if (footer != null) {
            if (UIComponentTag.isValueReference(footer)) {
                columnComponent.setValueBinding("footer", application.createValueBinding(footer));
            } else {
                columnComponent.setFooter(footer);
            }
        }
        if (rendered != null){
        	if (UIComponentTag.isValueReference(rendered)) {
        		Boolean brend = (Boolean)(application.createValueBinding(rendered)).getValue(context);                 
        		columnComponent.setRendered( brend.booleanValue() );
            } else {
                columnComponent.setRendered(Boolean.parseBoolean(rendered));
            }
        }else{
        	columnComponent.setRendered(true);
        }
        if (value != null) {
            if (UIComponentTag.isValueReference(value)) {
                columnComponent.setValueBinding("value", application.createValueBinding(value));
            } else {
                columnComponent.setValue(value);
            }
        }else{
        	columnComponent.setValue(" ");
        }
    }
    
    
    public String getComponentType() {
        return "indeval.TextColumn";
    }

    public String getRendererType() {
        return "indeval.TextColumn";
    }

    public String getAlign() {
        return align;
    }

    public String getSize() {
        return size;
    }

    public String getHeader() {
        return header;
    }

    public String getValue() {
        return value;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setCollspan(String collspan) {
        this.size = collspan;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public void release() {
        super.release();
        header = null;
        align = null;
        value = null;
        size = null;
        footer = null;
    }


    public String getFooter() {
        return footer;
    }


    public void setFooter(String footer) {
        this.footer = footer;
    }


    public void setSize(String size) {
        this.size = size;
    }


	public String getRendered() {
		return rendered;
	}


	public void setRendered(String rendered) {
		this.rendered = rendered;
	}
}
