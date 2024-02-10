/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.modelo.REmisionBoveda;

/**
 * Interfaz de DAO para las operaciones relacionadas con la entidad REmisionBoveda.
 */
public interface REmisionBovedaDao {

    /**
     * Obtiene un listado de objetos REmisionBoveda por id de emision. 
     * @param idEmision
     * @return El listado de objetos 
     */
    List<REmisionBoveda> getByIdEmision(Long idEmision);

    /**
     * Actualiza un objeto REmisionBoveda.
     * @param obj
     */
    void update(REmisionBoveda obj);

}
