// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.boveda;

import com.indeval.portalinternacional.middleware.servicios.dto.BovedaDto;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.TipoBovedaDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.Bovedas;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static DivisaDTO getDivisa(){
        DivisaDTO divisa = new DivisaDTO();
        divisa.setId(3);
        divisa.setIdString("3");
        divisa.setClaveAlfabetica("USD");

        return divisa;
    }

    public static BovedaDto getCriterio(){
        BovedaDto criterio = new BovedaDto();
        criterio.setTipoBoveda(new TipoBovedaDto(null, "E", null, null));

        return criterio;
    }

    public static List<BigInteger> getIdsBovedas(){
        List<BigInteger> ids = new ArrayList<BigInteger>();
        ids.add(new BigInteger("12"));
        ids.add(new BigInteger("13"));
        ids.add(new BigInteger("14"));
        ids.add(new BigInteger("15"));
        ids.add(new BigInteger("16"));
        ids.add(new BigInteger("17"));
        ids.add(new BigInteger("18"));
        ids.add(new BigInteger("20"));
        ids.add(new BigInteger("22"));
        ids.add(new BigInteger("24"));
        ids.add(new BigInteger("26"));
        ids.add(new BigInteger("28"));
        ids.add(new BigInteger("31"));
        ids.add(new BigInteger("33"));
        ids.add(new BigInteger("45"));

        return ids;
    }

    public static List<Bovedas> getAllBovedasEfectivoEsperadas() {
        List<Bovedas> bovedasEfectivoEsperadas = new ArrayList<>();
        Integer idTipoBoveda = 2;

        // Lista de nombres cortos correspondientes
        String[] nombresCortos = {"E-JPMORGAN", "E-CLEARST", "E-EUROCLE", "E-BONY", "E-BBVA", "E-DEUTSCHE", "E-SANTAND", "E-BOFA", "E-DCVCHILE", "E-DECEVAL", "E-CAVALI", "E-BOFAMERI", "E-CITIBANK", "E-BNPPARIBAS", "E-CUSGUS"};

        // Lista de descripciones correspondientes
        String[] descripciones = {"BOVEDA DE EFECTIVO JPMORGAN", "BOVEDA DE EFECTIVO CLEAR STREAM", "BOVEDA DE EFECTIVO EUROCLEAR", "BOVEDA DE EFECTIVO BONY", "BOVEDA DE EFECTIVO BBVA", "BOVEDA DE EFECTIVO DEUTSCHE", "BOVEDA DE EFECTIVO SANTANDER", "BOVEDA DE EFECTIVO BOFA", "BOVEDA DE EFECTIVO DCVCHILE", "BOVEDA DE EFECTIVO DECEVAL", "BOVEDA DE EFECTIVO CAVALI", "BOVEDA DE EFECTIVO BOFAMERI", "BOVEDA DE EFECTIVO CITIBANK", "BOVEDA DE EFECTIVO BNP PARIBAS", "BOVEDA EFECTIVO CUS GUS"};

        for (int i = 0; i < nombresCortos.length; i++) {
            Bovedas boveda = new Bovedas();
            boveda.setIdTipoBoveda(idTipoBoveda);
            boveda.setIdBoveda(i + 1);
            boveda.setNombreCorto(nombresCortos[i]);
            boveda.setDescripcion(descripciones[i]);
            bovedasEfectivoEsperadas.add(boveda);
        }

        return bovedasEfectivoEsperadas;
    }


    public static String bovedasToString(Bovedas boveda) {
        return "Boveda{" +
                "idBoveda=" + boveda.getIdBoveda() +
                ", idTipoBoveda=" + boveda.getIdTipoBoveda() +
                ", nombreCorto='" + boveda.getNombreCorto() + '\'' +
                ", descripcion='" + boveda.getDescripcion() +
                '}';

    }


}
