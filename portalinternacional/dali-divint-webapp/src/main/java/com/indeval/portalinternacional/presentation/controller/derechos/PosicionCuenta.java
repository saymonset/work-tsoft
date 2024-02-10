package com.indeval.portalinternacional.presentation.controller.derechos;

import com.indeval.portalinternacional.middleware.servicios.modelo.PosicionLiquidacion;
import com.indeval.portalinternacional.middleware.servicios.vo.PosicionCuentaVO;

public class PosicionCuenta {
	
	private Long idCuentaNombrada;
	private String cuenta;
	private Long posicion;
	private Long posicionAsignada;
	private Long posicionNoAsignada;
	private String nombreInstitucion;
	private Long idEmision;
	private String claveInstitucion;
	
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

	public void transform(PosicionLiquidacion posicionLiquidacion){
		idCuentaNombrada = posicionLiquidacion.getCuenta().getIdCuentaNombrada();
		cuenta = posicionLiquidacion.getCuenta().getCuenta();
		posicion = posicionLiquidacion.getSaldo();
		nombreInstitucion = posicionLiquidacion.getCuenta().getInstitucion().getNombreCorto();		
		posicionNoAsignada = posicionLiquidacion.getSaldo();
		idEmision = posicionLiquidacion.getIdEmision();
		claveInstitucion = posicionLiquidacion.getCuenta().getInstitucion().getClaveInstitucion();
	}

    public void transformVO(PosicionCuentaVO posicionCuentaVO){
        idCuentaNombrada = posicionCuentaVO.getIdCuentaNombrada();
        cuenta = posicionCuentaVO.getCuenta();
        posicion = posicionCuentaVO.getPosicion();
        nombreInstitucion = posicionCuentaVO.getNombreInstitucion();      
        posicionNoAsignada = posicionCuentaVO.getPosicion();
        idEmision = posicionCuentaVO.getIdEmision();
        claveInstitucion = posicionCuentaVO.getClaveInstitucion();
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
		return "PosicionCuenta [idCuentaNombrada=" + idCuentaNombrada + ", cuenta=" + cuenta + ", posicion=" + posicion
				+ ", posicionAsignada=" + posicionAsignada + ", posicionNoAsignada=" + posicionNoAsignada
				+ ", nombreInstitucion=" + nombreInstitucion + ", idEmision=" + idEmision + ", claveInstitucion="
				+ claveInstitucion + "]";
	}
}
