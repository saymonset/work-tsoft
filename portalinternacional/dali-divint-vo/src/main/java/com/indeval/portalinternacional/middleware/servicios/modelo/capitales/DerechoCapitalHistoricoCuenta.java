package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_DERECHO_CAPITAL_HISTORICO")
public class DerechoCapitalHistoricoCuenta {
    @Id
    @Column(name = "ID_DERECHO_CAPITAL_HISTORICO", unique = true, nullable = false)
    private Long idDerechoCapitalHistorico; // ID es el rownum

    @Column(name = "ID_DERECHO_CAPITAL", unique = false, nullable = true)
    private Long idDerechoCapital; // ID_DERECHO_CAPITAL

    @Column(name = "CLAVE_TIPO_VALOR", unique = false, nullable = true)
    private String tipoValor; // CLAVE_TIPO_VALOR

    @Column(name = "CLAVE_PIZARRA", unique = false, nullable = true)
    private String emisora; // CLAVE_PIZARRA

    @Column(name = "SERIE", unique = false, nullable = true)
    private String serie; // SERIE

    @Column(name = "ISIN", unique = false, nullable = true)
    private String isin; // ISIN

    @Column(name = "FECHA_CORTE", unique = false, nullable = true)
    private Date fechaCorte; // FECHA_CORTE

    @Column(name = "FECHA_PAGO_DERECHO_CAPITAL", unique = false, nullable = true)
    private Date fechaPago; // FECHA_PAGO_DERECHO_CAPITAL

    @Column(name = "CUENTA", unique = false, nullable = true)
    private String cuenta; // CUENTA

    @Column(name = "PORCENTAJE_MAV", unique = false, nullable = true)
    private Double porcentajeRetencionMav; // PORCENTAJE_MAV

    @Column(name = "PORCENTAJE_BEN", unique = false, nullable = true)
    private Long porcentajeRetencionBeneficiario; // PORCENTAJE_BEN

    @Column(name = "PORCENTAJE_HB_DEFAULT", unique = false, nullable = true)
    private Double porcentajeRetencionHorarioBenef; // PORCENTAJE_HB_DEFAULT

    @Column(name = "PORCENTAJE_RETENCION_CTB", unique = false, nullable = true)
    private Long porcentajeRetencionCuenta; // PORCENTAJE_RETENCION_CTB

    @Column(name = "ASIGNACION", unique = false, nullable = true)
    private Long asignacion; // ASIGNACION

    @Column(name = "PROPORCION", unique = false, nullable = true)
    private Double proporcion; // PROPORCION

    @Column(name = "FEE", unique = false, nullable = true)
    private Double fee; // FEE

    @Column(name = "MONTO_BRUTO", unique = false, nullable = true)
    private Double montoBruto; // MONTO_BRUTO

    @Column(name = "IMPUESTO_RETENIDO", unique = false, nullable = true)
    private Double impuestoRetenido; // IMPUESTO_RETENIDO

    @Column(name = "MONTO_NETO", unique = false, nullable = true)
    private Double montoNeto; // MONTO_NETO

    @Column(name = "id_estatus_derecho", unique = false, nullable = true)
    private Long idEstatusDerecho; // id_estatus_derecho

    @Column(name = "ID_CATBIC", unique = false, nullable = true)
    private Long idCatBic; // ID_CATBIC

    @Column(name = "ID_FOLIO_INSTITUCION", unique = false, nullable = true)
    private String idFolioInstitucion;

    @Column(name = "PORCENTAJE_RETENCION_REAL", unique = false, nullable = true)
    private Double porcentajeRetencionReal;

    @Column(name = "MONTO_FEE", unique = false, nullable = true)
    private Double montoFee;

    @Column(name = "DIVISA", unique = false, nullable = true)
    private String divisa; // clave_alfabetica

    @Column(name = "DETALLE_CUSTODIO", unique = false, nullable = true)
    private String custodio; // DETALLE_CUSTODIO

    @Column(name = "ID_TIPO_DERECHO", unique = false, nullable = true)
    private Long idTipoDerecho; // ID_TIPO_DERECHO

    @Column(name = "DESC_TIPO_DERECHO", unique = false, nullable = true)
    private String tipoDerecho; // DESC_TIPO_DERECHO

    @Column(name = "DESC_Estatus_DERECHO", unique = false, nullable = true)
    private String estadoDerecho; // DESC_Estatus_DERECHO

    /** @return this.tipoValor */
    public String getTipoValor() {
        return this.tipoValor;
    }

    /** @param tipoValor to be set in this.tipoValor */
    public void setTipoValor(final String tipoValor) {
        this.tipoValor = tipoValor;
    }

    /** @return this.emisora */
    public String getEmisora() {
        return this.emisora;
    }

    /** @param emisora to be set in this.emisora */
    public void setEmisora(final String emisora) {
        this.emisora = emisora;
    }

