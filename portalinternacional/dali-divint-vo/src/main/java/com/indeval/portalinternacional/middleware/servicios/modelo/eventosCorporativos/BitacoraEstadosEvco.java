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
@Table(name = "T_Bitacora_Estados_EVCO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_Bitacora_Estados_EVCO", allocationSize = 1, initialValue = 1)
public class BitacoraEstadosEvco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idBitacoraEstados;
	private Long idEventoCorporativo;
	private Date fechaModificacion;
	private Long idEstadoInicial;
	private Long idEstadoFinal;
	private String usuario;
	
	/**
	 * @return the idBitacoraCambios
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "id_bitacora_estados", unique = true, nullable = false)
	public Long getIdBitacoraEstados() {
		return idBitacoraEstados;
	}
	/**
	 * @param idBitacoraCambios the idBitacoraCambios to set
	 */
	public void setIdBitacoraEstados(Long idBitacoraCambios) {
		this.idBitacoraEstados = idBitacoraCambios;
	}
	/**
	 * @return the idEventoCorporativo
	 */
	@Column(name = "id_Evento_corporativo", unique = false, nullable = false)
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
	 * @return the fechaModificacion
	 */
	@Column(name = "fecha", unique = false, nullable = false)
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
	 * @return the idEstadoInicial
	 */
	@Column(name = "id_estado_origen", unique = false, nullable = false)
	public Long getIdEstadoInicial() {
		return idEstadoInicial;
	}
	/**
	 * @param idEstadoInicial the idEstadoInicial to set
	 */
	public void setIdEstadoInicial(Long idEstadoInicial) {
		this.idEstadoInicial = idEstadoInicial;
	}
	/**
	 * @return the idEstadoFinal
	 */
	@Column(name = "id_estado_final", unique = false, nullable = false)
	public Long getIdEstadoFinal() {
		return idEstadoFinal;
	}
	/**
	 * @param idEstadoFinal the idEstadoFinal to set
	 */
	public void setIdEstadoFinal(Long idEstadoFinal) {
		this.idEstadoFinal = idEstadoFinal;
	}
	/**
	 * @return the usuario
	 */
	@Column(name = "usuario", unique = false, nullable = false)
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
		
}
