/**
 * Portal DALI
 * 
 * 2H Software SA
 *
 * EmisionPendiente.java 
 *
 * Creado el: Sep 22, 2008
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Mapeo de la tabla "T_EMISION_PENDIENTE".
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
@Entity
@Table(name = "T_EMISION_PENDIENTE")
public class EmisionPendiente implements Serializable {

	private static final long serialVersionUID = 1L;

	/** El identificador de la emisión pendiente */
	@Id
	@Column(name = "ID_EMISION_PENDIENTE")
	private BigInteger idEmisionPendiente;

	/** El identificador del estado de la emisión */
	@Column(name = "ID_ESTADO_EMISION")
	private BigInteger idEstadoEmision;

	/** El estado de la emisión */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTADO_EMISION", updatable = false, insertable = false)
	private EstadoEmision estadoEmision;

	/** El tipo de valor de la emisión */
	@Column(name = "TV")
	private String tv;

	/** El tipo de valor de la emisión */
	@Column(name = "EMISORA")
	private String emisora;

	/** El tipo de valor de la emisión */
	@Column(name = "SERIE")
	private String serie;

	/** El tipo de valor de la emisión */
	@Column(name = "CUPON")
	private String cupon;

	/** La fecha de la última modificación */
	@Column(name = "FECHA_ULTIMA_MODIFICACION")
	private Date fechaUltimaModificacion;

	/**
	 * Obtiene el valor del atributo idEmisionPendiente
	 * 
	 * @return el valor del atributo idEmisionPendiente
	 */
	public BigInteger getIdEmisionPendiente() {
		return idEmisionPendiente;
	}

	/**
	 * Establece el valor del atributo idEmisionPendiente
	 * 
	 * @param idEmisionPendiente
	 *            el valor del atributo idEmisionPendiente a establecer.
	 */
	public void setIdEmisionPendiente(BigInteger idEmisionPendiente) {
		this.idEmisionPendiente = idEmisionPendiente;
	}

	/**
	 * Obtiene el valor del atributo idEstadoEmision
	 * 
	 * @return el valor del atributo idEstadoEmision
	 */
	public BigInteger getIdEstadoEmision() {
		return idEstadoEmision;
	}

	/**
	 * Establece el valor del atributo idEstadoEmision
	 * 
	 * @param idEstadoEmision
	 *            el valor del atributo idEstadoEmision a establecer.
	 */
	public void setIdEstadoEmision(BigInteger idEstadoEmision) {
		this.idEstadoEmision = idEstadoEmision;
	}

	/**
	 * Obtiene el valor del atributo estadoEmision
	 * 
	 * @return el valor del atributo estadoEmision
	 */
	public EstadoEmision getEstadoEmision() {
		return estadoEmision;
	}

	/**
	 * Establece el valor del atributo estadoEmision
	 * 
	 * @param estadoEmision
	 *            el valor del atributo estadoEmision a establecer.
	 */
	public void setEstadoEmision(EstadoEmision estadoEmision) {
		this.estadoEmision = estadoEmision;
	}

	/**
	 * Obtiene el valor del atributo tv
	 * 
	 * @return el valor del atributo tv
	 */
	public String getTv() {
		return tv;
	}

	/**
	 * Establece el valor del atributo tv
	 * 
	 * @param tv
	 *            el valor del atributo tv a establecer.
	 */
	public void setTv(String tv) {
		this.tv = tv;
	}

	/**
	 * Obtiene el valor del atributo emisora
	 * 
	 * @return el valor del atributo emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * Establece el valor del atributo emisora
	 * 
	 * @param emisora
	 *            el valor del atributo emisora a establecer.
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * Obtiene el valor del atributo serie
	 * 
	 * @return el valor del atributo serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * Establece el valor del atributo serie
	 * 
	 * @param serie
	 *            el valor del atributo serie a establecer.
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * Obtiene el valor del atributo cupon
	 * 
	 * @return el valor del atributo cupon
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * Establece el valor del atributo cupon
	 * 
	 * @param cupon
	 *            el valor del atributo cupon a establecer.
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * Obtiene el valor del atributo fechaUltimaModificacion
	 * 
	 * @return el valor del atributo fechaUltimaModificacion
	 */
	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	/**
	 * Establece el valor del atributo fechaUltimaModificacion
	 * 
	 * @param fechaUltimaModificacion
	 *            el valor del atributo fechaUltimaModificacion a establecer.
	 */
	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

}
