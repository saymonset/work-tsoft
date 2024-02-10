package com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("formaW8BEN2014")
public class FileTransferFormaW8BEN2014 extends FileTransferForma {

	@XStreamAsAttribute 
	private String nombre;
	
	@XStreamAsAttribute
	private String apellidoPaterno;
	
	@XStreamAsAttribute
	private String apellidoMaterno;
	
	 
	@XStreamAsAttribute
	private String paisResidencia;
	
	 
	@XStreamAsAttribute
	private String residenceAddressStreet;
	
	 
	@XStreamAsAttribute
	private String residenceAddressOutNumber;
	
	 
	@XStreamAsAttribute
	private String residenceAddressIntNumber;
	
	 
	@XStreamAsAttribute
	private String residenceAddressPostalCode;
	
	 
	@XStreamAsAttribute
	private String residenceAddressCity;
	
	 
	@XStreamAsAttribute
	private String residenceAddressState;
	
	 
	@XStreamAsAttribute
	private String residenceAddressCountry;
	
	 
	@XStreamAsAttribute
	private String mailAddressStreet;
	
	 
	@XStreamAsAttribute
	private String mailAddressOutNumber;
	
	 
	@XStreamAsAttribute
	private String mailAddressIntNumber;
	
	 
	@XStreamAsAttribute
	private String mailAddressPostalCode;
	
	 
	@XStreamAsAttribute
	private String mailAddressCity;
	
	 
	@XStreamAsAttribute
	private String mailAddressState;
	
	 
	@XStreamAsAttribute
	private String mailAddressCountry;
	
	 
	@XStreamAsAttribute
	private String foreingTaxIdNumber;
	
	 
	@XStreamAsAttribute
	private String referenceNumber;
	
	 
	@XStreamAsAttribute
	private String fechaNacimiento;
	
	@XStreamOmitField
	private Date dateFechaNacimiento;
	
	@XStreamAsAttribute
	private String paisResidenteBeneficiaro;
	
	
	 
	@XStreamAsAttribute
	private String idArticuloReclamoTarifa;
	
	 
	@XStreamAsAttribute
	private String porcentajeTasaRetencion;
	
	
	 
	@XStreamAsAttribute
	private String tipoIngreso;
	
	 
	@XStreamAsAttribute
	private String razonArticuloReclamo;
	
	 
	@XStreamAsAttribute
	private String capacidadActuacion;
	
	 
	@XStreamAsAttribute
	private String nombreFirmante;


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getPaisResidencia() {
		return paisResidencia;
	}


	public void setPaisResidencia(String paisResidencia) {
		this.paisResidencia = paisResidencia;
	}


	public String getResidenceAddressStreet() {
		return residenceAddressStreet;
	}


	public void setResidenceAddressStreet(String residenceAddressStreet) {
		this.residenceAddressStreet = residenceAddressStreet;
	}


	public String getResidenceAddressOutNumber() {
		return residenceAddressOutNumber;
	}


	public void setResidenceAddressOutNumber(String residenceAddressOutNumber) {
		this.residenceAddressOutNumber = residenceAddressOutNumber;
	}


	public String getResidenceAddressIntNumber() {
		return residenceAddressIntNumber;
	}


	public void setResidenceAddressIntNumber(String residenceAddressIntNumber) {
		this.residenceAddressIntNumber = residenceAddressIntNumber;
	}


	public String getResidenceAddressPostalCode() {
		return residenceAddressPostalCode;
	}


	public void setResidenceAddressPostalCode(String residenceAddressPostalCode) {
		this.residenceAddressPostalCode = residenceAddressPostalCode;
	}


	public String getResidenceAddressCity() {
		return residenceAddressCity;
	}


	public void setResidenceAddressCity(String residenceAddressCity) {
		this.residenceAddressCity = residenceAddressCity;
	}


	public String getResidenceAddressState() {
		return residenceAddressState;
	}


	public void setResidenceAddressState(String residenceAddressState) {
		this.residenceAddressState = residenceAddressState;
	}


	public String getResidenceAddressCountry() {
		return residenceAddressCountry;
	}


	public void setResidenceAddressCountry(String residenceAddressCountry) {
		this.residenceAddressCountry = residenceAddressCountry;
	}


	public String getMailAddressStreet() {
		return mailAddressStreet;
	}


	public void setMailAddressStreet(String mailAddressStreet) {
		this.mailAddressStreet = mailAddressStreet;
	}


	public String getMailAddressOutNumber() {
		return mailAddressOutNumber;
	}


	public void setMailAddressOutNumber(String mailAddressOutNumber) {
		this.mailAddressOutNumber = mailAddressOutNumber;
	}


	public String getMailAddressIntNumber() {
		return mailAddressIntNumber;
	}


	public void setMailAddressIntNumber(String mailAddressIntNumber) {
		this.mailAddressIntNumber = mailAddressIntNumber;
	}


