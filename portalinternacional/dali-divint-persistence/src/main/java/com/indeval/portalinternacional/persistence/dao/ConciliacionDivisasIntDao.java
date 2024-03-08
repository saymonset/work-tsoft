package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.dto.ConciliacionDivisasIntDTO;

import java.math.BigInteger;
import java.util.List;

public interface ConciliacionDivisasIntDao extends BaseDao {
    List<ConciliacionDivisasIntDTO> getAllByIdBovedaAndIdDivisa(Integer idBoveda, Integer idDivisa);

    List<ConciliacionDivisasIntDTO> getAllByIdDivisa(Integer idDivisa);
}
