// Cambio Multidivisas
/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Modelo que representa la tabla C_DIAS_INHABILES_DIVISAS.
 */
@Entity
@Table(name = "C_DIAS_INHABILES_DIVISAS")
@SequenceGenerator(name = "foliador", sequenceName = "C_DIAS_INHABIL_ID_SEQ", allocationSize = 1, initialValue = 1)
public class DiasInhabilesDivisas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_DIAS_INHABILES")
    private long idDiasInhabiles;

    @Column(name = "DIA_INHABIL")
    private Date diaInhabil;

    @Column(name = "ID_DIVISA", unique = true, nullable = false)
    @JoinColumn(name = "ID_DIVISA")
    private Long idDivisa;

    @Column(name = "CREADOR")
    private String creador;

    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;

    @Column(name = "FECHA_ULT_MODIFICACION")
    private Date fechaUltModificacion;

    @Column(name = "ID_HISTORICO")
    private Long idHistoricoDiasInhabilesDivisas;

    @Column(name = "ESTATUS")
    private Integer estatus;

    public Long getIdDiasInhabiles() {
        return idDiasInhabiles;
    }

    public void setIdDiasInhabiles(Long idDiasInhabiles) {
        this.idDiasInhabiles = idDiasInhabiles;
    }

    public Date getDiaInhabil() {
        return diaInhabil;
    }

    public void setDiaInhabil(Date diaInhabil) {
        this.diaInhabil = diaInhabil;
    }

    public Long getIdDivisa() {
        return idDivisa;
    }

    public void setIdDivisa(Long idDivisa) {
        this.idDivisa = idDivisa;
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

    public Long getIdHistoricoDiasInhabilesDivisas() {
        return idHistoricoDiasInhabilesDivisas;
    }

    public void setIdHistoricoDiasInhabilesDivisas(Long idHistoricoDiasInhabilesDivisas) {
        this.idHistoricoDiasInhabilesDivisas = idHistoricoDiasInhabilesDivisas;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "DiasInhabilesDivisas{" +
                "idDiasInhabiles=" + idDiasInhabiles +
                ", diaInhabil=" + diaInhabil +
                ", idDivisa=" + idDivisa +
                ", creador='" + creador + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaUltModificacion=" + fechaUltModificacion +
                ", idHistoricoDiasInhabilesDivisas=" + idHistoricoDiasInhabilesDivisas +
                ", estatus=" + estatus +
                '}';
    }
}
