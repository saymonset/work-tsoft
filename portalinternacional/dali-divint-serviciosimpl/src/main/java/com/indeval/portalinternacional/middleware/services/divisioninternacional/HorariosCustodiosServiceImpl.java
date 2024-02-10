// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.persistence.dao.HorariosCustodiosDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HorariosCustodiosServiceImpl implements HorariosCustodiosService{

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

    public void setHorariosCustodiosDao(HorariosCustodiosDao horariosCustodiosDao) {
        this.horariosCustodiosDao = horariosCustodiosDao;
    }

    public HorariosCustodiosDao getHorariosCustodiosDao() {
        return this.horariosCustodiosDao;
    }
}
