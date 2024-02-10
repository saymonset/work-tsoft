// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.sic;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.SicService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Multidivisa;
import junit.framework.TestCase;

import java.util.List;

public class SicServiceImplTest extends BaseDaoTestCase {

    SicService service;
    protected void onSetUp() throws Exception {
        super.onSetUp();
        service = (SicService) getBean("sicService");
    }
    public void testNotificaCambioEstadoMovEfeDivInt() {
        System.out.println("testNotificaCambioEstadoMovEfeDivInt()");
        try {
            assertNotNull("sicService Nulo", service);
            Multidivisa multidivisa = new Multidivisa();

            multidivisa.setTipoMovimiento(Multidivisa.TipoMovimiento.DEPOSITO);
            multidivisa.setEstado(Multidivisa.EstadoMovimiento.RETENIDO);
            multidivisa.setFolioConstrol(643L);
            multidivisa.setId(78L);

            String mensajeXML = service.crearXML(multidivisa);
            service.notificaCambioEstadoMovEfeDivInt(mensajeXML);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
            fail();
        }
    }
}