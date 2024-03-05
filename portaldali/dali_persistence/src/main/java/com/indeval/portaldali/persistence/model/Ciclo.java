/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Cat&aacute;logo de ciclos de liquidacion.
 *
 * T_CICLO_LIQUIDACION
 *
 * @author rmendoza
 * @version 1.0
 */

@Entity
@Table(name = "T_CICLO_LIQUIDACION")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_CICLO_LIQUIDACION", allocationSize = 1, initialValue = 1)
public class Ciclo implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del ciclo de liquidaci&oacute;n.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_CICLO")
	private BigInteger idCiclo;
	/**
	 * Estado del ciclo de liquidaci&oacute;n.
	 */
	@Column(name = "ESTADO")
	private BigInteger estado;
	/**
	 * Fecha y hora de inicio del ciclo de liquidaci&oacute;n.
	 */
	@Column(name = "FECHA_INICIO")
	private Date fechaInicio;
	/**
	 * Fecha y hora de finalizaci&oacute;n del ciclo de liquidaci&oacute;n.
	 */
	@Column(name = "FECHA_FIN")
	private Date fechaFin;	

	/**
	 * @return the estado
	 */
	public BigInteger getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(BigInteger estado) {
		this.estado = estado;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the idCiclo
	 */
	public BigInteger getIdCiclo() {
		return idCiclo;
	}
	/**
	 * @param idCiclo the idCiclo to set
	 */
	public void setIdCiclo(BigInteger idCiclo) {
		this.idCiclo = idCiclo;
	}
}