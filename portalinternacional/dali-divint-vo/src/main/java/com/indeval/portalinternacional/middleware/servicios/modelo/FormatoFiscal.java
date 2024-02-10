package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_FORMATOS_FISCALES_ING")
@SequenceGenerator(name = "formatoFiscal", sequenceName = "SEQ_T_FORMATOS_FISCALES_ING", allocationSize = 1, initialValue = 1)
public class FormatoFiscal implements Serializable {
	
    /**
     * Constante de serializacion por default
     */
    private static final long serialVersionUID = 1L;
    
    private Long formatoFiscalesID;
    
    private Long beneficiarioID;
    
    private String descriptionTipoBeneficiario;
    
    private Long tipoBeneficiario;
    
    private Long servicioID;
    
    private String tipoFormato;
    
    private Long statusFormatoID;
    
    private Date fechaIngreso;
    
    private Date fechaAutorizacion;
    
    private Boolean eliminado;
    
    private Long institucionID;
    
    private Long cuentaNombrada;
    
    private String custodio;
    
    private Long tipoInstitucionID;
    
    private String folioInstitucion;
    
    private String nombreBeneficiario;
    
    private String apellidoPaternoBeneficiario;
    
    private String apellidoMaternoBeneficiario;
    
    private String razonSocialBeneficiario;
    
    private String uoi;
    
    private String taxID;
    
    private String referenceNumber;
    
    private Boolean estado;
    
    private Boolean extraer;

    public FormatoFiscal(){
    	
    }
    
	/**
	 * @return the formatoFiscalesID
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "formatoFiscal")
    @Column(name = "ID_FORMATO_FISCALES", unique = true, nullable = false)
	public Long getFormatoFiscalesID() {
		return formatoFiscalesID;
	}

	/**
	 * @param formatoFiscalesID the formatoFiscalesID to set
	 */
	public void setFormatoFiscalesID(Long formatoFiscalesID) {
		this.formatoFiscalesID = formatoFiscalesID;
	}

	/**
	 * @return the beneficiarioID
	 */
    @Column(name = "ID_BENEFICIARIO", unique = false, nullable = false)
	public Long getBeneficiarioID() {
		return beneficiarioID;
	}

	/**
	 * @param beneficiarioID the beneficiarioID to set
	 */
	public void setBeneficiarioID(Long beneficiarioID) {
		this.beneficiarioID = beneficiarioID;
	}
	
	/**
	 * @return the servicioID
	 */
    @Column(name = "ID_SERVICIO", unique = false, nullable = false)
	public Long getServicioID() {
		return servicioID;
	}

	/**
	 * @return the descriptionTipoBeneficiario
	 */
    @Column(name = "DESC_TIPO_BENEFICIARIO", unique = false, nullable = true)
	public String getDescriptionTipoBeneficiario() {
		return descriptionTipoBeneficiario;
	}

	/**
	 * @param descriptionTipoBeneficiario the descriptionTipoBeneficiario to set
	 */
	public void setDescriptionTipoBeneficiario(String descriptionTipoBeneficiario) {
		this.descriptionTipoBeneficiario = descriptionTipoBeneficiario;
	}

	/**
	 * @param beneficiarioID the beneficiarioID to set
	 */
	public void setServicioID(Long servicioID) {
		this.servicioID = servicioID;
	}

	/**
	 * @return the tipoFormato
	 */
    @Column(name = "TIPO_FORMATO", unique = false, nullable = true)
	public String getTipoFormato() {
		return tipoFormato;
	}

	/**
	 * @param tipoFormato the tipoFormato to set
	 */
	public void setTipoFormato(String tipoFormato) {
		this.tipoFormato = tipoFormato;
	}

	/**
	 * @return the statusFormatoID
	 */
    @Column(name = "ID_STATUS_FORMATO", unique = false, nullable = false)
	public Long getStatusFormatoID() {
		return statusFormatoID;
	}

	/**
	 * @param statusFormatoID the statusFormatoID to set
	 */
	public void setStatusFormatoID(Long statusFormatoID) {
		this.statusFormatoID = statusFormatoID;
	}

