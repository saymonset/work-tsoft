/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */

package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.middleware.servicios.dto.InstitucionWebDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.InstitucionesVo;

/**
 * Interfaz de servicio para las operaciones relacionadas con instituciones
 */
public interface InstitucionesService {

    /**
     * Obtiene una institucion con tipo y folio como parametros.
     * @param idTipoInstitucion
     * @param folio
     * @return InstitucionesVo
     */
    InstitucionesVo getInstitucionByTipoYFolio(Integer idTipoInstitucion, String folio);
    // Cambio Multidivisas
    InstitucionWebDTO buscarInstitucionPorClaveYFolio(String idFolio);
    // Fin Cambio Multidivisas
}
