// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaHorariosCustodiosVO;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaMovEfeDivExtVO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface HorariosCustodiosService {

    Map<String, String> getRangoHorariosPorCustodio(Integer idCustodio);

    HorariosCustodiosDto salvarHorarioCustodio(HorariosCustodiosDto horariosCustodios);

    PaginaVO getHorariosCustodios(CriteriosConsultaHorariosCustodiosVO criteriosConsulta, PaginaVO paginaVO);

    HorariosCustodiosDto updateHorariosCustodios(Integer idHorarioCustodio, Integer cambioEstado, String usuarioChecker);
}
