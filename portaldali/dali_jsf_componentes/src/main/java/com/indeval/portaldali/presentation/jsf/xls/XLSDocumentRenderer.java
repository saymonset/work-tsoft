package com.indeval.portaldali.presentation.jsf.xls;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.indeval.portaldali.presentation.jsf.converters.TitleDateConverter;
import com.indeval.portaldali.presentation.util.UtilsXLS;

public class XLSDocumentRenderer extends Renderer {
    public XLSDocumentRenderer() {
        super();
    }
    
    private void encodeHeader(FacesContext facesContext, XLSDocumentComponent component)
            throws IOException {
    	UtilsXLS styles = UtilsXLS.getInstance();
        styles.clearStyles();
    	HSSFSheet sheet = component.getCurrentSheet();
    	HSSFRow row = sheet.createRow( component.getNextRow() );
    	HSSFCell cell =  row.createCell((short)0);
    	HSSFRichTextString cellvalue = new HSSFRichTextString(component.getDocumentTitle());
    	cell.setCellValue(cellvalue);
    	cell.setCellStyle( styles.getHeaderStyle(component) );
    	
    	if( component.getInstitucion() != null || component.getFecha() != null ){
    		component.getNextRow();
    	}
    	
    	if( component.getInstitucion() != null ){
    		row = sheet.createRow( component.getNextRow() );
    		cell =  row.createCell((short)0);
    		cellvalue = new HSSFRichTextString("Instituci\u00F3n:");
    		cell.setCellValue(cellvalue);
        	cell.setCellStyle( styles.getFilterLabelStyle(component) );
        	
    		cell =  row.createCell((short)1);
    		cellvalue = new HSSFRichTextString(component.getInstitucion());
    		cell.setCellValue(cellvalue);
    		cell.setCellStyle(styles.getFilterValueStyle(component));
    	}
    	if( component.getFecha() != null ){
    		row = sheet.createRow( component.getNextRow() );
    		cell =  row.createCell((short)0);
    		cellvalue = new HSSFRichTextString("Fecha:");
    		cell.setCellValue(cellvalue);
        	cell.setCellStyle( styles.getFilterLabelStyle(component) );
        	
        	cell =  row.createCell((short)1);
        	TitleDateConverter tdc = new TitleDateConverter();
        	cellvalue = new HSSFRichTextString(tdc.getAsString(facesContext, component, component.getFecha()) );
    		cell.setCellValue(cellvalue);
    		cell.setCellStyle(styles.getFilterValueStyle(component));
    	}
    	component.getNextRow();
    }

    
    public void encodeBegin(FacesContext facesContext, UIComponent component)
            throws IOException {
        XLSDocumentComponent xlsComponent = (XLSDocumentComponent) component;
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.reset();
    	response.resetBuffer();
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Disposition", "attachment; filename=" + xlsComponent.getDocumentName() + ".xls");
        response.setContentType("application/vnd.ms-excel");
        encodeHeader(facesContext, xlsComponent);
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {
        XLSDocumentComponent xlsComponent = (XLSDocumentComponent) component;
        try {
        	HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            ServletOutputStream out = response.getOutputStream();
            xlsComponent.getWorkBook().write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws java.io.IOException {
        if (component.getChildCount() > 0) {
            Iterator it = component.getChildren().iterator();
            while (it.hasNext()) {
                UIComponent child = (UIComponent) it.next();
                child.encodeBegin(context);
                child.encodeChildren(context);
                child.encodeEnd(context);
            }
        }
    }
}
