package com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.BovedaMontosDto;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.DetalleReferenciaDto;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.FolioAgrupadoDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.Util.*;

public class CuentasTransitoriasEfectivoDaoImplTest extends BaseDaoTestCase {

    CuentasTransitoriasEfectivoDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (CuentasTransitoriasEfectivoDao) getBean("cuentasTransitoriasEfectivoDao");
        prepararDatos();
    }

    public void testObtenerDivisasExtranjeras() {
        System.out.println("testObtenerDivisasExtranjeras()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);
        List<String[]> resultados = dao.obtenerDivisasExtranjeras();
        imprimirResultados(resultados);
        assertTrue(true);
    }

    public void testObtenerCustodios() {
        System.out.println("testObtenerCustodios()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);
        List<String[]> resultados = dao.obtenerCustodios();
        imprimirResultados(resultados);
        assertTrue(true);
    }

    public void testObetenerTotalBoveda(){
        System.out.println("testObetenerTotalBoveda");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);
        BovedaMontosDto bovedaMontosDto = dao.obetenerTotalBoveda(ID_DIVISA, ID_CUSTODIO);
        System.out.println(bovedaMontosDto);
        assertTrue(true);
    }

    public void testObetenerNegativosTotal() {
        System.out.println("testObtenerNegativosTotal()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);

        int i = 0;

        for (String[] combinacion : COMBINACIONES) {
            i++;
            System.out.println("      Prueba combinacion [" + i + "] :: " + combinacion[5]);
            try {
                FolioAgrupadoDto folioAgrupadoDto = dao.obetenerNegativosTotal(combinacion[0], combinacion[1]);
                System.out.println(folioAgrupadoDto);
            } catch (Exception e) {
                e.printStackTrace();
                assertTrue(false);
                break;
            }
        }
        assertTrue(true);
    }

    public void testObtenerNegativosDetalles() {
        System.out.println("testObtenerNegativosDetalles()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);

        int i = 0;

        for (String[] combinacion : COMBINACIONES) {
            i++;
            System.out.println("      Prueba combinacion [" + i + "] :: " + combinacion[5]);
            try {
                dao.obtenerNegativosDetalles(combinacion[0], combinacion[1]);
            } catch (Exception e) {
                e.printStackTrace();
                assertTrue(false);
                break;
            }
        }
        assertTrue(true);
    }

    public void testObtenerInformacionFoliosAgrupados() {
        System.out.println("testObtenerInformacionFoliosAgrupados()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);

        int i = 0;

        for (String[] combinacion : COMBINACIONES) {
            i++;
            System.out.println("      Prueba combinacion [" + i + "] :: " + combinacion[5]);
            try {
                dao.obtenerInformacionFoliosAgrupados(
                        combinacion[0], combinacion[1],
                        combinacion[2], combinacion[3],
                        combinacion[4]);
            } catch (Exception e) {
                e.printStackTrace();
                assertTrue(false);
                break;
            }
        }
        assertTrue(true);
    }

    public void testObtenerDetalleReferencias() {
        String ID_CUSTODIO = "2";
        String ID_DIVISA = "14";
        String FOLIO_RELACIONADO = "1000190182";
        System.out.println("testObtenerDetalleReferencias()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);
        List<DetalleReferenciaDto> resultados = dao.obtenerDetalleReferencias(ID_DIVISA, ID_CUSTODIO, FOLIO_RELACIONADO);
        for(DetalleReferenciaDto resultado: resultados){
            System.out.println(resultado);
        }
        assertTrue(true);
    }

    public void testObtenerDetalleReferenciasTotal() {
        System.out.println("testObtenerDetalleReferenciasTotal()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);
        BigDecimal resultado = dao.obtenerDetalleReferenciasTotal(ID_DIVISA, ID_CUSTODIO,FOLIO_RELACIONADO);
        System.out.println(resultado.toString());
        assertTrue(true);
    }

    public void testObtenerInformacionSinReferencias() {
        System.out.println("testObtenerInformacionSinReferencias()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);

        int i = 0;

        for (String[] combinacion : COMBINACIONES) {
            i++;
            System.out.println("      Prueba combinacion [" + i + "] :: " + combinacion[5]);
            try {
                dao.obtenerInformacionSinReferencias(
                        combinacion[0], combinacion[1],
                        combinacion[2], combinacion[3],
                        combinacion[4]);
            } catch (Exception e) {
                e.printStackTrace();
                assertTrue(false);
                break;
            }
        }
        assertTrue(true);
    }

    public void testObtenerMensajeISO() {
        System.out.println("testObtenerMensajeISO()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);
        System.out.println(dao.obtenerMensajeISO(ID_REGISTRO));
        assertTrue(true);
    }

    public void testValidarFolioRelacionado() {
        System.out.println("testValidarFolioRelacionado()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);
        dao.validarFolioRelacionado(FOLIO_RELACIONADO, ID_REGISTRO);
        assertTrue(true);
    }

    public void testObtenerFolioRelacionado() {
        System.out.println("testObtenerFolioRelacionado()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);
        List<String[]> resultados = new ArrayList<>();
        resultados.add(dao.obtenerFolioRelacionado(FOLIO_RELACIONADO));
        imprimirResultados(resultados);
        assertTrue(true);
    }

    public void testAsignarFolioRelacionado() {
        System.out.println("testAsignarFolioRelacionado()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);
        dao.asignarFolioRelacionado("Prueba", "0");
        assertTrue(true);
    }


    private void imprimirResultados(List<String[]> resultados) {
        if (resultados != null) {
            for (String[] resultado : resultados) {
                System.out.print(resultado[0]);
                for (int i = 1; i < resultado.length; i++) {
                    System.out.print(" | ");
                    System.out.print(resultado[i]);
                }
                System.out.println();
            }
        }
    }

}

