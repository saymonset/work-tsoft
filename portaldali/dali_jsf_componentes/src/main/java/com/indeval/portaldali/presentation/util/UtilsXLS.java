package com.indeval.portaldali.presentation.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;

import com.indeval.portaldali.presentation.jsf.xls.IXLSComponent;
import com.indeval.portaldali.presentation.jsf.xls.XLSColumnComponent;
import com.indeval.portaldali.presentation.jsf.xls.XLSTableComponent;

public class UtilsXLS {

    private static UtilsXLS utils;

    private HSSFCellStyle headerStyle;

    private HSSFCellStyle filterLabelStyle;

    private HSSFCellStyle filterValueStyle;

    private HSSFCellStyle subTitleStyle;

    private HSSFCellStyle tableHeaderStyle;
    
    private Map<String, HSSFCellStyle> tableCellStyles = new HashMap<String, HSSFCellStyle>();

    public static UtilsXLS getInstance() {
        if (utils == null) {
            utils = new UtilsXLS();
        }
        return utils;
    }

    private UtilsXLS() {

    }

    public void clearTableStyles(){
        tableCellStyles = new HashMap<String, HSSFCellStyle>();
    }
    
    
    public void clearStyles(){
        headerStyle = null;
        filterLabelStyle = null;
        filterValueStyle = null;
        subTitleStyle = null;
        tableHeaderStyle = null;
        tableCellStyles = new HashMap<String, HSSFCellStyle>();
    }
    
    public void setCellValue(HSSFCell cell, Object valor) {
        if (valor == null) {
            cell.setCellValue(new HSSFRichTextString(""));
            return;
        }
        if (valor instanceof Boolean) {
            cell.setCellValue(((Boolean) valor).booleanValue());
            return;
        }
        if (valor instanceof Number) {
            cell.setCellValue(((Number) valor).doubleValue());
            return;
        }
        if (valor instanceof Date) {
            cell.setCellValue((Date) valor);
            return;
        }
        cell.setCellValue(new HSSFRichTextString(valor.toString()));
    }

    public int getXLSValueType(Object valor) {
        if (valor == null) {
            return HSSFCell.CELL_TYPE_BLANK;
        }
        if (valor instanceof java.lang.Boolean) {
            return HSSFCell.CELL_TYPE_BOOLEAN;
        }
        if (valor instanceof java.lang.Number) {
            return HSSFCell.CELL_TYPE_NUMERIC;
        }
        if (valor instanceof java.util.Date) {
            return HSSFCell.CELL_TYPE_NUMERIC;
        }
        return HSSFCell.CELL_TYPE_STRING;
    }

    public HSSFCellStyle getHeaderStyle(IXLSComponent component) {
        if (headerStyle == null) {
            headerStyle = component.getWorkbook().createCellStyle();
            HSSFFont font = component.getWorkbook().createFont();
            font.setFontHeightInPoints((short) 16);
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            headerStyle.setFont(font);
        }
        return headerStyle;
    }

    public HSSFCellStyle getFilterLabelStyle(IXLSComponent component) {
        if (filterLabelStyle == null) {
            filterLabelStyle = component.getWorkbook().createCellStyle();
            HSSFFont font = component.getWorkbook().createFont();
            font.setFontHeightInPoints((short) 10);
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            filterLabelStyle.setFont(font);
        }
        return filterLabelStyle;
    }

    public HSSFCellStyle getFilterValueStyle(IXLSComponent component) {
        if (filterValueStyle == null) {
            filterValueStyle = component.getWorkbook().createCellStyle();
            HSSFFont font = component.getWorkbook().createFont();
            font.setFontHeightInPoints((short) 10);
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            font.setItalic(true);
            filterValueStyle.setFont(font);
        }
        return filterValueStyle;
    }

    public HSSFCellStyle getSubTitleStyle(IXLSComponent component) {
        if (subTitleStyle == null) {
            subTitleStyle = component.getWorkbook().createCellStyle();
            HSSFFont font = component.getWorkbook().createFont();
            font.setFontHeightInPoints((short) 12);
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            subTitleStyle.setFont(font);
        }
        return subTitleStyle;
    }

    public HSSFCellStyle getTableHeaderStyle(IXLSComponent component) {
        if (tableHeaderStyle == null) {
            tableHeaderStyle = component.getWorkbook().createCellStyle();
            HSSFFont font = component.getWorkbook().createFont();
            font.setFontHeightInPoints((short) 9);
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.WHITE.index);
            tableHeaderStyle.setFont(font);
            tableHeaderStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            tableHeaderStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
            tableHeaderStyle.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);
            tableHeaderStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            tableHeaderStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            tableHeaderStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            tableHeaderStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            tableHeaderStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

            tableHeaderStyle.setLeftBorderColor(HSSFColor.WHITE.index);
            tableHeaderStyle.setTopBorderColor(HSSFColor.WHITE.index);
            tableHeaderStyle.setBottomBorderColor(HSSFColor.WHITE.index);
            tableHeaderStyle.setRightBorderColor(HSSFColor.WHITE.index);
        }
        return tableHeaderStyle;
    }

    private HSSFCellStyle getCellStyle(XLSColumnComponent component,
            TipoLinea tipo) {
        Integer componentId = component.getCellNumber();
        HSSFCellStyle cellStyle = tableCellStyles.get(componentId
                + tipo.getDesc());
        if (cellStyle == null) {
            cellStyle = component.getWorkbook().createCellStyle();
            cellStyle.setFont(((XLSTableComponent) component.getParent()
                    .getParent()).getFontUsed());
            tableCellStyles.put(componentId + tipo.getDesc(), cellStyle);
        }
        return cellStyle;
    }

    public HSSFCellStyle getOddCellStyle(XLSColumnComponent component) {
        HSSFCellStyle cellStyle = getCellStyle(component, TipoLinea.ODD);
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }
    
    public HSSFCellStyle getRedCellStyle(XLSColumnComponent component) {
        HSSFCellStyle cellStyle = getCellStyle(component, TipoLinea.RED);
        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    public HSSFCellStyle getPairCellStyle(XLSColumnComponent component) {
        HSSFCellStyle cellStyle = getCellStyle(component, TipoLinea.PAIR);
        cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        cellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    public HSSFCellStyle getTableFooterStyle(XLSColumnComponent component) {
        HSSFCellStyle cellStyle = getCellStyle(component, TipoLinea.FOOTER);
        cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        cellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setTopBorderColor(HSSFColor.BLUE_GREY.index);
        return cellStyle;
    }

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(UtilsXLS.class);
    
}

enum TipoLinea{
    PAIR("par"), ODD("non"), FOOTER("footer"), RED("red");
    
    
    private TipoLinea( String desc ){
        this.desc=desc;
    }
    private String desc;
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}

