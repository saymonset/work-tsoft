/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "C_TIPO_CUENTA_EFECTIVO")
public class TipoCuentaEfectivoInt {
	private Long idTipoCuentaEfectivo;
	private String nombreCorto;
	private String descripcion;
	
	
	/**
	 * @return the idTipoCuentaEfectivo
	 */
	@Id	
	@Column(name = "ID_TIPO_CUENTA_EFECTIVO")
	public Long getIdTipoCuentaEfectivo() {
		return idTipoCuentaEfectivo;
	}

	/**
	 * @param idTipoCuentaEfectivo the idTipoCuentaEfectivo to set
	 */
	public void setIdTipoCuentaEfectivo(Long idTipoCuentaEfectivo) {
		this.idTipoCuentaEfectivo = idTipoCuentaEfectivo;
	}

	/**
	 * @return the nombreCorto
	 */
	@Column(name = "NOMBRE_CORTO")
	public String getNombreCorto() {
		return nombreCorto;
	}

	/**
	 * @param nombreCorto the nombreCorto to set
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	/**
	 * @return the descripcion
	 */
	@Column(name = "DESCRIPCION")
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
