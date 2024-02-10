package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;

/**
 * VO para la posicion de la cuenta, que al final se transformara en un PosicionCuenta que se tienen en el 
 * AdminBeneficiariosDerechoController.
 */
public class PosicionCuentaVO implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 24L;
	
	private Long idCuentaNombrada;
	private String cuenta;
	private Long posicion;
	private Long posicionAsignada;
	private Long posicionNoAsignada;
	private String nombreInstitucion;
	private Long idEmision;
	private String claveInstitucion;

    /**Constructor por omisi&oacute;n*/
    public PosicionCuentaVO() {
        super();
    }
	public Long getIdCuentaNombrada() {
		return idCuentaNombrada;
	}
	public void setIdCuentaNombrada(Long idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public Long getPosicion() {
		return posicion;
	}
	public void setPosicion(Long posicion) {
		this.posicion = posicion;
	}
	public Long getPosicionAsignada() {
		return posicionAsignada;
	}
	public void setPosicionAsignada(Long posicionAsignada) {
		this.posicionAsignada = posicionAsignada;
	}
	public Long getPosicionNoAsignada() {
		return posicionNoAsignada;
	}
	public void setPosicionNoAsignada(Long posicionNoAsignada) {
		this.posicionNoAsignada = posicionNoAsignada;
	}
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}
	public Long getIdEmision() {
		return idEmision;
	}
	public void setIdEmision(Long idEmision) {
		this.idEmision = idEmision;
	}
	public String getClaveInstitucion() {
		return claveInstitucion;
	}
	public void setClaveInstitucion(String claveInstitucion) {
		this.claveInstitucion = claveInstitucion;
	}
    @Override
    public String toString() {
        return "PosicionCuentaVO=[idCuentaNombrada=" + idCuentaNombrada + ", cuenta=" + cuenta + ", posicion=" + posicion
                + ", posicionAsignada=" + posicionAsignada + ", posicionNoAsignada=" + posicionNoAsignada
                + ", nombreInstitucion=" + nombreInstitucion + ", idEmision=" + idEmision + ", claveInstitucion="
                + claveInstitucion + "]";
    }
}
