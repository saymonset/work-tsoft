/*
 * Copyright (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional.capitales;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DerechosCapitalesService;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistorico;
import com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO;


/**
 * 
 * @author Abraham Morales
 * 
 */
@SuppressWarnings({"unchecked"})
public class ITestConsultaHistoricoCapitales extends BaseDaoTestCase {

    /**
     * Log de la clase
     */
	private static final Logger log = LoggerFactory.getLogger(ITestConsultaHistoricoCapitales.class);

    /*
	 * 
	 */
    private DerechosCapitalesService derechosCapitalesService;



    /**
	  * 
	  */
    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();

        this.derechosCapitalesService =
                (DerechosCapitalesService) this.getBean("derechosCapitalesService");

    }

    /**
	 * 
	 */
    public void testDerechosCapitalesService() {
        log.debug("============================ entrando a testDerechosCapitalesService() ===================================");
        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setRegistrosXPag(PaginaVO.TODOS);
        ParamConsultaDetalleEjerDerCapTO params = new ParamConsultaDetalleEjerDerCapTO();
        params.setIdDerechoCapital(new Long(33513));
        params.setIsin("US4642872752");
//        paginaVO =
//                this.derechosCapitalesService.consultaDetalleDerechosCapital(paginaVO, params,
//                        false);
        assertNotNull(paginaVO.getRegistros());
        List<DerechoCapitalHistorico> res = paginaVO.getRegistros();

        for (DerechoCapitalHistorico der : res) {
            System.out.println(der.getAsignacion());
        }

    }



}
