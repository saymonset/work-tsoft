package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ConsultaStatementsTotalesVO implements Serializable {

	private static final long serialVersionUID = -1L;
	/** Total de las posiciones */
	private Long posicion;
	/** Total de los dividendos */
	private BigDecimal dividendo;
	/** Total de los impuestos */
	private BigDecimal impuestos;
	/** Total de los dividendos netos */
	private BigDecimal dividendoNeto;

	/** Constructor por omision */
	public ConsultaStatementsTotalesVO() {
		super();
		posicion = 0l;
		dividendo = BigDecimal.ZERO;
		impuestos = BigDecimal.ZERO;
		dividendoNeto = BigDecimal.ZERO;
	}

	/** Constructor con los valores */
	public ConsultaStatementsTotalesVO(Long posicion, BigDecimal dividendo,
			BigDecimal impuestos, BigDecimal dividendoNeto) {
		this();
		if (posicion != null) {
			this.posicion = posicion;
		}
		if (dividendo != null) {
			this.dividendo = dividendo;
		}
		if (impuestos != null) {
			this.impuestos = impuestos;
		}
		if (dividendoNeto != null) {
			this.dividendoNeto = dividendoNeto;
		}
	}

	/**
	 * Total de las posiciones
	 * @return the posicion
	 */
	public Long getPosicion() {
		return posicion;
	}

	/**
	 * Total de las posiciones
	 * @param posicion the posicion to set
	 */
	public void setPosicion(Long posicion) {
		this.posicion = posicion;
	}

	/**
	 * Total de los dividendos
	 * @return the dividendo
	 */
	public BigDecimal getDividendo() {
		return dividendo;
	}

	/**
	 * Total de los dividendos
	 * @param dividendo the dividendo to set
	 */
	public void setDividendo(BigDecimal dividendo) {
		this.dividendo = dividendo;
	}

	/**
	 * Total de los impuestos
	 * @return the impuestos
	 */
	public BigDecimal getImpuestos() {
		return impuestos;
	}

	/**
	 * Total de los impuestos
	 * @param impuestos the impuestos to set
	 */
	public void setImpuestos(BigDecimal impuestos) {
		this.impuestos = impuestos;
	}

	/**
	 * Total de los dividendos netos
	 * @return the dividendoNeto
	 */
	public BigDecimal getDividendoNeto() {
		return dividendoNeto;
	}

	/**
	 * Total de los dividendos netos
	 * @param dividendoNeto the dividendoNeto to set
	 */
	public void setDividendoNeto(BigDecimal dividendoNeto) {
		this.dividendoNeto = dividendoNeto;
	}

	@Override
	public String toString() {
		ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("posicion", posicion).append("dividendo", dividendo).append("impuestos", impuestos).append("dividendoNeto", dividendoNeto);
		return toStringBuilder.toString();
	}
}
