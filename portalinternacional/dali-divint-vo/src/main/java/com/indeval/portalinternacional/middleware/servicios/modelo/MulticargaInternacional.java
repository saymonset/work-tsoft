package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "T_MULTICARGA_INTERNACIONAL")
@SequenceGenerator(name = "foliador", sequenceName = "ID_MULTICARGA_SEQ", allocationSize = 1, initialValue = 1)
public class MulticargaInternacional implements Serializable{
	
	/**
	 * Constante de serializacion por default 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * identificador de la multicarga internacional
	 * */
	private Long idMulticargaInternacional;
	
	/**
	 * fecha y hora de la carga
	 * */
	private Date fechaHoraCarga;
	
	/**
	 * numero de regitros que contiene el archivo
	 * */
	private Long numeroRegistros;
	
	/**
	 * tipo de operaci&oacute;n
	 * */
	private TipoOperacionMulticarga tipoOperacionMulticarga;
	
	/**
	 * indica el nombre del archivo
	 * */
	private String nombreArchivo;
	
	/**
	 *nombre del usuario que efectuo la carga 
	 * */
	private String nombreUsuarioCarga;
	
	/**
	 * nombre del usuario que realizo la autorizaci&oacute;n
	 * */
	private String nombreUsuarioAutorizacion;
	
	/**
	 * indica el estado de la carga
	 * */
	private EstadoMulticarga estadoMulticarga;
		
	/**
	 * indica la fecha y hora en la que se actualizo el archivo
	 * */
	private Date fechaHoraActualizacion;
	
		
	/**
	 * secci&oacute;n de geters and seters
	 * */	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name ="ID_MULTICARGA_INTERNACIONAL", unique = true, nullable = false)
	public Long getIdMulticargaInternacional() {
		return idMulticargaInternacional;
	}
	
	@Column(name ="FECHA_HORA_CARGA", unique = false, nullable = false)
	public Date getFechaHoraCarga() {
		return fechaHoraCarga;
	}
	
	@Column(name ="NUMERO_REGISTROS", unique = false, nullable = true)
	public Long getNumeroRegistros() {
		return numeroRegistros;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TIPO_OPERACION_MULTICARGA", nullable = false)
	public TipoOperacionMulticarga getTipoOperacionMulticarga() {
		return tipoOperacionMulticarga;
	}
	
	@Column(name ="NOMBRE_ARCHIVO", unique = false, nullable = true)
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	
	@Column(name ="NOMBRE_USUARIO_CARGA", unique = false, nullable = false)
	public String getNombreUsuarioCarga() {
		return nombreUsuarioCarga;
	}
	
	@Column(name ="NOMBRE_USUARIO_AUTORIZACION", unique = false, nullable = true)
	public String getNombreUsuarioAutorizacion() {
		return nombreUsuarioAutorizacion;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ESTADO_MULTICARGA", nullable = false)
	public EstadoMulticarga getEstadoMulticarga() {
		return estadoMulticarga;
	}
	

	@Column(name ="FECHA_HORA_ACTUALIZACION", unique = false, nullable = true)
	public Date getFechaHoraActualizacion() {
		return fechaHoraActualizacion;
	}
		
	
	public void setIdMulticargaInternacional(Long idMulticargaInternacional) {
		this.idMulticargaInternacional = idMulticargaInternacional;
	}	

	public void setFechaHoraCarga(Date fechaHoraCarga) {
		this.fechaHoraCarga = fechaHoraCarga;
	}

	public void setNumeroRegistros(Long numeroRegistros) {
		this.numeroRegistros = numeroRegistros;
	}	

	public void setTipoOperacionMulticarga(
			TipoOperacionMulticarga tipoOperacionMulticarga) {
		this.tipoOperacionMulticarga = tipoOperacionMulticarga;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public void setNombreUsuarioCarga(String nombreUsuarioCarga) {
		this.nombreUsuarioCarga = nombreUsuarioCarga;
	}

	public void setNombreUsuarioAutorizacion(String nombreUsuarioAutorizacion) {
		this.nombreUsuarioAutorizacion = nombreUsuarioAutorizacion;
	}

	public void setEstadoMulticarga(EstadoMulticarga estadoMulticarga) {
		this.estadoMulticarga = estadoMulticarga;
	}
			
	public void setFechaHoraActualizacion(Date fechaHoraActualizacion) {
		this.fechaHoraActualizacion = fechaHoraActualizacion;
	}
		
}
