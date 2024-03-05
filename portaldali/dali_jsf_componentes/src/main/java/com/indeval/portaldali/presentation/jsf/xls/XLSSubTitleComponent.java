package com.indeval.portaldali.presentation.jsf.xls;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XLSSubTitleComponent extends UIOutput implements IXLSComponent {

	private String pattern;
	
	
    public String getPattern() {
    	if( pattern == null ){
    		ValueBinding vb = getValueBinding("pattern");
    		if( vb != null ){
    			return vb.getValue(FacesContext.getCurrentInstance()).toString();
    		}
    	}
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public XLSSubTitleComponent(){
        this.setRendererType("indeval.XLSSubTitle");
    }
    
    public String getFamily() {
        return "indeval.XLSSubTitle";
    }
    
    public HSSFSheet getCurrentSheet() {
        return  ((IXLSComponent)getParent()).getCurrentSheet();
    }

	public short getCurrentRow() {
		return  ((IXLSComponent)getParent()).getCurrentRow();
	}

	public short getNextRow() {
		return  ((IXLSComponent)getParent()).getNextRow();
	}

	public HSSFDataFormat getDataFormat() {
		return ((IXLSComponent)this.getParent()).getDataFormat();
	}

	public HSSFWorkbook getWorkbook() {
		return ((IXLSComponent)this.getParent()).getWorkbook();
	}
	public void createNewSheet(String name) {
		((IXLSComponent)this.getParent()).createNewSheet( name );
		
	}
}
