// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.middleware.servicios.modelo.RetiroEfectivoIntPendientes;
import com.indeval.portalinternacional.persistence.dao.RetiroEfectivoIntPendientesDao;

public class RetiroEfectivoIntPendienteServiceImpl implements RetiroEfectivoIntPendientesService{

    private RetiroEfectivoIntPendientesDao retiroEfectivoIntPendientesDao;

    public RetiroEfectivoIntPendienteServiceImpl() {
    }

    @Override
    public void saveRetiroEfectivoIntPendientes(RetiroEfectivoIntPendientes retiroEfectivoIntPendientes) {
        retiroEfectivoIntPendientesDao.saveRetiroEfectivoIntPendientes(retiroEfectivoIntPendientes);
    }

    public RetiroEfectivoIntPendientesDao getRetiroEfectivoIntPendientesDao() {
        return retiroEfectivoIntPendientesDao;
    }

    public void setRetiroEfectivoIntPendientesDao(RetiroEfectivoIntPendientesDao retiroEfectivoIntPendientesDao) {
        this.retiroEfectivoIntPendientesDao = retiroEfectivoIntPendientesDao;
    }
}
