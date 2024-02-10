// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.modelo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "C_BANCO_CORRESPONSAL_103")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_BANCO_CORRESPONSAL_103", allocationSize = 1)
public class CuentaCorresponsal103 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_BANCO_CORRESPONSAL_103")
	private Long idCuentaCorresponsal103;

	@Column(name = "INTERMEDIARY_OPTION_PRINCIPAL")
	private String intermediaryOptionP;

	@Column(name = "INTERMEDIARY_PARTY_PRINCIPAL")
	private String intermediaryPartyIdP;

	@Column(name = "INTERMEDIARY_NAME_ADDRESS_PRINCIPAL")
	private String intermediaryNameAddressP;
	
	@Column(name = "INTERMEDIARY_BIC_PRINCIPAL")
	private String intermediaryBicP;

	@Column(name = "ACCOUNT_OPTION_PRINCIPAL")
	private String accountOptionP;

	@Column(name = "ACCOUNT_PARTY_PRINCIPAL")
	private String accountPartyIdP;

	@Column(name = "ACCOUNT_NAME_ADDRESS_PRINCIPAL")
	private String accountNameAddressP;

	@Column(name = "ACCOUNT_LOCATION_PRINCIPAL")
	private String accountLocationP;
	
	@Column(name = "ACCOUNT_BIC_PRINCIPAL")
	private String accountBicP;

	@Column(name = "BENEFICIARY_OPTION_PRINCIPAL")
	private String beneficiaryOptionP;

	@Column(name = "BENEFICIARY_ACCOUNT_PRINCIPAL")
	private String beneficiaryAccountP;

	@Column(name = "BENEFICIARY_NAME_ADDRESS_PRINCIPAL")
	private String beneficiaryNameAddressP;
	
	@Column(name = "BENEFICIARY_NUMBER_NAME_PRINCIPAL")
	private String beneficiaryNumberNameP;
	
	@Column(name = "BENEFICIARY_BIC_PRINCIPAL")
	private String beneficiaryBicP;

	@Column(name = "ACTIVO_CORRESPONSAL_PRINCIPAL")
	private Boolean activoCorresponsalPrincipal;

	@Column(name = "INTERMEDIARY_OPTION_BACKUP")
	private String intermediaryOptionB;

	@Column(name = "INTERMEDIARY_PARTY_BACKUP")
	private String intermediaryPartyIdB;

	@Column(name = "INTERMEDIARY_NAME_ADDRESS_BACKUP")
	private String intermediaryNameAddressB;
	
	@Column(name = "INTERMEDIARY_BIC_BACKUP")
	private String intermediaryBicB;

	@Column(name = "ACCOUNT_OPTION_BACKUP")
	private String accountOptionB;

	@Column(name = "ACCOUNT_PARTY_BACKUP")
	private String accountPartyIdB;

	@Column(name = "ACCOUNT_NAME_ADDRESS_BACKUP")
	private String accountNameAddressB;

	@Column(name = "ACCOUNT_LOCATION_BACKUP")
	private String accountLocationB;
	
	@Column(name = "ACCOUNT_BIC_BACKUP")
	private String accountBicB;

	@Column(name = "BENEFICIARY_OPTION_BACKUP")
	private String beneficiaryOptionB;

	@Column(name = "BENEFICIARY_ACCOUNT_BACKUP")
	private String beneficiaryAccountB;

	@Column(name = "BENEFICIARY_NAME_ADDRESS_BACKUP")
	private String beneficiaryNameAddressB;
	
	@Column(name = "BENEFICIARY_NUMBER_NAME_BACKUP")
	private String beneficiaryNumberNameB;
	
	@Column(name = "BENEFICIARY_BIC_BACKUP")
	private String beneficiaryBicB;

	@Column(name = "ACTIVO_CORRESPONSAL_BACKUP")
	private Boolean activoCorresponsalBackup;

	public Long getIdCuentaCorresponsal103() {
		return idCuentaCorresponsal103;
	}

	public void setIdCuentaCorresponsal103(Long idCuentaCorresponsal103) {
		this.idCuentaCorresponsal103 = idCuentaCorresponsal103;
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

	public String getIntermediaryBicP() {
		return intermediaryBicP;
	}

	public void setIntermediaryBicP(String intermediaryBicP) {
		this.intermediaryBicP = intermediaryBicP;
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

	public String getAccountBicP() {
		return accountBicP;
	}

	public void setAccountBicP(String accountBicP) {
		this.accountBicP = accountBicP;
	}

	public String getBeneficiaryOptionP() {
		return beneficiaryOptionP;
	}

	public void setBeneficiaryOptionP(String beneficiaryOptionP) {
		this.beneficiaryOptionP = beneficiaryOptionP;
	}

	public String getBeneficiaryAccountP() {
		return beneficiaryAccountP;
	}

	public void setBeneficiaryAccountP(String beneficiaryAccountP) {
		this.beneficiaryAccountP = beneficiaryAccountP;
	}

	public String getBeneficiaryNameAddressP() {
		return beneficiaryNameAddressP;
	}

	public void setBeneficiaryNameAddressP(String beneficiaryNameAddressP) {
		this.beneficiaryNameAddressP = beneficiaryNameAddressP;
	}

	public String getBeneficiaryNumberNameP() {
		return beneficiaryNumberNameP;
	}

	public void setBeneficiaryNumberNameP(String beneficiaryNumberNameP) {
		this.beneficiaryNumberNameP = beneficiaryNumberNameP;
	}

	public String getBeneficiaryBicP() {
		return beneficiaryBicP;
	}

	public void setBeneficiaryBicP(String beneficiaryBicP) {
		this.beneficiaryBicP = beneficiaryBicP;
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

	public String getIntermediaryBicB() {
		return intermediaryBicB;
	}

	public void setIntermediaryBicB(String intermediaryBicB) {
		this.intermediaryBicB = intermediaryBicB;
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

	public String getAccountBicB() {
		return accountBicB;
	}

	public void setAccountBicB(String accountBicB) {
		this.accountBicB = accountBicB;
	}

	public String getBeneficiaryOptionB() {
		return beneficiaryOptionB;
	}

	public void setBeneficiaryOptionB(String beneficiaryOptionB) {
		this.beneficiaryOptionB = beneficiaryOptionB;
	}

	public String getBeneficiaryAccountB() {
		return beneficiaryAccountB;
	}

	public void setBeneficiaryAccountB(String beneficiaryAccountB) {
		this.beneficiaryAccountB = beneficiaryAccountB;
	}

	public String getBeneficiaryNameAddressB() {
		return beneficiaryNameAddressB;
	}

	public void setBeneficiaryNameAddressB(String beneficiaryNameAddressB) {
		this.beneficiaryNameAddressB = beneficiaryNameAddressB;
	}

	public String getBeneficiaryNumberNameB() {
		return beneficiaryNumberNameB;
	}

	public void setBeneficiaryNumberNameB(String beneficiaryNumberNameB) {
		this.beneficiaryNumberNameB = beneficiaryNumberNameB;
	}

	public String getBeneficiaryBicB() {
		return beneficiaryBicB;
	}

	public void setBeneficiaryBicB(String beneficiaryBicB) {
		this.beneficiaryBicB = beneficiaryBicB;
	}

	public Boolean getActivoCorresponsalBackup() {
		return activoCorresponsalBackup;
	}

	public void setActivoCorresponsalBackup(Boolean activoCorresponsalBackup) {
		this.activoCorresponsalBackup = activoCorresponsalBackup;
	}

}
