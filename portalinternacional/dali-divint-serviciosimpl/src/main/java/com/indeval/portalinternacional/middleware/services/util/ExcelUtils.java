package com.indeval.portalinternacional.middleware.services.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExcelUtils {
    
	private final static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
	/**
	 * m&eacute;todo para convertir un InputStream a un HSSFWorkbook
	 * @param InputStream
	 * @return HSSFWorkbook
	 * @throws IOException
	 * */
	public static HSSFWorkbook convertInputStreamToHSSFWorkbook(InputStream inputStream)throws IOException{
		HSSFWorkbook wb = null;
		if(inputStream != null){
			wb = new HSSFWorkbook(POIFSFileSystem.createNonClosingInputStream(inputStream));
		}
		return wb;
	}
	
	/**
	 * m&eacute;todo para convertir un HSSFWorkbook a un HSSFSheet
	 * @param  HSSFWorkbook
	 * @return HSSFSheet
	 * */
	public static HSSFSheet convertHSSFWorkbookToHSSFSheet(HSSFWorkbook wb ){
		HSSFSheet sheet = null;
		if(wb != null){
			sheet = wb.getSheetAt(0);
		}
		return sheet;		
	} 
	
	/**
	 * m&eacute;todo para determinar el rango de filas con informaci&oacute;n
	 * @param HSSFSheet
	 * @return int[], [0] = primera fila y [1]= ultima fila
	 * */
	public static int[] devuelveRangoDeFilas(HSSFSheet sheet){		
		int[] rangoFilas = null;
		if(sheet != null){			
			int primeraFila = sheet.getFirstRowNum();						
			int ultimaFila = sheet.getLastRowNum();		
			rangoFilas = new int[2];
			rangoFilas[0] = primeraFila;
			rangoFilas[1] = ultimaFila; 
		}
		return rangoFilas;
	}
	
	/**
	 * m&eacute;todo para validar que tenga al menos una fila con informaci&oacute;n y que la primera fila tenga informaci&oacute;n
	 * @param HSSFSheet, int[]rangoFilas
	 * @throws IOException
	 * */
	public static void validaFormatoExcel(HSSFSheet sheet,int[]rangoFilas) throws IOException{						
		if(rangoFilas[0] == 0 && rangoFilas[1] == 0){
			if(sheet.getRow(0) == null){				
				throw new IOException("Archivo con Formato Incorrecto");
			}			
		}					
		if(rangoFilas[0] > 0){		
			throw new IOException("Archivo con Formato Incorrecto");
        }											
	}
	
	/**
	 * m&eacute;todo para validar que tenga al menos una fila con informaci&oacute;n y que la primera fila tenga informaci&oacute;n
	 * @param HSSFSheet, int[]rangoFilas
	 * @throws IOException
	 * */
	public static void validaFormatoExcelBeneficiarios(HSSFSheet sheet,int[]rangoFilas) throws IOException{						
		if(rangoFilas[0] == 0 && rangoFilas[1] == 0){
			if(sheet.getRow(0) == null){				
				throw new IOException("Archivo con Formato Incorrecto");
			}			
		}
		
		if(rangoFilas[0] > 0){		
			throw new IOException("Archivo con Formato Incorrecto");
        }
		
		if(rangoFilas[0] == 0 && (rangoFilas[1] == 1 || rangoFilas[1] == 0)){
			if(sheet.getRow(1) == null){				
				throw new IOException("Archivo con Formato Incorrecto");
			}			
		}
	}
	/**
	 * m&eacute;todo para validar que el numero maximo de registros sea de 1,000
	 * @param int[]rangoFilas
	 * @throws IOException
	 * */
	public static void validaRangoMaximoCargaBeneficiarios(int[]rangoFilas)throws IOException{
		int valorMaximoRegistros = 0;
		if(rangoFilas[1] > rangoFilas[0]){
			valorMaximoRegistros = rangoFilas[1] - rangoFilas[0];  
		}
		if(valorMaximoRegistros > 1001){
			throw new IOException("Archivo excede el número de registros permitidos, 1000 Registros ");
		}
	}
	
	
	/**
	 * m&eacute;todo para eliminar los espacios y convertir a may&uacute;sculas una cadena
	 * @param String
	 * @return String
	 * */
	public static String transformaCadena(String cadena) {
	        return StringUtils.trim(StringUtils.upperCase(cadena));
	}
	
	
	/**
	 * m&eacute;todo para convertir el valor de una celda a cadena
	 * @param HSSFCell
	 * @param String
	 * */
	public static String getColumnAsString(HSSFCell cell) {
	        String retorno = null;
	        if (cell != null) {	        	
	        	switch(cell.getCellType()){
	        		case HSSFCell.CELL_TYPE_BLANK:
	        			break;
	        		case HSSFCell.CELL_TYPE_BOOLEAN: 
	        			Boolean valorCeldaBolean = cell.getBooleanCellValue(); 
	        			retorno = valorCeldaBolean.toString();
	        			break;
	        		case HSSFCell.CELL_TYPE_FORMULA: 
	        			retorno = StringUtils.trimToNull(cell.getRichStringCellValue().getString()); 
	        			break;
	        		case HSSFCell.CELL_TYPE_NUMERIC:
	        			retorno = Double.toString(cell.getNumericCellValue());
	        			break;
	        		case HSSFCell.CELL_TYPE_STRING: 
	        			retorno = StringUtils.trimToNull(cell.getRichStringCellValue().getString()); 
	        			break;
	        		default:
	        			break;	
	        	}	        		            
	        }
	        if(logger.isDebugEnabled()){
	        	logger.debug("Recuperando de la hoja de excel el valor :"+retorno);
	        }
	        return retorno;
	}

}
