/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

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
 * 
 * @author lmunoz
 *
 */
@Entity
@Table(name = "T_BITACORA_CONFIRMACION")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_BITACORA_CONFIRMACION", allocationSize = 1)
public class BitacoraConfirmacion implements Serializable{
	private static final long serialVersionUID = -1L;
	private Long idBitacoraConfirmacion;
	private Long idConfirmacion;
	private Date fechaEmision;
	private Date fechaRecepcion;	
	private String folioMensaje;
	private String mensaje;
	private String chk;
	/**
	 * @return the idBitacoraConfirmacion
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_BITACORA_CONFIRMACION")
	public Long getIdBitacoraConfirmacion() {
		return idBitacoraConfirmacion;
	}
	/**
	 * @param idBitacoraConfirmacion the idBitacoraConfirmacion to set
	 */
	public void setIdBitacoraConfirmacion(Long idBitacoraConfirmacion) {
		this.idBitacoraConfirmacion = idBitacoraConfirmacion;
	}
	/**
	 * @return the idConfirmacion
	 */
	@Column(name = "ID_CONFIRMACION")
	public Long getIdConfirmacion() {
		return idConfirmacion;
	}
	/**
	 * @param idConfirmacion the idConfirmacion to set
	 */
	public void setIdConfirmacion(Long idConfirmacion) {
		this.idConfirmacion = idConfirmacion;
	}
	/**
	 * @return the fechaEmision
	 */
	@Column(name = "FECHA_EMISION")
	public Date getFechaEmision() {
		return fechaEmision;
	}
	/**
	 * @param fechaEmision the fechaEmision to set
	 */
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	/**
	 * @return the fechaRecepcion
	 */
	@Column(name = "FECHA_RECEPCION")
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	/**
	 * @param fechaRecepcion the fechaRecepcion to set
	 */
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	
	/**
	 * @return the folioMensaje
	 */
	@Column(name = "FOLIO_MENSAJE")
	public String getFolioMensaje() {
		return folioMensaje;
	}
	/**
	 * @param folioMensaje the folioMensaje to set
	 */
	public void setFolioMensaje(String folioMensaje) {
		this.folioMensaje = folioMensaje;
	}
	/**
	 * @return the mensaje
	 */	
	@Lob
	@Column(name = "MENSAJE")
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return the chk
	 */
	@Column(name = "CHK")
	public String getChk() {
		return chk;
	}
	/**
	 * @param chk the chk to set
	 */
	public void setChk(String chk) {
		this.chk = chk;
	}
}
