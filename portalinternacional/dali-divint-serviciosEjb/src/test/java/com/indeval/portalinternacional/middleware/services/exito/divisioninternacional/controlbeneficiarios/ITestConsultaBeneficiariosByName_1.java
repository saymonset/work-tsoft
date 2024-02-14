/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaNombreBeneficiarioParam;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaNombreBeneficiarioVO;

@SuppressWarnings({"unchecked"})
public class ITestConsultaBeneficiariosByName_1 extends BaseITestService{
	
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
        	controlBeneficiariosService = (ControlBeneficiariosService) applicationContext
                    .getBean("controlBeneficiariosService");
        }
    }
    
    /**
     * 
     *
     */
    public void testConsultaBeneficiarios() {
    	log.info("Entrando a testConsultaBeneficiarios()");
    	assertNotNull(controlBeneficiariosService);
		ConsultaNombreBeneficiarioParam param = new ConsultaNombreBeneficiarioParam();
        param.setNombres("JOSE");
//        param.setApellidoPaterno("IBARRA");
//        param.setApellidoMaterno("ZEN");
        param.setIdTipoBeneficiario(1l);
        param.setCustodio(4032l);
//        param.setRFC("CIII894523");
    	PaginaVO paginaVO = null;
    	assertNotNull("Lista Nula",paginaVO.getRegistros());
	    assertTrue("No existe la lista de Beneficiario", !paginaVO.getRegistros().isEmpty());
	    log.info("Total de registros = " + paginaVO.getRegistros().size());
	    for (Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
	    	 ConsultaNombreBeneficiarioVO element = (ConsultaNombreBeneficiarioVO) iter.next();
	    	 assertNotNull(element);
            log.info("Beneficiario [" + element.getNombres() + "]");
	    }
    }

}
