package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_FACTOR_SIMULACION_MAV")
public class FactorSimulacionMav  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1686098996978132547L;
	
	private Long idDerechoCapital;
	private Float proporcion;
	private Float fee;
	private Float porcentajeRetencion;
	
	@Id	
	@Column(name = "ID_DERECHO_CAPITAL", unique = true, nullable = false)
	public Long getIdDerechoCapital() {
		return idDerechoCapital;
	}
	@Column(name = "PROPORCION", unique = false, nullable = false)
	public Float getProporcion() {
		return proporcion;
	}
	@Column(name = "FEE", unique = false, nullable = true)
	public Float getFee() {
		return fee;
	}
	@Column(name = "PORCENTAJE_RETENCION", unique = false, nullable = true)
	public Float getPorcentajeRetencion() {
		return porcentajeRetencion;
	}
	public void setIdDerechoCapital(Long idDerechoCapital) {
		this.idDerechoCapital = idDerechoCapital;
	}
	public void setProporcion(Float proporcion) {
		this.proporcion = proporcion;
	}
	public void setFee(Float fee) {
		this.fee = fee;
	}
	public void setPorcentajeRetencion(Float porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}
	
}
