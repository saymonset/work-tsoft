package com.indeval.portalinternacional.persistence.dao.ConciliacionDivisasInt;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.dto.ConciliacionDivisasIntDTO;
import com.indeval.portalinternacional.persistence.dao.ConciliacionDivisasIntDao;

import java.util.List;

public class ConciliacionDivisasIntDaoImplTest extends BaseDaoTestCase {
    ConciliacionDivisasIntDao conciliacionDivisasIntDao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        conciliacionDivisasIntDao = (ConciliacionDivisasIntDao) getBean("conciliacionDivisasIntDao");
    }

    public void testGetAllByIdBovedaAndIdDivisa() {
        System.out.println("testGetAllByIdBovedaAndIdDivisa()");
        Integer idDivisa = 3;
        Integer idBoveda = 31;

        assertNotNull("ConciliacionDivisasIntDao Nulo", conciliacionDivisasIntDao);
        List<ConciliacionDivisasIntDTO> conciliacionDivisasIntDTO = conciliacionDivisasIntDao.getAllByIdBovedaAndIdDivisa(idBoveda, idDivisa);

        System.out.println("conciliacion divisas array by boveda and divisa: " + conciliacionDivisasIntDTO);

        assertNotNull(conciliacionDivisasIntDTO);
    }

    public void testGetAllByIdDivisa() {
        System.out.println("testGetAllByIdDivisa()");
        Integer idDivisa = 3;

        assertNotNull("ConciliacionDivisasIntDao Nulo", conciliacionDivisasIntDao);
        List<ConciliacionDivisasIntDTO> conciliacionDivisasIntDTO = conciliacionDivisasIntDao.getAllByIdDivisa(idDivisa);

        System.out.println("conciliacion divisas array by divisa: " + conciliacionDivisasIntDTO);

        assertNotNull(conciliacionDivisasIntDTO);
    }
}