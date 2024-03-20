package com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionDivisasInt;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionDivisasIntService;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionDivisasVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ConciliacionDivisasIntServiceImplTestMockito {

    @Mock
    ConciliacionDivisasIntService conciliacionDivisasIntServiceMock;

    @Test
    public void getAllByTest() {
        Mockito.when(conciliacionDivisasIntServiceMock.getAllBy(3, -1, new Date(), new Date()))
                .thenReturn(getConciliaciones());

        List<ConciliacionDivisasVO> conciliacionDivisasVOS = conciliacionDivisasIntServiceMock.getAllBy(3, -1, new Date(), new Date());

        System.out.println(conciliacionDivisasVOS);

        assertNotNull(conciliacionDivisasVOS);
    }

    private List<ConciliacionDivisasVO> getConciliaciones() {
        List<ConciliacionDivisasVO> conciliacionDivisasIntVOs = new ArrayList<>();
        ConciliacionDivisasVO conciliacionDivisasIntVO = new ConciliacionDivisasVO();
        conciliacionDivisasIntVO.setDivisaDescripcion("USD");
        conciliacionDivisasIntVO.setBovedaDescription("Boveda 1");
        conciliacionDivisasIntVO.setIdConciliacionEfectivo("1");
        conciliacionDivisasIntVO.setMontoCustodio(BigDecimal.valueOf(135));
        conciliacionDivisasIntVO.setMontoIndeval(BigDecimal.valueOf(164));
        conciliacionDivisasIntVO.setMontoDiferencia(BigDecimal.valueOf(432));
        conciliacionDivisasIntVO.setFecha(new Date());
        conciliacionDivisasIntVOs.add(conciliacionDivisasIntVO);
        return conciliacionDivisasIntVOs;
    }

}
