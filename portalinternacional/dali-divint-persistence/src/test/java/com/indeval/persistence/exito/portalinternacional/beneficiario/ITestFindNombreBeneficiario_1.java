/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.beneficiario;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.persistence.dao.BeneficiarioDao;

/**
 * Test para probar los tiempos de los metodos de los nombres de los beneficiarios
 * @author Rafael Ibarra Zendejas
 * 
 */
public class ITestFindNombreBeneficiario_1 extends BaseDaoTestCase {

	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindNombreBeneficiario_1.class);
	/** Formato de fechas */
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

	/**
	 * Dao que se va a probar
	 */
	private BeneficiarioDao beneficiarioDao;

	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		beneficiarioDao = (BeneficiarioDao) getBean("beneficiarioDao");
	}

	public void testFindBeneficiarioById() {
		log.info("Ejecutando prueba testFindBeneficiario()");
		assertNotNull("DAO nulo",beneficiarioDao);
//		Date horaInicioAnterior, horaFinAnterior, horaInicioNuevo, horaFinNuevo;
//
//
//		horaInicioNuevo = new Date();
//		List<NombreBeneficiario> beneficiarios2 = beneficiarioDao.getNombreBeneficiario();
//		horaFinNuevo = new Date();
//		assertNotNull("Lista Nula", beneficiarios2);
//		assertFalse("Lista Vacia", beneficiarios2.isEmpty());
//
//		horaInicioAnterior = new Date();
//		List<NombreBeneficiario> beneficiarios = beneficiarioDao.getNombresBeneficiarios(new ConsultaBeneficiariosParam());
//		horaFinAnterior = new Date();
//		assertNotNull("Lista Nula", beneficiarios);
//		assertFalse("Lista Vacia", beneficiarios.isEmpty());
//
//		long diffAnterior = horaFinAnterior.getTime() - horaInicioAnterior.getTime();
//		long diffNuevo =horaFinNuevo.getTime() - horaInicioNuevo.getTime();
//
//		log.info("Tiempo Anterior: [" + diffAnterior + "]");
//		log.info("Tiempo Nuevo: [" + diffNuevo + "]");


//		List<NombreBeneficiario> beneficiarios = beneficiarioDao.findBeneficiariosByName(new ConsultaBeneficiariosParam());
	}

}
