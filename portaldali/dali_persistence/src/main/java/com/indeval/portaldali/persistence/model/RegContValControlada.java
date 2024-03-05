/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Tabla que contiene los registros contables correspondientes a los movimientos
 * o acciones que se realizaron sobre las cuentas controladas de valores.
 * 
 * T_REG_CONTABLE_VAL_CONTROL
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "T_REG_CONTABLE_VAL_CONTROL")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_REG_CONTABLE_VAL_CONTROL", allocationSize = 1, initialValue = 1)
public class RegContValControlada implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la acci&oacute;n.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_REGISTRO_CONTABLE")
    private BigInteger idRegistroContable;

    /**
     * Identificador de la posici&oacute;n afectada.
     */
    @Column(name = "ID_POSICION", updatable = false)
    private BigInteger idPosicion;

    /**
     * La posición controlada relacionada con este registro contable
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_POSICION", updatable = false, insertable = false)
    private PosicionControlada posicionControlada;

    /**
     * Tipo de acci&oacutel;n aplicada a la posici&oacute;n.
     */
    @Column(name = "ID_TIPO_ACCION", updatable = false)
    private TipoAccion tipoAccion;

    /**
     * Cantidad aplicada a la posici&oacute;n.
     */
    @Column(name = "CANTIDAD", updatable = false)
    private BigInteger cantidad;

    /**
     * Fecha en que se afecto la posici&oacute;n.
     */
    @Column(name = "FECHA", updatable = false)
    private Date fecha;

    /**
     * Identificador del ciclo durante el cual se realizo la afectaci&oacute;n.
     */
    @Column(name = "ID_CICLO", updatable = false)
    private BigInteger idCiclo;

    /**
     * Identificador de la cuenta de la posici&oacute;n afectada.
     */
    @Column(name = "ID_CUENTA", updatable = false)
    private BigInteger idCuenta;

    /**
     * Identificador de la instituci&oacute;n.
     */
    @Column(name = "ID_INSTITUCION", updatable = false)
    private BigInteger idInstitucion;

    /**
     * Tipo de registro contable.
     */
    @Enumerated
    @Column(name = "ID_TIPO_REGISTRO_CONTABLE", updatable = false)
    private TipoRegistroContableControlada tipoRegistroContableControlada;

    /**
     * Lista de operaciones que conforman el registro contable.
     */
    @ManyToMany(targetEntity = com.indeval.portaldali.persistence.model.OperacionNombrada.class, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "R_REG_CONT_CONT_VAL_OP", joinColumns = {
        @JoinColumn(name = "ID_REGISTRO_CONTABLE") }, inverseJoinColumns = {
        @JoinColumn(name = "ID_OPERACION_NOMBRADA") })
    private Set<OperacionNombrada> operaciones;

    /**
     * 
     * Constructor de la clase RegContValControlada
     * 
     */
    public RegContValControlada() {

    }

    /**
     * Constructor.
     * 
     * @param idPosicion
     *            Identificador de la posici&oacute;n que esta&acute; siendo afectada
     * @param tipoAccion
     *            Tipo de acci&oacute;n a realizar. Puede ser un cargo o un
     *            abono
     * @param cantidad
     *            número de títulos a registrar
     * @param fecha
     *            Fecha en que se realiza el registro contable
     * @param idCiclo
     *            Identificador del ciclo que genera el registro contable
     * @param idCuenta
     *            Identificador de la cuenta controlada que se afectara
     * @param idInstitucion
     *            Identificador de la institucion
     * @param tipoRegCont
     *            Tipo del registro contable
     */
    public RegContValControlada(BigInteger idPosicion, TipoAccion tipoAccion, BigInteger cantidad,
            Date fecha, BigInteger idCiclo, BigInteger idCuenta, BigInteger idInstitucion,
            TipoRegistroContableControlada tipoRegCont) {
        this.idPosicion = idPosicion;
        this.tipoAccion = tipoAccion;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.idCiclo = idCiclo;
        this.idCuenta = idCuenta;
        this.idInstitucion = idInstitucion;
        this.tipoRegistroContableControlada = tipoRegCont;
    }

    /**
     * Identificador del movimiento.
     * 
     * @return BigInteger
     */
    public BigInteger getIdRegistroContable() {
        return idRegistroContable;
    }

    /**
     * Obtiene el valor del atributo posicionControlada
     * 
     * @return el valor del atributo posicionControlada
     */
    public PosicionControlada getPosicionControlada() {
        return posicionControlada;
    }

    /**
     * Establece el valor del atributo posicionControlada
     * 
     * @param posicionControlada
     *            el valor del atributo posicionControlada a establecer
     */
    public void setPosicionControlada(PosicionControlada posicionControlada) {
        this.posicionControlada = posicionControlada;
    }

    /**
     * Identificador del movimiento.
     * 
     * @param idRegistroContable
     */
    public void setIdRegistroContable(BigInteger idRegistroContable) {
        this.idRegistroContable = idRegistroContable;
    }

    /**
     * Identificador de la posici&oacute;n afectada en la posici&oacute;n
     * nombrada.
     * 
     * @return BigInteger
     */
    public BigInteger getIdPosicion() {
        return idPosicion;
    }

    /**
     * Identificador de la posici&oacute;n afectada en la posici&oacute;n
     * nombrada.
     * 
     * @param idPosicion
     */
    public void setIdPosicion(BigInteger idPosicion) {
        this.idPosicion = idPosicion;
    }

    /**
     * Identificador del tipo de movimiento.
     * 
     * @return TipoAccion
     */
    public TipoAccion getTipoAccion() {
        return tipoAccion;
    }

    /**
     * Identificador del tipo de movimiento.
     * 
     * @param tipoAccion
     */
    public void setTipoAccion(TipoAccion tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    /**
     * Cantidad de títulos que se movieron.
     * 
     * @return BigInteger
     */
    public BigInteger getCantidad() {
        return cantidad;
    }

    /**
     * Cantidad de títulos que se movieron.
     * 
     * @param cantidad
     */
    public void setCantidad(BigInteger cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Fecha en la que se realiz&oacute; el movimiento.
     * 
     * @return Date
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Fecha en la que se realiz&oacute; el movimiento.
     * 
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Identificador del ciclo de liquidaci&oacute;n.
     * 
     * @return the idCiclo
     */
    public BigInteger getIdCiclo() {
        return idCiclo;
    }

    /**
     * Identificador del ciclo de liquidaci&oacute;n.
     * 
     * @param idCiclo
     *            the idCiclo to set
     */
    public void setIdCiclo(BigInteger idCiclo) {
        this.idCiclo = idCiclo;
    }

    /**
     * Cuenta nombrada afectada en el registro contable.
     * 
     * @return the cuenta
     */
    public BigInteger getIdCuenta() {
        return idCuenta;
    }

    /**
     * Cuenta nombrada afectada en el registro contable.
     * 
     * @param idCuenta
     *            the cuentaNombrada to set
     */
    public void setIdCuenta(BigInteger idCuenta) {
        this.idCuenta = idCuenta;
    }

    /**
     * Lista de operaciones que conforman el registro contable.
     * 
     * @return the operaciones
     */
    public Set<OperacionNombrada> getOperaciones() {
        return operaciones;
    }

    /**
     * Lista de operaciones que conforman el registro contable.
     * 
     * @param operaciones
     */
    public void setOperaciones(Set<OperacionNombrada> operaciones) {
        this.operaciones = operaciones;
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
     * @return the tipoRegistroContableControlada
     */
    public TipoRegistroContableControlada getTipoRegistroContableControlada() {
        return tipoRegistroContableControlada;
    }

    /**
     * @param tipoRegistroContableControlada
     *            the tipoRegistroContableControlada to set
     */
    public void setTipoRegistroContableControlada(
            TipoRegistroContableControlada tipoRegistroContableControlada) {
        this.tipoRegistroContableControlada = tipoRegistroContableControlada;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "idRegistroContable ==>" + idRegistroContable + "\n" + "idPosicion ==>" + idPosicion
                + "\n" + "tipoAccion ==>" + tipoAccion + "\n" + "cantidad ==>" + cantidad + "\n"
                + "fecha ==>" + fecha + "\n" + "idCuenta==>" + idCuenta + "\n" + "idCiclo ==>"
                + idCiclo;
    }

    /**
     * @see java.lang.Object#hashCode()
     * @return Devuelve el hashCode de las variables idRegistroContable y
     * idSaldo, de un objeto RegContValControlada.
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(43, 53).append(idRegistroContable).append(idPosicion)
                .toHashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj El objeto RegContValControlada a compearar
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RegContValControlada)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RegContValControlada rhs = (RegContValControlada) obj;
        return new EqualsBuilder().append(idRegistroContable, rhs.getIdRegistroContable())
                .isEquals();
    }
	
}