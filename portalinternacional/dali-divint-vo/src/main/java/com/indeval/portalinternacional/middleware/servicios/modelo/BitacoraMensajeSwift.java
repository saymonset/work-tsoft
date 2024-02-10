package com.indeval.portalinternacional.middleware.servicios.modelo;

import org.hibernate.annotations.Immutable;

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

@Entity
@Table(name = "V_BITACORA_MENSAJE_SWIFT")
@Immutable
@SequenceGenerator(name = "foliador", sequenceName = "ID_BITACORA_SWIFT_SEQ", allocationSize = 1, initialValue = 1)
public class BitacoraMensajeSwift implements Serializable {
	
	/**
	 *Clase modificada para multidivisas
	 * Se hace el cambio de entidad a Inmutable para poder consultar
	 * al tiempo T_BITACORA_SWIFT y T_CUENTA_TRANSITORIA (donde viven los mensajes 9XX)
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//ID_BITACORA_MENSAJE
	private Long idCalendario;//ID_CALENDARIO_INT
	private Date fecha;//FECHA_NOTIFICACION
	private String mensaje;//MENSAJE
	private String origen;//ORIGEN
	private String tipoMensaje;//TIPO_MENSAJE
	
	public BitacoraMensajeSwift() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param idCalendario
	 * @param fecha
	 * @param mensaje
	 * @param origen
	 * @param tipoMensaje
	 */
	public BitacoraMensajeSwift(Long id, Long idCalendario, Date fecha,
			String mensaje, String origen, String tipoMensaje) {
		this.id = id;
		this.idCalendario = idCalendario;
		this.fecha = fecha;
		this.mensaje = mensaje;
		this.origen = origen;
		this.tipoMensaje = tipoMensaje;
	}

	/**
	 * @return the id
	 */
	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "indice")
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the idCalendario
	 */
	@Column(name = "ID_CALENDARIO_INT")
	public Long getIdCalendario() {
		return idCalendario;
	}

	/**
	 * @param idCalendario the idCalendario to set
	 */
	public void setIdCalendario(Long idCalendario) {
		this.idCalendario = idCalendario;
	}

	/**
	 * @return the fecha
	 */
	@Column(name = "FECHA_NOTIFICACION")
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
	 * @return the origen
	 */
	@Column(name = "CUSTODIO_ORIGEN")
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the tipoMensaje
	 */
	@Column(name = "TIPO_MENSAJE")
	public String getTipoMensaje() {
		return tipoMensaje;
	}

	/**
	 * @param tipoMensaje the tipoMensaje to set
	 */
	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

}
