package com.indeval.portaldali.presentation.jsf.pdf;

import java.util.List;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import com.lowagie.text.pdf.PdfPTable;

public class PDFGRowComponentDali extends UIOutput implements IPDFTableElementDali{

    private Integer border = new Integer(0);
    
    private String borderColor;
    
    private int maxCols = 0;

    public int getMaxCols() {
        return maxCols;
    }

    public void setMaxCols(int maxCols) {
        this.maxCols = maxCols;
    }

    public PDFGRowComponentDali(){
        this.setRendererType("indeval.PDFGridRow");
    }
    
    public String getFamily() {
        return "indeval.PDFGridRow";
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[1];
        state[0] = super.saveState(context);
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        
    }
    
    /**
     * @return The continer PdfPTable 
     */ 
    public PdfPTable getTable(){
        return ((IPDFTableElementDali)getParent()).getTable();
    }
    
    /**
     * @return the number of colls in de row considering collspan
     */
    public int getRealNumCols(){
        int numCols = 0;
            if( isRendered() && this.getChildCount() > 0 ){
                List children = this.getChildren();;
                for( int i = 0; i < children.size(); i++ ){
                    PDFGColumnComponentDali comp = (PDFGColumnComponentDali)children.get(i);
                    numCols += comp.getCollspan().intValue();
                }
            }
        return numCols;
    }

    public Integer getBorder() {
        return border;
    }

    public void setBorder(Integer border) {
        this.border = border;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    
}
