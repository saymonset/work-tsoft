package com.indeval.portalinternacional.persistence.dao;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.EstadoPaginacionDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosInDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.DivisaInt;

import java.math.BigInteger;
import java.util.List;

/**
 * Interface que expone los metodos para las operaciones realizadas sobre el
 * catalogo de divisas de la base de datos.
 *
 * @author Enrique Guzman
 */
public interface DivisaDaliDao {

    /**
     * Obtiene las divisas existentes en la base de datos
     *
     * @param estadoPaginacion DTO con los datos de la paginación actual. <code>null</code>
     *                         si no se requiere de la paginación.
     * @return Una lista de objetos que representan las divisas existentes
     */
    List<DivisaDTO> buscarDivisas(EstadoPaginacionDTO estadoPaginacion);

    /**
     * Obtiene la información de una divisa basandose en su identificador único
     *
     * @param idDivisa Identificador de la divisa
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
     * Busca las diferentes divisas asociadas filtradas por un conjunto de ids de divisa
     *
     * @param idsDivisa        Indica los ids válidos de las divisas a buscar
     * @param estadoPaginacion Estado de la paginación a utilizar
     * @return Listado de divisas localizadas
     */
    List<DivisaDTO> buscarDivisas(List<Long> idsDivisa, EstadoPaginacionDTO estadoPaginacion);

    /**
     * Obtiene el total de registros que la consulta de divisas retorna filtradas por una lista
     * de divisas validas
     *
     * @param idsDivisa Lista con los id de divisa a filtrar
     * @return número de registros obtenidos en la consulta
     */
    int obtenerProyeccionDeDivisas(List<Long> idsDivisa);

    /**
     * Obtiene las divisas existentes en la base de datos
     *
     * @param estadoPaginacion  DTO con los datos de la paginación actual. <code>null</code>
     *                          si no se requiere de la paginación.
     * @param idTipoInstruccion id del TipoInstruccion a filtrar.
     * @return Una lista de objetos que representan las divisas existentes
     */
    List<DivisaDTO> buscarDivisasPorTipoInstruccion(EstadoPaginacionDTO estadoPaginacion, BigInteger idTipoInstruccion);

    /**
     * Obtiene las divisas existentes en la base de datos
     *
     * @return Una lista de objetos que representan las divisas existentes
     */
    List<DivisaInt> consultarDivisas();

    /**
     * Obtiene el objeto Divisa a travs de su clave alfabetica
     *
     * @param claveDivisa Cadena con la clave de la Divisa
     * @return La Divisa solicitada, null si no existe.
     */
    DivisaDTO obtenerDivisaPorClaveAlfavetica(String claveDivisa);

	Divisa findDivisaByClaveAlfabetica(String claveDivisa);

	/**
	 * Obtiene List<DivisaDTO>  de Divisas a travs de su boveda
	 * @param idBoveda para filtrar la divisas
	 * @return	List<DivisaDTO>  Que son las Divisa solicitadas, null si no existe.
	 */
	List<DivisaDTO> findDivisaByBovedad(final Long idBoveda);


}