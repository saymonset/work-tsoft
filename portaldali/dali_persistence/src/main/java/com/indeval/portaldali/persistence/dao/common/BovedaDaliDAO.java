/**
 * 2H Software - Bursatec
 * Sistema de Consulta de Estados de Cuenta
 *
 * BovedaDaliDAO.java
 * Dec 6, 2007
 */
package com.indeval.portaldali.persistence.dao.common;

import java.math.BigInteger;
import java.util.List;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.persistence.model.Divisa;
import com.indeval.portaldali.persistence.model.DivisaBoveda;

/**
 * Interface que expone los métodos para las operaciones realizadas sobre el catálogo
 * de bóvedas de la base de datos.
 *
 * @author Sandra Flores Arrieta
 */
public interface BovedaDaliDAO {

	/**
	 * Obtiene una lista de objetos de tipo Boveda que contiene los datos de las bóvedas
	 * del catálogo de bóvedas filtrando por el tipo de bóveda que se define como Efectivo
	 * o de Valores.
	 *
	 * @param boveda  DTO con los datos del tipo de bóveda a buscar.
	 * @param estadoPaginacion DTO con los datos de la paginación actual. <code>null</code> si no se requiere de la paginación.
	 * @return  Lista de objetos de tipo Boveda que contiene los datos de las bóvedas
	 * del catálogo de bóvedas filtrando por el tipo de bóveda que se define como Efectivo
	 * o de Valores.
	 */
	public List<BovedaDTO> buscarBovedasPorTipoCustodia(BovedaDTO boveda, EstadoPaginacionDTO estadoPaginacion);
		/**
	 * se agrego un nuevo metodo para la busqueda de bovedaDTO por el nombre corto
	 *
	 * @param boveda DTO con nombre corto
	 * @return BovedaDTO
	 */
	public BovedaDTO buscarBovedaPorTipoCustodia(BovedaDTO boveda);

	/**
	 * Realiza una busqueda sobre todas las bovedas disponibles.
	 * */
	public List<BovedaDTO> buscarBovedas(EstadoPaginacionDTO estadoPaginacion);

	/**
	 * Obtiene una proyección del total de resultados obtenidos de la consulta de búsqueda de
	 * elementos en el catálogo de bóvedas filtrando por el tipo de bóveda que se define como Efectivo
	 * o de Valores.
	 *
	 * @param boveda DTO con los datos del tipo de bóveda a buscar.
	 * @return el número de resultados que se obtendrán de la consulta.
	 */
	public int obtenerProyeccionDeBusquedaDeBovedasPorTipoCustodia(BovedaDTO boveda);

	/**
	 * Consulta una bóveda basado en su identificador único.
	 * @param idBoveda Identificaddor de la bóveda
	 * @return DTO con la información recuperada de la bóveda
	 */
	public BovedaDTO consultarBovedaPorId(long idBoveda);


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
	 * @param idsBoveda Lista con los id de bóveda a filtrar
	 * @return número de registros obtenidos en la consulta
	 */
	int obtenerProyeccionDeBusquedaDeBovedasPorTipoCustodia(BovedaDTO boveda, List<Long> idsBoveda);

	/**
	 * Obtiene los Ids de las Bovedas por Divisa
	 * @param divisa La Divisa a filtrar
	 * @return Ids de Bovedas por Divisa
	 */
	List<BigInteger> obtenerBovedasPorDivisa(DivisaDTO divisaDTO);

	/**
	 * Obtiene los Ids de las Bovedas por TipoInstruccion
	 * @param tipoInstruccionDTO El TipoInstruccion a filtrar
	 * @return Ids de Bovedas por Divisa
	 */
	List<BigInteger> obtenerBovedasPorTipoInstruccion(TipoInstruccionDTO tipoInstruccionDTO);
}
