// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;

public interface CustodioDao extends BaseDao {

    Integer getIdCustodioByIdCatbic(Long idCatbic);

}
