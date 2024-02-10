// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.horariosCustodios;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.HorariosCustodiosService;

import java.util.Map;

public class HorariosCustodiosServiceImplTest extends BaseDaoTestCase {
    HorariosCustodiosService service;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        service = (HorariosCustodiosService) getBean("horariosCustodiosService");
    }

    public void testGetRangoHorariosPorCustodio() {
        System.out.println("testGetRangoHorariosPorCustodio()");

        Integer idCustodio = 9;
        int expectedSize = 2;

        assertNotNull("HorariosCustodiosService Nulo", service);
        Map<String, String> resultados = service.getRangoHorariosPorCustodio(idCustodio);

        assertNotNull(resultados);
        assertEquals(expectedSize, resultados.size());
    }
}