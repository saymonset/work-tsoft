package com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo;

import com.indeval.persistence.unittest.BaseDaoTestCase;

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

    public void testObtenerNegativos() {
        System.out.println("testObtenerNegativos()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);

        int i = 0;

        for (String[] combinacion : COMBINACIONES) {
            i++;
            System.out.println("      Prueba combinacion [" + i + "] :: " + combinacion[5]);
            try {
                dao.obtenerNegativos(combinacion[0], combinacion[1]);
            } catch (Exception e) {
                e.printStackTrace();
                assertTrue(false);
                break;
            }
        }
        assertTrue(true);
    }

    public void testObtenerInformacionFolioAgrupado() {
        System.out.println("testObtenerInformacionFolioAgrupado()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);

        int i = 0;

        for (String[] combinacion : COMBINACIONES) {
            i++;
            System.out.println("      Prueba combinacion [" + i + "] :: " + combinacion[5]);
            try {
                dao.obtenerInformacionFolioAgrupado(
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

    public void testObtenerInformacionReferencias() {
        System.out.println("testObtenerInformacionReferencias()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);

        int i = 0;

        for (String[] combinacion : COMBINACIONES) {
            i++;
            System.out.println("      Prueba combinacion [" + i + "] :: " + combinacion[5]);
            try {
                dao.obtenerInformacionReferencias(
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
        System.out.println("testObtenerDetalleReferencias()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);
        List<String[]> resultados = dao.obtenerDetalleReferencias(FOLIO_RELACIONADO);
        imprimirResultados(resultados);
        assertTrue(true);
    }

    public void testObtenerDetalleReferenciasTotal() {
        System.out.println("testObtenerDetalleReferenciasTotal()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);
        BigDecimal resultado = dao.obtenerDetalleReferenciasTotal(FOLIO_RELACIONADO);
        System.out.println(resultado.toString());
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

    public void testAsignarFolioRelacionado() {
        System.out.println("testAsignarFolioRelacionado()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);
        dao.asignarFolioRelacionado("Prueba", "0");
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

    public void testObtenerIdRegistro() {
        System.out.println("testObtenerFolioRelacionado()");
        assertNotNull("CuentasTransitoriasEfectivoDao Nulo", dao);
        List<String[]> resultados = new ArrayList<>();
        resultados.add(dao.obtenerIdRegistro(ID_REGISTRO));
        imprimirResultados(resultados);
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

