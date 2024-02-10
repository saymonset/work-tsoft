package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.util.Date;

public class CamposW {
	
	   private String tiin; 	
	   private String ssn;   
	   private String foreigntiin;
	   private String referenceNumber;
	   private String giin;
	   private String exemptPayeeCode;
	   private Date fechaNacimiento;
	   private String exemptionFromFatcaRepCode;
	   private String rfc;
	/**
	 * @return the tiin
	 */
	public String getTiin() {
		return tiin;
	}
	/**
	 * @param tiin the tiin to set
	 */
	public void setTiin(String tiin) {
		this.tiin = tiin;
	}
	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}
	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	/**
	 * @return the foreigntiin
	 */
	public String getForeigntiin() {
		return foreigntiin;
	}
	/**
	 * @param foreigntiin the foreigntiin to set
	 */
	public void setForeigntiin(String foreigntiin) {
		this.foreigntiin = foreigntiin;
	}
	/**
	 * @return the referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}
	/**
	 * @param referenceNumber the referenceNumber to set
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	/**
	 * @return the giin
	 */
	public String getGiin() {
		return giin;
	}
	/**
	 * @param giin the giin to set
	 */
	public void setGiin(String giin) {
		this.giin = giin;
	}
	/**
	 * @return the exemptPayeeCode
	 */
	public String getExemptPayeeCode() {
		return exemptPayeeCode;
	}
	/**
	 * @param exemptPayeeCode the exemptPayeeCode to set
	 */
	public void setExemptPayeeCode(String exemptPayeeCode) {
		this.exemptPayeeCode = exemptPayeeCode;
	}
	/**
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	/**
	 * @return the exemptionFromFatcaRepCode
	 */
	public String getExemptionFromFatcaRepCode() {
		return exemptionFromFatcaRepCode;
	}
	/**
	 * @param exemptionFromFatcaRepCode the exemptionFromFatcaRepCode to set
	 */
	public void setExemptionFromFatcaRepCode(String exemptionFromFatcaRepCode) {
		this.exemptionFromFatcaRepCode = exemptionFromFatcaRepCode;
	}
	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}
	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	@Override
	public String toString() {
		return "CamposW [tiin=" + tiin + ", ssn=" + ssn + ", foreigntiin=" + foreigntiin + ", referenceNumber="
				+ referenceNumber + ", giin=" + giin + ", exemptPayeeCode=" + exemptPayeeCode + ", fechaNacimiento="
				+ fechaNacimiento + ", exemptionFromFatcaRepCode=" + exemptionFromFatcaRepCode + ", rfc=" + rfc + "]";
	}

}
