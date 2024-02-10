// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.CuentaCorresponsal;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaCorresponsal;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;
import com.indeval.portalinternacional.persistence.dao.CuentaCorresponsalDao;
import junit.framework.TestCase;

import java.util.List;

public class CuentaCorresponsalDaoImplTest extends BaseDaoTestCase {

    CuentaCorresponsalDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (CuentaCorresponsalDao) getBean("cuentaCorresponsalDao");
    }

    public void testFindCuentaCorresponsalByIdDivisaAndIdInstitucion() {
        System.out.println("testFindCuentaCorresponsalByIdDivisaAndIdInstitucion()");

        Long idDivisa = 3L;
        Long idInstitucion = 9L;
        Long expectedIdCuentaCorresponsal = 8L;
        int expectedSize = 1;
        String expectedBeneficiaryNameAddressP = "INTERNACIONAL, SEGURIDAD, AMBIENTE 12345678901234567890123456789012345.                                  -";

        assertNotNull("cuentaCorresponsalDao Nulo", dao);
        List<CuentaCorresponsal> cuentasCorresponsal = dao.findCuentaCorresponsalByIdDivisaAndIdInstitucion(idDivisa, idInstitucion);

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

        assertNotNull("cuentaCorresponsalDao Nulo", dao);
        List<Long> idDivisas = dao.findIdDivisasByIdInstitucion(idInstitucion);

        for (Long idDivisa : idDivisas) {
            System.out.println(idDivisa);
        }

        assertFalse(idDivisas.isEmpty());
        assertNotNull(idDivisas);
        assertEquals(expectedSize, idDivisas.size());
    }
}