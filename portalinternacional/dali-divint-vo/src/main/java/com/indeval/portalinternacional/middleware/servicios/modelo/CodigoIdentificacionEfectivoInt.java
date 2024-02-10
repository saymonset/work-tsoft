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
@Table(name = "C_CODIGO_IDENTIFICACION")
public class CodigoIdentificacionEfectivoInt implements Serializable {
	
	@Id
	@Column(name = "ID_CODIGO_IDENTIFICACION")
	private Long idCodigoIdentificacion;
	
	@Column(name = "CODIGO_IDENTIFICACION")
	private String codigoIdentificacion;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	public CodigoIdentificacionEfectivoInt() {
		super();
	}
	
	public CodigoIdentificacionEfectivoInt(Long idCodigoIdentificacion){
		this.idCodigoIdentificacion = idCodigoIdentificacion;
	}
	
	/**
	 * @return the idCodigoIdentificacion
	 */
	public Long getIdCodigoIdentificacion() {
		return idCodigoIdentificacion;
	}
	/**
	 * @param idCodigoIdentificacion the idCodigoIdentificacion to set
	 */
	public void setIdCodigoIdentificacion(Long idCodigoIdentificacion) {
		this.idCodigoIdentificacion = idCodigoIdentificacion;
	}
	/**
	 * @return the codigoIdentificacion
	 */
	public String getCodigoIdentificacion() {
		return codigoIdentificacion;
	}
	/**
	 * @param codigoIdentificacion the codigoIdentificacion to set
	 */
	public void setCodigoIdentificacion(String codigoIdentificacion) {
		this.codigoIdentificacion = codigoIdentificacion;
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
