/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.test;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.presentation.test.BaseTestCase;

/**
 * Controller de Prueba la Consulta de Operaciones para la opción de Consulta de
 * Operaciones.
 * 
 * @author Erik Vera
 * 
 * @version 1.0
 */
public class ConsultaOperacionesControllerTest extends BaseTestCase {

	/** Servicio de division internacional */
	private DivisionInternacionalService divisionInternacionalService;
	
	/**Objeto de operaciones SIC*/
	private OperacionSic params = new OperacionSic();

	public void testConsultaOperaciones() {
		divisionInternacionalService=(DivisionInternacionalService) getBean("divisionInternacionalService");
		PaginaVO pagina = new PaginaVO();
		pagina=divisionInternacionalService.consultaOperaciones(params, pagina, false, true, false);

	}
}
