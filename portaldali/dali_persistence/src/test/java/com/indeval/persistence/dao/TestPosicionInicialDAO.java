/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 3, 2008
 */
package com.indeval.persistence.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;

/**
 * Prueba unitaria para el DAO de la consulta de movimientos de Efectivo.
 * 
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 * 
 */
public class TestPosicionInicialDAO extends BaseDaoTestCase{
	
	private Logger logger = LoggerFactory.getLogger(TestPosicionInicialDAO.class);

	public void testConsultaMovimientosEfectivo() {

		PosicionNombradaDaliDao posicionNombradaDao = (PosicionNombradaDaliDao) applicationContext.getBean("tPosicionNombradaDao");

		TPosicionNombradaParamsPersistence params = new TPosicionNombradaParamsPersistence();
		
		Date fechaInicio = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		try {
			fechaInicio = sdf.parse("27-02-2009 00:00:00");
		} catch ( Exception ex ) {
			logger.error("Error al convertir las fechas",ex);
		}
		
		params.setFechaInicio(fechaInicio);
		params.setIdInstitucion("01");
		params.setFolioInstitucion("003");
		String[] cuentas = {"0315"}; 
		params.setCuentas(cuentas);
		String[] tvs = {"1"}; 
		params.setTiposDeValor(tvs);
		params.setEmisora("ALFA");
		params.setSerie("A");
		params.setIdBoveda(BigInteger.valueOf(1));
		
		BigInteger posicionInicial = posicionNombradaDao.getSaldoInicial(params);
		
		logger.info("Valor obtenido: [" + posicionInicial + "]");
		
	}

	

}
