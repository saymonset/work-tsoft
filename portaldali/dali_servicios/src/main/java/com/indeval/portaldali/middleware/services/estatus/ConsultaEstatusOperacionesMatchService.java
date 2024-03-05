/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaEstatusOperacionesMatchService.java
 * 04/03/2008
 */
package com.indeval.portaldali.middleware.services.estatus;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.dto.OperacionValorMatchDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.model.util.ConsultaOperacionesMatch;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesExportacionDTO;

/**
 * Interfaz que define el contrato del servicio de negocio para realizar
 * consultas de estatus de operación
 * 
 * @author Emigdio Hernández
 * 
 */
public interface ConsultaEstatusOperacionesMatchService {
	/**
	 * Llave del mapa de resultados extras para el total de títulos del
	 * traspasante
	 */
	String TOTAL_TITULOS_TRASPASANTE = "titulosTraspasante";
	/**
	 * Llave del mapa de resultados extras para el total de títulos del receptor
	 */
	String TOTAL_TITULOS_RECEPTOR = "titulosReceptor";
	/**
	 * Llave del mapa de resultados extras para el total del monto traspasante
	 */
	String TOTAL_MONTO_TRASPASANTE = "montoTraspasante";
	/**
	 * Llave del mapa de resultados extras para el total del monto receptor
	 */
	String TOTAL_MONTO_RECEPTOR = "montoReceptor";

	/**
	 * Consulta las operaciones de valor registradas en el sistema según los
	 * criterios recibidos como parámetro. El objeto de criterios contiene 2
	 * tipos de propiedades campos primitivos como String o boolea y otros DTOs
	 * que representan criterios de búsqueda de alguna tabla asociada. Los
	 * criterios de búsqueda representados por otros DTOs consideran que para
	 * cada DTO asociado al criterio existe un ID , dicho id (el equivalente al
	 * id de la tabla) ser el dato tomado en cuenta para realizar la búsqueda.
	 * Un valor de -1 indica que el criterio no se debe de incluir en la
	 * búsqueda.
	 * 
	 * @param criterio
	 *            DTO con los criterios para realizar la consulta.
	 * @param paginacion
	 *            Estado de la paginacion
	 * @param resultadosExtra
	 *            Mapa de objetos devueltos por el servicio donde se pueden
	 *            colocar acumulados, totales, etc.
	 * @return Lista con los objetos que representan los datos de las
	 *         operaciones localizadas
	 */
	List<OperacionValorMatchDTO> consultarOperacionesValor(CriterioMatchOperacionesDTO criterio, EstadoPaginacionDTO paginacion,
			Map<Object, Object> resultadosExtra);

    /**
     * Consulta las operaciones de valor registradas en el sistema segun los criterios recibidos como parametro en las
     * vistas creadas para mejorar el rendimiento.
     * 
     * @param criterio DTO con los criterios para realizar la consulta.
     * @param paginacion Estado de la paginacion
     * @param resultadosExtra Mapa de objetos devueltos por el servicio donde se pueden colocar acumulados, totales, etc.
     * @return Lista con los objetos que representan los datos de las operaciones localizadas
     */
    List<OperacionValorMatchDTO> getOperacionesValorConVistas(CriterioMatchOperacionesExportacionDTO criterio, 
                                                              EstadoPaginacionDTO paginacion,
                                                              Map<Object, Object> resultadosExtra);

	/**
	 * Obtiene la proyección de los resultados de una consulta de operaciones de
	 * valor / match basado en los criterios enviados. Los criterios de búsqueda
	 * representados por otros DTOs consideran que para cada DTO asociado al
	 * criterio existe un ID , dicho id (el equivalente al id de la tabla) ser
	 * el dato tomado en cuenta para realizar la búsqueda. Un valor de -1 indica
	 * que el criterio no se debe de incluir en la búsqueda.
	 * 
	 * @param criterio
	 *            DTO con los criterios para realizar la consulta.
	 * @return número de registros localizados
	 */
	long obtenerProyeccionConsultaOperacionesValor(CriterioMatchOperacionesDTO criterio, Boolean debeDejarLog);

	/**
	 * Consulta un registro de operaciones de valor basado en su identificador
	 * único.
	 * 
	 * @param idInstruccion
	 *            Identificador del registro
	 * @return una lista donde el último elemento es el DTO con los datos que
	 *         representan la operación, los demás elementos en la lista son las
	 *         parcialidades relacionadas con la operación en caso de existir,
	 *         lista vacía en caso de no encontrarse
	 */
	List<OperacionValorMatchDTO> consultarInstruccionOperacionValorPorId(long idInstruccion);
	
	/**
	 * Consulta un registro de match por medio de su identificador.
	 * 
	 * @param idBitacoraMatch el identificador a consultar.
	 * @return el registro de match que corresponde con el identificador.
	 */
	public List<OperacionValorMatchDTO> consultarInstruccionMatchoPorId(long idBitacoraMatch);
	
	
	/**
	 * Obtiene el saldo actual de una posicion nombrada
	 * 
	 * @param agente Agente (id, folio y cuenta)
	 * @param emision Emision (tv, emisora, serie, cupon)
	 * @param idBoveda (boveda de valores)
	 * @return el saldo actual
	 */
	public BigInteger getSaldoActual( AgenteVO agente, EmisionVO emision, BigInteger idBoveda );
	
	/**
	 * Regresa la lista de posiciones con posicion disponible de una agente y emision
	 * 
	 * @param agente Agente del cual obtener sus posiciones
	 * @param emision Emision a buscar las posiciones
	 * @return Listado de Posiciones
	 */
	public List<PosicionDTO> getListaPosiciones( AgenteVO agente, EmisionVO emision);
	
	public List<OperacionValorMatchDTO> consultarOperacionesMiscelaneaFiscal(CriterioMatchOperacionesDTO criterio, 
			EstadoPaginacionDTO paginacion, Boolean debeDejarLog);

	/**
     * Consulta las operaciones de valor registradas en el sistema segun los criterios recibidos como parametro. 
     *  
     * @param criterio DTO con los criterios para realizar la consulta.
     * @param resultadosExtra Mapa de objetos devueltos por el servicio donde se pueden colocar acumulados, totales, etc.
     * @return Lista con los objetos que representan los datos de las operaciones localizadas.
     */
    List<ConsultaOperacionesMatch> getOperacionesValorExportacion(CriterioMatchOperacionesExportacionDTO criterio, Map<Object, Object> resultadosExtra);

}
