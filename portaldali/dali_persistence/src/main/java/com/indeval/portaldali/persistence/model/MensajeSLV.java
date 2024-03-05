/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Clase que se utiliza para persistir la tabla T_BITACORA_NOTIFICACION_SLV.
 * 
 * @author apalacios
 * @version 1.0
 */
@Entity
@Table(name = "T_BITACORA_NOTIFICACION_SLV")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_BIT_NOTIFICACION_SLV", allocationSize = 1, initialValue = 1)
@NamedQuery(name = "MensajeSLV.getIdInstruccionByFolioAndTipoInstruccion", query = "FROM MensajeSLV AS a WHERE a.folioInstLiquidacion =:folioLiquidacion AND a.idTipoInstruccion =:tipoInstruccion")
public class MensajeSLV implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_BITACORA_NOTIFICACION_SLV")
	private BigInteger id;

	/**
	 * Instrucci&oacute;n en Efectivo
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTRUCCION_EFECTIVO", updatable = true, insertable = true)
	private InstruccionEfectivo instruccionEfectivo;

	/**
	 * Folio de la instrucción.
	 */
	@Column(name = "FOLIO_INSTRUCCION")
	private BigInteger folioInstruccion;

	/**
	 * Folio instrucción de la liquidación.
	 */
	@Column(name = "FOLIO_INST_LIQUIDACION")
	private BigInteger folioInstLiquidacion;

	/**
	 * Tipo de instruccion de efectivo.
	 */
	@Column(name = "ID_TIPO_INSTRUCCION")
	private BigInteger idTipoInstruccion;

	/**
	 * Mensaje al preliquidador.
	 */
	@Lob
	@Column(name = "MENSAJE")
	private String mensaje;

	/**
	 * Fecha recepción.
	 */
	@Column(name = "FECHA_ENVIO")
	private Date fechaEnvio;

	/**
	 * Regresa el valor de la propiedad <code>id</code>.
	 * 
	 * @return id
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * Asigna la propiedad <code>id</code>.
	 * 
	 * @param id
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * Regresa el valor de la propiedad <code>folioInstruccion</code>.
	 * 
	 * @return folioInstruccion
	 */
	public BigInteger getFolioInstruccion() {
		return folioInstruccion;
	}

	/**
	 * Asigna la propiedad <code>folioInstruccion</code>.
	 * 
	 * @param folioInstruccion
	 */
	public void setFolioInstruccion(BigInteger folioInstruccion) {
		this.folioInstruccion = folioInstruccion;
	}

	/**
	 * Regresa el valor de la propiedad <code>folioInstLiquidacion</code>.
	 * 
	 * @return folioInstLiquidacion
	 */
	public BigInteger getFolioInstLiquidacion() {
		return folioInstLiquidacion;
	}

	/**
	 * Asigna la propiedad <code>folioInstLiquidacion</code>.
	 * 
	 * @param folioInstLiquidacion
	 */
	public void setFolioInstLiquidacion(BigInteger folioInstLiquidacion) {
		this.folioInstLiquidacion = folioInstLiquidacion;
	}

	/**
	 * Regresa el valor de la propiedad <code>idTipoInstruccion</code>.
	 * 
	 * @return idTipoInstruccion
	 */
	public BigInteger getIdTipoInstruccion() {
		return idTipoInstruccion;
	}

	/**
	 * Asigna la propiedad <code>idTipoInstruccion</code>.
	 * 
	 * @param idTipoInstruccion
	 */
	public void setIdTipoInstruccion(BigInteger idTipoInstruccion) {
		this.idTipoInstruccion = idTipoInstruccion;
	}

	/**
	 * Regresa el valor de la propiedad <code>mensaje</code>.
	 * 
	 * @return mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Asigna la propiedad <code>mensaje</code>.
	 * 
	 * @param mensaje
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * Regresa el valor de la propiedad <code>fechaEnvio</code>.
	 * 
	 * @return fechaEnvio
	 */
	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	/**
	 * Asigna la propiedad <code>fechaEnvio</code>.
	 * 
	 * @param fechaEnvio
	 */
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	/**
	 * Regresa el valor de la propiedad <code>instruccionEfectivo</code>.
	 * 
	 * @return instruccionEfectivo
	 */
	public InstruccionEfectivo getInstruccionEfectivo() {
		return instruccionEfectivo;
	}

	/**
	 * Asigna la propiedad <code>instruccionEfectivo</code>.
	 * 
	 * @param instruccionEfectivo
	 */
	public void setInstruccionEfectivo(InstruccionEfectivo instruccionEfectivo) {
		this.instruccionEfectivo = instruccionEfectivo;
	}

}