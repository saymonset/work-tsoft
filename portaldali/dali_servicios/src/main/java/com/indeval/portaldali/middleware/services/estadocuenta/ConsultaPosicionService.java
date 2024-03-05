/**
 * 2H Software - Bursatec
 * Sistema de Consulta de Estados de Cuenta
 *
 */
package com.indeval.portaldali.middleware.services.estadocuenta;

import java.util.List;

import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionExtended;
import com.indeval.portaldali.modelo.to.estadocuenta.TotalesPosicionTO;
import com.indeval.portaldali.persistencia.posicion.Posicion;

/**
 * Define la funcionalidad del servicio de negocio encargado de realizar las
 * operaciones de consulta a las tablas de posicion controlada y posicion
 * nombrada.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public interface ConsultaPosicionService {

//	/**
//	 * Esta función es encargada de invocar la búsqueda correspondiente de
//	 * posiciones nombradas o controladas dependiendo de los criterios de
//	 * búsqueda recibidos como parámetros. Consulta la base de datos para
//	 * obtener los valores de las posiciones nombradas dependiendo de los
//	 * criterios enviados a la consulta. Obtiene el valor y los datos de las
//	 * posiciones nombradas requeridas por los criterios al corte de cierto día,
//	 * es decir, se toma el valor del último estado de la posición al día
//	 * consultado.
//	 * 
//	 * @param criterios
//	 *            Criterios de búsqueda
//	 * @param paginacion
//	 *            Estado actual de la paginacion
//	 * @param orden
//	 *            Criterio de ordenamiento
//	 * @return Lista de DTO con los valores obtenidos en la consulta.
//	 */
//	public List<PosicionDTO> consultarPosiciones(PosicionDTO criterios, EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden);

	/**
	 * Metodo que realiza la consulta de la posicion nombrada.
	 * @param criterios Objeto con los criterios para la consulta.
	 * @param paginacion Objeto con los parametros de la paginacion.
	 * @param orden Objeto con el ordenamiento a aplicar en la consulta.
	 * @return Lista de objetos que representan la posicion.
	 */
	List<Posicion> consultarPosicion(PosicionDTO criterios, EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden);
	
	/**
	 * Obtiene el tamaño de resultados que arroja la consulta de posiciones
	 * nombradas o controladas basado en el criterio que se recibe como
	 * parámetro.
	 * 
	 * @param criterio
	 *            Criterio de consulta en el cual se basa la proyección de la
	 *            consulta
	 * @return El número de registros encontrados en la consulta
	 */
	int obtenerProyeccionDeConsultaDePosiciones(PosicionDTO criterio, Boolean debeDejarLog);

	/**
	 * Obtiene un conjunto de resultados con los distintos ids de divisa que
	 * produce la consulta de saldos de efectivo, ya sea nombrados o
	 * controlados.
	 * 
	 * @param criterio
	 *            Criterio para emitir la consulta de posicion
	 * @return Lista con valores de los ids de las emisiones
	 */
	List<Long> obtenerIdentificadoresValidosParaEmision(PosicionDTO criterio);

	/**
	 * Obtiene un conjunto de resultados con los distintos ids de boveda que
	 * produce la consulta de posiciones, ya sea nombrados o controlados.
	 * 
	 * @param criterio
	 *            Criterio para emitir la consulta de posicion
	 * @return Lista con valores de los ids de las bovedas
	 */
	List<Long> obtenerIdentificadoresValidosParaBoveda(PosicionDTO criterio);

	/**
	 * Obtiene un conjunto de resultados con los distintos ids de cuentas que
	 * produce la consulta de posiciones, ya sea nombrados o controlados.
	 * 
	 * @param criterio
	 *            Criterio para emitir la consulta de posicion
	 * @return Lista con valores de los ids de las cuentas
	 */
	List<Long> obtenerIdentificadoresValidosParaCuenta(PosicionDTO criterio);

	/**
	 * Obtiene los totales de la consulta de posicion.
	 * @param criterio Criterio para obtener los totales.
	 * @return TO con los totales.
	 */
	TotalesPosicionTO obtenerTotalesConsultaPosicion(PosicionDTO criterio);

	/**
	 * Esta función es encargada de invocar la búsqueda correspondiente de
	 * posiciones nombradas o controladas dependiendo de los criterios de
	 * búsqueda recibidos como parámetros. Consulta la base de datos para
	 * obtener los valores de las posiciones nombradas dependiendo de los
	 * criterios enviados a la consulta y de los identificadores de mercados que
	 * deseen ser filtrados. Obtiene el valor y los datos de las posiciones
	 * nombradas requeridas por los criterios al corte de cierto día, es decir,
	 * se toma el valor del último estado de la posición al día consultado.
	 * 
	 * @param criterios
	 *            Criterios de búsqueda
	 * @param paginacion
	 *            Estado actual de la paginacion
	 * @param orden
	 *            Criterio de ordenamiento
	 * @param identificadoresMercado
	 *            Tipos de mercado a filtrar
	 * @return Lista de DTO con los valores obtenidos en la consulta.
	 */
	public List<PosicionDTO> consultarPosicionesPorMercado(PosicionDTO criterios, EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden,
			Long[] identificadoresMercado);
	
	/**
	 * Consulta el catálogo de emisiones y para aquellas que cuenten con posición para un cliente específico, presentará los datos ligados.
	 * 
	 * @param criterios el DTO con los criterios para realizar la consulta.
	 * @param paginacion el DTO con la información de la paginación.
	 * @param identificadoresMercado un arreglo con los identificadores de los mercados a consultar.
	 * @return una lista con objetos {@link PosicionDTO} los cuales representan los resultados de la consulta.
	 */
	List<PosicionDTO> consultarPosicionesYEmisionesVigentes(PosicionDTO criterios, EstadoPaginacionExtended paginacion, Long[] identificadoresMercado);
	
	/**
	 * Obtiene la proyección de la consulta de posiciones y emisiones vigentes.
	 * 
	 * @param criterio
	 *            el DTO con los criterios para realizar la consulta.
	 * @param identificadoresMercado
	 *            los identificadores del mercado.
	 * @return el número de registros que regresará la consulta con los
	 *         criterios especificados.
	 */
	int obtenerProyeccionDeConsultaPosicionesYEmisionesVigentes(PosicionDTO criterio, Long[] identificadoresMercado);

	/**
	 * Obtiene la posicion por id.
	 * @param idPosicion Id del registro.
	 * @return DTO con la posicion.
	 */
	PosicionDTO buscarPosicionPorId(Posicion posicion);
}
