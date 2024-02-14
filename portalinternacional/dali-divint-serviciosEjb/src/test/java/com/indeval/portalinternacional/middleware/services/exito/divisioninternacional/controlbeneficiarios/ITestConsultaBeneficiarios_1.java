/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaBeneficiariosParam;

@SuppressWarnings({"unchecked"})
public class ITestConsultaBeneficiarios_1 extends BaseITestService {

	/** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private SimpleDateFormat sdfGeneral = new SimpleDateFormat("HH:mm:ss.SSS");
	/** Servicio que sera probado en esta prueba */
	private ControlBeneficiariosService controlBeneficiariosService;

	/**
	 * @see com.indeval.portalinternacional.portalinternacional.services.BaseITestService#onSetUp()
	 */
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		if (controlBeneficiariosService == null) {
			controlBeneficiariosService = (ControlBeneficiariosService) applicationContext.getBean("controlBeneficiariosService");
		}
	}

	/**
	 *
	 *
	 */
	public void testConsultaBeneficiarios() throws Exception {
		log.info("Entrando a testConsultaBeneficiarios()");
		Date horaInicioAnterior, horaFinAnterior, horaInicioNuevo, horaFinNuevo;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		assertNotNull("Servicio controlBeneficiariosService no encontrado", controlBeneficiariosService);
		ConsultaBeneficiariosParam param = new ConsultaBeneficiariosParam();
		param.setNombreRazonSocial("rafa");
//		param.setReferenceNumber("33334");
//		Institucion i = new Institucion();
//		i.setIdInstitucion(3l);
//		param.setInstitucion(i);
//		param.setFechaRegistroInicio(sdf.parse("18-01-2010 00:00:00.000"));
//		param.setFechaRegistroFin(sdf.parse("18-01-2010 23:59:59.999"));
//		param.setNombreRazonSocial("humberto gonzalez");
		PaginaVO paginaVO = new PaginaVO();
		paginaVO.setRegistrosXPag(PaginaVO.TODOS);

		horaInicioNuevo = new Date();
		paginaVO = controlBeneficiariosService.consultaBeneficiarios(param, paginaVO);
		horaFinNuevo = new Date();
		assertNotNull("Lista Nula", paginaVO.getRegistros());
		assertFalse("No existe la lista de Beneficiario", paginaVO.getRegistros().isEmpty());
		log.info("Total de registros2 = " + paginaVO.getRegistros().size());
		long diffNuevo =horaFinNuevo.getTime() - horaInicioNuevo.getTime();

//		for (Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
//			Beneficiario element = (Beneficiario) iter.next();
//			assertNotNull(element);
//			log.debug("Beneficiario [" + element.getNombreGeneral() + "]");
//		}

	}
}
