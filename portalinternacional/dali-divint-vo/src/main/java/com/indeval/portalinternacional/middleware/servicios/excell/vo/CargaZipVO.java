/*
 * Copyrigth (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.excell.vo;

import java.beans.Statement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Clase para representar los resultados de la carga de un zip completo
 * 
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class CargaZipVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/** Indica el total de archivos procesados */
	private int totalArchivos = 0;
	/** Lista de resultados */
	private List<CargaExcellVO> resultados;
	/** Mapa de los errore spo archivo */
	private Map<String, String> errores;

	public CargaZipVO() {
		super();
		resultados = new ArrayList<CargaExcellVO>();
		errores = new HashMap<String, String>();
	}

	/**
	 * Indica el total de archivos procesados
	 * @return the totalArchivos
	 */
	public int getTotalArchivos() {
		return totalArchivos;
	}

	/**
	 * Indica el total de archivos procesados
	 * @param totalArchivos the totalArchivos to set
	 */
	public void setTotalArchivos(int totalArchivos) {
		this.totalArchivos = totalArchivos;
	}

	/**
	 * Lista de resultados
	 * @return the resultados
	 */
	public List<CargaExcellVO> getResultados() {
		return resultados;
	}

	/**
	 * Mapa de los errore spo archivo
	 * @return the errores
	 */
	public Map<String, String> getErrores() {
		return errores;
	}

	public List<String> getMapErroresKeys() {
		List<String> retorno = null;
		if(errores != null && errores.size() > 0) {
			retorno = new ArrayList<String>(errores.keySet());
		}
		return retorno;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).
				append("totalArchivos", totalArchivos).toString();
	}
}
