// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.movimientoEfectivoInternacional;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.MovimientoEfectivoInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaTransitoria;
import com.indeval.portalinternacional.middleware.servicios.vo.MovimientoEfectivoInternacionalVO;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.DESC_DEPOSITO;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class MovimientoEfectivoInternacionalServiceImplTestMockito extends TestCase {

    @Mock
    MovimientoEfectivoInternacionalService serviceMock;

    public void testGetFolioControl() {
        //TODO: Implements
        fail("Not yet implemented");
    }

    public void testSaveMovimientoEfectivoInternacional() {
        //TODO: Implements
        fail("Not yet implemented");
    }

    public void testEsUsuarioPermitidoAutorizar() {
        //TODO: Implements
        fail("Not yet implemented");
    }

    @Test
    public void testEsUsuarioPermitidoCancelar() {
        String usuario = "Indeval";
        Long folioControl = 1234567890L;

        Mockito.when(serviceMock.esUsuarioPermitidoCancelar(eq(usuario), eq(folioControl))).thenReturn(true);
        assertTrue(serviceMock.esUsuarioPermitidoCancelar(usuario, folioControl));
    }

    @Test
    public void testValidaCuentaTransitoria() {
        MovimientoEfectivoInternacionalVO mov = Util.getMovimientoDeposito();
        Mockito.when(serviceMock.validaCuentaTransitoria(mov)).thenReturn(Util.obtenerCuentaTransitoriaEsperada(mov));

        CuentaTransitoria cuentaTransitoria = serviceMock.validaCuentaTransitoria(mov);
        assertNotNull(cuentaTransitoria);
        assertEquals(Long.toString(mov.getDivisa().getId()), cuentaTransitoria.getIdDivisa().getIdDivisa().toString());

    }


}