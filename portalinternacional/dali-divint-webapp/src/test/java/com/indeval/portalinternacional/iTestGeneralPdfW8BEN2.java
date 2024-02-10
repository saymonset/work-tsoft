package com.indeval.portalinternacional;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

import junit.framework.TestCase;

public class iTestGeneralPdfW8BEN2 extends TestCase {
	
	public void test() throws Exception {
		Logger log = LoggerFactory.getLogger(this.getClass());
		InputStream inputStream = iTestGeneralPdfW8BEN2.class.
			getResourceAsStream("/util/fw8ben.pdf");
		if( inputStream == null ) {
			throw new Exception("No se pudo encontrar el archivo");
		}
		PdfReader reader = new PdfReader(inputStream);
		if(reader!=null){
			FileOutputStream fos = new FileOutputStream("c:/registered6.pdf");
			PdfStamper stamp1 = new PdfStamper(reader, fos);
			AcroFields campos = stamp1.getAcroFields();
			campos.setField("f1-1", "Rafael Ibarra Zendejas"); // Nombre
			campos.setField("f1-2", "MEXICO"); // Pais incorporacion
			campos.setField("c1-1", "Yes"); //3-1
			campos.setField("c1-2", "Yes"); //3-2
			campos.setField("c1-3", "Yes"); //3-3
			campos.setField("c1-4", "Yes"); //3-4
			campos.setField("c1-5", "Yes"); //3-5
			campos.setField("c1-6", "Yes"); //3-6
			campos.setField("c1-7", "Yes"); //3-7
			campos.setField("c1-8", "Yes"); //3-8
			campos.setField("c1-9", "Yes"); //3-9
			campos.setField("c1-10", "Yes"); //3-10
			campos.setField("c1-6a", "Yes"); //3-11
			campos.setField("c1-7a", "Yes"); //3-12
			campos.setField("c1-8a", "Yes"); //3-13
			
			campos.setField("f1-3", "Dir1-1"); // residence address 1
			campos.setField("f1-4", "Dir1-2"); // residence address 2
			campos.setField("f1-5", "Dir1-3"); // residence address 3
			campos.setField("f1-6", "Dir2-1"); // mailing address 1
			campos.setField("f1-7", "Dir2-2"); // mailing address 2
			campos.setField("f1-7a", "Dir2-3"); // mailing address 3
			campos.setField("f1-8", "USId number"); // USId number
			campos.setField("c1-12", "Yes"); // US-id Number SSN
			campos.setField("c1-13", "Yes"); // US-id Number EIN
			campos.setField("f1-9", "ForeignIdNumber"); // ForeignIdNumber
			campos.setField("f1-10", "Cuentas"); // reference number
			
			campos.setField("c1-14", "Yes"); // 9a-1
			campos.setField("f1-11", "MEXICO 2"); // 9a-2
			campos.setField("c1-15", "Yes"); // 9b-1
			campos.setField("c1-16", "Yes"); // 9c-1
			campos.setField("c1-17", "Yes"); // 9d-1
			campos.setField("c1-18", "Yes"); // 9e-1
			
			campos.setField("f1-14", "10-1"); // 10-article
			campos.setField("f1-12", "10-2"); // 10-rate
			campos.setField("f1-15", "10-3"); // 10-income
			campos.setField("f1-13", "10-4-1"); // 10-reasons1
			campos.setField("f1-16", "10-4-2"); // 10-reasons2
			campos.setField("f1-17", "10-4-3"); // 10-reasons2
			
			campos.setField("c1-19", "Yes"); // 11
			
			campos.setField("f1-18", "Capacity"); // Capacity

			stamp1.close();
			fos.close();
			reader.close();
			
		}
	}
}
