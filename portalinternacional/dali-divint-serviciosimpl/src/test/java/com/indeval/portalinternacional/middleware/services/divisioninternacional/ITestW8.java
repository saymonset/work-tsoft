/*
 * Copyright (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.lang.reflect.Field;
import java.util.Calendar;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.formatosw.FormaW8BENE;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2015;
import com.indeval.portalinternacional.middleware.services.validador.w8bene.ValidatorFormatW8BENEServiceImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BENE;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY2015;
import com.indeval.portalinternacional.persistence.dao.FormatoW8Dao;


/**
 * 
 * @author Abraham Morales
 * 
 */
public class ITestW8 extends BaseDaoTestCase {
	
	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestW8.class);
	
 
	private FormatoW8Service formatoW8Service;
	
	private FormatoW8Dao formatoW8Dao;
	
	private ValidatorFormatW8BENEServiceImpl validatorFormatW8BENEServiceImpl;

	/**
	  * 
	  */
	protected void onSetUp() throws Exception {
		super.onSetUp();

		formatoW8Service = (FormatoW8Service) getBean("formatoW8Service");
		formatoW8Dao = (FormatoW8Dao) getBean("formatoW8Dao");
		validatorFormatW8BENEServiceImpl = (ValidatorFormatW8BENEServiceImpl) getBean("validatorFormatW8BENEService");
	}

	/**
	 * 
	 */
	public void ttestAltaW8BENE(){
		log.debug("============================ entrando a testW8() ===================================");

		FormaW8BENE forma = new FormaW8BENE();

		// Generar estado para el objeto Forma
        Field [] attributes =  forma.getClass().getDeclaredFields();

        for (Field field : attributes) {
            //System.out.println("ATTRIBUTE NAME: " + field.getName() + ", " + field.getGenericType() + ", " + field.getType());

        	if (!field.getName().equalsIgnoreCase("serialVersionUID")){
	        	try {
	            	String fieldType = field.getGenericType().toString();
	            	if (fieldType.indexOf("String")!=-1) {
	            		PropertyUtils.setSimpleProperty(forma, field.getName(), "VALOR AUTOMATICO T01 " + field.getName());
	            	} else if (fieldType.indexOf("boolean")!=-1) {
	            		PropertyUtils.setSimpleProperty(forma, field.getName(), false);
	            	} else if (fieldType.indexOf("long")!=-1) {
	            		PropertyUtils.setSimpleProperty(forma, field.getName(), 1l);
	            	}
	            	//System.out.println("ATTRIBUTE VALUE: " + PropertyUtils.getSimpleProperty(forma, field.getName()));
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
        	}

        }

		String result = formatoW8Service.generarXmlCamposFormato(forma);
		log.debug(result);
		
		FormatoW8BENE formato = new FormatoW8BENE();
		//formato.setCamposFormato(result);
		formato.setCamposFormato("test test test");
		
		formatoW8Dao.save(formato);

	}
	
	/**
	 * 
	 */
	public void testConsultarW8BENE(){
		Long idCamposFormato = 1l;
		FormaW8BENE forma = formatoW8Service.obtenerCamposFormatoW8BENE(idCamposFormato);

		Field [] attributes =  forma.getClass().getDeclaredFields();

        for (Field field : attributes) {
        	if (!field.getName().equalsIgnoreCase("serialVersionUID")){
	        	try {
					System.out.println(field.getName() + ": "  + PropertyUtils.getSimpleProperty(forma, field.getName()));
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        }

	}

	/**
	 * 
	 */
	public void ttestAltaW8IMY2015(){
		log.debug("============================ entrando a testW8() ===================================");

		Calendar cal = Calendar.getInstance();
		
		FormaW8IMY2015 forma = new FormaW8IMY2015();

		// Generar estado para el objeto Forma
        Field [] attributes =  forma.getClass().getDeclaredFields();

        for (Field field : attributes) {
            //System.out.println("ATTRIBUTE NAME: " + field.getName() + ", " + field.getGenericType() + ", " + field.getType());

        	if (!field.getName().equalsIgnoreCase("serialVersionUID")){
	        	try {
	            	String fieldType = field.getGenericType().toString();
	            	if (fieldType.indexOf("String")!=-1) {
	            		PropertyUtils.setSimpleProperty(forma, field.getName(), "Automatic " + field.getName() + " at " + cal.get(Calendar.DAY_OF_WEEK) + "-" + cal.get(Calendar.HOUR_OF_DAY));
	            		//PropertyUtils.setSimpleProperty(forma, field.getName(), null);
	            	} else if (fieldType.indexOf("boolean")!=-1) {
	            		PropertyUtils.setSimpleProperty(forma, field.getName(), true);
	            	} else if (fieldType.indexOf("long")!=-1) {
	            		PropertyUtils.setSimpleProperty(forma, field.getName(), 1l);
	            	}
	            	//System.out.println("ATTRIBUTE VALUE: " + PropertyUtils.getSimpleProperty(forma, field.getName()));
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
        	}

        }

		String result = formatoW8Service.generarXmlCamposFormato(forma);
		log.debug(result);
		
		FormatoW8IMY2015 formato = new FormatoW8IMY2015();
		//formato.setCamposFormato(result);
		//formato.setCamposFormato("test test");
		
		String resultTest = "<camposFormato>"
		+ "<partIcmp1>Automatic partIcmp1 at 3-18</partIcmp1>"
		+ "<partIcmp2>Automatic partIcmp2 at 3-18</partIcmp2>"
		+ "<partIcmp3>Automatic partIcmp3 at 3-18</partIcmp3>"
		+ "<partIcmp4a>true</partIcmp4a>"
		+ "<partIcmp4b>true</partIcmp4b>"
		+ "<partIcmp4c>true</partIcmp4c>"
		+ "<partIcmp4d>true</partIcmp4d>"
		+ "<partIcmp4e>true</partIcmp4e>"
		+ "<partIcmp4f>true</partIcmp4f>"
		+ "<partIcmp4g>true</partIcmp4g>"
		+ "<partIcmp4h>true</partIcmp4h>"
		+ "<partIcmp4i>true</partIcmp4i>"
		+ "<partIcmp5a>true</partIcmp5a>"
		+ "<partIcmp5b>true</partIcmp5b>"
		+ "<partIcmp5c>true</partIcmp5c>"
		+ "<partIcmp5d>true</partIcmp5d>"
		+ "<partIcmp5e>true</partIcmp5e>"
		+ "<partIcmp5f>true</partIcmp5f>"
		+ "<partIcmp5g>true</partIcmp5g>"
		+ "<partIcmp5h>true</partIcmp5h>"
		+ "<partIcmp5i>true</partIcmp5i>"
		+ "<partIcmp5j>true</partIcmp5j>"
		+ "<partIcmp5k>true</partIcmp5k>"
		+ "<partIcmp5l>true</partIcmp5l>"
		+ "<partIcmp5m>true</partIcmp5m>"
		+ "<partIcmp5n>true</partIcmp5n>"
		+ "<partIcmp5o>true</partIcmp5o>"
		+ "<partIcmp5p>true</partIcmp5p>"
		+ "<partIcmp5q>true</partIcmp5q>"
		+ "<partIcmp5r>true</partIcmp5r>"
		+ "<partIcmp5s>true</partIcmp5s>"
		+ "<partIcmp5t>true</partIcmp5t>"
		+ "<partIcmp5u>true</partIcmp5u>"
		+ "<partIcmp5v>true</partIcmp5v>"
		+ "<partIcmp5w>true</partIcmp5w>"
		+ "<partIcmp5x>true</partIcmp5x>"
		+ "<partIcmp5y>true</partIcmp5y>"
		+ "<partIcmp6a>Automatic partIcmp6a at 3-18</partIcmp6a>"
		+ "<partIcmp6b>Automatic partIcmp6b at 3-18</partIcmp6b>"
		+ "<partIcmp6c>Automatic partIcmp6c at 3-18</partIcmp6c>"
		+ "<partIcmp7a>Automatic partIcmp7a at 3-18</partIcmp7a>"
		+ "<partIcmp7b>Automatic partIcmp7b at 3-18</partIcmp7b>"
		+ "<partIcmp7c>Automatic partIcmp7c at 3-18</partIcmp7c>"
		+ "<partIcmp8>Automatic partIcmp8 at 3-18</partIcmp8>"
		+ "<partIcmp8a>true</partIcmp8a>"
		+ "<partIcmp8b>true</partIcmp8b>"
		+ "<partIcmp8c>true</partIcmp8c>"
		+ "<partIcmp8d>true</partIcmp8d>"
		+ "<partIcmp8e>true</partIcmp8e>"
		+ "<partIcmp9>Automatic partIcmp9 at 3-18</partIcmp9>"
		+ "<partIcmp10>Automatic partIcmp10 at 3-18</partIcmp10>"
		+ "<partIIcmp11a>true</partIIcmp11a>"
		+ "<partIIcmp11b>true</partIIcmp11b>"
		+ "<partIIcmp11c>true</partIIcmp11c>"
		+ "<partIIcmp11d>true</partIIcmp11d>"
		+ "<partIIcmp11e>true</partIIcmp11e>"
		+ "<partIIcmp12a>Automatic partIIcmp12a at 3-18</partIIcmp12a>"
		+ "<partIIcmp12b>Automatic partIIcmp12b at 3-18</partIIcmp12b>"
		+ "<partIIcmp12c>Automatic partIIcmp12c at 3-18</partIIcmp12c>"
		+ "<partIIcmp13>Automatic partIIcmp13 at 3-18</partIIcmp13>"
		+ "<partIII>true</partIII>"
		+ "<partIIIcmp14a>true</partIIIcmp14a>"
		+ "<partIIIcmp14b>true</partIIIcmp14b>"
		+ "<partIIIcmp14c>true</partIIIcmp14c>"
		+ "<partIIIcmp14c1>Automatic partIIIcmp14c1 at 3-18</partIIIcmp14c1>"
		+ "<partIIIcmp14d>true</partIIIcmp14d>"
		+ "<partIIIcmp14d1>Automatic partIIIcmp14d1 at 3-18</partIIIcmp14d1>"
		+ "<partIIIcmp14e>true</partIIIcmp14e>"
		+ "<partIIIcmp14e1>Automatic partIIIcmp14e1 at 3-18</partIIIcmp14e1>"
		+ "<partIIIcmp14ei>true</partIIIcmp14ei>"
		+ "<partIIIcmp14eii>true</partIIIcmp14eii>"
		+ "<partIIIcmp14f>true</partIIIcmp14f>"
		+ "<partIV>true</partIV>"
		+ "<partIVcmp15a>true</partIVcmp15a>"
		+ "<partIVcmp15b>true</partIVcmp15b>"
		+ "<partIVcmp15c>true</partIVcmp15c>"
		+ "<partIVcmp15d>true</partIVcmp15d>"
		+ "<partV>true</partV>"
		+ "<partVcmp16a>true</partVcmp16a>"
		+ "<partVcmp16b>true</partVcmp16b>"
		+ "<partVcmp16c>true</partVcmp16c>"
		+ "<partVI>true</partVI>"
		+ "<partVIcmp17a>true</partVIcmp17a>"
		+ "<partVIcmp17b>true</partVIcmp17b>"
		+ "<partVIcmp17c>true</partVIcmp17c>"
		+ "<partVII>true</partVII>"
		+ "<partVIIcmp18>true</partVIIcmp18>"
		+ "<partVIII>true</partVIII>"
		+ "<partVIIIcmp19>true</partVIIIcmp19>"
		+ "<partIX>true</partIX>"
		+ "<partIXcmp20>true</partIXcmp20>"
		+ "<partX>true</partX>"
		+ "<partXcmp21a>Automatic partXcmp21a at 3-18</partXcmp21a>"
		+ "<partXcmp21b>true</partXcmp21b>"
		+ "<partXcmp21c>true</partXcmp21c>"
		+ "<partXI>true</partXI>"
		+ "<partXIcmp22a>true</partXIcmp22a>"
		+ "<partXIcmp22b>true</partXIcmp22b>"
		+ "<partXIcmp22c>true</partXIcmp22c>"
		+ "<partXII>true</partXII>"
		+ "<partXIIcmp23>true</partXIIcmp23>"
		+ "<partXIII>true</partXIII>"
		+ "<partXIIIcmp24>true</partXIIIcmp24>"
		+ "<partXIV>true</partXIV>"
		+ "<partXIVcmp25a>Automatic partXIVcmp25a at 3-18</partXIVcmp25a>"
		+ "<partXIVcmp25b>true</partXIVcmp25b>"
		+ "<partXV>true</partXV>"
		+ "<partXVcmp26>true</partXVcmp26>"
		+ "<partXVI>true</partXVI>"
		+ "<partXVIcmp27a>true</partXVIcmp27a>"
		+ "<partXVIcmp27b>true</partXVIcmp27b>"
		+ "<partXVIcmp27c>true</partXVIcmp27c>"
		+ "<partXVII>true</partXVII>"
		+ "<partXVIIcmp28>true</partXVIIcmp28>"
		+ "<partXVIII>true</partXVIII>"
		+ "<partXVIIIcmp29a>true</partXVIIIcmp29a>"
		+ "<partXVIIIcmp29b>Automatic partXVIIIcmp29b at 3-18</partXVIIIcmp29b>"
		+ "<partXVIIIcmp29c>Automatic partXVIIIcmp29c at 3-18</partXVIIIcmp29c>"
		+ "<partXVIIIcmp29d>Automatic partXVIIIcmp29d at 3-18</partXVIIIcmp29d>"
		+ "<partXIX>true</partXIX>"
		+ "<partXIXcmp30a>true</partXIXcmp30a>"
		+ "<partXIXcmp30b>true</partXIXcmp30b>"
		+ "<partXIXcmp30c>true</partXIXcmp30c>"
		+ "<partXIXcmp30d>true</partXIXcmp30d>"
		+ "<partXIXcmp30e>true</partXIXcmp30e>"
		+ "<partXIXcmp30f>true</partXIXcmp30f>"
		+ "<partXX>true</partXX>"
		+ "<partXXcmp31>true</partXXcmp31>"
		+ "<partXXI>true</partXXI>"
		+ "<partXXIcmp32a>true</partXXIcmp32a>"
		+ "<partXXIcmp32b>Automatic partXXIcmp32b at 3-18</partXXIcmp32b>"
		+ "<partXXII>true</partXXII>"
		+ "<partXXIIcmp33a>true</partXXIIcmp33a>"
		+ "<partXXIIcmp33b>Automatic partXXIIcmp33b at 3-18</partXXIIcmp33b>"
		+ "<partXXIII>true</partXXIII>"
		+ "<partXXIIIcmp34a>true</partXXIIIcmp34a>"
		+ "<partXXIIIcmp34a1>Automatic partXXIIIcmp34a1 at 3-18</partXXIIIcmp34a1>"
		+ "<partXXIIIcmp34b>true</partXXIIIcmp34b>"
		+ "<partXXIIIcmp34b1>Automatic partXXIIIcmp34b1 at 3-18</partXXIIIcmp34b1>"
		+ "<partXXIIIcmp34b2>Automatic partXXIIIcmp34b2 at 3-18</partXXIIIcmp34b2>"
		+ "<partXXIV>true</partXXIV>"
		+ "<partXXIVcmp35>true</partXXIVcmp35>"
		+ "<partXXV>true</partXXV>"
		+ "<partXXVcmp36>true</partXXVcmp36>"
		+ "<partXXVI>true</partXXVI>"
		+ "<partXXVIcmp37>true</partXXVIcmp37>"
		+ "<partXXVII>true</partXXVII>"
		+ "<partXXVIIcmp38>Automatic partXXVIIcmp38 at 3-18</partXXVIIcmp38>"
		+ "<partXXVIIcmp39>true</partXXVIIcmp39>"
		+ "<partXXVIII>true</partXXVIII>"
		+ "<partXXVIIIa>Automatic partXXVIIIa at 3-18</partXXVIIIa>"
		+ "<partXXVIIIb>Automatic partXXVIIIb at 3-18</partXXVIIIb>"
		+ "</camposFormato>";
		formato.setCamposFormato(resultTest);
		
	}

	/**
	 * 
	 */
	public void ttestConsultarW8IMY2015(){
		Long idCamposFormato = 2l;
		FormaW8IMY2015 forma = formatoW8Service.obtenerCamposFormatoW8IMY2015(idCamposFormato);

		Field [] attributes =  forma.getClass().getDeclaredFields();

        for (Field field : attributes) {
        	if (!field.getName().equalsIgnoreCase("serialVersionUID")){
	        	try {
					System.out.println(field.getName() + ": "  + PropertyUtils.getSimpleProperty(forma, field.getName()));
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        }

	}

	/**
	 * 
	 */
	public void ttestValidadorW8BENE(){
		
		Beneficiario beneficiario = new Beneficiario();
		FormatoW8BENE formatoW8BENE = new FormatoW8BENE();
		FormaW8BENE forma = new FormaW8BENE();
		
		forma.setPartIcmp1("");
		String camposFormato = formatoW8Service.generarXmlCamposFormato(forma);

		formatoW8BENE.setCamposFormato(camposFormato);
		beneficiario.setFormatoW8BENE(formatoW8BENE);

		validatorFormatW8BENEServiceImpl.validaFormatoW(beneficiario);
	}

	
	
	
	
}
