/*
 * Copyrigth (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

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
import javax.persistence.Transient;

@Entity
@Table(name = "t_campos_formato_w9")
@SequenceGenerator(name = "foliador", sequenceName = "ID_CAMPOS_FORMATO_W9_SEQ", allocationSize = 1, initialValue = 1)
public class FormatoW9 implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idCamposFormatoW9;
	private String businessName;
	private Field3W9 typeTaxPayer;
	private String otherDescription;
	private String taxClassification;
	private Boolean exemptPayee;
	private String requesterNameAddress;
	private String listAccountNumbers;
	private String ssn;
	private String employerIdNumb;
	private ExemptPayeeW9 exemptPayeeW9;
	private ExemptionFatcaW9 exemptionFatcaW9;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "id_campos_formato_w9", unique = false, nullable = false)
	public Long getIdCamposFormatoW9() {
		return idCamposFormatoW9;
	}

	@Column(name = "business_name", unique = false, nullable = true)
	public String getBusinessName() {
		return businessName;
	}

	 @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_taxpayer", unique = false, nullable = true)
	public Field3W9 getTypeTaxPayer() {
		return typeTaxPayer;
	}

	@Column(name = "other_description", unique = false, nullable = true)
	public String getOtherDescription() {
		return otherDescription;
	}

	@Column(name = "tax_classification", unique = false, nullable = true)
	public String getTaxClassification() {
		return taxClassification;
	}

	@Column(name = "exempt_payee", unique = false, nullable = true)
	public Boolean getExemptPayee() {
		return exemptPayee;
	}

	@Transient
	public String getExemptPayeeString() {
		String retorno = "falso";
		if(exemptPayee != null && exemptPayee) {
			 retorno = "verdadero";
		}
		return retorno;
	}

	@Column(name = "requester_name_address", unique = false, nullable = true)
	public String getRequesterNameAddress() {
		return requesterNameAddress;
	}

	@Column(name = "list_account_numbers", unique = false, nullable = true)
	public String getListAccountNumbers() {
		return listAccountNumbers;
	}

	@Column(name = "ssn", unique = false, nullable = true)
	public String getSsn() {
		return ssn;
	}

	@Column(name = "employer_id_number", unique = false, nullable = true)
	public String getEmployerIdNumb() {
		return employerIdNumb;
	}
	
	/**
	 * @return the exemptPayeeW9
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_EXEMPT_PAYEE_CODE", unique = false, nullable = true)
	public ExemptPayeeW9 getExemptPayeeW9() {
		return exemptPayeeW9;
	}

	/**
	 * @return the exemptionFatcaW9
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_EXEMPTION_FATCA_CODE", unique = false, nullable = true)
	public ExemptionFatcaW9 getExemptionFatcaW9() {
		return exemptionFatcaW9;
	}

	public void setIdCamposFormatoW9(Long idCamposFormatoW9) {
		this.idCamposFormatoW9 = idCamposFormatoW9;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public void setTypeTaxPayer(Field3W9 typeTaxPayer) {
		this.typeTaxPayer = typeTaxPayer;
	}

	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}

	public void setTaxClassification(String taxClassification) {
		this.taxClassification = taxClassification;
	}

	public void setExemptPayee(Boolean exemptPayee) {
		this.exemptPayee = exemptPayee;
	}

	public void setRequesterNameAddress(String requesterNameAddress) {
		this.requesterNameAddress = requesterNameAddress;
	}

	public void setListAccountNumbers(String listAccountNumbers) {
		this.listAccountNumbers = listAccountNumbers;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public void setEmployerIdNumb(String employerIdNumb) {
		this.employerIdNumb = employerIdNumb;
	}
	
	/**
	 * @param exemptPayeeW9 the exemptPayeeW9 to set
	 */
	public void setExemptPayeeW9(ExemptPayeeW9 exemptPayeeW9) {
		this.exemptPayeeW9 = exemptPayeeW9;
	}

	/**
	 * @param exemptionFatcaW9 the exemptionFatcaW9 to set
	 */
	public void setExemptionFatcaW9(ExemptionFatcaW9 exemptionFatcaW9) {
		this.exemptionFatcaW9 = exemptionFatcaW9;
	}
}
