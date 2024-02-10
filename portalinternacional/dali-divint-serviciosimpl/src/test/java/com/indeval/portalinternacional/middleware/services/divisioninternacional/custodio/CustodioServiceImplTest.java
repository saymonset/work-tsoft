// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.custodio;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.BovedaService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.CustodioService;
import junit.framework.TestCase;

public class CustodioServiceImplTest extends BaseDaoTestCase {
    CustodioService service;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        service = (CustodioService) getBean("custodioService");
    }

    public void testGetIdCustodioByIdCatbic() {
        Long idCatbic = 5L;
        Integer expectedIdCustodio = 2;
        Integer idCustodio =service.getIdCustodioByIdCatbic(idCatbic);

        System.out.println("Id Custodio: " + idCustodio);

        assertEquals(expectedIdCustodio, idCustodio);
    }
}