package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad para la vista que se usa en la consulta de derechos de capital.
 * @author edeloera
 */
@Entity
@Table(name = "V_CONS_DER_CAP")
public class VConsultaDerechosCapital implements Serializable {

    private static final long serialVersionUID = 13L;

	@Id
	@Column(name = "ID_DERECHO_CAPITAL")
	private Long idDerechoCapital;

    @Column(name = "ID_EMISION")
    private Long idEmision;

	@Column(name = "CLAVE_TIPO_VALOR")
	private String claveTipoValor;

	@Column(name= "CLAVE_PIZARRA")
	private String clavePizarra;

	@Column(name = "SERIE")
	private String serie;

	@Column(name = "CLAVE_CUPON")
	private String claveCupon;

	@Column(name = "ISIN")
	private String isin;

	@Column(name = "ES_EXTRANJERA")
	private Boolean esExtranjera;

	@Column(name = "FECHA_CORTE")
	private Date fechaCorte;

	@Column(name = "FECHA_PAGO_DERECHO_CAPITAL")
	private Date fechaPagoDerechoCapital;

	@Column(name = "PROPORCION")
	private Float proporcion;

	@Column(name = "FEE")
	private Float fee;

	@Column(name = "ID_DIVISA")
	private Long idDivisa;

	@Column(name = "CLAVE_ALFABETICA")
	private String claveAlfabetica;

	@Column(name = "ID_BOVEDA")
	private Long idBoveda;

	@Column(name = "BOVEDA")
	private String boveda;

    @Column(name = "BOVEDA_CUSTODIO")
    private String bovedaCustodio;

    @Column(name = "ID_ESTATUS_DERECHO")
    private Long idEstatusDerecho;

    @Column(name = "ID_ESTATUS_DERECHO_CORTADO")
    private Long idEstatusDerechoCortado;

    @Column(name = "DESC_ESTATUS_DERECHO")
    private String descEstatusDerecho;

    @Column(name = "ID_TIPO_DERECHO")
    private Integer idTipoDerecho;

    @Column(name = "DESC_TIPO_DERECHO")
    private String descTipoDerecho;

    @Column(name = "ID_SUBTIPO_DERECHO")
    private Long idSubtipoDerecho;

    @Column(name = "DESC_SUBTIPO_DERECHO")
    private String descSubtipoDerecho;

    @Column(name = "ID_CUENTA_NOMBRADA")
    private Long idCuentaNombrada;

    /*@Column(name = "ESTATUS_REGISTRO")
    private String estatusRegistro;*/

    @Column(name = "BOVEDA_VALORES_DERECHO")
    private String bovedaValoresDerecho;
    
    @Column(name = "ID_FOLIO")
    private Long idFolio;

    @Column(name = "IMPORTE_ESPERADO")
    private Long importeEsperado;

    @Column(name = "IMPORTE_CONFIRMADO")
    private Long importeConfirmado;

    @Column(name = "DIFERENCIA")
    private Long diferencia;
    public Long getIdDerechoCapital() {
        return idDerechoCapital;
    }

    public void setIdDerechoCapital(Long idDerechoCapital) {
        this.idDerechoCapital = idDerechoCapital;
    }

    public Long getIdEmision() {
        return idEmision;
    }

    public void setIdEmision(Long idEmision) {
        this.idEmision = idEmision;
    }

    public String getClaveTipoValor() {
        return claveTipoValor;
    }

    public void setClaveTipoValor(String claveTipoValor) {
        this.claveTipoValor = claveTipoValor;
    }

    public String getClavePizarra() {
        return clavePizarra;
    }

