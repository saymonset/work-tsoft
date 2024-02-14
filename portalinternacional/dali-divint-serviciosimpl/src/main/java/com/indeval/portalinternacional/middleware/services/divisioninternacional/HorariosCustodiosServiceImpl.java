// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaHorariosCustodiosVO;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaMovEfeDivExtVO;
import com.indeval.portalinternacional.persistence.dao.HorariosCustodiosDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HorariosCustodiosServiceImpl implements HorariosCustodiosService {

    HorariosCustodiosDao horariosCustodiosDao;

    public HorariosCustodiosServiceImpl() {
    }

    public Map<String, String> getRangoHorariosPorCustodio(Integer idCustodio) {
        List<Object[]> resultados = horariosCustodiosDao.getHorarioInicialYHorarioFinalPorIdCustodio(idCustodio);

        Map<String, String> horarioMap = new HashMap<>();
        horarioMap.put("horarioInicial", resultados.get(0)[0].toString());
        horarioMap.put("horarioFinal", resultados.get(0)[1].toString());

        return horarioMap;
    }

    @Override
    public HorariosCustodiosDto salvarHorarioCustodio(HorariosCustodiosDto horariosCustodios) {
        return horariosCustodiosDao.salvarHorarioCustodio(horariosCustodios);
    }

    public void setHorariosCustodiosDao(HorariosCustodiosDao horariosCustodiosDao) {
        this.horariosCustodiosDao = horariosCustodiosDao;
    }

    public HorariosCustodiosDao getHorariosCustodiosDao() {
        return this.horariosCustodiosDao;
    }

    @Override
    public PaginaVO getHorariosCustodios(CriteriosConsultaHorariosCustodiosVO criteriosConsulta, PaginaVO paginaVO) {
        return this.horariosCustodiosDao.getHorariosCustodios(criteriosConsulta, paginaVO);
    }

    @Override
    public HorariosCustodiosDto updateHorariosCustodios(
            Integer idHorarioCustodio, Integer cambioEstado, String usuarioChecker) {
        return this.horariosCustodiosDao.updateHorariosCustodios(idHorarioCustodio, cambioEstado, usuarioChecker);
    }

}
