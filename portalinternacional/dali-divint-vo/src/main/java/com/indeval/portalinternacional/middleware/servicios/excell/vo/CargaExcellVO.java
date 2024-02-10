/*
 * Copyrigth (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.excell.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Clase para representar los resultados de la carga de un excell
 * 
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class CargaExcellVO<E> implements Serializable {

	private static final long serialVersionUID = 1L;
	/** Indica el total de registros procesados */
	private int total = 0;
	/** Indica el total de registros cargados */
	private int cargados = 0;
	/** Indica el total de registros no cargados */
	private int noCargados = 0;
	/** Lista de resultados */
	private List<E> resultados;
	 /** Mapa de Errores (numero de columna y el detalle del error) */
	private Map<Integer, String> errores;
	/** Indica el nombre del archivo */
	private String nombreArchivo;

	public CargaExcellVO() {
		super();
		resultados = new ArrayList<E>();
		errores = new HashMap<Integer, String>();
		total = 0;
		cargados = 0;
		noCargados = 0;
	}

	/**
	 * Indica el total de registros procesados
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * Indica el total de registros procesados
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * Indica el total de registros cargados
	 * @return the cargados
	 */
	public int getCargados() {
		return cargados;
	}

	/**
	 * Indica el total de registros cargados
	 * @param cargados the cargados to set
	 */
	public void setCargados(int cargados) {
		this.cargados = cargados;
	}

	/**
	 * Indica el total de registros no cargados
	 * @return the noCargados
	 */
	public int getNoCargados() {
		return noCargados;
	}

	/**
	 * Indica el total de registros no cargados
	 * @param noCargados the noCargados to set
	 */
	public void setNoCargados(int noCargados) {
		this.noCargados = noCargados;
	}

	/**
	 * Lista de resultados
	 * @return the resultados
	 */
	public List<E> getResultados() {
		return resultados;
	}

	/**
	 * Mapa de Errores (numero de columna y el detalle del error)
	 * @return the errores
	 */
	public Map<Integer, String> getErrores() {
		return errores;
	}

	public List<Integer> getMapKeys() {
		List<Integer> retorno = null;
		if(errores != null && errores.size() > 0) {
			retorno = new ArrayList<Integer>(errores.keySet());
		}
		return retorno;
	}

	/**
	 * Indica el nombre del archivo
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * Indica el nombre del archivo
	 * @param nombreArchivo the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).toString();
	}
}
