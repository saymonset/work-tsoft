package com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BovedaMontosDto extends FolioAgrupadoDto implements Serializable {
    /**
     * Id para la serializacion
     */
    private static final long serialVersionUID = -2709202303152671022L;

    protected BigDecimal idBoveda;
    protected String boveda;
    protected BigDecimal saldoDisponible;
    protected BigDecimal saldoNoDisponible;
    protected BigDecimal saldoTotal;

    public BigDecimal getIdBoveda() {
        return idBoveda;
    }

    public void setIdBoveda(BigDecimal idBoveda) {
        this.idBoveda = idBoveda;
    }

    public String getBoveda() {
        return boveda;
    }

    public void setBoveda(String boveda) {
        this.boveda = boveda;
    }

    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public BigDecimal getSaldoNoDisponible() {
        return saldoNoDisponible;
    }

    public void setSaldoNoDisponible(BigDecimal saldoNoDisponible) {
        this.saldoNoDisponible = saldoNoDisponible;
    }

    public BigDecimal getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(BigDecimal saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    @Override
    public String toString() {
        return "BovedaDto{" +
                "idBoveda=" + idBoveda +
                ", boveda='" + boveda + '\'' +
                ", idCustodio='" + idCustodio + '\'' +
                ", custodio='" + custodio + '\'' +
                ", idDivisa='" + idDivisa + '\'' +
                ", divisa='" + divisa + '\'' +
                ", saldoDisponible=" + saldoDisponible +
                ", saldoNoDisponible=" + saldoNoDisponible +
                ", saldoTotal=" + saldoTotal +
                ", montoNegativo=" + montoNegativo +
                '}';
    }
}
