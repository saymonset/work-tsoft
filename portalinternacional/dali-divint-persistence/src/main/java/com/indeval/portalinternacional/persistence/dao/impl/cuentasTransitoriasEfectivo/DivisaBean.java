package com.indeval.portalinternacional.persistence.dao.impl.cuentasTransitoriasEfectivo;

import java.math.BigDecimal;

public class DivisaBean {
    String claveAlfabetica;
    BigDecimal idDivisa;
    String nombreCortoCustodio;
    BigDecimal idCustodio;
    BigDecimal registros;
    BigDecimal total;
    boolean montoNegativo;


    public DivisaBean(String claveAlfabetica, String nombreCortoCustodio, BigDecimal total) {
        this.claveAlfabetica = claveAlfabetica;
        this.nombreCortoCustodio = nombreCortoCustodio;
        this.total = total;
        this.montoNegativo = (total.intValue() < 0);
    }

    public DivisaBean(String claveAlfabetica, BigDecimal idDivisa, String nombreCortoCustodio, BigDecimal idCustodio,BigDecimal total) {
        this.claveAlfabetica = claveAlfabetica;
        this.idDivisa = idDivisa;
        this.nombreCortoCustodio = nombreCortoCustodio;
        this.idCustodio = idCustodio;
        this.total = total;
        this.montoNegativo = (total.intValue() < 0);
    }

    public DivisaBean(String claveAlfabetica, String nombreCortoCustodio, BigDecimal registros, BigDecimal total) {
        this.claveAlfabetica = claveAlfabetica;
        this.nombreCortoCustodio = nombreCortoCustodio;
        this.registros = registros;
        this.total = total;
        this.montoNegativo = (total.intValue() < 0);
    }




    @Override
    public String toString() {
        return "DivisaBean{" +
                "claveAlfabetica='" + claveAlfabetica + '\'' +
                ", idDivisa='" + idDivisa + '\'' +
                ", nombreCortoCustodio='" + nombreCortoCustodio + '\'' +
                ", idCustodio='" + idCustodio + '\'' +
                ", registros=" + registros +
                ", total=" + total +
                ", montoNegativo=" + montoNegativo +
                '}';
    }
}
