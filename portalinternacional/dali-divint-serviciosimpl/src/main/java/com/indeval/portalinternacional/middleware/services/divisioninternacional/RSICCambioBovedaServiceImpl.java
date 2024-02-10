/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */

package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.persistence.dao.RSICCambioBovedaDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.RSICCambioBoveda;

/**
 * Implementaci&oacute;n de la interfaz de servicio RSICCambioBovedaService.
 */
public class RSICCambioBovedaServiceImpl implements RSICCambioBovedaService {

    /** Inyecci&oacute;n del servicio <code>RSICCambioBovedaDao</code>. **/
    private RSICCambioBovedaDao rSicCambioBovedaDao;

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.RSICCambioBovedaService#insertar(RSICCambioBoveda)
     */
    public void insertar(RSICCambioBoveda obj) {
        this.rSicCambioBovedaDao.save(obj);
    }

    public void setrSicCambioBovedaDao(RSICCambioBovedaDao rSicCambioBovedaDao) {
        this.rSicCambioBovedaDao = rSicCambioBovedaDao;
    }

}
