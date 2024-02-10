/*
 * Copyright (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.indeval.portaldali.persistence.modelo.CuentaNombrada;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
@Entity
@Table(name = "C_CATBIC")
@SequenceGenerator(name = "foliador", sequenceName = "ID_CATBIC_SEQ", allocationSize = 1, initialValue = 1)
public class CatBic implements Serializable {
    
	/**
	 * Constante de serializaci&oacute;n por default 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Long
	 */
	private Long idCatbic;

	/**
	 * CuentaNombrada
	 */
	private CuentaNombrada cuentaNombrada;

	/**
	 * String
	 */
	private String bicProd;

	/**
	 * String
	 */
	private String bicPrueba;

	/**
	 * String
	 */
	private String cuentaIndeval;

	/**
	 * Date
	 */
	private Date fechaHora;

	/**
	 * String
	 */
	private String pais;

	/**
	 * String
	 */
	private String status;

	/**
	 * String
	 */
	private String usuario;

	/**
	 * String 
	 */
	private String moneda;

	/**
	 * String
	 */
	private String detalleCustodio;

	/**
	 * String 
	 */
	private String mercado;
	
	/**
	 * String 
	 */
	private String estatusRegistro;
	

	/**
	 * Integer
	 */
	private Integer activo;
	
	/**
	 * String
	 */
	private String abreviacionCustodio;
	
	/**
	 * @return Long
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "id_catbic", unique = true, nullable = false)
	public Long getIdCatbic() {
		return idCatbic;
	}

	/**
	 * @return CuentaNombrada
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CUENTA_NOMBRADA", unique = false, nullable = false)
	public CuentaNombrada getCuentaNombrada() {
		return cuentaNombrada;
	}

	/**
	 * @return String
	 */
	@Column(name = "bic_prod", unique = false, nullable = true)
	public String getBicProd() {
		return bicProd;
	}

	/**
	 * @return String
	 */
	@Column(name = "bic_prueba", unique = false, nullable = true)
	public String getBicPrueba() {
		return bicPrueba;
	}

	/**
	 * @return String
	 */
	@Column(name = "cuenta_indeval", unique = false, nullable = true)
	public String getCuentaIndeval() {
		return cuentaIndeval;
	}

	/**
	 * @return Date
	 */
	@Column(name = "fecha_hora", unique = false, nullable = false)
	public Date getFechaHora() {
		return fechaHora;
	}

	/**
	 * @return String
	 */
	@Column(name = "pais", unique = false, nullable = true)
	public String getPais() {
		return pais;
	}

	/**
	 * @return String
	 */
	@Column(name = "status", unique = false, nullable = true)
	public String getStatus() {
		return status;
	}
	

	/**
	 * @return String
	 */
	@Column(name = "usuario", unique = false, nullable = true)
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @return String
	 */
	@Column(name = "moneda", unique = false, nullable = true)
	public String getMoneda() {
		return moneda;
	}

	/**
	 * @return String
	 */
	@Column(name = "detalle_custodio", unique = false, nullable = true)
	public String getDetalleCustodio() {
		return detalleCustodio;
	}

	/**
	 * @return String
	 */
	@Column(name = "mercado", unique = false, nullable = true)
	public String getMercado() {
		return mercado;
	}
	
	/**
	 * @return String
	 */
	@Column(name = "estatus_registro", unique = false, nullable = true)
	public String getEstatusRegistro() {
		return estatusRegistro;
	}
	
	/**
	 * 
	 * @return Integer
	 */
	@Column(name = "activo", unique = false, nullable= true)
	public Integer getActivo() {
		return activo;
	}
	
	@Column(name = "abreviacion", unique = false, nullable= true)
	public String getAbreviacionCustodio() {
		return abreviacionCustodio;
	}

	/**
	 * @param idCatbic
	 */
	public void setIdCatbic(Long idCatbic) {
		this.idCatbic = idCatbic;
	}

	/**
	 * @param cuentaNombrada
	 */
	public void setCuentaNombrada(CuentaNombrada cuentaNombrada) {
		this.cuentaNombrada = cuentaNombrada;
	}

	/**
	 * @param bicProd
	 */
	public void setBicProd(String bicProd) {
		this.bicProd = bicProd;
	}

	/**
	 * @param bicPrueba
	 */
	public void setBicPrueba(String bicPrueba) {
		this.bicPrueba = bicPrueba;
	}

	/**
	 * @param cuentaIndeval
	 */
	public void setCuentaIndeval(String cuentaIndeval) {
		this.cuentaIndeval = cuentaIndeval;
	}

	/**
	 * @param fechaHora
	 */
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	/**
	 * @param pais
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @param moneda
	 */
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	/**
	 * @param detalleCustodio
	 */
	public void setDetalleCustodio(String detalleCustodio) {
		this.detalleCustodio = detalleCustodio;
	}
	
	/**
	 * @param mercado
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	/**
	 * @param estatusRegistro
	 */
	public void setEstatusRegistro(String estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}
	
	/**
	 * 
	 * @param activo
	 */
	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	/**
	 * @param abreviacionCustodio the abreviacionCustodio to set
	 */
	public void setAbreviacionCustodio(String abreviacionCustodio) {
		this.abreviacionCustodio = abreviacionCustodio;
	}

    @Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {return false;}
		if(obj == this) {return true;}
		if(!(obj instanceof CatBic)) {return false;}

		CatBic other=(CatBic)obj;
		
		boolean isEquals = false;

		isEquals=EqualsBuilder.reflectionEquals(this, other);
		
		return isEquals;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CatBic [idCatbic=" + idCatbic + ", cuentaNombrada=" + cuentaNombrada + ", bicProd=" + bicProd
				+ ", bicPrueba=" + bicPrueba + ", cuentaIndeval=" + cuentaIndeval + ", fechaHora=" + fechaHora
				+ ", pais=" + pais + ", status=" + status + ", usuario=" + usuario + ", moneda=" + moneda
				+ ", detalleCustodio=" + detalleCustodio + ", mercado=" + mercado + ", estatusRegistro="
				+ estatusRegistro + ", activo=" + activo + ", abreviacionCustodio=" + abreviacionCustodio + "]";
	}
	
	
}