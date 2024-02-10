// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.CatBic;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.persistence.dao.BovedaDao;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;
import junit.framework.TestCase;

public class CatBicDaoImplTest extends BaseDaoTestCase {

    CatBicDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (CatBicDao) getBean("catBicDao");
    }

    public void testFindCatBicEnBaseABovedaEfectivoParticipante() {
        System.out.println("testFindCatBicEnBaseABovedaEfectivoParticipante()");

        Long idBoveda = 13L;
        Long idInstitucion = 9L;
        Long expectedIdCatBic = 5L;

        assertNotNull("catBicDao Nulo", dao);
        Long idCatbic = dao.findCatBicEnBaseABovedaEfectivoParticipante(idBoveda, idInstitucion);

        assertNotNull(idCatbic);
        assertEquals(expectedIdCatBic, idCatbic);
    }
}