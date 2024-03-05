package com.indeval.portaldali.presentation.jsf.xls;

import java.util.List;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XLSRowComponent extends UIOutput implements IXLSComponent {

	private Boolean oddRow = Boolean.FALSE;
	private HSSFRow row; 

	public HSSFRow getRow() {
		return row;
	}

	public void setRow(HSSFRow row) {
		this.row = row;
	}

	public XLSRowComponent() {
		this.setRendererType("indeval.XLSRow");
	}

	public String getFamily() {
		return "indeval.XLSRow";
	}

	public boolean hasHeaders() {
		if (isRendered() && this.getChildCount() > 0) {
			List children = this.getChildren();
			for (int i = 0; i < children.size(); i++) {
				XLSColumnComponent comp = (XLSColumnComponent) children.get(i);
				if (comp.isRendered()) {
					if (comp.getHeader() != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean hasFooters() {
		if (isRendered() && this.getChildCount() > 0) {
			List children = this.getChildren();
			;
			for (int i = 0; i < children.size(); i++) {
				XLSColumnComponent comp = (XLSColumnComponent) children.get(i);
				if (comp.isRendered()) {
					if (comp.getFooter() != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public HSSFSheet getCurrentSheet() {
		return ((IXLSComponent) getParent()).getCurrentSheet();
	}

	public short getCurrentRow() {
		return ((IXLSComponent) getParent()).getCurrentRow();
	}

	public short getNextRow() {
		return ((IXLSComponent) getParent()).getNextRow();
	}

	public HSSFDataFormat getDataFormat() {
		return ((IXLSComponent) this.getParent()).getDataFormat();
	}

	public HSSFWorkbook getWorkbook() {
		return ((IXLSComponent) this.getParent()).getWorkbook();
	}

	public Object saveState(FacesContext context) {
		Object[] state = new Object[2];
		state[0] = super.saveState(context);
		state[1] = isOddRow();
		return (Object) state;
	}

	public void restoreState(FacesContext context, Object state) {
		Object[] stateArr = (Object[]) state;
		super.restoreState(context, stateArr[0]);
		setOddRow((Boolean) stateArr[1]);

	}

	public Boolean isOddRow() {
		return oddRow;
	}

	public void setOddRow(Boolean oddRow) {
		this.oddRow = oddRow;
	}
	public void createNewSheet(String name) {
		((IXLSComponent)this.getParent()).createNewSheet( name );
		
	}
	
	
}
