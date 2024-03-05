/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;
import java.util.Set;

public class ValorDTO extends Valor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Set mdinTransacciones;

	public Set getMdinTransacciones() {
		return mdinTransacciones;
	}

	public void setMdinTransacciones(Set mdinTransacciones) {
		this.mdinTransacciones = mdinTransacciones;
	}
	
	
}
