/**
 * 2H Software - Bursatec
 * Sistema de Consulta de Estados de Cuenta
 *
 * PosicionDAO.java
 * Dec 6, 2007
 */
package com.indeval.portaldali.persistence.dao.estadocuenta;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;
import com.indeval.portaldali.middleware.dto.CuponDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionExtended;
import com.indeval.portaldali.modelo.to.estadocuenta.TotalesPosicionTO;
import com.indeval.portaldali.persistencia.posicion.Posicion;

/**
 * Interface que expone los métodos para las operaciones realizadas sobre las
 * tablas de posiciones controladas y nombradas de la base de datos.
 * 
 * @author Sandra Flores Arrieta
 */
public interface PosicionDAO {

	/**
	 * Metodo que realiza la consulta de la posicion nombrada.
	 * @param criterio Objeto con los criterios para la consulta.
	 * @param paginacion Objeto con los parametros de la paginacion.
	 * @param orden Objeto con el ordenamiento a aplicar en la consulta.
	 * @param esHistorica Indica si es consulta historica.
	 * @return Lista de objetos que representan la posicion.
	 */
	List<Posicion> buscarPosicionNombrada(PosicionDTO criterio, EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden, boolean esHistorica);

	/**
	 * Metodo que obtiene el total de resultados de la consulta de posicion nombrada.
	 * @param criterio Criterio para realizar la proyeccion.
	 * @param esHistorica Indica si es consulta historica.
	 * @return Total de registros de la consulta 
	 */
	Integer obtenerProyeccionConsultaPosicionNombrada(PosicionDTO criterio, boolean esHistorica);
	
	/**
	 * Metodo que realiza la consulta de la posicion controlada.
	 * @param criterio Objeto con los criterios para la consulta.
	 * @param paginacion Objeto con los parametros de la paginacion.
	 * @param orden Objeto con el ordenamiento a aplicar en la consulta.
	 * @param esHistorica Indica si es consulta historica.
	 * @return Lista de objetos que representan la posicion.
	 */
	List<Posicion> buscarPosicionControlada(PosicionDTO criterio, EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden, boolean esHistorica);
	
	/**
	 * Metodo que obtiene el total de resultados de la consulta de posicion controlada.
	 * @param criterio Criterio para realizar la proyeccion.
	 * @param esHistorica Indica si es consulta historica.
	 * @return Total de registros de la consulta 
	 */
	Integer obtenerProyeccionConsultaPosicionControlada(PosicionDTO criterio, boolean esHistorica);

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
	 * Obtiene un conjunto de resultados con los distintos ids de divisa que
	 * produce la consulta de saldos de efectivo, ya sea nombrados o controlados
	 * en el histórico.
	 * 
	 * @param criterio
	 *            Criterio para emitir la consulta de posicion
	 * @return Lista con valores de los ids de las emisiones
	 */
	List<Long> obtenerIdentificadoresValidosParaEmisionHistoricas(PosicionDTO criterio);

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
	 * Obtiene un conjunto de resultados con los distintos ids de boveda que
	 * produce la consulta de posiciones, ya sea nombrados o controlados en el
	 * histórico.
	 * 
	 * @param criterio
	 *            Criterio para emitir la consulta de posicion
	 * @return Lista con valores de los ids de las bovedas
	 */
	List<Long> obtenerIdentificadoresValidosParaBovedaHistoricas(PosicionDTO criterio);

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
	 * Obtiene un conjunto de resultados con los distintos ids de cuentas que
	 * produce la consulta de posiciones, ya sea nombrados o controlados en el
	 * histórico.
	 * 
	 * @param criterio
	 *            Criterio para emitir la consulta de posicion
	 * @return Lista con valores de los ids de las cuentas
	 */
	List<Long> obtenerIdentificadoresValidosParaCuentaHistoricas(PosicionDTO criterio);

	/**
	 * Obtiene un objeto del tipo {@link CuponDTO} a partir de un identificador
	 * de emisión y una fecha.
	 * 
	 * @param idEmision
	 *            Identifcador de la emisión para la cul se requiere obtener el
	 *            cupón.
	 * @param fecha
	 *            Fecha para la cul se desea obtener el cupón
	 * @return DTO con los datos del cupón encontrado. Nulo en caso de no
	 *         localizar el cupón.
	 */
	CuponDTO obtenerCuponDTO(long idEmision, Date fecha);

