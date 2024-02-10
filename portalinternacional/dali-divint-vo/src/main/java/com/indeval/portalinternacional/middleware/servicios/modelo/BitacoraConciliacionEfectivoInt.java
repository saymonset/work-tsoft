package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author lmunoz
 *
 */
@Entity
@Table(name = "T_BITACORA_CONCILIACION_EFE")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_BIT_CONCILIACION_EFE", allocationSize = 1)
public class BitacoraConciliacionEfectivoInt implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_BITACORA_CONCILIACION")
	private Long idBitacora;
	
	@Column(name = "ID_CONCILIACION_EFECTIVO")
	private Long idConciliacionEfectivo;
	
	@Column(name= "FOLIO_MENSAJE")
	private String folioMensaje;
	
	@Column(name = "SECUENCIA")
	private Integer secuencia;
	
	@Column(name = "BIC_CODE")
	private String bicCode;
	
	@Column(name = "DIVISA")
	private String divisa;
	
	@Column(name = "CUENTA")
	private String cuenta;
	
	@Column(name = "FECHA_EMISION")
	private Date fechaEmision;
	
	@Column(name = "FECHA_BALANCE")
	private Date fechaBalance;
	
	@Column(name = "FECHA_RECEPCION")
	private Date fechaRecepcion;
	@Lob
	@Column(name = "MENSAJE")
	private String mensaje;
	
	@Column(name = "BALANCE_INICIO")
	private BigDecimal balanceInicio;
	
	@Column(name = "BALANCE_FINAL")
	private BigDecimal balanceFinal;
	
	@Column(name = "TIPO_BALANCE")
	private String tipoBalance;
	
	@Column(name = "CHK")
	private String chk;
	
	/**
	 * @return the idBitacora
	 */
	public Long getIdBitacora() {
		return idBitacora;
	}
	/**
	 * @param idBitacora the idBitacora to set
	 */
	public void setIdBitacora(Long idBitacora) {
		this.idBitacora = idBitacora;
	}
	/**
	 * @return the conciliacionEfectivo
	 */
	public Long getIdConciliacionEfectivo() {
		return this.idConciliacionEfectivo;
	}
	/**
	 * @param conciliacionEfectivo the conciliacionEfectivo to set
	 */
	public void setIdConciliacionEfectivo(Long idConciliacionEfectivo) {
		this.idConciliacionEfectivo = idConciliacionEfectivo;
	}
	/**
	 * @return the folioMensaje
	 */
	public String getFolioMensaje() {
		return folioMensaje;
	}
	/**
	 * @param folioMensaje the folioMensaje to set
	 */
	public void setFolioMensaje(String folioMensaje) {
		this.folioMensaje = folioMensaje;
	}
	/**
	 * @return the secuencia
	 */
	public Integer getSecuencia() {
		return secuencia;
	}
	/**
	 * @param secuencia the secuencia to set
	 */
	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
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
	 * @return the fechaEmision
	 */
	public Date getFechaEmision() {
		return fechaEmision;
	}
	/**
	 * @param fechaEmision the fechaEmision to set
	 */
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
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
	 * @return the fechaRecepcion
	 */
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	/**
	 * @param fechaRecepcion the fechaRecepcion to set
	 */
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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
	 * @return the tipoBalance
	 */
	public String getTipoBalance() {
		return tipoBalance;
	}
	/**
	 * @param tipoBalance the tipoBalance to set
	 */
	public void setTipoBalance(String tipoBalance) {
		this.tipoBalance = tipoBalance;
	}
	/**
	 * @return the chk
	 */
	public String getChk() {
		return chk;
	}
	/**
	 * @param chk the chk to set
	 */
	public void setChk(String chk) {
		this.chk = chk;
	}
	
}
