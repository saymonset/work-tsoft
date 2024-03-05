/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */

package com.indeval.portaldali.middleware.services.tesoreria;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class RegCuentaLiqVO extends AbstractBaseDTO {

	/**
	 * Constante de Serializacion
	 */
	private static final long serialVersionUID = 1L;

	private String tipoEjercicio;

	private String idInst;

	private String folioInst;

	private String cuenta;

	private EmisionVO emision;

	private String isin;

	private String folioVariable;

	private String folioFija;

	private BigDecimal importePendiente;

	private BigDecimal importeLiquidado;

	private Date fechaVencimiento;

	private Date fechaPago;

	private String divisaPago;

	private Integer idDerecho;

	private Integer idTipoDerecho;

	private Integer idTipoEjercicio;

	/**
	 * @return String
	 */
	public String getFolioInst() {
		return folioInst;
	}

	/**
	 * @param folioInst
	 */
	public void setFolioInst(String folioInst) {
		this.folioInst = folioInst;
	}

	/**
	 * @return String
	 */
	public String getIdInst() {
		return idInst;
	}

	/**
	 * @param idInst
	 */
	public void setIdInst(String idInst) {
		this.idInst = idInst;
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
	 * @return EmisionVO
	 */
	public EmisionVO getEmision() {
		return emision;
	}

	/**
	 * @param emision
	 */
	public void setEmision(EmisionVO emision) {
		this.emision = emision;
	}

	/**
	 * @return Date
	 */
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * @param fechaVencimiento
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = clona(fechaVencimiento);
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getImporteLiquidado() {
		return importeLiquidado;
	}

	/**
	 * @param importeLiquidado
	 */
	public void setImporteLiquidado(BigDecimal importeLiquidado) {
		this.importeLiquidado = importeLiquidado;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getImportePendiente() {
		return importePendiente;
	}

	/**
	 * @param importePendiente
	 */
	public void setImportePendiente(BigDecimal importePendiente) {
		this.importePendiente = importePendiente;
	}

	/**
	 * @return String
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getRemanente() {
		BigDecimal resultado = new BigDecimal(0);
		if (importeLiquidado != null) {

			if (importePendiente != null) {
				resultado = importePendiente.subtract(importeLiquidado);
			} else {
				resultado = (new BigDecimal(0)).subtract(importeLiquidado);
			}

		} else if (importePendiente != null) {
			resultado = importePendiente;
		}
		return resultado;
	}

	/**
	 * @param inObject
	 * @return boolean
	 */
	public static boolean isAn(Object inObject) {

		if ((inObject == null) || (!(inObject instanceof RegCuentaLiqVO)))
			return false;

		return true;
	}

	/**
	 * @return Returns the folioVariable.
	 */
	public String getFolioVariable() {
		return folioVariable;
	}

	/**
	 * @return Returns the idTipoEjercicio.
	 */
	public String getTipoEjercicio() {
		return tipoEjercicio;
	}

	/**
	 * @param folioVariable
	 *            The folioVariable to set.
	 */
	public void setFolioVariable(String folioVariable) {
		this.folioVariable = folioVariable;
	}

	/**
	 * @param tipoEjercicio
	 *            The tipoEjercicio to set.
	 */
	public void setTipoEjercicio(String tipoEjercicio) {
		this.tipoEjercicio = tipoEjercicio;
	}

	/**
	 * @return Returns the folioFija.
	 */
	public String getFolioFija() {
		return folioFija;
	}

	/**
	 * @param folioFija
	 *            The folioFija to set.
	 */
	public void setFolioFija(String folioFija) {
		this.folioFija = folioFija;
	}

	/**
	 * @return Integer
	 */
	public Integer getIdDerecho() {
		return idDerecho;
	}

	/**
	 * @param idDerecho
	 */
	public void setIdDerecho(Integer idDerecho) {
		this.idDerecho = idDerecho;
	}

	/**
	 * @return Integer
	 */
	public Integer getIdTipoDerecho() {
		return idTipoDerecho;
	}

	/**
	 * @param idTipoDerecho
	 */
	public void setIdTipoDerecho(Integer idTipoDerecho) {
		this.idTipoDerecho = idTipoDerecho;
	}

	/**
	 * @return Integer
	 */
	public Integer getIdTipoEjercicio() {
		return idTipoEjercicio;
	}

	/**
	 * @param idTipoEjercicio
	 */
	public void setIdTipoEjercicio(Integer idTipoEjercicio) {
		this.idTipoEjercicio = idTipoEjercicio;
	}

	/**
	 * @return String
	 */
	public String getDivisaPago() {
		return divisaPago;
	}

	/**
	 * @param divisaPago
	 */
	public void setDivisaPago(String divisaPago) {
		this.divisaPago = divisaPago;
	}

	/**
	 * @return Date
	 */
	public Date getFechaPago() {
		return fechaPago;
	}

	/**
	 * @param fechaPago
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * @see org.springframework.validation.Validator #validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {

	}

}
