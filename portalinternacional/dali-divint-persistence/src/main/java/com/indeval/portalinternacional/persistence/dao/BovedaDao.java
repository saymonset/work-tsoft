/*
 * Copyright (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;


import java.math.BigInteger;
import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.dto.BovedaDto;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.EstadoPaginacionDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Bovedas;

/**
 * Dao para operaciones con la tabla C_BOVEDA.
 */
public interface BovedaDao extends BaseDao {

    /**
     * Encuentra la boveda por nombre corto.
     * @param nombreCorto El nombre corto de la boveda
     * @return El dto con el detalle de la boveda
     */
    Bovedas findBovedaByNombreCorto(String nombreCorto);

    /**
     * Encuentra todas las bovedas de valores-
     * @return Un listado con las bovedas.
     */
    List<Bovedas> findAllBovedasValores();

    /**
     * Obtiene una boveda usando el parametro idCuentaBoveda.
     * @param idCuentaBoveda
     * @return
     */
    Bovedas getBovedaByIdCuentaBoveda(Integer idCuentaBoveda);

    // Cambio Multidivisas

    /**
     * Obtiene los Ids de las Bovedas por Divisa
     * @param divisaDTO La Divisa a filtrar
     * @return Ids de Bovedas por Divisa
     */
    List<BigInteger> obtenerBovedasPorDivisa(final DivisaDTO divisaDTO);

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
    List<BovedaDto> buscarBovedasPorTipoCustodia(BovedaDto boveda, EstadoPaginacionDTO estadoPaginacion);

    /**
     * Multidivisas: Encuentra todas las bovedas de movimiento efectivo
     *
     * @return Un listado con las bovedas.
     */
    List<Bovedas> findAllBovedasEfectivo();

    // Fin Cambio Multidivisas
}
