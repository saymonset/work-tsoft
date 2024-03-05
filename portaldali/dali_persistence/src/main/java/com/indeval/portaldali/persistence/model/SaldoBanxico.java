/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tabla que indica los saldos de Banxico (T_SALDOS_BANXICO).
 * 
 * @author RCHAVEZ
 * @version 1.0
 */
@Entity
@Table(name = "T_SALDO_BANXICO")
@Deprecated
public class SaldoBanxico implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la instituci&oacute;n.
     */
    @Id
    @Column(name = "ID_INSTITUCION")
    private BigInteger idInstitucion;

    /**
     * Identificador de la cuenta controlada.
     */
    @Column(name = "ID_CUENTA_CONTROLADA")
    private BigInteger idCuentaControlada;

    /**
     * Identificador de la cuenta controlada.
     */
    @Column(name = "SALDO")
    private BigDecimal saldo;

    /**
     * @return the idCuentaControlada
     */
    public BigInteger getIdCuentaControlada() {
        return idCuentaControlada;
    }

    /**
     * @param idCuentaControlada
     *            the idCuentaControlada to set
     */
    public void setIdCuentaControlada(BigInteger idCuentaControlada) {
        this.idCuentaControlada = idCuentaControlada;
    }

    /**
     * @return the idInstitucion
     */
    public BigInteger getIdInstitucion() {
        return idInstitucion;
    }

    /**
     * @param idInstitucion
     *            the idInstitucion to set
     */
    public void setIdInstitucion(BigInteger idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    /**
     * @return the saldo
     */
    public BigDecimal getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
