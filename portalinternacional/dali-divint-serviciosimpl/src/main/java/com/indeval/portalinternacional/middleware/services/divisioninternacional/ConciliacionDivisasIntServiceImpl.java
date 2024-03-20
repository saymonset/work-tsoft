package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portalinternacional.middleware.servicios.dto.ConciliacionDivisasIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionDivisasVO;
import com.indeval.portalinternacional.persistence.dao.BovedaDao;
import com.indeval.portalinternacional.persistence.dao.ConciliacionDivisasIntDao;
import com.indeval.portalinternacional.persistence.dao.DivisaDaliDao;

import javax.faces.model.SelectItem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConciliacionDivisasIntServiceImpl implements ConciliacionDivisasIntService {
    private ConciliacionDivisasIntDao conciliacionDivisasIntDao;

    private BovedaDao bovedaDao;

    private DivisaDaliDao divisaDao;

    @Override
    public List<ConciliacionDivisasVO> getAllBy(Integer idBoveda, Integer idDivisa, Date startDate, Date endDate) {
        List<ConciliacionDivisasIntDTO> conciliations = new ArrayList<ConciliacionDivisasIntDTO>();

        if (idBoveda != -1) {
            conciliations = conciliacionDivisasIntDao.getAllByIdBovedaAndIdDivisa(idBoveda, idDivisa, startDate, endDate);
        } else {
            conciliations = conciliacionDivisasIntDao.getAllByIdDivisa(idDivisa, startDate, endDate);
        }

        List<ConciliacionDivisasVO> conciliacionesDivisasVO = new ArrayList<ConciliacionDivisasVO>();

        for (ConciliacionDivisasIntDTO conciliation: conciliations) {
            ConciliacionDivisasVO conciliacionDivisasVO = new ConciliacionDivisasVO();

            conciliacionDivisasVO.setBovedaDescription(bovedaDao.getBovedaDescriptionById(conciliation.getIdBoveda()));
            conciliacionDivisasVO.setDivisaDescripcion(divisaDao.getDivisaDescripcionById(conciliation.getIdDivisa()));
            conciliacionDivisasVO.setIdConciliacionEfectivo(conciliation.getIdConciliacionEfectivo());
            conciliacionDivisasVO.setMontoCustodio(conciliation.getMontoCustodio());
            conciliacionDivisasVO.setMontoIndeval(conciliation.getMontoIndeval());
            conciliacionDivisasVO.setMontoDiferencia(conciliation.getMontoDiferencia());
            conciliacionDivisasVO.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(conciliation.getFecha()));

            conciliacionesDivisasVO.add(conciliacionDivisasVO);
        }

        return conciliacionesDivisasVO;
    }

    public void setConciliacionDivisasIntDao(ConciliacionDivisasIntDao conciliacionDivisasIntDao) {
        this.conciliacionDivisasIntDao = conciliacionDivisasIntDao;
    }

    public void setBovedaDao(BovedaDao bovedaDao) {
        this.bovedaDao = bovedaDao;
    }

    public void setDivisaDao(DivisaDaliDao divisaDao) {
        this.divisaDao = divisaDao;
    }
}
