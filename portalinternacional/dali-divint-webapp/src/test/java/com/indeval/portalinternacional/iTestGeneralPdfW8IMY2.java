package com.indeval.portalinternacional;

import java.io.FileOutputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.indeval.portalinternacional.presentation.controller.beneficiarios.ObtieneFormatoW;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class iTestGeneralPdfW8IMY2 extends TestCase {
	
	public void test() throws Exception {
		InputStream inputStream = iTestGeneralPdfW8IMY2.class.
			getResourceAsStream("/util/fw8imy.pdf");
		if( inputStream == null ) {
			throw new Exception("No se pudo encontrar el archivo");
		}
		PdfReader reader = new PdfReader(inputStream);
		if(reader!=null){
			FileOutputStream fos = new FileOutputStream("c:/registeredW8imy.pdf");
			PdfStamper stamp1 = new PdfStamper(reader, fos);
			AcroFields campos = stamp1.getAcroFields();
			campos.setField("f1-1", "Rafael Ibarra Zendejas"); // Nombre
			campos.setField("f1-2", "MEXICO"); // Pais incorporacion
			campos.setField("c1-1", "Yes"); //Qualified Intermediary
			campos.setField("c1-2", "Yes"); //Non Qualified Intermediary
			campos.setField("c1-3", "Yes"); //3-3
			campos.setField("c1-4", "Yes"); //3-4
			campos.setField("c1-5", "Yes"); //3-5
			campos.setField("c1-6", "Yes"); //3-6
			campos.setField("c1-7", "Yes"); //3-7
			campos.setField("c1-8", "Yes"); //3-8
			
			campos.setField("f1-3", "Dir1-1"); // residence address 1
			campos.setField("f1-4", "Dir1-2"); // residence address 2
			campos.setField("f1-5", "Dir1-3"); // residence address 3
			campos.setField("f1-6", "Dir2-1"); // mailing address 1
			campos.setField("f1-7", "Dir2-2"); // mailing address 2
			campos.setField("f1-8", "Dir2-3"); // mailing address 3
			
			campos.setField("f1-15", "USId number"); // USId number
			campos.setField("c1-9", "Yes"); // US-id Number SSN
			campos.setField("c1-10", "Yes"); // US-id Number EIN
			campos.setField("c1-11", "Yes"); // US-id Number QEIN
			campos.setField("f1-9", "ForeignIdNumber"); // ForeignIdNumber
			campos.setField("f1-10", "Cuentas"); // reference number
			
			campos.setField("c1-12", "Yes"); // 9a
			campos.setField("c1-13", "Yes"); // 9b-1
			campos.setField("f1-11", "9b-2"); // 9b-2
			campos.setField("f1-12", "9b-3"); // 9b-3
			campos.setField("c1-14", "Yes"); // 9c-1
			campos.setField("f1-13", "9b-2"); // 9c-2
			campos.setField("f1-14", "9b-3"); // 9c-3
			
			campos.setField("c1-15", "Yes"); // 10a
			campos.setField("c1-16", "Yes"); // 10b
			campos.setField("c1-17", "Yes"); // 11
			campos.setField("c1-18", "Yes"); // 12
			campos.setField("c1-19", "Yes"); // 13
			campos.setField("c1-20", "Yes"); // 14
			campos.setField("c1-21", "Yes"); // 15
			
			stamp1.close();
			fos.close();
			reader.close();
			
		}
	}
}
