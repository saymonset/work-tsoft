/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 20, 2007
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object que representa el detalle de un estado de cuenta de posición
 * agrupado por bóveda.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 *
 */
public class DetalleEstadoCuentaPosicionPorBovedaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** Los datos de la bóveda por la cual se agrupa este detalle del estado de cuenta */
	private BovedaDTO boveda;
	
	/** El estado inicial de la posición en el periodo de consulta*/
	private EstadoPosicionDTO estadoPosicionInicial;
	
	/** El estado final de la posición en el periodo de consulta */
	private EstadoPosicionDTO estadoPosicionFinal;
	
	/** Los registros que contienen el detalle de los registros contables de posiciones nombradas de una boveda y una emisión */
	private List<RegistroContablePosicionNombradaDTO> registrosContablesNombradas = new ArrayList<RegistroContablePosicionNombradaDTO>();
	
	/** Los registros que contienen el detalle de los registro contables de posiciones controladas de una bóveda y una emisión */
	private List<RegistroContablePosicionControladaDTO> registrosContablesControladas = new ArrayList<RegistroContablePosicionControladaDTO>();
	
	/**
	 * Obtiene el valor del atributo registrosContablesControladas
	 * 
	 * @return el valor del atributo registrosContablesControladas
	 */
	public List<RegistroContablePosicionControladaDTO> getRegistrosContablesControladas() {
		return registrosContablesControladas;
	}

	/**
	 * Establece el valor del atributo registrosContablesControladas
	 *
	 * @param registrosContablesControladas el valor del atributo registrosContablesControladas a establecer
	 */
	public void setRegistrosContablesControladas(List<RegistroContablePosicionControladaDTO> registrosContablesControladas) {
		this.registrosContablesControladas = registrosContablesControladas;
	}

	/**
	 * Obtiene el campo registrosContables
	 * @return  registrosContables
	 */
	public List<RegistroContablePosicionNombradaDTO> getRegistrosContablesNombradas() {
		return registrosContablesNombradas;
	}

	/**
	 * Asigna el valor del campo registrosContables
	 * @param registrosContables el valor de registrosContables a asignar
	 */
	public void setRegistrosContablesNombradas(
			List<RegistroContablePosicionNombradaDTO> registrosContablesNombradas) {
		this.registrosContablesNombradas = registrosContablesNombradas;
	}

	/**
	 * Obtiene el valor del atributo boveda
	 *
	 * @return el valor del atributo boveda
	 */
	public BovedaDTO getBoveda() {
		return boveda;
	}

	/**
	 * Establece el valor del atributo boveda
	 *
	 * @param boveda el valor del atributo boveda a establecer.
	 */
	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}

	/**
	 * Obtiene el valor del atributo estadoPosicionInicial
	 *
	 * @return el valor del atributo estadoPosicionInicial
	 */
	public EstadoPosicionDTO getEstadoPosicionInicial() {
		return estadoPosicionInicial;
	}

	/**
	 * Establece el valor del atributo estadoPosicionInicial
	 *
	 * @param estadoPosicionInicial el valor del atributo estadoPosicionInicial a establecer.
	 */
	public void setEstadoPosicionInicial(EstadoPosicionDTO estadoPosicionInicial) {
		this.estadoPosicionInicial = estadoPosicionInicial;
	}

	/**
	 * Obtiene el valor del atributo estadoPosicionFinal
	 *
	 * @return el valor del atributo estadoPosicionFinal
	 */
	public EstadoPosicionDTO getEstadoPosicionFinal() {
		return estadoPosicionFinal;
	}

	/**
	 * Establece el valor del atributo estadoPosicionFinal
	 *
	 * @param estadoPosicionFinal el valor del atributo estadoPosicionFinal a establecer.
	 */
	public void setEstadoPosicionFinal(EstadoPosicionDTO estadoPosicionFinal) {
		this.estadoPosicionFinal = estadoPosicionFinal;
	}
	
}
