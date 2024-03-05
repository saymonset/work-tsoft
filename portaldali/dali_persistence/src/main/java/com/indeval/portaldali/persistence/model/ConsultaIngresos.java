/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Catalogo para guardar los tipos de consulta por sistema
 * asi como informacion de cobro
 * 
 * C_CONSULTAS_INGRESOS
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
@Entity
@Table(name = "C_CONSULTAS_INGRESOS")
public class ConsultaIngresos implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la consulta
	 */
	@Id
	@Column(name = "ID_CONSULTA")
	private Long idConsulta;
	/**
	 * Sistema asociado.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SISTEMA")
	private Sistema sistema;
	/**
	 * Nombre de la consulta
	 */
	@Column(name = "NOMBRE")
	private String nombre;
	/**
	 * Fecha del registro de la consulta.
	 */
	@Column(name = "FECHA_REGISTRO")
	private Date fechaRegistro;
	/**
	 * Cobro por registro.
	 */
	@Column(name = "COBRO_POR_REGISTRO")
	private BigDecimal cobroPorRegistro;
	/**
	 * Cobro por consulta.
	 */
	@Column(name = "COBRO_POR_CONSULTA")
	private BigDecimal cobroPorConsulta;
	/**
	 * Indica si la consulta es bitacorable.
	 */
	@Column(name = "ES_BITACORABLE")
	private Boolean bitacorable;
	/**
	 * Indica si la consulta se debe cobrar.
	 */
	@Column(name = "ES_COBRABLE")
	private Boolean cobrable;
	/**
	 * @return the idConsulta
	 */
	public Long getIdConsulta() {
		return idConsulta;
	}
	/**
	 * @param idConsulta the idConsulta to set
	 */
	public void setIdConsulta(Long idConsulta) {
		this.idConsulta = idConsulta;
	}
	/**
	 * @return the sistema
	 */
	public Sistema getSistema() {
		return sistema;
	}
	/**
	 * @param sistema the sistema to set
	 */
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
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
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	/**
	 * @return the cobroPorRegistro
	 */
	public BigDecimal getCobroPorRegistro() {
		return cobroPorRegistro;
	}
	/**
	 * @param cobroPorRegistro the cobroPorRegistro to set
	 */
	public void setCobroPorRegistro(BigDecimal cobroPorRegistro) {
		this.cobroPorRegistro = cobroPorRegistro;
	}
	/**
	 * @return the cobroPorConsulta
	 */
	public BigDecimal getCobroPorConsulta() {
		return cobroPorConsulta;
	}
	/**
	 * @param cobroPorConsulta the cobroPorConsulta to set
	 */
	public void setCobroPorConsulta(BigDecimal cobroPorConsulta) {
		this.cobroPorConsulta = cobroPorConsulta;
	}
	/**
	 * @return the bitacorable
	 */
	public Boolean getBitacorable() {
		return bitacorable;
	}
	/**
	 * @param bitacorable the bitacorable to set
	 */
	public void setBitacorable(Boolean bitacorable) {
		this.bitacorable = bitacorable;
	}
	/**
	 * @return the cobrable
	 */
	public Boolean getCobrable() {
		return cobrable;
	}
	/**
	 * @param cobrable the cobrable to set
	 */
	public void setCobrable(Boolean cobrable) {
		this.cobrable = cobrable;
	}
	
	
}
