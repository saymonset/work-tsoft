// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.Saldo;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.dto.SaldoEfectivoDTO;
import com.indeval.portalinternacional.persistence.dao.SaldoDAO;

import java.util.List;

public class SaldoDAOV2ImplTest extends BaseDaoTestCase {
    SaldoDAO dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (SaldoDAO) getBean("saldoDAO");
    }

    public void testConsultarSaldosNombradas() {
        System.out.println("testConsultarSaldosNombradas()");

        SaldoEfectivoDTO criterio = Util.getCriterio();

        assertNotNull("SaldoDAO Nulo", dao);
        List<SaldoEfectivoDTO> saldosEfectivo = dao.consultarSaldosNombradas(criterio, null, null);

        for (SaldoEfectivoDTO saldo: saldosEfectivo) {
            System.out.println(saldo.toString());
        }

        assertNotNull(saldosEfectivo);
        assertFalse(saldosEfectivo.isEmpty());

    }
}