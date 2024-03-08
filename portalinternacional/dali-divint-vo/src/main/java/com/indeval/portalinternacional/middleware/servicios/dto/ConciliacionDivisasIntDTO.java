package com.indeval.portalinternacional.middleware.servicios.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class ConciliacionDivisasIntDTO implements Serializable {
    private Long idConciliacionDivisasInt;

    private Date fecha;

    private Integer idBoveda;

    private Integer idDivisa;

    private String idConciliacionEfectivo;

    private BigDecimal montoCustodio;

    private BigDecimal montoIndeval;

    private BigDecimal montoDiferencia;

    public Long getIdConciliacionDivisasInt() {
        return idConciliacionDivisasInt;
    }

    public void setIdConciliacionDivisasInt(Long idConciliacionDivisasInt) {
        this.idConciliacionDivisasInt = idConciliacionDivisasInt;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdBoveda() {
        return idBoveda;
    }

    public void setIdBoveda(Integer idBoveda) {
        this.idBoveda = idBoveda;
    }

    public Integer getIdDivisa() {
        return idDivisa;
    }

    public void setIdDivisa(Integer idDivisa) {
        this.idDivisa = idDivisa;
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
}
