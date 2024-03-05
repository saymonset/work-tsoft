package com.indeval.portaldali.presentation.jsf.xls;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XLSIteratorComponent extends UIOutput implements IXLSComponent {
    
    private String var;

    public XLSIteratorComponent(){
        this.setRendererType("indeval.XLSIterator");
        
    }
    
    public String getFamily() {
        return "indeval.XLSIterator";
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[2];
        state[0] = super.saveState(context);
        state[1] = getVar();
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        setVar( (String)stateArr[1] );
    }
    
    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
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
