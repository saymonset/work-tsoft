// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.HistoricoDiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.HistoricoDiasInhabilesDivisas;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorariosCustodios;
import com.indeval.portalinternacional.persistence.util.DTOAssembler;

import java.util.Date;
import java.util.List;

public interface HistoricoDiasInhabilesDivisasDao extends BaseDao {
    List<HistoricoDiasInhabilesDivisas> obtenerTodo(final String creador, final Integer anio);

    Long ultimoID();

    void updateHistorico(final Long idHorarioCustodio, final Integer cambioEstado, final String usuarioChecker);


}
