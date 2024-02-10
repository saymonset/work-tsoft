// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.MovimientosEfectivoInternacional;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoDepositoEfectivoInternacional;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoRetiroEfectivoInternacional;
import com.indeval.portalinternacional.persistence.dao.MovimientoEfectivoInternacionalDao;

import java.util.ArrayList;
import java.util.List;

import static com.indeval.portalinternacional.persistence.dao.MovimientosEfectivoInternacional.Util.*;

public class MovimientoEfectivoInternacionalDaoImplTest extends BaseDaoTestCase {

    MovimientoEfectivoInternacionalDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (MovimientoEfectivoInternacionalDao) getBean("movimientoEfectivoInternacionalDao");
    }

    public void testGetFolioControl() {
        System.out.println("testGetFolioControl()");

        assertNotNull("MovimientoEfectivoInternacionalDao Nulo", dao);
        Long folioControl =  dao.getFolioControl();

        assertNotNull(folioControl);
    }

    public void testSaveMovimientoRetiroEfectivoInternacional() {
        System.out.println("testSaveMovimientoRetiroEfectivoInternacional()");

        MovimientoRetiroEfectivoInternacional retiro = getRetiro();

        assertNotNull("MovimientoEfectivoInternacionalDao Nulo", dao);
        dao.saveMovimientoRetiroEfectivoInternacional(retiro);

        assertNotNull(retiro.getIdMovimientoEfectivoInt());
    }

    public void testSaveMovimientoDepositoEfectivoInternacional() {
        System.out.println("testSaveMovimientoDepositoEfectivoInternacional()");

        MovimientoDepositoEfectivoInternacional deposito = getDeposito();

        assertNotNull("MovimientoEfectivoInternacionalDao Nulo", dao);
        dao.saveMovimientoDepositoEfectivoInternacional(deposito);

        assertNotNull(deposito.getIdMovimientoEfectivoInt());

    }

    public void testEsUsuarioPermitidoTransaccion() {
        System.out.println("testSaveMovimientoDepositoEfectivoInternacional()");

        String usuario = "indevaldrp";
        List<Integer> idsOperacionTransaccion = new ArrayList<Integer>();
        idsOperacionTransaccion.add(1101);
        Long folioControl = 617L;

        assertNotNull("MovimientoEfectivoInternacionalDao Nulo", dao);
        boolean esPermitido = dao.esUsuarioPermitidoTransaccion(usuario, idsOperacionTransaccion, folioControl, true);

        assertTrue(esPermitido);
    }
}