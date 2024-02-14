// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.consultaSaldosEfectivo;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaSaldosEfectivoService;
import com.indeval.portalinternacional.middleware.servicios.dto.SaldoEfectivoDTO;

import java.util.List;

public class ConsultaSaldosEfectivoServiceImplTest extends BaseDaoTestCase {
    ConsultaSaldosEfectivoService service;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        service = (ConsultaSaldosEfectivoService) getBean("consultaSaldosEfectivoService");
    }

    public void testConsultarSaldosEfectivo() {
        System.out.println("testConsultarSaldosEfectivo()");

        SaldoEfectivoDTO criterio = Util.getCriterio();

        assertNotNull("ConsultaSaldosEfectivoService Nulo", service);
        List<SaldoEfectivoDTO> saldosEfectivo = service.consultarSaldosEfectivo(criterio, null, null);

        for (SaldoEfectivoDTO saldo: saldosEfectivo) {
            System.out.println(saldo.toString());
        }

        assertNotNull(saldosEfectivo);
        assertFalse(saldosEfectivo.isEmpty());
    }
}