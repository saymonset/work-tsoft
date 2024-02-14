/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.HistoricoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaHistoricoBeneficiariosParam;

/**
 * Clase para
 * @author Rafael Ibarra Zendejas
 */
public class ITestConsultaHistoricoBeneficiario_1 extends BaseITestService {
	/** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    /** Servicio que sera probado en esta prueba */
    private ControlBeneficiariosService controlBeneficiariosService;

    /**
     * @see com.indeval.portalinternacional.portalinternacional.services.BaseITestService#onSetUp()
     */
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
    public void testActivaBeneficiario() {
    	log.info("Entrando a testInsertaBeneficiario()");
    	assertNotNull(controlBeneficiariosService);

		ConsultaHistoricoBeneficiariosParam param = new ConsultaHistoricoBeneficiariosParam();
		//param.setIdBeneficiario(6l);
		PaginaVO paginaVO = new PaginaVO();
		paginaVO.setRegistrosXPag(0);
        param.setNombreRazonSocial("nava");
		paginaVO = controlBeneficiariosService.consultaBeneficiariosHistorico(param, paginaVO);

		System.out.println("Numero de regsitros: [" + paginaVO.getTotalRegistros() + "]");
		System.out.println("Numero de regsitros de la pagina: [" + paginaVO.getRegistros().size() + "]");

		for(HistoricoBeneficiario h : (List<HistoricoBeneficiario>)paginaVO.getRegistros()) {
			System.out.println("Numero de instituciones: [" + h.getBeneficiario().getInstitucion().size() + "]");
		}

    }
}
