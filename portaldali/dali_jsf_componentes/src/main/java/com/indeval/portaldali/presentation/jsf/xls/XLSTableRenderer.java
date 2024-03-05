package com.indeval.portaldali.presentation.jsf.xls;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.indeval.portaldali.presentation.util.UtilsXLS;

public class XLSTableRenderer extends Renderer {
    private Logger logger = LoggerFactory.getLogger(XLSTableRenderer.class);
    

    public XLSTableRenderer() {
        super();
    }

    private boolean renderHeader = false;
    private boolean renderFooter = false;

    public void encodeBegin(FacesContext facesContext, UIComponent component)
            throws IOException {
    	UtilsXLS util = UtilsXLS.getInstance(); 
        util.clearTableStyles();
        renderHeader = false;
        renderFooter = false;
        XLSTableComponent tableComponent = (XLSTableComponent) component;
        List rowChildren = tableComponent.getChildren();

        if (tableComponent.getChildCount() > 0) {
            for (int i = 0; i < rowChildren.size(); i++) {
                XLSRowComponent rowComponent = (XLSRowComponent) rowChildren.get(i);
                if( rowComponent.isRendered() ) {
                    renderHeader = renderHeader || rowComponent.hasHeaders();
                    renderFooter = renderFooter || rowComponent.hasFooters();
                }
            }
            if (renderHeader) {
                for (int i = 0; i < rowChildren.size(); i++) {
                    XLSRowComponent rowComponent = (XLSRowComponent) rowChildren.get(i);
                    if( rowComponent.isRendered() ) {
                        logger.trace("numRow: " + tableComponent.getNextRow());
                    	HSSFRow headerRow = tableComponent.getCurrentSheet().createRow(tableComponent.getNextRow());
                        List columnChildren = rowComponent.getChildren();
                        int j = 0;
                        for (j = 0; j < rowComponent.getChildCount(); j++) {
                            XLSColumnComponent columnComponent = (XLSColumnComponent) columnChildren.get(j);
                            if( columnComponent.isRendered() ) {
                            	columnComponent.getHeader();
                            	HSSFCell cell = headerRow.createCell((short)j);
                            	util.setCellValue(cell, columnComponent.getHeader());
                                cell.setCellStyle(util.getTableHeaderStyle(tableComponent));
                            }
    
                        }
                    }
                }
            }
        }
    }

    
    
    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {
        try {
            XLSTableComponent tableComponent = (XLSTableComponent) component;
            List rowChildren = tableComponent.getChildren();
            UtilsXLS util = UtilsXLS.getInstance(); 
            
            if (renderFooter) {
                for (int i = 0; i < rowChildren.size(); i++) {
                    XLSRowComponent rowComponent = (XLSRowComponent) rowChildren.get(i);
                    if( rowComponent.isRendered() ) {
                    	HSSFRow footerRow = tableComponent.getCurrentSheet().createRow(tableComponent.getNextRow());
                        List columnChildren = rowComponent.getChildren();
                        int j = 0;
                        for (j = 0; j < rowComponent.getChildCount(); j++) {
                            XLSColumnComponent columnComponent = (XLSColumnComponent) columnChildren.get(j);
                            if( columnComponent.isRendered() ) {
                            	HSSFCell cell = footerRow.createCell((short)j);
                            	Object valor = columnComponent.getFooter();
                            	util.setCellValue(cell, valor);
                            	HSSFCellStyle style = util.getTableFooterStyle(columnComponent);
                            	int cellType = util.getXLSValueType(valor); 
                            	cell.setCellType(cellType);
                            	if( columnComponent.getPattern() != null && cellType == HSSFCell.CELL_TYPE_NUMERIC ){
                                	HSSFDataFormat format = columnComponent.getDataFormat();
                                	style.setDataFormat(format.getFormat(columnComponent.getPattern()));
                                }
                                cell.setCellStyle(style);
                            }
    
                        }
                    }
                }
            }
            tableComponent.getNextRow();
            
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

    }

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws java.io.IOException {
        XLSTableComponent tableComponent = (XLSTableComponent) component;
        if (tableComponent.getChildCount() > 0) {
            List rowChildren = tableComponent.getChildren();
            Object[] datos = null;

            if (tableComponent.getValue().getClass().isArray()) {
                datos = (Object[]) tableComponent.getValue();
            } else {
            	Object valor = tableComponent.getValue(); 
            	if ( valor instanceof List) {
            		datos = ((List) tableComponent.getValue()).toArray();
               } else if ( valor instanceof Collection ) {
                    datos = ((Collection) tableComponent.getValue()).toArray();
               } else if (valor instanceof Map) {
                    datos = ((Map) tableComponent.getValue()).values().toArray();
               }
            }
            
            if (datos == null) {
                datos = new Object[1];
                datos[0] = tableComponent.getValue();
            }

            if (datos != null ) {
                Map reqMap = context.getExternalContext().getRequestMap();
                for (int i = 0; i < datos.length; i++) {
                    reqMap.put(tableComponent.getVar(), datos[i]);
                    for (int j = 0; j < rowChildren.size(); j++) {
                    	
                        XLSRowComponent row = (XLSRowComponent) rowChildren.get(j);
                        row.setOddRow(new Boolean( i%2!=0 ));
                        row.encodeBegin(context);
                        row.encodeChildren(context);
                        row.encodeEnd(context);
                    }
                }
                reqMap.remove(tableComponent.getVar());
            }
        }
    }

}
