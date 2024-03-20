package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.dto.ConciliacionDivisasIntDTO;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface ConciliacionDivisasIntDao extends BaseDao {
    List<ConciliacionDivisasIntDTO> getAllByIdBovedaAndIdDivisa(Integer idBoveda, Integer idDivisa, final Date startDate, final Date endDate);

    List<ConciliacionDivisasIntDTO> getAllByIdDivisa(Integer idDivisa, final Date startDate, final Date endDate);
}
