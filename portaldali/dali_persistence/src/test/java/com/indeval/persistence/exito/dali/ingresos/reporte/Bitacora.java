/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.ingresos.reporte;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class Bitacora implements Serializable {

	private Long idInstitucion;
	private String idFolio;
	private String nombreInstitucion;
	private Long idConsulta;
	private String nombreConsulta;
	private int totalConsultas;
	private int totalRegistros;
	private int gratis;

	public static String tab = "\t";

	public Bitacora() {
		super();
	}

	public Long getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getIdFolio() {
		return idFolio;
	}

	public void setIdFolio(String idFolio) {
		this.idFolio = idFolio;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	public Long getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(Long idConsulta) {
		this.idConsulta = idConsulta;
	}

	public String getNombreConsulta() {
		return nombreConsulta;
	}

	public void setNombreConsulta(String nombreConsulta) {
		this.nombreConsulta = nombreConsulta;
	}

	public int getTotalConsultas() {
		return totalConsultas;
	}

	public void setTotalConsultas(int totalConsultas) {
		this.totalConsultas = totalConsultas;
	}

	public int getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public int getGratis() {
		return gratis;
	}

	public void setGratis(int gratis) {
		this.gratis = gratis;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 23)
			.append(idInstitucion)
			.append(idConsulta)
			.toHashCode();
	}

	/**
	 * Implementacion del metodo equals.
	 */
	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if (obj != null &&
				obj instanceof Bitacora) {
			Bitacora bitacora = (Bitacora) obj;
			isEqual = new EqualsBuilder()
				.append(idInstitucion, bitacora.getIdInstitucion())
				.append(idConsulta, bitacora.getIdConsulta())
				.isEquals();
		}

		return isEqual;
	}

	/**
	 * Implementacion del metodo toString.
	 */
	@Override
	public String toString() {
		StringBuilder retorno = new StringBuilder();
		retorno.append(idFolio);
		retorno.append(tab);
		retorno.append(nombreInstitucion);
		retorno.append(tab);
		retorno.append(nombreConsulta);
		retorno.append(tab);
		retorno.append(totalConsultas);
		retorno.append(tab);
		retorno.append(totalRegistros);
		retorno.append(tab);
		retorno.append(gratis);
		retorno.append(tab);
		retorno.append((totalRegistros-gratis));

		return retorno.toString();
	}

}
