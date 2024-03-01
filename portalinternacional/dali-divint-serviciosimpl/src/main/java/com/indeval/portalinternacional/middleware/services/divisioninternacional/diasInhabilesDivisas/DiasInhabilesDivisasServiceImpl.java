/*
 * Multidivisas: Dás inhábiles por Divisa
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional.diasInhabilesDivisas;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DiasInhabilesDivisasService;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.DiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.HistoricoDiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.DiasInhabilesDivisas;
import com.indeval.portalinternacional.middleware.servicios.modelo.HistoricoDiasInhabilesDivisas;
import com.indeval.portalinternacional.persistence.dao.DiasInhabilesDivisasDao;
import com.indeval.portalinternacional.persistence.dao.HistoricoDiasInhabilesDivisasDao;
import com.indeval.portalinternacional.persistence.util.DTOAssembler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiasInhabilesDivisasServiceImpl implements DiasInhabilesDivisasService {

    DiasInhabilesDivisasDao diasInhabilesDivisasDao;
    HistoricoDiasInhabilesDivisasDao historicoDiasInhabilesDivisasDao;

    public DiasInhabilesDivisasServiceImpl() {
    }

    public List<Date> getDiasInhabilesByIdDivisa(Long idDivisa) {
        return diasInhabilesDivisasDao.getDiasInhabilesByIdDivisa(idDivisa);
    }

    @Override
    public List<Integer> getAniosDisponibles() {
        return diasInhabilesDivisasDao.getAniosDisponibles();
    }

    @Override
    public List<HistoricoDiasInhabilesDivisasDTO> getAll(String creador, Integer anio) {
        List<HistoricoDiasInhabilesDivisasDTO> resultadosPresentacion = new ArrayList<>();

        List<HistoricoDiasInhabilesDivisas> resultados =
                this.historicoDiasInhabilesDivisasDao.obtenerTodo(creador, anio);
        for (HistoricoDiasInhabilesDivisas entity : resultados) {
            resultadosPresentacion.add(DTOAssembler.crearHistoricoDiasInhabilesDivisasDTO(entity));
        }
        return resultadosPresentacion;
    }

    @Override
    public PaginaVO  getDiasInhabilesByIdHistorico(final Long idHistorico, final PaginaVO paginaVO) {
        return diasInhabilesDivisasDao.getDiasInhabilesByIdHistorico(idHistorico,paginaVO);
    }

    @Override
    public Long getUltimoID() {
        return historicoDiasInhabilesDivisasDao.ultimoID();
    }

    @Override
    public void salvarHistorico(HistoricoDiasInhabilesDivisas historicoDiasInhabilesDivisas) {
        historicoDiasInhabilesDivisasDao.save(historicoDiasInhabilesDivisas);
    }

    @Override
    public void salvarDiasInhabiles(List<DiasInhabilesDivisas> diasInhabilesDivisas) {
        for (DiasInhabilesDivisas diasInhabilDivisa : diasInhabilesDivisas) {
            diasInhabilesDivisasDao.saveDiasInhabilesDivisas(diasInhabilDivisa);
        }
    }

    @Override
    public void actualizarHistorico(Long idHorarioCustodio, Integer cambioEstado, String usuarioChecker) {
        historicoDiasInhabilesDivisasDao.updateHistorico(idHorarioCustodio, cambioEstado, usuarioChecker);
    }

    @Override
    public void actualizarDiaInhabil(Long idDiaInhabil, Integer cambioEstado) {
        diasInhabilesDivisasDao.updateDiaInhabilDivisas(idDiaInhabil, cambioEstado);
    }


    public DiasInhabilesDivisasDao getDiasInhabilesDivisasDao() {
        return diasInhabilesDivisasDao;
    }

    public void setDiasInhabilesDivisasDao(DiasInhabilesDivisasDao diasInhabilesDivisasDao) {
        this.diasInhabilesDivisasDao = diasInhabilesDivisasDao;
    }

    public HistoricoDiasInhabilesDivisasDao getHistoricoDiasInhabilesDivisasDao() {
        return historicoDiasInhabilesDivisasDao;
    }

    public void setHistoricoDiasInhabilesDivisasDao(HistoricoDiasInhabilesDivisasDao historicoDiasInhabilesDivisasDao) {
        this.historicoDiasInhabilesDivisasDao = historicoDiasInhabilesDivisasDao;
    }
}
