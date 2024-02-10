package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "T_NOTIFICACION_EVCO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_NOTIFICACION_EVCO", allocationSize = 1, initialValue = 1)
public class NotificacionEventoCorporativo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tipoNotificacion;
	private Long idNotificacion;
	private String cronexpresion;
	private String mensaje;
	private Long idEventoCorporativo;
	private Long procesado;
	private Date fechaInicio;
	private Date fechaFin;
	private Long vigente;
	private String jobName;
	private ListaDistribucion listaDistribucion;
	private String jobGroup;
	private String operacionARealizar;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_NOTIFICACION", unique = true, nullable = false)
	public Long getIdNotificacion() {
		return idNotificacion;
	}
	
	public void setIdNotificacion(Long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}
	
	@Column(name="CRONEXPRESION", unique = true, nullable = false)
	public String getCronexpresion() {
		return cronexpresion;
	}
	
	public void setCronexpresion(String cronexpresion) {
		this.cronexpresion = cronexpresion;
	}
	
	@Column(name="MENSAJE", unique = false, nullable = false)
	public String getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return the listaDistribucion
	 */
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_LISTA", referencedColumnName="ID_LISTA",unique = false, nullable = true, insertable=true, updatable=true)
	public ListaDistribucion getListaDistribucion() {
		return listaDistribucion;
	}

	/**
	 * @param listaDistribucion the listaDistribucion to set
	 */
	public void setListaDistribucion(ListaDistribucion listaDistribucion) {
		this.listaDistribucion = listaDistribucion;
	}
	
	
	@Column(name="ID_EVENTO_CORPORATIVO", unique = false, nullable = false)
	public Long getIdEventoCorporativo() {
		return idEventoCorporativo;
	}

	public void setIdEventoCorporativo(Long idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	
	@Column(name="PROCESADO", unique = false, nullable = false)
	public Long getProcesado() {
		return procesado;
	}
	
	public void setProcesado(Long procesado) {
		this.procesado = procesado;
	}
	
	@Column(name="FECHA_INICIO", unique = false, nullable = false)
	public Date getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	@Column(name="FECHA_FIN", unique = false, nullable = false)
	public Date getFechaFin() {
		return fechaFin;
	}
	
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	@Column(name="VIGENTE", unique = false, nullable = false)
	public Long getVigente() {
		return vigente;
	}
	
	public void setVigente(Long vigente) {
		this.vigente = vigente;
	}
	
	@Column(name="JOB_NAME", unique = false, nullable = true)
	public String getJobName() {
		return jobName;
	}
	
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the jobGroup
	 */
	@Column(name="JOB_GROUP", unique = false, nullable = true)
	public String getJobGroup() {
		return jobGroup;
	}

	/**
	 * @param jobGroup the jobGroup to set
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	@Column(name="TIPO_NOTIFICACION", unique = false, nullable = true)
	public String getTipoNotificacion() {
		return tipoNotificacion;
	}

	public void setTipoNotificacion(String tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}

	/**
	 * @return the operacionARealizar
	 */
	@Transient
	public String getOperacionARealizar() {
		return operacionARealizar;
	}

	/**
	 * @param operacionARealizar the operacionARealizar to set
	 */
	public void setOperacionARealizar(String operacionARealizar) {
		this.operacionARealizar = operacionARealizar;
	}

}
