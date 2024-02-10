// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.dto;

import java.io.Serializable;
import java.util.List;

public class InstitucionWebDTO implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 8720752030734471639L;

	/**
	 * Id de instituci&oacute;n
	 */
	private Integer idInstitucion;

	/** Clave del Tipo de Instituci&oacute;n */
	private String claveTipoInstitucion;

	/**
	 * clave de instituci&oacute;n
	 */
	private String clave;

	/**
	 * id folio
	 */
	private String folio;

	/**
	 * clave CASFIM
	 */
	private String claveCasfim;

	/**
	 * clave BIC
	 */
	private String bic;

	/**
	 * nacionalidad
	 */
	private String nacionalidad;

	/**
	 * Razon social de la instituci&oacute;n
	 */
	private String razonSocial;

	/**
	 * Direcci&oacute;n fiscal Calle
	 */
	private String domicilioFiscal;

	/**
	 * C&oacute;digo postal
	 */
	private Integer codigoPostal;

	/**
	 * Descricion de la ciudad
	 */
	private String descripcionCiudad;

	/**
	 * Descripcion del estado
	 */
	private String descripcionEstado;

	/** Id de la ciudad */
	private Integer idCiudad;

	/** Id del pais */
	private Integer idPais;

	/** Registro Federal de Contribuyentes */
	private String rfc;

	/** Nombre corto de la instituci&oacute;n */
	private String nombre;

	/** Bandera para operar T&iacute;tulo Global */
	private Boolean operaTituloGlobal;

	/** Id del Estado */
	private Integer idEntidadFederativa;

	/**
	 * id del tipo de instituci&oacute;n
	 */
	private Integer idTipoInstitucion;

	/** Lista de Perfiles asociados a la instituci&oacute;n */
	private List perfiles;

	/** Configuracion de la institucion */
	private Integer configuracionInstituciones = null;

	private Integer estatusInstitucion = null;

	/** CLABE */
	private String clabe = null;

	/** Indica si la institucion es interna */
	private boolean interna;

	/** Almacena el valor de entrada del CASFIM, para saber si cambio */
	private String casfimInicial;

	/** Almacena el valor de entrada de la cuenta CLABE, para saber si cambio */
	private String clabeInicial;

	/** SPEI */
	private Integer claveSpei;

	/** SPEI del Beneficiario */
	private Integer claveSpeiBeneficiario;

	/** RFC del Beneficiario */
	private String rfcBeneficiario;

	/** Nombre del Beneficiario */
	private String nombreBeneficiario;

	/** Cuenta CLABE del Beneficiario */
	private String clabeBeneficiario;

	/** Bandera que indica si la institucion opera SIAC = 1, o NO opera SIAC = 0 */
	private Boolean operaSiac;

	private String cuentaPrincipal;

	private String nombrePrincipal;

	private Boolean activoPrincipal;

	private String cuentaBackup;

	private String nombreBackup;

	private Boolean activoBackup;

	public Integer getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Integer idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getClaveTipoInstitucion() {
		return claveTipoInstitucion;
	}

	public void setClaveTipoInstitucion(String claveTipoInstitucion) {
		this.claveTipoInstitucion = claveTipoInstitucion;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getClaveCasfim() {
		return claveCasfim;
	}

	public void setClaveCasfim(String claveCasfim) {
		this.claveCasfim = claveCasfim;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getDomicilioFiscal() {
		return domicilioFiscal;
	}

	public void setDomicilioFiscal(String domicilioFiscal) {
		this.domicilioFiscal = domicilioFiscal;
	}

	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDescripcionCiudad() {
		return descripcionCiudad;
	}

	public void setDescripcionCiudad(String descripcionCiudad) {
		this.descripcionCiudad = descripcionCiudad;
	}

	public String getDescripcionEstado() {
		return descripcionEstado;
	}

	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	public Integer getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(Integer idCiudad) {
		this.idCiudad = idCiudad;
	}

	public Integer getIdPais() {
		return idPais;
	}

	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getOperaTituloGlobal() {
		return operaTituloGlobal;
	}

	public void setOperaTituloGlobal(Boolean operaTituloGlobal) {
		this.operaTituloGlobal = operaTituloGlobal;
	}

	public Integer getIdEntidadFederativa() {
		return idEntidadFederativa;
	}

	public void setIdEntidadFederativa(Integer idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

	public Integer getIdTipoInstitucion() {
		return idTipoInstitucion;
	}

	public void setIdTipoInstitucion(Integer idTipoInstitucion) {
		this.idTipoInstitucion = idTipoInstitucion;
	}

	public List getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List perfiles) {
		this.perfiles = perfiles;
	}

	public Integer getConfiguracionInstituciones() {
		return configuracionInstituciones;
	}

	public void setConfiguracionInstituciones(Integer configuracionInstituciones) {
		this.configuracionInstituciones = configuracionInstituciones;
	}

	public Integer getEstatusInstitucion() {
		return estatusInstitucion;
	}

	public void setEstatusInstitucion(Integer estatusInstitucion) {
		this.estatusInstitucion = estatusInstitucion;
	}

	public String getClabe() {
		return clabe;
	}

	public void setClabe(String clabe) {
		this.clabe = clabe;
	}

	public boolean isInterna() {
		return interna;
	}

	public void setInterna(boolean interna) {
		this.interna = interna;
	}

	public String getCasfimInicial() {
		return casfimInicial;
	}

	public void setCasfimInicial(String casfimInicial) {
		this.casfimInicial = casfimInicial;
	}

	public String getClabeInicial() {
		return clabeInicial;
	}

	public void setClabeInicial(String clabeInicial) {
		this.clabeInicial = clabeInicial;
	}

	public Integer getClaveSpei() {
		return claveSpei;
	}

	public void setClaveSpei(Integer claveSpei) {
		this.claveSpei = claveSpei;
	}

	public Integer getClaveSpeiBeneficiario() {
		return claveSpeiBeneficiario;
	}

	public void setClaveSpeiBeneficiario(Integer claveSpeiBeneficiario) {
		this.claveSpeiBeneficiario = claveSpeiBeneficiario;
	}

	public String getRfcBeneficiario() {
		return rfcBeneficiario;
	}

	public void setRfcBeneficiario(String rfcBeneficiario) {
		this.rfcBeneficiario = rfcBeneficiario;
	}

	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}

	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}

	public String getClabeBeneficiario() {
		return clabeBeneficiario;
	}

	public void setClabeBeneficiario(String clabeBeneficiario) {
		this.clabeBeneficiario = clabeBeneficiario;
	}

	public Boolean getOperaSiac() {
		return operaSiac;
	}

	public void setOperaSiac(Boolean operaSiac) {
		this.operaSiac = operaSiac;
	}

	public String getCuentaPrincipal() {
		return cuentaPrincipal;
	}

	public void setCuentaPrincipal(String cuentaPrincipal) {
		this.cuentaPrincipal = cuentaPrincipal;
	}

	public String getNombrePrincipal() {
		return nombrePrincipal;
	}

	public void setNombrePrincipal(String nombrePrincipal) {
		this.nombrePrincipal = nombrePrincipal;
	}

	public Boolean getActivoPrincipal() {
		return activoPrincipal;
	}

	public void setActivoPrincipal(Boolean activoPrincipal) {
		this.activoPrincipal = activoPrincipal;
	}

	public String getCuentaBackup() {
		return cuentaBackup;
	}

	public void setCuentaBackup(String cuentaBackup) {
		this.cuentaBackup = cuentaBackup;
	}

	public String getNombreBackup() {
		return nombreBackup;
	}

	public void setNombreBackup(String nombreBackup) {
		this.nombreBackup = nombreBackup;
	}

	public Boolean getActivoBackup() {
		return activoBackup;
	}

	public void setActivoBackup(Boolean activoBackup) {
		this.activoBackup = activoBackup;
	}

}
