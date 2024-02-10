package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.indeval.portaldali.persistence.modelo.CuentaNombrada;

@Entity
@Table(name="T_POSICIONES_LIQUIDACION")
public class PosicionLiquidacion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8306584257039204298L;
	
	@Id
	@Column(name = "ID_POSICION_LIQUIDACION", unique = true, nullable = false)
	private Long idPosicionLiquidacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CUENTA", nullable = false)
	private CuentaNombrada cuenta;
	
	@Column(name = "ID_DERECHO", unique = false, nullable = false)
	private Long idDerechoCapital;
	
	@Column(name = "SALDO", unique = false, nullable = false)
	private Long saldo;
		
	@Column(name = "ID_EMISION", nullable = false)
	private Long idEmision;
	
	@Column(name = "ID_TIPO_DERECHO", unique = false, nullable = false)
	private Integer idTipoDerecho;

	public Long getIdPosicionLiquidacion() {
		return idPosicionLiquidacion;
	}

	public CuentaNombrada getCuenta() {
		return cuenta;
	}

	public Long getIdDerechoCapital() {
		return idDerechoCapital;
	}

	public Long getSaldo() {
		return saldo;
	}

	public void setIdPosicionLiquidacion(Long idPosicionLiquidacion) {
		this.idPosicionLiquidacion = idPosicionLiquidacion;
	}

	public void setCuenta(CuentaNombrada cuenta) {
		this.cuenta = cuenta;
	}

	public void setIdDerechoCapital(Long idDerechoCapital) {
		this.idDerechoCapital = idDerechoCapital;
	}

	public void setSaldo(Long saldo) {
		this.saldo = saldo;
	}

	public Integer getIdTipoDerecho() {
		return idTipoDerecho;
	}

	public void setIdTipoDerecho(Integer idTipoDerecho) {
		this.idTipoDerecho = idTipoDerecho;
	}

	public Long getIdEmision() {
		return idEmision;
	}

	public void setIdEmision(Long idEmision) {
		this.idEmision = idEmision;
	}
}
