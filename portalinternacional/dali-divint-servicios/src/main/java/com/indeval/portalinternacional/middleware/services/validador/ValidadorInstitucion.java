/**
 * Copyrigth (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.vo.InstitucionesVo;

/**
 * Interfaz que expone los servicios de validación para una Institucion.
 * 
 */
public interface ValidadorInstitucion {

    /**
     * Valida la existencia de la Institucion.
     * @param idFolio El id y folio de la institucion.
     * @return La institucion obtenida.
     * @throws BusinessException
     */
    InstitucionesVo validarExistencia(String idFolio) throws BusinessException;

}
