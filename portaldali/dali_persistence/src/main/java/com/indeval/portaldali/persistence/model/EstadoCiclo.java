/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

/**
 * Contiene los estados posibles que puede tomar un ciclo de liquidaci&oacute;n.
 *
 * @author rmendoza
 * @version 1.0
 */
public enum EstadoCiclo {
	
	/**
	 * Estado de &eacute;xito de un ciclo de liquidaci&oacute;n.
	 */
	INICIALIZADO("INICIALIZADO", 0L),

	/**
	 * Estado de &eacute;xito de un ciclo de liquidaci&oacute;n.
	 */
	EXITOSO("EXITOSO", 1L);

	private EstadoCiclo(String nombre, long valor) {
		this.nombre = nombre;
		this.valor = valor;
	}
	
	private long valor;
	private String nombre;
	
	/**
	 * @return the valor
	 */
	public long getValor() {
		return valor;
	}
	
	/**
	 * @param valor the valor to set
	 */
	public void setValor(long valor) {
		this.valor = valor;
	}
	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
