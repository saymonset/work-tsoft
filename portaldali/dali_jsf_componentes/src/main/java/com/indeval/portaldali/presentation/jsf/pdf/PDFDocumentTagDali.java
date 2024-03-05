package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

import org.apache.commons.beanutils.ConversionException;
import org.apache.myfaces.shared_impl.taglib.UIComponentTagBase;

public class PDFDocumentTagDali extends UIComponentTagBase {

    private String documentName;
    private String documentTitle;
    private String imageResource;
    private String landscape;
    private String legal;
    private String institucion;
    private String fecha;

    
    
    public void release() {
        super.release();
        documentName = null;
        documentTitle = null;
        imageResource = null;
        landscape = null;
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
        if (imageResource != null) {
            if (UIComponentTag.isValueReference(imageResource)) {
                component.getAttributes().put("imageResource", application.createValueBinding(imageResource).getValue(context).toString());
            } else {
                component.getAttributes().put("imageResource", imageResource);
            }
        }
        if (landscape != null && !landscape.equals("")) {
            if (UIComponentTag.isValueReference(landscape)) {
                String value = application.createValueBinding(landscape).getValue(context).toString();
                component.getAttributes().put("landscape", new Boolean (value));

            } else {
                component.getAttributes().put("landscape", new Boolean(landscape));
            }
        }
        if (legal != null && !legal.equals("")) {
            if (UIComponentTag.isValueReference(legal)) {
                String value = application.createValueBinding(legal).getValue(context).toString();
                component.getAttributes().put("legal", new Boolean (value));

            } else {
                component.getAttributes().put("legal", new Boolean(legal));
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
        return "indeval.PDFDocument";
    }

    public String getRendererType() {
        return "indeval.PDFDocument";
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getLandscape() {
        return landscape;
    }

    public void setLandscape(String landscape) {
        this.landscape = landscape;
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
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
