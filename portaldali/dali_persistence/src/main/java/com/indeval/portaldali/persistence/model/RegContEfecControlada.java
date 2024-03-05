/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * o acciones que se realizaron sobre las cuentas controladas de efectivo.
 * 
 * T_REG_CONTABLE_EFE_CONTROL
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "T_REG_CONTABLE_EFE_CONTROL")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_REG_CONTABLE_EFE_CONTROL", allocationSize = 1, initialValue = 1)
public class RegContEfecControlada implements Serializable {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor para la clase RegContEfecControlada.java
     */
    public RegContEfecControlada() {
    }

    /**
     * Identificador de la acci&oacute;n.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_REGISTRO_CONTABLE")
    private BigInteger idRegistroContable;

    /**
     * Identificador del saldo afectado.
     */
    @Column(name = "ID_SALDO", updatable = false)
    private BigInteger idSaldo;

    /**
     * Saldo afectado
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SALDO", updatable = false, insertable = false)
    private SaldoControlada saldo;

    /**
     * Tipo de acci&oacutel;n aplicada al saldo.
     */
    @Enumerated
    @Column(name = "ID_TIPO_ACCION", updatable = false)
    private TipoAccion tipoAccion;

    /**
     * Importe aplicado al saldo.
     */
    @Column(name = "IMPORTE", updatable = false)
    private BigDecimal importe;

    /**
     * Fecha en que se afecto el saldo.
     */
    @Column(name = "FECHA", updatable = false)
    private Date fecha;

    /**
     * Identificador del ciclo durante el cual se realizo la afectaci&oacute;n.
     */
    @Column(name = "ID_CICLO", updatable = false)
    private BigInteger idCiclo;

    /**
     * Ciclo en el cual se realizo el registro contable
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CICLO", updatable = false, insertable = false)
    private Ciclo ciclo;

    /**
     * Identificador de la cuenta del saldo afectado.
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
    @JoinTable(name = "R_REG_CONT_CONT_EFEC_OP", joinColumns = {
        @JoinColumn(name = "ID_REGISTRO_CONTABLE") }, inverseJoinColumns = {
        @JoinColumn(name = "ID_OPERACION_NOMBRADA") })
    private Set<OperacionNombrada> operaciones;

    /**
     * Constructor.
     * 
     * @param idSaldo
     *            Identificador del saldo que esta&acute; siendo afectado
     * @param tipoAccion
     *            Tipo de acci&oacute;n a realizar. Puede ser un cargo o un
     *            abono
     * @param importe
     *            Importe a registrar
     * @param fecha
     *            Fecha en que se realiza el registro contable
     * @param idCiclo
     *            Identificador del ciclo que genera el registro contable
     * @param idCuenta
     *            Identificador de la cuenta controlada que se afectara
     * @param idInstitucion
     *            Identificador de la instituci&oacute;n.
     * @param tipoRegCont
     *            Tipo del registro contable
     */
    public RegContEfecControlada(BigInteger idSaldo, TipoAccion tipoAccion, BigDecimal importe,
            Date fecha, BigInteger idCiclo, BigInteger idCuenta, BigInteger idInstitucion,
            TipoRegistroContableControlada tipoRegCont) {
        this.idSaldo = idSaldo;
        this.tipoAccion = tipoAccion;
        this.importe = importe;
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
     * Identificador del movimiento.
     * 
     * @param idRegistroContable
     */
    public void setIdRegistroContable(BigInteger idRegistroContable) {
        this.idRegistroContable = idRegistroContable;
    }

    /**
     * Identificador de la posici&oacute;n afectada en la cuenta nombrada.
     * 
     * @return BigInteger
     */
    public BigInteger getIdSaldo() {
        return idSaldo;
    }

    /**
     * Identificador de la posici&oacute;n afectada en la cuenta nombrada.
     * 
     * @param idSaldo
     */
    public void setIdSaldo(BigInteger idSaldo) {
        this.idSaldo = idSaldo;
    }

    /**
     * Obtiene el saldo afectado.
     * 
     * @return the saldo
     */
    public SaldoControlada getSaldo() {
        return saldo;
    }

    /**
     * Asigna el saldo afectado.
     * 
     * @param saldo
     *            the saldo to set
     */
    public void setSaldo(SaldoControlada saldo) {
        this.saldo = saldo;
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
     * Cantidad de efectivo que se movi&oacute;.
     * 
     * @return BigDecimal
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * Cantidad de efectivo que se movi&oacute;.
     * 
     * @param importe
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
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
        return "idRegistroContable ==>" + idRegistroContable + "\n" + "idSaldo ==>" + idSaldo
                + "\n" + "tipoAccion ==>" + tipoAccion + "\n" + "importe ==>" + importe + "\n"
                + "fecha ==>" + fecha + "\n" + "idCuenta==>" + idCuenta + "\n" + "idCiclo ==>"
                + idCiclo + "\n";
    }

    /**
     * @see java.lang.Object#hashCode()
     * @return Devuelve el hashCode de las variables idRegistroContable y
     *         idSaldo, de un objeto RegContEfecControlada.
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(43, 53).append(idRegistroContable).append(idSaldo).toHashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj El objeto RegContEfecControlada a compearar
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RegContEfecControlada)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RegContEfecControlada rhs = (RegContEfecControlada) obj;
        return new EqualsBuilder().append(idRegistroContable, rhs.getIdRegistroContable())
                .isEquals();
    }

    /**
     * Obtiene el atributo ciclo
     *
     * @return El atrubuto ciclo
     */
    public Ciclo getCiclo() {
        return ciclo;
    }

    /**
     * Establece la propiedad ciclo
     *
     * @param ciclo el campo ciclo a establecer
     */
    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

}