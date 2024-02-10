// Cambio Multidivisas
package com.indeval.portalinternacional.common.util;

import com.indeval.portalinternacional.middleware.servicios.dto.CuentaCorresponsalDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.InstitucionWebDTO;
import com.indeval.portalinternacional.unittest.BaseDaoTestCase;
import com.sun.xml.internal.bind.v2.TODO;
import org.junit.Test;


import javax.faces.model.SelectItem;
import java.util.List;

public class ConsultaCatalogosFacadeTest extends BaseDaoTestCase {

    ConsultaCatalogosFacade service;
    protected void onSetUp() throws Exception {
        super.onSetUp();
        service = (ConsultaCatalogosFacade) getBean("catalogosFacade");
    }

    public void testBuscarInstitucionPorIdFolio() {
        System.out.println("testBuscarInstitucionPorIdFolio()");

        String idFolio = "01009";
        String expectedName = "CBVALMEX";

        assertNotNull("ConsultaCatalogosFacade Nulo", service);
        InstitucionWebDTO institucionWebDTO = service.buscarInstitucionPorIdFolio(idFolio);

        assertNotNull(institucionWebDTO);
        assertEquals(expectedName, institucionWebDTO.getNombre());
    }

    public void testGetSelectItemsTipoDivisa() {
        System.out.println("testGetSelectItemsTipoDivisa()");
        int expectedSize = 185;

        assertNotNull("ConsultaCatalogosFacade Nulo", service);
        List<SelectItem> selectItems = service.getSelectItemsTipoDivisa();
        assertNotNull(selectItems);
        assertEquals(expectedSize, selectItems.size());
    }

    public void testGetSelectItemsBovedasEfectivoPorDivisa() {
        System.out.println("testGetSelectItemsBovedasEfectivoPorDivisa()");

        DivisaDTO divisaDTO = new DivisaDTO();
        divisaDTO.setId(3);
        divisaDTO.setClaveAlfabetica("Seleccione una Divisa"); //"USD"
        int expectedSize = 16;

        assertNotNull("ConsultaCatalogosFacade Nulo", service);
        List<SelectItem> selectItems = service.getSelectItemsBovedasEfectivoPorDivisa(divisaDTO);
        assertNotNull(selectItems);
        assertEquals(expectedSize, selectItems.size());
    }

    public void testGetCuentasCorresponsalesByDivisaAndInstitucion() {
        System.out.println("testGetCuentasCorresponsalesByDivisaAndInstitucion()");

        Long idDivisa = 3L;
        Long idInstitucion = 9L;
        Long expectedIdCuentaCorresponsal = 9L;

        assertNotNull("ConsultaCatalogosFacade Nulo", service);
        CuentaCorresponsalDTO corresponsalDTO = service.getCuentasCorresponsalesByDivisaAndInstitucion(idDivisa, idInstitucion);
        assertNotNull(corresponsalDTO);
        assertEquals(expectedIdCuentaCorresponsal, corresponsalDTO.getIdCuentaCorresponsal());
    }

    public void testGetSaldoNetoEfectivo() {
        System.out.println("testBuscarInstitucionPorIdFolio()");

        String idFolioParticipante = "";
        Double expectedSaldoNetoEfectivo = 0.0;

        assertNotNull("ConsultaCatalogosFacade Nulo", service);
        Double saldoNetoEfectivo = service.getSaldoNetoEfectivo(idFolioParticipante);
        assertEquals(expectedSaldoNetoEfectivo, saldoNetoEfectivo);
    }

    public void testObtenerDivisasPorInstitucion() {
        System.out.println("testBuscarInstitucionPorIdFolio()");

        long idInstitucion = 9L;
        int expectedSize = 6;

        assertNotNull("ConsultaCatalogosFacade Nulo", service);
        List<SelectItem> divisasPorInstitucion = service.obtenerDivisasPorInstitucion(idInstitucion);
        assertNotNull(divisasPorInstitucion);
        assertEquals(expectedSize, divisasPorInstitucion.size());
    }

    public void testGetSaldoNetoEfectivoPorBovedaDivisa() {
        System.out.println("testGetSaldoNetoEfectivoPorBovedaDivisa()");

        String idFolioParticipante = "01009";
        Long idBoveda = 13L;
        Long idDivisa = 3L;

        assertNotNull("ConsultaCatalogosFacade Nulo", service);
        Double saldo = service.getSaldoNetoEfectivoPorBovedaDivisa(idFolioParticipante, idBoveda, idDivisa);

        System.out.println(saldo);

        assertNotNull(saldo);
        assertTrue(true);
    }

    public void testGetSaldoDisponibleEfectivoPorBovedaDivisa() {
        System.out.println("testGetSaldoDisponibleEfectivoPorBovedaDivisa()");

        String idFolioParticipante = "01009";
        Long idBoveda = 13L;
        Long idDivisa = 3L;

        assertNotNull("ConsultaCatalogosFacade Nulo", service);
        Double saldo = service.getSaldoDisponibleEfectivoPorBovedaDivisa(idFolioParticipante, idBoveda, idDivisa);

        System.out.println(saldo);

        assertNotNull(saldo);
        assertTrue(true);
    }

    public void testFindCatBicEnBaseABovedaEfectivoParticipante() {
        System.out.println("testFindCatBicEnBaseABovedaEfectivoParticipante()");

        Long idBoveda = 13L;
        Long idInstitucion = 9L;
        Long expectedIdCatBic = 5L;

        assertNotNull("BovedaService Nulo", service);
        Long idCatbic = service.findCatBicEnBaseABovedaEfectivoParticipante(idBoveda, idInstitucion);

        assertNotNull(idCatbic);
        assertEquals(expectedIdCatBic, idCatbic);
    }
}