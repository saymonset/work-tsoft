/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto.filetransfer;

import java.io.Serializable;

/**
 * Data Transfer Object que representa un campo en el proceso de File Transfer.
 * 
 * @author Pablo Balderas
 */
public class CampoRegFileTransDto implements Serializable {

	/** Id para la serialización */
	private static final long serialVersionUID = -5528083109991045271L;

	/** Indica el numero de campo en la plantilla de file transfer */
	private int numeroCampo;

	/** Longitud del campo */
	private int longitudCampo;
	
	/** Etiqueta del campo */
	private String etiquetaCampo;
	
	/** Nombre del campo */
	private String nombreCampo;
	
	/** Valor del campo */
	private String valorCampo;
	
	/** Indica si el cmapo es requerido */
	private boolean requerido;
	
	/** Indica si el campo paso sus validaciones */
	private boolean campoCorrecto = true;

	/**
	 * Constructor de la clase
	 */
	public CampoRegFileTransDto() {}
	
	/**
	 * Constructor de la clase
	 * @param numeroCampo Número del campo en la plantilla
	 * @param longitudCampo Longitud del campo
	 * @param nombreCampo Nombre del campo
	 */
	public CampoRegFileTransDto(int numeroCampo, int longitudCampo, String nombreCampo) {
		this.numeroCampo = numeroCampo;
		this.longitudCampo = longitudCampo;
		this.nombreCampo = nombreCampo;
	}

	/**
	 * @return the numeroCampo
	 */
	public int getNumeroCampo() {
		return numeroCampo;
	}

	/**
	 * @param numeroCampo the numeroCampo to set
	 */
	public void setNumeroCampo(int numeroCampo) {
		this.numeroCampo = numeroCampo;
	}

	/**
	 * @return the longitudCampo
	 */
	public int getLongitudCampo() {
		return longitudCampo;
	}

	/**
	 * @param longitudCampo the longitudCampo to set
	 */
	public void setLongitudCampo(int longitudCampo) {
		this.longitudCampo = longitudCampo;
	}

	/**
	 * @return the nombreCampo
	 */
	public String getNombreCampo() {
		return nombreCampo;
	}

	/**
	 * @param nombreCampo the nombreCampo to set
	 */
	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	/**
	 * @return the valorCampo
	 */
	public String getValorCampo() {
		return valorCampo;
	}

	/**
	 * @param valorCampo the valorCampo to set
	 */
	public void setValorCampo(String valorCampo) {
		this.valorCampo = valorCampo;
	}

	/**
	 * @return the campoCorrecto
	 */
	public boolean isCampoCorrecto() {
		return campoCorrecto;
	}

	/**
	 * @param campoCorrecto the campoCorrecto to set
	 */
	public void setCampoCorrecto(boolean campoCorrecto) {
		this.campoCorrecto = campoCorrecto;
	}

	/**
	 * @return the etiquetaCampo
	 */
	public String getEtiquetaCampo() {
		return etiquetaCampo;
	}

	/**
	 * @param etiquetaCampo the etiquetaCampo to set
	 */
	public void setEtiquetaCampo(String etiquetaCampo) {
		this.etiquetaCampo = etiquetaCampo;
	}

	/**
	 * @return the requerido
	 */
	public boolean isRequerido() {
		return requerido;
	}

	/**
	 * @param requerido the requerido to set
	 */
	public void setRequerido(boolean requerido) {
		this.requerido = requerido;
	}

}
