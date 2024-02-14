package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.temporal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.TemporalBeneficiariosService;

public class ITestValidaNombresBeneficiarios_1 extends BaseITestService{
	
	 /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    /** Servicio que sera probado en esta prueba */
    private TemporalBeneficiariosService temporalBeneficiariosService;

    /**
     * @see com.indeval.portalinternacional.portalinternacional.services.BaseITestService#onSetUp()
     */
	@Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (temporalBeneficiariosService == null) {
        	temporalBeneficiariosService = (TemporalBeneficiariosService) applicationContext.getBean("temporalBeneficiariosService");
        }
    }
    
    public void testValidaNombresBeneficiarios() {
    	log.info("Entrando a ITestValidaNombresBeneficiarios_1.testValidaNombresBeneficiarios()");
    	assertNotNull(temporalBeneficiariosService);
    	
    	/* Se realiza la insercion */
    	temporalBeneficiariosService.validaNombresBeneficiarios();
    	log.info("Se activo el registro exitosamente");
    }

}
