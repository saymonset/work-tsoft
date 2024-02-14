/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.divisioninternacional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;

/**
 * @author javiles
 *
 */
public class ITestActualizaOperacionSIC_1 extends BaseITestService {

    /** Objeto de loggeo */
	private final Logger log = LoggerFactory.getLogger(ITestActualizaOperacionSIC_1.class);
    
    /** Servicio que sera probado en esta prueba */
    private DivisionInternacionalService divisionInternacionalService;

    /**
     * @see com.indeval.portalinternacional.portalinternacional.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (divisionInternacionalService == null) {
            divisionInternacionalService = (DivisionInternacionalService) applicationContext
                    .getBean("divisionInternacionalService");
        }
    }
    
    /**
     * TestCase para probar el metodo divisionInternacionalService.actualizaOperacionSIC()
     * @throws BusinessException
     */
    public void testActualizaOperacionSIC() throws BusinessException {
    	
        log.info("Entrando a testActualizaOperacionSIC()");
        
        assertNotNull(divisionInternacionalService);
        
        int registrosActualizados = divisionInternacionalService.actualizaOperacionSIC(null);
        log.debug("Registros actualizados [" + registrosActualizados + "]");
    }
}
