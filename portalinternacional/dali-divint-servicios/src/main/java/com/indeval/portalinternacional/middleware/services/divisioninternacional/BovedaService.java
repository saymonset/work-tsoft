/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigInteger;
import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.dto.BovedaDto;

import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.EstadoPaginacionDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Bovedas;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;

/**
 * Servicio de negocio que expone las consultas de bovedas y bovedas por instrumeto
 */
public interface BovedaService {

	/**
	 * Obtiene el detalle de una boveda por su id
	 * @param idBoveda Id de la boveda
	 * @return Dto con el detalle de la boveda
	 */
	BovedaDto getBovedaById(Integer idBoveda);

	/**
	 * Obtiene el detalle de una boveda por su id
	 * @param idBoveda Id de la boveda
	 * @return Dto con el detalle de la boveda
	 */
	BovedaDto obtenerBoveda(Integer idBoveda);

	/**
	 * Obtiene el objeto boveda por su nombre corto
	 * @param nombreCorto El nombre corto de la boveda
	 * @return El objeto boveda
	 */
	Bovedas obtenerBovedaByNombreCorto(String nombreCorto);

	/**
	 * Obtiene todas las bovedas de la tabla.
	 * @return Un listado de bovedas.
	 * @throws BusinessException
	 */
	List<Bovedas> findAll() throws BusinessException;

	/**
	 * Obtiene todas las bovedas de valores de la tabla.
	 * @return Un listado de bovedas.
	 * @throws BusinessException
	 */
    List<Bovedas> findAllBovedasValores() throws BusinessException;

    /**
     * Obtiene una boveda en base al parametro idCuentaBoveda
     * @param idCuentaBoveda
     * @return
     * @throws BusinessException
     */
    Bovedas getBovedaByIdCuentaBoveda(Integer idCuentaBoveda) throws BusinessException;

    /**
     * Obtiene la descripcion bic_prod de un CatBic a partir de una boveda.
     * @param boveda La boveda.
     * @return
     * @throws BusinessException
     */
    String obtenerCatBic(Bovedas boveda) throws BusinessException;

	// Cambio Multidivisas
	/**
	 * Obtiene los Ids de las Bovedas por Divisa
	 * @param divisaDTO La Divisa a filtrar
	 * @return Ids de Bovedas por Divisa
	 */
	List<BigInteger> obtenerBovedasPorDivisa(DivisaDTO divisaDTO);

	/**
	 * Busca las diferentes bovedas asociadas filtradas por un conjunto de ids de boveda
	 * @param boveda DTO con los datos del tipo de bóveda a buscar.
	 * @param idsBoveda Indica los ids válidos de las bóvedas a buscar
	 * @param estadoPaginacion Estado de la paginación a utilizar
	 * @return Listado de bóvedas localizadas
	 */
	public List<BovedaDto> buscarBovedasPorTipoCustodia(BovedaDto boveda, List<Long> idsBoveda, EstadoPaginacionDTO estadoPaginacion);

	/**
	 * Obtiene el id de catbic buscando por idBovedaEfectivo y idInstitucion.
	 *
	 * @param idBovedaEfectivo Id de la boveda efectivo
	 *
	 * @param idInstitucion Id de la institucion
	 *
	 * @return Regresa el id del catbic.
	 * */
	Long findCatBicEnBaseABovedaEfectivoParticipante(Long idBovedaEfectivo, Long idInstitucion);

    /**
     * Multidivisas: Obtiene las bovedas para Movimiento de Efectivo Internacional
     *
     * @return List
     * */
    List<Bovedas> findAllBovedasEfectivo();

    // Fin Cambio Multidivisas
}
