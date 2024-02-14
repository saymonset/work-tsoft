// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.diasInhabilesDivisas;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.CustodioService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DiasInhabilesDivisasService;
import junit.framework.TestCase;

import java.util.Date;
import java.util.List;

public class DiasInhabilesDivisasServiceImplTest extends BaseDaoTestCase {
    DiasInhabilesDivisasService service;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        service = (DiasInhabilesDivisasService) getBean("diasInhabilesDivisasService");
    }

    public void testGetDiasInhabilesByIdDivisa() {
        System.out.println("testGetDiasInhabilesByIdDivisa()");
        Long idDivisa = 3L;

        assertNotNull(service);
        List<Date> diasInhabiles = service.getDiasInhabilesByIdDivisa(idDivisa);

        for (Date diaInhabil: diasInhabiles) {
            System.out.println("Dia inhabil> " + diaInhabil);
        }

        TestCase.assertNotNull(diasInhabiles);
    }
}