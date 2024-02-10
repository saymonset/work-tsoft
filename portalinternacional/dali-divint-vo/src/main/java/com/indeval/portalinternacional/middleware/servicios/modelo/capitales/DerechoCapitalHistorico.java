package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;

@Entity
@Table(name = "T_DERECHO_CAPITAL_HISTORICO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_DERECHO_CAPITAL_HIST", allocationSize = 1, initialValue = 1)
public class DerechoCapitalHistorico {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
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

    @Column(name = "FECHA_PAGO_DERECHO_CAPITAL", unique = false, nullable = true)
    private Date fechaPago; // FECHA_PAGO_DERECHO_CAPITAL

    @Column(name = "FECHA_CORTE", unique = false, nullable = true)
    private Date fechaCorte; // FECHA_CORTE

    @Column(name = "ID_CATBIC", unique = false, nullable = true)
    private Long idCatBic; // ID_CATBIC

    @Column(name = "DETALLE_CUSTODIO", unique = false, nullable = true)
    private String custodio; // DETALLE_CUSTODIO

    @Column(name = "ID_CUENTA_NOM_BEN", unique = false, nullable = true)
    private Long idCuentaNombradaBeneficiario; // ID_CUENTA_NOM_BEN

    @Column(name = "ID_CUENTA_NOM_CTA", unique = false, nullable = true)
    private Long idCuentaNombradaCuenta; // ID_CUENTA_NOM_CTA

    @Column(name = "CUENTA", unique = false, nullable = true)
    private String cuenta; // CUENTA

    @Column(name = "SALDO", unique = false, nullable = true)
    private Long saldo; // SALDO

    @Column(name = "ID_DIVISA", unique = false, nullable = true)
    private Long idDivisa; // ID_DIVISA

    @Column(name = "ID_BENEFICIARIO", unique = false, nullable = true)
    private Long idBeneficiario; // ID_BENEFICIARIO

    @Column(name = "ADP", unique = false, nullable = true)
    private String adp; // ADP

    @Column(name = "uoi", unique = false, nullable = true)
    private String uoi; // uoi

    @Column(name = "BENEFICIARIO", unique = false, nullable = true)
    private String beneficiario;

    @Column(name = "PAIS_INCORPORACION", unique = false, nullable = true)
    private String paisIncorporacion; // PAIS_INCORPORACION

    @Column(name = "ID_TIPO_BENEFICIARIO", unique = false, nullable = true)
    private Long idTipoBeneficiario; // ID_TIPO_BENEFICIARIO

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_BENEFICIARIO", insertable = false, updatable = false)
    private TipoBeneficiario tipoBeneficiario;

    @Column(name = "ID_DOMICILIO_W", unique = false, nullable = true)
    private Long idDomicilioW; // DOMICILIO

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_DOMICILIO_W", insertable = false, updatable = false)
    private Domicilio domicilio;

    @Column(name = "TIPO_FORMATO", unique = false, nullable = true)
    private String tipoFormato; // TIPO_FORMATO

    @Column(name = "PROPORCION", unique = false, nullable = true)
    private Double proporcion; // PROPORCION

    @Column(name = "FEE", unique = false, nullable = true)
    private Double fee; // FEE

    @Column(name = "PORCENTAJE_MAV", unique = false, nullable = true)
    private Double porcentajeMav; // PORCENTAJE_MAV

    @Column(name = "PORCENTAJE_BEN", unique = false, nullable = true)
    private Long porcentajeBen; // PORCENTAJE_BEN

    @Column(name = "PORCENTAJE_HB_DEFAULT", unique = false, nullable = true)
    private Double porcentajeHBDefault; // PORCENTAJE_HB_DEFAULT

    @Column(name = "PORCENTAJE_RETENCION_CTB", unique = false, nullable = true)
    private Long porcentajeRetencionCTB; // PORCENTAJE_RETENCION_CTB

    @Column(name = "ASIGNACION", unique = false, nullable = true)
    private Long asignacion; // ASIGNACION

