// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.EstadoPaginacionDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaCorresponsal;

import java.math.BigInteger;
import java.util.List;

/**
 * Define la funcionalidad del servicio de negocio encargado de realizar las
 * operaciones de consulta al catalogo de divisas.
 *
 * @author Enrique Guzman Cruz
 *
 */
public interface ConsultaDivisaService {
	
	/** Busca cuentas corresponsales por el id de una divisa */
	List<CuentaCorresponsal> findCuentaCorresponsalByIdDivisaAndIdInstitucion(Long idDivisa, Long idInstitucion);
	
	/** Consulta idDivisas por el id de la institucion*/
	List<DivisaDTO> findIdDivisasByIdInstitucion(final Long idInstitucion);

	/**
	 * Consulta y obtiene el catálogo de divisas de la base de datos
	 *
	 * @param estadoPaginacion
	 *            DTO con los datos de la paginación actual. <code>null</code>
	 *            si no se requiere de la paginación.
	 * @return Lista con las divisas encontradas
	 */
	List<DivisaDTO> consultarDivisas(EstadoPaginacionDTO estadoPaginacion);

	/**
	 * Consulta una divisa basado en su identificador único.
	 *
	 * @param idDivisa
	 *            Identificador de la divisa
	 * @return DTO con la información de la divisa
	 */
	DivisaDTO consultarDivisaPorId(long idDivisa);

	/**
	 * Obtiene el total de registros que la consulta de divisas retorna
	 *
	 * @return número de registros obtenidos en la consulta
	 */
	int obtenerProyeccionDeDivisas();

	/**
	 * @param idsDivisa Indica los ids válidos de las divisas a buscar
	 * @param estadoPaginacion Estado de la paginación a utilizar
	 * @return Listado de divisas localizadas
	 */
	List<DivisaDTO> buscarDivisas(List<Long> idsDivisa, EstadoPaginacionDTO estadoPaginacion);

	/**
	 * Obtiene el total de registros que la consulta de divisas retorna filtradas por una lista
	 * de divisas validas
	 * @param idsDivisa Lista con los id de divisa a filtrar
	 * @return número de registros obtenidos en la consulta
	 */
	int obtenerProyeccionDeDivisas(List<Long> idsDivisa);

	/**
	 * Consulta y obtiene el catálogo de divisas de la base de datos filtrados por TipoInstruccion
	 *
	 * @param estadoPaginacion
	 *            DTO con los datos de la paginación actual. <code>null</code>
	 *            si no se requiere de la paginación.
	 * @param idTipoInstruccion id del TipoInstruccion para filtrar.
	 * @return Lista con las divisas encontradas
	 */
	List<DivisaDTO> consultarDivisasPorTipoInstruccion(EstadoPaginacionDTO estadoPaginacion, BigInteger idTipoInstruccion);

}
