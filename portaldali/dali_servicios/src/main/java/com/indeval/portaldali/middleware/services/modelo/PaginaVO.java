/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class PaginaVO implements Serializable {

	/** Constante de Serializacion */
	private static final long serialVersionUID = 1l;

	/** TODOS = 0 */
	public static final Integer TODOS = new Integer(0);

	/** El primer registro para el offset es el 0 */
	private Integer offset = new Integer(0);

	private Integer totalRegistros = new Integer(0);

	private Integer registrosXPag = PaginaVO.TODOS;

	private List registros = null;

	private Map valores = new HashMap();

	/**
	 * @return Integer
	 */
	public Integer getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * @return Integer
	 */
	public Integer getRegistrosXPag() {
		return registrosXPag;
	}

	/**
	 * @param registrosXPag
	 */
	public void setRegistrosXPag(Integer registrosXPag) {
		this.registrosXPag = registrosXPag;
	}

	/**
	 * @return Integer
	 */
	public Integer getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros
	 */
	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	/**
	 * @return List
	 */
	public List getRegistros() {
		return registros;
	}

	/**
	 * @param registros
	 */
	public void setRegistros(List registros) {
		this.registros = registros;
	}

	/**
	 * @return Map
	 */
	public Map getValores() {
		return valores;
	}

	/**
	 * @param valores
	 */
	public void setValores(Map valores) {
		this.valores = valores;
	}

	/**
	 * Extrae los registros de una lista para ser llenar la pagina.
	 * 
	 * @param list
	 *            de la que se extraen los registros
	 * @return Esta misma pagina.
	 */
	public PaginaVO extraerSublist(List list) {
		List subLista = new ArrayList();

		if (registrosXPag.equals(TODOS)) {
			registros = list;
			totalRegistros = new Integer(list.size());
		} else {
			int fromIndex = offset.intValue();

			int toIndex = offset.intValue() + registrosXPag.intValue();
			// validacion para evitar IndexOutOfBoundsException en caso de pasar
			// parametro mayor a lista.size()
			if (toIndex > list.size()) {
				toIndex = list.size();
			}
			try {
				// Aqui existia un error pues sublist regresa
				// RandomAccessSubList
				// La cual no es serializable
				subLista.addAll(list.subList(fromIndex, toIndex));
			} catch (IndexOutOfBoundsException e) {
				// handle exception, Supon que la lista.size()=20
				// y el sublist es de 15 a 30 entonces existira un
				// IndexOutOfBoundsException
			}
			setRegistros(subLista);
			totalRegistros = new Integer(list.size());
		}
		return this;
	}

	/**
	 * Actualiza los valores de la pagina con los que trae la pagina que recibe.
	 * 
	 * @param pagina
	 */
	public void update(PaginaVO pagina) {
		offset = pagina.getOffset();
		totalRegistros = pagina.getTotalRegistros();
		registrosXPag = pagina.getRegistrosXPag();
		registros = pagina.getRegistros();
	}

}