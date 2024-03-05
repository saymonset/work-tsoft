package com.indeval.portaldali.presentation.jsf.xls;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class XLSTableComponent extends UIOutput implements IXLSComponent {
    
    private String var;


    public XLSTableComponent(){
        this.setRendererType("indeval.XLSTable");
        
    }
    
    public String getFamily() {
        return "indeval.XLSTable";
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[2];
        state[0] = super.saveState(context);
        state[1] = var;
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        var = (String)stateArr[1];
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

	public HSSFDataFormat getDataFormat() {
		return ((IXLSComponent)this.getParent()).getDataFormat();
	}

	public short getNextRow() {
		return ((IXLSComponent)this.getParent()).getNextRow();
	}

	public HSSFWorkbook getWorkbook() {
		return ((IXLSComponent)this.getParent()).getWorkbook();
	}
	public void createNewSheet(String name) {
		((IXLSComponent)this.getParent()).createNewSheet( name );
		
	}

    private HSSFFont fontUsed;

    public HSSFFont getFontUsed() {
        if(fontUsed == null) {
            fontUsed = createFont();
        }
        return fontUsed;
    }
    
    private HSSFFont createFont() {
        final HSSFFont font = getWorkbook().createFont();
        font.setFontHeightInPoints((short) 9);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font.setColor(HSSFColor.BLACK.index);
        return font;
    }

}
