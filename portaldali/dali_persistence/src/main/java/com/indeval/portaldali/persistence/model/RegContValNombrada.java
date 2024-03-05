/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Tabla que contiene las acciones (cargos y abonos) que se realizaron sobre las
 * cuentas nombradas de valores.
 * 
 * T_REG_CONTABLE_VAL_NOMBRADA
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "T_REG_CONTABLE_VAL_NOMBRADA")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_REG_CONTABLE_VAL_NOMBRAD", allocationSize = 1, initialValue = 1)
public class RegContValNombrada implements Serializable {

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
     * Posici&oacute;n
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_POSICION", updatable = false, insertable = false)
    private PosicionNombrada posicion;

    /**
     * @deprecated use posicion
     * Identificador de la posici&oacute;n afectada.
     */
    @Column(name = "ID_POSICION", updatable = false)
    private BigInteger idPosicion;

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
     * Ciclo Operaci&oacute;n
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CICLO", updatable = false, insertable = false)
    private Ciclo ciclo;

    /**
     * @deprecated use ciclo
     * Identificador del ciclo durante el cual se realizo la afectaci&oacute;n.
     */
    @Column(name = "ID_CICLO", updatable = false)
    private BigInteger idCiclo;

    /**
     * Operaci&oacute;n
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_OPERACION", updatable = false, insertable = false)
    private OperacionNombrada operacion;

    /**
     * @deprecated use operacion
     * Identificador de la operaci&oacute;n que origino la afectaci&oacute;n.
     */
    @Column(name = "ID_OPERACION", updatable = false)
    private BigInteger idOperacion;

    /**
     * Identificador de la cuenta de la posici&oacute;n afectada.
     */
    @Column(name = "ID_CUENTA", updatable = false)
    private BigInteger idCuenta;

    /**
     * Identifica si se afecto el disponible o el no disponible de la
     * posici&oacute;n.
     */
    @Column(name = "CARGO_A", updatable = false)
    private BigInteger cargoA;

    /**
     * Identificador del registro contable de controladas donde participo este
     * registro.
     */
    @Column(name = "ID_REG_CONTABLE_CONTROLADA", updatable = false)
    private BigInteger idRegContableControlada;

    /**
     * 
     * Constructor de la clase RegContValNombrada
     * 
     */
    public RegContValNombrada() {

    }

    /**
     * Constructor
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
     * @param idOperacion
     *            Identificador de la operaci&oacute;n que genera el registro
     *            contable
     * @param idCuenta
     *            Identificador de la cuenta nombrada que se afectara
     * @param cargoA
     *            Indica si se carga al disponible (1) o al no disponible (2)
     * @param idRegContableControlada
     *            Identificador del registro contable de controlada
     */
    public RegContValNombrada(BigInteger idPosicion, TipoAccion tipoAccion, BigInteger cantidad,
            Date fecha, BigInteger idCiclo, BigInteger idOperacion, BigInteger idCuenta, BigInteger cargoA,
            BigInteger idRegContableControlada) {
        this.idPosicion = idPosicion;
        this.tipoAccion = tipoAccion;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.idCiclo = idCiclo;
        this.idOperacion = idOperacion;
        this.idCuenta = idCuenta;
        this.cargoA = cargoA;
        this.idRegContableControlada = idRegContableControlada;
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
     * Identificador de la operaci&oacute;n que origina el registro.
     * 
     * @return BigInteger
     */
    public BigInteger getIdOperacion() {
        return idOperacion;
    }

    /**
     * Identificador de la operaci&oacute;n que origina el registro.
     * 
     * @param idOperacion
     */
    public void setIdOperacion(BigInteger idOperacion) {
        this.idOperacion = idOperacion;
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
     * Indica si el cargo se hace al disponible o al no disponible.
     * 
     * @return BigInteger
     */
    public BigInteger getCargoA() {
        return cargoA;
    }

    /**
     * Indica si el cargo se hace al disponible o al no disponible.
     * 
     * @param cargoA
     */
    public void setCargoA(BigInteger cargoA) {
        this.cargoA = cargoA;
    }

    /**
     * @return the idRegContableControlada
     */
    public BigInteger getIdRegContableControlada() {
        return idRegContableControlada;
    }

    /**
     * @param idRegContableControlada
     *            the idRegContableControlada to set
     */
    public void setIdRegContableControlada(BigInteger idRegContableControlada) {
        this.idRegContableControlada = idRegContableControlada;
    }

    /**
     * Obtiene el valor del atributo posicion
     *
     * @return el valor del atributo posicion
     */
    public PosicionNombrada getPosicion() {
        return posicion;
    }

    /**
     * Establece el valor del atributo posicion
     *
     * @param posicion el valor del atributo posicion a establecer.
     */
    public void setPosicion(PosicionNombrada posicion) {
        this.posicion = posicion;
    }

    /**
     * Obtiene el valor del atributo operacion
     *
     * @return el valor del atributo operacion
     */
    public OperacionNombrada getOperacion() {
        return operacion;
    }

    /**
     * Establece el valor del atributo operacion
     *
     * @param operacion el valor del atributo operacion a establecer.
     */
    public void setOperacion(OperacionNombrada operacion) {
        this.operacion = operacion;
    }

    /**
     * Obtiene el valor del atributo ciclo
     *
     * @return el valor del atributo ciclo
     */
	public Ciclo getCiclo() {
		return ciclo;
	}

	/**
     * Establece el valor del atributo ciclo
     *
     * @param operacion el valor del atributo ciclo a establecer.
     */
	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ("idRegistroContable ==>" + idRegistroContable + "\n" + "idPosicion ==>"
                + idPosicion + "\n" + "tipoAccion ==>" + tipoAccion + "\n" + "cantidad ==>"
                + cantidad + "\n" + "fecha ==>" + fecha + "\n" + "idCuenta==>" + idCuenta + "\n"
                + "idCiclo ==>" + idCiclo + "\n" + "cargoA ==>" + cargoA);
    }

    /**
     * @see java.lang.Object#hashCode()
     * @return Devuelve el hashCode de las variables idRegistroContable y
     *         idSaldo, de un objeto RegContValNombrada.
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(43, 53).append(idRegistroContable).append(idPosicion)
                .toHashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj
     *            El objeto RegContValNombrada a compearar
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RegContValNombrada)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RegContValNombrada rhs = (RegContValNombrada) obj;
        return new EqualsBuilder().append(idRegistroContable, rhs.getIdRegistroContable())
                .isEquals();
    }

}
