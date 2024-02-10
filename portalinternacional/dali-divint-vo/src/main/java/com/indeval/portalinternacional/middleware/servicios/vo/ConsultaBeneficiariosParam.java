package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.indeval.portaldali.persistence.modelo.Institucion;
import java.util.List;

public class ConsultaBeneficiariosParam implements Serializable {

	private static final long serialVersionUID = -5123097124282686987L;
	/** Custodio Seleccionado */
	private Long custodio = -1l;
	/** TipoBeneficiario Seleccionado */
	private Long tipoBeneficiario = -1l;
	/** Formarto de acuerdo a custodio y a tipo beneficiario */
	private String formato;
	/** Institucion */
	private Institucion institucion;
	/** Status Beneficiario */
	private Long statusBenef = -1l;
	/** Nombre */
	private String nombreRazonSocial;
	/** Persono Fisica o Moral */
	private boolean personaFisica;
	/** Indica la fecha inicial de Registro */
	private Date fechaRegistroInicio;
	/** Indica la fecha final de Registro */
	private Date fechaRegistroFin;
	/** Indica la fecha inicial de Formato */
	private Date fechaFormatoInicio;
	/** Indica la fecha final de Formato */
	private Date fechaFormatoFin;
	/** Indica la fecha minima para todos los campos */
	private Date fechaMinima;
	/** Indica la fecha maxima para todos los campos */
	private Date fechaMaxima;
	/** Indica numero unico asignado por el sistema */
	private String uoiNumber;
	/** Indica el numero de referencia para W8BEN y W8IMY */
	private String referenceNumber;
	/** Indica la fecha inicial de Autorizacion */
	private Date fechaAutorizacionInicio;
	/** Indica la fecha final de Autorizacion */
	private Date fechaAutorizacionFin;
    /** Lista de Beneficiarios */
    private List<Long> listaBeneficiarios;
	/** Adp */
	private String adp;
	/** Cuando es true ordena por nombre luego razon social */
	private boolean ordernar = true;
	
	private Boolean activo;
	
	/** Valor para consulta de RFC en formatos W8BEN y W8BENE **/
	private String rfc;

	/*metodo para saber si se cosulta con direccion o sin direeccion*/
	private boolean traeDireccion = true;
	private String letra;
	private Integer pagina;
	
	public ConsultaBeneficiariosParam() {
		super();
		institucion = new Institucion();
		Calendar fechaActual = Calendar.getInstance();
		fechaActual.add(Calendar.YEAR, -3);
		fechaMinima = fechaActual.getTime();
		fechaActual = Calendar.getInstance();
		fechaActual.add(Calendar.YEAR, 3);
		fechaMaxima = fechaActual.getTime();
	}
	
	/**
	 * @return the custodio
	 */
	public Long getCustodio() {
		return custodio;
	}

	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(Long custodio) {
		this.custodio = custodio;
	}
	
	/**
	 * @return the tipoBeneficiario
	 */
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
	 * @return the formato
	 */
	public String getFormato() {
		return formato;
	}

	/**
	 * @param formato the formato to set
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}

	/**
	 * @param institucion the institucion to set
	 */
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	/**
	 * @return the institucion
	 */
	public Institucion getInstitucion() {
		return institucion;
	}

	/**
	 * @param statusBenef the statusBenef to set
	 */
	public void setStatusBenef(Long statusBenef) {
		this.statusBenef = statusBenef;
	}

	/**
	 * @return the statusBenef
	 */
	public Long getStatusBenef() {
		return statusBenef;
	}

