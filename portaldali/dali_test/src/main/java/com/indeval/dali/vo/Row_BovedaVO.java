package com.indeval.dali.vo;

import java.util.List;

public class Row_BovedaVO {
	
	private String descripcion;	
	
	private List<Row_DetalleMovimientoVO> detalleMovimientos;
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	
	
	public void setDetalleMovimientos(List<Row_DetalleMovimientoVO> detalleMovimientos) {
		this.detalleMovimientos = detalleMovimientos;
	}
	
	public List<Row_DetalleMovimientoVO> getDetalleMovimientos() {
		return detalleMovimientos;
	}
	
}
