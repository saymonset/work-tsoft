/**
 * 
 */
package com.indeval.portalinternacional.presentation.controller.derechos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author juanhuizar
 * 
 */
public class AdminBeneficiariosDerecho implements Serializable {

	private static final long serialVersionUID = 4565022993227663524L;
	private Long idDerechoBeneficiario;
	private Long tipoInstitucion;
	private String folioInstitucion;
	private String nombre;
	private String direccion;
	private String rfc;
	private Double porcentajeRetencion;
	private Long posicion;
	private BigDecimal monto;
	private BigDecimal montoRetenido;
	private BigDecimal montoPagado;
	private String cuenta;
	private Long idDerechoCapital;
	private String uoi;
	private String error;
	private Long idBeneficiario;
	private String adpNumber;
	private String country;
	private String tipoFormato;
	private String tipoBeneficiario;
	private boolean eliminar;
	private Long idCuentaNombrada;
	private Long posicionAsignada;
	private Long posicionNoAsignada;
	private String nombreInstitucion;
	private Long idEmision;
	private String claveInstitucion;

	/**
	 * @return the idDerechoBeneficiario
	 */
	public Long getIdDerechoBeneficiario() {
		return idDerechoBeneficiario;
	}

	/**
	 * @param idDerechoBeneficiario
	 *            the idDerechoBeneficiario to set
	 */
	public void setIdDerechoBeneficiario(Long idDerechoBeneficiario) {
		this.idDerechoBeneficiario = idDerechoBeneficiario;
	}

	/**
	 * @return the tipoInstitucion
	 */
	public Long getTipoInstitucion() {
		return tipoInstitucion;
	}

	/**
	 * @param tipoInstitucion
	 *            the tipoInstitucion to set
	 */
	public void setTipoInstitucion(Long tipoInstitucion) {
		this.tipoInstitucion = tipoInstitucion;
	}

	/**
	 * @return the folioInstitucion
	 */
	public String getFolioInstitucion() {
		return folioInstitucion;
	}

	/**
	 * @param folioInstitucion
	 *            the folioInstitucion to set
	 */
	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * @param rfc
	 *            the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * @return the porcentajeRetencion
	 */
	public Double getPorcentajeRetencion() {
		return porcentajeRetencion;
	}

	/**
	 * @param porcentajeRetencion
	 *            the porcentajeRetencion to set
	 */
	public void setPorcentajeRetencion(Double porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}

	/**
	 * @return the posicion
	 */
	public Long getPosicion() {
		return posicion;
	}

	/**
	 * @param posicion
	 *            the posicion to set
	 */
	public void setPosicion(Long posicion) {
		this.posicion = posicion;
	}

	/**
	 * @return the monto
	 */
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * @param monto
	 *            the monto to set
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	/**
	 * @return the montoRetenido
	 */
	public BigDecimal getMontoRetenido() {
		return montoRetenido;
	}

	/**
	 * @param montoRetenido
	 *            the montoRetenido to set
	 */
	public void setMontoRetenido(BigDecimal montoRetenido) {
		this.montoRetenido = montoRetenido;
	}

	/**
	 * @return the montoPagado
	 */
	public BigDecimal getMontoPagado() {
		return montoPagado;
	}

	/**
	 * @param montoPagado
	 *            the montoPagado to set
	 */
	public void setMontoPagado(BigDecimal montoPagado) {
		this.montoPagado = montoPagado;
	}

	/**
	 * @return the cuenta
	 */
	public String getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta
	 *            the cuenta to set
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return the idDerechoCapital
	 */
	public Long getIdDerechoCapital() {
		return idDerechoCapital;
	}

	/**
	 * @param idDerechoCapital
	 *            the idDerechoCapital to set
	 */
	public void setIdDerechoCapital(Long idDerechoCapital) {
		this.idDerechoCapital = idDerechoCapital;
	}

	/**
	 * @return the uoi
	 */
	public String getUoi() {
		return uoi;
	}

	/**
	 * @param uoi
	 *            the uoi to set
	 */
	public void setUoi(String uoi) {
		this.uoi = uoi;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the idBeneficiario
	 */
	public Long getIdBeneficiario() {
		return idBeneficiario;
	}

	/**
	 * @param idBeneficiario
	 *            the idBeneficiario to set
	 */
	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	/**
	 * @return the adpNumber
	 */
	public String getAdpNumber() {
		return adpNumber;
	}

	/**
	 * @param adpNumber
	 *            the adpNumber to set
	 */
	public void setAdpNumber(String adpNumber) {
		this.adpNumber = adpNumber;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the tipoFormato
	 */
	public String getTipoFormato() {
		return tipoFormato;
	}

	/**
	 * @param tipoFormato
	 *            the tipoFormato to set
	 */
	public void setTipoFormato(String tipoFormato) {
		this.tipoFormato = tipoFormato;
	}

	/**
	 * @return the tipoBeneficiario
	 */
	public String getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	/**
	 * @param tipoBeneficiario
	 *            the tipoBeneficiario to set
	 */
	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	/**
	 * @return the eliminar
	 */
	public boolean isEliminar() {
		return eliminar;
	}

	/**
	 * @param eliminar
	 *            the eliminar to set
	 */
	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}

	/**
	 * @return the idCuentaNombrada
	 */
	public Long getIdCuentaNombrada() {
		return idCuentaNombrada;
	}

	/**
	 * @param idCuentaNombrada
	 *            the idCuentaNombrada to set
	 */
	public void setIdCuentaNombrada(Long idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
	}

	/**
	 * @return the posicionAsignada
	 */
	public Long getPosicionAsignada() {
		return posicionAsignada;
	}

	/**
	 * @param posicionAsignada
	 *            the posicionAsignada to set
	 */
	public void setPosicionAsignada(Long posicionAsignada) {
		this.posicionAsignada = posicionAsignada;
	}

	/**
	 * @return the posicionNoAsignada
	 */
	public Long getPosicionNoAsignada() {
		return posicionNoAsignada;
	}

	/**
	 * @param posicionNoAsignada
	 *            the posicionNoAsignada to set
	 */
	public void setPosicionNoAsignada(Long posicionNoAsignada) {
		this.posicionNoAsignada = posicionNoAsignada;
	}

	/**
	 * @return the nombreInstitucion
	 */
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	/**
	 * @param nombreInstitucion
	 *            the nombreInstitucion to set
	 */
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	/**
	 * @return the idEmision
	 */
	public Long getIdEmision() {
		return idEmision;
	}

	/**
	 * @param idEmision
	 *            the idEmision to set
	 */
	public void setIdEmision(Long idEmision) {
		this.idEmision = idEmision;
	}

	/**
	 * @return the claveInstitucion
	 */
	public String getClaveInstitucion() {
		return claveInstitucion;
	}

	/**
	 * @param claveInstitucion
	 *            the claveInstitucion to set
	 */
	public void setClaveInstitucion(String claveInstitucion) {
		this.claveInstitucion = claveInstitucion;
	}

}
