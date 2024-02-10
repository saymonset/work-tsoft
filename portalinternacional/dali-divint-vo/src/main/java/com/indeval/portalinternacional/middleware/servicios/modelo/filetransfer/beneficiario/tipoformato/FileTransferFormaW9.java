package com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("formaW9")
public class FileTransferFormaW9 extends FileTransferForma {

	
	
	@XStreamAlias("formato") 
	@XStreamAsAttribute
	private String tipoFormato;
	
	@XStreamAlias("beneficiario") 
	@XStreamAsAttribute
	private String tipoBeneficiario;
	
	@XStreamAlias("custodio") 
	@XStreamAsAttribute
	private String claveCustodio;
	
	@XStreamAlias("idCuentaNombrada") 
	@XStreamAsAttribute
	private Long idCuentaNombrada;
	
	@XStreamAlias("esPersonaFisica") 
	@XStreamAsAttribute
	private Boolean personaFisica;
	
	@XStreamAlias("institucion") 
	@XStreamAsAttribute
	private String claveInstitucion;
	
	@XStreamAlias("idInstitucion") 
	@XStreamAsAttribute
	private Long idInstitucion;
	
	@XStreamAlias("fechaFormato") 
	@XStreamAsAttribute
	private String fechaFormato;
		
	@XStreamAlias("estado") 
	@XStreamAsAttribute
	private String estado;
	

	@XStreamAlias("razonSocial") 
	@XStreamAsAttribute
	private String razonSocial;
	
	@XStreamAlias("bussinesName") 
	@XStreamAsAttribute
	private String bussinesName;
	
	@XStreamAlias("tipoTaxPayer") 
	@XStreamAsAttribute
	private String tipoTaxPayer;
	
	@XStreamAlias("descripcionTaxPayer") 
	@XStreamAsAttribute
	private String descripcionTaxPayer;
	
	@XStreamAlias("idTaxPayer") 
	@XStreamAsAttribute
	private Long idTaxPayer;
	
	@XStreamAlias("taxClassification") 
	@XStreamAsAttribute
	private String taxClassification;
	
	@XStreamAlias("exemptPayeeCode") 
	@XStreamAsAttribute
	private String exemptPayeeCode;
	
	@XStreamAlias("fatcaReportingCode") 
	@XStreamAsAttribute
	private String fatcaReportingCode;
	
	@XStreamAlias("street") 
	@XStreamAsAttribute
	private String street;
	
	@XStreamAlias("outerNumber") 
	@XStreamAsAttribute
	private String outerNumber;
	
	@XStreamAlias("interiorNumber") 
	@XStreamAsAttribute
	private String interiorNumber;
	
	@XStreamAlias("zipCode") 
	@XStreamAsAttribute
	private String zipCode;
	
	@XStreamAlias("city") 
	@XStreamAsAttribute
	private String city;
	
	@XStreamAlias("state") 
	@XStreamAsAttribute
	private String state;
	
	@XStreamAlias("requesterNameAddress") 
	@XStreamAsAttribute
	private String requesterNameAddress;
	
	@XStreamAlias("listAccountNumbers") 
	@XStreamAsAttribute
	private String listAccountNumbers;
	
	@XStreamAlias("ssn") 
	@XStreamAsAttribute
	private String ssn;
	
	@XStreamAlias("employerIdNumb") 
	@XStreamAsAttribute
	private String employerIdNumb;
	
	
	

	@XStreamAlias("cuentaConBusinessName") 
	@XStreamAsAttribute
	private Boolean cuentaConBusinessName;
	
	@XStreamAlias("cuentaConNumeroInterior") 
	@XStreamAsAttribute
	private Boolean cuentaConNumeroInterior;
	
	@XStreamAlias("cuentaConRequesterNameAddress") 
	@XStreamAsAttribute
	private Boolean cuentaConRequesterNameAddress;
	
	@XStreamAlias("cuentaConListAccountNumber") 
	@XStreamAsAttribute
	private Boolean cuentaConListAccountNumber;
	
	@XStreamAlias("esActivo") 
	@XStreamAsAttribute
	private Boolean activo;
	
	@XStreamAlias("fechaFormatoDate") 
	@XStreamAsAttribute
	private Date fechaFormatoDate;
	
	@XStreamAlias("idExemptPayee") 
	@XStreamAsAttribute
	private Long idExemptPayee;
	
	@XStreamAlias("idFatcaCode") 
	@XStreamAsAttribute
	private Long idFatcaCode;

	public String getTipoFormato() {
		return tipoFormato;
	}

	public void setTipoFormato(String tipoFormato) {
		this.tipoFormato = tipoFormato;
	}

	public String getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	public String getClaveCustodio() {
		return claveCustodio;
	}

	public void setClaveCustodio(String claveCustodio) {
		this.claveCustodio = claveCustodio;
	}

	public Long getIdCuentaNombrada() {
		return idCuentaNombrada;
	}

	public void setIdCuentaNombrada(Long idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
	}

	
	public Boolean getPersonaFisica() {
		return personaFisica;
	}

	public void setPersonaFisica(Boolean personaFisica) {
		this.personaFisica = personaFisica;
	}

	public String getClaveInstitucion() {
		return claveInstitucion;
	}

	public void setClaveInstitucion(String claveInstitucion) {
		this.claveInstitucion = claveInstitucion;
	}

	public Long getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getFechaFormato() {
		return fechaFormato;
	}

