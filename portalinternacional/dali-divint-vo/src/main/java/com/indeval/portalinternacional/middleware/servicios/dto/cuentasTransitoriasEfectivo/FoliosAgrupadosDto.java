package com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo;


import java.math.BigDecimal;
import java.util.List;

public class FoliosAgrupadosDto {
    private String divisa;
    private BigDecimal monto;

    /**
     * Indica si el monto TOTAL de los movimientos es NEGATIVO
     */
    private boolean montoNegativo;
    private List<FolioAgrupadoDto> referencias;

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public boolean isMontoNegativo() {
        return montoNegativo;
    }

    public void setMontoNegativo(boolean montoNegativo) {
        this.montoNegativo = montoNegativo;
    }

    public List<FolioAgrupadoDto> getReferencias() {
        return referencias;
    }

    public void setReferencias(List<FolioAgrupadoDto> referencias) {
        this.referencias = referencias;
    }

    @Override
    public String toString() {
        return "FoliosAgrupadosDto{" +
                "divisa='" + divisa + '\'' +
                ", monto=" + monto + '\'' +
                ", montoNegativo=" + montoNegativo + '\'' +
                ", referencias=" + referencias + '\'' +

                '}';
    }
}
