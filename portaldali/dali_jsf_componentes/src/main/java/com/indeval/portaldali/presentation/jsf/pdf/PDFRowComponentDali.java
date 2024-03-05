package com.indeval.portaldali.presentation.jsf.pdf;

import java.util.List;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import com.lowagie.text.pdf.PdfPTable;

public class PDFRowComponentDali extends UIOutput implements IPDFTableElementDali, IPDFRowElementDali {

    private Boolean oddRow = new Boolean(false);
    private int maxCols = 0;

    public int getMaxCols() {
        return maxCols;
    }

    public void setMaxCols(int maxCols) {
        this.maxCols = maxCols;
    }

    public PDFRowComponentDali(){
        this.setRendererType("indeval.PDFRow");
    }
    
    public String getFamily() {
        return "indeval.PDFRow";
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[2];
        state[0] = super.saveState(context);
        state[1] = isOddRow();
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        setOddRow((Boolean)stateArr[1]);
        
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
                    PDFColumnComponentDali comp = (PDFColumnComponentDali)children.get(i);
                    if( comp.isRendered() ) {
                        numCols += comp.getCollspan().intValue();
                    }
                }
            }
        return numCols;
    }
    
    public boolean hasHeaders(){
        if( isRendered() && this.getChildCount() > 0 ){
            List children = this.getChildren();;
            for( int i = 0; i < children.size(); i++ ){
                PDFColumnComponentDali comp = (PDFColumnComponentDali)children.get(i);
                if( comp.isRendered() ) {
                    if( comp.getHeader() != null ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean hasFooters(){
        if( isRendered() && this.getChildCount() > 0 ){
            List children = this.getChildren();;
            for( int i = 0; i < children.size(); i++ ){
                PDFColumnComponentDali comp = (PDFColumnComponentDali)children.get(i);
                if( comp.isRendered() ) {
                    if( comp.getFooter() != null ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Boolean isOddRow() {
        return oddRow;
    }

    public void setOddRow(Boolean oddRow) {
        this.oddRow = oddRow;
    }

    
}
