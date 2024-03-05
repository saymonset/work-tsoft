/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.util;

import com.indeval.portaldali.persistence.util.DateUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test para la clase DateUtil
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class ITestDateUtil extends TestCase {

	public void testGetPlazo() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		
		Date fecha1 = sdf.parse("25-07-2010 23:59:21.999");
		Date fecha2 = sdf.parse("29-07-2010 23:56:21.999");
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		cal1.setTime(fecha1);
		cal2.setTime(fecha2);

		long diff = DateUtil.getPlazo(cal1, cal2);

		Logger logger = LoggerFactory.getLogger(this.getClass());

		logger.info("Diff : [" + diff + "]");

		assertTrue("Resultado erroneo", diff == 4);

	}

}
