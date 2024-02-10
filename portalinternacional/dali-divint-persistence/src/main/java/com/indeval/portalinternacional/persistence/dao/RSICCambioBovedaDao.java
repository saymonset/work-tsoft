/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.persistence.dao;

import com.indeval.portalinternacional.middleware.servicios.modelo.RSICCambioBoveda;

/**
 * Interfaz de DAO para las operaciones relacionadas con la entidad RSICCambioBoveda.
 */
public interface RSICCambioBovedaDao {

    /**
     * Guarda en BD un registro de la entidad RSICCambioBoveda.
     * @param obj
     */
    void save(RSICCambioBoveda obj);
}
