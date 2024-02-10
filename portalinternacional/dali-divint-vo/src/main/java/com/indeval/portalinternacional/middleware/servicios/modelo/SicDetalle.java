/*
 * Copyright (c) 2005-2008 Bursatec. All Rights Reserved.
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

import com.indeval.portaldali.persistence.modelo.CuentaNombrada;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
@Entity
@Table(name = "C_SIC_DETALLE")
@SequenceGenerator(name = "foliador", sequenceName = "ID_SIC_DETALLE_SEQ", allocationSize = 1, initialValue = 1)
public class SicDetalle implements Serializable {
    
	/**
	 * Constante de serializaci&oacute;n por default 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Long
	 */
	private Long idSicDetalle;

	/**
	 * CuentaNombrada
	 */
	private CuentaNombrada cuentaNombrada;

	/**
	 * String
	 */
	private String bicDepLiq;

	/**
	 * String
	 */
	private String depLiq;

	/**
	 * String
	 */
	private String idDepLiq;

	/**
	 * Date
	 */
	private Date fechaHora;

	/**
	 * String 
	 */
	private String aplicacion;
	
	/**
	 * String 
	 */
	private String estatusRegistro;
	
	/**
	 * CatBic 
	 */
	private CatBic catBic;
        

	/**
	 * @return Long
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "id_sic_detalle", unique = true, nullable = false)
	public Long getIdSicDetalle() {
		return idSicDetalle;
	}

	/**
	 * @return CuentaNombrada
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CUENTA_NOMBRADA", nullable = false)
	public CuentaNombrada getCuentaNombrada() {
		return cuentaNombrada;
	}

	/**
	 * @return String
	 */
	@Column(name = "bic_dep_liq", unique = false, nullable = false)
	public String getBicDepLiq() {
		return bicDepLiq;
	}

	/** 
	 * @return String
	 */
	@Column(name = "dep_liq", unique = false, nullable = false)
	public String getDepLiq() {
		return depLiq;
	}

	/**
	 * @return String
	 */
	@Column(name = "id_dep_liq", unique = false, nullable = false)
	public String getIdDepLiq() {
		return idDepLiq;
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
	@Column(name = "aplicacion", unique = false, nullable = true)
	public String getAplicacion() {
		return aplicacion;
	}

	/**
	 * @return String
	 */
	@Column(name = "estatus_registro", unique = false, nullable = true)
	public String getEstatusRegistro() {
		return (estatusRegistro);
	}
	
	/**
	 * @return CatBic
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_catbic", nullable = true, insertable = true, updatable = true)
	public CatBic getCatBic() {
		return catBic;
	}
	
	/**
	 * @param idSicDetalle
	 */
	public void setIdSicDetalle(Long idSicDetalle) {
		this.idSicDetalle = idSicDetalle;
	}

	/**
	 * @param cuentaNombrada the cuentaNombrada to set
	 */
	public void setCuentaNombrada(CuentaNombrada cuentaNombrada) {
		this.cuentaNombrada = cuentaNombrada;
	}

	/**
	 * @param bicDepLiq
	 */
	public void setBicDepLiq(String bicDepLiq) {
		this.bicDepLiq = bicDepLiq;
	}

	/**
	 * @param depLiq
	 */
	public void setDepLiq(String depLiq) {
		this.depLiq = depLiq;
	}

	/**
	 * @param idDepLiq
	 */
	public void setIdDepLiq(String idDepLiq) {
		this.idDepLiq = idDepLiq;
	}

	/**
	 * @param fechaHora
	 */
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	/**
	 * @param aplicacion
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @param estatusRegistro
	 */
	public void setEstatusRegistro(String estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}
	
	/**
	 * @param catBic
	 */
	public void setCatBic(CatBic catBic) {
		this.catBic = catBic;
	}
        
}
