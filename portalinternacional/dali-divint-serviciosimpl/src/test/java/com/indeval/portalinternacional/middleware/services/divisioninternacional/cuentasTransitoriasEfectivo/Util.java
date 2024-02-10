package com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static final String ID_DIVISA = "3";
    public static final String ID_CUSTODIO = "13";
    public static final String FECHA_INICIO = "01/01/2023";
    public static final String FECHA_FIN = "31/12/2023";
    public static final String FOLIO_RELACIONADO = "2333962289002";
    public static final String ID_REGISTRO = "170";

    public static final List<String[]> COMBINACIONES = new ArrayList<>();

    public static void prepararDatos() {
        System.out.println("Preparar datos de combinacion de filtros");
        COMBINACIONES.add(new String[]{null, null, null, null, null, "Sin Filtros"});
        COMBINACIONES.add(new String[]{ID_DIVISA, null, null, null, null, "Divisa"});
        COMBINACIONES.add(new String[]{null, ID_CUSTODIO, null, null, null, "Custodio"});
        COMBINACIONES.add(new String[]{null, null, FECHA_INICIO, null, null, "Fecha Inicio"});
        COMBINACIONES.add(new String[]{null, null, null, FECHA_FIN, null, "Fecha Fin"});
        COMBINACIONES.add(new String[]{null, null, null, null, FOLIO_RELACIONADO, "Folio"});

        COMBINACIONES.add(new String[]{ID_DIVISA, ID_CUSTODIO, null, null, null, "Divisa - Custodio"});
        COMBINACIONES.add(new String[]{ID_DIVISA, null, FECHA_INICIO, null, null, "Divisa - FechaInicio"});
        COMBINACIONES.add(new String[]{ID_DIVISA, null, null, FECHA_FIN, null, "Divisa - Fecha Fin"});
        COMBINACIONES.add(new String[]{ID_DIVISA, null, null, null, FOLIO_RELACIONADO, "Divisa - Folio"});

        COMBINACIONES.add(new String[]{null, ID_CUSTODIO, FECHA_INICIO, null, null, "Custodio - FechaInicio"});
        COMBINACIONES.add(new String[]{null, ID_CUSTODIO, null, FECHA_FIN, null, "Custodio - FechaFin"});
        COMBINACIONES.add(new String[]{null, ID_CUSTODIO, null, null, FOLIO_RELACIONADO, "Custodio - Folio"});

        COMBINACIONES.add(new String[]{null, null, FECHA_INICIO, FECHA_FIN, null, "FechaInicio - FechaFin"});
        COMBINACIONES.add(new String[]{null, null, FECHA_INICIO, null, FOLIO_RELACIONADO, "FechaInicio - Folio"});

        COMBINACIONES.add(new String[]{null, null, null, FECHA_FIN, FOLIO_RELACIONADO, "FechaFin - Folio"});

        COMBINACIONES.add(new String[]{ID_DIVISA, ID_CUSTODIO, FECHA_INICIO, null, null, "Divisa - Custodio - FechaInicio"});
        COMBINACIONES.add(new String[]{ID_DIVISA, ID_CUSTODIO, null, FECHA_FIN, null, "Divisa - Custodio - FechaFin"});;
        COMBINACIONES.add(new String[]{ID_DIVISA, ID_CUSTODIO, null, null, FOLIO_RELACIONADO, "Divisa - Custodio - Folio"});

        COMBINACIONES.add(new String[]{ID_DIVISA, null, FECHA_INICIO, FECHA_FIN, null, "Divisa - FechaInicio - FechaFin"});
        COMBINACIONES.add(new String[]{ID_DIVISA, null, FECHA_INICIO, null, FOLIO_RELACIONADO, "Divisa - FechaInicio - Folio"});

        COMBINACIONES.add(new String[]{ID_DIVISA, null, null, FECHA_FIN, FOLIO_RELACIONADO, "Divisa - FechaFin - Folio"});

        COMBINACIONES.add(new String[]{null, ID_CUSTODIO, FECHA_INICIO, FECHA_FIN, null, "Custodio - FechaInicio - FechaFin"});
        COMBINACIONES.add(new String[]{null, ID_CUSTODIO, FECHA_INICIO, null, FOLIO_RELACIONADO, "Custodio - FechaInicio - Folio"});

        COMBINACIONES.add(new String[]{null, ID_CUSTODIO, null, FECHA_FIN, FOLIO_RELACIONADO, "Custodio - FechaFin - Folio"});


        COMBINACIONES.add(new String[]{null, null, FECHA_INICIO, FECHA_FIN, FOLIO_RELACIONADO, " FechaFin - Folio"});

        COMBINACIONES.add(new String[]{ID_DIVISA, ID_CUSTODIO, FECHA_INICIO, FECHA_FIN, FOLIO_RELACIONADO, " Todos"});


    }
}
