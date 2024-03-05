/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Oct 21, 2008
 */
package com.indeval.persistence.dao;

import java.text.DecimalFormat;

import junit.framework.TestCase;

import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;

/**
 * 
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public class TestDecimalFormat extends TestCase {
	
	public void testDecimalFormat() {
    	DecimalFormat df = new DecimalFormat(DaliConstants.FORMATO_MONEDA_LARGO_8_DECIMALES);
    	System.out.println("Formato: " + df.format(8.64556685778));
    }
}
