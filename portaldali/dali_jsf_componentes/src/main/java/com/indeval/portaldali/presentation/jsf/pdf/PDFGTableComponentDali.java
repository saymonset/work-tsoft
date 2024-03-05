package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.IDocument;
import com.lowagie.text.pdf.PdfPTable;

public class PDFGTableComponentDali extends UIOutput implements IPDFTableElementDali, IPDFComponentDali{
    
    private PdfPTable table;
    private String align = "CENTER";
    private Integer widthPercentage = new Integer(100);
    private int[] cellwidths;
    private Integer border = new Integer(0);
    private String bordercolor;
    
    public Integer getWidthPercentage() {
        return widthPercentage;
    }

    public void setWidthPercentage(Integer widthPercentage) {
        this.widthPercentage = widthPercentage;
    }
    
    public void setAlign(String align) {
        if( align != null && 
                ( align.toUpperCase().equals("CENTER") ||
                  align.toUpperCase().equals("RIGHT") ||
                  align.toUpperCase().equals("LEFT"))
           ){
            this.align = align.toUpperCase();
        }
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[6];
        state[0] = super.saveState(context);
        state[1] = widthPercentage;
        state[2] = align;
        state[3] = cellwidths;
        state[4] = border;
        state[5] = bordercolor;
        
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        widthPercentage =  (Integer)stateArr[1];
        align = (String)stateArr[2];
        cellwidths = (int[])stateArr[3];
        border = (Integer)stateArr[4];
        bordercolor = (String)stateArr[5];
    }
    
    
    public PDFGTableComponentDali() {
        this.setRendererType("indeval.PDFGrid");
    }

    public String getFamily() {
        return "indeval.PDFGrid";
    }

    public IDocument getDocument() {
        return ((IPDFComponentDali)this.getParent()).getDocument();
    }

    public PdfPTable getTable() {
        return table;
    }

    public void setTable(PdfPTable table) {
        this.table = table;
    }

    public String getAlign() {
        return align;
    }

    public int[] getCellwidths() {
        return cellwidths;
    }

    public void setCellwidths(int[] cellwidths) {
        this.cellwidths = cellwidths;
    }

    public Integer getBorder() {
        return border;
    }

    public void setBorder(Integer border) {
        this.border = border;
    }

    public String getBordercolor() {
        return bordercolor;
    }

    public void setBordercolor(String bordercolor) {
        this.bordercolor = bordercolor;
    }

   

}
