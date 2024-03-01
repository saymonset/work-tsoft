// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.DiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.HistoricoDiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.DiasInhabilesDivisas;
import com.indeval.portalinternacional.middleware.servicios.modelo.HistoricoDiasInhabilesDivisas;

import java.util.Date;
import java.util.List;

public interface DiasInhabilesDivisasService {
    List<Date> getDiasInhabilesByIdDivisa(Long idDivisa);

    List<Integer> getAniosDisponibles();

    List<HistoricoDiasInhabilesDivisasDTO> getAll(String creador, Integer anio);

    PaginaVO getDiasInhabilesByIdHistorico(Long idHistorico, PaginaVO paginaVO);

    Long getUltimoID();

    void salvarHistorico(HistoricoDiasInhabilesDivisas historicoDiasInhabilesDivisas);

    void salvarDiasInhabiles(List<DiasInhabilesDivisas> diasInhabilesDivisas);

    void actualizarHistorico(Long idHorarioCustodio, Integer cambioEstado, String usuarioChecker);

    void actualizarDiaInhabil(Long idDiaInhabil, Integer cambioEstado);
}
