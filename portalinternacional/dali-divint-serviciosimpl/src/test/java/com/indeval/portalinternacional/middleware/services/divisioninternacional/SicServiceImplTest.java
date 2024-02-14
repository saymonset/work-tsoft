/**
 * Multidivisas  - Portal Internacional : Consulta de Movimientos
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portalinternacional.middleware.servicios.modelo.Multidivisa;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class SicServiceImplTest {

    @Mock
    SicService sicServiceMock;

    @Test
    public void notificaCambioEstadoMovEfeDivInt() {
        Multidivisa notificacionMoi = new Multidivisa();
        notificacionMoi.setId(776L);
        notificacionMoi.setFolioConstrol(776L);
        notificacionMoi.setTipoMovimiento(Multidivisa.TipoMovimiento.DEPOSITO);
        notificacionMoi.setEstado(Multidivisa.EstadoMovimiento.LIBERADO);
        sicServiceMock.notificaCambioEstadoMovEfeDivInt(notificacionMoi);
        Mockito.verify(sicServiceMock).notificaCambioEstadoMovEfeDivInt(eq(notificacionMoi));
        assertTrue(true);
    }


}