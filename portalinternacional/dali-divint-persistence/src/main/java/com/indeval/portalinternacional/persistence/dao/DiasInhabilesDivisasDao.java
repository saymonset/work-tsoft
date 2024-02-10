// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;

import java.util.Date;
import java.util.List;

public interface DiasInhabilesDivisasDao extends BaseDao {

    List<Date> getDiasInhabilesByIdDivisa(Long idDivisa);

}
