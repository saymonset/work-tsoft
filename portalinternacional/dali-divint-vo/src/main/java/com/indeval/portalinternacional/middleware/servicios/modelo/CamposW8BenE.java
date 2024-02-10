package com.indeval.portalinternacional.middleware.servicios.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.CamposW;

@Entity
@Table(name = "T_CAMPOS_FORMATO_W8BENE" )
public class CamposW8BenE extends CamposW {
	 	    
	private Long id;
  
	/**
	 * @return the tiin
	 */
	@Column(name = "US_TIN", unique = false, nullable = true, insertable = false, updatable =false)
	public String getTiin() {
		return super.getTiin();
	}
	/**
	 * @param tiin the tiin to set
	 */
	public void setTiin(String tiin) {
		super.setTiin( tiin);
	}
	
	/**
	 * @return the foreigntiin
	 */
	@Column(name = "FOREING_TIN", unique = false, nullable = true, insertable = false, updatable =false)
	public String getForeigntiin() {
		return super.getForeigntiin();
	}
	/**
	 * @param foreigntiin the foreigntiin to set
	 */
	public void setForeigntiin(String foreigntiin) {
		super.setForeigntiin(foreigntiin);
	}
	/**
	 * @return the referenceNumber
	 */
	@Column(name = "REFERENCE_NUMBER", unique = false, nullable = true, insertable = false, updatable =false)
	public String getReferenceNumber() {
		return super.getReferenceNumber();
	}
	/**
	 * @param referenceNumber the referenceNumber to set
	 */
	public void setReferenceNumber(String referenceNumber) {
		super.setReferenceNumber( referenceNumber);
	}
	/**
	 * @return the giin
	 */
	@Column(name = "GIIN", unique = false, nullable = true, insertable = false, updatable =false)
	public String getGiin() {
		return super.getGiin();
	}
	/**
	 * @param giin the giin to set
	 */
	public void setGiin(String giin) {
		super.setGiin(giin);
	}
	
	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID_CAMPOS_FORMATO_W8BENE", unique = true, nullable = false, insertable = false, updatable =false)
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the rfc
	 */
	@Column(name = "rfc", unique = false, nullable = true, insertable = false, updatable =false)
	public String getRfc() {
		return super.getRfc();
	}
	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		super.setRfc( rfc);
	}
	   
}
