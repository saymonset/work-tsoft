/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.sicdetalle;

import java.util.ArrayList;
import java.util.List;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.persistence.dao.SicDetalleDao;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestFindDepositantes_1 extends BaseDaoTestCase {

	/** Dao a probar */
	private SicDetalleDao sicDetalleDao;

	/**
	 * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
	 */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		sicDetalleDao = (SicDetalleDao) getBean("sicDetalleDao");
	}

	/**
	 * TestCase para probar SicDetalleDao.findDepositantes()
	 */
	public void testFindDepositantes_1() {
		assertNotNull(sicDetalleDao);
		List<CatBic> catBics = new ArrayList<CatBic>();

		CatBic catBic = new CatBic();
		catBic.setIdCatbic(new Long(5));
		catBics.add(catBic);

		catBic = new CatBic();
		catBic.setIdCatbic(new Long(1));
		catBics.add(catBic);

		catBic = new CatBic();
		catBic.setIdCatbic(new Long(2));
		catBics.add(catBic);
		
		List<SicDetalle> sicDetalles = sicDetalleDao.findDepositantes(catBics);
		assertNotNull(sicDetalles);
		assertFalse(sicDetalles.isEmpty());
		System.out.println("---------------------> registros encontrados ("+sicDetalles.size()+")");
	}

	/**
	 * TestCase para probar SicDetalleDao.findDepositantes()
	 */
	public void testFindDepositantes_2() {
		assertNotNull(sicDetalleDao);

		CatBic catBic = new CatBic();
		catBic.setIdCatbic(new Long(5));
		
		List<SicDetalle> sicDetalles = sicDetalleDao.findDepositantes(catBic, Boolean.TRUE);
		assertNotNull(sicDetalles);
		assertFalse(sicDetalles.isEmpty());
		System.out.println("---------------------> registros encontrados ("+sicDetalles.size()+")");
	}

	/**
	 * TestCase para probar SicDetalleDao.findDepositantes()
	 */
	public void testFindDepositantes_3() {
		assertNotNull(sicDetalleDao);
		CatBic catBic = new CatBic();
		catBic.setIdCatbic(new Long(5));
		PaginaVO paginaVO = sicDetalleDao.findDepositantes(catBic, "DTCYUS33XXX", "DTCYID",null, null);
		assertNotNull(paginaVO);
		assertNotNull(paginaVO.getRegistros());
		assertFalse(paginaVO.getRegistros().isEmpty());
		System.out.println("----------> registros encontrados ("+paginaVO.getTotalRegistros()+")");
	}

	/**
	 * TestCase para probar SicDetalleDao.findDepositantes()
	 */
	public void testFindDepositantes_4() {
		assertNotNull(sicDetalleDao);
		CatBic catBic = new CatBic();
		catBic.setIdCatbic(new Long(5));
		PaginaVO paginaVO = sicDetalleDao.findDepositantes(catBic, null, null,null, null);
		assertNotNull(paginaVO);
		assertNotNull(paginaVO.getRegistros());
		assertFalse(paginaVO.getRegistros().isEmpty());
		System.out.println("----------> registros encontrados ("+paginaVO.getTotalRegistros()+")");
	}

}
