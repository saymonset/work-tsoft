package com.indeval.portaldali.presentation.jsf.pdf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.http.HttpServletResponse;

import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.ExtendedDocument;
import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.IDocument;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;

public class PDFDocumentComponentDali extends UIOutput implements IPDFComponentDali{
    
    private String documentName = "reporte";
    private String documentTitle = "";
    private String imageResource;
    private Boolean landscape = Boolean.FALSE;
    private IDocument document;
    private String institucion;
    private Date fecha;
    private Boolean legal = Boolean.FALSE;
    private PdfWriter writer;
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[8];
        state[0] = super.saveState(context);
        state[1] = getDocumentName();
        state[2] = getDocumentTitle();
        state[3] = getImageResource();
        state[4] = getLandscape();
        state[5] = getLegal();
        state[6] = getFecha();
        state[7] = getInstitucion();
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        setDocumentName((String)stateArr[1]);
        setDocumentTitle((String)stateArr[2]);
        setImageResource((String)stateArr[3]);
        setLandscape((Boolean)stateArr[4]);
        setLegal((Boolean)stateArr[5]);
        setFecha((Date) stateArr[6]);
        setInstitucion((String) stateArr[7]);
        
    }
    
    
    public PDFDocumentComponentDali() {
        this.setRendererType("indeval.PDFDocument");
    }

    public String getFamily() {
        return "indeval.PDFDocument";
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
    
    public IDocument getDocument() {
        return document;
    }

        
    public void setDocument(IDocument document) throws DocumentException, IOException{
        this.document = document;
        OutputStream os = ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).getOutputStream();
        writer = PdfWriter.getInstance((ExtendedDocument)this.document, os );
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public Boolean getLandscape() {
        return landscape;
    }

    public void setLandscape(Boolean landscape) {
        this.landscape = landscape;
    }

    public Boolean getLegal() {
        return legal;
    }

    public void setLegal(Boolean legal) {
        this.legal = legal;
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

    public PdfWriter getWriter() {
        return writer;
    }

    public void setWriter(PdfWriter writer) {
        this.writer = writer;
    }
    
}