    public void setClavePizarra(String clavePizarra) {
        this.clavePizarra = clavePizarra;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getClaveCupon() {
        return claveCupon;
    }

    public void setClaveCupon(String claveCupon) {
        this.claveCupon = claveCupon;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Boolean esExtranjera() {
        return esExtranjera;
    }

    public void setEsExtranjera(Boolean esExtranjera) {
        this.esExtranjera = esExtranjera;
    }

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public Date getFechaPagoDerechoCapital() {
        return fechaPagoDerechoCapital;
    }

    public void setFechaPagoDerechoCapital(Date fechaPagoDerechoCapital) {
        this.fechaPagoDerechoCapital = fechaPagoDerechoCapital;
    }

    public Float getProporcion() {
        return proporcion;
    }

    public void setProporcion(Float proporcion) {
        this.proporcion = proporcion;
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public Long getIdDivisa() {
        return idDivisa;
    }

    public void setIdDivisa(Long idDivisa) {
        this.idDivisa = idDivisa;
    }

    public String getClaveAlfabetica() {
        return claveAlfabetica;
    }

    public void setClaveAlfabetica(String claveAlfabetica) {
        this.claveAlfabetica = claveAlfabetica;
    }

    public Long getIdBoveda() {
        return idBoveda;
    }

    public void setIdBoveda(Long idBoveda) {
        this.idBoveda = idBoveda;
    }

    public String getBoveda() {
        return boveda;
    }

    public void setBoveda(String boveda) {
        this.boveda = boveda;
    }

    public String getBovedaCustodio() {
        return bovedaCustodio;
    }

    public void setBovedaCustodio(String bovedaCustodio) {
        this.bovedaCustodio = bovedaCustodio;
    }

    public Long getIdEstatusDerecho() {
        return idEstatusDerecho;
    }

    public void setIdEstatusDerecho(Long idEstatusDerecho) {
        this.idEstatusDerecho = idEstatusDerecho;
    }

    public Long getIdEstatusDerechoCortado() {
        return idEstatusDerechoCortado;
    }

    public void setIdEstatusDerechoCortado(Long idEstatusDerechoCortado) {
        this.idEstatusDerechoCortado = idEstatusDerechoCortado;
    }

    public String getDescEstatusDerecho() {
        return descEstatusDerecho;
    }

    public void setDescEstatusDerecho(String descEstatusDerecho) {
        this.descEstatusDerecho = descEstatusDerecho;
    }

    public Integer getIdTipoDerecho() {
        return idTipoDerecho;
    }

    public void setIdTipoDerecho(Integer idTipoDerecho) {
        this.idTipoDerecho = idTipoDerecho;
    }

    public String getDescTipoDerecho() {
        return descTipoDerecho;
    }

    public void setDescTipoDerecho(String descTipoDerecho) {
        this.descTipoDerecho = descTipoDerecho;
    }

    public Long getIdSubtipoDerecho() {
        return idSubtipoDerecho;
    }

    public void setIdSubtipoDerecho(Long idSubtipoDerecho) {
        this.idSubtipoDerecho = idSubtipoDerecho;
    }

    public String getDescSubtipoDerecho() {
        return descSubtipoDerecho;
    }

    public void setDescSubtipoDerecho(String descSubtipoDerecho) {
        this.descSubtipoDerecho = descSubtipoDerecho;
    }

    public Long getIdCuentaNombrada() {
        return idCuentaNombrada;
    }

    public void setIdCuentaNombrada(Long idCuentaNombrada) {
        this.idCuentaNombrada = idCuentaNombrada;
    }

    /*public String getEstatusRegistro() {
        return estatusRegistro;
    }

    public void setEstatusRegistro(String estatusRegistro) {
        this.estatusRegistro = estatusRegistro;
    }*/

    public Long getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(Long idFolio) {
        this.idFolio = idFolio;
    }

    public Long getImporteEsperado() { return importeEsperado; }

    public void setImporteEsperado(Long importeEsperado) { this.importeEsperado = importeEsperado; }

    public Long getImporteConfirmado() { return importeConfirmado; }

    public void setImporteConfirmado(Long importeConfirmado) { this.importeConfirmado = importeConfirmado; }

    public Long getDiferencia() { return diferencia; }

    public void setDiferencia(Long diferencia) { this.diferencia = diferencia; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("idDerechoCapital=[" + this.idDerechoCapital.toString() + "] | ").
        append("claveTipoValor=[" + this.claveTipoValor + "] | ").
        append("clavePizarra=[" + this.clavePizarra + "] | ").
        append("serie=[" + this.serie + "] | ").
        append("claveCupon=[" + this.claveCupon + "] | ").
        append("isin=[" + this.isin + "] | ").
        append("esExtranjera=[" + this.esExtranjera + "] | ").
        append("fechaCorte=[" + this.fechaCorte.toString() + "] | ").
        append("fechaPagoDerechoCapital=[" + this.fechaPagoDerechoCapital.toString() + "] | ").
        append("proporcion=[" + this.proporcion.toString() + "] | ").
        append("fee=[" + this.fee.toString() + "] | ").
        append("idDivisa=[" + this.idDivisa.toString() + "] | ").
        append("claveAlfabetica=[" + this.claveAlfabetica + "] | ").
        append("idBoveda=[" + this.idBoveda.toString() + "] | ").
        append("boveda=[" + this.boveda + "] | ").
        append("bovedaCustodio=[" + this.bovedaCustodio + "] | ").
        append("idEstatusDerecho=[" + this.idEstatusDerecho.toString() + "] | ").
        append("idEstatusDerechoCortado=[" + this.idEstatusDerechoCortado.toString() + "] | ").
        append("descEstatusDerecho=[" + this.descEstatusDerecho + "] | ").
        append("idTipoDerecho=[" + this.idTipoDerecho.toString() + "] | ").
        append("descTipoDerecho=[" + this.descTipoDerecho + "] | ").
        append("idSubtipoDerecho=[" + this.idSubtipoDerecho.toString() + "] | ").
        append("descSubtipoDerecho=[" + this.descSubtipoDerecho + "] | ").
        //append("idCuentaNombrada=[" + this.idCuentaNombrada.toString() + "] | ").
        //append("estatusRegistro=[" + this.estatusRegistro + "] | ").
        append("idFolio=[" + this.idFolio.toString() + "] | ").
        append("importeEsperado=[" + this.importeEsperado.toString() + "] | ").
        append("importeConfirmado=[" + this.importeConfirmado.toString() + "] | ").
        append("diferencia=[" + this.diferencia.toString() + "] | ").
        append("bovedaValoresDerecho=[" + this.bovedaValoresDerecho + "]").toString()
        ;
    }

	public String getBovedaValoresDerecho() {
		return bovedaValoresDerecho;
	}

	public void setBovedaValoresDerecho(String bovedaValoresDerecho) {
		this.bovedaValoresDerecho = bovedaValoresDerecho;
	}

}
