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
@Table(name = "R_CAMV_CAEV")
public class TipoPagoIntBR implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;//ID_CAEV_CAMV
	private Long idCaev;//ID_CAEV
	private Long idCamv;//ID_CAMV
	private String tipo;//TIPO
	
		
	
	/**
	 * @param id
	 * @param idCaev
	 * @param idCamv
	 * @param tipo
	 */
	public TipoPagoIntBR(Long id, Long idCaev, Long idCamv, String tipo) {
		this.id = id;
		this.idCaev = idCaev;
		this.idCamv = idCamv;
		this.tipo = tipo;
	}



	public TipoPagoIntBR(){
		
	}



	/**
	 * @return the id
	 */
	@Id		
	@Column(name = "ID_CAEV_CAMV")
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
	 * @return the idCaev
	 */
	@Column(name = "ID_CAEV")
	public Long getIdCaev() {
		return idCaev;
	}



	/**
	 * @param idCaev the idCaev to set
	 */
	public void setIdCaev(Long idCaev) {
		this.idCaev = idCaev;
	}



	/**
	 * @return the idCamv
	 */
	@Column(name = "ID_CAMV")
	public Long getIdCamv() {
		return idCamv;
	}



	/**
	 * @param idCamv the idCamv to set
	 */
	public void setIdCamv(Long idCamv) {
		this.idCamv = idCamv;
	}



	/**
	 * @return the tipo
	 */
	@Column(name = "TIPO")
	public String getTipo() {
		return tipo;
	}



	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
		
}
