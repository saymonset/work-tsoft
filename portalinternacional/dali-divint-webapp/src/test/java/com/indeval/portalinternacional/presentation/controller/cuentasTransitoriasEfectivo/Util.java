package com.indeval.portalinternacional.presentation.controller.cuentasTransitoriasEfectivo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Util {

    public static final  Date FECHA_INICIO = new Date();
    public static final  Date FECHA_FIN = new Date();
    public static final  Long ID_CUSTODIO  = 13L;
    public static final Long ID_DIVISA = 13L;
    public static final String FOLIO_RELACIONADO = "2333962289002";
    public static final String ID_REGISTRO = "170";

    public static final List<DataFaces> COMBINACIONES = new ArrayList<>();

    public static void prepararDatos() {
        System.out.println("Preparar datos de combinacion de filtros");
        COMBINACIONES.add(new DataFaces(null, null, null, null, null, "Sin Filtros"));
        COMBINACIONES.add(new DataFaces(ID_DIVISA, null, null, null, null, "Divisa"));
        COMBINACIONES.add(new DataFaces(null, ID_CUSTODIO, null, null, null, "Custodio"));
        COMBINACIONES.add(new DataFaces(null, null, FECHA_INICIO, null, null, "Fecha Inicio"));
        COMBINACIONES.add(new DataFaces(null, null, null, FECHA_FIN, null, "Fecha Fin"));
        COMBINACIONES.add(new DataFaces(null, null, null, null, FOLIO_RELACIONADO, "Folio"));

        COMBINACIONES.add(new DataFaces(ID_DIVISA, ID_CUSTODIO, null, null, null, "Divisa - Custodio"));
        COMBINACIONES.add(new DataFaces(ID_DIVISA, null, FECHA_INICIO, null, null, "Divisa - FechaInicio"));
        COMBINACIONES.add(new DataFaces(ID_DIVISA, null, null, FECHA_FIN, null, "Divisa - Fecha Fin"));
        COMBINACIONES.add(new DataFaces(ID_DIVISA, null, null, null, FOLIO_RELACIONADO, "Divisa - Folio"));

        COMBINACIONES.add(new DataFaces(null, ID_CUSTODIO, FECHA_INICIO, null, null, "Custodio - FechaInicio"));
        COMBINACIONES.add(new DataFaces(null, ID_CUSTODIO, null, FECHA_FIN, null, "Custodio - FechaFin"));
        COMBINACIONES.add(new DataFaces(null, ID_CUSTODIO, null, null, FOLIO_RELACIONADO, "Custodio - Folio"));

        COMBINACIONES.add(new DataFaces(null, null, FECHA_INICIO, FECHA_FIN, null, "FechaInicio - FechaFin"));
        COMBINACIONES.add(new DataFaces(null, null, FECHA_INICIO, null, FOLIO_RELACIONADO, "FechaInicio - Folio"));

        COMBINACIONES.add(new DataFaces(null, null, null, FECHA_FIN, FOLIO_RELACIONADO, "FechaFin - Folio"));

        COMBINACIONES.add(new DataFaces(ID_DIVISA, ID_CUSTODIO, FECHA_INICIO, null, null, "Divisa - Custodio - FechaInicio"));
        COMBINACIONES.add(new DataFaces(ID_DIVISA, ID_CUSTODIO, null, FECHA_FIN, null, "Divisa - Custodio - FechaFin"));;
        COMBINACIONES.add(new DataFaces(ID_DIVISA, ID_CUSTODIO, null, null, FOLIO_RELACIONADO, "Divisa - Custodio - Folio"));

        COMBINACIONES.add(new DataFaces(ID_DIVISA, null, FECHA_INICIO, FECHA_FIN, null, "Divisa - FechaInicio - FechaFin"));
        COMBINACIONES.add(new DataFaces(ID_DIVISA, null, FECHA_INICIO, null, FOLIO_RELACIONADO, "Divisa - FechaInicio - Folio"));

        COMBINACIONES.add(new DataFaces(ID_DIVISA, null, null, FECHA_FIN, FOLIO_RELACIONADO, "Divisa - FechaFin - Folio"));

        COMBINACIONES.add(new DataFaces(null, ID_CUSTODIO, FECHA_INICIO, FECHA_FIN, null, "Custodio - FechaInicio - FechaFin"));
        COMBINACIONES.add(new DataFaces(null, ID_CUSTODIO, FECHA_INICIO, null, FOLIO_RELACIONADO, "Custodio - FechaInicio - Folio"));

        COMBINACIONES.add(new DataFaces(null, ID_CUSTODIO, null, FECHA_FIN, FOLIO_RELACIONADO, "Custodio - FechaFin - Folio"));


        COMBINACIONES.add(new DataFaces(null, null, FECHA_INICIO, FECHA_FIN, FOLIO_RELACIONADO, " FechaFin - Folio"));

        COMBINACIONES.add(new DataFaces(ID_DIVISA, ID_CUSTODIO, FECHA_INICIO, FECHA_FIN, FOLIO_RELACIONADO, " Todos"));


    }
}
