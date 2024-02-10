/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.beneficiario;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;

/**
 * 
 * @author Rafael Ibarra
 * 
 */
public class ITestEncriptacion_1 extends BaseDaoTestCase {

	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestEncriptacion_1.class);

	private PBEStringEncryptor stringEncryptor;

	/**
	  * 
	  */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		stringEncryptor = (PBEStringEncryptor) getBean("strongEncryptor");
	}

	/**
	  * 
	  *
	  */
	public void testEncriptacion1() throws Exception {
		log.info("Ejecutando prueba testEncriptacion1()");
		assertNotNull(stringEncryptor);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row;
		HSSFCell cell;
		String cadena = "El secretario de Educación Pública, Alonso Lujambio, anunció que en breve " +
				"los maestros de preescolar, primaria y secundaria con mejor desempeño del país " +
				"obtendrán un estímulo económico de entre dos mil y 20 mil pesos. En conferencia de " +
				"prensa en la que hizo un balance de su primer año de gestión al frente de la " +
				"Secretaría de Educación Pública (SEP) , el funcionario explicó que para este programa" +
				" de estímulos se cuenta con un presupuesto de 900 millones de pesos.";
		row = sheet.createRow(0);
		cell = row.createCell((short)0);
		cell.setCellValue("Tamaño original");
		cell = row.createCell((short)1);
		cell.setCellValue("Tamaño encriptado");
		String subcadena, cadenaEncriptada;
		for(int i=1; i < cadena.length(); i++) {
			row = sheet.createRow(i);
			subcadena = cadena.substring(0,i);
			cadenaEncriptada = stringEncryptor.encrypt(subcadena);
			log.info("Tamaño original: [" + subcadena.length() + "] - Tamaño encriptada: [" + cadenaEncriptada.length() + "]");
			cell = row.createCell((short)0);
			cell.setCellValue(subcadena.length());
			cell = row.createCell((short)1);
			cell.setCellValue(cadenaEncriptada.length());
		}
		FileOutputStream fos = new FileOutputStream("/soft/logs/encriptacion.xls");
		workbook.write(fos);
		fos.close();

	}

}
