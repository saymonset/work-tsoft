package com.indeval.portalinternacional.middleware.servicios.modelo;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *  Entity creado para el proyecto Multidivisas
 * Se usa como auxiliar para el calculo del Importe Confirmadon
 * implementa el cruce de T_BITACORA_SWIFT y T_CUENTA_TRANSITORIA
 * Pantalla Deuda / Calendario Indeval
 */
@Entity
@Table(name = "V_BITACORA_MENSAJE_SWIFT_IMPORTE")
@Immutable
@SequenceGenerator(name = "foliador", sequenceName = "ID_BITACORA_SWIFT_SEQ", allocationSize = 1, initialValue = 1)
public class BitacoraMensajeSwiftImporte implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "INDICE")
	private Long id;//ID_BITACORA_MENSAJE
	private Long idCalendario;//ID_CALENDARIO_INT
	private Date fecha;//FECHA_NOTIFICACION
	private String mensaje;//MENSAJE
	private String origen;//ORIGEN
	private String tipoMensaje;//TIPO_MENSAJE
	//Dato usado para Multidivisas - Se usa para el calculo de Importe segun los mensajes
	private double importe;//IMPORTE

	public BitacoraMensajeSwiftImporte() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param idCalendario
	 * @param fecha
	 * @param mensaje
	 * @param origen
	 * @param tipoMensaje
	 * @param importe
	 */
	public BitacoraMensajeSwiftImporte(Long id, Long idCalendario, Date fecha,
                                       String mensaje, String origen, String tipoMensaje, double importe) {
		this.id = id;
		this.idCalendario = idCalendario;
		this.fecha = fecha;
		this.mensaje = mensaje;
		this.origen = origen;
		this.tipoMensaje = tipoMensaje;
		this.importe = importe;
	}

	/**
	 * @return the id
	 */

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
	@Column(name = "IDTRANSACCION")
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
	@Column(name = "FECHA")
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
	@Column(name = "ORIGEN")
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
	@Column(name = "TIPOMENSAJE")
	public String getTipoMensaje() {
		return tipoMensaje;
	}

	/**
	 * @param tipoMensaje the tipoMensaje to set
	 */
	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	//AÑADIDO PARA MULTIDIVISAS - CALCULO DE IMPORTE SEGUN EL TIPO DE MENSAJES
	@Column(name = "IMPORTE")
	public double getImporte(){
		return importe;
	}
	//Añadido para multidivisas - para el calculo de importe
	public void setImporte(double importe){
		this.importe=importe;
	}
}
