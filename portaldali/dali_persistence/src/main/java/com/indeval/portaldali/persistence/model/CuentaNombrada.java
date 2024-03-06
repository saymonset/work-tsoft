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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Cata&acute;logo que contiene las cuentas nombradas de valores y efectivo.
 * 
 * C_CUENTA_NOMBRADA
 *
 * @author rchavez
 * @version 1.0
 */
 @Entity
 @Table(name="C_CUENTA_NOMBRADA")
public class CuentaNombrada implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la cuenta nombrada.
	 */
	@Id
	@Column(name = "ID_CUENTA_NOMBRADA")
	private BigInteger idCuentaNombrada;
	/**
	 * N&uacute;mero de la cuenta.
	 * Formalmente el n&uacute;mero de la cuenta se forma por el tipo de instituci&oacute;n mas el
	 * folio de instituci&oacute;n mas este n&uacute;mero de cuenta.
	 * Este identificador en conjunto con la instituci&oacute;n deben ser &uacute;nicos.
	 */
	@Column(name = "CUENTA")
	private String cuenta;
	/**
	 * C&oacute;digo Internacional de Cuenta Bancaria.
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
	 * Tipo de cuenta.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
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
	@Column(name = "RAZON_SOCIAL_CUENTA")
	private String razonSocialCuenta;
	/**
	 * Nombre de la cuenta.
	 */
	@Column(name = "NOMBRE_CUENTA")
	private String nombreCuenta;
	/**
	 * Identificador del sector al que pertenece la cuenta.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SECTOR")
	private Sector sector;
	/**
	 * Fecha de alta de la cuenta.
	 */
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;
	/**
	 * Identificador del estado de la cuenta.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_ESTADO")
	private EstadoCuenta estadoCuenta;
	/**
	 * Fecha de baja de la cuenta.
	 */
	@Column(name = "FECHA_BAJA")
	private Date fechaBaja;
	/**
	 * Cuenta controlada a la que esta ligada la cuenta nombrada.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_CONTROLADA", updatable = false, insertable = false)
	private CuentaControlada cuentaControlada;
	/**
	 * Identificador de la cuenta controlada a la que esta ligada la cuenta nombrada.
	 */
	@Column(name = "ID_CUENTA_CONTROLADA")
	private BigInteger idCuentaControlada;

	/**
	 * Identificador secuencial de la Cuenta.
	 * @return BigInteger 
	 */
	public BigInteger getIdCuentaNombrada() {
        return idCuentaNombrada;
    }

	/**
	 * @param idCuentaNombrada
	 */
	public void setIdCuentaNombrada(BigInteger idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
	}

	/**
	 * Clave de la Cuenta
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
	 * Identificador internacional de cuenta (Homologación con IBAN ISO-13616)
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
	 * Referencia a la Institución a la que pertenece la Cuenta.
	 * @return BigInteger
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
	 * @return long
	 */
    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

	/**
	 * @param tipoCuenta
	 */
	public void setTipoCuenta(TipoCuenta tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	/**
	 * Descripción de la Cuenta
	 * @return String
	 */
    public String getRazonSocialCuenta() {
        return razonSocialCuenta;
    }

	/**
	 * @param razonSocialCuenta
	 */
	public void setRazonSocialCuenta(String razonSocialCuenta) {
		this.razonSocialCuenta = razonSocialCuenta;
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
	 * Referencia al Sector al que pertenece la Cuenta.
	 * @return String
	 */
	public Sector getSector() {
		return sector;
	}

	/**
	 * Referencia al Sector al que pertenece la Cuenta.
	 * @param sector
	 */
	public void setSector(Sector sector) {
		this.sector = sector;
    }

	/**
	 * Fecha en la que se hace el alta de la Cuenta
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
	 * Fecha en la que se hace la Baja Lógica de la Cuenta
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
	 * Referencia a la Cuenta Controlada a la cual esta&acute; asociada una Cuenta 
	 * Nombrada.
	 * @return long
     */
	public CuentaControlada getCuentaControlada() {
		return cuentaControlada;
    }

    /**
	 * Referencia a la Cuenta Controlada a la cual esta&acute; asociada una Cuenta 
	 * Nombrada. 
	 * @param cuentaControlada
     */
	public void setCuentaControlada(CuentaControlada cuentaControlada) {
		this.cuentaControlada = cuentaControlada;
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
	 * @return the idCuentaControlada
     */
	public BigInteger getIdCuentaControlada() {
		return idCuentaControlada;
    }

    /**
	 * @param idCuentaControlada the idCuentaControlada to set
     */
	public void setIdCuentaControlada(BigInteger idCuentaControlada) {
		this.idCuentaControlada = idCuentaControlada;
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
	 * @see java.lang.Object#hashCode()
     */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(57, 87).append(idCuentaNombrada).toHashCode();
    }

    /**
	 * @see java.lang.Object#equals(java.lang.Object)
     */
	@Override
	public boolean equals(Object obj) {
	   if (obj instanceof CuentaNombrada == false) {
		     return false;
	   }
	   if (this == obj) {
		     return true;
	   }
	   CuentaNombrada rhs = (CuentaNombrada) obj;
	   return new EqualsBuilder()
	   		.append(idCuentaNombrada, rhs.getIdCuentaNombrada())
			.isEquals();
    }
    
}
