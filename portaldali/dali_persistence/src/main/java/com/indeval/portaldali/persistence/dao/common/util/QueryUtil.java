/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * QueryUtil.java
 * 04/03/2008
 */
package com.indeval.portaldali.persistence.dao.common.util;

import java.util.List;

/**
 * Clase Helper para la construcción de cadenas de querys.
 * @author Emigdio Hernández
 *
 */
public class QueryUtil {
	/**
	 * Agrega una condición de AND o WHERE al query según el número de parámetros enviados
	 * para validar.
	 * @param query Query a agregar condición
	 * @param parametros parámetros actualmente usados en la consulta
	 * 
	 */
	public static void agregarCondicion(StringBuffer query,List<Object> parametros){
		if(parametros != null && parametros.size()>0){
			query.append(" and ");
		}else{
			query.append(" where ");
		}
	}
}
