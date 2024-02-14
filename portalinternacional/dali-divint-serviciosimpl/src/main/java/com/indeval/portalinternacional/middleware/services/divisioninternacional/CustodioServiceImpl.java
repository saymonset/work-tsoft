// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.persistence.dao.CustodioDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CustodioServiceImpl implements CustodioService {
    private static final Logger LOG = LoggerFactory.getLogger(CustodioServiceImpl.class);


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

    @Override
    public List<Custodio> findAll() {
        List<Custodio> custodios = custodioDao.findAll();
        return custodios;
    }
}
