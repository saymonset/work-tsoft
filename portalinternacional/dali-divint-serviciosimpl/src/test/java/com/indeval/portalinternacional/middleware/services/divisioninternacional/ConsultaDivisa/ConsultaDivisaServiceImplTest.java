// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaDivisa;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.BovedaService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaDivisaService;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaCorresponsal;
import junit.framework.TestCase;

import java.util.List;

public class ConsultaDivisaServiceImplTest extends BaseDaoTestCase {
    ConsultaDivisaService service;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        service = (ConsultaDivisaService) getBean("consultaDivisaService");
    }

    public void testConsultarDivisas() {
        System.out.println("testConsultarDivisas()");

        int expectedSize = 185;

        assertNotNull("ConsultaDivisaService Nulo", service);
        List<DivisaDTO> divisas = service.consultarDivisas(null);

        for (DivisaDTO divisa : divisas) {
            System.out.println(divisa.getDescripcion());
        }

        assertFalse(divisas.isEmpty());
        assertNotNull(divisas);
        assertEquals(expectedSize, divisas.size());
    }

    public void testFindCuentaCorresponsalByIdDivisaAndIdInstitucion() {
        System.out.println("testFindCuentaCorresponsalByIdDivisaAndIdInstitucion()");

        Long idDivisa = 3L;
        Long idInstitucion = 9L;
        Long expectedIdCuentaCorresponsal = 8L;
        int expectedSize = 1;
        String expectedBeneficiaryNameAddressP = "INTERNACIONAL, SEGURIDAD, AMBIENTE 12345678901234567890123456789012345.                                  -";

        assertNotNull("ConsultaDivisaService Nulo", service);
        List<CuentaCorresponsal> cuentasCorresponsal = service.findCuentaCorresponsalByIdDivisaAndIdInstitucion(idDivisa, idInstitucion);

        for (CuentaCorresponsal cuenta : cuentasCorresponsal) {
            System.out.println(cuenta.getIdDivisa());
        }

        assertFalse(cuentasCorresponsal.isEmpty());
        assertNotNull(cuentasCorresponsal);
        assertEquals(expectedSize, cuentasCorresponsal.size());
        assertNull(cuentasCorresponsal.get(0).getCuentaCorresponsal103());
        assertEquals(expectedIdCuentaCorresponsal, cuentasCorresponsal.get(0).getIdCuentaCorresponsal());
        assertEquals(expectedBeneficiaryNameAddressP, cuentasCorresponsal.get(0).getCuentaCorresponsal202().getBeneficiaryNameAddressP());
    }

    public void testFindIdDivisasByIdInstitucion() {
        System.out.println("testFindIdDivisasByIdInstitucion()");

        Long idInstitucion = 9L;
        int expectedSize = 5;

        assertNotNull("cuentaCorresponsalDao Nulo", service);
        List<DivisaDTO> divisas = service.findIdDivisasByIdInstitucion(idInstitucion);

        for (DivisaDTO idDivisa : divisas) {
            System.out.println(idDivisa);
        }

        assertFalse(divisas.isEmpty());
        assertNotNull(divisas);
        assertEquals(expectedSize, divisas.size());
    }

    public void testConsultarDivisaPorId() {
        System.out.println("testConsultarDivisaPorId()");

        int idDivisa = 3;
        String expectedClaveAlfabetica = "USD";
        int expectedClaveNumerica = 840;
        String expectedDescripcion = "USA DOLLAR";

        assertNotNull("ConsultaDivisaService Nulo", service);
        DivisaDTO divisa = service.consultarDivisaPorId(idDivisa);

        assertNotNull(divisa);
        assertEquals(expectedClaveAlfabetica, divisa.getClaveAlfabetica());
        assertEquals(expectedClaveNumerica, divisa.getClaveNumerica());
        assertEquals(expectedDescripcion, divisa.getDescripcion());
    }
}