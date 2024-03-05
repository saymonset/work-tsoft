package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.IDocument;
import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.PDFExtendedCell;
import com.lowagie.text.pdf.PdfPTable;

public class PDFGColumnComponentDali extends UIOutput implements IPDFComponentDali, IPDFTableElementDali {
    
    private String align = "CENTER";
    private String valign = "MIDDLE";
    private Integer collspan = new Integer(1);
    private Integer border = new Integer(0);
    private String bgcolor;
    private String borderColor;
    private PDFExtendedCell cell;
    
    public PDFGColumnComponentDali(){
        this.setRendererType("indeval.PDFGridColumn");
    }
    
    public String getFamily() {
        return "indeval.PDFGridColumn";
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[5];
        state[0] = super.saveState(context);
        state[1] = collspan;
        state[2] = align;
        state[3] = valign;
        state[4] = bgcolor;
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        collspan    =(Integer) stateArr[1];
        align       = (String)stateArr[2]; 
        valign      = (String)stateArr[3];
        bgcolor     = (String)stateArr[4]; 

    }
    
    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        if( align != null && ( align.toUpperCase().equals("CENTER") ||
                align.toUpperCase().equals("RIGHT") ||
                align.toUpperCase().equals("LEFT"))){
            this.align = align.toUpperCase();
        }
    }

    public PdfPTable getTable(){
        return ((IPDFTableElementDali)getParent()).getTable();
    }
    
    public Integer getCollspan() {
        return collspan;
    }

    public void setCollspan(Integer collspan) {
        this.collspan = collspan;
    }

    public PDFExtendedCell getCell() {
        return cell;
    }

    public void setCell(PDFExtendedCell cell) {
        this.cell = cell;
    }

    public IDocument getDocument() {
        return cell;
    }

    public String getValign() {
        return valign;
    }

    public void setValign(String valign) {
        if( valign != null && ( valign.toUpperCase().equals("MIDDLE") ||
                valign.toUpperCase().equals("TOP") ||
                valign.toUpperCase().equals("BOTTOM"))){
            this.valign = valign.toUpperCase();
        }
    }

    public Integer getBorder() {
        return border;
    }

    public void setBorder(Integer border) {
        this.border = border;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }
    
    
}