	/**
	 * Consulta los datos de una posición nombrada basado en su identificador
	 * único.
	 * 
	 * @param idPosicion
	 *            Identificador de la posicion buscara
	 * @param tipoCuenta
	 *            Tipo de cuenta asociado a la posición
	 * @return DTO con los datos de la posición
	 */
	PosicionDTO buscarPosicionNombradaPorId(long idPosicion);

	/**
	 * Consulta los datos de una posición controlada basado en su identificador
	 * único.
	 * 
	 * @param idPosicion
	 *            Identificador de la posicion buscara
	 * @param tipoCuenta
	 *            Tipo de cuenta asociado a la posición
	 * @return DTO con los datos de la posición
	 */
	PosicionDTO buscarPosicionControladaPorId(long idPosicion);
	
	/**
	 * Metodo que obtiene los totales de la consulta de posicion nombrada.
	 * @param criterio Critaerio de busqueda.
	 * @param esHistorica Indica si es historica.
	 * @return TO con los totales
	 */
	TotalesPosicionTO obtenerTotalesPosicionNombrada(PosicionDTO criterio, boolean esHistorica);
	
	/**
	 * Metodo que obtiene los totales de la consulta de posicion controlada.
	 * @param criterio Critaerio de busqueda.
	 * @param esHistorica Indica si es historica.
	 * @return TO con los totales
	 */
	TotalesPosicionTO obtenerTotalesPosicionControlada(PosicionDTO criterio, boolean esHistorica);

	/**
	 * Esta función es encargada de invocar la búsqueda correspondiente de
	 * posiciones nombradas dependiendo de los criterios de búsqueda recibidos
	 * como parámetros. Consulta la base de datos para obtener los valores de
	 * las posiciones nombradas dependiendo de los criterios enviados a la
	 * consulta y de los identificadores de mercados que deseen ser filtrados.
	 * Obtiene el valor y los datos de las posiciones nombradas requeridas por
	 * los criterios al corte de cierto día, es decir, se toma el valor del
	 * último estado de la posición al día consultado.
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
	public List<PosicionDTO> consultarPosicionesNombradasPorMercado(PosicionDTO criterios, EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden,
			Long[] identificadoresMercado);

	/**
	 * 
	 * 
	 * @param criterio
	 * @param paginacion
	 * @param identificadoresMercado
	 * @return
	 */
	List<Object[]> buscarEmisionesYPosiciones(PosicionDTO criterio, final EstadoPaginacionDTO paginacion, Long[] identificadoresMercado);

	/**
	 * 
	 * @param criterio
	 * @param identificadoresMercado
	 * @return
	 */
	BigDecimal contarEmisionesYPosiciones(PosicionDTO criterio, Long[] identificadoresMercado);

	/**
	 * Busca un conjunto de emisiones las cuales pertenezcan a un conjunto dado
	 * de emisiones y a un participante.
	 * 
	 * @param criterios
	 *            los criterios para realizar la consulta.
	 * @param paginacion
	 *            la información de la paginación.
	 * @param identificadoresMercado
	 *            los identificadores para llevar a cabo la consulta por mercado
	 * @param idsEmisiones
	 *            los identificadores de las emisiones a considerar.
	 * @return una lista con las posiciones que
	 */
	List<PosicionDTO> buscarPosicionesDeEmisiones(PosicionDTO criterios, EstadoPaginacionExtended paginacion, Long[] identificadoresMercado,
			List<Long> idsEmisiones);
	
	/**
	 * Busca una posición nombrada en el histórico de posiciones.
	 * 
	 * @param idPosicion el identificador de la posición nombrada en el histórico a consultar.
	 * @return un DTO que representa la posición del histórico consultada.
	 */
	PosicionDTO buscarPosicionNombradaHistoricoPorId(Date fecha,long idPosicion);
	
	/**
	 * Busca la posición controlada en las tablas historicas.
	 * @param fechaPosicion Fecha en que se busca la posición.
	 * @param idPosicion Id de la posición.
	 * @return DTO con la posición.
	 */
	PosicionDTO buscarPosicionControladaHistoricoPorId(Date fechaPosicion, long idPosicion);
}
