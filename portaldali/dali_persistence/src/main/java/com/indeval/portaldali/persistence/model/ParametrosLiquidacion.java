/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Este Tabla indica cuando se realealizo la liquidacion a Banxico
 * 
 *
 * 
 * @author 
 * @version 1.0
 */
@Entity
@Table(name = "C_PARAMETROS_LIQUIDACION")
public class ParametrosLiquidacion implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identificador de la tabla de parametros
	 * 
	 */
	@Id
	@Column(name = "ID_CONFIGURACION")
	private BigInteger idConfiguracion;
	
	public BigInteger getIdConfiguracion() {
		return idConfiguracion;
	}

	public void setIdConfiguracion(BigInteger idConfiguracion) {
		this.idConfiguracion = idConfiguracion;
	}

	public int getProcesoFin() {
		return procesoFin;
	}

	public void setProcesoFin(int procesoFin) {
		this.procesoFin = procesoFin;
	}

	/**
	 *  0 indica que no se ha realizado 1 que ya se realizo la liquidacion a Banxico
	 * 
	 */
    @Column(name = "PROCESO_FIN_DIA")
	private int procesoFin;
	
}