	public String getMailAddressPostalCode() {
		return mailAddressPostalCode;
	}


	public void setMailAddressPostalCode(String mailAddressPostalCode) {
		this.mailAddressPostalCode = mailAddressPostalCode;
	}


	public String getMailAddressCity() {
		return mailAddressCity;
	}


	public void setMailAddressCity(String mailAddressCity) {
		this.mailAddressCity = mailAddressCity;
	}


	public String getMailAddressState() {
		return mailAddressState;
	}


	public void setMailAddressState(String mailAddressState) {
		this.mailAddressState = mailAddressState;
	}


	public String getMailAddressCountry() {
		return mailAddressCountry;
	}


	public void setMailAddressCountry(String mailAddressCountry) {
		this.mailAddressCountry = mailAddressCountry;
	}


	public String getForeingTaxIdNumber() {
		return foreingTaxIdNumber;
	}


	public void setForeingTaxIdNumber(String foreingTaxIdNumber) {
		this.foreingTaxIdNumber = foreingTaxIdNumber;
	}


	public String getReferenceNumber() {
		return referenceNumber;
	}


	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}


	public String getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public String getIdArticuloReclamoTarifa() {
		return idArticuloReclamoTarifa;
	}


	public void setIdArticuloReclamoTarifa(String idArticuloReclamoTarifa) {
		this.idArticuloReclamoTarifa = idArticuloReclamoTarifa;
	}


	public String getPorcentajeTasaRetencion() {
		return porcentajeTasaRetencion;
	}


	public void setPorcentajeTasaRetencion(String porcentajeTasaRetencion) {
		this.porcentajeTasaRetencion = porcentajeTasaRetencion;
	}


	public String getTipoIngreso() {
		return tipoIngreso;
	}


	public void setTipoIngreso(String tipoIngreso) {
		this.tipoIngreso = tipoIngreso;
	}


	public String getRazonArticuloReclamo() {
		return razonArticuloReclamo;
	}


	public void setRazonArticuloReclamo(String razonArticuloReclamo) {
		this.razonArticuloReclamo = razonArticuloReclamo;
	}


	public String getCapacidadActuacion() {
		return capacidadActuacion;
	}


	public void setCapacidadActuacion(String capacidadActuacion) {
		this.capacidadActuacion = capacidadActuacion;
	}


	public String getNombreFirmante() {
		return nombreFirmante;
	}


	public void setNombreFirmante(String nombreFirmante) {
		this.nombreFirmante = nombreFirmante;
	}


	public String getPaisResidenteBeneficiaro() {
		return paisResidenteBeneficiaro;
	}


	public void setPaisResidenteBeneficiaro(String paisResidenteBeneficiaro) {
		this.paisResidenteBeneficiaro = paisResidenteBeneficiaro;
	}

	public Date getDateFechaNacimiento() {
		return dateFechaNacimiento;
	}


	public void setDateFechaNacimiento(Date dateFechaNacimiento) {
		this.dateFechaNacimiento = dateFechaNacimiento;
	}

	

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}


	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}


	public String getApellidoMaterno() {
		return apellidoMaterno;
	}


	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) {return false;}
		if(obj == this) {return true;}
		if(!(obj instanceof FileTransferFormaW8BEN2014)) {return false;}

		FileTransferFormaW8BEN2014 other=(FileTransferFormaW8BEN2014)obj;
		
		boolean isEquals = false;
		final EqualsBuilder eqb = new EqualsBuilder();

		eqb.append(nombre,other.nombre);
		eqb.append(apellidoPaterno,other.apellidoPaterno);
		eqb.append(apellidoMaterno,other.apellidoMaterno);
		eqb.append(getClaveCustodio(),other.getClaveCustodio());
		eqb.append(getTipoBeneficiario(),other.getTipoBeneficiario());
		
		isEquals=eqb.isEquals();

		return isEquals;

		
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,31).
				   append(nombre!=null?nombre:0).
				   append(apellidoPaterno != null ? apellidoPaterno : 0).
				   append(apellidoMaterno != null ? apellidoMaterno : 0).
				   append(getClaveCustodio() != null ? getClaveCustodio() : 0).
				   append(getTipoBeneficiario() != null ? getTipoBeneficiario() : 0).toHashCode();		
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nFileTransferFormaW8BEN2014 [nombre=");
		builder.append(nombre);
		builder.append(", apellidoPaterno=");
		builder.append(apellidoPaterno);
		builder.append(", apellidoMaterno=");
		builder.append(apellidoMaterno);		
		builder.append(", claveCustodio=");
		builder.append(getClaveCustodio());
		builder.append(", tipoBeneficiario=");
		builder.append(getTipoBeneficiario());		
		builder.append(", numeroRegistro=");
		builder.append(getNumeroRegistro());
		builder.append("]");
		return builder.toString();
	}
}
