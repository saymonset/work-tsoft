package com.indeval.portalinternacional.middleware.servicios.modelo;


import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author Arzate-Jacinto A.
 *
 */
@Entity
@Table(name = "T_BITACORA_CONCILIACION_INT")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_BIT_CONCILIACION_INT", allocationSize = 1, initialValue = 1)
public class BitacoraMensajeConciliacionInt implements Serializable {

	
	private static final long serialVersionUID = -2261065275088903397L;

	private Long id;
	private Long conciliacion;
	private String folio;
	private String numeroMensaje;
	private Date fechaMensaje;
	private Date fechaRecepcion;
	private String cuenta;
	private String mensaje;
	private Custodio custodio;
	
	public BitacoraMensajeConciliacionInt() {
		super();
	}
	
	public BitacoraMensajeConciliacionInt(Long id, String folio,
			Long conciliacion, String numeroMensaje,
			Date fechaMensaje, Date fechaRecepcion, String cuenta, String mensaje) {
		super();
		this.id = id;
		this.conciliacion = conciliacion;
		this.folio = folio;
		this.numeroMensaje = numeroMensaje;
		this.fechaMensaje = fechaMensaje;
		this.fechaRecepcion = fechaRecepcion;
		this.cuenta = cuenta;
		this.mensaje = mensaje;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_BITACORA")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
    @Column(name = "ID_CONCILIACION")
	public Long getConciliacion() {
		return conciliacion;
	}
	public void setConciliacion(Long conciliacion) {
		this.conciliacion = conciliacion;
	}
	
	@Column(name = "FOLIO")
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}

	@Column(name = "NUM_MENSAJE")
	public String getNumeroMensaje() {
		return numeroMensaje;
	}
	public void setNumeroMensaje(String numeroMensaje) {
		this.numeroMensaje = numeroMensaje;
	}
	
	@Column(name = "FECHA_MENSAJE")
	public Date getFechaMensaje() {
		return fechaMensaje;
	}
	public void setFechaMensaje(Date fechaMensaje) {
		this.fechaMensaje = fechaMensaje;
	}
	
	@Column(name = "FECHA_RECEPCION")
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	
	@Column(name = "CUENTA")
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	@Lob
	@Column(name = "MENSAJE")
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the custodio
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CUSTODIO", unique = false, nullable = true)
	public Custodio getCustodio() {
		return custodio;
	}

	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(Custodio custodio) {
		this.custodio = custodio;
	}
}