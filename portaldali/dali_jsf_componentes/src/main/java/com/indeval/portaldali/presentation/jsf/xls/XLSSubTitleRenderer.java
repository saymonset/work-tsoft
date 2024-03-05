package com.indeval.portaldali.presentation.jsf.xls;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.indeval.portaldali.presentation.util.UtilsXLS;

public class XLSSubTitleRenderer extends Renderer {
    Logger logger = LoggerFactory.getLogger(XLSSubTitleRenderer.class); 
    public XLSSubTitleRenderer() {
        super();
    }

    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent)
            throws IOException {
        try{
        	UtilsXLS styles = UtilsXLS.getInstance();
            XLSSubTitleComponent component = (XLSSubTitleComponent) uiComponent;
            HSSFSheet sheet = component.getCurrentSheet();
            HSSFRow row = sheet.createRow(component.getNextRow());
            HSSFCell cell = row.createCell((short)0);
            int cellType = styles.getXLSValueType(component.getValue());
            cell.setCellType( cellType );
            styles.setCellValue(cell, component.getValue());
            HSSFCellStyle subTitleStyle =  styles.getSubTitleStyle(component);
            
            if( component.getPattern() != null && cellType == HSSFCell.CELL_TYPE_NUMERIC ){
            	HSSFDataFormat format = component.getDataFormat();
            	subTitleStyle.setDataFormat(format.getFormat(component.getPattern()));
            }
            cell.setCellStyle( subTitleStyle );
            component.getNextRow();
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
