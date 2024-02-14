package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;

public class ITestActivaBeneficiario_1 extends BaseITestService{
	
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
    public void testActivaBeneficiario() {
    	log.info("Entrando a testInsertaBeneficiario()");
    	assertNotNull(controlBeneficiariosService);
    	
    	/* Se realiza la insercion */
    	controlBeneficiariosService.activarBeneficiario(259l);
    	log.info("Se activo el registro exitosamente");
    		
    }

}
