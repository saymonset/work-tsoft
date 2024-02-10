package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;

public class BeneficiariosPaginacionVO implements Serializable{

	private String letra;
	private Integer cantidad;
	private Integer paginas;
	private String etiquetaMostrar;
	
	private static final long serialVersionUID = 1L;
	
	public BeneficiariosPaginacionVO(){
	}
	
	public BeneficiariosPaginacionVO(String letra, Integer cantidad, Integer paginas){
		this.letra = letra;
		this.cantidad = cantidad;
		this.paginas = paginas;
	}
	
	public BeneficiariosPaginacionVO(String letra, String etiquetaMostrar, Integer paginas){
		this.letra = letra;
		this.etiquetaMostrar = etiquetaMostrar;
		this.paginas = paginas;
	}
	

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getPaginas() {
		return paginas;
	}

	public void setPaginas(Integer paginas) {
		this.paginas = paginas;
	}

	public String getEtiquetaMostrar() {
		return etiquetaMostrar;
	}

	public void setEtiquetaMostrar(String etiquetaMostrar) {
		this.etiquetaMostrar = etiquetaMostrar;
	}
	
	
	
}
