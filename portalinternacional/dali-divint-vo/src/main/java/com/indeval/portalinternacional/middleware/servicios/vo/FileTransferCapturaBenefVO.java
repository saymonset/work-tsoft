package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FileTransferCapturaBenefVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**id del usuario*/
	private String id;
	
	/** folio del usuario*/
	private String folio;
	
	/** clave del usuario*/
	private String claveUsuario;
	
	/** archivo a cargar*/
	private HSSFWorkbook archivo;
	
	/** id del tipo de formato a guardar*/
	private Long tipoFormato;
	
	/** bandera para determinar si el usuario es de indeval*/
	private boolean usuarioIndeval;
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getClaveUsuario() {
		return claveUsuario;
	}

	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public HSSFWorkbook getArchivo() {
		return archivo;
	}

	public void setArchivo(HSSFWorkbook archivo) {
		this.archivo = archivo;
	}

	public Long getTipoFormato() {
		return tipoFormato;
	}

	public void setTipoFormato(Long tipoFormato) {
		this.tipoFormato = tipoFormato;
	}

	public boolean isUsuarioIndeval() {
		return usuarioIndeval;
	}

	public void setUsuarioIndeval(boolean usuarioIndeval) {
		this.usuarioIndeval = usuarioIndeval;
	}
	
	

}
