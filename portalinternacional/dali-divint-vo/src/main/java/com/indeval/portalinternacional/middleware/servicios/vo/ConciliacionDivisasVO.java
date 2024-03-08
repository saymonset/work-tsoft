package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portalinternacional.middleware.servicios.dto.BovedaDto;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ConciliacionDivisasVO implements Serializable {
    private String bovedaDescription;

    private String divisaDescripcion;

    private String idConciliacionEfectivo;

    private BigDecimal montoCustodio;

    private BigDecimal montoIndeval;

    private BigDecimal montoDiferencia;

    public String getBovedaDescription() {
        return bovedaDescription;
    }

    public void setBovedaDescription(String bovedaDescription) {
        this.bovedaDescription = bovedaDescription;
    }

    public String getIdConciliacionEfectivo() {
        return idConciliacionEfectivo;
    }

    public void setIdConciliacionEfectivo(String idConciliacionEfectivo) {
        this.idConciliacionEfectivo = idConciliacionEfectivo;
    }

    public BigDecimal getMontoCustodio() {
        return montoCustodio;
    }

    public void setMontoCustodio(BigDecimal montoCustodio) {
        this.montoCustodio = montoCustodio;
    }

    public BigDecimal getMontoIndeval() {
        return montoIndeval;
    }

    public void setMontoIndeval(BigDecimal montoIndeval) {
        this.montoIndeval = montoIndeval;
    }

    public BigDecimal getMontoDiferencia() {
        return montoDiferencia;
    }

    public void setMontoDiferencia(BigDecimal montoDiferencia) {
        this.montoDiferencia = montoDiferencia;
    }

    public String getDivisaDescripcion() {
        return divisaDescripcion;
    }

    public void setDivisaDescripcion(String divisaDescripcion) {
        this.divisaDescripcion = divisaDescripcion;
    }
}
