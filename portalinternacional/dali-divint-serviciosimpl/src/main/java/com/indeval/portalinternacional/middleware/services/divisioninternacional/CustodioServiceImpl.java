// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.persistence.dao.CustodioDao;

public class CustodioServiceImpl implements CustodioService{

    CustodioDao custodioDao;

    public CustodioServiceImpl() {
    }

    public Integer getIdCustodioByIdCatbic(Long idCatbic) {
        return custodioDao.getIdCustodioByIdCatbic(idCatbic);
    }

    public CustodioDao getCustodioDao() {
        return custodioDao;
    }

    public void setCustodioDao(CustodioDao custodioDao) {
        this.custodioDao = custodioDao;
    }
}
