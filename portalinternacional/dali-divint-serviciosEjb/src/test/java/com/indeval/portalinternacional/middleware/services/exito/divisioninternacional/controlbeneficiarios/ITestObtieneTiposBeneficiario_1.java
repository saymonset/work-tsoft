package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;

public class ITestObtieneTiposBeneficiario_1 extends BaseITestService {
	
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
     * TestCase para ControlBeneficiariosService.obtieneTiposBeneficiario()
     */
	public void testObtieneTiposBeneficiario() {
	    	
    	log.info("Entrando a testObtieneTiposBeneficiario()");
    	assertNotNull(controlBeneficiariosService);
    	Long idCatBic= new Long(4033);
    	
    	TipoBeneficiario[] tiposBeneficiario =
    		controlBeneficiariosService.obtieneTiposBeneficiario(idCatBic.toString());
    	for(int i=0;i<tiposBeneficiario.length;i++)
    	{
    		assertNotNull(tiposBeneficiario[i]);
    		log.debug("Beneficiario [" + ReflectionToStringBuilder.toString(tiposBeneficiario[i]) + "]");
    	}
	}

}
