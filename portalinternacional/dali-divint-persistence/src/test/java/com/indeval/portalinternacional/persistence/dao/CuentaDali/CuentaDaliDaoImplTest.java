// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.CuentaDali;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.dto.CuentaEfectivoDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.InstitucionWebDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.TipoCuentaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.TipoNaturalezaDTO;
import com.indeval.portalinternacional.persistence.dao.CuentaDaliDAO;
import com.indeval.portalinternacional.persistence.dao.DivisaDaliDao;
import junit.framework.TestCase;

import java.util.List;

import static com.indeval.portalinternacional.persistence.dao.CuentaDali.Util.getCriterio;

public class CuentaDaliDaoImplTest extends BaseDaoTestCase {

    CuentaDaliDAO dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (CuentaDaliDAO) getBean("cuentaDaliDAO");
    }

    public void testBuscarCuentasNombradasEfectivo() {
        System.out.println("testBuscarCuentasNombradasEfectivo()");
        int expectedIdCuenta = 3909;
        String expectedDescripcion = "010092000";
        int expectedSize = 1;

        assertNotNull("cuentaDaliDAO Nulo", dao);

        CuentaEfectivoDTO criterio = getCriterio();
        List<CuentaEfectivoDTO> cuentasNombradasE = dao.buscarCuentasNombradasEfectivo(criterio, null);

        for (CuentaEfectivoDTO cuenta : cuentasNombradasE) {
            System.out.println(cuenta.getInstitucion().getRazonSocial() + " " + cuenta.getInstitucion().getNombre());
        }

        assertNotNull(cuentasNombradasE);
        assertEquals(expectedSize, cuentasNombradasE.size());
        assertEquals(expectedIdCuenta, cuentasNombradasE.get(0).getIdCuenta());
        assertEquals(expectedDescripcion, cuentasNombradasE.get(0).getDescripcion());
    }
}