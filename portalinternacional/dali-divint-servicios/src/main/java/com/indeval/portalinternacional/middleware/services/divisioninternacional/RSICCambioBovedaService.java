/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */

package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.middleware.servicios.modelo.Emisiones;
import com.indeval.portalinternacional.middleware.servicios.modelo.RSICCambioBoveda;

/**
 * Interfaz de servicio para las operaciones relacionadas con la entidad RSICCambioBoveda.
 */
public interface RSICCambioBovedaService {

    /**
     * Obtiene el detalle de una emision liberada en base a los parametros recibidos.
     * @param tv TV de la emision
     * @param emisora Emisora de la emision
     * @param serie Serie de la emision
     * @param isin ISIN de la emisio
     * @return
     */
    void insertar(RSICCambioBoveda obj);

}
