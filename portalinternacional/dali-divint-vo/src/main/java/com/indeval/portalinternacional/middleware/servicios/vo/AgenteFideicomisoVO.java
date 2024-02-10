/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class AgenteFideicomisoVO implements Serializable {
	
	/**
	 * Constante de versi&oacute;n de serializaci&oacute;n 
	 */
	public static final long serialVersionUID = (long)1;
	
	/**
	 * ID de la instituci&oacute;n 
	 */
	private String id;
	
	/**
	 * Folio de la instituci&oacute;n 
	 */
	private String folio;
	
	/**
	 * Cuenta correspondiente 
	 */
	private String cuenta;
	
	/**
	 * Nombre corto de la instituci&oacute;n 
	 */
	private String nombreCorto;
	
	/**
	 * Posici&oacute;n disponible actual 
	 */
	private BigInteger posicionActual;
	
	/**
	 * Valuaci&oacute;n de mercado actual 
	 */
	private BigInteger valuacionMercado;

	/**
	 * Valuaci&oacute;n nominal 
	 */
	private BigDecimal valuacionNominal;
	
	/**
	 * String 
	 */
	private String claveAgenteCuenta;
	
	/**
	 * String 
	 */
	private String prefijoSufijoCuenta;
	
	/**
	 * Constructor por omisi&oacute;n
	 */
	public AgenteFideicomisoVO() {
		super();
	}
	
	/**
	 * Constructor parametrizado
	 * @param id
	 * @param folio
	 * @param cuenta
	 * @param nombreCorto
	 * @param posicionActual
	 * @param valuacionMercado
	 */
	public AgenteFideicomisoVO(String id, String folio, String cuenta, String nombreCorto, BigInteger posicionActual, BigInteger valuacionMercado) {
		super();
		this.id = id;
		this.folio = folio;
		this.cuenta = cuenta;
		this.nombreCorto = nombreCorto;
		this.posicionActual = posicionActual;
		this.valuacionMercado = valuacionMercado;
		this.claveAgenteCuenta = id + folio + cuenta;
		if (cuenta.endsWith("97")) this.prefijoSufijoCuenta = "97";
		else if (cuenta.endsWith("98")) this.prefijoSufijoCuenta = "98";
		else if (cuenta.startsWith("54")) this.prefijoSufijoCuenta = "54";
		else this.prefijoSufijoCuenta = null;
	}
	
	/**
	 * @return String
	 */
	public String getClaveAgenteCuenta() {
		return (claveAgenteCuenta);
	}
	
	/**
	 * @return String
	 */
	public String getPrefijoSufijoCuenta() {
		return (prefijoSufijoCuenta);
	}
	
	
	/**
	 * @param agenteFideicomisoVO
	 * @return int
	 */
	public int compareTo(AgenteFideicomisoVO agenteFideicomisoVO) {
		return (this.getClaveAgenteCuenta().compareTo(agenteFideicomisoVO.getClaveAgenteCuenta()));
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return (this.getClaveAgenteCuenta());
	}
	
	/**
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return String
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 */
	public void setFolio(String folio) {
		this.folio = folio;
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
	 * @return String
	 */
	public String getNombreCorto() {
		return nombreCorto;
	}

	/**
	 * @param nombreCorto
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getPosicionActual() {
		return posicionActual;
	}

	/**
	 * @param posicionActual
	 */
	public void setPosicionActual(BigInteger posicionActual) {
		this.posicionActual = posicionActual;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getValuacionMercado() {
		return valuacionMercado;
	}

	/**
	 * @param valuacionMercado
	 */
	public void setValuacionMercado(BigInteger valuacionMercado) {
		this.valuacionMercado = valuacionMercado;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getValuacionNominal() {
		return valuacionNominal;
	}

	/**
	 * @param valuacionNominal
	 */
	public void setValuacionNominal(BigDecimal valuacionNominal) {
		this.valuacionNominal = valuacionNominal;
	}
}
