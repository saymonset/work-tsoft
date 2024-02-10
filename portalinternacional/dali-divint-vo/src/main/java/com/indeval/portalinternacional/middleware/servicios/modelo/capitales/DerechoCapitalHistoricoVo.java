package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;
import java.util.Date;

public class DerechoCapitalHistoricoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Long idDerechoCapital; // ID_DERECHO_CAPITAL
    private String claveTipoValor; // CLAVE_TIPO_VALOR
    private String clavePizarra; // CLAVE_PIZARRA
    private String serie; // SERIE
    private String isin; // ISIN
    private Date fechaPagoDerechoCapital; // FECHA_PAGO_DERECHO_CAPITAL
    private Date fechaCorte; // FECHA_CORTE
    private Long idCatBic; // ID_CATBIC
    private String detalleCustodio; // DETALLE_CUSTODIO
    private String idFolio; //ID_FOLIO
    private Long idEmision; //ID_EMISION
    private Long idCuentaNombradaBeneficiario; // ID_CUENTA_NOM_BEN
    private Long idCuentaNombradaCuenta; // ID_CUENTA_NOM_CTA
    private String cuenta; // CUENTA
    private Long saldo; // SALDO
    private Long idDivisa; // ID_DIVISA
    private Long idBeneficiario; // ID_BENEFICIARIO
    private String adp; // ADP
    private String uoi; // uoi
    private String nombreBenef;
    private String apellidoPaternoBenef;
    private String apellidoMaternoBenef;
    private String razonSocialBenef;
    private String paisIncorporacion; // PAIS_INCORPORACION
    private Long idTipoBeneficiario; // ID_TIPO_BENEFICIARIO
    private Long domicilio;
    private String tipoFormato; // TIPO_FORMATO
    private Double proporcion; // PROPORCION
    private Double fee; // FEE
    private Double porcentajeMav; // PORCENTAJE_MAV
    private Long porcentajeBen; // PORCENTAJE_BEN
    private Double porcentajeHBDefault; // PORCENTAJE_HB_DEFAULT
    private Long porcentajeRetencionCTB; // PORCENTAJE_RETENCION_CTB
    private Long asignacion; // ASIGNACION
    private Long idTipoDerecho; // ID_TIPO_DERECHO
    private Long idEstatusDerechoMav; //ID_ESTATUS_MAV
    private Long idEstatusDerecho; // id_estatus_derecho
    private String claveAlfabetica; //CLAVE_ALFABETICA
    private String descEstatusDerecho; // desc_estatus_derecho
    private Date fechaFormato;
    private String estatusBeneficiario; // ESTATUS_BENEFICIARIO
    private String descTipoDerecho; // DESC_TIPO_DERECHO
    private Long id; //ROWNUM AS ID
    
    private Double porcentajeRetencionReal; // PORCENTAJE_RETENCION_REAL
    private String tiin; 	
    private String ssn;
    private String foreigntiin;
    private String referenceNumber;
    private String giin;
    private String exemptPayeeCode;
    private Date fechaNacimiento;
    private String exemptionFromFatcaRepCode;
    private String rfc; // rfc
    private Double montoFee; // rfc
    private Double impuestoRetenido; // IMPUESTO_RETENIDO
    private Double MontoBruto; // MONTO_BRUTO
    private Double montoNeto; // MONTO_NETO
   
    
	/**
	 * 
	 */
	public DerechoCapitalHistoricoVo() {
		
	}


	/**
	 * @param idDerechoCapital
	 * @param claveTipoValor
	 * @param clavePizarra
	 * @param serie
	 * @param isin
	 * @param fechaPagoDerechoCapital
	 * @param fechaCorte
	 * @param idCatBic
	 * @param detalleCustodio
	 * @param idFolio
	 * @param idEmision
	 * @param idCuentaNombradaBeneficiario
	 * @param idCuentaNombradaCuenta
	 * @param cuenta
	 * @param saldo
	 * @param idDivisa
	 * @param idBeneficiario
	 * @param adp
	 * @param uoi
	 * @param nombreBenef
	 * @param apellidoPaternoBenef
	 * @param apellidoMaternoBenef
	 * @param razonSocialBenef
	 * @param paisIncorporacion
	 * @param idTipoBeneficiario
	 * @param domicilio
	 * @param tipoFormato
	 * @param proporcion
	 * @param fee
	 * @param porcentajeMav
	 * @param porcentajeBen
	 * @param porcentajeHBDefault
	 * @param porcentajeRetencionCTB
	 * @param asignacion
	 * @param idTipoDerecho
	 * @param idEstatusDerechoMav
	 * @param idEstatusDerecho
	 * @param claveAlfabetica
	 * @param descEstatusDerecho
	 * @param fechaFormato
	 * @param estatusBeneficiario
	 * @param descTipoDerecho
	 * @param id
	 */
	public DerechoCapitalHistoricoVo(Long idDerechoCapital, String claveTipoValor, String clavePizarra, String serie,
			String isin, Date fechaPagoDerechoCapital, Date fechaCorte, Long idCatBic, String detalleCustodio,
			String idFolio, Long idEmision, Long idCuentaNombradaBeneficiario, Long idCuentaNombradaCuenta, String cuenta,
			Long saldo, Long idDivisa, Long idBeneficiario, String adp, String uoi, String nombreBenef,
			String apellidoPaternoBenef, String apellidoMaternoBenef, String razonSocialBenef, String paisIncorporacion,
			Long idTipoBeneficiario, Long domicilio, String tipoFormato, Double proporcion, Double fee,
			Double porcentajeMav, Long porcentajeBen, Double porcentajeHBDefault, Long porcentajeRetencionCTB,
			Long asignacion, Long idTipoDerecho, Long idEstatusDerechoMav, Long idEstatusDerecho,
			String claveAlfabetica, String descEstatusDerecho, Date fechaFormato, String estatusBeneficiario,
			String descTipoDerecho, Long id) {
		this.idDerechoCapital = idDerechoCapital;
		this.claveTipoValor = claveTipoValor;
		this.clavePizarra = clavePizarra;
		this.serie = serie;
		this.isin = isin;
		this.fechaPagoDerechoCapital = fechaPagoDerechoCapital;
		this.fechaCorte = fechaCorte;
		this.idCatBic = idCatBic;
		this.detalleCustodio = detalleCustodio;
		this.idFolio = idFolio;
		this.idEmision = idEmision;
		this.idCuentaNombradaBeneficiario = idCuentaNombradaBeneficiario;
		this.idCuentaNombradaCuenta = idCuentaNombradaCuenta;
		this.cuenta = cuenta;
		this.saldo = saldo;
		this.idDivisa = idDivisa;
		this.idBeneficiario = idBeneficiario;
		this.adp = adp;
		this.uoi = uoi;
		this.nombreBenef = nombreBenef;
		this.apellidoPaternoBenef = apellidoPaternoBenef;
		this.apellidoMaternoBenef = apellidoMaternoBenef;
		this.razonSocialBenef = razonSocialBenef;
		this.paisIncorporacion = paisIncorporacion;
		this.idTipoBeneficiario = idTipoBeneficiario;
		this.domicilio = domicilio;
		this.tipoFormato = tipoFormato;
		this.proporcion = proporcion;
		this.fee = fee;
		this.porcentajeMav = porcentajeMav;
		this.porcentajeBen = porcentajeBen;
		this.porcentajeHBDefault = porcentajeHBDefault;
		this.porcentajeRetencionCTB = porcentajeRetencionCTB;
		this.asignacion = asignacion;
		this.idTipoDerecho = idTipoDerecho;
		this.idEstatusDerechoMav = idEstatusDerechoMav;
		this.idEstatusDerecho = idEstatusDerecho;
		this.claveAlfabetica = claveAlfabetica;
		this.descEstatusDerecho = descEstatusDerecho;
		this.fechaFormato = fechaFormato;
		this.estatusBeneficiario = estatusBeneficiario;
		this.descTipoDerecho = descTipoDerecho;
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
	 * @return the claveTipoValor
	 */
	public String getClaveTipoValor() {
		return claveTipoValor;
	}


	/**
	 * @param claveTipoValor the claveTipoValor to set
	 */
	public void setClaveTipoValor(String claveTipoValor) {
		this.claveTipoValor = claveTipoValor;
	}


	/**
	 * @return the clavePizarra
	 */
	public String getClavePizarra() {
		return clavePizarra;
	}


	/**
	 * @param clavePizarra the clavePizarra to set
	 */
	public void setClavePizarra(String clavePizarra) {
		this.clavePizarra = clavePizarra;
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
	 * @return the fechaPagoDerechoCapital
	 */
	public Date getFechaPagoDerechoCapital() {
		return fechaPagoDerechoCapital;
	}


	/**
	 * @param fechaPagoDerechoCapital the fechaPagoDerechoCapital to set
	 */
	public void setFechaPagoDerechoCapital(Date fechaPagoDerechoCapital) {
		this.fechaPagoDerechoCapital = fechaPagoDerechoCapital;
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
	 * @return the detalleCustodio
	 */
	public String getDetalleCustodio() {
		return detalleCustodio;
	}


	/**
	 * @param detalleCustodio the detalleCustodio to set
	 */
	public void setDetalleCustodio(String detalleCustodio) {
		this.detalleCustodio = detalleCustodio;
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
	 * @return the nombreBenef
	 */
	public String getNombreBenef() {
		return nombreBenef;
	}


	/**
	 * @param nombreBenef the nombreBenef to set
	 */
	public void setNombreBenef(String nombreBenef) {
		this.nombreBenef = nombreBenef;
	}


	/**
	 * @return the apellidoPaternoBenef
	 */
	public String getApellidoPaternoBenef() {
		return apellidoPaternoBenef;
	}


	/**
	 * @param apellidoPaternoBenef the apellidoPaternoBenef to set
	 */
	public void setApellidoPaternoBenef(String apellidoPaternoBenef) {
		this.apellidoPaternoBenef = apellidoPaternoBenef;
	}


	/**
	 * @return the apellidoMaternoBenef
	 */
	public String getApellidoMaternoBenef() {
		return apellidoMaternoBenef;
	}


	/**
	 * @param apellidoMaternoBenef the apellidoMaternoBenef to set
	 */
	public void setApellidoMaternoBenef(String apellidoMaternoBenef) {
		this.apellidoMaternoBenef = apellidoMaternoBenef;
	}


	/**
	 * @return the razonSocialBenef
	 */
	public String getRazonSocialBenef() {
		return razonSocialBenef;
	}


	/**
	 * @param razonSocialBenef the razonSocialBenef to set
	 */
	public void setRazonSocialBenef(String razonSocialBenef) {
		this.razonSocialBenef = razonSocialBenef;
	}


	/**
	 * @return the paisIncorporacion
	 */
	public String getPaisIncorporacion() {
		return paisIncorporacion;
	}


	/**
	 * @param paisIncorporacion the paisIncorporacion to set
	 */
	public void setPaisIncorporacion(String paisIncorporacion) {
		this.paisIncorporacion = paisIncorporacion;
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
	 * @return the domicilio
	 */
	public Long getDomicilio() {
		return domicilio;
	}


	/**
	 * @param domicilio the domicilio to set
	 */
	public void setDomicilio(Long domicilio) {
		this.domicilio = domicilio;
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
	 * @return the porcentajeMav
	 */
	public Double getPorcentajeMav() {
		return porcentajeMav;
	}


	/**
	 * @param porcentajeMav the porcentajeMav to set
	 */
	public void setPorcentajeMav(Double porcentajeMav) {
		this.porcentajeMav = porcentajeMav;
	}


	/**
	 * @return the porcentajeBen
	 */
	public Long getPorcentajeBen() {
		return porcentajeBen;
	}


	/**
	 * @param porcentajeBen the porcentajeBen to set
	 */
	public void setPorcentajeBen(Long porcentajeBen) {
		this.porcentajeBen = porcentajeBen;
	}


	/**
	 * @return the porcentajeHBDefault
	 */
	public Double getPorcentajeHBDefault() {
		return porcentajeHBDefault;
	}


	/**
	 * @param porcentajeHBDefault the porcentajeHBDefault to set
	 */
	public void setPorcentajeHBDefault(Double porcentajeHBDefault) {
		this.porcentajeHBDefault = porcentajeHBDefault;
	}


	/**
	 * @return the porcentajeRetencionCTB
	 */
	public Long getPorcentajeRetencionCTB() {
		return porcentajeRetencionCTB;
	}


	/**
	 * @param porcentajeRetencionCTB the porcentajeRetencionCTB to set
	 */
	public void setPorcentajeRetencionCTB(Long porcentajeRetencionCTB) {
		this.porcentajeRetencionCTB = porcentajeRetencionCTB;
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
	 * @return the claveAlfabetica
	 */
	public String getClaveAlfabetica() {
		return claveAlfabetica;
	}


	/**
	 * @param claveAlfabetica the claveAlfabetica to set
	 */
	public void setClaveAlfabetica(String claveAlfabetica) {
		this.claveAlfabetica = claveAlfabetica;
	}


	/**
	 * @return the descEstatusDerecho
	 */
	public String getDescEstatusDerecho() {
		return descEstatusDerecho;
	}


	/**
	 * @param descEstatusDerecho the descEstatusDerecho to set
	 */
	public void setDescEstatusDerecho(String descEstatusDerecho) {
		this.descEstatusDerecho = descEstatusDerecho;
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
	 * @return the descTipoDerecho
	 */
	public String getDescTipoDerecho() {
		return descTipoDerecho;
	}


	/**
	 * @param descTipoDerecho the descTipoDerecho to set
	 */
	public void setDescTipoDerecho(String descTipoDerecho) {
		this.descTipoDerecho = descTipoDerecho;
	}


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


	@Override
	public String toString() {
		return "DerechoCapitalHistoricoVo [idDerechoCapital=" + idDerechoCapital + ", claveTipoValor=" + claveTipoValor
				+ ", clavePizarra=" + clavePizarra + ", serie=" + serie + ", isin=" + isin
				+ ", fechaPagoDerechoCapital=" + fechaPagoDerechoCapital + ", fechaCorte=" + fechaCorte + ", idCatBic="
				+ idCatBic + ", detalleCustodio=" + detalleCustodio + ", idFolio=" + idFolio + ", idEmision="
				+ idEmision + ", idCuentaNombradaBeneficiario=" + idCuentaNombradaBeneficiario
				+ ", idCuentaNombradaCuenta=" + idCuentaNombradaCuenta + ", cuenta=" + cuenta + ", saldo=" + saldo
				+ ", idDivisa=" + idDivisa + ", idBeneficiario=" + idBeneficiario + ", adp=" + adp + ", uoi=" + uoi
				+ ", nombreBenef=" + nombreBenef + ", apellidoPaternoBenef=" + apellidoPaternoBenef
				+ ", apellidoMaternoBenef=" + apellidoMaternoBenef + ", razonSocialBenef=" + razonSocialBenef
				+ ", paisIncorporacion=" + paisIncorporacion + ", idTipoBeneficiario=" + idTipoBeneficiario
				+ ", domicilio=" + domicilio + ", tipoFormato=" + tipoFormato + ", proporcion=" + proporcion + ", fee="
				+ fee + ", porcentajeMav=" + porcentajeMav + ", porcentajeBen=" + porcentajeBen
				+ ", porcentajeHBDefault=" + porcentajeHBDefault + ", porcentajeRetencionCTB=" + porcentajeRetencionCTB
				+ ", asignacion=" + asignacion + ", idTipoDerecho=" + idTipoDerecho + ", idEstatusDerechoMav="
				+ idEstatusDerechoMav + ", idEstatusDerecho=" + idEstatusDerecho + ", claveAlfabetica="
				+ claveAlfabetica + ", descEstatusDerecho=" + descEstatusDerecho + ", fechaFormato=" + fechaFormato
				+ ", estatusBeneficiario=" + estatusBeneficiario + ", descTipoDerecho=" + descTipoDerecho + ", id=" + id
				+ "]";
	}
	
}