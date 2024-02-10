/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.beneficiario;

import java.text.ParseException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaBeneficiariosParam;
import com.indeval.portalinternacional.persistence.dao.BeneficiarioDao;

/**
 * 
 * @author Oscar Garcia Granados
 * 
 */
@SuppressWarnings( { "unchecked" })
public class ITestFindBeneficiarios_1 extends BaseDaoTestCase {

	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindBeneficiarios_1.class);

	/**
	 * Dao que se va a probar
	 */
	private BeneficiarioDao beneficiarioDao;

	/**
	  * 
	  */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		beneficiarioDao = (BeneficiarioDao) getBean("beneficiarioDao");
	}

	/**
	 * @throws ParseException 
	  * 

	 *
	  */
	public void testFindBeneficiario() throws ParseException {

		log.info("Ejecutando prueba testFindBeneficiario()");

		assertNotNull(beneficiarioDao);

		/* Objeto de prueba */
		ConsultaBeneficiariosParam param = new ConsultaBeneficiariosParam();
		/* Campos de la Institucion */
		Institucion institucion = new Institucion();
		institucion.setIdInstitucion(3l);
		param.setInstitucion(institucion);
		/* Se ponen valores a los campos del objeto */
//		TipoBeneficiario tipoBeneficiario = new TipoBeneficiario();
//		StatusBeneficiario statusBeneficiario = new StatusBeneficiario();
//		param.setNombres("Ra");
		//param.setFormato("W9");
		
		
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		param.setFechaRegistroInicio(sdf.parse("01/11/2009"));
//		param.setFechaRegistroFin(sdf.parse("30/11/2009"));

		/* Prueba de consulta */
		PaginaVO paginaVO = new PaginaVO();
		paginaVO.setRegistrosXPag(20);
		paginaVO.setOffset(0);
		paginaVO = beneficiarioDao.findBeneficiarios(param,paginaVO,false);
		assertNotNull(paginaVO);
		assertNotNull(paginaVO.getRegistros());
		assertTrue("No existe la lista de Beneficiario", !paginaVO.getRegistros().isEmpty());
		log.debug("Total de registros = " + paginaVO.getRegistros().size());
		for (Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
			Beneficiario element = (Beneficiario) iter.next();
			assertNotNull(element);
			log.debug("Beneficiario [" + element + "]");
		}

	}

}
