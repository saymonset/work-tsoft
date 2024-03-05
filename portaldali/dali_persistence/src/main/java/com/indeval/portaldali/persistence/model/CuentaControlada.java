/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

 /**
 * Cata&acute;logo que contiene las cuentas controladas de valores y efectivo.
 * Esta tabla requiere de una validación por medio de un trigger,
 * de manera que cuando se modifiquen los campos ID_CUENTA_ESTADO y FECHA_BAJA,
 * no se proporcionen datos contradictorios, por ejemplo un estado
 * inhabilitado y al mismo tiempo una fecha de baja.
 *
 * C_CUENTA_CONTROLADA
 *
 * @author rchavez
 * @version 1.0
 */
 @Entity
 @Table(name="C_CUENTA_CONTROLADA")
public class CuentaControlada implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la cuenta controlada.
	 */
	@Id
	@Column(name = "ID_CUENTA_CONTROLADA")
	private BigInteger idCuentaControlada;
	/**
	 * N&uacute;mero de la cuenta.
	 * Formalmente el n&uacute;mero de la cuenta se forma por el tipo de instituci&oacute;n mas el
	 * folio de instituci&oacute;n mas este n&uacute;mero de cuenta.
	 * Este identificador en conjunto con la instituci&oacute;n deben ser &uacute;nicos.
	 */
	@Column(name = "CUENTA")
	private String cuenta; 
	/**
	 * C&oacute;digo Internacional de cuenta Bancaria.
	 */
	@Column(name = "IBAN")
	private String iban;

	/**
	 * Instituci&oacute;n
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTITUCION", updatable = false, insertable = false)
	private Institucion institucion;

	/**
	 * Identificador de la instituci&oacute;n. 
	 * Este identificador en conjunto con la cuenta deben ser &uacute;nicos.
	 */
	@Column(name = "ID_INSTITUCION")
	private BigInteger idInstitucion;
	/**
	 * Identificador del tipo de cuenta.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_CUENTA", updatable = false, insertable = false)
	private TipoCuenta tipoCuenta;
	/**
	 * Identificador del tipo de cuenta.
	 */
	@Column(name = "ID_TIPO_CUENTA")
	private BigInteger idTipoCuenta;
	/**
	 * Raz&oacute;n social de la cuenta.
	 */
	@Column(name = "NOMBRE_CUENTA")
	private String nombreCuenta;
	/**
	 * Fecha de alta de la cuenta.
	 */
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta; 
	/**
	 * Estado de la cuenta.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_ESTADO")
	private EstadoCuenta estadoCuenta;
	/**
	 * Fecha de baja de la cuenta.
	 */
	@Column(name = "FECHA_BAJA")
	private Date fechaBaja;
	@Column(name ="id_sector", unique = false, nullable = true)
	private BigDecimal idSector;

	/**
	 * Identificador secuencial de la Cuenta.
	 * @return BigInteger
	 */
	public BigInteger getIdCuentaControlada() {
        return idCuentaControlada;
    }

	/**
	 * @param idCuentaControlada
	 */
	public void setIdCuentaControlada(BigInteger idCuentaControlada) {
		this.idCuentaControlada = idCuentaControlada;
    }

	/**
	 * Clave de la Cuenta.
	 * @return String
	 */
	public String getCuenta() {
		return cuenta;
    }

	/**
	 * @param cuenta
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
    }

	/**
	 * Identificador internacional de cuenta (Homologación con IBAN ISO-13616).
	 * @return String
	 */
	public String getIban() {
		return iban;
    }

	/**
	 * @param iban
	 */
	public void setIban(String iban) {
		this.iban = iban;
    }

	/**
	 * @return the institucion
	 */
	public Institucion getInstitucion() {
		return institucion;
    }

    /**
	 * @param institucion the institucion to set
     */
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
    }

    /**
	 * Referencia a la Institución a la que pertenece la Cuenta.
	 * @return long
     */
	public BigInteger getIdInstitucion() {
		return idInstitucion;
    }

    /**
	 * @param idInstitucion
     */
	public void setIdInstitucion(BigInteger idInstitucion) {
		this.idInstitucion = idInstitucion;
    }

    /**
	 * Referencia al Tipo de Cuenta asociado.
	 * @return TipoCuenta
     */
	public TipoCuenta getTipoCuenta() {
		return tipoCuenta;
    }

    /**
	 * Referencia al Tipo de Cuenta asociado.
	 * @param tipoCuenta
     */
    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    /**
	 * Nombre de la Cuenta
	 * @return String
	 */
	public String getNombreCuenta() {
		return nombreCuenta;
	}

	/**
     * @param nombreCuenta
     */
    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    /**
	 * Fecha en la que se hace el alta de la Cuenta.
	 * @return Date
	 */
	public Date getFechaAlta() {
		return fechaAlta;
	}

	/**
     * @param fechaAlta
     */
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    /**
	 * Referencia al Estado de la Cuenta.
	 * @return long
	 */
	public EstadoCuenta getCuentaEstado() {
		return estadoCuenta;
	}

	/**
	 * @param estadoCuenta
     */
    public void setCuentaEstado(EstadoCuenta estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    /**
	 * Fecha en la que se hace la baja lógica de la Cuenta.
	 * @return Date
	 */
	public Date getFechaBaja() {
		return fechaBaja;
	}

	/**
     * @param fechaBaja
     */
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    /**
	 * @return the estadoCuenta
	 */
	public EstadoCuenta getEstadoCuenta() {
		return estadoCuenta;
	}

	/**
	 * @param estadoCuenta the estadoCuenta to set
	 */
	public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}

	/**
	 * @return the idTipoCuenta
	 */
	public BigInteger getIdTipoCuenta() {
		return idTipoCuenta;
	}

	/**
	 * @param idTipoCuenta the idTipoCuenta to set
	 */
	public void setIdTipoCuenta(BigInteger idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
	}
	
	
    public BigDecimal getIdSector() {
        return idSector;
    }
	
	/**
     * @param idSector
     */
    public void setIdSector(BigDecimal idSector) {
        this.idSector = idSector;
}

	/**
	 * @see java.lang.Object#hashCode()
     */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(47, 77).append(idCuentaControlada).toHashCode();
    }
    
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	   if (!(obj instanceof CuentaControlada)) {
		     return false;
	   }
	   if (this == obj) {
		     return true;
	   }
	   CuentaControlada rhs = (CuentaControlada) obj;
	   return new EqualsBuilder()
	   		.append(idCuentaControlada, rhs.getIdCuentaControlada())
			.isEquals();
	}
}