	/**
	 * Nombre
	 * @return the nombreRazonSocial
	 */
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}

	/**
	 * Nombre
	 * @param nombreRazonSocial the nombreRazonSocial to set
	 */
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}

	/**
	 * @return the personaFisica
	 */
	public boolean isPersonaFisica() {
		return personaFisica;
	}

	/**
	 * @param personaFisica the personaFisica to set
	 */
	public void setPersonaFisica(boolean personaFisica) {
		this.personaFisica = personaFisica;
	}

	/**
	 * @return the fechaRegistroInicio
	 */
	public Date getFechaRegistroInicio() {
		return fechaRegistroInicio;
	}

	/**
	 * @return the fechaRegistroFin
	 */
	public Date getFechaRegistroFin() {
		return fechaRegistroFin;
	}

	/**
	 * @return the fechaFormatoInicio
	 */
	public Date getFechaFormatoInicio() {
		return fechaFormatoInicio;
	}

	/**
	 * @return the fechaFormatoFin
	 */
	public Date getFechaFormatoFin() {
		return fechaFormatoFin;
	}

	/**
	 * @param fechaRegistroInicio the fechaRegistroInicio to set
	 */
	public void setFechaRegistroInicio(Date fechaRegistroInicio) {
		this.fechaRegistroInicio = fechaRegistroInicio;
	}

	/**
	 * @param fechaRegistroFin the fechaRegistroFin to set
	 */
	public void setFechaRegistroFin(Date fechaRegistroFin) {
		this.fechaRegistroFin = fechaRegistroFin;
	}

	/**
	 * @param fechaFormatoInicio the fechaFormatoInicio to set
	 */
	public void setFechaFormatoInicio(Date fechaFormatoInicio) {
		this.fechaFormatoInicio = fechaFormatoInicio;
	}

	/**
	 * @param fechaFormatoFin the fechaFormatoFin to set
	 */
	public void setFechaFormatoFin(Date fechaFormatoFin) {
		this.fechaFormatoFin = fechaFormatoFin;
	}

	/**
	 * @return the fechaMinima
	 */
	public Date getFechaMinima() {
		return fechaMinima;
	}

	/**
	 * @return the fechaMaxima
	 */
	public Date getFechaMaxima() {
		return fechaMaxima;
	}

	/**
	 * @param fechaMinima the fechaMinima to set
	 */
	public void setFechaMinima(Date fechaMinima) {
		this.fechaMinima = fechaMinima;
	}

	/**
	 * @param fechaMaxima the fechaMaxima to set
	 */
	public void setFechaMaxima(Date fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}

	/**
	 * @return the uoiNumber
	 */
	public String getUoiNumber() {
		return uoiNumber;
	}

	/**
	 * @param uoiNumber the uoiNumber to set
	 */
	public void setUoiNumber(String uoiNumber) {
		this.uoiNumber = uoiNumber;
	}

	/**
	 * Indica el numero de referencia para W8BEN y W8IMY
	 * @return the referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * Indica el numero de referencia para W8BEN y W8IMY
	 * @param referenceNumber the referenceNumber to set
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	/**
	 * Indica la fecha inicial de Autorizacion
	 * @return the fechaAutorizacionInicio
	 */
	public Date getFechaAutorizacionInicio() {
		return fechaAutorizacionInicio;
	}

	/**
	 * Indica la fecha inicial de Autorizacion
	 * @param fechaAutorizacionInicio the fechaAutorizacionInicio to set
	 */
	public void setFechaAutorizacionInicio(Date fechaAutorizacionInicio) {
		this.fechaAutorizacionInicio = fechaAutorizacionInicio;
	}

	/**
	 * Indica la fecha final de Autorizacion
	 * @return the fechaAutorizacionFin
	 */
	public Date getFechaAutorizacionFin() {
		return fechaAutorizacionFin;
	}

	/**
	 * Indica la fecha final de Autorizacion
	 * @param fechaAutorizacionFin the fechaAutorizacionFin to set
	 */
	public void setFechaAutorizacionFin(Date fechaAutorizacionFin) {
		this.fechaAutorizacionFin = fechaAutorizacionFin;
	}

    /**
     * Lista de Beneficiarios
     * @return the listaBeneficiarios
     */
    public List<Long> getListaBeneficiarios() {
        return listaBeneficiarios;
    }

    /**
     * Lista de Beneficiarios
     * @param listaBeneficiarios the listaBeneficiarios to set
     */
    public void setListaBeneficiarios(List<Long> listaBeneficiarios) {
        this.listaBeneficiarios = listaBeneficiarios;
    }

	public String getAdp() {
		return adp;
	}

	public void setAdp(String adp) {
		this.adp = adp;
	}

	public boolean isTraeDireccion() {
		return traeDireccion;
	}

	public void setTraeDireccion(boolean traeDireccion) {
		this.traeDireccion = traeDireccion;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	/**
	 * @return the ordernar
	 */
	public boolean isOrdernar() {
		return ordernar;
	}

	/**
	 * @param ordernar the ordernar to set
	 */
	public void setOrdernar(boolean ordernar) {
		this.ordernar = ordernar;
	}

	/**
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConsultaBeneficiariosParam [custodio=" + custodio + ", tipoBeneficiario=" + tipoBeneficiario
				+ ", formato=" + formato + ", institucion=" + institucion + ", statusBenef=" + statusBenef
				+ ", nombreRazonSocial=" + nombreRazonSocial + ", personaFisica=" + personaFisica
				+ ", fechaRegistroInicio=" + fechaRegistroInicio + ", fechaRegistroFin=" + fechaRegistroFin
				+ ", fechaFormatoInicio=" + fechaFormatoInicio + ", fechaFormatoFin=" + fechaFormatoFin
				+ ", fechaMinima=" + fechaMinima + ", fechaMaxima=" + fechaMaxima + ", uoiNumber=" + uoiNumber
				+ ", referenceNumber=" + referenceNumber + ", fechaAutorizacionInicio=" + fechaAutorizacionInicio
				+ ", fechaAutorizacionFin=" + fechaAutorizacionFin + ", listaBeneficiarios=" + listaBeneficiarios
				+ ", adp=" + adp + ", ordernar=" + ordernar + ", activo=" + activo + ", rfc=" + rfc + ", traeDireccion="
				+ traeDireccion + ", letra=" + letra + ", pagina=" + pagina + "]";
	}
	
}
