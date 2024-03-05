/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Creado el Dec 31, 2007
 */
package com.indeval.portaldali.middleware.services.common.constants;

/**
 * Constantes comnes al Sistema de consulta de estado de cuenta.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public interface CommonConstants {

    /**
     * La preposición utilizada para construir la descripción del registro contable
     */
    String PREPOSICION_POR = " POR ";

    /**
     * La preposición utilizada para construir la descripción del registro contable
     */
    String SUFIJO_DESCRIPCION_OPERACIONES_CONTROLADA = " A CUENTA CONTROLADA POR FIN DE CICLO";

    /**
     * La cantidad mxima de resultados a obtener en una consulta de un suggestion box
     */
    int CANTIDAD_MAXIMA_RESULTADOS_SUGGESTION_BOX = 500;

}
