// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.movimientoEfectivoInternacional;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.MovimientoEfectivoInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.vo.MovimientoEfectivoInternacionalVO;

import static com.indeval.portalinternacional.middleware.services.divisioninternacional.movimientoEfectivoInternacional.Util.*;

public class MovimientoEfectivoInternacionalServiceImplTest extends BaseDaoTestCase {
    MovimientoEfectivoInternacionalService service;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        service = (MovimientoEfectivoInternacionalService) getBean("movimientoEfectivoInternacionalService");
    }

    public void testGetFolioControl() {
        System.out.println("testGetFolioControl()");

        Long expectedFolioControl = 748L;

        assertNotNull("MovimientoEfectivoInternacionalService Nulo", service);
        Long folioControl =  service.getFolioControl();

        assertNotNull(folioControl);
        //assertEquals(expectedFolioControl, folioControl);
    }

    public void testSaveMovimientoEfectivoInternacional() {
        System.out.println("testSaveMovimientoEfectivoInternacional()");

        MovimientoEfectivoInternacionalVO retiro = getMovimientoRetiro();
        MovimientoEfectivoInternacionalVO deposito = getMovimientoDeposito();

        assertNotNull("MovimientoEfectivoInternacionalService Nulo", service);
        service.saveMovimientoEfectivoInternacional(retiro);
        service.saveMovimientoEfectivoInternacional(deposito);

        assertNotNull(retiro.getIdMovimiento());
        assertNotNull(deposito.getIdMovimiento());
    }

    public void testEsUsuarioPermitidoAutorizar() {
        System.out.println("testEsUsuarioPermitidoAutorizar()");

        String usuario = "indevaldrp";
        Long folioControl = 617L;

        assertNotNull("MovimientoEfectivoInternacionalService Nulo", service);
        boolean esPermitido = service.esUsuarioPermitidoAutorizar(usuario, folioControl);

        assertTrue(esPermitido);
    }
}