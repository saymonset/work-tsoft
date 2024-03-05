/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Cata&acute;logo con los campos para cuentas de retiro de efectivo internacionales 
 * 
 * C_CUENTA_RETIRO_INT
 *
 * @author  Maria C. Buendia
 * @version 1.0
 */
@Entity
@Table(name="C_CUENTA_RETIRO_INT")
@PrimaryKeyJoinColumn(name="ID_CUENTA_RETIRO")
public class CuentaRetiroInternacional extends CuentaRetiro implements Serializable {
		
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** identificador de cuenta */
	@Column(name = "ID_CUENTA_RETIRO_INT", nullable = false)
	private Long idCuentaRetiroInt;
	
	/** nombre corto */
	@Column(name = "NOMBRE_CORTO")
	private String nombreCorto;

	/** cuenta beneficiario final */
	@Column(name = "CTA_BENEFC_FINAL", nullable = false)
	private String cuentaBeneficiarioFinal;
	
	/** nombre beneficiario final */
	@Column(name = "NOM_BENEFC_FINAL", nullable = false)
	private String nombreBeneficiarioFinal;
	
	/** banco beneficiario */
	@Column(name = "BCO_BENEFICIARIO", nullable = false)
	private String bancoBeneficiario;

	/** nombre banco beneficiario */
	@Column(name = "NOMBCO_BENEFICIARIO", nullable = false)
	private String nombreBancoBeneficiario;

	/** cuenta beneficiario */
	@Column(name = "CTA_BENEFICIARIO")
	private String cuentaBeneficiario;
	
	/** cuenta intermediario */
	@Column(name = "CTA_INTERMEDIARIO")
	private String cuentaIntermediario;

	/** banco intermediario */
	@Column(name = "BCO_INTERMEDIARIO")
	private String bancoIntermediario;

	/** nombre intermediario */
	@Column(name = "NOM_INTERMEDIARIO")
	private String nombreIntermediario;

	/** detalles de pago */
	@Column(name = "DETALLES_PAGO")
	private String detallesPago;

	public Long getIdCuentaRetiroInt() {
		return idCuentaRetiroInt;
	}

	public void setIdCuentaRetiroInt(Long idCuentaRetiroInt) {
		this.idCuentaRetiroInt = idCuentaRetiroInt;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getBancoBeneficiario() {
		return bancoBeneficiario;
	}

	public void setBancoBeneficiario(String bancoBeneficiario) {
		this.bancoBeneficiario = bancoBeneficiario;
	}

	public String getNombreBancoBeneficiario() {
		return nombreBancoBeneficiario;
	}

	public void setNombreBancoBeneficiario(String nombreBancoBeneficiario) {
		this.nombreBancoBeneficiario = nombreBancoBeneficiario;
	}

	public String getCuentaBeneficiario() {
		return cuentaBeneficiario;
	}

	public void setCuentaBeneficiario(String cuentaBeneficiario) {
		this.cuentaBeneficiario = cuentaBeneficiario;
	}

	public String getCuentaIntermediario() {
		return cuentaIntermediario;
	}

	public void setCuentaIntermediario(String cuentaIntermediario) {
		this.cuentaIntermediario = cuentaIntermediario;
	}

	public String getBancoIntermediario() {
		return bancoIntermediario;
	}

	public void setBancoIntermediario(String bancoIntermediario) {
		this.bancoIntermediario = bancoIntermediario;
	}

	public String getNombreIntermediario() {
		return nombreIntermediario;
	}

	public void setNombreIntermediario(String nombreIntermediario) {
		this.nombreIntermediario = nombreIntermediario;
	}

	public String getDetallesPago() {
		return detallesPago;
	}

	public void setDetallesPago(String detallesPago) {
		this.detallesPago = detallesPago;
	}

	public String getCuentaBeneficiarioFinal() {
		return cuentaBeneficiarioFinal;
	}

	public void setCuentaBeneficiarioFinal(String cuentaBeneficiarioFinal) {
		this.cuentaBeneficiarioFinal = cuentaBeneficiarioFinal;
	}

	public String getNombreBeneficiarioFinal() {
		return nombreBeneficiarioFinal;
	}

	public void setNombreBeneficiarioFinal(String nombreBeneficiarioFinal) {
		this.nombreBeneficiarioFinal = nombreBeneficiarioFinal;
	}

}
