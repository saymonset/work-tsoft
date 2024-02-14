/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.divisioninternacional;

import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;

/**
 * @author javiles
 *
 */
public class ITestObtieneCustodios_1 extends BaseITestService {

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
     * @throws Exception
     */
    public void testObtieneCusotdios_1() throws Exception {
        CatBic[] catBics = divisionInternacionalService.obtieneCustodios(new EmisionVO("D7","MACQ235","071210","0002"));
        assertNotNull(catBics);
        assertTrue(catBics.length > 0);
        System.out.println("--------------> registros encontrados ("+catBics.length+")");
    }    
}
