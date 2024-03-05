/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


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
 * @author fernnado vazquez ulloa
 * @version 1.0
 */
 
 
public class CuentaControladaDTO implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la cuenta controlada.
	 */
	
	
	private BigInteger idCuentaControlada;
	/**
	 * N&uacute;mero de la cuenta.
	 * Formalmente el n&uacute;mero de la cuenta se forma por el tipo de instituci&oacute;n mas el
	 * folio de instituci&oacute;n mas este n&uacute;mero de cuenta.
	 * Este identificador en conjunto con la instituci&oacute;n deben ser &uacute;nicos.
	 */
	
	private String cuenta; 
	/**
	 * C&oacute;digo Internacional de cuenta Bancaria.
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
	 * Identificador del tipo de cuenta.
	 */
	
	
	private TipoCuentaDTO tipoCuenta;
	/**
	 * Identificador del tipo de cuenta.
	 */
	
	private BigInteger idTipoCuenta;
	/**
	 * Raz&oacute;n social de la cuenta.
	 */
	
	private String nombreCuenta;
	/**
	 * Fecha de alta de la cuenta.
	 */
	
	private Date fechaAlta; 
	/**
	 * Estado de la cuenta.
	 */
	
	
	private EstadoCuentaDTO estadoCuenta;
	/**
	 * Fecha de baja de la cuenta.
	 */
	
	private Date fechaBaja;
	
	private BigDecimal idSector;

	/**
	 * Identificador secuencial de la Cuenta.
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
	 * Clave de la Cuenta.
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
	 * Identificador internacional de cuenta (Homologación con IBAN ISO-13616).
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
	 * Referencia al Tipo de Cuenta asociado.
	 * 
     */
    public void setTipoCuenta(TipoCuentaDTO tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
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
	 * Fecha en la que se hace el alta de la Cuenta.
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
	 * Fecha en la que se hace la baja lógica de la Cuenta.
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
	public BigInteger getIdTipoCuenta() {
		return idTipoCuenta;
	}

	/**
	 * 
	 */
	public void setIdTipoCuenta(BigInteger idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
	}
	
	
    public BigDecimal getIdSector() {
        return idSector;
    }
	
	/**
     * 
     */
    public void setIdSector(BigDecimal idSector) {
        this.idSector = idSector;
}

	/**
	 * 
     */
	
	public int hashCode() {
		return new HashCodeBuilder(47, 77).append(idCuentaControlada).toHashCode();
    }
    
	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
	   if (!(obj instanceof CuentaControladaDTO)) {
		     return false;
	   }
	   if (this == obj) {
		     return true;
	   }
	   CuentaControladaDTO rhs = (CuentaControladaDTO) obj;
	   return new EqualsBuilder()
	   		.append(idCuentaControlada, rhs.getIdCuentaControlada())
			.isEquals();
	}
}
