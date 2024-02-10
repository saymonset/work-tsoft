package com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo;


import java.util.List;

public class FoliosAgrupadosDto {
    private String divisa;
    private String monto;

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

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
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
