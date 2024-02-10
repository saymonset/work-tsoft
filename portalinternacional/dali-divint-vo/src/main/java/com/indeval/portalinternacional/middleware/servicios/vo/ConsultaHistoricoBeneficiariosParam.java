package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.indeval.portaldali.persistence.modelo.Institucion;
import java.util.List;

public class ConsultaHistoricoBeneficiariosParam implements Serializable {

	private static final long serialVersionUID = -1L;
	/** Beneficiario Seleccionado */
	private Long idBeneficiario = -1l;
	/** Custodio Seleccionado */
	private Long custodio = -1l;
	/** TipoBeneficiario Seleccionado */
	private Long tipoBeneficiario = -1l;
	/** Formarto de acuerdo a custodio y a tipo beneficiario */
	private String formato;
	/** Institucion */
	private Institucion institucion;
	/** Status Anterior Beneficiario */
	private Long statusAnterior = -1l;
	/** Status Nuevo Beneficiario */
	private Long statusNuevo = -1l;
	/** Nombre */
	private String nombreRazonSocial;
	/** Indica la fecha inicial de Registro */
	private Date fechaRegistroInicio;
	/** Indica la fecha final de Registro */
	private Date fechaRegistroFin;
	/** Indica la fecha minima para todos los campos */
	private Date fechaMinima;
	/** Indica la fecha maxima para todos los campos */
	private Date fechaMaxima;
	/** Indica numero unico asignado por el sistema */
	private String uoiNumber;
    /** Lista de beneficiairios */
    private List<Long> listaBeneficiarios;
	
	
	public ConsultaHistoricoBeneficiariosParam() {
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
	 * Beneficiario Seleccionado
	 * @return the idBeneficiario
	 */
	public Long getIdBeneficiario() {
		return idBeneficiario;
	}

	/**
	 * Beneficiario Seleccionado
	 * @param idBeneficiario the idBeneficiario to set
	 */
	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	/**
	 * Custodio Seleccionado
	 * @return the custodio
	 */
	public Long getCustodio() {
		return custodio;
	}

	/**
	 * Custodio Seleccionado
	 * @param custodio the custodio to set
	 */
	public void setCustodio(Long custodio) {
		this.custodio = custodio;
	}

	/**
	 * TipoBeneficiario Seleccionado
	 * @return the tipoBeneficiario
	 */
	public Long getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	/**
	 * TipoBeneficiario Seleccionado
	 * @param tipoBeneficiario the tipoBeneficiario to set
	 */
	public void setTipoBeneficiario(Long tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	/**
	 * Formarto de acuerdo a custodio y a tipo beneficiario
	 * @return the formato
	 */
	public String getFormato() {
		return formato;
	}

	/**
	 * Formarto de acuerdo a custodio y a tipo beneficiario
	 * @param formato the formato to set
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}

	/**
	 * Institucion
	 * @return the institucion
	 */
	public Institucion getInstitucion() {
		return institucion;
	}

	/**
	 * Institucion
	 * @param institucion the institucion to set
	 */
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	/**
	 * Status Anterior Beneficiario
	 * @return the statusAnterior
	 */
	public Long getStatusAnterior() {
		return statusAnterior;
	}

	/**
	 * Status Anterior Beneficiario
	 * @param statusAnterior the statusAnterior to set
	 */
	public void setStatusAnterior(Long statusAnterior) {
		this.statusAnterior = statusAnterior;
	}

	/**
	 * Status Nuevo Beneficiario
	 * @return the statusNuevo
	 */
	public Long getStatusNuevo() {
		return statusNuevo;
	}

	/**
	 * Status Nuevo Beneficiario
	 * @param statusNuevo the statusNuevo to set
	 */
	public void setStatusNuevo(Long statusNuevo) {
		this.statusNuevo = statusNuevo;
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
	 * Indica la fecha inicial de Registro
	 * @return the fechaRegistroInicio
	 */
	public Date getFechaRegistroInicio() {
		return fechaRegistroInicio;
	}

	/**
	 * Indica la fecha inicial de Registro
	 * @param fechaRegistroInicio the fechaRegistroInicio to set
	 */
	public void setFechaRegistroInicio(Date fechaRegistroInicio) {
		this.fechaRegistroInicio = fechaRegistroInicio;
	}

	/**
	 * Indica la fecha final de Registro
	 * @return the fechaRegistroFin
	 */
	public Date getFechaRegistroFin() {
		return fechaRegistroFin;
	}

	/**
	 * Indica la fecha final de Registro
	 * @param fechaRegistroFin the fechaRegistroFin to set
	 */
	public void setFechaRegistroFin(Date fechaRegistroFin) {
		this.fechaRegistroFin = fechaRegistroFin;
	}

	/**
	 * Indica la fecha minima para todos los campos
	 * @return the fechaMinima
	 */
	public Date getFechaMinima() {
		return fechaMinima;
	}

	/**
	 * Indica la fecha minima para todos los campos
	 * @param fechaMinima the fechaMinima to set
	 */
	public void setFechaMinima(Date fechaMinima) {
		this.fechaMinima = fechaMinima;
	}

	/**
	 * Indica la fecha maxima para todos los campos
	 * @return the fechaMaxima
	 */
	public Date getFechaMaxima() {
		return fechaMaxima;
	}

	/**
	 * Indica la fecha maxima para todos los campos
	 * @param fechaMaxima the fechaMaxima to set
	 */
	public void setFechaMaxima(Date fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}

	/**
	 * Indica numero unico asignado por el sistema
	 * @return the uoiNumber
	 */
	public String getUoiNumber() {
		return uoiNumber;
	}

	/**
	 * Indica numero unico asignado por el sistema
	 * @param uoiNumber the uoiNumber to set
	 */
	public void setUoiNumber(String uoiNumber) {
		this.uoiNumber = uoiNumber;
	}

    /**
     * Lista de beneficiairios
     * @return the listaBeneficiarios
     */
    public List<Long> getListaBeneficiarios() {
        return listaBeneficiarios;
    }

    /**
     * Lista de beneficiairios
     * @param listaBeneficiarios the listaBeneficiarios to set
     */
    public void setListaBeneficiarios(List<Long> listaBeneficiarios) {
        this.listaBeneficiarios = listaBeneficiarios;
    }
		
}
