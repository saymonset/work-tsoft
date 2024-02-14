// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.boveda.Util;
import com.indeval.portalinternacional.middleware.servicios.modelo.Bovedas;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class BovedaServiceImplTest {

    @Mock
    BovedaService bovedaServiceMock;

    public void testObtenerBovedasPorDivisa() {
        //TODO: Implements
        fail("Not yet implemented");
    }

    public void testBuscarBovedasPorTipoCustodia() {
        //TODO: Implements
        fail("Not yet implemented");
    }

    public void testFindCatBicEnBaseABovedaEfectivoParticipante() {
        //TODO: Implements
        fail("Not yet implemented");
    }

    @Test
    public void testFindAllBovedasEfectivo() {
        Mockito.when(bovedaServiceMock.findAllBovedasEfectivo()).
                thenReturn(Util.getAllBovedasEfectivoEsperadas());
        List<Bovedas> bovedas = bovedaServiceMock.findAllBovedasEfectivo();
        for (Bovedas boveda : bovedas) {
            System.out.println(Util.bovedasToString(boveda));
        }
        assertNotNull(bovedas);
    }
}