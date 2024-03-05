package com.indeval.portaldali.presentation.jsf.xls;

import java.util.Date;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XLSDocumentComponent extends UIOutput implements IXLSComponent{
    
    private String documentName = "reporte";
    private String documentTitle = "";
    private String institucion;
    private String sheetName;
    private Date fecha;
    private HSSFWorkbook workBook;
    private HSSFSheet currentSheet;
    private short currentRow = 0;
    private HSSFDataFormat format;
    

    public XLSDocumentComponent() {
        this.setRendererType("indeval.XLSDocument");
        workBook = new HSSFWorkbook();
    }

    public short getCurrentRow(){
    	return currentRow;
    }
    
    public short getNextRow(){
    	return ++currentRow;
    }
    
    public void createNewSheet(String name){
    	if( name!=null && name.trim().length() > 0 ){
    		currentSheet = workBook.createSheet(name);
    	}else{
    		currentSheet = workBook.createSheet();
    	}
    	currentRow = -1;
    }
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[6];
        state[0] = super.saveState(context);
        state[1] = documentName;
        state[2] = documentTitle;
        state[3] = fecha;
        state[4] = institucion;
        state[5] = sheetName;
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        documentName = (String)stateArr[1];
        documentTitle = (String)stateArr[2];
        fecha = (Date) stateArr[3];
        institucion = (String) stateArr[4];
        sheetName = (String) stateArr[5];
    }

    public String getFamily() {
        return "indeval.XLSDocument";
    }
    
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    
    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }
    
    public HSSFSheet getCurrentSheet() {
    	if( currentSheet == null ){
    		createNewSheet(sheetName);
    	}
        return currentSheet;
    }

    public Date getFecha() {
        if( fecha == null ) {
            ValueBinding vbFecha = getValueBinding("fecha");
            if( vbFecha != null ) {
                return (Date)vbFecha.getValue(FacesContext.getCurrentInstance());
            }
            return null;
        }
        return fecha;
        
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getInstitucion() {
        if( institucion == null ) {
            ValueBinding vbInstitucion = getValueBinding("institucion");
            if( vbInstitucion != null ) {
                return (String)vbInstitucion.getValue(FacesContext.getCurrentInstance());
            }
            return null;
        }
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

	public HSSFWorkbook getWorkBook() {
		return workBook;
	}

	public void setWorkBook(HSSFWorkbook workBook) {
		this.workBook = workBook;
	}

	public HSSFDataFormat getDataFormat() {
		if( format == null ){
			format = workBook.createDataFormat(); 
		}
		return format;
	}

	public HSSFWorkbook getWorkbook() {
		return workBook;
	}

	public String getSheetName() {
		 if( sheetName == null ) {
	            ValueBinding vbSheetName = getValueBinding("sheetName");
	            if( vbSheetName != null ) {
	                return (String)vbSheetName.getValue(FacesContext.getCurrentInstance());
	            }
	            return null;
	     }
	     return sheetName; 
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	

    
}
