/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.persistence.dao;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.Emisiones;

/**
 * Interfaz de DAO para las operaciones relacionadas con consulta de emisiones
 */
public interface EmisionesConsultasDao {

    /**
     * Obtiene el detalle de una emision liberada en base a los parametros recibidos.
     * @param tv TV de la emision
     * @param emisora Emisora de la emision
     * @param serie Serie de la emision
     * @param isin ISIN de la emisio
     * @return
     */
    Emisiones obtenerEmisionLiberada(String tv, String emisora, String serie, String isin);

    /**
     * Obtiene el detalle de una emision liberada en base a los parametros recibidos.
     * @param tv TV de la emision
     * @param emisora Emisora de la emision
     * @param serie Serie de la emision
     * @return
     */
    Emisiones obtenerEmisionLiberada(String tv, String emisora, String serie);

    /**
     * Obtiene el saldo de la emision.
     * @param idEmision
     * @param idCuenta
     * @param idBoveda
     * @param idCuponVigente
     * @return El saldo de la cuenta.
     * @throws BusinessException
     */
    BigDecimal getSaldo(Integer idEmision, Integer idCuenta, Integer idBoveda, Integer idCuponVigente) throws BusinessException;

    /**
     * Obtiene el saldo total de todas las cuentas de la emision.
     * @param idEmision
     * @param idBoveda
     * @param idCuponVigente
     * @return El saldo total.
     * @throws BusinessException
     */
    BigDecimal getSaldoTotal(Integer idEmision, Integer idBoveda, Integer idCuponVigente) throws BusinessException;

    /***
     * Obtiene la posicion NO disponible de las cuentas de una emision.
     * @param idEmision
     * @param idBoveda
     * @param idCuponVigente
     * @return El saldo de la posicion no disponible.
     * @throws BusinessException
     */
    BigDecimal getSaldoPosicionNoDisponible(Integer idEmision, Integer idBoveda, Integer idCuponVigente) throws BusinessException;

    /**
     * Obtiene una emision por su ISIN.
     * @param isin
     * @return
     */
    Emisiones obtenerEmisionByIsin(String isin);

    /**
     * Obtiene una emision por su ID.
     * @param id
     * @return
     */
    Emisiones getById(Long id);

    /**
     * Actualiza una emision.
     * @param emision
     */
    void update(Emisiones emision);

}
