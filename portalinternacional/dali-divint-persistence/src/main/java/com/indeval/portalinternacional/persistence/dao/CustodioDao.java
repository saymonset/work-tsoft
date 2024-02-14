// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;

import java.util.List;

public interface CustodioDao extends BaseDao {

    Integer getIdCustodioByIdCatbic(Long idCatbic);

    List<Custodio> findAll();

}
