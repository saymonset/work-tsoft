package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author Fernando Pineda
 *
 */
@Entity
@Table(name = "T_CONCILIACION_EFECTIVO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_CONCILIACION_EFECTIVO", allocationSize = 1)
public class ConciliacionEfectivoInt implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_CONCILIACION_EFECTIVO")
	private Long folioConciliacion;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="ID_CONCILIACION_EFECTIVO")
	private List<DetalleConciliacionEfectivoInt> listaDetalleConciliacionEfectivo;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="ID_CONCILIACION_EFECTIVO")
	private List<BitacoraConciliacionEfectivoInt> listaBitacoraConciliacionEfectivo;
	
	@Column(name = "STATEMENT_NUMBER")
	private Integer statementNumber;
	
	@Column(name = "FECHA_BALANCE")
	private Date fechaBalance;
	
	@Column(name = "REFERENCIA")
	private String referencia;
	
	@Column(name = "BIC_CODE")
	private String bicCode;
	
	@Column(name = "DIVISA")
	private String divisa;
	
	@Column(name = "CUENTA")
	private String cuenta;
	
	@Column(name = "BALANCE_INICIO")
	private BigDecimal balanceInicio;
	
	@Column(name = "BALANCE_FINAL")
	private BigDecimal balanceFinal;
	
	@Column(name = "SALDO_BOVEDA")
	private BigDecimal saldoBoveda;
	
	@Column(name = "NETO_MOVIMIENTOS")
	private BigDecimal netoMovimientos;
	
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
	 * @return the listaDetalleConciliacionEfectivo
	 */
	public List<DetalleConciliacionEfectivoInt> getListaDetalleConciliacionEfectivo() {
		return listaDetalleConciliacionEfectivo;
	}
	/**
	 * @param listaDetalleConciliacionEfectivo the listaDetalleConciliacionEfectivo to set
	 */
	public void setListaDetalleConciliacionEfectivo(
			List<DetalleConciliacionEfectivoInt> listaDetalleConciliacionEfectivo) {
		this.listaDetalleConciliacionEfectivo = listaDetalleConciliacionEfectivo;
	}
	/**
	 * @return the listaBitacoraConciliacionEfectivo
	 */
	public List<BitacoraConciliacionEfectivoInt> getListaBitacoraConciliacionEfectivo() {
		return listaBitacoraConciliacionEfectivo;
	}
	/**
	 * @param listaBitacoraConciliacionEfectivo the listaBitacoraConciliacionEfectivo to set
	 */
	public void setListaBitacoraConciliacionEfectivo(
			List<BitacoraConciliacionEfectivoInt> listaBitacoraConciliacionEfectivo) {
		this.listaBitacoraConciliacionEfectivo = listaBitacoraConciliacionEfectivo;
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
	 * @return the fechaBalance
	 */
	public Date getFechaBalance() {
		return fechaBalance;
	}
	/**
	 * @param fechaBalance the fechaBalance to set
	 */
	public void setFechaBalance(Date fechaBalance) {
		this.fechaBalance = fechaBalance;
	}
	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}
	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	/**
	 * @return the bicCode
	 */
	public String getBicCode() {
		return bicCode;
	}
	/**
	 * @param bicCode the bicCode to set
	 */
	public void setBicCode(String bicCode) {
		this.bicCode = bicCode;
	}
	/**
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}
	/**
	 * @param divisa the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	/**
	 * @return the cuenta
	 */
	public String getCuenta() {
		return cuenta;
	}
	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	/**
	 * @return the balanceInicio
	 */
	public BigDecimal getBalanceInicio() {
		return balanceInicio;
	}
	/**
	 * @param balanceInicio the balanceInicio to set
	 */
	public void setBalanceInicio(BigDecimal balanceInicio) {
		this.balanceInicio = balanceInicio;
	}
	/**
	 * @return the balanceFinal
	 */
	public BigDecimal getBalanceFinal() {
		return balanceFinal;
	}
	/**
	 * @param balanceFinal the balanceFinal to set
	 */
	public void setBalanceFinal(BigDecimal balanceFinal) {
		this.balanceFinal = balanceFinal;
	}
	/**
	 * @return the saldoBoveda
	 */
	public BigDecimal getSaldoBoveda() {
		return saldoBoveda;
	}
	/**
	 * @param saldoBoveda the saldoBoveda to set
	 */
	public void setSaldoBoveda(BigDecimal saldoBoveda) {
		this.saldoBoveda = saldoBoveda;
	}
	/**
	 * @return the netoMovimientos
	 */
	public BigDecimal getNetoMovimientos() {
		return netoMovimientos;
	}
	/**
	 * @param netoMovimientos the netoMovimientos to set
	 */
	public void setNetoMovimientos(BigDecimal netoMovimientos) {
		this.netoMovimientos = netoMovimientos;
	}
}