// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.boveda;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.BovedaService;
import com.indeval.portalinternacional.middleware.servicios.dto.BovedaDto;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;

import java.math.BigInteger;
import java.util.List;

import static com.indeval.portalinternacional.middleware.services.divisioninternacional.boveda.Util.getCriterio;
import static com.indeval.portalinternacional.middleware.services.divisioninternacional.boveda.Util.getDivisa;

public class BovedaServiceImplTest extends BaseDaoTestCase {
    BovedaService service;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        service = (BovedaService) getBean("bovedaService");
    }

    public void testObtenerBovedasPorDivisa() {
        System.out.println("testObtenerBovedasPorDivisa()");

        int expectedSize = 15;
        DivisaDTO divisa = getDivisa();

        assertNotNull("BovedaService Nulo", service);
        List<BigInteger> idsBovDiv = service.obtenerBovedasPorDivisa(divisa);

        assertNotNull(idsBovDiv);
        assertEquals(expectedSize, idsBovDiv.size());
    }

    public void testBuscarBovedasPorTipoCustodia() {
        System.out.println("testBuscarBovedasPorTipoCustodia()");
        int expectedSize = 15;
        BovedaDto criterio = getCriterio();

        assertNotNull("BovedaService Nulo", service);
        List<BovedaDto> bovedas = service.buscarBovedasPorTipoCustodia(criterio, null, null);

        for (BovedaDto boveda : bovedas) {
            System.out.println(boveda.getDescripcion());
        }

        assertNotNull(bovedas);
        assertEquals(expectedSize, bovedas.size());
    }

    public void testFindCatBicEnBaseABovedaEfectivoParticipante() {
        System.out.println("testFindCatBicEnBaseABovedaEfectivoParticipante()");

        Long idBoveda = 13L;
        Long idInstitucion = 9L;
        Long expectedIdCatBic = 5L;

        assertNotNull("BovedaService Nulo", service);
        Long idCatbic = service.findCatBicEnBaseABovedaEfectivoParticipante(idBoveda, idInstitucion);

        assertNotNull(idCatbic);
        assertEquals(expectedIdCatBic, idCatbic);
    }
}