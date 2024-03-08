// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.DivisaDali;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.ConciliacionInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.SaldoNombradaInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosInDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.DivisaInt;
import com.indeval.portalinternacional.persistence.dao.DivisaDaliDao;

import java.text.ParseException;
import java.util.List;

public class DivisaDaliDaoImplTest extends BaseDaoTestCase {

    DivisaDaliDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (DivisaDaliDao) getBean("divisaDaliDao");
    }

    public void testBuscarDivisas() {
        System.out.println("testBuscarDivisas()");

        int expectedSize = 185;

        assertNotNull("DivisaDaliDao Nulo", dao);
        List<DivisaDTO> divisas = dao.buscarDivisas(null);

        for (DivisaDTO divisa : divisas) {
            System.out.println(divisa.getDescripcion());
        }

        assertFalse(divisas.isEmpty());
        assertNotNull(divisas);
        assertEquals(expectedSize, divisas.size());
    }

    public void testConsultarDivisaPorId() {
        System.out.println("testConsultarDivisaPorId()");

        int idDivisa = 3;
        String expectedClaveAlfabetica = "USD";
        String expectedClaveNumerica = "840";
        String expectedDescripcion = "USA DOLLAR";

        assertNotNull("DivisaDaliDao Nulo", dao);
        DivisaDTO divisa = dao.consultarDivisaPorId(idDivisa);

        assertNotNull(divisa);
        assertEquals(expectedClaveAlfabetica, divisa.getClaveAlfabetica());
        assertEquals(expectedClaveNumerica, divisa.getClaveNumerica());
        assertEquals(expectedDescripcion, divisa.getDescripcion());
    }

    public void testFindDivisasByBovedad() {
        System.out.println("testFindDivisaByBovedad()");

        long idBoveda = 12;

        assertNotNull("DivisaDaliDao Nulo", dao);
        List<DivisaDTO> list = dao.findDivisaByBovedad(idBoveda);


        assertNotNull(list);

    }



    public void testConsultarDivisas() {
        List<DivisaInt> divisas = dao.consultarDivisas();
        for (DivisaInt divisaInt : divisas) {
            System.out.println(
                    divisaInt.getIdDivisa() + ", " +
                            divisaInt.getClaveAlfabetica() + ", " +
                            divisaInt.getDescripcion());
        }
    }

    public void testGetDivisaDescripcionById() {
        System.out.println("testGetDivisaDescripcionById()");

        int idDivisa = 3;
        String expectedDescripcion = "USA DOLLAR";

        assertNotNull("DivisaDaliDao Nulo", dao);
        String descripcion = dao.getDivisaDescripcionById(idDivisa);

        System.out.println(descripcion);

        assertNotNull(descripcion);
        assertEquals(expectedDescripcion, descripcion);
    }

    public void testGetDivisaDescripcionByWrongId() {
        System.out.println("testGetDivisaDescripcionByWrongId()");

        int idDivisa = 0;

        assertNotNull("DivisaDaliDao Nulo", dao);
        String result = dao.getDivisaDescripcionById(idDivisa);

        System.out.println(result);

        assertNull(result);
    }
}
