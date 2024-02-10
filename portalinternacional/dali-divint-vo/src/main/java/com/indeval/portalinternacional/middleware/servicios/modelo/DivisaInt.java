package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Cata&acute;logo con las divisas en las cuales se denominan las emisiones y el
 * efectivo, homologado con la norma ISO 4217.
 *
 * C_DIVISA
 *
 * @author Enrique Guzman
 * @version 1.0
 */
@Entity
@Table(name = "C_DIVISA")
public class DivisaInt implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la divisa.
	 */
	@Id
	@Column(name = "ID_DIVISA")
	private BigInteger idDivisa;
	/**
	 * Clave alfabetica de la divisa.
	 */
	@Column(name = "CLAVE_ALFABETICA")
	private String claveAlfabetica;
	/**
	 * Clave numerica de la divisa.
	 */
	@Column(name = "CLAVE_NUMERICA")
	private String claveNumerica;
	/**
	 * DEscripci&oacute;n de la divisa.
	 */
	@Column(name = "DESCRIPCION")
	private String descripcion;

	/**
	 * Identificador secuencial de Moneda.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getIdDivisa() {
		return idDivisa;
	}

	/**
	 * @param idDivisa
	 */
	public void setIdDivisa(BigInteger idDivisa) {
		this.idDivisa = idDivisa;
	}

	/**
	 * C&oacute;digo alfab&eacute;tico de la divisa.
	 * 
	 * @return String
	 */
	public String getClaveAlfabetica() {
		return claveAlfabetica;
	}

	/**
	 * @param claveAlfabetica
	 */
	public void setClaveAlfabetica(String claveAlfabetica) {
		this.claveAlfabetica = claveAlfabetica;
	}

	/**
	 * C&oacute;digo num&eacute;rico de la divisa.
	 * 
	 * @return String
	 */
	public String getClaveNumerica() {
		return claveNumerica;
	}

	/**
	 * C&oacute;digo num&eacute;rico de la divisa.
	 * 
	 * @param claveNumerica
	 */
	public void setClaveNumerica(String claveNumerica) {
		this.claveNumerica = claveNumerica;
	}

	/**
	 * Descripci&oacute;n de la divisa.
	 * 
	 * @return String
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Descripci&oacute;n de la divisa.
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
