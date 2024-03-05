/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 * 
 * Creado el: 31/12/2007
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object que representa el detalle del estado de cuenta por bóveda y divisa.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 */
public class DetalleEstadoCuentaSaldoPorBovedaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** Los registros que contienen el detalle de los registros contables de saldos nombrados de una boveda y una divisa */
	private List<RegistroContableSaldoControladaDTO>registrosContablesControladas = new ArrayList<RegistroContableSaldoControladaDTO>();
	
	/** Los registros que contienen el detalle de los registros contables de saldos controlados de una boveda y una divisa */
	private List<RegistroContableSaldoNombradaDTO>registrosContablesNombradas = new ArrayList<RegistroContableSaldoNombradaDTO>();
	
	/** Los datos de la bóveda por la cual se agrupa este detalle del estado de cuenta */
	private BovedaDTO boveda = null;
	
	/** El estado inicial del saldo en el periodo de consulta*/
	private EstadoSaldoDTO estadoSaldoInicial;
	
	/** El estado final del saldo en el periodo de consulta */
	private EstadoSaldoDTO estadoSaldoFinal;
	
	/**
	 * Obtiene el valor del atributo registrosContablesControladas
	 * 
	 * @return el valor del atributo registrosContablesControladas
	 */
	public List<RegistroContableSaldoControladaDTO> getRegistrosContablesControladas() {
		return registrosContablesControladas;
	}

	/**
	 * Establece el valor del atributo registrosContablesControladas
	 *
	 * @param registrosContablesControladas el valor del atributo registrosContablesControladas a establecer
	 */
	public void setRegistrosContablesControladas(List<RegistroContableSaldoControladaDTO> registrosContablesControladas) {
		this.registrosContablesControladas = registrosContablesControladas;
	}

	/**
	 * Obtiene el valor del atributo registrosContablesNombradas
	 * 
	 * @return el valor del atributo registrosContablesNombradas
	 */
	public List<RegistroContableSaldoNombradaDTO> getRegistrosContablesNombradas() {
		return registrosContablesNombradas;
	}

	/**
	 * Establece el valor del atributo registrosContablesNombradas
	 *
	 * @param registrosContablesNombradas el valor del atributo registrosContablesNombradas a establecer
	 */
	public void setRegistrosContablesNombradas(List<RegistroContableSaldoNombradaDTO> registrosContablesNombradas) {
		this.registrosContablesNombradas = registrosContablesNombradas;
	}

	/**
	 * Obtiene el valor del atributo estadoSaldoInicial
	 * 
	 * @return el valor del atributo estadoSaldoInicial
	 */
	public EstadoSaldoDTO getEstadoSaldoInicial() {
		return estadoSaldoInicial;
	}

	/**
	 * Establece el valor del atributo estadoSaldoInicial
	 *
	 * @param estadoSaldoInicial el valor del atributo estadoSaldoInicial a establecer
	 */
	public void setEstadoSaldoInicial(EstadoSaldoDTO estadoSaldoInicial) {
		this.estadoSaldoInicial = estadoSaldoInicial;
	}

	/**
	 * Obtiene el valor del atributo estadoSaldoFinal
	 * 
	 * @return el valor del atributo estadoSaldoFinal
	 */
	public EstadoSaldoDTO getEstadoSaldoFinal() {
		return estadoSaldoFinal;
	}

	/**
	 * Establece el valor del atributo estadoSaldoFinal
	 *
	 * @param estadoSaldoFinal el valor del atributo estadoSaldoFinal a establecer
	 */
	public void setEstadoSaldoFinal(EstadoSaldoDTO estadoSaldoFinal) {
		this.estadoSaldoFinal = estadoSaldoFinal;
	}

	/**
	 * Obtiene el campo boveda
	 * @return  boveda
	 */
	public BovedaDTO getBoveda() {
		return boveda;
	}

	/**
	 * Asigna el valor del campo boveda
	 * @param boveda el valor de boveda a asignar
	 */
	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}
}
