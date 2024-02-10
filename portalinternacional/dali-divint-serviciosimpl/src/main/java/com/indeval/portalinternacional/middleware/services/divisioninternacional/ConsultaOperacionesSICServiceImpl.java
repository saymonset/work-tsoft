/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.persistence.dao.SicDetalleDao;

/**
 * Servicio para obtener 
 */
public class ConsultaOperacionesSICServiceImpl implements ConsultaOperacionesSICService {

    /** Resolvedor de Mensajes */
    private MessageResolver messageResolver = null;

    /* DAO de SicDetalle */
    private SicDetalleDao sicDetalleDao;

    public List<String> getDepositantesLiquidadores() throws BusinessException {
        List<String> dl = this.sicDetalleDao.findDepositantesLiquidadores();
        if (dl == null || dl.size() == 0) {
            throw new BusinessException(this.messageResolver.getMessage("J0146"));
        }
        return dl;
    }

    public void setMessageResolver(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void setSicDetalleDao(SicDetalleDao sicDetalleDao) {
        this.sicDetalleDao = sicDetalleDao;
    }

}


