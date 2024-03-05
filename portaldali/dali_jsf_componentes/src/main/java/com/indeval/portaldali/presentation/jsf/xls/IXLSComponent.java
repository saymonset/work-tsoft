package com.indeval.portaldali.presentation.jsf.xls;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface IXLSComponent {
    public HSSFSheet getCurrentSheet();
    public void createNewSheet( String name );
    public HSSFWorkbook getWorkbook();
    public short getNextRow();
    public short getCurrentRow();
    public HSSFDataFormat getDataFormat();
}
