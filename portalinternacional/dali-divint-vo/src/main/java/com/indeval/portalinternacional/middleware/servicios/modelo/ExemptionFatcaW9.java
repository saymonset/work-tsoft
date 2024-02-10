/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author César Hernández
 *
 */
@Entity
@Table(name = "C_EXEMPTION_FATCA_W9")
public class ExemptionFatcaW9 implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long idExemptionFatcaW9;
	private String fatcaCode;
	private String description;
	
	/**
	 * @return the idExemptionFatcaW9
	 */
	@Id
	@Column(name = "ID_EXEMPTION_FATCA_W9", unique = true, nullable = false)
	public Long getIdExemptionFatcaW9() {
		return idExemptionFatcaW9;
	}
	
	/**
	 * @param idExemptionFatcaW9 the idExemptionFatcaW9 to set
	 */
	public void setIdExemptionFatcaW9(Long idExemptionFatcaW9) {
		this.idExemptionFatcaW9 = idExemptionFatcaW9;
	}
	
	/**
	 * @return the fatcaCode
	 */
	@Column(name = "FATCA_CODE", unique = false, nullable = false)
	public String getFatcaCode() {
		return fatcaCode;
	}
	
	/**
	 * @param fatcaCode the fatcaCode to set
	 */
	public void setFatcaCode(String fatcaCode) {
		this.fatcaCode = fatcaCode;
	}
	
	/**
	 * @return the description
	 */
	@Column(name = "DESCRIPTION", unique = false, nullable = false)
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
