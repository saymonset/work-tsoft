// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.dto;

import java.io.Serializable;
import java.util.Date;

public class HorariosCustodiosDto implements Serializable {

    private Integer idHorariosCustodios;
    private Integer idDivisa;
    private Integer idCustodio;
    private String horarioInicial;
    private String  horarioFinal;
    private String creador;
    private Date fechaCreacion;
    private Date fechaUltModificacion;
    private Integer estatus;
    private String usuarioChecker;

    public HorariosCustodiosDto() {
    }

    public HorariosCustodiosDto(Integer idHorariosCustodios, Integer idDivisa, Integer idCustodio, String horarioInicial, String horarioFinal, String creador, Date fechaCreacion, Date fechaUltModificacion, Integer estatus, String usuarioChecker) {
        this.idHorariosCustodios = idHorariosCustodios;
        this.idDivisa = idDivisa;
        this.idCustodio = idCustodio;
        this.horarioInicial = horarioInicial;
        this.horarioFinal = horarioFinal;
        this.creador = creador;
        this.fechaCreacion = fechaCreacion;
        this.fechaUltModificacion = fechaUltModificacion;
        this.estatus = estatus;
        this.usuarioChecker = usuarioChecker;
    }

    public Integer getIdHorariosCustodios() {
        return idHorariosCustodios;
    }

    public void setIdHorariosCustodios(Integer idHorariosCustodios) {
        this.idHorariosCustodios = idHorariosCustodios;
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

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaUltModificacion() {
        return fechaUltModificacion;
    }

    public void setFechaUltModificacion(Date fechaUltModificacion) {
        this.fechaUltModificacion = fechaUltModificacion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getUsuarioChecker() {
        return usuarioChecker;
    }

    public void setUsuarioChecker(String usuarioChecker) {
        this.usuarioChecker = usuarioChecker;
    }
}
