package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;

public class ITestObtieneCatBic_1 extends BaseITestService {
	
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
    public void testObtieneCatBic() {
    	log.info("Entrando a testObtieneCatBic()");
    	assertNotNull(controlBeneficiariosService);
    	List<Object[]> catbics=controlBeneficiariosService.obtieneCatBic();
    	if(catbics != null && catbics.size()!=0)
    	{
	    	for(int i=0;i<catbics.size();i++)
	    	{
	    		Object[] element = catbics.get(i);
	 		   	assertNotNull(element[0]);
	 		   	assertNotNull(element[1]);
	 	        log.debug("CatBic [" + element[0] + "\t" + element[1] + "]");
	    	}
    	}
    	
    }

}
