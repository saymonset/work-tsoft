/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;



import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Cata&acute;logo que contiene las cuentas nombradas de valores y efectivo.
 * 
 * 
 *
 * @author Fernando vazquez ulloa
 * @version 1.0
 */
 
public class CuentaNombradaDTO implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la cuenta nombrada.
	 */
	
	
	private BigInteger idCuentaNombrada;
	/**
	 * N&uacute;mero de la cuenta.
	 * Formalmente el n&uacute;mero de la cuenta se forma por el tipo de instituci&oacute;n mas el
	 * folio de instituci&oacute;n mas este n&uacute;mero de cuenta.
	 * Este identificador en conjunto con la instituci&oacute;n deben ser &uacute;nicos.
	 */
	
	private String cuenta;
	/**
	 * C&oacute;digo Internacional de Cuenta Bancaria.
	 */
	
	private String iban;
	/**
	 * Instituci&oacute;n
	 */
	
	
	private InstitucionDTO institucion;
	/**
	 * Identificador de la instituci&oacute;n. 
	 * Este identificador en conjunto con la cuenta deben ser &uacute;nicos.
	 */
	
	private BigInteger idInstitucion;
	/**
	 * Tipo de cuenta.
	 */
	
	
	private TipoCuentaDTO tipoCuenta;
	/**
	 * Identificador del tipo de cuenta.
	 */
	
	private BigInteger idTipoCuenta;
	/**
	 * Raz&oacute;n social de la cuenta.
	 */
	
	private String razonSocialCuenta;
	/**
	 * Nombre de la cuenta.
	 */
	
	private String nombreCuenta;
	/**
	 * Identificador del sector al que pertenece la cuenta.
	 */
	
	
	private SectorDTO sector;
	/**
	 * Fecha de alta de la cuenta.
	 */
	
	private Date fechaAlta;
	/**
	 * Identificador del estado de la cuenta.
	 */
	
	
	private EstadoCuentaDTO estadoCuenta;
	/**
	 * Fecha de baja de la cuenta.
	 */
	
	private Date fechaBaja;
	/**
	 * Cuenta controlada a la que esta ligada la cuenta nombrada.
	 */
	
	
	private CuentaControladaDTO cuentaControlada;
	/**
	 * Identificador de la cuenta controlada a la que esta ligada la cuenta nombrada.
	 */
	
	private BigInteger idCuentaControlada;

	/**
	 * Identificador secuencial de la Cuenta.
	 * 
	 */
	public BigInteger getIdCuentaNombrada() {
        return idCuentaNombrada;
    }

	/**
	 * 
	 */
	public void setIdCuentaNombrada(BigInteger idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
	}

	/**
	 * Clave de la Cuenta
	 * 
	 */
    public String getCuenta() {
        return cuenta;
    }

	/**
	 * 
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * Identificador internacional de cuenta (Homologación con IBAN ISO-13616)
	 * 
	 */
    public String getIban() {
        return iban;
    }

	/**
	 * 
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}

	/**
	 * Referencia a la Institución a la que pertenece la Cuenta.
	 * 
	 */
	public BigInteger getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * 
	 */
	public void setIdInstitucion(BigInteger idInstitucion) {
		this.idInstitucion = idInstitucion;
    }

	/**
	 * Referencia al Tipo de Cuenta asociado. 
	 * 
	 */
    public TipoCuentaDTO getTipoCuenta() {
        return tipoCuenta;
    }

	/**
	 * 
	 */
	public void setTipoCuenta(TipoCuentaDTO tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	/**
	 * Descripción de la Cuenta
	 * 
	 */
    public String getRazonSocialCuenta() {
        return razonSocialCuenta;
    }

	/**
	 * 
	 */
	public void setRazonSocialCuenta(String razonSocialCuenta) {
		this.razonSocialCuenta = razonSocialCuenta;
	}

	/**
	 * Nombre de la Cuenta
	 * 
	 */
    public String getNombreCuenta() {
        return nombreCuenta;
    }

	/**
	 * 
	 */
	public void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}

	/**
	 * Referencia al Sector al que pertenece la Cuenta.
	 * 
	 */
	public SectorDTO getSector() {
		return sector;
	}

	/**
	 * Referencia al Sector al que pertenece la Cuenta.
	 * 
	 */
	public void setSector(SectorDTO sector) {
		this.sector = sector;
    }

	/**
	 * Fecha en la que se hace el alta de la Cuenta
	 * 
	 */
    public Date getFechaAlta() {
        return fechaAlta;
    }

	/**
	 * 
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	/**
	 * Referencia al Estado de la Cuenta.
	 * 
	 */
    public EstadoCuentaDTO getCuentaEstado() {
        return estadoCuenta;
    }

	/**
	 * 
	 */
	public void setCuentaEstado(EstadoCuentaDTO estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}

	/**
	 * Fecha en la que se hace la Baja Lógica de la Cuenta
	 * 
	 */
    public Date getFechaBaja() {
        return fechaBaja;
    }

	/**
	 * 
	 */
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
    }

    /**
	 * Referencia a la Cuenta Controlada a la cual esta&acute; asociada una Cuenta 
	 * Nombrada.
	 * 
     */
	public CuentaControladaDTO getCuentaControlada() {
		return cuentaControlada;
    }

    /**
	 * Referencia a la Cuenta Controlada a la cual esta&acute; asociada una Cuenta 
	 * Nombrada. 
	 * 
     */
	public void setCuentaControlada(CuentaControladaDTO cuentaControlada) {
		this.cuentaControlada = cuentaControlada;
    }

    /**
	 * 
     */
	public EstadoCuentaDTO getEstadoCuenta() {
		return estadoCuenta;
    }

    /**
	 * 
     */
	public void setEstadoCuenta(EstadoCuentaDTO estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
    }

    /**
	 * 
     */
	public BigInteger getIdCuentaControlada() {
		return idCuentaControlada;
    }

    /**
	 * 
     */
	public void setIdCuentaControlada(BigInteger idCuentaControlada) {
		this.idCuentaControlada = idCuentaControlada;
    }

    /**
	 * 
     */
	public BigInteger getIdTipoCuenta() {
		return idTipoCuenta;
    }

    /**
	 * 
     */
	public void setIdTipoCuenta(BigInteger idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
    }

    /**
	 * 
     */
	public InstitucionDTO getInstitucion() {
		return institucion;
    }

    /**
	 * 
     */
	public void setInstitucion(InstitucionDTO institucion) {
		this.institucion = institucion;
    }

    /**
	 * 
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
	   if (obj instanceof CuentaNombradaDTO == false) {
		     return false;
	   }
	   if (this == obj) {
		     return true;
	   }
	   CuentaNombradaDTO rhs = (CuentaNombradaDTO) obj;
	   return new EqualsBuilder()
	   		.append(idCuentaNombrada, rhs.getIdCuentaNombrada())
			.isEquals();
    }
    
}
