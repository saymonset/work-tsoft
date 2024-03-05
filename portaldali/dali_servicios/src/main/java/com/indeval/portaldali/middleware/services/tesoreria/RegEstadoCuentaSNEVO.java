/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class RegEstadoCuentaSNEVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private AgenteVO contraparte;

	private BigDecimal importeCobro;

	private BigDecimal importePago;

	private BigDecimal saldo;

	private BigInteger folioOperacion;

	private String mercado;

	private String origen;

	private String movimiento;

	private String aplicacion;

	private String cuenta;

	private String cuentaValor;

	private Date hora; // hh24:mi:ss

	private String folioOriginal;

	private EmisionVO claveDeValor;

	private BigInteger titulos;

	private BigDecimal precio;

	private BigInteger plazo;

	private BigDecimal porcentaje;

	private boolean valido;

	/**
	 * Constructor
	 */
	public RegEstadoCuentaSNEVO() {
		setImporteCobro(BIG_DECIMAL_ZERO);
		setImportePago(BIG_DECIMAL_ZERO);
		setSaldo(BIG_DECIMAL_ZERO);
		setFolioOperacion(BIG_INTEGER_ZERO);
		setTitulos(BIG_INTEGER_ZERO);
		setPrecio(BIG_DECIMAL_ZERO);
		setPlazo(BIG_INTEGER_ZERO);
		setPorcentaje(BIG_DECIMAL_ZERO);
	}

	/**
	 * @return String
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
	 * @return AgenteVO
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
	 * @return BigInteger
	 */
	public BigInteger getFolioOperacion() {
		return folioOperacion;
	}

	/**
	 * @param folioOperacion
	 */
	public void setFolioOperacion(BigInteger folioOperacion) {
		this.folioOperacion = folioOperacion;
	}

	/**
	 * @return String
	 */
	public String getFolioOriginal() {
		return folioOriginal;
	}

	/**
	 * @param folioOriginal
	 */
	public void setFolioOriginal(String folioOriginal) {
		this.folioOriginal = folioOriginal;
	}

	/**
	 * @return Date
	 */
	public Date getHora() {
		return hora;
	}

	/**
	 * @param hora
	 */
	public void setHora(Date hora) {
		this.hora = new Date(hora.getTime());
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getImporteCobro() {
		return importeCobro;
	}

	/**
	 * @param importeCobro
	 */
	public void setImporteCobro(BigDecimal importeCobro) {
		this.importeCobro = importeCobro;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getImportePago() {
		return importePago;
	}

	/**
	 * @param importePago
	 */
	public void setImportePago(BigDecimal importePago) {
		this.importePago = importePago;
	}

	/**
	 * @return String
	 */
	public String getMercado() {
		return mercado;
	}

	/**
	 * @param mercado
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	/**
	 * @return String
	 */
	public String getMovimiento() {
		return movimiento;
	}

	/**
	 * @param movimiento
	 */
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}

	/**
	 * @return String
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
	 * @return BigDecimal
	 */
	public BigDecimal getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo
	 */
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	/**
	 * @return EmisionVO
	 */
	public EmisionVO getClaveDeValor() {
		return claveDeValor;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getPlazo() {
		return plazo;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getPrecio() {
		return precio;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getTitulos() {
		return titulos;
	}

	/**
	 * @param claveDeValor
	 */
	public void setClaveDeValor(EmisionVO claveDeValor) {
		this.claveDeValor = claveDeValor;
	}

	/**
	 * @param plazo
	 */
	public void setPlazo(BigInteger plazo) {
		this.plazo = plazo;
	}

	/**
	 * @param porcentaje
	 */
	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	/**
	 * @param precio
	 */
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	/**
	 * @param titulos
	 */
	public void setTitulos(BigInteger titulos) {
		this.titulos = titulos;
	}

	/**
	 * @param inObject
	 * @return boolean
	 */
	public static boolean isAn(Object inObject) {

		if ((inObject == null) || (!(inObject instanceof RegEstadoCuentaSNEVO)))
			return false;

		return true;
	}

	/**
	 * @return String
	 */
	public String getCuentaValor() {
		return cuentaValor;
	}

	/**
	 * @param cuentaValor
	 */
	public void setCuentaValor(String cuentaValor) {
		this.cuentaValor = cuentaValor;
	}

	/**
	 * @return boolean
	 */
	public boolean isValido() {
		return valido;
	}

	/**
	 * @param valido
	 */
	public void setValido(boolean valido) {
		this.valido = valido;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
