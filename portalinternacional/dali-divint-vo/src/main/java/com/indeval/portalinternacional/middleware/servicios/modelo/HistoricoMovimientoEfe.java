package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.indeval.portaldali.persistence.modelo.Divisa;

@Entity
@Table(name = "T_HISTORICO_MOVIMIENTO_EFE")
@SequenceGenerator(name = "sequence", sequenceName = "SEQ_T_HISTORICO_MOVIMIENTO_EFE", allocationSize = 1)
public class HistoricoMovimientoEfe implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "ID_HISTORICO")
	private Long idHistorico;
	
	@Column(name = "BIC_CODE")
	private String bicCode;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DIVISA")
	private Divisa divisa;
	
	@Column(name = "CUENTA")
	private String cuenta;
	
	@Column(name = "ACTIVO")
	private Boolean activo;
	
	@Column(name = "ENVIO")
	private Boolean envio;
	
	@Column(name = "SALDO")
	private BigDecimal saldo;
	
	@Column(name = "FECHA")
	private Date fecha;
	
	/**
	 * @return the idHistorico
	 */
	public Long getIdHistorico() {
		return idHistorico;
	}

	/**
	 * @param idHistorico the idHistorico to set
	 */
	public void setIdHistorico(Long idHistorico) {
		this.idHistorico = idHistorico;
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
	public Divisa getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa the divisa to set
	 */
	public void setDivisa(Divisa divisa) {
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
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the envio
	 */
	public Boolean getEnvio() {
		return envio;
	}

	/**
	 * @param envio the envio to set
	 */
	public void setEnvio(Boolean envio) {
		this.envio = envio;
	}

	/**
	 * @return the saldo
	 */
	public BigDecimal getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
