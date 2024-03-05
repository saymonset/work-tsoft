package com.indeval.dali.vo;

public class Row_DetalleMovimientoValoresVO extends Row_DetalleMovimientoVO{

	
	private String contraparte;
	
	private String cupo;
	
	
	private Long recepcion;
	
	private Long entrega;
	
	private Long disponible;
	
	private Long noDisponible;
	
	private Long posicion;

	

	public String getContraparte() {
		return contraparte;
	}

	public void setContraparte(String contraparte) {
		this.contraparte = contraparte;
	}

	public String getCupo() {
		return cupo;
	}

	public void setCupo(String cupo) {
		this.cupo = cupo;
	}

	
	public Long getRecepcion() {
		return recepcion;
	}

	public void setRecepcion(Long recepcion) {
		this.recepcion = recepcion;
	}

	public Long getEntrega() {
		return entrega;
	}

	public void setEntrega(Long entrega) {
		this.entrega = entrega;
	}

	public Long getDisponible() {
		return disponible;
	}

	public void setDisponible(Long disponible) {
		this.disponible = disponible;
	}

	public Long getNoDisponible() {
		return noDisponible;
	}

	public void setNoDisponible(Long noDisponible) {
		this.noDisponible = noDisponible;
	}

	public Long getPosicion() {
		return posicion;
	}

	public void setPosicion(Long posicion) {
		this.posicion = posicion;
	}
	
	
}
