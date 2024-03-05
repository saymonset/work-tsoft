package com.indeval.portaldali.presentation.jsf.text;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

import org.apache.commons.beanutils.ConversionException;
import org.apache.myfaces.shared_impl.taglib.UIComponentTagBase;

public class TextDocumentTag extends UIComponentTagBase {

    private String documentName;
    private String documentTitle;
    private String institucion;
    private String fecha;

    
    
    public void release() {
        super.release();
        documentName = null;
        documentTitle = null;
        institucion = null;
        fecha = null;
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();

        if (documentName != null) {
            if (UIComponentTag.isValueReference(documentName)) {
                component.getAttributes().put("documentName", application.createValueBinding(documentName).getValue(context).toString());
            } else {
                component.getAttributes().put("documentName", documentName);
            }
        }
        
        if (fecha != null) {
            if (UIComponentTag.isValueReference(fecha)) {
                component.setValueBinding("fecha", application.createValueBinding(fecha));
            } else {
                throw new ConversionException( "el valor de fecha debe un binding" ); 
            }
        }
        
        if (institucion != null) {
            if (UIComponentTag.isValueReference(institucion)) {
                component.setValueBinding("institucion", application.createValueBinding(institucion));
            } else {
                component.getAttributes().put("institucion", institucion); 
            }
        }
        
        if (documentTitle != null) {
            if (UIComponentTag.isValueReference(documentTitle)) {
                component.getAttributes().put("documentTitle", application.createValueBinding(documentTitle).getValue(context).toString());
            } else {
                component.getAttributes().put("documentTitle", documentTitle);
            }
        }
    }
    
    public String getDocumentName() {
        return documentName;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }
    
    public String getComponentType() {
        return "indeval.TextDocument";
    }

    public String getRendererType() {
        return "indeval.TextDocument";
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }
    
    
    
}
