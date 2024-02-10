// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.persistence.dao.DiasInhabilesDivisasDao;

import java.util.Date;
import java.util.List;

public class DiasInhabilesDivisasServiceImpl implements DiasInhabilesDivisasService{

    DiasInhabilesDivisasDao diasInhabilesDivisasDao;

    public DiasInhabilesDivisasServiceImpl() {
    }

    public List<Date> getDiasInhabilesByIdDivisa(Long idDivisa) {
        return diasInhabilesDivisasDao.getDiasInhabilesByIdDivisa(idDivisa);
    }

    public DiasInhabilesDivisasDao getDiasInhabilesDivisasDao() {
        return diasInhabilesDivisasDao;
    }

    public void setDiasInhabilesDivisasDao(DiasInhabilesDivisasDao diasInhabilesDivisasDao) {
        this.diasInhabilesDivisasDao = diasInhabilesDivisasDao;
    }
}
