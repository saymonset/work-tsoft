/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Clase que contiene los saldos por instituci&oacute;n que devuelve Banxico
 * posterior a una liquidaci&oacute;n de cargos y abonos o una
 * liquidaci&oacute;n de retiros.
 * 
 * @author rchavez
 */
@Entity
@Table(name = "T_RESPUESTA_SALDO_BANXICO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_RESPUESA_SALDO_BANXICO", allocationSize = 1, initialValue = 1)
public class RespuestaSaldoBanxico implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la respuesta de saldos de Banxico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_RESPUESTA_SALDO_BANXICO")
    private BigInteger idRespuestaSaldoBanxico;

    /**
     * Clave CASFIM de la instituci&oacute;n que se afecto el saldo.
     */
    @Column(name = "CLAVE_CASFIM")
    private String claveCasfim;

    /**
     * Identificador del ciclo durante el que se afecto el saldo.
     */
    @Column(name = "ID_CICLO")
    private BigInteger idCiclo;

    /**
     * Saldo proporcionado por Banxico.
     */
    @Column(name = "SALDO")
    private BigDecimal saldo;

    /**
     * Tipo de ciclo en que se afecto (Cargo-Abono o Retiro)
     */
    @Column(name = "TIPO_CICLO")
    private String tipoCiclo;

    /**
     * Constructor default.
     */
    public RespuestaSaldoBanxico() {

    }

    /**
     * Constructor.
     * 
     * @param claveCasfim
     * @param idCiclo
     * @param saldo
     * @param tipoCiclo
     */
    public RespuestaSaldoBanxico(String claveCasfim, BigInteger idCiclo, BigDecimal saldo,
            String tipoCiclo) {
        this.claveCasfim = claveCasfim;
        this.idCiclo = idCiclo;
        this.saldo = saldo;
        this.tipoCiclo = tipoCiclo;
    }

    /**
     * @return the idCiclo
     */
    public BigInteger getIdCiclo() {
        return idCiclo;
    }

    /**
     * @param idCiclo
     *            the idCiclo to set
     */
    public void setIdCiclo(BigInteger idCiclo) {
        this.idCiclo = idCiclo;
    }

    /**
     * @return the claveCasfim
     */
    public String getClaveCasfim() {
        return claveCasfim;
    }

    /**
     * @param claveCasfim
     *            the claveCasfim to set
     */
    public void setClaveCasfim(String claveCasfim) {
        this.claveCasfim = claveCasfim;
    }

    /**
     * @return the idRespuestaSaldoBanxico
     */
    public BigInteger getIdRespuestaSaldoBanxico() {
        return idRespuestaSaldoBanxico;
    }

    /**
     * @param idRespuestaSaldoBanxico
     *            the idRespuestaSaldoBanxico to set
     */
    public void setIdRespuestaSaldoBanxico(BigInteger idRespuestaSaldoBanxico) {
        this.idRespuestaSaldoBanxico = idRespuestaSaldoBanxico;
    }

    /**
     * @return the saldo
     */
    public BigDecimal getSaldo() {
        return saldo;
    }

    /**
     * @param saldo
     *            the saldo to set
     */
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    /**
     * @return the tipoCiclo
     */
    public String getTipoCiclo() {
        return tipoCiclo;
    }

    /**
     * @param tipoCiclo the tipoCiclo to set
     */
    public void setTipoCiclo(String tipoCiclo) {
        this.tipoCiclo = tipoCiclo;
    }
}
