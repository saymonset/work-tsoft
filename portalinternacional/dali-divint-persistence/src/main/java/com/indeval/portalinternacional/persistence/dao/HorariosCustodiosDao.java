// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;

import java.util.List;

public interface HorariosCustodiosDao extends BaseDao {

    List<HorariosCustodiosDto> findAllByIdDivisa(Integer idDivisa);

    List<Object[]> getHorarioInicialYHorarioFinalPorIdCustodio(Integer idCustodio);
}