	public void setFechaFormato(String fechaFormato) {
		this.fechaFormato = fechaFormato;
	}

	public String getEstado() {		
		return estado;
	}

	public void setEstado(String estado) {		
		this.estado = estado;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getBussinesName() {
		return bussinesName;
	}

	public void setBussinesName(String bussinesName) {
		this.bussinesName = bussinesName;
	}

	public String getTipoTaxPayer() {
		return tipoTaxPayer;
	}

	public void setTipoTaxPayer(String tipoTaxPayer) {
		this.tipoTaxPayer = tipoTaxPayer;
	}

	public String getDescripcionTaxPayer() {
		return descripcionTaxPayer;
	}

	public void setDescripcionTaxPayer(String descripcionTaxPayer) {
		this.descripcionTaxPayer = descripcionTaxPayer;
	}

	public Long getIdTaxPayer() {
		return idTaxPayer;
	}

	public void setIdTaxPayer(Long idTaxPayer) {
		this.idTaxPayer = idTaxPayer;
	}

	public String getTaxClassification() {
		return taxClassification;
	}

	public void setTaxClassification(String taxClassification) {
		this.taxClassification = taxClassification;
	}

	public String getExemptPayeeCode() {
		return exemptPayeeCode;
	}

	public void setExemptPayeeCode(String exemptPayeeCode) {
		this.exemptPayeeCode = exemptPayeeCode;
	}

	public String getFatcaReportingCode() {
		return fatcaReportingCode;
	}

	public void setFatcaReportingCode(String fatcaReportingCode) {
		this.fatcaReportingCode = fatcaReportingCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getOuterNumber() {
		return outerNumber;
	}

	public void setOuterNumber(String outerNumber) {
		this.outerNumber = outerNumber;
	}

	public String getInteriorNumber() {
		return interiorNumber;
	}

	public void setInteriorNumber(String interiorNumber) {
		this.interiorNumber = interiorNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRequesterNameAddress() {
		return requesterNameAddress;
	}

	public void setRequesterNameAddress(String requesterNameAddress) {
		this.requesterNameAddress = requesterNameAddress;
	}

	public String getListAccountNumbers() {
		return listAccountNumbers;
	}

	public void setListAccountNumbers(String listAccountNumbers) {
		this.listAccountNumbers = listAccountNumbers;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getEmployerIdNumb() {
		return employerIdNumb;
	}

	public void setEmployerIdNumb(String employerIdNumb) {
		this.employerIdNumb = employerIdNumb;
	}

	
	

	public Boolean getCuentaConBusinessName() {
		return cuentaConBusinessName;
	}

	public void setCuentaConBusinessName(Boolean cuentaConBusinessName) {
		this.cuentaConBusinessName = cuentaConBusinessName;
	}

	public Boolean getCuentaConNumeroInterior() {
		return cuentaConNumeroInterior;
	}

	public void setCuentaConNumeroInterior(Boolean cuentaConNumeroInterior) {
		this.cuentaConNumeroInterior = cuentaConNumeroInterior;
	}

	public Boolean getCuentaConRequesterNameAddress() {
		return cuentaConRequesterNameAddress;
	}

	public void setCuentaConRequesterNameAddress(
			Boolean cuentaConRequesterNameAddress) {
		this.cuentaConRequesterNameAddress = cuentaConRequesterNameAddress;
	}

	public Boolean getCuentaConListAccountNumber() {
		return cuentaConListAccountNumber;
	}

	public void setCuentaConListAccountNumber(Boolean cuentaConListAccountNumber) {
		this.cuentaConListAccountNumber = cuentaConListAccountNumber;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Date getFechaFormatoDate() {
		return fechaFormatoDate;
	}

	public void setFechaFormatoDate(Date fechaFormatoDate) {
		this.fechaFormatoDate = fechaFormatoDate;
	}

	public Long getIdExemptPayee() {
		return idExemptPayee;
	}

	public void setIdExemptPayee(Long idExemptPayee) {
		this.idExemptPayee = idExemptPayee;
	}

	public Long getIdFatcaCode() {
		return idFatcaCode;
	}

	public void setIdFatcaCode(Long idFatcaCode) {
		this.idFatcaCode = idFatcaCode;
	}

	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {return false;}
		if(obj == this) {return true;}
		if(!(obj instanceof FileTransferFormaW9)) {return false;}

		FileTransferFormaW9 other=(FileTransferFormaW9)obj;
		
		boolean isEquals = false;
		final EqualsBuilder eqb = new EqualsBuilder();

		eqb.append(razonSocial,other.razonSocial);
		eqb.append(claveCustodio,other.claveCustodio);
		eqb.append(tipoBeneficiario,other.tipoBeneficiario);
		
		isEquals=eqb.isEquals();

		return isEquals;

		
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,31).
				append(razonSocial!=null?razonSocial:0).
				append(claveCustodio!=null?claveCustodio:0).
				append(tipoBeneficiario!=null?tipoBeneficiario:0).toHashCode();		
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileTransferFormaW9 [razonSocial=");
		builder.append(razonSocial);
		builder.append(", claveCustodio=");
		builder.append(claveCustodio);
		builder.append(", tipoBeneficiario=");
		builder.append(tipoBeneficiario);
		builder.append(", numeroRegistro=");
		builder.append(getNumeroRegistro());
		builder.append("]");
		return builder.toString();
	}
	

	
}
