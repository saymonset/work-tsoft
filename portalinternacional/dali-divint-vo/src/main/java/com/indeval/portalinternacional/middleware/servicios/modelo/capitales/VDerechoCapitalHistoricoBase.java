package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class VDerechoCapitalHistoricoBase implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID", unique = true, nullable = false, insertable = false, updatable =false)
    private Long id; // ID es el rownum

    @Column(name = "ID_DERECHO_CAPITAL", unique = false, nullable = true, insertable = false, updatable =false)
    private Long idDerechoCapital; // ID_DERECHO_CAPITAL

    @Column(name = "CLAVE_TIPO_VALOR", unique = false, nullable = true, insertable = false, updatable =false)
    private String tipoValor; // CLAVE_TIPO_VALOR

    @Column(name = "CLAVE_PIZARRA", unique = false, nullable = true, insertable = false, updatable =false)
    private String emisora; // CLAVE_PIZARRA

    @Column(name = "SERIE", unique = false, nullable = true, insertable = false, updatable =false)
    private String serie; // SERIE

    @Column(name = "ISIN", unique = false, nullable = true, insertable = false, updatable =false)
    private String isin; // ISIN

    @Column(name = "FECHA_PAGO_DERECHO_CAPITAL", unique = false, nullable = true, insertable = false, updatable =false)
    private Date fechaPago; // FECHA_PAGO_DERECHO_CAPITAL

    @Column(name = "FECHA_CORTE", unique = false, nullable = true, insertable = false, updatable =false)
    private Date fechaCorte; // FECHA_CORTE

    @Column(name = "ID_CATBIC", unique = false, nullable = true, insertable = false, updatable =false)
    private Long idCatBic; // ID_CATBIC

    @Column(name = "DETALLE_CUSTODIO", unique = false, nullable = true, insertable = false, updatable =false)
    private String custodio; // DETALLE_CUSTODIO

    @Column(name = "ID_FOLIO", unique = false, nullable = true, insertable = false, updatable =false)
    private String idFolio; // ID_FOLIO

    @Column(name = "ID_EMISION", unique = false, nullable = true, insertable = false, updatable =false)
    private Long idEmision; // ID_EMISION

    @Column(name = "ID_CUENTA_NOM_BEN", unique = false, nullable = true, insertable = false, updatable =false)
    private Long idCuentaNombradaBeneficiario; // ID_CUENTA_NOM_BEN

    @Column(name = "ID_CUENTA_NOM_CTA", unique = false, nullable = true, insertable = false, updatable =false)
    private Long idCuentaNombradaCuenta; // ID_CUENTA_NOM_CTA

    @Column(name = "CUENTA", unique = false, nullable = true, insertable = false, updatable =false)
    private String cuenta; // CUENTA

    @Column(name = "SALDO", unique = false, nullable = true, insertable = false, updatable =false)
    private Long saldo; // SALDO

    @Column(name = "ID_DIVISA", unique = false, nullable = true, insertable = false, updatable =false)
    private Long idDivisa; // ID_DIVISA

    @Column(name = "ID_BENEFICIARIO", unique = false, nullable = true, insertable = false, updatable =false)
    private Long idBeneficiario; // ID_BENEFICIARIO

    @Column(name = "ADP", unique = false, nullable = true, insertable = false, updatable =false)
    private String adp; // ADP
    
   @Transient
    private String rfc; // rfc
   
   @Transient
   private Double montoFee; // rfc

    @Column(name = "NOMBRE_BENEF", unique = false, nullable = true, insertable = false, updatable =false)
    private String nombre; // NOMBRE_BENEF

    @Column(name = "APELLIDO_PATERNO_BENEF", unique = false, nullable = true, insertable = false, updatable =false)
    private String apellidoPaterno; // APELLIDO_PATERNO_BENEF

    @Column(name = "APELLIDO_MATERNO_BENEF", unique = false, nullable = true, insertable = false, updatable =false)
    private String apellidoMaterno; // APELLIDO_MATERNO_BENEF

    @Column(name = "RAZON_SOCIAL_BENEF", unique = false, nullable = true, insertable = false, updatable =false)
    private String razonSocial; // RAZON_SOCIAL_BENEF

    @Column(name = "PAIS_INCORPORACION", unique = false, nullable = true, insertable = false, updatable =false)
    private String pais; // PAIS_INCORPORACION

    @Column(name = "ID_TIPO_BENEFICIARIO", unique = false, nullable = true, insertable = false, updatable =false)
    private Long idTipoBeneficiario; // ID_TIPO_BENEFICIARIO

    @Column(name = "DOMICILIO", unique = false, nullable = true, insertable = false, updatable =false)
    private Long idDomicilio; // DOMICILIO

    @Column(name = "TIPO_FORMATO", unique = false, nullable = true, insertable = false, updatable =false)
    private String tipoFormato; // TIPO_FORMATO

    @Column(name = "PROPORCION", unique = false, nullable = true, insertable = false, updatable =false)
    private Double proporcion; // PROPORCION

    @Column(name = "FEE", unique = false, nullable = true, insertable = false, updatable =false)
    private Double fee; // FEE

    @Column(name = "PORCENTAJE_MAV", unique = false, nullable = true, insertable = false, updatable =false)
    private Double porcentajeRetencionMav; // PORCENTAJE_MAV

    @Column(name = "PORCENTAJE_BEN", unique = false, nullable = true, insertable = false, updatable =false)
    private Long porcentajeRetencionBeneficiario; // PORCENTAJE_BEN

    @Column(name = "PORCENTAJE_HB_DEFAULT", unique = false, nullable = true, insertable = false, updatable =false)
    private Double porcentajeRetencionHorarioBenef; // PORCENTAJE_HB_DEFAULT

    @Column(name = "PORCENTAJE_RETENCION_CTB", unique = false, nullable = true, insertable = false, updatable =false)
    private Long porcentajeRetencionCuenta; // PORCENTAJE_RETENCION_CTB

    @Column(name = "ASIGNACION", unique = false, nullable = true, insertable = false, updatable =false)
    private Long asignacion; // ASIGNACION

    @Column(name = "ID_TIPO_DERECHO", unique = false, nullable = true, insertable = false, updatable =false)
    private Long idTipoDerecho; // ID_TIPO_DERECHO

    @Transient
    private Double MontoBruto; // MONTO_BRUTO

    @Transient
    private Double impuestoRetenido; // IMPUESTO_RETENIDO

    @Transient
    private Double montoNeto; // MONTO_NETO

    @Column(name = "id_estatus_derecho", unique = false, nullable = true, insertable = false, updatable =false)
    private Long idEstatusDerecho; // id_estatus_derecho
    
    @Column(name = "id_estatus_derecho_mav", unique = false, nullable = true, insertable = false, updatable =false)
    private Long idEstatusDerechoMav; // id_estatus_derecho

    @Column(name = "uoi", unique = false, nullable = true, insertable = false, updatable =false)
    private String uoi; // uoi

    @Column(name = "clave_alfabetica", unique = false, nullable = true, insertable = false, updatable =false)
    private String divisa; // clave_alfabetica

    @Column(name = "desc_estatus_derecho", unique = false, nullable = true, insertable = false, updatable =false)
    private String estatusDerecho; // desc_estatus_derecho

    @Transient
    private Double porcentajeRetencionReal; // PORCENTAJE_RETENCION_REAL
    
    @Column(name = "estatus_beneficiario", unique = false, nullable = true, insertable = false, updatable =false)
    private String estatusBeneficiario; // desc_estatus_benef
    
    @Column(name = "FECHA_FORMATO", unique = false, nullable = true, insertable = false, updatable =false)
    private Date fechaFormato; // FECHA_Formato

    @Transient
    private String tiin; 	
    @Transient
    private String ssn;
    @Transient
    private String foreigntiin;
    @Transient
    private String referenceNumber;
    @Transient
    private String giin;
    @Transient
    private String exemptPayeeCode;
    @Transient
    private Date fechaNacimiento;
    @Transient
    private String exemptionFromFatcaRepCode;
    
    @Column(name = "DESC_TIPO_DERECHO", unique = false, nullable = true, insertable = false, updatable =false)
    private String  tipoDerecho; // Desc_tipo_derecho}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the idDerechoCapital
	 */
	public Long getIdDerechoCapital() {
		return idDerechoCapital;
	}

	/**
	 * @param idDerechoCapital the idDerechoCapital to set
	 */
	public void setIdDerechoCapital(Long idDerechoCapital) {
		this.idDerechoCapital = idDerechoCapital;
	}

	/**
	 * @return the tipoValor
	 */
	public String getTipoValor() {
		return tipoValor;
	}

	/**
	 * @param tipoValor the tipoValor to set
	 */
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return the fechaPago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}

	/**
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * @return the fechaCorte
	 */
	public Date getFechaCorte() {
		return fechaCorte;
	}

	/**
	 * @param fechaCorte the fechaCorte to set
	 */
	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	/**
	 * @return the idCatBic
	 */
	public Long getIdCatBic() {
		return idCatBic;
	}

	/**
	 * @param idCatBic the idCatBic to set
	 */
	public void setIdCatBic(Long idCatBic) {
		this.idCatBic = idCatBic;
	}

	/**
	 * @return the custodio
	 */
	public String getCustodio() {
		return custodio;
	}

	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}

	/**
	 * @return the idFolio
	 */
	public String getIdFolio() {
		return idFolio;
	}

	/**
	 * @param idFolio the idFolio to set
	 */
	public void setIdFolio(String idFolio) {
		this.idFolio = idFolio;
	}

	/**
	 * @return the idEmision
	 */
	public Long getIdEmision() {
		return idEmision;
	}

	/**
	 * @param idEmision the idEmision to set
	 */
	public void setIdEmision(Long idEmision) {
		this.idEmision = idEmision;
	}

	/**
	 * @return the idCuentaNombradaBeneficiario
	 */
	public Long getIdCuentaNombradaBeneficiario() {
		return idCuentaNombradaBeneficiario;
	}

	/**
	 * @param idCuentaNombradaBeneficiario the idCuentaNombradaBeneficiario to set
	 */
	public void setIdCuentaNombradaBeneficiario(Long idCuentaNombradaBeneficiario) {
		this.idCuentaNombradaBeneficiario = idCuentaNombradaBeneficiario;
	}

	/**
	 * @return the idCuentaNombradaCuenta
	 */
	public Long getIdCuentaNombradaCuenta() {
		return idCuentaNombradaCuenta;
	}

	/**
	 * @param idCuentaNombradaCuenta the idCuentaNombradaCuenta to set
	 */
	public void setIdCuentaNombradaCuenta(Long idCuentaNombradaCuenta) {
		this.idCuentaNombradaCuenta = idCuentaNombradaCuenta;
	}

	/**
	 * @return the cuenta
	 */
	public String getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return the saldo
	 */
	public Long getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(Long saldo) {
		this.saldo = saldo;
	}

	/**
	 * @return the idDivisa
	 */
	public Long getIdDivisa() {
		return idDivisa;
	}

	/**
	 * @param idDivisa the idDivisa to set
	 */
	public void setIdDivisa(Long idDivisa) {
		this.idDivisa = idDivisa;
	}

	/**
	 * @return the idBeneficiario
	 */
	public Long getIdBeneficiario() {
		return idBeneficiario;
	}

	/**
	 * @param idBeneficiario the idBeneficiario to set
	 */
	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	/**
	 * @return the adp
	 */
	public String getAdp() {
		return adp;
	}

	/**
	 * @param adp the adp to set
	 */
	public void setAdp(String adp) {
		this.adp = adp;
	}

	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * @return the montoFee
	 */
	public Double getMontoFee() {
		return montoFee;
	}

	/**
	 * @param montoFee the montoFee to set
	 */
	public void setMontoFee(Double montoFee) {
		this.montoFee = montoFee;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * @param apellidoPaterno the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * @param apellidoMaterno the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
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
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * @return the idTipoBeneficiario
	 */
	public Long getIdTipoBeneficiario() {
		return idTipoBeneficiario;
	}

	/**
	 * @param idTipoBeneficiario the idTipoBeneficiario to set
	 */
	public void setIdTipoBeneficiario(Long idTipoBeneficiario) {
		this.idTipoBeneficiario = idTipoBeneficiario;
	}

	/**
	 * @return the idDomicilio
	 */
	public Long getIdDomicilio() {
		return idDomicilio;
	}

	/**
	 * @param idDomicilio the idDomicilio to set
	 */
	public void setIdDomicilio(Long idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	/**
	 * @return the tipoFormato
	 */
	public String getTipoFormato() {
		return tipoFormato;
	}

	/**
	 * @param tipoFormato the tipoFormato to set
	 */
	public void setTipoFormato(String tipoFormato) {
		this.tipoFormato = tipoFormato;
	}

	/**
	 * @return the proporcion
	 */
	public Double getProporcion() {
		return proporcion;
	}

	/**
	 * @param proporcion the proporcion to set
	 */
	public void setProporcion(Double proporcion) {
		this.proporcion = proporcion;
	}

	/**
	 * @return the fee
	 */
	public Double getFee() {
		return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(Double fee) {
		this.fee = fee;
	}

	/**
	 * @return the porcentajeRetencionMav
	 */
	public Double getPorcentajeRetencionMav() {
		return porcentajeRetencionMav;
	}

	/**
	 * @param porcentajeRetencionMav the porcentajeRetencionMav to set
	 */
	public void setPorcentajeRetencionMav(Double porcentajeRetencionMav) {
		this.porcentajeRetencionMav = porcentajeRetencionMav;
	}

	/**
	 * @return the porcentajeRetencionBeneficiario
	 */
	public Long getPorcentajeRetencionBeneficiario() {
		return porcentajeRetencionBeneficiario;
	}

	/**
	 * @param porcentajeRetencionBeneficiario the porcentajeRetencionBeneficiario to set
	 */
	public void setPorcentajeRetencionBeneficiario(Long porcentajeRetencionBeneficiario) {
		this.porcentajeRetencionBeneficiario = porcentajeRetencionBeneficiario;
	}

	/**
	 * @return the porcentajeRetencionHorarioBenef
	 */
	public Double getPorcentajeRetencionHorarioBenef() {
		return porcentajeRetencionHorarioBenef;
	}

	/**
	 * @param porcentajeRetencionHorarioBenef the porcentajeRetencionHorarioBenef to set
	 */
	public void setPorcentajeRetencionHorarioBenef(Double porcentajeRetencionHorarioBenef) {
		this.porcentajeRetencionHorarioBenef = porcentajeRetencionHorarioBenef;
	}

	/**
	 * @return the porcentajeRetencionCuenta
	 */
	public Long getPorcentajeRetencionCuenta() {
		return porcentajeRetencionCuenta;
	}

	/**
	 * @param porcentajeRetencionCuenta the porcentajeRetencionCuenta to set
	 */
	public void setPorcentajeRetencionCuenta(Long porcentajeRetencionCuenta) {
		this.porcentajeRetencionCuenta = porcentajeRetencionCuenta;
	}

	/**
	 * @return the asignacion
	 */
	public Long getAsignacion() {
		return asignacion;
	}

	/**
	 * @param asignacion the asignacion to set
	 */
	public void setAsignacion(Long asignacion) {
		this.asignacion = asignacion;
	}

	/**
	 * @return the idTipoDerecho
	 */
	public Long getIdTipoDerecho() {
		return idTipoDerecho;
	}

	/**
	 * @param idTipoDerecho the idTipoDerecho to set
	 */
	public void setIdTipoDerecho(Long idTipoDerecho) {
		this.idTipoDerecho = idTipoDerecho;
	}

	/**
	 * @return the montoBruto
	 */
	public Double getMontoBruto() {
		return MontoBruto;
	}

	/**
	 * @param montoBruto the montoBruto to set
	 */
	public void setMontoBruto(Double montoBruto) {
		MontoBruto = montoBruto;
	}

	/**
	 * @return the impuestoRetenido
	 */
	public Double getImpuestoRetenido() {
		return impuestoRetenido;
	}

	/**
	 * @param impuestoRetenido the impuestoRetenido to set
	 */
	public void setImpuestoRetenido(Double impuestoRetenido) {
		this.impuestoRetenido = impuestoRetenido;
	}

	/**
	 * @return the montoNeto
	 */
	public Double getMontoNeto() {
		return montoNeto;
	}

	/**
	 * @param montoNeto the montoNeto to set
	 */
	public void setMontoNeto(Double montoNeto) {
		this.montoNeto = montoNeto;
	}

	/**
	 * @return the idEstatusDerecho
	 */
	public Long getIdEstatusDerecho() {
		return idEstatusDerecho;
	}

	/**
	 * @param idEstatusDerecho the idEstatusDerecho to set
	 */
	public void setIdEstatusDerecho(Long idEstatusDerecho) {
		this.idEstatusDerecho = idEstatusDerecho;
	}

	/**
	 * @return the idEstatusDerechoMav
	 */
	public Long getIdEstatusDerechoMav() {
		return idEstatusDerechoMav;
	}

	/**
	 * @param idEstatusDerechoMav the idEstatusDerechoMav to set
	 */
	public void setIdEstatusDerechoMav(Long idEstatusDerechoMav) {
		this.idEstatusDerechoMav = idEstatusDerechoMav;
	}

	/**
	 * @return the uoi
	 */
	public String getUoi() {
		return uoi;
	}

	/**
	 * @param uoi the uoi to set
	 */
	public void setUoi(String uoi) {
		this.uoi = uoi;
	}

	/**
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return the estatusDerecho
	 */
	public String getEstatusDerecho() {
		return estatusDerecho;
	}

	/**
	 * @param estatusDerecho the estatusDerecho to set
	 */
	public void setEstatusDerecho(String estatusDerecho) {
		this.estatusDerecho = estatusDerecho;
	}

	/**
	 * @return the porcentajeRetencionReal
	 */
	public Double getPorcentajeRetencionReal() {
		return porcentajeRetencionReal;
	}

	/**
	 * @param porcentajeRetencionReal the porcentajeRetencionReal to set
	 */
	public void setPorcentajeRetencionReal(Double porcentajeRetencionReal) {
		this.porcentajeRetencionReal = porcentajeRetencionReal;
	}

	/**
	 * @return the estatusBeneficiario
	 */
	public String getEstatusBeneficiario() {
		return estatusBeneficiario;
	}

	/**
	 * @param estatusBeneficiario the estatusBeneficiario to set
	 */
	public void setEstatusBeneficiario(String estatusBeneficiario) {
		this.estatusBeneficiario = estatusBeneficiario;
	}

	/**
	 * @return the fechaFormato
	 */
	public Date getFechaFormato() {
		return fechaFormato;
	}

	/**
	 * @param fechaFormato the fechaFormato to set
	 */
	public void setFechaFormato(Date fechaFormato) {
		this.fechaFormato = fechaFormato;
	}

	/**
	 * @return the tiin
	 */
	public String getTiin() {
		return tiin;
	}

	/**
	 * @param tiin the tiin to set
	 */
	public void setTiin(String tiin) {
		this.tiin = tiin;
	}

	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
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

	/**
	 * @return the referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * @param referenceNumber the referenceNumber to set
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	/**
	 * @return the giin
	 */
	public String getGiin() {
		return giin;
	}

	/**
	 * @param giin the giin to set
	 */
	public void setGiin(String giin) {
		this.giin = giin;
	}

	/**
	 * @return the exemptPayeeCode
	 */
	public String getExemptPayeeCode() {
		return exemptPayeeCode;
	}

	/**
	 * @param exemptPayeeCode the exemptPayeeCode to set
	 */
	public void setExemptPayeeCode(String exemptPayeeCode) {
		this.exemptPayeeCode = exemptPayeeCode;
	}

	/**
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * @return the exemptionFromFatcaRepCode
	 */
	public String getExemptionFromFatcaRepCode() {
		return exemptionFromFatcaRepCode;
	}

	/**
	 * @param exemptionFromFatcaRepCode the exemptionFromFatcaRepCode to set
	 */
	public void setExemptionFromFatcaRepCode(String exemptionFromFatcaRepCode) {
		this.exemptionFromFatcaRepCode = exemptionFromFatcaRepCode;
	}

	/**
	 * @return the tipoDerecho
	 */
	public String getTipoDerecho() {
		return tipoDerecho;
	}

	/**
	 * @param tipoDerecho the tipoDerecho to set
	 */
	public void setTipoDerecho(String tipoDerecho) {
		this.tipoDerecho = tipoDerecho;
	}

}
