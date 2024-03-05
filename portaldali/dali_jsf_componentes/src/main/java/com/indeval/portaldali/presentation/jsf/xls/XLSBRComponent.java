package com.indeval.portaldali.presentation.jsf.xls;

import javax.faces.component.UIOutput;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XLSBRComponent extends UIOutput implements IXLSComponent {

	public XLSBRComponent(){
        this.setRendererType("indeval.XLSBR");
    }
    
    public String getFamily() {
        return "indeval.XLSBR";
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
