/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigInteger;

import com.indeval.portaldali.persistence.modelo.Cupon;
import com.indeval.portaldali.persistence.modelo.Depositos;
import com.indeval.portaldali.persistence.modelo.Retiros;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 */
public class MovimientoFideicomisoVO implements Serializable {
	
	/**
	 * Constante de versi&oacute;n de serializaci&oacute;n 
	 */
	public static final long serialVersionUID = (long)1;
	
	/**
	 * Cupon 
	 */
	private Cupon cupon;
	
	/**
	 * Integer 
	 */
	private BigInteger cantidadOperada;
	
	/**
	 * String 
	 */
	private String tipoMovimiento;
	
	/**
	 * String 
	 */
	private String usuario;

	/**
	 * String 
	 */
	private String folioConfirmacion;
	
	/**
	 * Constructor por omisi&oacute;n 
	 */
	public MovimientoFideicomisoVO() {
		super();
	}

	/**
	 * Constructor sobrecargado
	 * @param deposito
	 * @param retiro
	 */
	public MovimientoFideicomisoVO(Depositos deposito, Retiros retiro) {
		super();
		if (deposito != null) {
			this.cupon = deposito.getCupon();
			this.cantidadOperada = deposito.getNumeroTitulos();
			this.folioConfirmacion = deposito.getFolioDeposito();
			this.tipoMovimiento = "D";
			this.usuario = "DIVINT";
		}
		else if (retiro != null) {
			this.cupon = retiro.getCupon();
			this.cantidadOperada = retiro.getNumeroTitulos();
			this.folioConfirmacion = retiro.getFolioRetiro();
			this.tipoMovimiento = "R";
			this.usuario = "DIVINT";
		}
	}
	
	/**
	 * @return Cupon
	 */
	public Cupon getCupon() {
		return cupon;
	}

	/**
	 * @param cupon
	 */
	public void setCupon(Cupon cupon) {
		this.cupon = cupon;
	}

	/**
	 * @return Integer
	 */
	public BigInteger getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * @param cantidadOperada
	 */
	public void setCantidadOperada(BigInteger cantidadOperada) {
		this.cantidadOperada = cantidadOperada;
	}

	/**
	 * @return String
	 */
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	/**
	 * @param tipoMovimiento
	 */
	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	/**
	 * @return String
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return String
	 */
	public String getFolioConfirmacion() {
		return folioConfirmacion;
	}

	/**
	 * @param folioConfirmacion
	 */
	public void setFolioConfirmacion(String folioConfirmacion) {
		this.folioConfirmacion = folioConfirmacion;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ("[ cupon: "+cupon+", cantidadOperada: "+cantidadOperada+", "+
				"folioConfirmacion: "+folioConfirmacion+", tipoMovimiento: "+tipoMovimiento+", "+
				"usuario: "+usuario+" ]");
	}
}