    /** @return this.serie */
    public String getSerie() {
        return this.serie;
    }

    /** @param serie to be set in this.serie */
    public void setSerie(final String serie) {
        this.serie = serie;
    }

    /** @return this.isin */
    public String getIsin() {
        return this.isin;
    }

    /** @param isin to be set in this.isin */
    public void setIsin(final String isin) {
        this.isin = isin;
    }

    /** @return this.fechaPago */
    public Date getFechaPago() {
        return this.fechaPago;
    }

    /** @param fechaPago to be set in this.fechaPago */
    public void setFechaPago(final Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    /** @return this.fechaCorte */
    public Date getFechaCorte() {
        return this.fechaCorte;
    }

    /** @param fechaCorte to be set in this.fechaCorte */
    public void setFechaCorte(final Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    /** @return this.cuenta */
    public String getCuenta() {
        return this.cuenta;
    }

    /** @param cuenta to be set in this.cuenta */
    public void setCuenta(final String cuenta) {
        this.cuenta = cuenta;
    }


    /** @return this.proporcion */
    public Double getProporcion() {
        return this.proporcion;
    }

    /** @param proporcion to be set in this.proporcion */
    public void setProporcion(final Double proporcion) {
        this.proporcion = proporcion;
    }

    /** @return this.fee */
    public Double getFee() {
        return this.fee;
    }

    /** @param fee to be set in this.fee */
    public void setFee(final Double fee) {
        this.fee = fee;
    }

    /** @return this.porcentajeRetencionMav */
    public Double getPorcentajeRetencionMav() {
        return this.porcentajeRetencionMav;
    }

    /** @param porcentajeRetencionMav to be set in this.porcentajeRetencionMav */
    public void setPorcentajeRetencionMav(final Double porcentajeRetencionMav) {
        this.porcentajeRetencionMav = porcentajeRetencionMav;
    }

    /** @return this.porcentajeRetencionBeneficiario */
    public Long getPorcentajeRetencionBeneficiario() {
        return this.porcentajeRetencionBeneficiario;
    }

    /** @param porcentajeRetencionBeneficiario to be set in this.porcentajeRetencionBeneficiario */
    public void setPorcentajeRetencionBeneficiario(final Long porcentajeRetencionBeneficiario) {
        this.porcentajeRetencionBeneficiario = porcentajeRetencionBeneficiario;
    }

    /** @return this.porcentajeRetenciónHorarioBenef */
    public Double getPorcentajeRetencionHorarioBenef() {
        return this.porcentajeRetencionHorarioBenef;
    }

    /** @param porcentajeRetenciónHorarioBenef to be set in this.porcentajeRetenciónHorarioBenef */
    public void setPorcentajeRetencionHorarioBenef(final Double porcentajeRetencionHorarioBenef) {
        this.porcentajeRetencionHorarioBenef = porcentajeRetencionHorarioBenef;
    }

    /** @return this.porcentajeRetencionCuenta */
    public Long getPorcentajeRetencionCuenta() {
        return this.porcentajeRetencionCuenta;
    }

    /** @param porcentajeRetencionCuenta to be set in this.porcentajeRetencionCuenta */
    public void setPorcentajeRetencionCuenta(final Long porcentajeRetencionCuenta) {
        this.porcentajeRetencionCuenta = porcentajeRetencionCuenta;
    }

    /** @return this.asignacion */
    public Long getAsignacion() {
        return this.asignacion;
    }

    /** @param asignacion to be set in this.asignacion */
    public void setAsignacion(final Long asignacion) {
        this.asignacion = asignacion;
    }



    /** @return this.montoBruto */
    public Double getMontoBruto() {
        return this.montoBruto;
    }

    /** @param montoBruto to be set in this.montoBruto */
    public void setMontoBruto(final Double montoBruto) {
        this.montoBruto = montoBruto;
    }

    /** @return this.impuestoRetenido */
    public Double getImpuestoRetenido() {
        return this.impuestoRetenido;
    }

    /** @param impuestoRetenido to be set in this.impuestoRetenido */
    public void setImpuestoRetenido(final Double impuestoRetenido) {
        this.impuestoRetenido = impuestoRetenido;
    }

    /** @return this.montoNeto */
    public Double getMontoNeto() {
        return this.montoNeto;
    }

    /** @param montoNeto to be set in this.montoNeto */
    public void setMontoNeto(final Double montoNeto) {
        this.montoNeto = montoNeto;
    }

    /** @param idEstatusDerecho to be set in this.idEstatusDerecho */
    public void setIdEstatusDerecho(final Long idEstatusDerecho) {
        this.idEstatusDerecho = idEstatusDerecho;
    }

    /**
     * @return the idDerechoCapitalHistorico
     */
    public Long getIdDerechoCapitalHistorico() {
        return this.idDerechoCapitalHistorico;
    }

    /**
     * @param idDerechoCapitalHistorico the idDerechoCapitalHistorico to set
     */
    public void setIdDerechoCapitalHistorico(final Long idDerechoCapitalHistorico) {
        this.idDerechoCapitalHistorico = idDerechoCapitalHistorico;
    }

    /**
     * @return the idDerechoCapital
     */
    public Long getIdDerechoCapital() {
        return this.idDerechoCapital;
    }

    /**
     * @param idDerechoCapital the idDerechoCapital to set
     */
    public void setIdDerechoCapital(final Long idDerechoCapital) {
        this.idDerechoCapital = idDerechoCapital;
    }

    /**
     * @return the idEstatusDerecho
     */
    public Long getIdEstatusDerecho() {
        return this.idEstatusDerecho;
    }

    /**
     * Método para obtener el atributo idCatBic
     * 
     * @return El atributo idCatBic
     */
    public Long getIdCatBic() {
        return this.idCatBic;
    }

    /**
     * Método para establecer el atributo idCatBic
     * 
     * @param idCatBic El valor del atributo idCatBic a establecer.
     */
    public void setIdCatBic(final Long idCatBic) {
        this.idCatBic = idCatBic;
    }

    /**
     * Método para obtener el atributo idFolioInstitucion
     * 
     * @return El atributo idFolioInstitucion
     */
    public String getIdFolioInstitucion() {
        return this.idFolioInstitucion;
    }

    /**
     * Método para establecer el atributo idFolioInstitucion
     * 
     * @param idFolioInstitucion El valor del atributo idFolioInstitucion a establecer.
     */
    public void setIdFolioInstitucion(final String idFolioInstitucion) {
        this.idFolioInstitucion = idFolioInstitucion;
    }

    /**
     * Método para obtener el atributo porcentajeRetencionReal
     * 
     * @return El atributo porcentajeRetencionReal
     */
    public Double getPorcentajeRetencionReal() {
        return this.porcentajeRetencionReal;
    }

    /**
     * Método para establecer el atributo porcentajeRetencionReal
     * 
     * @param porcentajeRetencionReal El valor del atributo porcentajeRetencionReal a establecer.
     */
    public void setPorcentajeRetencionReal(final Double porcentajeRetencionReal) {
        this.porcentajeRetencionReal = porcentajeRetencionReal;
    }

    /**
     * Regresa el porcentaje de retención con el símbolo %.
     * 
     * @return Cadena con el porcentaje.
     */
    public String getPorcentajeRetencion() {
        String porcentaje = null;
        if (this.porcentajeRetencionReal != null) {
            porcentaje = this.porcentajeRetencionReal + "%";
        }
        return porcentaje;
    }

    /**
     * Método para obtener el atributo montoFee
     * 
     * @return El atributo montoFee
     */
    public Double getMontoFee() {
        return this.montoFee;
    }

    /**
     * Método para establecer el atributo montoFee
     * 
     * @param montoFee El valor del atributo montoFee a establecer.
     */
    public void setMontoFee(final Double montoFee) {
        this.montoFee = montoFee;
    }

    /**
     * Método para obtener el atributo divisa
     * 
     * @return El atributo divisa
     */
    public String getDivisa() {
        return this.divisa;
    }

    /**
     * Método para establecer el atributo divisa
     * 
     * @param divisa El valor del atributo divisa a establecer.
     */
    public void setDivisa(final String divisa) {
        this.divisa = divisa;
    }

    /**
     * Método para obtener el atributo custodio
     * 
     * @return El atributo custodio
     */
    public String getCustodio() {
        return this.custodio;
    }

    /**
     * Método para establecer el atributo custodio
     * 
     * @param custodio El valor del atributo custodio a establecer.
     */
    public void setCustodio(final String custodio) {
        this.custodio = custodio;
    }



    /** @return this.tipoDerecho */
    public String getTipoDerecho() {
        return this.tipoDerecho;
    }

    /** @param tipoDerecho to be set in this.tipoDerecho */
    public void setTipoDerecho(final String tipoDerecho) {
        this.tipoDerecho = tipoDerecho;
    }

    /** @return this.idTipoDerecho */
    public Long getIdTipoDerecho() {
        return this.idTipoDerecho;
    }

    /** @param idTipoDerecho to be set in this.idTipoDerecho */
    public void setIdTipoDerecho(final Long idTipoDerecho) {
        this.idTipoDerecho = idTipoDerecho;
    }

    /** @return this.estadoDerecho */
    public String getEstadoDerecho() {
        return this.estadoDerecho;
    }

    /** @param estadoDerecho to be set in this.estadoDerecho */
    public void setEstadoDerecho(final String estadoDerecho) {
        this.estadoDerecho = estadoDerecho;
    }


}
