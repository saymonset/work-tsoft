package com.indeval.portalinternacional.middleware.servicios.vo;

import java.math.BigDecimal;

public class ConsultaSaldoCustodiosTotalesInDTO {

    private String title;
    private BigDecimal totalSaldo = new BigDecimal(0);
    private BigDecimal totalDisponible = new BigDecimal(0);
    private BigDecimal totalNoDisponible = new BigDecimal(0);

    public BigDecimal getTotalSaldo() {
        return totalSaldo;
    }

    public void setTotalSaldo(BigDecimal totalSaldo) {
        this.totalSaldo = totalSaldo;
    }

    public BigDecimal getTotalDisponible() {
        return totalDisponible;
    }

    public void setTotalDisponible(BigDecimal totalDisponible) {
        this.totalDisponible = totalDisponible;
    }

    public BigDecimal getTotalNoDisponible() {
        return totalNoDisponible;
    }

    public void setTotalNoDisponible(BigDecimal totalNoDisponible) {
        this.totalNoDisponible = totalNoDisponible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
