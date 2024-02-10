/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Modelo que representa la tabla C_INSTRUMENTO.
 *
 * @author Roman Rubio
 * @version 1.0
 */
@Entity
@Table(name = "C_INSTRUMENTO")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Instrumentos implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador del instrumento.
     */
    @Id
    @Column(name = "ID_INSTRUMENTO")
    private Long idInstrumento;
    /**
     * Clave del tipo de instrumento.
     */
    @Column(name = "CLAVE_TIPO_VALOR")
    private String claveTipoValor;
    /**
     * Clave CFI del instrumento.
     */
    @Column(name = "CLAVE_CFI")
    private String claveCfi;
    /**
     * Descripci&oacute;n del instrumento.
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;
    /**
     * Descripci&oacute;n en ingles del instrumento.
     */
    @Column(name = "DESCRIPCION_INGLES")
    private String descripcionIngles;

    @Column(name = "REPORTABLE")
    private String reportable;

    @Column(name = "ES_SOCIEDADES_INVERSION")
    private Boolean esSociedadesInversion;

    /**
     *
     */
    public Instrumentos() {
    }

    /**
     * Identificador secuencial para el Instrumento.
     * @return Long
     */
    public Long getIdInstrumento() {
        return idInstrumento;
    }

    /**
     * Identificador secuencial para el Instrumento.
     * @param idInstrumento
     */
    public void setIdInstrumento(Long idInstrumento) {
        this.idInstrumento = idInstrumento;
    }

    /**
     * Representa la clave del tipo de valor
     * @return String
     */
    public String getClaveTipoValor() {
        return claveTipoValor;
    }

    /**
     * Representa la clave del tipo de valor
     * @param claveTipoValor
     */
    public void setClaveTipoValor(String claveTipoValor) {
        this.claveTipoValor = claveTipoValor;
    }

    /**
     * Clasificación del instrumento de acuerdo al catálogo de tipos de
     * instrumentos ISO-10962/CFI
     * @return String
     */
    public String getClaveCfi() {
        return claveCfi;
    }

    /**
     * Clasificación del instrumento de acuerdo al catálogo de tipos de
     * instrumentos ISO-10962/CFI
     * @param claveCfi
     */
    public void setClaveCfi(String claveCfi) {
        this.claveCfi = claveCfi;
    }

    /**
     * Descripción del Tipo de Valor o Instrumento
     * @return String
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Descripción del Tipo de Valor o Instrumento
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Descripción del Tipo de Valor o Instrumento en ingls.
     * @return String
     */
    public String getDescripcionIngles() {
        return descripcionIngles;
    }

    /**
     * Descripción del Tipo de Valor o Instrumento en ingls.
     * @param descripcionIngles
     */
    public void setDescripcionIngles(String descripcionIngles) {
        this.descripcionIngles = descripcionIngles;
    }

    /**
     *
     * @return
     */
    public String getReportable() {
        return reportable;
    }

    /**
     *
     * @param reportable
     */
    public void setReportable(String reportable) {
        this.reportable = reportable;
    }

    public Boolean getEsSociedadesInversion() {
        return esSociedadesInversion;
    }

    public void setEsSociedadesInversion(Boolean esSociedadesInversion) {
        this.esSociedadesInversion = esSociedadesInversion;
    }

}
