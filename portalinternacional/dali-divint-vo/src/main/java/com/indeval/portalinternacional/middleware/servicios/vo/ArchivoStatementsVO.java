/*
 * Copyrigth (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Clase para representar un archivo que contiene statements
 * 
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class ArchivoStatementsVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/** Nombre del archivo de statements */
	private String nombreArchivo;
	/** Cantidad de registros del archivo */
	private Long cantidadRegistros;
	/** Indica si el archivo esta seleccionado */
	private boolean selected;

	public ArchivoStatementsVO() {
		super();
		selected = false;
	}

	public ArchivoStatementsVO(String nombreArchivo, Long cantidadRegistros) {
		this();
		this.nombreArchivo = nombreArchivo;
		this.cantidadRegistros = cantidadRegistros;
	}

	/**
	 * Nombre del archivo de statements
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * Nombre del archivo de statements
	 * @param nombreArchivo the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	/**
	 * Cantidad de registros del archivo
	 * @return the cantidadRegistros
	 */
	public Long getCantidadRegistros() {
		return cantidadRegistros;
	}

	/**
	 * Cantidad de registros del archivo
	 * @param cantidadRegistros the cantidadRegistros to set
	 */
	 public void setCantidadRegistros(Long cantidadRegistros) {
		this.cantidadRegistros = cantidadRegistros;
	}

	/**
	 * Indica si el archivo esta seleccionado
	 * @return the selected
	 */
	 public boolean isSelected() {
		return selected;
	}

	/**
	 * Indica si el archivo esta seleccionado
	 * @param selected the selected to set
	 */
	 public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if (obj instanceof ArchivoStatementsVO) {
			ArchivoStatementsVO archivo = (ArchivoStatementsVO) obj;
			isEqual = new EqualsBuilder().append(nombreArchivo, archivo.nombreArchivo).isEquals();
		}
		return isEqual;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 23).append(nombreArchivo).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).
				append("nombreArchivo", nombreArchivo).
				append("cantidadRegistros", cantidadRegistros).
				append("selected", selected).
				toString();
	}
}
