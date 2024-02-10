package com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo;


import com.indeval.portalinternacional.persistence.dao.impl.cuentasTransitoriasEfectivo.ConsultasCuentasTransitoriasEfectivo;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.Util.*;


public class ConsultasCuentasTransitoriasEfectivoTest extends TestCase {
    Connection connection;
    Statement statement;

    public void setUp() throws Exception {
        super.setUp();
        prepararDatos();
        inicioConexion();
    }


    public void testGetQueryFoliosRelacionadosAgrupados() {
        System.out.println("testGetQueryFoliosRelacionadosAgrupado()");
        int i = 0;
        for (String[] combinacion : COMBINACIONES) {
            i++;
            System.out.println("     Prueba combinacion [" + i + "] :: " + combinacion[5]);

            if (!validarQuery(false,
                    ConsultasCuentasTransitoriasEfectivo.
                            getQueryFoliosRelacionadosAgrupados(
                                    combinacion[0], combinacion[1],
                                    combinacion[2], combinacion[3],
                                    combinacion[4]))) {
                assertTrue(false);
                break;
            }
        }
        assertTrue(true);
    }

    public void testGetQueryFoliosNegativos() {
        System.out.println("testGetQueryFoliosNegativos()");
        int i = 0;
        for (String[] combinacion : COMBINACIONES) {
            i++;
            System.out.println("     Prueba combinacion [" + i + "] :: " + combinacion[5]);

            if (!validarQuery(false,
                    ConsultasCuentasTransitoriasEfectivo.getQueryNegativos(
                            combinacion[0], combinacion[1]))) {
                assertTrue(false);
                break;
            }
        }
        assertTrue(true);
    }


    public void testGetQueryMensajeISO() {
        assertTrue(validarQuery(false,
                ConsultasCuentasTransitoriasEfectivo.
                        getQueryMensajeISO(ID_REGISTRO)));
    }

    public void testGetQueryReferencias() {
        System.out.println("testGetQueryReferencias()");
        int i = 0;
        for (String[] combinacion : COMBINACIONES) {
            i++;
            System.out.println("     Prueba combinacion [" + i + "] :: " + combinacion[5]);
            if (!validarQuery(false,
                    ConsultasCuentasTransitoriasEfectivo.getQueryReferencias(
                            combinacion[0], combinacion[1],
                            combinacion[2], combinacion[3],
                            combinacion[4]))) {
                assertTrue(false);
                break;
            }
        }
        assertTrue(true);
    }

    public void testGetQueryReferenciasDetalle() {
        assertTrue(validarQuery(false,
                ConsultasCuentasTransitoriasEfectivo.
                        getQueryReferenciasDetalle(FOLIO_RELACIONADO)));
    }

    public void testGetQueryReferenciasDetalleTotal() {
        assertTrue(validarQuery(false,
                ConsultasCuentasTransitoriasEfectivo.
                        getQueryReferenciasDetalleTotal(FOLIO_RELACIONADO)));
    }

    public void testGetQueryValidarFolioRelacionado() {
        assertTrue(validarQuery(false,
                ConsultasCuentasTransitoriasEfectivo.
                        getQueryValidarFolioRelacionado(FOLIO_RELACIONADO)));
    }

    public void testGetQueryObtenerCustodio() {
        assertTrue(validarQuery(false,
                ConsultasCuentasTransitoriasEfectivo.
                        getQueryObtenerCustodio(ID_REGISTRO)));
    }

    public void testGetQueryAsignarFolioRelacionado() {
        assertTrue(validarQuery(true,
                ConsultasCuentasTransitoriasEfectivo.
                        getQueryAsignarFolioRelacionado(FOLIO_RELACIONADO, ID_REGISTRO)));
    }

    public void testGetQueryRegistroPorIdRegistro() {
        assertTrue(validarQuery(true,
                ConsultasCuentasTransitoriasEfectivo.
                        getQueryRegistroPorIdRegistro(ID_REGISTRO)));
    }

    public void testGetQueryRegistroPorReferencia() {
        assertTrue(validarQuery(true,
                ConsultasCuentasTransitoriasEfectivo.
                        getQueryRegistroPorReferencia(FOLIO_RELACIONADO)));
    }

    private boolean validarQuery(boolean update, String query) {
        try {
            Statement statement = connection.createStatement();
            if (update) {
                statement.executeUpdate(query);
            } else {
                statement.execute(query);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void inicioConexion() {
        System.out.println("Inicio conexión para probar queries");
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String url = "jdbc:oracle:thin:@10.100.230.42:1521/homology";
            //jdbc:oracle:thin:@hostname:1521/service_name
            //jdbc:oracle:thin:@hostname:1521:SID
            String username = "dali_admin";
            String password = "dalidev";
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tearDown() {
        System.out.println("Cerrar conexion");
        try {
            if (connection != null) {
                connection.rollback();
                connection.close();
            }
        } catch (Exception ex) {
            connection = null;
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (Exception ex) {
            statement = null;
        }
    }


}