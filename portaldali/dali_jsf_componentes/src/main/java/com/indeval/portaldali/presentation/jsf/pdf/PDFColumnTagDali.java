package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

public class PDFColumnTagDali extends UIComponentTag {
    
    private String header;
    private String align;
    private String value;
    private String collspan;
    private String footer;
    private String rendered;
    private String bgcolor;
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
         
        PDFColumnComponentDali columnComponent = (PDFColumnComponentDali)component; 
        
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
        if (value != null) {
            if (UIComponentTag.isValueReference(value)) {
                columnComponent.setValueBinding("value", application.createValueBinding(value));
            } else {
                columnComponent.setValue(value);
            }
        }else{
        	columnComponent.setValue(" ");
        }
        if( rendered != null ) {
            if (UIComponentTag.isValueReference(rendered)) {
                Boolean renderedData = (Boolean)application.createValueBinding(rendered).getValue(context);
                columnComponent.setRendered(renderedData.booleanValue());
            } else {
                Boolean boRendered = new Boolean(rendered);
                columnComponent.setRendered(boRendered.booleanValue());
            }
        }
        

		if (bgcolor != null) {
			if (UIComponentTag.isValueReference(bgcolor)) {
				columnComponent.setValueBinding("bgcolor", application.createValueBinding(bgcolor));
			} else {
				columnComponent.setBgcolor(bgcolor);
			}
		} else {
			columnComponent.setBgcolor(null);
		}
    }
    
    
    public String getComponentType() {
        return "indeval.PDFColumn";
    }

    public String getRendererType() {
        return "indeval.PDFColumn";
    }

    public String getAlign() {
        return align;
    }

    public String getCollspan() {
        return collspan;
    }

    public String getHeader() {
        return header;
    }

    /**
	 * Obtiene el valor del atributo bgcolor
	 *
	 * @return el valor del atributo bgcolor
	 */
	public String getBgcolor() {
		return bgcolor;
	}


	/**
	 * Establece el valor del atributo bgcolor
	 *
	 * @param bgcolor el valor del atributo bgcolor a establecer
	 */
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}


	public String getValue() {
        return value;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setCollspan(String collspan) {
        this.collspan = collspan;
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
        collspan = null;
        footer = null;
        rendered = null;
    }


    public String getFooter() {
        return footer;
    }


    public void setFooter(String footer) {
        this.footer = footer;
    }


    public String getRendered() {
        return rendered;
    }


    public void setRendered(String rendered) {
        this.rendered = rendered;
    }
    
    
}
