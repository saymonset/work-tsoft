package com.indeval.portaldali.persistence.model;

import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.lob.ClobImpl;

@Entity
@Table(name = "T_BITACORA_REENVIO")
@SequenceGenerator(name = "SeqBitacoraReenvioGenerator", sequenceName = "SEQ_T_BITACORA_REENVIO", allocationSize = 1, initialValue = 1)
public class BitacoraReenvio {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqBitacoraReenvioGenerator")
	@Column(name ="ID_BITACORA_REENVIO", unique = true, nullable = false)
	private Long idBitacoraReenvio;
	
	@Column(name ="FECHA_REENVIO", unique = false, nullable = false)
	private Date fechaReenvio;
	
	@Column(name ="USUARIO", unique = false, nullable = false)
	private String usuario;
	
	@Column(name ="ORIGEN", unique = false, nullable = false)
	private String origen;
	
	@Column(name ="DESTINO", unique = false, nullable = false)
	private String destino;
	
	@Column(name ="XML", nullable = false)
	private Clob xml;

	public Long getIdBitacoraReenvio() {
		return idBitacoraReenvio;
	}

	public void setIdBitacoraReenvio(Long idBitacoraReenvio) {
		this.idBitacoraReenvio = idBitacoraReenvio;
	}

	public Date getFechaReenvio() {
		return fechaReenvio;
	}

	public void setFechaReenvio(Date fechaReenvio) {
		this.fechaReenvio = fechaReenvio;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Clob getXml() {
		return xml;
	}

	public void setXml(Clob xml) {
		this.xml = xml;
	}

	public void setXml(String xml) {
		this.xml = new ClobImpl(xml);
	}
}
