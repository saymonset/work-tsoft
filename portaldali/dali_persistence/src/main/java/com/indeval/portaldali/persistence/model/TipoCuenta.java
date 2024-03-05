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
 * Cata&acute;logo que contiene subclasificaciones de las cuentas: propia, terceros,
 * garanti&acute;as propia, garantías terceros, caucio&acute;n, etc.
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "C_TIPO_CUENTA")
public class TipoCuenta implements Serializable {
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador del tipo de cuenta.
     */
    @Id
    @Column(name = "ID_TIPO_CUENTA")
    private BigInteger idTipoCuenta;

    /**
     * Clave del tipo de cuenta.
     */
    @Column(name = "CLAVE_TIPO_CUENTA")
    private String claveTipoCuenta;

    /**
     * Descripci&oacute;n del tipo de cuenta.
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Naturaleza por el proceso de liquidacio&acute;n. N(Nombradas), C(Controladas)
     */
    @Column(name = "NATURALEZA_PROC_LIQ")
    private String naturalezaProcesoLiquidacion;

    /**
     * Naturaleza contable. P(Pasivo), A (Activo)
     */
    @Column(name = "NATURALEZA_CONTABLE")
    private String naturalezaContable;

    /**
     * Naturaleza Legal. P(Propia), T(Terceros)
     */
    @Column(name = "NATURALEZA_LEGAL")
    private String naturalezaLegal;

    /**
     * Tipo de tenencia. E(EmisionPersistence), C(Circulacion)
     */
    @Column(name = "TIPO_TENENCIA")
    private String tipoTenencia;

    /**
     * Tipo de custodia. V(Valores), E(Efectivo)
     */
    @Column(name = "TIPO_CUSTODIA")
    private String tipoCustodia;

    /**
     * Tipo de administraci&oacute;n. O(Obligatoria), N(No Obligatoria)
     */
    @Column(name = "TIPO_ADMINISTRACION")
    private String tipoAdministracion;

    /**
     * Tipo de administraci&oacute;n. O(Obligatoria), N(No Obligatoria)
     */
    @Column(name = "CLAVE_SUBGRUPO")
    private String claveSubgrupo;

   

    /**
     * Identificador del Tipo Cuenta.
     * 
     * @return long
     */
    public BigInteger getIdTipoCuenta() {
        return idTipoCuenta;
    }

    /**
     * Identificador del Tipo Cuenta.
     * 
     * @param idTipoCuenta
     */
    public void setIdTipoCuenta(BigInteger idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }

    /**
     * Clave del Tipo de Cuenta.
     * 
     * @return String
     */
    public String getClaveTipoCuenta() {
        return claveTipoCuenta;
    }

    /**
     * Clave del Tipo de Cuenta.
     * 
     * @param claveTipoCuenta
     */
    public void setClaveTipoCuenta(String claveTipoCuenta) {
        this.claveTipoCuenta = claveTipoCuenta;
    }

    /**
     * Descripcio&acute;n del Tipo de Cuenta.
     * 
     * @return String
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Descripcio&acute;n del Tipo de Cuenta.
     * 
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the naturalezaContable
     */
    public String getNaturalezaContable() {
        return naturalezaContable;
    }

    /**
     * @param naturalezaContable
     *            the naturalezaContable to set
     */
    public void setNaturalezaContable(String naturalezaContable) {
        this.naturalezaContable = naturalezaContable;
    }

    /**
     * @return the naturalezaLegal
     */
    public String getNaturalezaLegal() {
        return naturalezaLegal;
    }

    /**
     * @param naturalezaLegal
     *            the naturalezaLegal to set
     */
    public void setNaturalezaLegal(String naturalezaLegal) {
        this.naturalezaLegal = naturalezaLegal;
    }

    /**
     * @return the naturalezaProcesoLiquidacion
     */
    public String getNaturalezaProcesoLiquidacion() {
        return naturalezaProcesoLiquidacion;
    }

    /**
     * @param naturalezaProcesoLiquidacion
     *            the naturalezaProcesoLiquidacion to set
     */
    public void setNaturalezaProcesoLiquidacion(String naturalezaProcesoLiquidacion) {
        this.naturalezaProcesoLiquidacion = naturalezaProcesoLiquidacion;
    }

    /**
     * @return the tipoAdministracion
     */
    public String getTipoAdministracion() {
        return tipoAdministracion;
    }

    /**
     * @param tipoAdministracion
     *            the tipoAdministracion to set
     */
    public void setTipoAdministracion(String tipoAdministracion) {
        this.tipoAdministracion = tipoAdministracion;
    }

    /**
     * @return the tipoCustodia
     */
    public String getTipoCustodia() {
        return tipoCustodia;
    }

    /**
     * @param tipoCustodia
     *            the tipoCustodia to set
     */
    public void setTipoCustodia(String tipoCustodia) {
        this.tipoCustodia = tipoCustodia;
    }

    /**
     * @return the tipoTenencia
     */
    public String getTipoTenencia() {
        return tipoTenencia;
    }

    /**
     * @param tipoTenencia
     *            the tipoTenencia to set
     */
    public void setTipoTenencia(String tipoTenencia) {
        this.tipoTenencia = tipoTenencia;
    }

    /**
     * @return the claveSubgrupo
     */
    public String getClaveSubgrupo() {
        return claveSubgrupo;
    }

    /**
     * @param claveSubgrupo the claveSubgrupo to set
     */
    public void setClaveSubgrupo(String claveSubgrupo) {
        this.claveSubgrupo = claveSubgrupo;
    }

    

}
