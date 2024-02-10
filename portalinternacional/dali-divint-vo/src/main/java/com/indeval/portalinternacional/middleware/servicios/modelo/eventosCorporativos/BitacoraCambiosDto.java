/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.util.Date;

/**
 * @author kode-
 *
 */
public class BitacoraCambiosDto {
	private Long idBitacoraCambios;
	private Date fechaModificacion;
	private String formatoOriginal;
	private Long visible;
	private String usuario;
	private Long idEventoCorporativo;
	private boolean visibleCheck;
	/**
	 * @return the idBitacoraCambios
	 */
	public Long getIdBitacoraCambios() {
		return idBitacoraCambios;
	}
	/**
	 * @param idBitacoraCambios the idBitacoraCambios to set
	 */
	public void setIdBitacoraCambios(Long idBitacoraCambios) {
		this.idBitacoraCambios = idBitacoraCambios;
	}
	/**
	 * @return the fechaModificacion
	 */
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	/**
	 * @param fechaModificacion the fechaModificacion to set
	 */
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	/**
	 * @return the formatoOriginal
	 */
	public String getFormatoOriginal() {
		return formatoOriginal;
	}
	/**
	 * @param formatoOriginal the formatoOriginal to set
	 */
	public void setFormatoOriginal(String formatoOriginal) {
		this.formatoOriginal = formatoOriginal;
	}
	/**
	 * @return the visible
	 */
	public Long getVisible() {
		return visible;
	}
	/**
	 * @param visible the visible to set
	 */
	public void setVisible(Long visible) {
		this.visible = visible;
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
	 * @return the idEventoCorporativo
	 */
	public Long getIdEventoCorporativo() {
		return idEventoCorporativo;
	}
	/**
	 * @param idEventoCorporativo the idEventoCorporativo to set
	 */
	public void setIdEventoCorporativo(Long idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	/**
	 * @return the visibleCheck
	 */
	public boolean isVisibleCheck() {
		return visibleCheck;
	}
	/**
	 * @param visibleCheck the visibleCheck to set
	 */
	public void setVisibleCheck(boolean visibleCheck) {
		this.visibleCheck = visibleCheck;
	}
	

}
