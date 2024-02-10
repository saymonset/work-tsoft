package com.indeval.portalinternacional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;

import junit.framework.TestCase;

public class iTestGeneralPdf extends TestCase {
	
	public void test() throws Exception {
		Logger log = LoggerFactory.getLogger(this.getClass());
		InputStream inputStream = iTestGeneralPdf.class.getResourceAsStream("/util/fw9.pdf");
		if( inputStream == null ) {
			throw new Exception("No se pudo encontrar el archivo");
		}
		PdfReader reader = new PdfReader(inputStream);
		if(reader!=null){
//			PdfStamper stamp1 = new PdfStamper(reader, new FileOutputStream("c:/registered.pdf"));
			AcroFields campos = reader.getAcroFields();
			HashMap mapa = campos.getFields();
			Set<String> llaves = mapa.keySet();
			List<String> listaLLaves = new ArrayList<String>();
			for(String llave : llaves) {
				if( llave.startsWith("") ) {
//					log.info(llave + "-[" + campos.getFieldType(llave) + "]" );
					listaLLaves.add(llave);
				}
//				log.info("--Valor: [" + campos.getField(llave) + "]");
//				float posiciones[] = campos.getFieldPositions(llave);
//				for(float pos : posiciones) {
//					log.info("--" + pos);
//				}
			}
			Collections.sort(listaLLaves);
			for(String llave : listaLLaves) {
				log.info(llave);
			}
			
			log.info("Numero: [" + listaLLaves.size() + "]");
//			log.info("Tamaño: [" + llaves.size() + "]");
//			log.info("Tipo checkbox: [" + AcroFields.FIELD_TYPE_CHECKBOX + "]");
//			log.info("Tipo texo: [" + AcroFields.FIELD_TYPE_TEXT + "]");
//			log.info("Tipo checkbox: [" + AcroFields.FIELD_TYPE_CHECKBOX + "]");
		} else {
			log.error("No se puede crear el Reader.");
		}
	}
}
