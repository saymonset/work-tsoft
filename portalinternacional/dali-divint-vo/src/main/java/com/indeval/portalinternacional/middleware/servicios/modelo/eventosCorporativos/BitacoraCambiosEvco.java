/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author kode-
 *
 */
@Entity
@Table(name = "T_BITACORA_CAMBIOS_EVCO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_BITACORA_CAMBIOS_EVCO", allocationSize = 1, initialValue = 1)
public class BitacoraCambiosEvco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idBitacoraCambios;
	private Date fechaModificacion;
	private String formatoOriginal;
	private Long visible;
	private String usuario;
	private Long idEventoCorporativo;
	/**
	 * @return the idBitacoraCambios
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_BITACORA_CAMBIOS", unique = true, nullable = false)
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
	@Column(name = "FECHAMODIFICACION", unique = false, nullable = false)
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
	@Lob
	@Column(name = "FORMATO_ORIGINAL", unique = true, nullable = false)
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
	@Column(name = "VISIBLE", unique = false, nullable = false)
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
	@Column(name = "USUARIO", unique = false, nullable = false)
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
	@Column(name = "ID_EVENTO_CORPORATIVO", unique = false, nullable = false)
	public Long getIdEventoCorporativo() {
		return idEventoCorporativo;
	}
	/**
	 * @param idEventoCorporativo the idEventoCorporativo to set
	 */
	public void setIdEventoCorporativo(Long idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	
	
	
}
