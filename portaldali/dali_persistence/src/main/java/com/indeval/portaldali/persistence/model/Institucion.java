/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Cata&acute;logo de las instituciones financieras que tienen relaci&oacute;n
 * con INDEVAL. Esta contiene principalmente diferentes claves por medio de las
 * cuales puede identificarse a cada uno de los participantes.
 * 
 * C_INSTITUCION
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "C_INSTITUCION")
public class Institucion implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la instituci&oacute;n.
	 */
	@Id
	@Column(name = "ID_INSTITUCION")
	private BigInteger idInstitucion;
	/**
	 * Tipo de instituci&oacute;n.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_INSTITUCION")
	private TipoInstitucion tipoInstitucion;
	/**
	 * Folio de la instituci&oacute;n.
	 */
	@Column(name = "FOLIO_INSTITUCION")
	private String folioInstitucion;
	/**
	 * BIC de la instituci&oacute;n.
	 */
	@Column(name = "BIC")
	private String bic;
	/**
	 * Clave casfim de la instituci&oacute;n.
	 */
	@Column(name = "CLAVE_CASFIM")
	private String claveCasfim;
	/**
	 * Nombre corto de la instituci&oacute;n.
	 */
	@Column(name = "NOMBRE_CORTO")
	private String nombreCorto;
	/**
	 * Fecha de registro de la instituci&oacute;n.
	 */
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;
	/**
	 * Identificador del estado de la instituci&oacute;n.
	 */
	@Column(name = "ID_ESTADO_INSTITUCION")
	private BigInteger idEstadoInstitucion;
	/**
	 * Fecha de baja de la instituci&oacute;n.
	 */
	@Column(name = "FECHA_BAJA")
	private Date fechaBaja;

	/**
	 * La cuenta CLABE de la institución.
	 */
	@Column(name = "CUENTA_CLABE")
	private String cuentaClabe;

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name =
	// "id_estado_institucion",insertable=false,updatable=false)
	// private EstadoInstitucion estadoInstitucion; // FK
	@Column(name = "clave_casfim2", unique = false, nullable = true)
	private BigDecimal claveCasfim2;
	@Column(name = "origen", unique = false, nullable = true)
	private String origen;
	@Column(name = "interna", unique = false, nullable = true)
	private BigDecimal interna;
	@Column(name = "razon_social", unique = false, nullable = true)
	private String razonSocial;

	/*
	 * Se agregan campos para la resolución del requerimiento R-IND-2014-010
	 * 02/10/2014 Pablo Balderas.
	 */
	
	/** Clave SPEI */
	@Column(name="CLAVE_SPEI")
	private String claveSpei;
	
	/** Opera SIAC */
	@Column(name="OPERA_SIAC")
	private Boolean operaSiac;
	
	/** Cuenta Clabe BENEFICIARIO */
	@Column(name="CUENTA_CLABE_BENEFICIARIO")
	private String cuentaClabeBeneficiario;
	
	/** Clave SPEI BENEFICIARIO */
	@Column(name="CLAVE_SPEI_BENEFICIARIO")
	private String claveSpeiBeneficiario;
	
	
	/**
	 * @return the claveSpei
	 */
	public String getClaveSpei() {
		return claveSpei;
	}

	/**
	 * @param claveSpei the claveSpei to set
	 */
	public void setClaveSpei(String claveSpei) {
		this.claveSpei = claveSpei;
	}

	/**
	 * @return the operaSiac
	 */
	public Boolean isOperaSiac() {
		return operaSiac;
	}

	/**
	 * @param operaSiac the operaSiac to set
	 */
	public void setOperaSiac(Boolean operaSiac) {
		this.operaSiac = operaSiac;
	}

	/**
	 * Identificador secuencial de la instituci&oacute;n.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * @param idInstitucion
	 */
	public void setIdInstitucion(BigInteger idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * Referencia al tipo de la instituci&oacute;n.
	 * 
	 * @return TipoInstitucion
	 */
	public TipoInstitucion getTipoInstitucion() {
		return tipoInstitucion;
	}

	/**
	 * Obtiene el valor del atributo cuentaClabe
	 * 
	 * @return el valor del atributo cuentaClabe
	 */
	public String getCuentaClabe() {
		return cuentaClabe;
	}

	/**
	 * Establece el valor del atributo cuentaClabe
	 * 
	 * @param cuentaClabe
	 *            el valor del atributo cuentaClabe a establecer
	 */
	public void setCuentaClabe(String cuentaClabe) {
		this.cuentaClabe = cuentaClabe;
	}

	/**
	 * @param tipoInstitucion
	 */
	public void setTipoInstitucion(TipoInstitucion tipoInstitucion) {
		this.tipoInstitucion = tipoInstitucion;
	}

	/**
	 * Folio correspondiente a la instituci&oacute;n.
	 * 
	 * @return String
	 */
	public String getFolioInstitucion() {
		return folioInstitucion;
	}

	/**
	 * @param folioInstitucion
	 */
	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}

	/**
	 * BIC/BEI. Homologaci&oacute;n con norma ISO-9362.
	 * 
	 * @return String
	 */
	public String getBic() {
		return bic;
	}

	/**
	 * @param bic
	 */
	public void setBic(String bic) {
		this.bic = bic;
	}

	/**
	 * número de sector, gui&oacute;n como separador, y número de
	 * identificaci&oacute;n de instituci&oacute;n financiera.
	 * Homologaci&oacute;n con CASFIM.
	 * 
	 * @return String
	 */
	public String getClaveCasfim() {
		return claveCasfim;
	}

	/**
	 * @param claveCasfim
	 */
	public void setClaveCasfim(String claveCasfim) {
		this.claveCasfim = claveCasfim;
	}

	/**
	 * Nombre corto, siglas de la instituci&oacute;n. Para una
	 * instituci&oacute;n listada en CASFIM debe coincidir con el nombre corto
	 * ah listado.
	 * 
	 * @return String
	 */
	public String getNombreCorto() {
		return nombreCorto;
	}

	/**
	 * @param nombreCorto
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	/**
	 * Fecha en que se hace el alta de la instituci&oacute;n.
	 * 
	 * @return Date
	 */
	public Date getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * @param fechaAlta
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	/**
	 * Referencia al estado de la instituci&oacute;n.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getIdEstadoInstitucion() {
		return idEstadoInstitucion;
	}

	/**
	 * @param idEstadoInstitucion
	 */
	public void setIdEstadoInstitucion(BigInteger idEstadoInstitucion) {
		this.idEstadoInstitucion = idEstadoInstitucion;
	}

	/**
	 * Fecha en que se hace la baja l&oacute;gica de la instituci&oacute;n.
	 * 
	 * @return Date
	 */
	public Date getFechaBaja() {
		return fechaBaja;
	}

	/**
	 * @param fechaBaja
	 */
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 23).append(idInstitucion).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Institucion)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Institucion rhs = (Institucion) obj;
		return new EqualsBuilder().append(idInstitucion, rhs.getIdInstitucion()).isEquals();
	}

	public BigDecimal getClaveCasfim2() {
		return claveCasfim2;
	}

	public String getOrigen() {
		return origen;
	}

	public BigDecimal getInterna() {
		return interna;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param estadoInstitucion
	 */

	/**
	 * @param claveCasfim2
	 */
	public void setClaveCasfim2(BigDecimal claveCasfim2) {
		this.claveCasfim2 = claveCasfim2;
	}

	/**
	 * @param origen
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @param interna
	 */
	public void setInterna(BigDecimal interna) {
		this.interna = interna;
	}

	/**
	 * @param razonSocial
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return the cuentaClabeBeneficiario
	 */
	public String getCuentaClabeBeneficiario() {
		return cuentaClabeBeneficiario;
	}

	/**
	 * @param cuentaClabeBeneficiario the cuentaClabeBeneficiario to set
	 */
	public void setCuentaClabeBeneficiario(String cuentaClabeBeneficiario) {
		this.cuentaClabeBeneficiario = cuentaClabeBeneficiario;
	}

	/**
	 * @return the claveSpeiBeneficiario
	 */
	public String getClaveSpeiBeneficiario() {
		return claveSpeiBeneficiario;
	}

	/**
	 * @param claveSpeiBeneficiario the claveSpeiBeneficiario to set
	 */
	public void setClaveSpeiBeneficiario(String claveSpeiBeneficiario) {
		this.claveSpeiBeneficiario = claveSpeiBeneficiario;
	}
	
	
}