    @Column(name = "ID_TIPO_DERECHO", unique = false, nullable = true)
    private Long idTipoDerecho; // ID_TIPO_DERECHO

    @Column(name = "MONTO_BRUTO", unique = false, nullable = true)
    private Double montoBruto; // MONTO_BRUTO

    @Column(name = "IMPUESTO_RETENIDO", unique = false, nullable = true)
    private Double impuestoRetenido; // IMPUESTO_RETENIDO

    @Column(name = "MONTO_NETO", unique = false, nullable = true)
    private Double montoNeto; // MONTO_NETO

    @Column(name = "id_estatus_derecho", unique = false, nullable = true)
    private Long idEstatusDerecho; // id_estatus_derecho

    @Column(name = "DIVISA", unique = false, nullable = true)
    private String divisa; // clave_alfabetica

    @Column(name = "desc_estatus_derecho", unique = false, nullable = true)
    private String descEstatusDerecho; // desc_estatus_derecho

    @Column(name = "RFC", unique = false, nullable = true)
    private String rfc;

    @Column(name = "PORCENTAJE_RETENCION_REAL", unique = false, nullable = true)
    private Double porcentajeRetencionReal;

    @Column(name = "ID_FOLIO_INSTITUCION", unique = false, nullable = true)
    private String idFolioInstitucion;

    @Column(name = "MONTO_FEE", unique = false, nullable = true)
    private Double montoFee;

    @Column(name = "FECHA_FORMATO_W")
    private Date fechaFormatoW;

    @Column(name = "ESTADO_W")
    private String estadoW;

    @Column(name = "GIIN")
    private String giin;

    @Column(name = "REFERENCE_NUMBER")
    private String referenceNumber;

    @Column(name = "EXEMPT_PAYEE_CODE")
    private String exempPayeeCode;

    @Column(name = "EXEMPT_FATCA_REP_CODE")
    private String exemptFatcaRepCode;

    @Column(name = "FECHA_NACIMIENTO_FW")
    private Date fechaNacimientoFW;

    @Column(name = "SSN_EIN")
    private String ssnEin;

    @Column(name = "TIIN")
    private String tiin;

    @Column(name = "DESC_TIPO_DERECHO", unique = false, nullable = true)
    private String tipoDerecho;
    
    @Transient
    private String foreigntiin;

    /**
     * Método para obtener el atributo idDerechoCapitalHistorico
     * 
     * @return El atributo idDerechoCapitalHistorico
     */
    public Long getIdDerechoCapitalHistorico() {
        return this.idDerechoCapitalHistorico;
    }

    /**
     * Método para establecer el atributo idDerechoCapitalHistorico
     * 
     * @param idDerechoCapitalHistorico El valor del atributo idDerechoCapitalHistorico a
     *        establecer.
     */
    public void setIdDerechoCapitalHistorico(final Long idDerechoCapitalHistorico) {
        this.idDerechoCapitalHistorico = idDerechoCapitalHistorico;
    }

    /**
     * Método para obtener el atributo idDerechoCapital
     * 
     * @return El atributo idDerechoCapital
     */
    public Long getIdDerechoCapital() {
        return this.idDerechoCapital;
    }

    /**
     * Método para establecer el atributo idDerechoCapital
     * 
     * @param idDerechoCapital El valor del atributo idDerechoCapital a establecer.
     */
    public void setIdDerechoCapital(final Long idDerechoCapital) {
        this.idDerechoCapital = idDerechoCapital;
    }

    /**
     * Método para obtener el atributo tipoValor
     * 
     * @return El atributo tipoValor
     */
    public String getTipoValor() {
        return this.tipoValor;
    }

    /**
     * Método para establecer el atributo tipoValor
     * 
     * @param tipoValor El valor del atributo tipoValor a establecer.
     */
    public void setTipoValor(final String tipoValor) {
        this.tipoValor = tipoValor;
    }

