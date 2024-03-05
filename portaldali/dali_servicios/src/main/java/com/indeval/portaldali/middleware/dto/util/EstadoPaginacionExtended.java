/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 2, 2008
 */
package com.indeval.portaldali.middleware.dto.util;

import java.util.List;

/**
 * DTO que representa el estado de la paginación pero además contiene
 * información auxiliar para poder realizar paginaciones más complejas.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class EstadoPaginacionExtended extends EstadoPaginacionDTO {

	private static final long serialVersionUID = 1L;

	/** El indice del registro para la primer consulta */
	private int indice = 0;

	/** El indice del registro para ls segunda consulta */
	private int subindice = 0;

	/** Lista con los resultados por pagina. */
	@SuppressWarnings("rawtypes")
	private List resultadosPorPagina;
	
	/**
	 * Obtiene el valor del atributo indice
	 * 
	 * @return el valor del atributo indice
	 */
	public int getIndice() {
		return indice;
	}

	/**
	 * Establece el valor del atributo indice
	 * 
	 * @param indice
	 *            el valor del atributo indice a establecer
	 */
	public void setIndice(int indice) {
		this.indice = indice;
	}

	/**
	 * Obtiene el valor del atributo subindice
	 * 
	 * @return el valor del atributo subindice
	 */
	public int getSubindice() {
		return subindice;
	}

	/**
	 * Establece el valor del atributo subindice
	 * 
	 * @param subindice
	 *            el valor del atributo subindice a establecer
	 */
	public void setSubindice(int subindice) {
		this.subindice = subindice;
	}

	/**
	 * @return the resultadosPorPagina
	 */
	@SuppressWarnings("rawtypes")
	public List getResultadosPorPagina() {
		return resultadosPorPagina;
	}

	/**
	 * @param resultadosPorPagina the resultadosPorPagina to set
	 */
	@SuppressWarnings("rawtypes")
	public void setResultadosPorPagina(List resultadosPorPagina) {
		this.resultadosPorPagina = resultadosPorPagina;
	}

}
