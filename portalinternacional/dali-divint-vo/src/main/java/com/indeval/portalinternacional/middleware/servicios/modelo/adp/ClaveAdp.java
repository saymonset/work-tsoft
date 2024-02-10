package com.indeval.portalinternacional.middleware.servicios.modelo.adp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "C_CLAVE_ADP")
@SequenceGenerator(name = "foliador", sequenceName = "ID_CLAVE_ADP_SEQ", allocationSize = 1, initialValue = 1)
@Proxy(lazy = false)
public class ClaveAdp implements Serializable{

	/**
	 * Constante de serialización por default
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Long
	 */
	private Long idClaveAdp;
	
	/**
	 * String
	 */
	private String claveAdp;

	/**
	 * @return Long
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "id_clave_adp", unique = true, nullable = false)
	public Long getIdClaveAdp() {
		return this.idClaveAdp;
	}

	/**
	 * @param idClaveAdp
	 */
	public void setIdClaveAdp(Long idClaveAdp) {
		this.idClaveAdp = idClaveAdp;
	}

	/**
	 * @return String
	 */
	@Column(name = "clave_adp", unique = true, nullable = true)
	public String getClaveAdp() {
		return this.claveAdp;
	}

	/**
	 * @param claveAdp
	 */
	public void setClaveAdp(String claveAdp) {
		this.claveAdp = claveAdp;
	}

	@Override
	public String toString() {
		return "ClaveAdp [idClaveAdp=" + idClaveAdp + ", claveAdp=" + claveAdp + "]";
	}
}