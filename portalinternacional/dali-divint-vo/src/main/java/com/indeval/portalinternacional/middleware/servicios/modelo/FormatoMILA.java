/*
 * Copyrigth (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


 @Entity
 @Table(name="t_campos_formato_mila")
 @SequenceGenerator(name = "foliador", sequenceName = "ID_CAMPOS_FORMATO_MILA_SEQ", allocationSize = 1, initialValue = 1)
public class FormatoMILA implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idCamposFormatoMILA;
    
    private MILATipoDocumento tipoDocumentoIndentidad;
    
    private String numeroDocumento;
    
    private String identificadorMILA;
    
    private Boolean residente;
    
    private MILASectorEconomico sectorEconomico;
    
    private MILATipoEmpresa caracterEntidad;
    
    private String direccionEmail;
    
    private String telefono;
    
    private String fax;
    
    private PaisInt paisNacionalidad;
    
    private PaisInt paisResidencia;
    
    private PaisInt paisDireccion;
    
    private MILACodigoDepartamento codigoEstadoEntidad;
    
    private String rfc;
    
    private String referencia;
    
    private String nombreDocumento;
    
    private FormatoMilaArchivos archivos;


     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
     @Column(name ="id_campos_formato_mila", unique = false, nullable = false)
    public Long getIdCamposFormatoMILA() {
        return idCamposFormatoMILA;
    }

	/**
	 * @return the tipoDocumentoIndentidad
	 */
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name ="id_tipo_documento_identidad", nullable = false)
	public MILATipoDocumento getTipoDocumentoIndentidad() {
		return tipoDocumentoIndentidad;
	}


	/**
	 * @return the numeroDocumento
	 */
    @Column(name ="numero_documento_identidad", unique = false, nullable = false)
	public String getNumeroDocumento() {
		return numeroDocumento;
	}


	/**
	 * @return the identificadorMILA
	 */
    @Column(name ="indentificador_mila", unique = false, nullable = false)
	public String getIdentificadorMILA() {
		return identificadorMILA;
	}


	/**
	 * @return the residente
	 */
    @Column(name ="indicador_residente", unique = false, nullable = false)
	public Boolean getResidente() {
		return residente;
	}


	/**
	 * @return the sectorEconomico
	 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="id_sector_economico", nullable = true)
	public MILASectorEconomico getSectorEconomico() {
		return sectorEconomico;
	}


	/**
	 * @return the caracterEntidad
	 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="id_caracter_entidad", nullable = true)
	public MILATipoEmpresa getCaracterEntidad() {
		return caracterEntidad;
	}


	/**
	 * @return the direccionEmail
	 */
    @Column(name ="direccion_email", unique = false, nullable = true)
	public String getDireccionEmail() {
		return direccionEmail;
	}


	/**
	 * @return the telefono
	 */
    @Column(name ="telefono", unique = false, nullable = true)
	public String getTelefono() {
		return telefono;
	}


	/**
	 * @return the fax
	 */
    @Column(name ="fax", unique = false, nullable = true)
	public String getFax() {
		return fax;
	}


	/**
	 * @return the paisNacionalidad
	 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="id_pais_nacionalidad", nullable = false)
	public PaisInt getPaisNacionalidad() {
		return paisNacionalidad;
	}


	/**
	 * @return the paisResidencia
	 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="id_pais_residencia", nullable = false)
	public PaisInt getPaisResidencia() {
		return paisResidencia;
	}


	/**
	 * @return the paisDireccion
	 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="id_pais_direccion", nullable = false)
	public PaisInt getPaisDireccion() {
		return paisDireccion;
	}


	/**
	 * @return the codigoEstadoEntidad
	 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="id_codigo_departamento", nullable = true)
	public MILACodigoDepartamento getCodigoEstadoEntidad() {
		return codigoEstadoEntidad;
	}
    
	/**
	 * @return the rfc
	 */
	@Column(name ="rfc", unique = false, nullable = true)
	public String getRfc() {
		return rfc;
	}
    
	/**
	 * @return the referencia
	 */
	@Column(name ="referencia", unique = false, nullable = true)
	public String getReferencia() {
		return referencia;
	}

	/**
	 * @return the nombreDocumento
	 */
	@Column(name ="nombre_documento", unique = false, nullable = true)
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	
	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public FormatoMilaArchivos getArchivos() {
		return archivos;
	}

	/**
	 * @param idCamposFormatoMILA the idCamposFormatoMILA to set
	 */
	public void setIdCamposFormatoMILA(Long idCamposFormatoMILA) {
		this.idCamposFormatoMILA = idCamposFormatoMILA;
	}


	/**
	 * @param tipoDocumentoIndentidad the tipoDocumentoIndentidad to set
	 */
	public void setTipoDocumentoIndentidad(MILATipoDocumento tipoDocumentoIndentidad) {
		this.tipoDocumentoIndentidad = tipoDocumentoIndentidad;
	}


	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}


	/**
	 * @param identificadorMILA the identificadorMILA to set
	 */
	public void setIdentificadorMILA(String identificadorMILA) {
		this.identificadorMILA = identificadorMILA;
	}


	/**
	 * @param residente the residente to set
	 */
	public void setResidente(Boolean residente) {
		this.residente = residente;
	}


	/**
	 * @param sectorEconomico the sectorEconomico to set
	 */
	public void setSectorEconomico(MILASectorEconomico sectorEconomico) {
		this.sectorEconomico = sectorEconomico;
	}


	/**
	 * @param direccionEmail the direccionEmail to set
	 */
	public void setDireccionEmail(String direccionEmail) {
		this.direccionEmail = direccionEmail;
	}


	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}


	/**
	 * @param paisNacionalidad the paisNacionalidad to set
	 */
	public void setPaisNacionalidad(PaisInt paisNacionalidad) {
		this.paisNacionalidad = paisNacionalidad;
	}


	/**
	 * @param paisResidencia the paisResidencia to set
	 */
	public void setPaisResidencia(PaisInt paisResidencia) {
		this.paisResidencia = paisResidencia;
	}


	/**
	 * @param paisDireccion the paisDireccion to set
	 */
	public void setPaisDireccion(PaisInt paisDireccion) {
		this.paisDireccion = paisDireccion;
	}


	/**
	 * @param codigoEstadoEntidad the codigoEstadoEntidad to set
	 */
	public void setCodigoEstadoEntidad(MILACodigoDepartamento codigoEstadoEntidad) {
		this.codigoEstadoEntidad = codigoEstadoEntidad;
	}

	/**
	 * @param caracterEntidad the caracterEntidad to set
	 */
	public void setCaracterEntidad(MILATipoEmpresa caracterEntidad) {
		this.caracterEntidad = caracterEntidad;
	}

	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	/**
	 * @param nombreDocumento the nombreDocumento to set
	 */
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	/**
	 * @param archivos the archivos to set
	 */
	public void setArchivos(FormatoMilaArchivos archivos) {
		this.archivos = archivos;
	}
}