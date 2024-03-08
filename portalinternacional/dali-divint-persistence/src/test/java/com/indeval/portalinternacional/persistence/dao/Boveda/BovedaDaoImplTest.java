// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.Boveda;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.dto.BovedaDto;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Bovedas;
import com.indeval.portalinternacional.persistence.dao.BovedaDao;

import java.math.BigInteger;
import java.util.List;

import static com.indeval.portalinternacional.persistence.dao.Boveda.Util.getCriterio;
import static com.indeval.portalinternacional.persistence.dao.Boveda.Util.getDivisa;

public class BovedaDaoImplTest extends BaseDaoTestCase {

    BovedaDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (BovedaDao) getBean("bovedaIntDao");
    }

    public void testObtenerBovedasPorDivisa() {
        System.out.println("testObtenerBovedasPorDivisa()");
        int expectedSize = 15;
        DivisaDTO divisa = getDivisa();

        assertNotNull("BovedaDao Nulo", dao);
        List<BigInteger> idsBovDiv = dao.obtenerBovedasPorDivisa(divisa);

        for (BigInteger id: idsBovDiv) {
            System.out.println(id);
        }

        assertNotNull(idsBovDiv);
        assertEquals(expectedSize, idsBovDiv.size());
    }

    public void testBuscarBovedasPorTipoCustodia() {
        System.out.println("testBuscarBovedasPorTipoCustodia()");
        int expectedSize = 15;
        BovedaDto criterio = getCriterio();

        assertNotNull("BovedaDao Nulo", dao);
        List<BovedaDto> bovedas = dao.buscarBovedasPorTipoCustodia(criterio, null);

        for (BovedaDto boveda : bovedas) {
            System.out.println(boveda.getDescripcion());
        }

        assertNotNull(bovedas);
        assertEquals(expectedSize, bovedas.size());

    }

    public void testFindAllBovedasEfectivo() {
        List<Bovedas>  bovedasEfectivo = dao.findAllBovedasEfectivo();
        for(Bovedas boveda: bovedasEfectivo ){
            System.out.println(Util.bovedasToString(boveda));
        }
        assertNotNull(bovedasEfectivo);
    }

    public void testGetBovedaDescriptionById() {
        System.out.println("testGetBovedaDescriptionById()");

        int idBoveda = 13;
        String expectedBovedaDescription = "BOVEDA DE EFECTIVO EUROCLEAR";

        assertNotNull("BovedasDao Nulo", dao);
        String bovedaDescription = dao.getBovedaDescriptionById(idBoveda);

        System.out.println(bovedaDescription);

        assertNotNull(bovedaDescription);
        assertEquals(expectedBovedaDescription, bovedaDescription);
    }

    public void testGetBovedaDescriptionByWrongId() {
        System.out.println("testGetBovedaDescriptionByWrongId()");

        int idBoveda = 0;

        assertNotNull("BovedasDao Nulo", dao);
        String result = dao.getBovedaDescriptionById(idBoveda);

        System.out.println(result);

        assertNull(result);
    }
}