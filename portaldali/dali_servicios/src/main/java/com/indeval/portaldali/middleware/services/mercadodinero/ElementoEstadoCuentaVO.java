/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ElementoEstadoCuentaVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private AgenteVO agente;

	private AgenteVO contraparte;

	private EmisionVO claveValor;

	private BigDecimal saldoInicial;

	private BigInteger entradas;

	private BigInteger salidas;

	private BigDecimal saldoFinal;

	private BigInteger folioControl;

	private Integer plazo;

	private BigDecimal tasa;

	private String tasaVariable;

	private BigDecimal precio;

	private BigDecimal monto;

	private String origen;

	private String aplicacion;

	private String tipoOperacion;

	/**
	 * Constructor
	 */
	public ElementoEstadoCuentaVO() {

		this.setSaldoInicial(BIG_DECIMAL_ZERO);
		this.setEntradas(BIG_INTEGER_ZERO);
		this.setSalidas(BIG_INTEGER_ZERO);
		this.setSaldoFinal(BIG_DECIMAL_ZERO);

	}

	/**
	 * @return
	 */
	public AgenteVO getAgente() {
		return agente;
	}

	/**
	 * @param agente
	 */
	public void setAgente(AgenteVO agente) {
		this.agente = agente;
	}

	/**
	 * @return
	 */
	public String getAplicacion() {
		return aplicacion;
	}

	/**
	 * @param aplicacion
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @return
	 */
	public EmisionVO getClaveValor() {
		return claveValor;
	}

	/**
	 * @param claveValor
	 */
	public void setClaveValor(EmisionVO claveValor) {
		this.claveValor = claveValor;
	}

	/**
	 * @return
	 */
	public AgenteVO getContraparte() {
		return contraparte;
	}

	/**
	 * @param contraparte
	 */
	public void setContraparte(AgenteVO contraparte) {
		this.contraparte = contraparte;
	}

	/**
	 * @return
	 */
	public BigInteger getEntradas() {
		return entradas;
	}

	/**
	 * @param entradas
	 */
	public void setEntradas(BigInteger entradas) {
		this.entradas = entradas;
	}

	/**
	 * @return
	 */
	public BigInteger getFolioControl() {
		return folioControl;
	}

	/**
	 * @param folioControl
	 */
	public void setFolioControl(BigInteger folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * @return
	 */
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * @param monto
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	/**
	 * @return
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return
	 */
	public Integer getPlazo() {
		return plazo;
	}

	/**
	 * @param plazo
	 */
	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
	}

	/**
	 * @return
	 */
	public BigDecimal getPrecio() {
		return precio;
	}

	/**
	 * @param precio
	 */
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	/**
	 * @return
	 */
	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}

	/**
	 * @param saldoFinal
	 */
	public void setSaldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
	}

	/**
	 * @return
	 */
	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	/**
	 * @param saldoInicial
	 */
	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	/**
	 * @return
	 */
	public BigInteger getSalidas() {
		return salidas;
	}

	/**
	 * @param salidas
	 */
	public void setSalidas(BigInteger salidas) {
		this.salidas = salidas;
	}

	/**
	 * @return
	 */
	public BigDecimal getTasa() {
		return tasa;
	}

	/**
	 * @param tasa
	 */
	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}

	/**
	 * @return
	 */
	public String getTasaVariable() {
		return tasaVariable;
	}

	/**
	 * @param tasaVariable
	 */
	public void setTasaVariable(String tasaVariable) {
		this.tasaVariable = tasaVariable;
	}

	/**
	 * @return
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
