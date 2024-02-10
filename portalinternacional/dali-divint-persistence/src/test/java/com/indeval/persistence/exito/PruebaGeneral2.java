/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

/**
 *
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class PruebaGeneral2 extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(PruebaGeneral2.class);

	public void testCase() {
		String cadenas[] = {"    ", "   asd  asda   ads    asd     asd      asd  ",null,"dasd   asdasd  ", "        asdasd    "};
		for (String cadena : cadenas) {
			log.info("Numero [" + cadena + "]: [" + limpiaEspaciosDobles(cadena) + "]");
		}

	}

	private String limpiaEspaciosDobles(String cadenaOriginal) {
		String retorno = StringUtils.trimToNull(cadenaOriginal);

		if (StringUtils.isNotBlank(retorno)) {
			while (retorno.contains("  ")) {
				retorno = retorno.replaceAll("  ", " ");
			}
		}

		return retorno;
	}
}
