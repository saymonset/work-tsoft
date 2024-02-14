package com.indeval.portalinternacional.middleware.services.divisioninternacional.custodio;

import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<Custodio> getAllCustodiosEsperados() {
        List<Custodio> custodios = new ArrayList<>();

        // Lista de nombres cortos  correspondientes
        String[] nombresCortos = {"CLEARST", "EUROCLE", "DEUTSCHE", "SANTANDER", "BBVA", "BONY", "JPMORGAN", "BOFAMERI", "DCVCHILE", "DECEVAL", "CAVALI", "MARKIT", "CITIBANK", "BNPPARIBAS"};

        // Lista de descripciones correspondientes
        String[] descripciones = {"CLEARSTREAM BANKING", "EUROCLEAR BANK", "DEUTSCHE BANK AG, NY", "SANTANDER INVESTMENT SERVICES, S.A.", "BANCO BILBAO VIZCAYA ARGENTARIA SA", "THE BANK OF NEW YORK", "JP MORGAN", "BANK OF AMERICA MERILL LYNCH", "DEPOSITO CENTRAL DE VALORES S.A.", "DECEVAL S.Field52aOrderInstLocal.A.", "CAVALI S.A. I.C.L.V.", "MARKIT", "CITIBANK, N.A.", "BNP PARIBAS"};


        for (int i = 0; i < nombresCortos.length; i++) {
            Custodio custodio = new Custodio();
            custodio.setId(i + 1);
            custodio.setNombreCorto(nombresCortos[i]);
            custodio.setDescripcion(descripciones[i]);
            custodios.add(custodio);
        }
        return custodios;
    }

    public static String custodioToString(Custodio custodio) {
        return "Custodio{" +
                "idCustodio=" + custodio.getId() +
                ", nombreCorto='" + custodio.getNombreCorto() + '\'' +
                ", descripcion='" + custodio.getDescripcion() +
                '}';

    }
}
