package com.indeval.portalinternacional.persistence.dao.ConciliacionEfectivo;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraConciliacionEfectivoInt;
import com.indeval.portalinternacional.persistence.dao.ConciliacionEfectivoDao;

import java.util.List;

public class ConciliacionEfectivoDaoImplTest extends BaseDaoTestCase {
    ConciliacionEfectivoDao conciliacionEfectivoDao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        conciliacionEfectivoDao = (ConciliacionEfectivoDao) getBean("conciliacionEfectivoDao");
    }

    public void testGetBitacoraConciliacionEfectivoInt() {
        System.out.println("testGetBitacoraConciliacionEfectivoInt()");
        String idConciliacionEfectivo = "7,8,9";

        assertNotNull("conciliacionEfectivoDao Nulo", conciliacionEfectivoDao);
        List<BitacoraConciliacionEfectivoInt> messages = conciliacionEfectivoDao.getBitacoraConciliacionEfectivoInt(idConciliacionEfectivo);

        System.out.println("Messages by multiple ids: " + messages);

        assertNotNull(messages);
    }
}