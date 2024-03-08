package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_CONCILIACION_DIVISAS_INT")
@SequenceGenerator(name = "seqTConciliacionDivisasInt", sequenceName = "SEQ_T_CONCILIACION_DIVISAS_INT", allocationSize = 1)
public class ConciliacionDivisasInt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTConciliacionDivisasInt")
    @Column(name = "ID_T_CONCILIACION_DIVISAS_INT")
    private Long idConciliacionDivisasInt;

    @Column(name = "FECHA")
    private Date fecha;

    @Column(name = "ID_BOVEDA")
    private Integer idBoveda;

    @Column(name = "ID_DIVISA")
    private Integer idDivisa;

    @Column(name = "ID_CONCILIACION_EFECTIVO")
    private String idConciliacionEfectivo;

    @Column(name = "MONTO_CUSTODIO")
    private BigDecimal montoCustodio;

    @Column(name = "MONTO_INDEVAL")
    private BigDecimal montoIndeval;

    @Column(name = "MONTO_DIFERENCIA")
    private BigDecimal montoDiferencia;

/*
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="ID_CONCILIACION_EFECTIVO")
    private List<DetalleConciliacionEfectivoInt> listaDetalleConciliacionEfectivo;
*/

/*
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="ID_CONCILIACION_EFECTIVO")
    private List<BitacoraConciliacionEfectivoInt> listaBitacoraConciliacionEfectivo;
*/

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
