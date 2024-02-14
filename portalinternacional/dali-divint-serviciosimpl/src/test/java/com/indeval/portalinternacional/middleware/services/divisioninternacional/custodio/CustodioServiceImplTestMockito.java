// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.custodio;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.CustodioService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CustodioServiceImplTestMockito {

    @Mock
    CustodioService custodioServiceMock;

    @Test
    public void findAll() {
        Mockito.when(custodioServiceMock.findAll()).
                thenReturn(Util.getAllCustodiosEsperados());
        List<Custodio> custodios = custodioServiceMock.findAll();
        for (Custodio custodio : custodios) {
            System.out.println(Util.custodioToString(custodio));
        }
        assertNotNull(custodios);
    }
}
