package com.indeval.portalinternacional.middleware.servicios.modelo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Fernando Pineda
 * 
 */
@Entity
@Table(name = "C_PAIS_INT")
public class PaisInt implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2809352333787774957L;
	
	public static final int ID_PAIS_MEXICO=1;
	public static final int ID_PAIS_CHILE=48;
	public static final int ID_PAIS_PERU=176;
	public static final int ID_PAIS_COLOMBIA=51;
	public static final int ID_PAIS_USA=235;

	@Id
	@Column(name = "ID_PAIS")
	private Integer idPais;
	
	@Column(name = "CLAVE")
	private String clave;
	
	@Column(name = "NOMBRE_PAIS")
	private String nombrePais;
	
	@Column(name = "CODIGO_MILA")
	private Long codigoMila;
	
	

	public PaisInt() {
		super();
	}

	public PaisInt(Integer idPais) {
		super();
		this.idPais = idPais;
	}

	/**
	 * @return the idPais
	 */
	public Integer getIdPais() {
		return idPais;
	}

	/**
	 * @param idPais the idPais to set
	 */
	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return the nombrePais
	 */
	public String getNombrePais() {
		return nombrePais;
	}

	/**
	 * @param nombrePais the nombrePais to set
	 */
	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}

	/**
	 * @return
	 */
	public Long getCodigoMila() {
		return codigoMila;
	}

	/**
	 * @param codigoMila
	 */
	public void setCodigoMila(Long codigoMila) {
		this.codigoMila = codigoMila;
	}
}