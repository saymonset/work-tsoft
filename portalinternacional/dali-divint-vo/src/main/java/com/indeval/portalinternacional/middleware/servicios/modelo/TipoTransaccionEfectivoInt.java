package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Fernando Pineda
 *
 */
@Entity
@Table(name = "C_TIPO_TRANSACCION")
public class TipoTransaccionEfectivoInt implements Serializable {
	
	@Id
	@Column(name = "ID_TIPO_TRANSACCION")
	private Long idTipoTransaccion;
	
	@Column(name = "TIPO_TRANSACCION")
	private String tipoTransaccion;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	public TipoTransaccionEfectivoInt() {
		super();
	}

	public TipoTransaccionEfectivoInt(Long idTipoTransaccion){
		this.idTipoTransaccion = idTipoTransaccion;
	}
	
	/**
	 * @return the idTipoTransaccion
	 */
	public Long getIdTipoTransaccion() {
		return idTipoTransaccion;
	}
	/**
	 * @param idTipoTransaccion the idTipoTransaccion to set
	 */
	public void setIdTipoTransaccion(Long idTipoTransaccion) {
		this.idTipoTransaccion = idTipoTransaccion;
	}
	/**
	 * @return the tipoTransaccion
	 */
	public String getTipoTransaccion() {
		return tipoTransaccion;
	}
	/**
	 * @param tipoTransaccion the tipoTransaccion to set
	 */
	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
