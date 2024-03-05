/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Este cata&acute;logo contiene la caracterización de las instrucciones segu&acute;n su tipo.
 * 
 * C_TIPO_INSTRUCCION
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "C_TIPO_INSTRUCCION")
public class TipoInstruccion implements Serializable {
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador del tipo de instruccio&acute;n.
     */
    @Id
    @Column(name = "ID_TIPO_INSTRUCCION")
    private BigInteger idTipoInstruccion;

    /**
     * Nombre corto del tipo de instruccio&acute;n.
     */
    @Column(name = "NOMBRE_CORTO")
    private String nombreCorto;

    /**
     * Nombre medio
     */
    @Column(name = "INSTRUCCION")
    private String instruccion;

    /**
     * Descripcio&acute;n del tipo de instruccio&acute;n.
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Indica si la instruccio&acute;n es suceptible de ser enviada a la compensacio&acute;n.
     */
    @Column(name = "INSTRUCCION_COMPENSABLE")
    private boolean instruccionCompensable;

    /**
     * Indica si la instruccio&acute;n requiere usar una cuenta de efectivo.
     */
    @Column(name = "CUENTA_EFECTIVO")
    private boolean cuentaEfectivoCLV;

    /**
     * Indica si la instruccio&acute;n es suceptible de ser enviada al match.
     */
    @Column(name = "REQUIERE_MATCH")
    private boolean requiereMatch;

    /**
     * Identificador secuencial de la institucio&acute;n.
     * 
     * @return long
     */
    public BigInteger getIdTipoInstruccion() {
        return idTipoInstruccion;
    }

    /**
     * Identificador secuencial de la institucio&acute;n.
     * 
     * @param idTipoInstruccion
     */
    public void setIdTipoInstruccion(BigInteger idTipoInstruccion) {
        this.idTipoInstruccion = idTipoInstruccion;
    }

    /**
     * Nombre corto de la instruccio&acute;n.
     * 
     * @return String
     */
    public String getNombreCorto() {
        return nombreCorto;
    }

    /**
     * Nombre corto de la instruccio&acute;n.
     * 
     * @param nombreCorto
     */
    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    /**
     * Descripcio&acute;n del tipo de instruccio&acute;n.
     * 
     * @return String
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Descripcio&acute;n del tipo de instruccio&acute;n.
     * 
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Indica si la instruccio&acute;n requiere pasar por el proceso de compensacio&acute;n.
     * 
     * @return boolean
     */
    public boolean isInstruccionCompensable() {
        return instruccionCompensable;
    }

    /**
     * Indica si la instruccio&acute;n requiere pasar por el proceso de compensacio&acute;n.
     * 
     * @param instruccionCompensable
     */
    public void setInstruccionCompensable(boolean instruccionCompensable) {
        this.instruccionCompensable = instruccionCompensable;
    }

    /**
     * Indica si opera con cuentas de efectivo CLV.
     * 
     * @return boolean
     */
    public boolean isCuentaEfectivoCLV() {
        return cuentaEfectivoCLV;
    }

    /**
     * Indica si opera con cuentas de efectivo CLV.
     * 
     * @param cuentaEfectivoCLV
     */
    public void setCuentaEfectivoCLV(boolean cuentaEfectivoCLV) {
        this.cuentaEfectivoCLV = cuentaEfectivoCLV;
    }

    /**
     * Indica si la instruccio&acute;n requiere match.
     * 
     * @return boolean
     */
    public boolean isRequiereMatch() {
        return requiereMatch;
    }

    /**
     * Indica si la instruccio&acute;n requiere match.
     * 
     * @param requiereMatch
     */
    public void setRequiereMatch(boolean requiereMatch) {
        this.requiereMatch = requiereMatch;
    }

    /**
     * Obtiene el campo instruccion
     * @return  instruccion
     */
    public String getInstruccion() {
        return instruccion;
    }

    /**
     * Asigna el valor del campo instruccion
     * @param instruccion el valor de instruccion a asignar
     */
    public void setInstruccion(String instruccion) {
        this.instruccion = instruccion;
    }

}
