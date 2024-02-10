// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.custodio;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.persistence.dao.CustodioDao;
import junit.framework.TestCase;

public class CustodioDaoImplTest extends BaseDaoTestCase {
    CustodioDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (CustodioDao) getBean("custodioDao");
    }

    public void testGetIdCustodioByIdCatbic() {
        Long idCatbic = 5L;
        Integer expectedIdCustodio = 2;
        Integer idCustodio =dao.getIdCustodioByIdCatbic(idCatbic);

        System.out.println("Id Custodio: " + idCustodio);

        assertEquals(expectedIdCustodio, idCustodio);
    }
}