/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Esta clase se utiliza para persistir en Base de Datos el cat&aacute;logo de
 * Motivo de Devoluci&oacute;n.
 * 
 * @author Abraham Resendiz
 * @since 1.0
 */
@Entity
@Table(name = "C_MOTIVO_DEVOLUCION")
public class MotivoDevolucion implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del Motivo de Devoluci&oacute;n
	 */
	@Id
	@Column(name = "ID_MOTIVO_DEVOLUCION")
	private BigInteger id;

	/**
	 * Clave del Motivo de Devoluci&oacute;n
	 */
	@Column(name = "CLAVE_MOTIVO_DEVOLUCION")
	private BigInteger claveMotivoDevolucion;

	/**
	 * Descripci&oacute;n del Motivo de Devoluci&oacute;n
	 */
	@Column(name = "DESCRIPCION")
	private String descripcion;

	/**
	 * Fecha de creaci&oacute;n
	 */
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;

	/**
	 * Fecha de la ultima modificaci&oacute;n
	 */
	@Column(name = "FECHA_ULT_MODIFICACION")
	private Date fechaUltimaModificacion;

	/**
	 * Regresa el valor de la propiedad <code>claveMotivoDevolucion</code>.
	 * 
	 * @return claveMotivoDevolucion. Clave del Motivo de Devoluci&oacute;n
	 */
	public BigInteger getClaveMotivoDevolucion() {
		return claveMotivoDevolucion;
	}

	/**
	 * Asigna la propiedad <code>claveMotivoDevolucion</code>.
	 * 
	 * @param claveMotivoDevolucion
	 *            Clave del Motivo de Devoluci&oacute;n
	 */
	public void setClaveMotivoDevolucion(BigInteger claveMotivoDevolucion) {
		this.claveMotivoDevolucion = claveMotivoDevolucion;
	}

	/**
	 * Regresa el valor de la propiedad <code>descripcion</code>.
	 * 
	 * @return descripcion Descripci&oacute;n del Motivo de Devoluci&oacute;n
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Asigna la propiedad <code>descripcion</code>.
	 * 
	 * @param descripcion
	 *            Descripci&oacute;n del Motivo de Devoluci&oacute;n
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Regresa el valor de la propiedad <code>id</code>.
	 * 
	 * @return id. Identificador del Motivo de Devoluci&oacute;n
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * Asigna la propiedad <code>id</code>.
	 * 
	 * @param id
	 *            Identificador del Motivo de Devoluci&oacute;n
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * Regresa el valor de la propiedad <code>fechaCreacion</code>.
	 * 
	 * @return fechaCreacion. Fecha de creaci&oacute;n
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Asigna la propiedad <code>fechaCreacion</code>.
	 * 
	 * @param fechaCreacion
	 *            Fecha de creaci&oacute;n
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Regresa el valor de la propiedad <code>fechaUltimaModificacion</code>.
	 * 
	 * @return fechaUltimaModificacion. Fecha de la ultima modificaci&oacute;n
	 */
	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	/**
	 * Asigna la propiedad <code>fechaUltimaModificacion</code>.
	 * 
	 * @param fechaUltimaModificacion
	 *            Fecha de la ultima modificaci&oacute;n
	 */
	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}
}
