package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;

public class ITestConsultaTipoBeneficiario extends BaseITestService{
	
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
    
    public void testActualizaBeneficiario() {
    	
    	log.info("Entrando a testActualizaBeneficiario()");
    	assertNotNull(controlBeneficiariosService);
    	
    	TipoBeneficiario[] lstBeneficiario = controlBeneficiariosService.obtieneTiposBeneficiario("THE BANK OF NEW YORK");
    	
    	assertNotNull(lstBeneficiario);
    	assertTrue(lstBeneficiario.length > 0);
    	
    	log.info("Tamaño: [" + lstBeneficiario + "]");
    }
    	
}
