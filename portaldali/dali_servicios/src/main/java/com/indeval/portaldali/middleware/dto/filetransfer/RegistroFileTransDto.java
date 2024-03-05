/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto.filetransfer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object que representa un registro en el proceso de File Transfer. 
 * 
 * @author Pablo Balderas
 */
public class RegistroFileTransDto implements Serializable {

	/** Id para la serialización. */
	private static final long serialVersionUID = -1306590081806425104L;

	/** Consecutivo del registro */
	private int consecutivo;
	
	/** Indica el usuario que realizo el file transfer */
	private String usuario;
	
	/** Fecha y hora en que se procesa el registro */
	private Date fechaProcesamiento;
	
	/** Estado del registro */
	private String edoRegistro;
	
	/** Mensajes de error generados */
	private String[] mensajesRegistro;
	
	/** Cadena del registro */
	private String cadenaRegistro;
	
	/** Campos que consforman el registro */
	private List<CampoRegFileTransDto> camposRegistro = null;

	/**
	 * @return the consecutivo
	 */
	public int getConsecutivo() {
		return consecutivo;
	}

	/**
	 * @param consecutivo the consecutivo to set
	 */
	public void setConsecutivo(int consecutivo) {
		this.consecutivo = consecutivo;
	}

	/**
	 * @return the fechaProcesamiento
	 */
	public Date getFechaProcesamiento() {
		return fechaProcesamiento;
	}

	/**
	 * @param fechaProcesamiento the fechaProcesamiento to set
	 */
	public void setFechaProcesamiento(Date fechaProcesamiento) {
		this.fechaProcesamiento = fechaProcesamiento;
	}

	/**
	 * @return the edoRegistro
	 */
	public String getEdoRegistro() {
		return edoRegistro;
	}

	/**
	 * @param edoRegistro the edoRegistro to set
	 */
	public void setEdoRegistro(String edoRegistro) {
		this.edoRegistro = edoRegistro;
	}

	/**
	 * @return the cadenaRegistro
	 */
	public String getCadenaRegistro() {
		return cadenaRegistro;
	}

	/**
	 * @param cadenaRegistro the cadenaRegistro to set
	 */
	public void setCadenaRegistro(String cadenaRegistro) {
		this.cadenaRegistro = cadenaRegistro;
	}

	/**
	 * @return the camposRegistro
	 */
	public List<CampoRegFileTransDto> getCamposRegistro() {
		return camposRegistro;
	}

	/**
	 * @param camposRegistro the camposRegistro to set
	 */
	public void setCamposRegistro(List<CampoRegFileTransDto> camposRegistro) {
		this.camposRegistro = camposRegistro;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the mensajesRegistro
	 */
	public String[] getMensajesRegistro() {
		return mensajesRegistro;
	}

	/**
	 * @param mensajesRegistro the mensajesRegistro to set
	 */
	public void setMensajesRegistro(String[] mensajesRegistro) {
		this.mensajesRegistro = mensajesRegistro;
	}
 }
