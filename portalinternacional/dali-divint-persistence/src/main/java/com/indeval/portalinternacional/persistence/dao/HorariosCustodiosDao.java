// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaHorariosCustodiosVO;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaMovEfeDivExtVO;
import com.indeval.portalinternacional.middleware.servicios.vo.HorariosCustodiosVO;

import java.util.List;

public interface HorariosCustodiosDao extends BaseDao {

    List<HorariosCustodiosDto> findAllByIdDivisa(Integer idDivisa);

    List<Object[]> getHorarioInicialYHorarioFinalPorIdCustodio(Integer idCustodio);

    HorariosCustodiosDto salvarHorarioCustodio(HorariosCustodiosDto horariosCustodios);

    Integer ultimoID();

    Divisa obtenerDivisa(int idDivisa);

    Custodio obtenerCustodio(int idCustodio);

    PaginaVO getHorariosCustodios(CriteriosConsultaHorariosCustodiosVO criteriosConsulta, PaginaVO paginaVO);

    HorariosCustodiosDto updateHorariosCustodios(Integer idHorarioCustodio, Integer cambioEstado, String usuarioChecker);
}
