/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para transferir los datos de un grupo de divisa que agrupa registros contables.
 * Objeto de transferencia de datos  para transportar un conjunto de registros contables 
 * agrupados por bóveda.
 * @author Emigdio
 * @version 1.0
 */
public class EstadoCuentaEfectivoPorDivisaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Conjunto de objetos de boveda que representan un conjunto de registros contables
	 * agrupados por bóveda que pertenecen a la divisa que representa este DTO
	 */
	private List<DetalleEstadoCuentaSaldoPorBovedaDTO>registrosContablesPorBoveda  = new ArrayList<DetalleEstadoCuentaSaldoPorBovedaDTO>();

	private DivisaDTO divisa = null;
	/**
	 * Obtiene el campo registrosContablesPorBoveda
	 * @return  registrosContablesPorBoveda
	 */
	public List<DetalleEstadoCuentaSaldoPorBovedaDTO> getRegistrosContablesPorBoveda() {
		return registrosContablesPorBoveda;
	}

	/**
	 * Asigna el valor del campo registrosContablesPorBoveda
	 * @param registrosContablesPorBoveda el valor de registrosContablesPorBoveda a asignar
	 */
	public void setRegistrosContablesPorBoveda(
			List<DetalleEstadoCuentaSaldoPorBovedaDTO> registrosContablesPorBoveda) {
		this.registrosContablesPorBoveda = registrosContablesPorBoveda;
	}

	/**
	 * Obtiene el campo divisa
	 * @return  divisa
	 */
	public DivisaDTO getDivisa() {
		return divisa;
	}

	/**
	 * Asigna el valor del campo divisa
	 * @param divisa el valor de divisa a asignar
	 */
	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
	}
	

}
