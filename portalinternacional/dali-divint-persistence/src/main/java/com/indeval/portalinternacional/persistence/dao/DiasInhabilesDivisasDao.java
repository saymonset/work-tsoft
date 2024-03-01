// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.DiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.HistoricoDiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.DiasInhabilesDivisas;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorariosCustodios;
import com.indeval.portalinternacional.persistence.util.DTOAssembler;

import java.util.Date;
import java.util.List;

public interface DiasInhabilesDivisasDao extends BaseDao {

    List<Date> getDiasInhabilesByIdDivisa(Long idDivisa);

    List<Integer> getAniosDisponibles();

    PaginaVO getDiasInhabilesByIdHistorico(final Long idHistorico, final PaginaVO paginaVO);

    void saveDiasInhabilesDivisas(DiasInhabilesDivisas diasInhabilesDivisas);

    void updateDiaInhabilDivisas(Long id, Integer cambioEstado);

}
