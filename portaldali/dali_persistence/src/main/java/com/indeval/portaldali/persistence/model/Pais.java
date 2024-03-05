/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Cata&acute;logo de paises
 * 
 * C_PAISES
 *
 * @author  Maria C. Buendia
 * @version 1.0
 */
@Entity
@Table(name="C_PAISES")
@SequenceGenerator(name = "SEQ_Pais", sequenceName = "C_PAISES_ID_SEQ",allocationSize = 1,initialValue = 1)
public class Pais implements Serializable {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Id asignado para cada pais */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Pais")
	@Column(name = "ID_PAIS", nullable = false)
	private BigInteger idPais;

	/** clave */
	@Column(name = "CLAVE")
	private String clave;
	
	/** nombre pais */
	@Column(name = "NOMBRE_PAIS")
	private String nombrePais;

	public BigInteger getIdPais() {
		return idPais;
	}

	public void setIdPais(BigInteger idPais) {
		this.idPais = idPais;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombrePais() {
		return nombrePais;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}
}
