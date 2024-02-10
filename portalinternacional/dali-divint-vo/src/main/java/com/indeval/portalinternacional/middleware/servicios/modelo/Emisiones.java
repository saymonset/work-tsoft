/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.indeval.portaldali.persistence.modelo.Cupon;
import com.indeval.portaldali.persistence.modelo.Divisa;

/**
 * Modelo que representa la tabla C_EMISION.
 */
@Entity
@Table(name = "C_EMISION")
public class Emisiones implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador de la emisi&oacute;n.
     */
    @Id
    @Column(name = "ID_EMISION")
    private Long idEmision;
    /**
     * Instrumento de la emisi&oacute;n.
     */
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "ID_INSTRUMENTO")
    private Instrumentos instrumento;
    /**
     * Identificador de la emisora.
     */
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "ID_EMISORA")
    private Emisoras emisora;
    /**
     * Serie de la emisi&oacute;n.
     */
    @Column(name = "SERIE")
    private String serie;
    /**
     * Isin de la emisi&oacute;n.
     */
    @Column(name = "ISIN")
    private String isin;
    /**
     * Valor nominal de la emisi&oacute;n.
     */
    @Column(name = "VALOR_NOMINAL", precision = 19, scale = 2)
    private BigDecimal valorNominal;
    /**
     *
     */
    @Column(name = "FECHA_LIMITE_MOVIMIENTOS")
    private Date fechaLimitMotivos;
    /**
     * Fecha de inicio de la emisi&oacute;n.
     */
    @Column(name = "FECHA_EMISION")
    private Date fechaEmision;
    /**
     *
     */
    @Column(name = "PRIMER_FECHA_PAGO")
    private Date fechaPago;
    /**
     * Fecha de expiraci&oacute;n de la emisi&oacute;n.
     */
    @Column(name = "FECHA_VENCIMIENTO")
    private Date fechaExpiracion;
    /**
     *
     */
    @OneToMany(mappedBy = "emision", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @OrderBy("claveCupon")
    private Set<Cupon> cupones;
    /**
     *
     */
    @Column(name = "ID_ESTATUS_EMISION")
    private Integer idEstatusEmision;
    /**
     *
     */
    @Column(name = "EMISION_EXTRANJERA")
    private String emisionExtranjera;
    /**
     *
     */
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "ID_REPRESENTANTE_COMUN", updatable = false, insertable = false)
    private Instituciones representanteComun;
    /**
     *
     */
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "ID_DIVISA_VALOR_NOMINAL", updatable = false, insertable = false)
    private Divisa divisa;
    /**
     *
     */
    @Column(name = "ID_TIPO_EMISION")
    private Integer idTipoEmision;
    /**
     *
     */
    @Column(name = "POSICION_CIRCULACION")
    private BigDecimal posicionCirculacion;
    /**
     *
     */
    @Column(name = "MONTO_CIRCULACION")
    private BigDecimal montoCirculacion;
    /**
     *
     */
    @Column(name = "FECHA_POSICION")
    private Date fechaUltimaModificacion;
    
    /**
     * Id de la boveda asociada a la emision.
     */
    @Column(name = "ID_BOVEDA", unique = false, nullable = false)
    private Integer idBoveda;

    /**
     * Constructor vacio
     */
    public Emisiones() {
    }

    /**
     * Identificador secuencial para la Emision.
     * @return long
     */
    public Long getIdEmision() {
        return idEmision;
    }

    /**
     * Identificador secuencial para la Emision.
     * @param idEmision
     */
    public void setIdEmision(Long idEmision) {
        this.idEmision = idEmision;
    }

    /**
     * Referencia al Instrumento de la Emisi&oacute;n (tipo valor).
     * @return Instrumento
     */
    public Instrumentos getInstrumento() {
        return instrumento;
    }

    /**
     * Referencia al Instrumento de la Emisi&oacute;n (tipo valor).
     * @param instrumento
     */
    public void setInstrumento(Instrumentos instrumento) {
        this.instrumento = instrumento;
    }

    /**
     * Serie de la Emisi&oacute;n. Indica la fecha en la que vence el papel.
     * @return String
     */
    public String getSerie() {
        return serie;
    }

    /**
     * Serie de la Emisi&oacute;n. Indica la fecha en la que vence el papel.
     * @param serie
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * C&oacute;digo de Identificaci&oacute;n del instrumento financiero
     * (Homologaci&oacute;n con ISIN ISO  6166).
     * @return String
     */
    public String getIsin() {
        return isin;
    }

    /**
     * C&oacute;digo de Identificaci&oacute;n del instrumento financiero
     * (Homologaci&oacute;n con ISIN ISO  6166).
     * @param isin
     */
    public void setIsin(String isin) {
        this.isin = isin;
    }

    /**
     * Es el precio de referencia, expresado en unidades correspondientes a
     * la divisa asociada, que aparece en los títulos en el momento de
     * su emisi&oacute;n, como expresi&oacute;n de parte del capital contable que
     * represente y como antecedente para definir el precio de su
     * suscripci&oacute;n. En los títulos de deuda, el valor nominal es el valor
     * del título a vencimiento.
     * @return Double
     */
    public BigDecimal getValorNominal() {
        return valorNominal;
    }

    /**
     * Es el precio de referencia, expresado en unidades correspondientes a
     * la divisa asociada, que aparece en los títulos en el momento de
     * su emisi&oacute;n, como expresi&oacute;n de parte del capital contable que
     * represente y como antecedente para definir el precio de su
     * suscripci&oacute;n. En los títulos de deuda, el valor nominal es el valor
     * del título a vencimiento.
     * @param valorNominal
     */
    public void setValorNominal(BigDecimal valorNominal) {
        this.valorNominal = valorNominal;
    }

    /**
     * Fecha en que se hace la emisi&oacute;n.
     * @return Date
     */
    public Date getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Fecha en que se hace la emisi&oacute;n.
     * @param fechaEmision
     */
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * Fecha de vencimiento de la emisi&oacute;n.
     * @return Date
     */
    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    /**
     * @param fechaExpiracion
     */
    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(67, 97).append(idEmision).toHashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean ret = false;
        if (!(obj instanceof Emisiones)) {
            ret = false;
        } else {
            Emisiones rhs = (Emisiones) obj;
            ret = new EqualsBuilder().append(idEmision, rhs.getIdEmision()).isEquals();
        }
        return ret;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

    /**
     * @return the emisora
     */
    public Emisoras getEmisora() {
        return emisora;
    }

    /**
     * @param emisora the emisora to set
     */
    public void setEmisora(Emisoras emisora) {
        this.emisora = emisora;
    }

    /**
     * @return the idEstatusEmision
     */
    public Integer getIdEstatusEmision() {
        return idEstatusEmision;
    }

    /**
     * @param idEstatusEmision the idEstatusEmision to set
     */
    public void setIdEstatusEmision(Integer idEstatusEmision) {
        this.idEstatusEmision = idEstatusEmision;
    }

    /**
     * @return the emisionExtranjera
     */
    public String getEmisionExtranjera() {
        return emisionExtranjera;
    }

    /**
     * @param emisionExtranjera the emisionExtranjera to set
     */
    public void setEmisionExtranjera(String emisionExtranjera) {
        this.emisionExtranjera = emisionExtranjera;
    }

    /**
     * @return the representanteComun
     */
    public Instituciones getRepresentanteComun() {
        return representanteComun;
    }

    /**
     * @param representanteComun the representanteComun to set
     */
    public void setRepresentanteComun(Instituciones representanteComun) {
        this.representanteComun = representanteComun;
    }

    public Divisa getDivisa() {
        return divisa;
    }

    public void setDivisa(Divisa divisa) {
        this.divisa = divisa;
    }

    public Integer getIdTipoEmision() {
        return idTipoEmision;
    }

    public void setIdTipoEmision(Integer idTipoEmision) {
        this.idTipoEmision = idTipoEmision;
    }

    public Set<Cupon> getCupones() {
        return cupones;
    }

    public void setCupones(Set<Cupon> cupones) {
        this.cupones = cupones;
    }

    public Date getFechaLimitMotivos() {
        return fechaLimitMotivos;
    }

    public void setFechaLimitMotivos(Date fechaLimitMotivos) {
        this.fechaLimitMotivos = fechaLimitMotivos;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getPosicionCirculacion() {
        return posicionCirculacion;
    }

    public void setPosicionCirculacion(BigDecimal posicionCirculacion) {
        this.posicionCirculacion = posicionCirculacion;
    }

    public BigDecimal getMontoCirculacion() {
        return montoCirculacion;
    }

    public void setMontoCirculacion(BigDecimal montoCirculacion) {
        this.montoCirculacion = montoCirculacion;
    }

    /**
     * @return the fechaUltimaModificacion
     */
    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    /**
     * @param fechaUltimaModificacion the fechaUltimaModificacion to set
     */
    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public Integer getIdBoveda() {
        return idBoveda;
    }

    public void setIdBoveda(Integer idBoveda) {
        this.idBoveda = idBoveda;
    }

}
