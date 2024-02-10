// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.dto;

import java.io.Serializable;
import java.util.Date;

public class CuentaCorresponsalDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idCuentaCorresponsal;

	private Long idInstitucion;

	private Long idDivisa;
	
	private String mensaje;

	private String intermediaryOptionP;

	private String intermediaryValueP;

	private String intermediaryNameAddressP;

	private String accountOptionP;

	private String accountValueP;

	private String accountNameAddressP;

	private String accountLocationP;

	private String beneficiaryOptionP;

	private String beneficiaryValueP;

	private String beneficiaryNameAddressP;
	
	private String beneficiaryNumberNameP;

	private Boolean activoCorresponsalPrincipal;

	private String intermediaryOptionB;

	private String intermediaryValueB;

	private String intermediaryNameAddressB;

	private String accountOptionB;

	private String accountValueB;

	private String accountNameAddressB;

	private String accountLocationB;

	private String beneficiaryOptionB;

	private String beneficiaryValueB;

	private String beneficiaryNameAddressB;
	
	private String beneficiaryNumberNameB;

	private Boolean activoCorresponsalBackup;

	private Date fechaAlta;

	private Date fechaUltModificacion;
	
	private String intermediaryBicP;
	
	private String intermediaryBicB;
	
	private String accountBicP;
	
	private String accountBicB;
	
	private String beneficiaryBicP;
	
	private String beneficiaryBicB;

	public Long getIdCuentaCorresponsal() {
		return idCuentaCorresponsal;
	}

	public void setIdCuentaCorresponsal(Long idCuentaCorresponsal) {
		this.idCuentaCorresponsal = idCuentaCorresponsal;
	}

	public Long getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public Long getIdDivisa() {
		return idDivisa;
	}

	public void setIdDivisa(Long idDivisa) {
		this.idDivisa = idDivisa;
	}

	public Boolean getActivoCorresponsalPrincipal() {
		return activoCorresponsalPrincipal;
	}

	public void setActivoCorresponsalPrincipal(Boolean activoCorresponsalPrincipal) {
		this.activoCorresponsalPrincipal = activoCorresponsalPrincipal;
	}

	public Boolean getActivoCorresponsalBackup() {
		return activoCorresponsalBackup;
	}

	public void setActivoCorresponsalBackup(Boolean activoCorresponsalBackup) {
		this.activoCorresponsalBackup = activoCorresponsalBackup;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaUltModificacion() {
		return fechaUltModificacion;
	}

	public void setFechaUltModificacion(Date fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	public String getIntermediaryOptionP() {
		return intermediaryOptionP;
	}

	public void setIntermediaryOptionP(String intermediaryOptionP) {
		this.intermediaryOptionP = intermediaryOptionP;
	}

	public String getIntermediaryValueP() {
		return intermediaryValueP;
	}

	public void setIntermediaryValueP(String intermediaryValueP) {
		this.intermediaryValueP = intermediaryValueP;
	}

	public String getIntermediaryNameAddressP() {
		return intermediaryNameAddressP;
	}

	public void setIntermediaryNameAddressP(String intermediaryNameAddressP) {
		this.intermediaryNameAddressP = intermediaryNameAddressP;
	}

	public String getAccountOptionP() {
		return accountOptionP;
	}

	public void setAccountOptionP(String accountOptionP) {
		this.accountOptionP = accountOptionP;
	}

	public String getAccountValueP() {
		return accountValueP;
	}

	public void setAccountValueP(String accountValueP) {
		this.accountValueP = accountValueP;
	}

	public String getAccountNameAddressP() {
		return accountNameAddressP;
	}

	public void setAccountNameAddressP(String accountNameAddressP) {
		this.accountNameAddressP = accountNameAddressP;
	}

	public String getAccountLocationP() {
		return accountLocationP;
	}

	public void setAccountLocationP(String accountLocationP) {
		this.accountLocationP = accountLocationP;
	}

	public String getBeneficiaryOptionP() {
		return beneficiaryOptionP;
	}

	public void setBeneficiaryOptionP(String beneficiaryOptionP) {
		this.beneficiaryOptionP = beneficiaryOptionP;
	}

	public String getBeneficiaryValueP() {
		return beneficiaryValueP;
	}

	public void setBeneficiaryValueP(String beneficiaryValueP) {
		this.beneficiaryValueP = beneficiaryValueP;
	}

	public String getBeneficiaryNameAddressP() {
		return beneficiaryNameAddressP;
	}

	public void setBeneficiaryNameAddressP(String beneficiaryNameAddressP) {
		this.beneficiaryNameAddressP = beneficiaryNameAddressP;
	}

	public String getIntermediaryOptionB() {
		return intermediaryOptionB;
	}

	public void setIntermediaryOptionB(String intermediaryOptionB) {
		this.intermediaryOptionB = intermediaryOptionB;
	}

	public String getIntermediaryValueB() {
		return intermediaryValueB;
	}

	public void setIntermediaryValueB(String intermediaryValueB) {
		this.intermediaryValueB = intermediaryValueB;
	}

	public String getIntermediaryNameAddressB() {
		return intermediaryNameAddressB;
	}

	public void setIntermediaryNameAddressB(String intermediaryNameAddressB) {
		this.intermediaryNameAddressB = intermediaryNameAddressB;
	}

	public String getAccountOptionB() {
		return accountOptionB;
	}

	public void setAccountOptionB(String accountOptionB) {
		this.accountOptionB = accountOptionB;
	}

	public String getAccountValueB() {
		return accountValueB;
	}

	public void setAccountValueB(String accountValueB) {
		this.accountValueB = accountValueB;
	}

	public String getAccountNameAddressB() {
		return accountNameAddressB;
	}

	public void setAccountNameAddressB(String accountNameAddressB) {
		this.accountNameAddressB = accountNameAddressB;
	}

	public String getAccountLocationB() {
		return accountLocationB;
	}

	public void setAccountLocationB(String accountLocationB) {
		this.accountLocationB = accountLocationB;
	}

	public String getBeneficiaryOptionB() {
		return beneficiaryOptionB;
	}

	public void setBeneficiaryOptionB(String beneficiaryOptionB) {
		this.beneficiaryOptionB = beneficiaryOptionB;
	}

	public String getBeneficiaryValueB() {
		return beneficiaryValueB;
	}

	public void setBeneficiaryValueB(String beneficiaryValueB) {
		this.beneficiaryValueB = beneficiaryValueB;
	}

	public String getBeneficiaryNameAddressB() {
		return beneficiaryNameAddressB;
	}

	public void setBeneficiaryNameAddressB(String beneficiaryNameAddressB) {
		this.beneficiaryNameAddressB = beneficiaryNameAddressB;
	}
	
	public void setAccountBicB(String accountBicB) {
		this.accountBicB = accountBicB;
	}
	
	public String getAccountBicB() {
		return accountBicB;
	}
	
	public void setAccountBicP(String accountBicP) {
		this.accountBicP = accountBicP;
	}
	
	public String getAccountBicP() {
		return accountBicP;
	}
	
	public void setBeneficiaryBicB(String beneficiaryBicB) {
		this.beneficiaryBicB = beneficiaryBicB;
	}
	
	public String getBeneficiaryBicB() {
		return beneficiaryBicB;
	}
	
	public void setBeneficiaryBicP(String beneficiaryBicP) {
		this.beneficiaryBicP = beneficiaryBicP;
	}
	
	public String getBeneficiaryBicP() {
		return beneficiaryBicP;
	}
	
	public void setIntermediaryBicB(String intermediaryBicB) {
		this.intermediaryBicB = intermediaryBicB;
	}
	
	public String getIntermediaryBicB() {
		return intermediaryBicB;
	}
	
	public void setIntermediaryBicP(String intermediaryBicP) {
		this.intermediaryBicP = intermediaryBicP;
	}
	
	public String getIntermediaryBicP() {
		return intermediaryBicP;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getBeneficiaryNumberNameP() {
		return beneficiaryNumberNameP;
	}

	public void setBeneficiaryNumberNameP(String beneficiaryNumberNameP) {
		this.beneficiaryNumberNameP = beneficiaryNumberNameP;
	}

	public String getBeneficiaryNumberNameB() {
		return beneficiaryNumberNameB;
	}

	public void setBeneficiaryNumberNameB(String beneficiaryNumberNameB) {
		this.beneficiaryNumberNameB = beneficiaryNumberNameB;
	}

	@Override
	public String toString() {
		return "CuentaCorresponsalDTO [idCuentaCorresponsal=" + idCuentaCorresponsal + ", idInstitucion="
				+ idInstitucion + ", idDivisa=" + idDivisa + ", mensaje=" + mensaje + ", intermediaryOptionP="
				+ intermediaryOptionP + ", intermediaryValueP=" + intermediaryValueP + ", intermediaryNameAddressP="
				+ intermediaryNameAddressP + ", accountOptionP=" + accountOptionP + ", accountValueP=" + accountValueP
				+ ", accountNameAddressP=" + accountNameAddressP + ", accountLocationP=" + accountLocationP
				+ ", beneficiaryOptionP=" + beneficiaryOptionP + ", beneficiaryValueP=" + beneficiaryValueP
				+ ", beneficiaryNameAddressP=" + beneficiaryNameAddressP + ", beneficiaryNumberNameP="
				+ beneficiaryNumberNameP + ", activoCorresponsalPrincipal=" + activoCorresponsalPrincipal
				+ ", intermediaryOptionB=" + intermediaryOptionB + ", intermediaryValueB=" + intermediaryValueB
				+ ", intermediaryNameAddressB=" + intermediaryNameAddressB + ", accountOptionB=" + accountOptionB
				+ ", accountValueB=" + accountValueB + ", accountNameAddressB=" + accountNameAddressB
				+ ", accountLocationB=" + accountLocationB + ", beneficiaryOptionB=" + beneficiaryOptionB
				+ ", beneficiaryValueB=" + beneficiaryValueB + ", beneficiaryNameAddressB=" + beneficiaryNameAddressB
				+ ", beneficiaryNumberNameB=" + beneficiaryNumberNameB + ", activoCorresponsalBackup="
				+ activoCorresponsalBackup + ", fechaAlta=" + fechaAlta + ", fechaUltModificacion="
				+ fechaUltModificacion + ", intermediaryBicP=" + intermediaryBicP + ", intermediaryBicB="
				+ intermediaryBicB + ", accountBicP=" + accountBicP + ", accountBicB=" + accountBicB
				+ ", beneficiaryBicP=" + beneficiaryBicP + ", beneficiaryBicB=" + beneficiaryBicB + "]";
	}

	
}