/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.beneficiario;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8BEN;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8IMY;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W9;
import com.indeval.portalinternacional.persistence.dao.CamposFormatosDao;

/**
 * 
 * @author Oscar Garcia Granados
 * 
 */
public class ITestFindCamposFormato_1 extends BaseDaoTestCase {

	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindCamposFormato_1.class);

	/**
	 * Dao que se va a probar
	 */
	private CamposFormatosDao camposFormatosDao;

	protected void onSetUp() throws Exception {
		super.onSetUp();
		camposFormatosDao = (CamposFormatosDao) getBean("camposFormatosDao");
	}

	
	public void testFindCamposW8BEN() throws Exception {
		log.info("Ejecutando prueba testFindCamposW8BEN()");
		assertNotNull(camposFormatosDao);

		List<Field3W8BEN> lista = camposFormatosDao.getField3W8BEN();
		for(Field3W8BEN campo : lista) {
			log.info("Campo: [" + campo.getIdCampo() + "-" + campo.getDescripcion() + "]");
		}

	}
	
	public void testFindCamposW8IMY() throws Exception {
		log.info("Ejecutando prueba testFindCamposW8IMY()");
		assertNotNull(camposFormatosDao);

		List<Field3W8IMY> lista = camposFormatosDao.getField3W8IMY();
		for(Field3W8IMY campo : lista) {
			log.info("Campo: [" + campo.getIdCampo() + "-" + campo.getDescripcion() + "]");
		}

	}
	
	public void testFindCamposW9() throws Exception {
		log.info("Ejecutando prueba testFindCamposW9()");
		assertNotNull(camposFormatosDao);

		List<Field3W9> lista = camposFormatosDao.getField3W9();
		for(Field3W9 campo : lista) {
			log.info("Campo: [" + campo.getIdCampo() + "-" + campo.getDescripcion() + "]");
		}

	}

}
