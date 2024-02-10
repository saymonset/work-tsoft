// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.Boveda;

import com.indeval.portalinternacional.middleware.servicios.dto.*;

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

}
