/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;

public interface ConsultaOperacionesSICService {

    /**
     * Obtiene el listado de depositantes liquidadores, especificamente el campo DEP_LIQ.
     * @return Un listado de objetos con la descripcion.
     * @throws BusinessException
     */
	List<String> getDepositantesLiquidadores() throws BusinessException;

}
