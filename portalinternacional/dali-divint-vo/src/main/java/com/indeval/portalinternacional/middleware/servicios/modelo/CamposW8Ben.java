package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.CamposW;

@Entity
@Table(name = "T_CAMPOS_FORMATO_W8BEN" )
public class CamposW8Ben extends CamposW {
	 	    
	private Long id;
  
	/**
	 * @return the tiin
	 */
	@Column(name = "TAXPAYER_ID_NUMB", unique = false, nullable = true, insertable = false, updatable =false)
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
	@Column(name = "FOREIGN_TAX_ID_NUMB", unique = false, nullable = true, insertable = false, updatable =false)
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
	@Column(name = "REFERENCE_NUMBERS", unique = false, nullable = true, insertable = false, updatable =false)
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
	 * @return the fechaNacimiento
	 */
	@Column(name = "FECHA_NACIMIENTO", unique = false, nullable = true, insertable = false, updatable =false)
	public Date getFechaNacimiento() {
		return super.getFechaNacimiento();
	}
	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		super.setFechaNacimiento( fechaNacimiento);
	}
	
	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID_CAMPOS_FORMATO_W8BEN", unique = true, nullable = false, insertable = false, updatable =false)
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
