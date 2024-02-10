package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "C_TIPO_PAGO_INT")
public class TipoPagoInt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//ID_TIPO_PAGO
	private String clavePago;//CLAVE_PAGO
	private String descripcion;//DESCRIPCION
	private Integer caev;//CAEV
	private Integer participante;//PARTICIPANTE
	
	public TipoPagoInt(){
		
	}
	
	/**
	 * @param id
	 * @param clavePago
	 * @param descripcion
	 */
	public TipoPagoInt(Long id, String clavePago, String descripcion) {
		this.id = id;
		this.clavePago = clavePago;
		this.descripcion = descripcion;
	}

	/**
	 * @return the id
	 */
	@Id		
	@Column(name = "ID_TIPO_PAGO")
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
	 * @return the clavePago
	 */
	@Column(name = "CLAVE_PAGO")
	public String getClavePago() {
		return clavePago;
	}
	/**
	 * @param clavePago the clavePago to set
	 */
	public void setClavePago(String clavePago) {
		this.clavePago = clavePago;
	}
	/**
	 * @return the descripcion
	 */
	@Column(name = "DESCRIPCION")
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * 
	 * @return the caev
	 */
	@Column(name = "CAEV")
	public Integer getCaev() {
		return caev;
	}

	/**
	 * 
	 * @param caev the caev to set
	 */
	public void setCaev(Integer caev) {
		this.caev = caev;
	}

	/**
	 * @return the participante
	 */
	@Column(name = "PARTICIPANTE")
	public Integer getParticipante() {
		return participante;
	}

	/**
	 * @param participante the participante to set
	 */
	public void setParticipante(Integer participante) {
		this.participante = participante;
	}
}
