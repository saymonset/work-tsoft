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
@Table(name = "C_EXEMPT_PAYEE_W9")
public class ExemptPayeeW9 implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long idExemptPayeeW9;
	private String fatcaCode;
	private String description;
	
	/**
	 * @return the idExemptPayeeW9
	 */
	@Id
	@Column(name = "ID_EXEMPT_PAYEE_W9", unique = true, nullable = false)
	public Long getIdExemptPayeeW9() {
		return idExemptPayeeW9;
	}
	
	/**
	 * @param idExemptPayeeW9 the idExemptPayeeW9 to set
	 */
	public void setIdExemptPayeeW9(Long idExemptPayeeW9) {
		this.idExemptPayeeW9 = idExemptPayeeW9;
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
