/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 27/02/2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * Data Transfer Object que representa un elemento del catálogo de
 * instituciones.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public class InstitucionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** El identificador de la institución */
	private long id = 0;

	/** El nombre corto asociado a la institución */
	private String nombreCorto = null;
	
	/** La clave del tipo de institucion */
	private String claveTipoInstitucion = null;
	
	private TipoInstitucionDTO tipoInstitucionDTO;

	/** El folio de la institución */
	private String folioInstitucion = null;

	/** La razón social de la institución */
	private String razonSocial = null;

	/** La cuenta CLABE ligada a la institución */
	private String cuentaClabe = null;
	
	/** El CASMIM  de la institucion */
	private String claveCasfim= null;

	/*
	 * Se agregan campos para la resolución del requerimiento R-IND-2014-010
	 * 02/10/2014 Pablo Balderas.
	 */
	
	/** Clave SPEI */
	private String claveSpei;
	
	/** Opera SIAC */
	private Boolean operaSiac;
	
	/**
	 * Cuenta clabe beneficiario
	 */
	private String cuentaClabeBeneficiario;
	
	/**
	 * Clave spei beneficiario
	 */
	private String claveSpeiBeneficiario;
	
	/**
	 * Constructor de la clase.
	 */
	public InstitucionDTO() {}

	 /**
	 * Constructor de la clase.
	 * @param id Id de la cuenta
	 */
	public InstitucionDTO(long id) {
		super();
		this.id = id;
	}

	/**
	 * método para obtener el atributo id
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * método para establecer el atributo id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * método para obtener el atributo nombreCorto
	 * 
	 * @return the nombreCorto
	 */
	public String getNombreCorto() {
		return nombreCorto;
	}

	/**
	 * Obtiene el valor del atributo razonSocial
	 * 
	 * @return el valor del atributo razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * Establece el valor del atributo razonSocial
	 * 
	 * @param razonSocial
	 *            el valor del atributo razonSocial a establecer
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * método para establecer el atributo nombreCorto
	 * 
	 * @param nombreCorto
	 *            the nombreCorto to set
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	/**
	 * Obtiene el valor del atributo cuentaClabe
	 * 
	 * @return el valor del atributo cuentaClabe
	 */
	public String getCuentaClabe() {
		return cuentaClabe;
	}

	/**
	 * Establece el valor del atributo cuentaClabe
	 * 
	 * @param cuentaClabe
	 *            el valor del atributo cuentaClabe a establecer
	 */
	public void setCuentaClabe(String cuentaClabe) {
		this.cuentaClabe = cuentaClabe;
	}

	/**
	 * Obtiene el campo claveTipoInstitucion
	 * 
	 * @return claveTipoInstitucion
	 */
	public String getClaveTipoInstitucion() {
		return claveTipoInstitucion;
	}

	/**
	 * Asigna el valor del campo claveTipoInstitucion
	 * 
	 * @param claveTipoInstitucion
	 *            el valor de claveTipoInstitucion a asignar
	 */
	public void setClaveTipoInstitucion(String claveTipoInstitucion) {
		this.claveTipoInstitucion = claveTipoInstitucion;
	}

	/**
	 * Obtiene el campo folioInstitucion
	 * 
	 * @return folioInstitucion
	 */
	public String getFolioInstitucion() {
		return folioInstitucion;
	}

	/**
	 * Asigna el valor del campo folioInstitucion
	 * 
	 * @param folioInstitucion
	 *            el valor de folioInstitucion a asignar
	 */
	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}
	
	/**
	 * Obtiene el campo del idFolio
	 * 
	 * @return idFolio de la institucion
	 */
	public String getIdFolio() {
		String idFolio = "";
		
		if( StringUtils.isNotBlank(claveTipoInstitucion) ) {
			idFolio = idFolio.concat(claveTipoInstitucion);
		}
		
		if( StringUtils.isNotBlank(folioInstitucion) ) {
			idFolio = idFolio.concat(folioInstitucion);
		}
		
		return idFolio;
	}

	public String getClaveCasfim() {
		return claveCasfim;
}

	public void setClaveCasfim(String claveCasfim) {
		this.claveCasfim = claveCasfim;
	}

	public TipoInstitucionDTO getTipoInstitucionDTO() {
		return tipoInstitucionDTO;
	}

	public void setTipoInstitucionDTO(TipoInstitucionDTO tipoInstitucionDTO) {
		this.tipoInstitucionDTO = tipoInstitucionDTO;
	}

	/**
	 * @return the claveSpei
	 */
	public String getClaveSpei() {
		return claveSpei;
	}

	/**
	 * @param claveSpei the claveSpei to set
	 */
	public void setClaveSpei(String claveSpei) {
		this.claveSpei = claveSpei;
	}

	/**
	 * @return the operaSiac
	 */
	public Boolean isOperaSiac() {
		return operaSiac;
	}

	/**
	 * @param operaSiac the operaSiac to set
	 */
	public void setOperaSiac(Boolean operaSiac) {
		this.operaSiac = operaSiac;
	}

	/**
	 * @return the cuentaClabeBeneficiario
	 */
	public String getCuentaClabeBeneficiario() {
		return cuentaClabeBeneficiario;
	}

	/**
	 * @param cuentaClabeBeneficiario the cuentaClabeBeneficiario to set
	 */
	public void setCuentaClabeBeneficiario(String cuentaClabeBeneficiario) {
		this.cuentaClabeBeneficiario = cuentaClabeBeneficiario;
	}

	/**
	 * @return the claveSpeiBeneficiario
	 */
	public String getClaveSpeiBeneficiario() {
		return claveSpeiBeneficiario;
	}

	/**
	 * @param claveSpeiBeneficiario the claveSpeiBeneficiario to set
	 */
	public void setClaveSpeiBeneficiario(String claveSpeiBeneficiario) {
		this.claveSpeiBeneficiario = claveSpeiBeneficiario;
	}
	
}
