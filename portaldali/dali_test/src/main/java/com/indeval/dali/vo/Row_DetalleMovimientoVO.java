package com.indeval.dali.vo;

public abstract class Row_DetalleMovimientoVO {

	private String fechaLiquidacion;
	
	private String descripcion;
	
	private String folioInstruccion;
	
	private String tipoInstruccion;

	public String getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(String fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFolioInstruccion() {
		return folioInstruccion;
	}

	public void setFolioInstruccion(String folioInstruccion) {
		this.folioInstruccion = folioInstruccion;
	}

	public String getTipoInstruccion() {
		return tipoInstruccion;
	}

	public void setTipoInstruccion(String tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}
	
		
}
