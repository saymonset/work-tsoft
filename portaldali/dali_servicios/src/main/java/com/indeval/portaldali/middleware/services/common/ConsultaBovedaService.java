/**
 * 2H Software - Bursatec
 * Sistema de Consulta de Estados de Cuenta
 *
 */
package com.indeval.portaldali.middleware.services.common;


import java.math.BigInteger;
import java.util.List;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;

/**
 * Define la funcionalidad del servicio de negocio encargado de realizar las operaciones de consulta
 * al catálogo de bóvedas.
 * @author Emigdio Hernández
 * @version 1.0
 */
public interface ConsultaBovedaService {
	/**
	 * Consulta las bóvedas disponibles en la base de datos que cumplan con el criterio de tipo
	 * de bóveda definido en el DTO de entrada, estos valores pueden ser
	 * V para bóveda de valor
	 * E para bóveda de efectivo.
	 * @param estadoPaginacion DTO con la información del estado de la paginación. <code>null</code> si no es necesaria la paginación.
	 * @param criterio DTO con el criterio de consulta
	 * @return Lista con las bóvedas encontradas
	 */
	public List<BovedaDTO>consultarBovedasPorTipoDeCustodia(BovedaDTO criterio, EstadoPaginacionDTO estadoPaginacion);

	/**
	 * Consulta una bóveda basado en su identificador único.
	 * @param idBoveda Identificaddor de la bóveda
	 * @return DTO con la información recuperada de la bóveda
	 */
	public BovedaDTO consultarBovedaPorId(long idBoveda);

	/**
	 * Obtiene una proyección del total de resultados obtenidos de la consulta de búsqueda de
	 * elementos en el catálogo de bóvedas filtrando por el tipo de bóveda que se define como Efectivo
	 * o de Valores.
	 *
	 * @param boveda DTO con los datos del tipo de bóveda a buscar.
	 * @return el número de resultados que se obtendrán de la consulta.
	 */
	public int obtenerProyeccionDeConsultaBovedasPorTipoCustodia(BovedaDTO criterio);


	/**
	 * Busca las diferentes bovedas asociadas filtradas por un conjunto de ids de boveda
	 * @param boveda DTO con los datos del tipo de bóveda a buscar.
	 * @param idsBoveda Indica los ids válidos de las bóvedas a buscar
	 * @param estadoPaginacion Estado de la paginación a utilizar
	 * @return Listado de bóvedas localizadas
	 */
	public List<BovedaDTO> buscarBovedasPorTipoCustodia(BovedaDTO boveda, List<Long> idsBoveda,EstadoPaginacionDTO estadoPaginacion);

	/**
	 * Obtiene el total de registros que la consulta de bóvedas retorna filtradas por una lista
	 * de bóvedas validas
	 * @param boveda DTO con los datos del tipo de bóveda a buscar.
	 * @param idsBoveda Lista con los id de divisa a filtrar
	 * @return número de registros obtenidos en la consulta
	 */
	int obtenerProyeccionDeBusquedaDeBovedasPorTipoCustodia(BovedaDTO boveda, List<Long> idsBoveda);

	/**
	 * Obtiene los Ids de las Bovedas por Divisa
	 * @param divisa La Divisa a filtrar
	 * @return Ids de Bovedas por Divisa
	 */
	List<BigInteger> obtenerBovedasPorDivisa(final DivisaDTO divisaDTO);

	/**
	 * Obtiene los Ids de las Bovedas por TipoInstruccion
	 * @param tipoInstruccionDTO El TipoInstruccion a filtrar
	 * @return Ids de Bovedas por Divisa
	 */
	List<BigInteger> obtenerBovedasPorTipoInstruccion(TipoInstruccionDTO tipoInstruccionDTO);

}