package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import com.indeval.portaldali.presentation.jsf.pdf.itextExtension.IDocument;
import com.lowagie.text.pdf.PdfPTable;

public class PDFTableComponentDali extends UIOutput implements IPDFTableElementDali, IPDFComponentDali {
    
    private PdfPTable table;
    private String currentBean;
    private Integer widthPercentage = new Integer(96);
    private String align = "CENTER";
    private int maxColums=Integer.MAX_VALUE;


    public int getMaxColums() {
		return maxColums;
	}

	public void setMaxColums(int maxColums) {
		this.maxColums = maxColums;
	}

    public String getAlign() {
        return align;
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

    public Integer getWidthPercentage() {
        return widthPercentage;
    }

    public void setWidthPercentage(Integer widthPercentage) {
        this.widthPercentage = widthPercentage;
    }

    public PDFTableComponentDali(){
        this.setRendererType("indeval.PDFTable");
        
    }
    
    public String getFamily() {
        return "indeval.PDFTable";
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[4];
        state[0] = super.saveState(context);
        state[1] = getCurrentBean();
        state[2] = getWidthPercentage();
        state[3] = getAlign();
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        setCurrentBean( (String)stateArr[1] );
        setWidthPercentage( (Integer)stateArr[2] );
        setAlign((String)stateArr[3]);
    }
    
    
    public String getCurrentBean() {
        return currentBean;
    }

    public void setCurrentBean(String currentBean) {
        this.currentBean = currentBean;
    }

    public void setTable(PdfPTable table) {
        this.table = table;
    }

    public PdfPTable getTable() {
        return table;
    }

    public IDocument getDocument() {
        return ((IPDFComponentDali)this.getParent()).getDocument();
    }


}
