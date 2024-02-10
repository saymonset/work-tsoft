package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "T_OPCIONES_EVCO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_OPCIONES_EVCO", allocationSize = 1, initialValue = 1)
public class OpcionesEventoCorporativo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idOpcion;
	private Long idEventoCorporativo;
	private String cuerpo;
	private Date fechaIndeval;
	private Date fechaCliente;
	private Date fechaPago;
	private Long opcDefault;
	private Integer numeroOpcion;
	private String operacionARealizar;
	private boolean borrado;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_OPCION", unique = true, nullable = false)
	public Long getIdOpcion() {
		return idOpcion;
	}
	
	public void setIdOpcion(Long idOpcion) {
		this.idOpcion = idOpcion;
	}
	
	@Column(name="ID_EVENTO_CORPORATIVO", unique = true, nullable = false)
	public Long getIdEventoCorporativo() {
		return idEventoCorporativo;
	}
	
	public void setIdEventoCorporativo(Long idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	
	@Column(name="CUERPO", unique = true, nullable = false)
	public String getCuerpo() {
		return cuerpo;
	}
	
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	
	@Column(name="FECHA_INDEVAL", unique = true, nullable = true)
	public Date getFechaIndeval() {
		return fechaIndeval;
	}
	
	public void setFechaIndeval(Date fechaIndeval) {
		this.fechaIndeval = fechaIndeval;
	}
	
	@Column(name="FECHA_CLIENTE", unique = true, nullable = true)
	public Date getFechaCliente() {
		return fechaCliente;
	}
	
	public void setFechaCliente(Date fechaCliente) {
		this.fechaCliente = fechaCliente;
	}
	
	@Column(name="FECHA_PAGO", unique = true, nullable = true)
	public Date getFechaPago() {
		return fechaPago;
	}
	
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * @return the opcDefault
	 */
	@Column(name="OPC_DEFAULT", unique = true, nullable = false)
	public Long getOpcDefault() {
		return opcDefault;
	}

	/**
	 * @param opcDefault the opcDefault to set
	 */
	public void setOpcDefault(Long opcDefault) {
		this.opcDefault = opcDefault;
	}

	/**
	 * @return the numeroOpcion
	 */
	
	@Column(name="NUM_OPCION", unique = false, nullable = true)
	public Integer getNumeroOpcion() {
		return numeroOpcion;
	}

	/**
	 * @param numeroOpcion the numeroOpcion to set
	 */
	public void setNumeroOpcion(Integer numeroOpcion) {
		this.numeroOpcion = numeroOpcion;
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

	/**
	 * @return the borrado
	 */
	@Column(name = "BORRADO", columnDefinition = "BIT", length = 1, unique = false, nullable = false)	
	public boolean getBorrado() {
		return borrado;
	}

	/**
	 * @param borrado the borrado to set
	 */
	public void setBorrado(boolean borrado) {
		this.borrado = borrado;
	}
	
}
