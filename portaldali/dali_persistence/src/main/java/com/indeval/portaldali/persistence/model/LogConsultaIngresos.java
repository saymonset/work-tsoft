/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Tabla para guardar los registros de la bitacora de consultas 
 * para los ingresos
 * 
 * T_LOG_CONSULTAS_INGRESOS
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
@Entity
@Table(name = "T_LOG_CONSULTAS_INGRESOS")
@SequenceGenerator(name = "SEQ_LogConsultaIngresos", sequenceName = "T_LOG_CONSULTA_INGRESOS_SEQ",allocationSize = 1,initialValue = 1)
public class LogConsultaIngresos implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador del registro de bitacora
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LogConsultaIngresos")
	@Column(name = "ID_LOG_CONSULTA")
	private Long idLogConsulta;
	/**
	 * Identificadr del tipo de consulta.
	 */
	@Column(name = "ID_TIPO_CONSULTA")
	private Integer idTipoConsulta;
	/**
	 * Ticket asociado.
	 */
	@Column(name = "TICKET")
	private String ticket;
	/**
	 * Fecha y hora del registro.
	 */
	@Column(name = "FECHA_HORA_REGISTRO")
	private Date fechaHoraRegistro;
	/**
	 * Tipo de consulta.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONSULTA")
	private ConsultaIngresos consultaIngresos;
	/**
	 * Numero de registros.
	 */
	@Column(name = "NUMERO_REGISTROS")
	private BigInteger numeroRegistros;
	/**
	 * Institucion asociada.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTITUCION")
	private Institucion institucion;
	/**
	 * @return the idLogConsulta
	 */
	public Long getIdLogConsulta() {
		return idLogConsulta;
	}
	/**
	 * @param idLogConsulta the idLogConsulta to set
	 */
	public void setIdLogConsulta(Long idLogConsulta) {
		this.idLogConsulta = idLogConsulta;
	}
	/**
	 * @return the idTipoConsulta
	 */
	public Integer getIdTipoConsulta() {
		return idTipoConsulta;
	}
	/**
	 * @param idTipoConsulta the idTipoConsulta to set
	 */
	public void setIdTipoConsulta(Integer idTipoConsulta) {
		this.idTipoConsulta = idTipoConsulta;
	}
	/**
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}
	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	/**
	 * @return the fechaHoraRegistro
	 */
	public Date getFechaHoraRegistro() {
		return fechaHoraRegistro;
	}
	/**
	 * @param fechaHoraRegistro the fechaHoraRegistro to set
	 */
	public void setFechaHoraRegistro(Date fechaHoraRegistro) {
		this.fechaHoraRegistro = fechaHoraRegistro;
	}
	/**
	 * @return the consultaIngresos
	 */
	public ConsultaIngresos getConsultaIngresos() {
		return consultaIngresos;
	}
	/**
	 * @param consultaIngresos the consultaIngresos to set
	 */
	public void setConsultaIngresos(ConsultaIngresos consultaIngresos) {
		this.consultaIngresos = consultaIngresos;
	}
	/**
	 * @return the numeroRegistros
	 */
	public BigInteger getNumeroRegistros() {
		return numeroRegistros;
	}
	/**
	 * @param numeroRegistros the numeroRegistros to set
	 */
	public void setNumeroRegistros(BigInteger numeroRegistros) {
		this.numeroRegistros = numeroRegistros;
	}
	/**
	 * @return the institucion
	 */
	public Institucion getInstitucion() {
		return institucion;
	}
	/**
	 * @param institucion the institucion to set
	 */
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}
	
	
}
