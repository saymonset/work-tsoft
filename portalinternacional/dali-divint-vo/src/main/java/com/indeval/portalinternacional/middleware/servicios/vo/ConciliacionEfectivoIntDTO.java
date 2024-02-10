package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class ConciliacionEfectivoIntDTO implements Serializable{
	
	private Set<String> bicCodes;
	private Set<String> divisas;
	private Set<String> cuentas;
	private Long folioConciliacion;
	private Integer statementNumber;
	private String referenciaMT;
	private Date fechaBalanceInicio;
	private Date fechaBalanceFin;
	private Date fechaEmisionInicio;
	private Date fechaEmisionFin;
	private Boolean cuentaComercial;
	private Boolean cuentaCustodia;
	private Boolean diferencia;
	
	/**
	 * @return the bicCodes
	 */
	public Set<String> getBicCodes() {
		return bicCodes;
	}
	/**
	 * @param bicCodes the bicCodes to set
	 */
	public void setBicCodes(Set<String> bicCodes) {
		this.bicCodes = bicCodes;
	}
	/**
	 * @return the divisas
	 */
	public Set<String> getDivisas() {
		return divisas;
	}
	/**
	 * @param divisas the divisas to set
	 */
	public void setDivisas(Set<String> divisas) {
		this.divisas = divisas;
	}
	/**
	 * @return the cuentas
	 */
	public Set<String> getCuentas() {
		return cuentas;
	}
	/**
	 * @param cuentas the cuentas to set
	 */
	public void setCuentas(Set<String> cuentas) {
		this.cuentas = cuentas;
	}
	/**
	 * @return the folioConciliacion
	 */
	public Long getFolioConciliacion() {
		return folioConciliacion;
	}
	/**
	 * @param folioConciliacion the folioConciliacion to set
	 */
	public void setFolioConciliacion(Long folioConciliacion) {
		this.folioConciliacion = folioConciliacion;
	}
	/**
	 * @return the statementNumber
	 */
	public Integer getStatementNumber() {
		return statementNumber;
	}
	/**
	 * @param statementNumber the statementNumber to set
	 */
	public void setStatementNumber(Integer statementNumber) {
		this.statementNumber = statementNumber;
	}
	/**
	 * @return the referenciaMT
	 */
	public String getReferenciaMT() {
		return referenciaMT;
	}
	/**
	 * @param referenciaMT the referenciaMT to set
	 */
	public void setReferenciaMT(String referenciaMT) {
		this.referenciaMT = referenciaMT;
	}
	/**
	 * @return the fechaBalanceInicio
	 */
	public Date getFechaBalanceInicio() {
		return fechaBalanceInicio;
	}
	/**
	 * @param fechaBalanceInicio the fechaBalanceInicio to set
	 */
	public void setFechaBalanceInicio(Date fechaBalanceInicio) {
		this.fechaBalanceInicio = fechaBalanceInicio;
	}
	/**
	 * @return the fechaBalanceFin
	 */
	public Date getFechaBalanceFin() {
		return fechaBalanceFin;
	}
	/**
	 * @param fechaBalanceFin the fechaBalanceFin to set
	 */
	public void setFechaBalanceFin(Date fechaBalanceFin) {
		this.fechaBalanceFin = fechaBalanceFin;
	}
	/**
	 * @return the fechaEmisionInicio
	 */
	public Date getFechaEmisionInicio() {
		return fechaEmisionInicio;
	}
	/**
	 * @param fechaEmisionInicio the fechaEmisionInicio to set
	 */
	public void setFechaEmisionInicio(Date fechaEmisionInicio) {
		this.fechaEmisionInicio = fechaEmisionInicio;
	}
	/**
	 * @return the fechaEmisionFin
	 */
	public Date getFechaEmisionFin() {
		return fechaEmisionFin;
	}
	/**
	 * @param fechaEmisionFin the fechaEmisionFin to set
	 */
	public void setFechaEmisionFin(Date fechaEmisionFin) {
		this.fechaEmisionFin = fechaEmisionFin;
	}
	/**
	 * @return the cuentaComercial
	 */
	public Boolean getCuentaComercial() {
		return cuentaComercial;
	}
	/**
	 * @param cuentaComercial the cuentaComercial to set
	 */
	public void setCuentaComercial(Boolean cuentaComercial) {
		this.cuentaComercial = cuentaComercial;
	}
	/**
	 * @return the cuentaCustodia
	 */
	public Boolean getCuentaCustodia() {
		return cuentaCustodia;
	}
	/**
	 * @param cuentaCustodia the cuentaCustodia to set
	 */
	public void setCuentaCustodia(Boolean cuentaCustodia) {
		this.cuentaCustodia = cuentaCustodia;
	}
	/**
	 * @return the diferencia
	 */
	public Boolean getDiferencia() {
		return diferencia;
	}
	/**
	 * @param diferencia the diferencia to set
	 */
	public void setDiferencia(Boolean diferencia) {
		this.diferencia = diferencia;
	}
}
