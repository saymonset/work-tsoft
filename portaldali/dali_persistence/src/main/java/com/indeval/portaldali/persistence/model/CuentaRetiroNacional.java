/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Cata&acute;logo con los campos para cuentas de retiro de efectivo nacionales 
 * 
 * C_CUENTA_RETIRO_NAL
 *
 * @author  Maria C. Buendia
 * @version 1.0
 */
@Entity
@Table(name="C_CUENTA_RETIRO_NAL")
@PrimaryKeyJoinColumn(name="ID_CUENTA_RETIRO")
public class CuentaRetiroNacional extends CuentaRetiro implements Serializable {
		
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** identificador de cuenta */
	@Column(name = "ID_CUENTA_RETIRO_NAL")
	private Long idCuentaRetiroNal;

	/** identificador de cuenta */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_RETIRO", nullable = false)
	private CuentaRetiro cuentaRetiro;
	
	/** boveda correspondiente */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BOVEDA", nullable = false)
	private Boveda boveda;

	/** institucion beneficiario */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INST_BENEFICIARIO", nullable = false)
	private Institucion instBeneficiario;

	/** cuenta del beneficiario */
	@Column(name = "CTA_BENEFICIARIO", nullable = false)
	private String cuentaBeneficiario;

	/** nombre del beneficiario */
	@Column(name = "NOM_BENEFICIARIO", nullable = false)
	private String nombreBeneficiario;

	/** cuenta encriptada */	
	@Column(name = "CUENTA_DIGEST")
	private String cuentaDigest;
	
	public Long getIdCuentaRetiroNal() {
		return idCuentaRetiroNal;
	}

	public void setIdCuentaRetiroNal(Long idCuentaRetiroNal) {
		this.idCuentaRetiroNal = idCuentaRetiroNal;
	}

	public Boveda getBoveda() {
		return boveda;
	}

	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

	public Institucion getInstBeneficiario() {
		return instBeneficiario;
	}

	public void setInstBeneficiario(Institucion instBeneficiario) {
		this.instBeneficiario = instBeneficiario;
	}

	public String getCuentaBeneficiario() {
		return cuentaBeneficiario;
	}

	public void setCuentaBeneficiario(String cuentaBeneficiario) {
		this.cuentaBeneficiario = cuentaBeneficiario;
	}

	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}

	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}

	public String getCuentaDigest() {
		return cuentaDigest;
	}

	public void setCuentaDigest(String cuentaDigest) {
		this.cuentaDigest = cuentaDigest;
	}

	/**
	 * @return the cuentaRetiro
	 */
	public CuentaRetiro getCuentaRetiro() {
		return cuentaRetiro;
	}

	/**
	 * @param cuentaRetiro the cuentaRetiro to set
	 */
	public void setCuentaRetiro(CuentaRetiro cuentaRetiro) {
		this.cuentaRetiro = cuentaRetiro;
	}

}
