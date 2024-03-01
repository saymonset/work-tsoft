/**
 * Multidivisas: Controller para registro de horarios de operación de custodios
 */

package com.indeval.portalinternacional.presentation.controller.horariosCustodios;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.CustodioService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.HorariosCustodiosService;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.presentation.controller.common.CapturaOperacionesController;
import com.indeval.portalinternacional.middleware.servicios.constantes.EstatusDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.ID_ESTADO_REGISTRADO;
import static com.indeval.portalinternacional.presentation.controller.horariosCustodios.UtilHorariosCustodioController.*;

/**
 * Multidivisas: Controller para consultas de horarios de operación de custodios
 */

public class HorariosCustodiosRegistroController extends CapturaOperacionesController {

    /*Trasa de Log*/
    private static final Logger LOG = LoggerFactory.getLogger(HorariosCustodiosRegistroController.class);
    private Integer idDivisa;
    private Integer idCustodio;
    private String horarioInicial;
    private String horarioFinal;
    private Integer estatus;
    private List<SelectItem> listaDivisas;
    private List<SelectItem> listaCustodio;
    private List<SelectItem> horaInicio;
    private List<SelectItem> horaFin;
    private DivisaDao divisaDao;
    private CustodioService custodioService;
    private List<HorariosCustodiosDto> horariosCustodiosDto;
    private HorariosCustodiosService horariosCustodiosService;

    private boolean consultaEjecutada = false;


    /**
     * Inicializa lo necesario para la pantalla de registros
     */
    public String getInicializarRegistro() {
        LOG.info("getInicializarRegistro");

        listaCustodio = obtenerCustodios(custodioService.findAll());
        listaDivisas = obtenerDivisas(divisaDao.findDivisas());

        horariosCustodiosDto = new ArrayList<HorariosCustodiosDto>();

        horaInicio = obtenerListaHorasDelDia(true);
        horaFin = obtenerListaHorasDelDia(false);


        LOG.debug("cargaSelecciones :: REGISTROS");
        idDivisa = Integer.parseInt((String) listaDivisas.get(0).getValue());
        idCustodio = Integer.parseInt((String) listaCustodio.get(0).getValue());

        consultaEjecutada = false;
        return "";
    }


    /**
     * Registrar Horario de Custodio
     *
     * @param evt Evento lanzado por botón buscar
     */
    public void registrarHorarioCustodio(ActionEvent evt) {
        LOG.info("Registrar Horario Custodio");
        LOG.debug("idBoveda : " + idCustodio + " | " +
                "idDivisa: " + idDivisa + " | " +
                "horarioInicial: " + horarioInicial + " | " +
                "horarioFinal: " + horarioFinal);
        horariosCustodiosDto = new ArrayList<HorariosCustodiosDto>();
        try {
            this.consultaEjecutada = false;
            HorariosCustodiosDto horarioCustodio = new HorariosCustodiosDto();
            horarioCustodio.setIdDivisa(idDivisa);
            horarioCustodio.setIdCustodio(idCustodio);
            horarioCustodio.setHorarioInicial(horarioInicial);
            horarioCustodio.setHorarioFinal(horarioFinal);
            horarioCustodio.setFechaCreacion(new Date());
            horarioCustodio.setFechaUltModificacion(horarioCustodio.getFechaCreacion());
            horarioCustodio.setEstatus(ID_ESTADO_REGISTRADO);
            horarioCustodio.setCreador(getNombreUsuarioSesion());

            LOG.debug("Salvar :: " + horarioCustodio);
            HorariosCustodiosDto nuevoHorarioCustodioDto = horariosCustodiosService.salvarHorarioCustodio(horarioCustodio);


            if (horariosCustodiosDto != null) {
                LOG.debug("Horario Custodio Registrado :: " + nuevoHorarioCustodioDto.toString());
                this.consultaEjecutada = true;
                nuevoHorarioCustodioDto.setHorarioInicial(
                        nuevoHorarioCustodioDto.getHorarioInicial().substring(0,
                                nuevoHorarioCustodioDto.getHorarioInicial().length() - 3));
                nuevoHorarioCustodioDto.setHorarioFinal(
                        nuevoHorarioCustodioDto.getHorarioFinal().substring(0,
                                nuevoHorarioCustodioDto.getHorarioFinal().length() - 3));
                this.horariosCustodiosDto.add(nuevoHorarioCustodioDto);
                LOG.debug(nuevoHorarioCustodioDto.toString());
                agregarInfoMensaje("Horario registrado con exito");
            }
        } catch (Exception ex) {
            LOG.error(ex.toString(), ex);
            this.consultaEjecutada = false;
        }

        if (!this.consultaEjecutada) {
            horariosCustodiosDto = new ArrayList<HorariosCustodiosDto>();
            agregarMensaje(new BusinessException("ERROR: No fue posible registrar el horario del custodio"));
        }
    }

    /**
     * Obtienen la descripción del estado del horario del custodio
     *
     * @param estatus Estado del horario
     */
    public String obtenerDescripcionEstatus(int estatus) {
        return EstatusDB.obtenerDescripcion(estatus);
    }


    public Integer getIdDivisa() {
        return idDivisa;
    }

    public void setIdDivisa(Integer idDivisa) {
        this.idDivisa = idDivisa;
    }

    public Integer getIdCustodio() {
        return idCustodio;
    }

    public void setIdCustodio(Integer idCustodio) {
        this.idCustodio = idCustodio;
    }

    public String getHorarioInicial() {
        return horarioInicial;
    }

    public void setHorarioInicial(String horarioInicial) {
        this.horarioInicial = horarioInicial;
    }

    public String getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(String horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public List<SelectItem> getListaDivisas() {
        return listaDivisas;
    }

    public void setListaDivisas(List<SelectItem> listaDivisas) {
        this.listaDivisas = listaDivisas;
    }

    public List<SelectItem> getListaCustodio() {
        return listaCustodio;
    }

    public void setListaCustodio(List<SelectItem> listaCustodio) {
        this.listaCustodio = listaCustodio;
    }

    public List<SelectItem> getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(List<SelectItem> horaInicio) {
        this.horaInicio = horaInicio;
    }

    public List<SelectItem> getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(List<SelectItem> horaFin) {
        this.horaFin = horaFin;
    }

    public DivisaDao getDivisaDao() {
        return divisaDao;
    }

    public void setDivisaDao(DivisaDao divisaDao) {
        this.divisaDao = divisaDao;
    }

    public CustodioService getCustodioService() {
        return custodioService;
    }

    public void setCustodioService(CustodioService custodioService) {
        this.custodioService = custodioService;
    }

    public List<HorariosCustodiosDto> getHorariosCustodiosDto() {
        return horariosCustodiosDto;
    }

    public void setHorariosCustodiosDto(List<HorariosCustodiosDto> horariosCustodiosDto) {
        this.horariosCustodiosDto = horariosCustodiosDto;
    }

    public HorariosCustodiosService getHorariosCustodiosService() {
        return horariosCustodiosService;
    }

    public void setHorariosCustodiosService(HorariosCustodiosService horariosCustodiosService) {
        this.horariosCustodiosService = horariosCustodiosService;
    }

    public boolean isConsultaEjecutada() {
        return consultaEjecutada;
    }

    public void setConsultaEjecutada(boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }


}
