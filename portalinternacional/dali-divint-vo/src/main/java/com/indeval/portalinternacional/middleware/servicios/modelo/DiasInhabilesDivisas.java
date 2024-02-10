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
@SequenceGenerator(name = "foliador", sequenceName = "C_DIAS_INHABILES_DIVISAS", allocationSize = 1, initialValue = 1)
public class DiasInhabilesDivisas implements Serializable {

    private static final long serialVersionUID = -2198929005144500134L;

    private long idDiasInhabiles;

    private Date diaInhabil;

    private Long idDivisa;

    private String creador;

    private Date fechaCreacion;

    private Date fechaUltModificacion;

    public DiasInhabilesDivisas() { super();}

    public DiasInhabilesDivisas(Long idDiasInhabiles, Date diaInhabil, Long idDivisa, String creador, Date fechaCreacion, Date fechaUltModificacion) {
        super();
        this.idDiasInhabiles = idDiasInhabiles;
        this.diaInhabil = diaInhabil;
        this.idDivisa = idDivisa;
        this.creador = creador;
        this.fechaCreacion = fechaCreacion;
        this.fechaUltModificacion = fechaUltModificacion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_DIAS_INHABILES")
    public Long getIdDiasInhabiles(){return idDiasInhabiles;}

    public void setIdDiasInhabiles(Long idDiasInhabiles){this.idDiasInhabiles = idDiasInhabiles;}

    @Column(name= "DIA_INHABIL")
    public Date getDiaInhabil(){return diaInhabil;}

    public void setDiaInhabil(Date diaInhabil){this.diaInhabil = diaInhabil; }

    @Column(name= "ID_DIVISA",unique = true, nullable = false)
    @JoinColumn(name = "ID_DIVISA")
    public Long getIdDivisa(){return idDivisa;}

    public void setIdDivisa(Long idDivisa){this.idDivisa = idDivisa;}

    @Column(name= "CREADOR")
    public String getCreador(){return creador;}

    public void setCreador(String creador){this.creador = creador;}

    @Column(name= "FECHA_CREACION")
    public Date getFechaCreacion(){return fechaCreacion;}

    public void setFechaCreacion(Date fechaCreacion){this.fechaCreacion = fechaCreacion;}

    @Column(name= "FECHA_ULT_MODIFICACION")
    public Date getFechaUltModificacion(){return fechaUltModificacion;}

    public void setFechaUltModificacion(Date fechaUltModificacion){this.fechaUltModificacion = fechaUltModificacion;}

}
