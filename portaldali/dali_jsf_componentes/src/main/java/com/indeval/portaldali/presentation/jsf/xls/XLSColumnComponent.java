package com.indeval.portaldali.presentation.jsf.xls;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XLSColumnComponent extends UIOutput implements IXLSComponent {
    
    private Object header = null;
    private Object footer = null;
    private Boolean oddRow = Boolean.FALSE;
    private String pattern; 
    private Integer cellNumber;
    private String bgcolor;
    
    public Boolean isOddRow() {
		return oddRow;
	}

	public void setOddRow(Boolean oddRow) {
		this.oddRow = oddRow;
	}

	/**
	 * Obtiene el valor del atributo bgcolor
	 *
	 * @return el valor del atributo bgcolor
	 */
	public String getBgcolor() {
		return bgcolor;
	}

	/**
	 * Establece el valor del atributo bgcolor
	 *
	 * @param bgcolor el valor del atributo bgcolor a establecer
	 */
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public XLSColumnComponent(){
        this.setRendererType("indeval.XLSColumn");
    }
     
    public String getFamily() {
        return "indeval.XLSColumn";
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[6];
        state[0] = super.saveState(context);
        state[1] = header;
        state[2] = oddRow;
        state[3] = footer;
        state[4] = pattern;
        state[5] = bgcolor;
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        header = (String)stateArr[1];
        oddRow = (Boolean)stateArr[2];
        footer = (String)stateArr[3];
        pattern = (String)stateArr[4];
        bgcolor = (String)stateArr[5];
    }
    

    public Object getHeader() {
        if( header == null ) {
            try {
                return getValueBinding("header").getValue(FacesContext.getCurrentInstance());
            }catch (Exception e) {
            }
             
        }
        return header;

    }



   
    public void setHeader(Object header) {
        this.header = header;
    }

    public Object getFooter() {
        if( footer == null ) {
            try {
                return getValueBinding("footer").getValue(FacesContext.getCurrentInstance());
            }catch (Exception e) {
            }
        }
        return footer;
    }

    public void setFooter(Object footer) {
    	this.footer = footer;
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

	public Integer getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(Integer cellNumber) {
		this.cellNumber = cellNumber;
	}
	public void createNewSheet(String name) {
		((IXLSComponent)this.getParent()).createNewSheet( name );
		
	}
}
