/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */

package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Emisiones;

/**
 * Interfaz de servicio para las operaciones relacionadas con consulta de emisiones
 */
public interface EmisionesConsultasService {

    /**
     * Obtiene el detalle de una emision liberada en base a los parametros recibidos.
     * @param tv TV de la emision
     * @param emisora Emisora de la emision
     * @param serie Serie de la emision
     * @param isin ISIN de la emision
     * @return
     */
    Emisiones obtenerEmisionLiberada(String tv, String emisora, String serie, String isin);

    /**
     * Obtiene el detalle de una emision liberada en base al isin.
     * @param isin ISIN de la emision
     * @return
     */
    Emisiones obtenerEmisionLiberada(String isin);

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

    /**
     * Obtiene la posicion NO disponible de las cuentas de una emision.
     * @param idEmision El id de la emision.
     * @param idBoveda El id de la boveda.
     * @param idCuponVigente El id del cupon vigente.
     * @return El saldo de la posicion no disponible.
     * @throws BusinessException
     */
    BigDecimal getSaldoPosicionNoDisponible(Integer idEmision, Integer idBoveda, Integer idCuponVigente) throws BusinessException;

    /**
     * Consulta si la emision que se recibe como parametro para checar si se encuentra involucrada en algun cambio de boveda.
     * @param emisionVO La emision a verificar.
     * @return true si la emision en el momento se encuentra involucrada en algun cambio de boveda, false en caso contrario.
     * @throws BusinessException
     */
    boolean consultarEmisionActualEnCambioBoveda(EmisionVO emisionVO) throws BusinessException;

    /**
     * Consulta si la emision por ISIN que se recibe como parametro para checar si se encuentra involucrada en algun cambio de boveda.
     * @param emisionVO La emision a verificar.
     * @return true si la emision en el momento se encuentra involucrada en algun cambio de boveda, false en caso contrario.
     * @throws BusinessException
     */
    boolean consultarEmisionActualEnCambioBovedaByIsin(String isin) throws BusinessException;

}
