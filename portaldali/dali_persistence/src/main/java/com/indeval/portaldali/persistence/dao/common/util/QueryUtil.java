/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 * <p>
 * QueryUtil.java
 * 04/03/2008
 */
package com.indeval.portaldali.persistence.dao.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Helper para la construcción de cadenas de querys.
 *
 * @author Emigdio Hernández
 */
public class QueryUtil {
    private static final Logger logger = LoggerFactory.getLogger(QueryUtil.class);

    /**
     * Agrega una condición de AND o WHERE al query según el número de parámetros enviados
     * para validar.
     *
     * @param query      Query a agregar condición
     * @param parametros parámetros actualmente usados en la consulta
     */
    public static void agregarCondicion(StringBuffer query, List<Object> parametros) {
        if (parametros != null && parametros.size() > 0) {
            query.append(" and ");
        } else {
            query.append(" where ");
        }
    }

    public static void imprimir(String query, ArrayList<Object> params) {
        logger.trace("Query :: ");
        logger.trace(query);
        for (Object param : params) {
            query = query.replaceFirst("\\?", param.toString());
            logger.trace(".........." + param.toString());
        }
        logger.debug(query);
    }
}
