/**
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.modelo.Cupon;
import com.indeval.portalinternacional.middleware.servicios.modelo.Emisiones;

/**
 * Interfaz que expone los servicios de validación para una Emision.
 * 
 */
public interface ValidadorEmision {


    /**
     * Valida la existencia de la emision liberada por tv, emisora, serie e isin.
     * @param tv El tipo valor
     * @param emisora La emisora
     * @param serie La serie
     * @param isin El isin
     * @return La emision correspondiente
     * @throws BusinessException
     */
    Emisiones validarEmisionLiberada(String tv, String emisora, String serie, String isin) throws BusinessException;

    /**
     * Valida la existencia de la emision liberada por isin solamente.
     * @param isin El isin
     * @return La emision correspondiente
     * @throws BusinessException
     */
    Emisiones validarEmisionLiberada(String isin) throws BusinessException;

    /**
     * Valida la existencia de la emision liberada por tv, emisora y serie.
     * @param tv El tipo valor
     * @param emisora La emisora
     * @param serie La serie
     * @return La emision correspondiente
     * @throws BusinessException
     */
    Emisiones validarEmisionLiberada(String tv, String emisora, String serie) throws BusinessException;

    /**
     * Valida la existencia de cupones y que tenga un cupon vigente.
     * @param emision La emision a validar el cupon.
     * @return El cupon vigente.
     * @throws BusinessException
     */
    Cupon validarCuponVigente(Emisiones emision) throws BusinessException;

}
