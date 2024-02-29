package com.indeval.portalinternacional.middleware.servicios.vo;

import java.math.BigDecimal;

public class ConsultaSaldoCustodiosTotalesInDTO {

    private boolean isConsultaPorPagina;
    private TituloPagina title;
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

    public TituloPagina getTitle() {
        return title;
    }

    public void setTitle(TituloPagina title) {
        this.title = title;
    }

    public boolean isConsultaPorPagina() {
        return isConsultaPorPagina;
    }

    public void setConsultaPorPagina(boolean consultaPorPagina) {
        isConsultaPorPagina = consultaPorPagina;
    }
}
