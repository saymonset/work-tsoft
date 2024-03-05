/**
 * 
 * Portal DALI
 *
 * 
 * 
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Data Transfer Object que representa un elemento 
 * 
 * @author 
 * 
 */
public class ParametrosLiquidacionDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	 
	
		public int getProcesoFin() {
		return procesoFin;
	}

	public void setProcesoFin(int procesoFin) {
		this.procesoFin = procesoFin;
	}

		public BigInteger getIdConfiguracion() {
		return idConfiguracion;
	}

	public void setIdConfiguracion(BigInteger idConfiguracion) {
		this.idConfiguracion = idConfiguracion;
	}

		private BigInteger idConfiguracion;
	
		private int procesoFin;

}
