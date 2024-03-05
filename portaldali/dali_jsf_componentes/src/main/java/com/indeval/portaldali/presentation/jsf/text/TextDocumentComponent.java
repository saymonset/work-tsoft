package com.indeval.portaldali.presentation.jsf.text;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class TextDocumentComponent extends UIOutput {
    
    private String documentName = "reporte";
    private String documentTitle = "";
    private String institucion;
    private Date fecha;
    
    
    private ByteArrayOutputStream baos;
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[5];
        state[0] = super.saveState(context);
        state[1] = getDocumentName();
        state[2] = getDocumentTitle();
        state[3] = getFecha();
        state[4] = getInstitucion();
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        setDocumentName((String)stateArr[1]);
        setDocumentTitle((String)stateArr[2]);
        setFecha((Date) stateArr[3]);
        setInstitucion((String) stateArr[4]);
        
    }
    
    
    public TextDocumentComponent() {
        this.setRendererType("indeval.TextDocument");
    }

    public String getFamily() {
        return "indeval.TextDocument";
    }
    
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    
    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }
    
    public ByteArrayOutputStream getBaos() {
        return baos;
    }
    
   

    public Date getFecha() {
        if( fecha == null ) {
            ValueBinding vbFecha = getValueBinding("fecha");
            if( vbFecha != null ) {
                return (Date)vbFecha.getValue(FacesContext.getCurrentInstance());
            }
        }
        return null;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getInstitucion() {
        if( institucion == null ) {
            ValueBinding vbInstitucion = getValueBinding("institucion");
            if( vbInstitucion != null ) {
                return (String)vbInstitucion.getValue(FacesContext.getCurrentInstance());
            }
        }
        return null;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    
}