	/**
	 * @return the fechaAutorizacion
	 */
    @Column(name = "FECHA_AUTORIZACION", unique = false, nullable = false)
	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	/**
	 * @param fechaAutorizacion the fechaAutorizacion to set
	 */
	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}
	
	/**
	 * @return the fechaIngreso
	 */
	@Column(name = "FECHA_INGRESO", unique = false, nullable = false)
	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	/**
	 * @param fechaIngreso the fechaIngreso to set
	 */
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	/**
	 * @return the eliminado
	 */
    @Column(name = "ELIMINADO", unique = false, nullable = false)
	public Boolean getEliminado() {
		return eliminado;
	}

	/**
	 * @param eliminado the eliminado to set
	 */
	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}

	/**
	 * @return the institucionID
	 */
    @Column(name = "ID_INSTITUCION", unique = false, nullable = true)
	public Long getInstitucionID() {
		return institucionID;
	}

	/**
	 * @param institucionID the institucionID to set
	 */
	public void setInstitucionID(Long institucionID) {
		this.institucionID = institucionID;
	}

	/**
	 * @return the tipoInstitucionID
	 */
    @Column(name = "ID_TIPO_INSTITUCION", unique = false, nullable = true)
	public Long getTipoInstitucionID() {
		return tipoInstitucionID;
	}

	/**
	 * @param tipoInstitucionID the tipoInstitucionID to set
	 */
	public void setTipoInstitucionID(Long tipoInstitucionID) {
		this.tipoInstitucionID = tipoInstitucionID;
	}

	/**
	 * @return the folioInstitucion
	 */
    @Column(name = "FOLIO_INSTITUCION", unique = false, nullable = true)
	public String getFolioInstitucion() {
		return folioInstitucion;
	}

	/**
	 * @param folioInstitucion the folioInstitucion to set
	 */
	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}

	/**
	 * @return the nombreBeneficiario
	 */
    @Column(name = "NOMBRE_BENEF", unique = false, nullable = true)
	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}

	/**
	 * @param nombreBeneficiario the nombreBeneficiario to set
	 */
	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}

	/**
	 * @return the apellidoPaternoBeneficiario
	 */
    @Column(name = "APELLIDO_PATERNO_BENEF", unique = false, nullable = true)
	public String getApellidoPaternoBeneficiario() {
		return apellidoPaternoBeneficiario;
	}

	/**
	 * @param apellidoPaternoBeneficiario the apellidoPaternoBeneficiario to set
	 */
	public void setApellidoPaternoBeneficiario(String apellidoPaternoBeneficiario) {
		this.apellidoPaternoBeneficiario = apellidoPaternoBeneficiario;
	}

	/**
	 * @return the apellidoMaternoBeneficiario
	 */
    @Column(name = "APELLIDO_MATERNO_BENEF", unique = false, nullable = true)
	public String getApellidoMaternoBeneficiario() {
		return apellidoMaternoBeneficiario;
	}

	/**
	 * @param apellidoMaternoBeneficiario the apellidoMaternoBeneficiario to set
	 */
	public void setApellidoMaternoBeneficiario(String apellidoMaternoBeneficiario) {
		this.apellidoMaternoBeneficiario = apellidoMaternoBeneficiario;
	}

	/**
	 * @return the razonSocialBeneficiario
	 */
    @Column(name = "RAZON_SOCIAL_BENEF", unique = false, nullable = true)
	public String getRazonSocialBeneficiario() {
		return razonSocialBeneficiario;
	}

	/**
	 * @param razonSocialBeneficiario the razonSocialBeneficiario to set
	 */
	public void setRazonSocialBeneficiario(String razonSocialBeneficiario) {
		this.razonSocialBeneficiario = razonSocialBeneficiario;
	}

	/**
	 * @return the cuentaNombrada
	 */
    @Column(name = "CUENTA_NOMBRADA", unique = false, nullable = false)
	public Long getCuentaNombrada() {
		return cuentaNombrada;
	}

	/**
	 * @param cuentaNombrada the cuentaNombrada to set
	 */
	public void setCuentaNombrada(Long cuentaNombrada) {
		this.cuentaNombrada = cuentaNombrada;
	}

	/**
	 * @return the uoi
	 */
    @Column(name = "UOI", unique = false, nullable = true)
	public String getUoi() {
		return uoi;
	}

	/**
	 * @param uoi the uoi to set
	 */
	public void setUoi(String uoi) {
		this.uoi = uoi;
	}

	/**
	 * @return the taxID
	 */
    @Column(name = "TAXID", unique = false, nullable = true)
	public String getTaxID() {
		return taxID;
	}

	/**
	 * @param taxID the taxID to set
	 */
	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}

	/**
	 * @return the referenceNumber
	 */
    @Column(name = "REFERENCE_NUMBER", unique = false, nullable = true)
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
	 * @return the estado
	 */
    @Column(name = "ESTADO", unique = false, nullable = true)
	public Boolean getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	/**
	 * @return the extraer
	 */
    @Column(name = "EXTRAER", unique = false, nullable = true)
	public Boolean getExtraer() {
		return extraer;
	}

	/**
	 * @param extraer the extraer to set
	 */
	public void setExtraer(Boolean extraer) {
		this.extraer = extraer;
	}

	/**
	 * @return the tipoBeneficiario
	 */
    @Column(name = "TIPO_BENEFICIARIO", unique = false, nullable = false)
	public Long getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	/**
	 * @param tipoBeneficiario the tipoBeneficiario to set
	 */
	public void setTipoBeneficiario(Long tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	/**
	 * @return the custodio
	 */
    @Column(name = "CUSTODIO", unique = false, nullable = true)
	public String getCustodio() {
		return custodio;
	}

	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}

}
