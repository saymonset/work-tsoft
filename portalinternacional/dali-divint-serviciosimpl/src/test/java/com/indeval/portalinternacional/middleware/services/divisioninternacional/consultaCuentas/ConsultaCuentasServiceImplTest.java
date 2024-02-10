// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.consultaCuentas;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.BovedaService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaCuentasService;
import com.indeval.portalinternacional.middleware.servicios.dto.CuentaEfectivoDTO;
import junit.framework.TestCase;

import java.util.List;

import static com.indeval.portalinternacional.middleware.services.divisioninternacional.consultaCuentas.Util.getCriterio;

public class ConsultaCuentasServiceImplTest extends BaseDaoTestCase {
    ConsultaCuentasService service;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        service = (ConsultaCuentasService) getBean("consultaCuentasService");
    }

    public void testBuscarCuentasNombradasEfectivo() {
        System.out.println("testBuscarCuentasNombradasEfectivo()");
        int expectedIdCuenta = 3909;
        String expectedDescripcion = "010092000";
        int expectedSize = 1;

        assertNotNull("ConsultaCuentasService Nulo", service);

        CuentaEfectivoDTO criterio = getCriterio();
        List<CuentaEfectivoDTO> cuentasNombradasE = service.buscarCuentasNombradasEfectivo(criterio, null);

        for (CuentaEfectivoDTO cuenta : cuentasNombradasE) {
            System.out.println(cuenta.getInstitucion().getRazonSocial() + " " + cuenta.getInstitucion().getNombre());
        }

        assertNotNull(cuentasNombradasE);
        assertEquals(expectedSize, cuentasNombradasE.size());
        assertEquals(expectedIdCuenta, cuentasNombradasE.get(0).getIdCuenta());
        assertEquals(expectedDescripcion, cuentasNombradasE.get(0).getDescripcion());
    }
}