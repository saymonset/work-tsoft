/*
 * Copyrigth (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Clase para representar un statement
 * 
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class StatementDivintVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/** Campo del Id de la entidad */
	private Long idStatement;
	/** Campo del tipo valor de la emision */
	private String tv;
	/** Campo de la emisora de la emision */
	private String emisora;
	/** Campo de la serie de la emision */
	private String serie;
	/** Campo del isin de la emision */
	private String isin;
	/** Campo de la fecha de pago */
	private Date fechaPago;
	/** Campo de la fecha de registro */
	private Date fechaRegistro;
	/** Campo de la fecha de creacion del registro */
	private Date fechaCreacion;
	/** Id de la institucion */
	private Long idInstitucion;
	/** Campo del id Folio de la institucion */
	private String idFolio;
	/** Campo del nombre de la institucion */
	private String nombreInstitucion;
	/** Razon social de la institucion */
	private String razonSocial;
	/** Campo del ADP del beneficiario */
	private String adp;
	/** Campo del nombre */
	private String nombre;
	/** Campo del  rfc */
	private String rfc;
	/** Campo del direccion */
	private String direccion;
	/** Campo del  Tax Payer Identification Number */
	private String taxPayerNumber;
	/** Campo del tipo de formato */
	private String formato;
	/** Campo del status of owner */
	private String statusOwner;
	/** Campo del Id del tipo de beneficiario */
	private Long idTipoBeneficiario;
	/** Campo del tipo de beneficiario */
	private String tipoBeneficiario;
	/** Campo del pais */
	private String pais;
	/** Campo del porcentaje de retencion */
	private BigDecimal porcentajeRetencion;
	/** Campo del numero de titulos */
	private Long numeroTitulos;
	/** Campo de la proporcion */
	private BigDecimal proporcion;
	/** Campo del dividendo */
	private BigDecimal dividendo;
	/** Campo del impuesto */
	private BigDecimal impuesto;
	/** Campo del dividendo eneto */
	private BigDecimal dividendoNeto;
	/** Campo del nombre del archivo origen */
	private String archivoOrigen;
	/** Campo del nombre del archivo zip de origen si lo hay */
	private String archivoZip;
	/** Campo de cargo de la institucion */
	private Boolean cargoInstitucion;
	
	/** Campo de cargo de la custodio */
	private String custodio;

	/** Cosntructor por omision */
	public StatementDivintVO() {
		super();
		idTipoBeneficiario = null;
	}

	/** Constructor con los parametros para Hibernate */
	public StatementDivintVO(Long idStatement, String tv, String emisora, String serie, String isin, Date fechaPago,
			Date fechaRegistro, Date fechaCreacion, String idFolio, String nombreInstitucion, String adp, String nombre,
			String rfc, String direccion, String taxPayerNumber, String formato, String statusOwner, String tipoBeneficiario,
			String pais, BigDecimal porcentajeRetencion, Long numeroTitulos, BigDecimal proporcion, BigDecimal dividendo,
			BigDecimal impuesto, BigDecimal dividendoNeto, String archivoOrigen, String razonSocial, Boolean cargoInstitucion,
			Long idInstitucion, String rfcInstitucion) {
		this();
		this.idStatement = idStatement;
		this.tv = tv;
		this.emisora = emisora;
		this.serie = serie;
		this.isin = isin;
		this.fechaPago = fechaPago;
		this.fechaRegistro = fechaRegistro;
		this.fechaCreacion = fechaCreacion;
		this.idFolio = idFolio;
		this.nombreInstitucion = nombreInstitucion;
		this.adp = adp;
		this.nombre = nombre;
		if(!cargoInstitucion) {
			this.rfc = rfc;
		} else {
			this.rfc = rfcInstitucion;
		}
		this.direccion = direccion;
		this.taxPayerNumber = taxPayerNumber;
		this.formato = formato;
		this.statusOwner = statusOwner;
		this.tipoBeneficiario = tipoBeneficiario;
		this.pais = pais;
		this.porcentajeRetencion = porcentajeRetencion != null ? porcentajeRetencion : BigDecimal.ZERO;
		this.numeroTitulos = numeroTitulos;
		this.proporcion = proporcion != null ? proporcion : BigDecimal.ZERO;
		this.dividendo = dividendo != null ? dividendo : BigDecimal.ZERO;
		this.impuesto = impuesto != null ? impuesto : BigDecimal.ZERO;
		this.dividendoNeto = dividendoNeto != null ? dividendoNeto : BigDecimal.ZERO;
		this.archivoOrigen = archivoOrigen;
		this.razonSocial = razonSocial;
		this.cargoInstitucion = cargoInstitucion;
		this.idInstitucion = idInstitucion;

	}
	
	/*Constructor modificado se agrega campo  custodio*/
	public StatementDivintVO(Long idStatement, String tv, String emisora, String serie, String isin, Date fechaPago,
			Date fechaRegistro, Date fechaCreacion, String idFolio, String nombreInstitucion, String adp, String nombre,
			String rfc, String direccion, String taxPayerNumber, String formato, String statusOwner, String tipoBeneficiario,
			String pais, BigDecimal porcentajeRetencion, Long numeroTitulos, BigDecimal proporcion, BigDecimal dividendo,
			BigDecimal impuesto, BigDecimal dividendoNeto, String archivoOrigen, String razonSocial, Boolean cargoInstitucion,
			Long idInstitucion, String rfcInstitucion, String custodio) {
		this();
		this.idStatement = idStatement;
		this.tv = tv;
		this.emisora = emisora;
		this.serie = serie;
		this.isin = isin;
		this.fechaPago = fechaPago;
		this.fechaRegistro = fechaRegistro;
		this.fechaCreacion = fechaCreacion;
		this.idFolio = idFolio;
		this.nombreInstitucion = nombreInstitucion;
		this.adp = adp;
		this.nombre = nombre;
		if(!cargoInstitucion) {
			this.rfc = rfc;
		} else {
			this.rfc = rfcInstitucion;
		}
		this.direccion = direccion;
		this.taxPayerNumber = taxPayerNumber;
		this.formato = formato;
		this.statusOwner = statusOwner;
		this.tipoBeneficiario = tipoBeneficiario;
		this.pais = pais;
		this.porcentajeRetencion = porcentajeRetencion != null ? porcentajeRetencion : BigDecimal.ZERO;
		this.numeroTitulos = numeroTitulos;
		this.proporcion = proporcion != null ? proporcion : BigDecimal.ZERO;
		this.dividendo = dividendo != null ? dividendo : BigDecimal.ZERO;
		this.impuesto = impuesto != null ? impuesto : BigDecimal.ZERO;
		this.dividendoNeto = dividendoNeto != null ? dividendoNeto : BigDecimal.ZERO;
		this.archivoOrigen = archivoOrigen;
		this.razonSocial = razonSocial;
		this.cargoInstitucion = cargoInstitucion;
		this.idInstitucion = idInstitucion;
		this.custodio = custodio;

	}


	/**
	 * Campo del Id de la entidad
	 * @return the idStatement
	 */
	public Long getIdStatement() {
		return idStatement;
	}

	/**
	 * Campo del Id de la entidad
	 * @param idStatement the idStatement to set
	 */
	public void setIdStatement(Long idStatement) {
		this.idStatement = idStatement;
	}

	/**
	 * Campo del tipo valor de la emision
	 * @return the tv
	 */
	public String getTv() {
		return tv;
	}

	/**
	 * Campo del tipo valor de la emision
	 * @param tv the tv to set
	 */
	public void setTv(String tv) {
		this.tv = tv;
	}

	/**
	 * Campo de la emisora de la emision
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * Campo de la emisora de la emision
	 * @param emisora the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * Campo de la serie de la emision
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * Campo de la serie de la emision
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * Campo del isin de la emision
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * Campo del isin de la emision
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * Campo de la fecha de pago
	 * @return the fechaPago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}

	/**
	 * Campo de la fecha de pago
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * Campo de la fecha de registro
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * Campo de la fecha de registro
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * Campo de la fecha de creacion del registro
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Campo de la fecha de creacion del registro
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Id de la institucion
	 * @return the idInstitucion
	 */ public Long getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * Id de la institucion
	 * @param idInstitucion the idInstitucion to set
	 */ public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * Campo del id Folio de la institucion
	 * @return the idFolio
	 */
	public String getIdFolio() {
		return idFolio;
	}

	/**
	 * Campo del id Folio de la institucion
	 * @param idFolio the idFolio to set
	 */
	public void setIdFolio(String idFolio) {
		this.idFolio = idFolio;
	}

	/**
	 * Campo del nombre de la institucion
	 * @return the nombreInstitucion
	 */
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	/**
	 * Campo del nombre de la institucion
	 * @param nombreInstitucion the nombreInstitucion to set
	 */
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	/**
	 * Razon social de la institucion
	 * @return the razonSocial
	 */ public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * Razon social de la institucion
	 * @param razonSocial the razonSocial to set
	 */ public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * Campo del ADP del beneficiario
	 * @return the adp
	 */
	public String getAdp() {
		return adp;
	}

	/**
	 * Campo del ADP del beneficiario
	 * @param adp the adp to set
	 */
	public void setAdp(String adp) {
		this.adp = adp;
	}

	/**
	 * Campo del nombre
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Campo del nombre
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Campo del  rfc
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * Campo del  rfc
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * Campo del direccion
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Campo del direccion
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Campo del  Tax Payer Identification Number
	 * @return the taxPayerNumber
	 */
	public String getTaxPayerNumber() {
		return taxPayerNumber;
	}

	/**
	 * Campo del  Tax Payer Identification Number
	 * @param taxPayerNumber the taxPayerNumber to set
	 */
	public void setTaxPayerNumber(String taxPayerNumber) {
		this.taxPayerNumber = taxPayerNumber;
	}

	/**
	 * Campo del tipo de formato
	 * @return the formato
	 */
	public String getFormato() {
		return formato;
	}

	/**
	 * Campo del tipo de formato
	 * @param formato the formato to set
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}

	/**
	 * Campo del status of owner
	 * @return the statusOwner
	 */
	public String getStatusOwner() {
		return statusOwner;
	}

	/**
	 * Campo del status of owner
	 * @param statusOwner the statusOwner to set
	 */
	public void setStatusOwner(String statusOwner) {
		this.statusOwner = statusOwner;
	}

	/**
	 * Campo del Id del tipo de beneficiario
	 * @return the idTipoBeneficiario
	 */
	public Long getIdTipoBeneficiario() {
		return idTipoBeneficiario;
	}

	/**
	 * Campo del Id del tipo de beneficiario
	 * @param idTipoBeneficiario the idTipoBeneficiario to set
	 */
	public void setIdTipoBeneficiario(Long idTipoBeneficiario) {
		this.idTipoBeneficiario = idTipoBeneficiario;
	}

	/**
	 * Campo del tipo de beneficiario
	 * @return the tipoBeneficiario
	 */
	public String getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	/**
	 * Campo del tipo de beneficiario
	 * @param tipoBeneficiario the tipoBeneficiario to set
	 */
	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	/**
	 * Campo del pais
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * Campo del pais
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * Campo del porcentaje de retencion
	 * @return the porcentajeRetencion
	 */
	public BigDecimal getPorcentajeRetencion() {
		return porcentajeRetencion;
	}

	/**
	 * Campo del porcentaje de retencion
	 * @param porcentajeRetencion the porcentajeRetencion to set
	 */
	public void setPorcentajeRetencion(BigDecimal porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}

	/**
	 * Campo del numero de titulos
	 * @return the numeroTitulos
	 */
	public Long getNumeroTitulos() {
		return numeroTitulos;
	}

	/**
	 * Campo del numero de titulos
	 * @param numeroTitulos the numeroTitulos to set
	 */
	public void setNumeroTitulos(Long numeroTitulos) {
		this.numeroTitulos = numeroTitulos;
	}

	/**
	 * Campo de la proporcion
	 * @return the proporcion
	 */
	public BigDecimal getProporcion() {
		return proporcion;
	}

	/**
	 * Campo de la proporcion
	 * @param proporcion the proporcion to set
	 */
	public void setProporcion(BigDecimal proporcion) {
		this.proporcion = proporcion;
	}

	/**
	 * Campo del dividendo
	 * @return the dividendo
	 */
	public BigDecimal getDividendo() {
		return dividendo;
	}

	/**
	 * Campo del dividendo
	 * @param dividendo the dividendo to set
	 */
	public void setDividendo(BigDecimal dividendo) {
		this.dividendo = dividendo;
	}

	/**
	 * Campo del impuesto
	 * @return the impuesto
	 */
	public BigDecimal getImpuesto() {
		return impuesto;
	}

	/**
	 * Campo del impuesto
	 * @param impuesto the impuesto to set
	 */
	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}

	/**
	 * Campo del dividendo eneto
	 * @return the dividendoNeto
	 */
	public BigDecimal getDividendoNeto() {
		return dividendoNeto;
	}

	/**
	 * Campo del dividendo eneto
	 * @param dividendoNeto the dividendoNeto to set
	 */
	public void setDividendoNeto(BigDecimal dividendoNeto) {
		this.dividendoNeto = dividendoNeto;
	}

	/**
	 * Campo del nombre del archivo origen
	 * @return the archivoOrigen
	 */
	public String getArchivoOrigen() {
		return archivoOrigen;
	}

	/**
	 * Campo del nombre del archivo origen
	 * @param archivoOrigen the archivoOrigen to set
	 */
	public void setArchivoOrigen(String archivoOrigen) {
		this.archivoOrigen = archivoOrigen;
	}

	/**
	 * Campo del nombre del archivo zip de origen si lo hay
	 * @return the archivoZip
	 */
	public String getArchivoZip() {
		return archivoZip;
	}

	/**
	 * Campo del nombre del archivo zip de origen si lo hay
	 * @param archivoZip the archivoZip to set
	 */
	public void setArchivoZip(String archivoZip) {
		this.archivoZip = archivoZip;
	}
	
	


	public String getCustodio() {
		return custodio;
	}

	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}

	/**
	 * Campo de cargo de la institucion
	 * @return the cargoInstitucion
	 */ public Boolean getCargoInstitucion() {
		return cargoInstitucion;
	}

	/**
	 * Campo de cargo de la institucion
	 * @param cargoInstitucion the cargoInstitucion to set
	 */ public void setCargoInstitucion(Boolean cargoInstitucion) {
		this.cargoInstitucion = cargoInstitucion;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if (obj instanceof StatementDivintVO) {
			StatementDivintVO statement = (StatementDivintVO) obj;
			isEqual = new EqualsBuilder().append(idStatement, statement.idStatement).isEquals();
		}
		return isEqual;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 23).append(idStatement).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).
				append("idStatement", idStatement).
				append("tv", tv).
				append("emisora", emisora).
				append("serie", serie).
				append("fechaPago", fechaPago).
				append("fechaRegistro", fechaRegistro).
				append("isin", isin).
				append("idFolio", idFolio).
				append("adp", adp).
				append("nombre", nombre).
				append("direccion", direccion).
				append("taxPayerNumber", taxPayerNumber).
				append("formato", formato).
				append("statusOwner", statusOwner).
				append("tipoBeneficiario", tipoBeneficiario).
				append("pais", pais).
				append("porcentajeRetencion", porcentajeRetencion).
				append("numeroTitulos", numeroTitulos).
				append("rfc", rfc).
				append("proporcion", proporcion).
				append("dividendo", dividendo).
				append("impuesto", impuesto).
				append("dividendoNeto", dividendoNeto).
				append("archivoOrigen", archivoOrigen).
				append("archivoZip", archivoZip).
				toString();
	}
}
