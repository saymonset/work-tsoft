/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.ejercicioDerechosInt;

import java.text.SimpleDateFormat;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.presentation.test.BaseTestCase;

/**
 
 */
public class ITestCalendarioServicios extends BaseTestCase {

    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
    private DivisionInternacionalService divisionInternacionalService;
    

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (divisionInternacionalService == null) {
        	divisionInternacionalService = (DivisionInternacionalService) applicationContext.getBean("divisionInternacionalService");
        }
    }

    public void testCargaInformacion() throws Exception {
    	divisionInternacionalService.instruyeMensajeRetiro(1971L, 12L);
    	assertTrue(true);
        
    }
}

