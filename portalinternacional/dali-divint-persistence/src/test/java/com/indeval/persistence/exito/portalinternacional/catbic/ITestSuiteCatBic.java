/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.catbic;

import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;

import junit.framework.TestFailure;
import junit.framework.TestResult;
import junit.framework.TestSuite;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * 
 */
@SuppressWarnings({"unchecked"})
public class ITestSuiteCatBic extends BaseDaoTestCase {

	/** Objeto de loggeo */
	private static final Logger log = LoggerFactory.getLogger(ITestSuiteCatBic.class);

	/**
	 * TestCase para testSuite()
	 */
	public void testSuite() {

		log.debug("Entrando a testSuite()");

		TestSuite testSuitePosicionNombrada = new TestSuite();
		TestResult resultados = new TestResult();

		testSuitePosicionNombrada.addTestSuite(ITestFindCatBic_1.class);
		testSuitePosicionNombrada.addTestSuite(ITestFindCustodios_1.class);

		testSuitePosicionNombrada.run(resultados);

		if (!resultados.wasSuccessful()) {

			log.debug("Los errores fueron los siguientes : ");

			TestFailure falla = null;

			if(resultados.failureCount() > 0) {
				Enumeration fallos = resultados.failures();

				for (; fallos.hasMoreElements();) {
					falla = (TestFailure) fallos.nextElement();

					log.debug("Falla [" + falla.failedTest() + "]: fallo ["
							+ falla.trace() + "]");
				}
			}

			if(resultados.errorCount() > 0) {
				Enumeration errores = resultados.errors();

				for (; errores.hasMoreElements();) {
					falla = (TestFailure) errores.nextElement();

					log.debug("Error [" + falla.failedTest() + "]: error ["
							+ falla.trace() + "]");
				}
			}
			
			assertTrue(resultados.failureCount() + resultados.errorCount() == 0);
			
		}

	}

}
