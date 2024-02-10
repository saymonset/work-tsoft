/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Modelo que representa la tabla C_EMISORA.
 */
@Entity
@Table(name = "C_EMISORA")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Emisoras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_EMISORA", nullable = false)
    private Integer idEmisora;
    /**
     *
     */
    @Column(name = "CLAVE_PIZARRA")
    private String clavePizarra;
    /**
     *
     */
    @Column(name = "ID_TIPO_EMISORA")
    private Integer idTipoEmisora;
    /**
     *
     */
    @Column(name = "CLAVE")
    private String clave;
    /**
     *
     */
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    /**
     *
     */
    @Column(name = "ESTATUS_EMISORA")
    private Integer estatusEmisora;

    public Integer getIdEmisora() {
        return idEmisora;
    }

    public void setIdEmisora(Integer idEmisora) {
        this.idEmisora = idEmisora;
    }

    public String getClavePizarra() {
        return clavePizarra;
    }

    public void setClavePizarra(String clavePizarra) {
        this.clavePizarra = clavePizarra;
    }

    public Integer getIdTipoEmisora() {
        return idTipoEmisora;
    }

    public void setIdTipoEmisora(Integer idTipoEmisora) {
        this.idTipoEmisora = idTipoEmisora;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the estatusEmisora
     */
    public Integer getEstatusEmisora() {
        return estatusEmisora;
    }

    /**
     * @param estatusEmisora the estatusEmisora to set
     */
    public void setEstatusEmisora(Integer estatusEmisora) {
        this.estatusEmisora = estatusEmisora;
    }
}
