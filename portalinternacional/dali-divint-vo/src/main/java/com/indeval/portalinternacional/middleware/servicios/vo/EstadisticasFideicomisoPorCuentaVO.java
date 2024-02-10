/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class EstadisticasFideicomisoPorCuentaVO implements Serializable {
	
	/**
	 * Constante de versi&oacute;n de serializaci&oacute;n  
	 */
	public static final long serialVersionUID = (long)1;

	/**
	 * Cuenta involucrada 
	 */
	private String cuenta;
	
	/**
	 * Porcentaje correspondiente
	 */
	private BigDecimal porcentaje;

	/**
	 * Constructor por omisi&oacute;n 
	 */
	public EstadisticasFideicomisoPorCuentaVO() {
		super();
	}
	
	/**
	 * Constructor parametrizado
	 * @param cuenta
	 * @param porcentaje
	 */
	public EstadisticasFideicomisoPorCuentaVO(String cuenta, BigDecimal porcentaje) {
		super();
		this.cuenta = cuenta;
		this.porcentaje = porcentaje;
	}
	
	/**
	 * @return String
	 */
	public String getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	/**
	 * @param porcentaje
	 */
	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}
}
