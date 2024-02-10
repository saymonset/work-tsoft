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

public class iTestGeneralPdfW9 extends TestCase {
	
	public void test() throws Exception {
		InputStream inputStream = iTestGeneralPdfW9.class.
			getResourceAsStream("/util/fw9.pdf");
		if( inputStream == null ) {
			throw new Exception("No se pudo encontrar el archivo");
		}
		PdfReader reader = new PdfReader(inputStream);
		if(reader!=null){
			FileOutputStream fos = new FileOutputStream("c:/registeredW9.pdf");
			PdfStamper stamp1 = new PdfStamper(reader, fos);
			AcroFields campos = stamp1.getAcroFields();
			campos.setField("f1_01(0)", "Rafael Ibarra Zendejas"); // Name
			campos.setField("f1_02(0)", "Bussiness Name"); // Bussiness Name
			campos.setField("c1_01(0)", "Yes"); // Individual
			campos.setField("c1_02(0)", "Yes"); // Corporation
			campos.setField("c1_03(0)", "Yes"); // Parternship
			campos.setField("c1_04(0)", "Yes"); // Other
			campos.setField("c1_05(0)", "Yes"); // Limited
			campos.setField("c1_06(0)", "Yes"); // Exempt payee
			
			campos.setField("f1_03(0)", "Other desc"); // Other desc
			campos.setField("f1_18(0)", "X"); // limited desc
			campos.setField("f1_04(0)", "Addres number"); // Addres number
			campos.setField("f1_05(0)", "City"); // City
			
			campos.setField("f1_06(0)", "Requester Name"); // Requester Name
			campos.setField("f1_07(0)", "List Account"); // List Account
			campos.setField("f1_08(0)", "s1"); // ssn1
			campos.setField("f1_11(0)", "s2"); // ssn2
			campos.setField("f1_13(0)", "s3"); // ssn3
			
			campos.setField("f1_17(0)", "e1"); // ein1
			campos.setField("f1_19(0)", "e2"); // ein2
			
			stamp1.close();
			fos.close();
			reader.close();
			
		}
	}
}
