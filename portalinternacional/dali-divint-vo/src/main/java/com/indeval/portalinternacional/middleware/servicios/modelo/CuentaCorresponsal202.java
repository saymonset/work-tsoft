// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.modelo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "C_BANCO_CORRESPONSAL_202")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_BANCO_CORRESPONSAL_202", allocationSize = 1)
public class CuentaCorresponsal202 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_BANCO_CORRESPONSAL_202")
	private Long idCuentaCorresponsal202;

	@Column(name = "INTERMEDIARY_OPTION_PRINCIPAL")
	private String intermediaryOptionP;

	@Column(name = "INTERMEDIARY_PARTY_PRINCIPAL")
	private String intermediaryPartyIdP;

	@Column(name = "INTERMEDIARY_NAME_ADDRESS_PRINCIPAL")
	private String intermediaryNameAddressP;

	@Column(name = "ACCOUNT_OPTION_PRINCIPAL")
	private String accountOptionP;

	@Column(name = "ACCOUNT_PARTY_PRINCIPAL")
	private String accountPartyIdP;

	@Column(name = "ACCOUNT_NAME_ADDRESS_PRINCIPAL")
	private String accountNameAddressP;

	@Column(name = "ACCOUNT_LOCATION_PRINCIPAL")
	private String accountLocationP;

	@Column(name = "BENEFICIARY_OPTION_PRINCIPAL")
	private String beneficiaryOptionP;

	@Column(name = "BENEFICIARY_PARTY_PRINCIPAL")
	private String beneficiaryPartyIdP;

	@Column(name = "BENEFICIARY_NAME_ADDRESS_PRINCIPAL")
	private String beneficiaryNameAddressP;

	@Column(name = "ACTIVO_CORRESPONSAL_PRINCIPAL")
	private Boolean activoCorresponsalPrincipal;

	@Column(name = "INTERMEDIARY_OPTION_BACKUP")
	private String intermediaryOptionB;

	@Column(name = "INTERMEDIARY_PARTY_BACKUP")
	private String intermediaryPartyIdB;

	@Column(name = "INTERMEDIARY_NAME_ADDRESS_BACKUP")
	private String intermediaryNameAddressB;

	@Column(name = "ACCOUNT_OPTION_BACKUP")
	private String accountOptionB;

	@Column(name = "ACCOUNT_PARTY_BACKUP")
	private String accountPartyIdB;

	@Column(name = "ACCOUNT_NAME_ADDRESS_BACKUP")
	private String accountNameAddressB;

	@Column(name = "ACCOUNT_LOCATION_BACKUP")
	private String accountLocationB;

	@Column(name = "BENEFICIARY_OPTION_BACKUP")
	private String beneficiaryOptionB;

	@Column(name = "BENEFICIARY_PARTY_BACKUP")
	private String beneficiaryPartyIdB;

	@Column(name = "BENEFICIARY_NAME_ADDRESS_BACKUP")
	private String beneficiaryNameAddressB;

	@Column(name = "ACTIVO_CORRESPONSAL_BACKUP")
	private Boolean activoCorresponsalBackup;

	@Column(name = "INTERMEDIARY_BIC_PRINCIPAL")
	private String intermediaryBicP;
	
	@Column(name = "INTERMEDIARY_BIC_BACKUP")
	private String intermediaryBicB;
	
	@Column(name = "ACCOUNT_BIC_PRINCIPAL")
	private String accountBicP;
	
	@Column(name = "ACCOUNT_BIC_BACKUP")
	private String accountBicB;
	
	@Column(name = "BENEFICIARY_BIC_PRINCIPAL")
	private String beneficiaryBicP;
	
	@Column(name = "BENEFICIARY_BIC_BACKUP")
	private String beneficiaryBicB;

	public Long getIdCuentaCorresponsal202() {
		return idCuentaCorresponsal202;
	}

	public void setIdCuentaCorresponsal202(Long idCuentaCorresponsal202) {
		this.idCuentaCorresponsal202 = idCuentaCorresponsal202;
	}

	public String getIntermediaryOptionP() {
		return intermediaryOptionP;
	}

	public void setIntermediaryOptionP(String intermediaryOptionP) {
		this.intermediaryOptionP = intermediaryOptionP;
	}

	public String getIntermediaryPartyIdP() {
		return intermediaryPartyIdP;
	}

	public void setIntermediaryPartyIdP(String intermediaryPartyIdP) {
		this.intermediaryPartyIdP = intermediaryPartyIdP;
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

	public String getAccountPartyIdP() {
		return accountPartyIdP;
	}

	public void setAccountPartyIdP(String accountPartyIdP) {
		this.accountPartyIdP = accountPartyIdP;
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

	public String getBeneficiaryPartyIdP() {
		return beneficiaryPartyIdP;
	}

	public void setBeneficiaryPartyIdP(String beneficiaryPartyIdP) {
		this.beneficiaryPartyIdP = beneficiaryPartyIdP;
	}

	public String getBeneficiaryNameAddressP() {
		return beneficiaryNameAddressP;
	}

	public void setBeneficiaryNameAddressP(String beneficiaryNameAddressP) {
		this.beneficiaryNameAddressP = beneficiaryNameAddressP;
	}

	public Boolean getActivoCorresponsalPrincipal() {
		return activoCorresponsalPrincipal;
	}

	public void setActivoCorresponsalPrincipal(Boolean activoCorresponsalPrincipal) {
		this.activoCorresponsalPrincipal = activoCorresponsalPrincipal;
	}

	public String getIntermediaryOptionB() {
		return intermediaryOptionB;
	}

	public void setIntermediaryOptionB(String intermediaryOptionB) {
		this.intermediaryOptionB = intermediaryOptionB;
	}

	public String getIntermediaryPartyIdB() {
		return intermediaryPartyIdB;
	}

	public void setIntermediaryPartyIdB(String intermediaryPartyIdB) {
		this.intermediaryPartyIdB = intermediaryPartyIdB;
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

	public String getAccountPartyIdB() {
		return accountPartyIdB;
	}

	public void setAccountPartyIdB(String accountPartyIdB) {
		this.accountPartyIdB = accountPartyIdB;
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

	public String getBeneficiaryPartyIdB() {
		return beneficiaryPartyIdB;
	}

	public void setBeneficiaryPartyIdB(String beneficiaryPartyIdB) {
		this.beneficiaryPartyIdB = beneficiaryPartyIdB;
	}

	public String getBeneficiaryNameAddressB() {
		return beneficiaryNameAddressB;
	}

	public void setBeneficiaryNameAddressB(String beneficiaryNameAddressB) {
		this.beneficiaryNameAddressB = beneficiaryNameAddressB;
	}

	public Boolean getActivoCorresponsalBackup() {
		return activoCorresponsalBackup;
	}

	public void setActivoCorresponsalBackup(Boolean activoCorresponsalBackup) {
		this.activoCorresponsalBackup = activoCorresponsalBackup;
	}

	public String getIntermediaryBicP() {
		return intermediaryBicP;
	}

	public void setIntermediaryBicP(String intermediaryBicP) {
		this.intermediaryBicP = intermediaryBicP;
	}

	public String getIntermediaryBicB() {
		return intermediaryBicB;
	}

	public void setIntermediaryBicB(String intermediaryBicB) {
		this.intermediaryBicB = intermediaryBicB;
	}

	public String getAccountBicP() {
		return accountBicP;
	}

	public void setAccountBicP(String accountBicP) {
		this.accountBicP = accountBicP;
	}

	public String getAccountBicB() {
		return accountBicB;
	}

	public void setAccountBicB(String accountBicB) {
		this.accountBicB = accountBicB;
	}

	public String getBeneficiaryBicP() {
		return beneficiaryBicP;
	}

	public void setBeneficiaryBicP(String beneficiaryBicP) {
		this.beneficiaryBicP = beneficiaryBicP;
	}

	public String getBeneficiaryBicB() {
		return beneficiaryBicB;
	}

	public void setBeneficiaryBicB(String beneficiaryBicB) {
		this.beneficiaryBicB = beneficiaryBicB;
	}

}