    /**
     * Método para obtener el atributo emisora
     * 
     * @return El atributo emisora
     */
    public String getEmisora() {
        return this.emisora;
    }

    /**
     * Método para establecer el atributo emisora
     * 
     * @param emisora El valor del atributo emisora a establecer.
     */
    public void setEmisora(final String emisora) {
        this.emisora = emisora;
    }

    /**
     * Método para obtener el atributo serie
     * 
     * @return El atributo serie
     */
    public String getSerie() {
        return this.serie;
    }

    /**
     * Método para establecer el atributo serie
     * 
     * @param serie El valor del atributo serie a establecer.
     */
    public void setSerie(final String serie) {
        this.serie = serie;
    }

    /**
     * Método para obtener el atributo isin
     * 
     * @return El atributo isin
     */
    public String getIsin() {
        return this.isin;
    }

    /**
     * Método para establecer el atributo isin
     * 
     * @param isin El valor del atributo isin a establecer.
     */
    public void setIsin(final String isin) {
        this.isin = isin;
    }

    /**
     * Método para obtener el atributo fechaPago
     * 
     * @return El atributo fechaPago
     */
    public Date getFechaPago() {
        return this.fechaPago;
    }

    /**
     * Método para establecer el atributo fechaPago
     * 
     * @param fechaPago El valor del atributo fechaPago a establecer.
     */
    public void setFechaPago(final Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    /**
     * Método para obtener el atributo fechaCorte
     * 
     * @return El atributo fechaCorte
     */
    public Date getFechaCorte() {
        return this.fechaCorte;
    }

    /**
     * Método para establecer el atributo fechaCorte
     * 
     * @param fechaCorte El valor del atributo fechaCorte a establecer.
     */
    public void setFechaCorte(final Date fechaCorte) {
        this.fechaCorte = fechaCorte;
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

    /**
     * Método para obtener el atributo idCuentaNombradaBeneficiario
     * 
     * @return El atributo idCuentaNombradaBeneficiario
     */
    public Long getIdCuentaNombradaBeneficiario() {
        return this.idCuentaNombradaBeneficiario;
    }

    /**
     * Método para establecer el atributo idCuentaNombradaBeneficiario
     * 
     * @param idCuentaNombradaBeneficiario El valor del atributo idCuentaNombradaBeneficiario a
     *        establecer.
     */
    public void setIdCuentaNombradaBeneficiario(final Long idCuentaNombradaBeneficiario) {
        this.idCuentaNombradaBeneficiario = idCuentaNombradaBeneficiario;
    }

    /**
     * Método para obtener el atributo idCuentaNombradaCuenta
     * 
     * @return El atributo idCuentaNombradaCuenta
     */
    public Long getIdCuentaNombradaCuenta() {
        return this.idCuentaNombradaCuenta;
    }

    /**
     * Método para establecer el atributo idCuentaNombradaCuenta
     * 
     * @param idCuentaNombradaCuenta El valor del atributo idCuentaNombradaCuenta a establecer.
     */
    public void setIdCuentaNombradaCuenta(final Long idCuentaNombradaCuenta) {
        this.idCuentaNombradaCuenta = idCuentaNombradaCuenta;
    }

    /**
     * Método para obtener el atributo cuenta
     * 
     * @return El atributo cuenta
     */
    public String getCuenta() {
        return this.cuenta;
    }

    /**
     * Método para establecer el atributo cuenta
     * 
     * @param cuenta El valor del atributo cuenta a establecer.
     */
    public void setCuenta(final String cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * Método para obtener el atributo saldo
     * 
     * @return El atributo saldo
     */
    public Long getSaldo() {
        return this.saldo;
    }

    /**
     * Método para establecer el atributo saldo
     * 
     * @param saldo El valor del atributo saldo a establecer.
     */
    public void setSaldo(final Long saldo) {
        this.saldo = saldo;
    }

    /**
     * Método para obtener el atributo idDivisa
     * 
     * @return El atributo idDivisa
     */
    public Long getIdDivisa() {
        return this.idDivisa;
    }

    /**
     * Método para establecer el atributo idDivisa
     * 
     * @param idDivisa El valor del atributo idDivisa a establecer.
     */
    public void setIdDivisa(final Long idDivisa) {
        this.idDivisa = idDivisa;
    }

    /**
     * Método para obtener el atributo idBeneficiario
     * 
     * @return El atributo idBeneficiario
     */
    public Long getIdBeneficiario() {
        return this.idBeneficiario;
    }

    /**
     * Método para establecer el atributo idBeneficiario
     * 
     * @param idBeneficiario El valor del atributo idBeneficiario a establecer.
     */
    public void setIdBeneficiario(final Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    /**
     * Método para obtener el atributo adp
     * 
     * @return El atributo adp
     */
    public String getAdp() {
        return this.adp;
    }

    /**
     * Método para establecer el atributo adp
     * 
     * @param adp El valor del atributo adp a establecer.
     */
    public void setAdp(final String adp) {
        this.adp = adp;
    }

    /**
     * Método para obtener el atributo uoi
     * 
     * @return El atributo uoi
     */
    public String getUoi() {
        return this.uoi;
    }

    /**
     * Método para establecer el atributo uoi
     * 
     * @param uoi El valor del atributo uoi a establecer.
     */
    public void setUoi(final String uoi) {
        this.uoi = uoi;
    }

    /**
     * Método para obtener el atributo paisIncorporacion
     * 
     * @return El atributo paisIncorporacion
     */
    public String getPaisIncorporacion() {
        return this.paisIncorporacion;
    }

    /**
     * Método para establecer el atributo paisIncorporacion
     * 
     * @param paisIncorporacion El valor del atributo paisIncorporacion a establecer.
     */
    public void setPaisIncorporacion(final String paisIncorporacion) {
        this.paisIncorporacion = paisIncorporacion;
    }

    /**
     * Método para obtener el atributo idTipoBeneficiario
     * 
     * @return El atributo idTipoBeneficiario
     */
    public Long getIdTipoBeneficiario() {
        return this.idTipoBeneficiario;
    }

    /**
     * Método para establecer el atributo idTipoBeneficiario
     * 
     * @param idTipoBeneficiario El valor del atributo idTipoBeneficiario a establecer.
     */
    public void setIdTipoBeneficiario(final Long idTipoBeneficiario) {
        this.idTipoBeneficiario = idTipoBeneficiario;
    }

    /**
     * Método para obtener el atributo idDomicilioW
     * 
     * @return El atributo idDomicilioW
     */
    public Long getIdDomicilioW() {
        return this.idDomicilioW;
    }

    /**
     * Método para establecer el atributo idDomicilioW
     * 
     * @param idDomicilioW El valor del atributo idDomicilioW a establecer.
     */
    public void setIdDomicilioW(final Long idDomicilioW) {
        this.idDomicilioW = idDomicilioW;
    }

    /**
     * Método para obtener el atributo domicilio
     * 
     * @return El atributo domicilio
     */
    public Domicilio getDomicilio() {
        return this.domicilio;
    }

    /**
     * Método para establecer el atributo domicilio
     * 
     * @param domicilio El valor del atributo domicilio a establecer.
     */
    public void setDomicilio(final Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    /**
     * Método para obtener el atributo tipoFormato
     * 
     * @return El atributo tipoFormato
     */
    public String getTipoFormato() {
        return this.tipoFormato;
    }

    /**
     * Método para establecer el atributo tipoFormato
     * 
     * @param tipoFormato El valor del atributo tipoFormato a establecer.
     */
    public void setTipoFormato(final String tipoFormato) {
        this.tipoFormato = tipoFormato;
    }

    /**
     * Método para obtener el atributo proporcion
     * 
     * @return El atributo proporcion
     */
    public Double getProporcion() {
        return this.proporcion;
    }

    /**
     * Método para establecer el atributo proporcion
     * 
     * @param proporcion El valor del atributo proporcion a establecer.
     */
    public void setProporcion(final Double proporcion) {
        this.proporcion = proporcion;
    }

    /**
     * Método para obtener el atributo fee
     * 
     * @return El atributo fee
     */
    public Double getFee() {
        return this.fee;
    }

    /**
     * Método para establecer el atributo fee
     * 
     * @param fee El valor del atributo fee a establecer.
     */
    public void setFee(final Double fee) {
        this.fee = fee;
    }

    /**
     * Método para obtener el atributo porcentajeMav
     * 
     * @return El atributo porcentajeMav
     */
    public Double getPorcentajeMav() {
        return this.porcentajeMav;
    }

    /**
     * Método para establecer el atributo porcentajeMav
     * 
     * @param porcentajeMav El valor del atributo porcentajeMav a establecer.
     */
    public void setPorcentajeMav(final Double porcentajeMav) {
        this.porcentajeMav = porcentajeMav;
    }

    /**
     * Método para obtener el atributo porcentajeBen
     * 
     * @return El atributo porcentajeBen
     */
    public Long getPorcentajeBen() {
        return this.porcentajeBen;
    }

    /**
     * Método para establecer el atributo porcentajeBen
     * 
     * @param porcentajeBen El valor del atributo porcentajeBen a establecer.
     */
    public void setPorcentajeBen(final Long porcentajeBen) {
        this.porcentajeBen = porcentajeBen;
    }

    /**
     * Método para obtener el atributo porcentajeHBDefault
     * 
     * @return El atributo porcentajeHBDefault
     */
    public Double getPorcentajeHBDefault() {
        return this.porcentajeHBDefault;
    }

    /**
     * Método para establecer el atributo porcentajeHBDefault
     * 
     * @param porcentajeHBDefault El valor del atributo porcentajeHBDefault a establecer.
     */
    public void setPorcentajeHBDefault(final Double porcentajeHBDefault) {
        this.porcentajeHBDefault = porcentajeHBDefault;
    }

    /**
     * Método para obtener el atributo porcentajeRetencionCTB
     * 
     * @return El atributo porcentajeRetencionCTB
     */
    public Long getPorcentajeRetencionCTB() {
        return this.porcentajeRetencionCTB;
    }

    /**
     * Método para establecer el atributo porcentajeRetencionCTB
     * 
     * @param porcentajeRetencionCTB El valor del atributo porcentajeRetencionCTB a establecer.
     */
    public void setPorcentajeRetencionCTB(final Long porcentajeRetencionCTB) {
        this.porcentajeRetencionCTB = porcentajeRetencionCTB;
    }

    /**
     * Método para obtener el atributo asignacion
     * 
     * @return El atributo asignacion
     */
    public Long getAsignacion() {
        return this.asignacion;
    }

    /**
     * Método para establecer el atributo asignacion
     * 
     * @param asignacion El valor del atributo asignacion a establecer.
     */
    public void setAsignacion(final Long asignacion) {
        this.asignacion = asignacion;
    }

    /**
     * Método para obtener el atributo idTipoDerecho
     * 
     * @return El atributo idTipoDerecho
     */
    public Long getIdTipoDerecho() {
        return this.idTipoDerecho;
    }

    /**
     * Método para establecer el atributo idTipoDerecho
     * 
     * @param idTipoDerecho El valor del atributo idTipoDerecho a establecer.
     */
    public void setIdTipoDerecho(final Long idTipoDerecho) {
        this.idTipoDerecho = idTipoDerecho;
    }

    /**
     * Método para obtener el atributo montoBruto
     * 
     * @return El atributo montoBruto
     */
    public Double getMontoBruto() {
        return this.montoBruto;
    }

    /**
     * Método para establecer el atributo montoBruto
     * 
     * @param montoBruto El valor del atributo montoBruto a establecer.
     */
    public void setMontoBruto(final Double montoBruto) {
        this.montoBruto = montoBruto;
    }

    /**
     * Método para obtener el atributo impuestoRetenido
     * 
     * @return El atributo impuestoRetenido
     */
    public Double getImpuestoRetenido() {
        return this.impuestoRetenido;
    }

    /**
     * Método para establecer el atributo impuestoRetenido
     * 
     * @param impuestoRetenido El valor del atributo impuestoRetenido a establecer.
     */
    public void setImpuestoRetenido(final Double impuestoRetenido) {
        this.impuestoRetenido = impuestoRetenido;
    }

    /**
     * Método para obtener el atributo montoNeto
     * 
     * @return El atributo montoNeto
     */
    public Double getMontoNeto() {
        return this.montoNeto;
    }

    /**
     * Método para establecer el atributo montoNeto
     * 
     * @param montoNeto El valor del atributo montoNeto a establecer.
     */
    public void setMontoNeto(final Double montoNeto) {
        this.montoNeto = montoNeto;
    }

    /**
     * Método para obtener el atributo idEstatusDerecho
     * 
     * @return El atributo idEstatusDerecho
     */
    public Long getIdEstatusDerecho() {
        return this.idEstatusDerecho;
    }

    /**
     * Método para establecer el atributo idEstatusDerecho
     * 
     * @param idEstatusDerecho El valor del atributo idEstatusDerecho a establecer.
     */
    public void setIdEstatusDerecho(final Long idEstatusDerecho) {
        this.idEstatusDerecho = idEstatusDerecho;
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
     * Método para obtener el atributo descEstatusDerecho
     * 
     * @return El atributo descEstatusDerecho
     */
    public String getDescEstatusDerecho() {
        return this.descEstatusDerecho;
    }

    /**
     * Método para establecer el atributo descEstatusDerecho
     * 
     * @param descEstatusDerecho El valor del atributo descEstatusDerecho a establecer.
     */
    public void setDescEstatusDerecho(final String descEstatusDerecho) {
        this.descEstatusDerecho = descEstatusDerecho;
    }

    /**
     * Método para obtener el atributo rfc
     * 
     * @return El atributo rfc
     */
    public String getRfc() {
        return this.rfc;
    }

    /**
     * Método para establecer el atributo rfc
     * 
     * @param rfc El valor del atributo rfc a establecer.
     */
    public void setRfc(final String rfc) {
        this.rfc = rfc;
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
     * Método para obtener el atributo tipoBeneficiario
     * 
     * @return El atributo tipoBeneficiario
     */
    public TipoBeneficiario getTipoBeneficiario() {
        return this.tipoBeneficiario;
    }

    /**
     * Método para establecer el atributo tipoBeneficiario
     * 
     * @param tipoBeneficiario El valor del atributo tipoBeneficiario a establecer.
     */
    public void setTipoBeneficiario(final TipoBeneficiario tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
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
     * Método para obtener el atributo beneficiario
     * 
     * @return El atributo beneficiario
     */
    public String getBeneficiario() {
        return this.beneficiario;
    }

    /**
     * Método para establecer el atributo beneficiario
     * 
     * @param beneficiario El valor del atributo beneficiario a establecer.
     */
    public void setBeneficiario(final String beneficiario) {
        this.beneficiario = beneficiario;
    }
    
    /**
	 * @return the fechaFormatoW
	 */
	public Date getFechaFormatoW() {
		return fechaFormatoW;
	}

	/**
	 * @param fechaFormatoW the fechaFormatoW to set
	 */
	public void setFechaFormatoW(Date fechaFormatoW) {
		this.fechaFormatoW = fechaFormatoW;
	}

    /**
     * Método para obtener el atributo estadoW
     * 
     * @return El atributo estadoW
     */
    public String getEstadoW() {
        return this.estadoW;
    }

    /**
     * Método para establecer el atributo estadoW
     * 
     * @param estadoW El valor del atributo estadoW a establecer.
     */
    public void setEstadoW(final String estadoW) {
        this.estadoW = estadoW;
    }

    /**
     * Método para obtener el atributo giin
     * 
     * @return El atributo giin
     */
    public String getGiin() {
        return this.giin;
    }

    /**
     * Método para establecer el atributo giin
     * 
     * @param giin El valor del atributo giin a establecer.
     */
    public void setGiin(final String giin) {
        this.giin = giin;
    }

    /**
     * Método para obtener el atributo referenceNumber
     * 
     * @return El atributo referenceNumber
     */
    public String getReferenceNumber() {
        return this.referenceNumber;
    }

    /**
     * Método para establecer el atributo referenceNumber
     * 
     * @param referenceNumber El valor del atributo referenceNumber a establecer.
     */
    public void setReferenceNumber(final String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Método para obtener el atributo exempPayeeCode
     * 
     * @return El atributo exempPayeeCode
     */
    public String getExempPayeeCode() {
        return this.exempPayeeCode;
    }

    /**
     * Método para establecer el atributo exempPayeeCode
     * 
     * @param exempPayeeCode El valor del atributo exempPayeeCode a establecer.
     */
    public void setExempPayeeCode(final String exempPayeeCode) {
        this.exempPayeeCode = exempPayeeCode;
    }

    /**
     * Método para obtener el atributo exemptFatcaRepCode
     * 
     * @return El atributo exemptFatcaRepCode
     */
    public String getExemptFatcaRepCode() {
        return this.exemptFatcaRepCode;
    }

    /**
     * Método para establecer el atributo exemptFatcaRepCode
     * 
     * @param exemptFatcaRepCode El valor del atributo exemptFatcaRepCode a establecer.
     */
    public void setExemptFatcaRepCode(final String exemptFatcaRepCode) {
        this.exemptFatcaRepCode = exemptFatcaRepCode;
    }

    /**
     * Método para obtener el atributo fechaNacimientoFW
     * 
     * @return El atributo fechaNacimientoFW
     */
    public Date getFechaNacimientoFW() {
        return this.fechaNacimientoFW;
    }

    /**
     * Método para establecer el atributo fechaNacimientoFW
     * 
     * @param fechaNacimientoFW El valor del atributo fechaNacimientoFW a establecer.
     */
    public void setFechaNacimientoFW(final Date fechaNacimientoFW) {
        this.fechaNacimientoFW = fechaNacimientoFW;
    }

    /**
     * Método para obtener el atributo ssnEin
     * 
     * @return El atributo ssnEin
     */
    public String getSsnEin() {
        return this.ssnEin;
    }

    /**
     * Método para establecer el atributo ssnEin
     * 
     * @param ssnEin El valor del atributo ssnEin a establecer.
     */
    public void setSsnEin(final String ssnEin) {
        this.ssnEin = ssnEin;
    }

    /**
     * Método para obtener el atributo tiin
     * 
     * @return El atributo tiin
     */
    public String getTiin() {
        return this.tiin;
    }

    /**
     * Método para establecer el atributo tiin
     * 
     * @param tiin El valor del atributo tiin a establecer.
     */
    public void setTiin(final String tiin) {
        this.tiin = tiin;
    }

    /** @return this.tipoDerecho */
    public String getTipoDerecho() {
        return this.tipoDerecho;
    }

    /** @param tipoDerecho to be set in this.tipoDerecho */
    public void setTipoDerecho(final String tipoDerecho) {
        this.tipoDerecho = tipoDerecho;
    }

    @Transient
    public String getFormatoPantalla() {
        String tipoFormatoPantalla = null;

        if (BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(this.tipoFormato)) {
            tipoFormatoPantalla = BeneficiariosConstantes.FORMATO_W8_IMY2016;
        } else {
            tipoFormatoPantalla = this.tipoFormato;
        }

        return tipoFormatoPantalla;
    }
    
	/**
	 * @return the foreigntiin
	 */
	public String getForeigntiin() {
		return foreigntiin;
	}

	/**
	 * @param foreigntiin the foreigntiin to set
	 */
	public void setForeigntiin(String foreigntiin) {
		this.foreigntiin = foreigntiin;
	}

}
