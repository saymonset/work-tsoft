package com.indeval.portaldali.presentation.jsf.xls;

import javax.faces.component.UIOutput;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XLSNewSheetComponent extends UIOutput implements IXLSComponent{

    public XLSNewSheetComponent(){
        this.setRendererType("indeval.XLSNewSheet");
    }
    
    public String getFamily() {
        return "indeval.XLSNewSheet";
    }
    
    public HSSFSheet getCurrentSheet() {
        return ((IXLSComponent)this.getParent()).getCurrentSheet();
    }

	public short getCurrentRow() {
		return ((IXLSComponent)this.getParent()).getCurrentRow();
	}

	public short getNextRow() {
		 return ((IXLSComponent)this.getParent()).getNextRow();
	}

	public HSSFDataFormat getDataFormat() {
		return ((IXLSComponent)this.getParent()).getDataFormat();
	}

	public HSSFWorkbook getWorkbook() {
		// TODO Auto-generated method stub
		return ((IXLSComponent)this.getParent()).getWorkbook();
	}
	public void createNewSheet(String name) {
		((IXLSComponent)this.getParent()).createNewSheet( name );
		
	}
}
