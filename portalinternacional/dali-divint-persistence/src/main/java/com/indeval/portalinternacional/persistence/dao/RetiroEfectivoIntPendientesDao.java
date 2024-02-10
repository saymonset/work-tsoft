// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.RetiroEfectivoIntPendientes;

public interface RetiroEfectivoIntPendientesDao extends BaseDao {

    public void saveRetiroEfectivoIntPendientes(RetiroEfectivoIntPendientes retiroEfectivoIntPendientes);

}
