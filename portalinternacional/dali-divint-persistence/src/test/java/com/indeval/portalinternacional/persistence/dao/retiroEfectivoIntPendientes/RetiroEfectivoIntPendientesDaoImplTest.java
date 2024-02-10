// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.retiroEfectivoIntPendientes;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.RetiroEfectivoIntPendientes;
import com.indeval.portalinternacional.persistence.dao.RetiroEfectivoIntPendientesDao;

import static com.indeval.portalinternacional.persistence.dao.retiroEfectivoIntPendientes.Util.getRetiroPendiente;


public class RetiroEfectivoIntPendientesDaoImplTest extends BaseDaoTestCase {
    RetiroEfectivoIntPendientesDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (RetiroEfectivoIntPendientesDao) getBean("retiroEfectivoIntPendientesDao");
    }

    public void testSaveRetiroEfectivoIntPendientes() {
        System.out.println("testSaveRetiroEfectivoIntPendientes()");

        RetiroEfectivoIntPendientes retiroPendiente = getRetiroPendiente();

        assertNotNull("RetiroEfectivoIntPendientesDao Nulo", dao);
        dao.saveRetiroEfectivoIntPendientes(retiroPendiente);

        assertNotNull(retiroPendiente.getIdMovimientoRetiroEfectivoInt());
    }
}