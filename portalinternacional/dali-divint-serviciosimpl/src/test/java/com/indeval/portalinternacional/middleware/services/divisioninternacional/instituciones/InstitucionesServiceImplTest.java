// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.instituciones;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.InstitucionesService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.consultaSaldosEfectivo.Util;
import com.indeval.portalinternacional.middleware.servicios.dto.InstitucionWebDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.SaldoEfectivoDTO;
import junit.framework.TestCase;

public class InstitucionesServiceImplTest extends BaseDaoTestCase {
    InstitucionesService service;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        service = (InstitucionesService) getBean("consultaSaldosEfectivoService");
    }

    public void testBuscarInstitucionPorClaveYFolio() {
        System.out.println("testBuscarInstitucionPorClaveYFolio()");

        String idFolio = "1009";
        String expectedRazonSocial = "VALORES MEXICANOS, CASA DE BOLSA, S. A. DE C.V.";
        String expectedNombre = "CBVALMEX";

        assertNotNull("InstitucionesService Nulo", service);
        InstitucionWebDTO institucion = service.buscarInstitucionPorClaveYFolio(idFolio);

        assertNotNull(institucion);
        assertEquals(expectedRazonSocial, institucion.getRazonSocial());
        assertEquals(expectedNombre, institucion.getNombre());
    }
}