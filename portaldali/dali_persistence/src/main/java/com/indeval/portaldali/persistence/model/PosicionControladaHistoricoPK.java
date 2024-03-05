/**
 * Portal DALI
 * 
 * 2H Software SA
 *
 * PosicionNombradaHistorico.java 
 *
 * Creado el: Sep 14, 2008
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PrimaryKey de la tabla T_POSICION_NOMBRADA_H
 * 
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 * 
 */
@Embeddable
public class PosicionControladaHistoricoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * El identificador de la posición asociada al histórico.
	 */
	@Column(name = "ID_POSICION_CONTROLADA")
	private BigInteger idPosicion;
	
	/**
	 * La fecha en que se registró el movimiento en la posición.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CREACION")
	private Date fecha;

	/**
	 * Obtiene el valor del atributo idPosicion
	 * 
	 * @return el valor del atributo idPosicion
	 */
	public BigInteger getIdPosicion() {
		return idPosicion;
	}

	/**
	 * Establece el valor del atributo idPosicion
	 * 
	 * @param idPosicion
	 *            el valor del atributo idPosicion a establecer.
	 */
	public void setIdPosicion(BigInteger idPosicion) {
		this.idPosicion = idPosicion;
	}

	/**
	 * Obtiene el valor del atributo fecha
	 * 
	 * @return el valor del atributo fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Establece el valor del atributo fecha
	 * 
	 * @param fecha
	 *            el valor del atributo fecha a establecer.
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result
				+ ((idPosicion == null) ? 0 : idPosicion.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PosicionControladaHistoricoPK other = (PosicionControladaHistoricoPK) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (idPosicion == null) {
			if (other.idPosicion != null)
				return false;
		} else if (!idPosicion.equals(other.idPosicion))
			return false;
		return true;
	}

}
