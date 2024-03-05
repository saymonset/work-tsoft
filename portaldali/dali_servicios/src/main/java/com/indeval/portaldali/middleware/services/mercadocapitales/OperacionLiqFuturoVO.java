/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class OperacionLiqFuturoVO extends AbstractBaseDTO{
	
	/** Constante de serializacion */
    private static final long serialVersionUID = 1L;

	private BigInteger folio;
	
	private String folioBMV;
	
	private String idVendedor;
	
	private String cuentaVendedor;
    
    private String cuentaComprador;
	
	private String idComprador;
	
	private String folioComprador;
    
    private String folioVendedor;
	
	private String claveValor;
	
	private String tipoOperacion;
	
	private BigInteger cantidadTitulos;
	
	private BigDecimal precio;
	
	private BigDecimal importe;
    
    private String emisora;
    
    private String serie;
    
    private String nombreInstitucionVendedor;
    
    private String nombreInstitucionComprador;
    
    /**
     * @return String
     */
    public String getNombreInstitucionComprador() {
        return nombreInstitucionComprador;
    }

    /**
     * @param nombreInstitucionComprador
     */
    public void setNombreInstitucionComprador(String nombreInstitucionComprador) {
        this.nombreInstitucionComprador = nombreInstitucionComprador;
    }

    /**
     * @return String
     */
    public String getNombreInstitucionVendedor() {
        return nombreInstitucionVendedor;
    }

    /**
     * @param nombreInstitucionVendedor
     */
    public void setNombreInstitucionVendedor(String nombreInstitucionVendedor) {
        this.nombreInstitucionVendedor = nombreInstitucionVendedor;
    }

    /**
     * @return String
     */
    public String getSerie() {
        return serie;
    }

    /**
     * @param serie
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * @return String
     */
    public String getEmisora() {
        return emisora;
    }

    /**
     * @param emisora
     */
    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
		
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getCantidadTitulos() {
		return cantidadTitulos;
	}

	/**
	 * @param cantidadTitulos
	 */
	public void setCantidadTitulos(BigInteger cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	/**
	 * @return String
	 */ 
	public String getClaveValor() {
		return claveValor;
	}

	/**
	 * @param claveValor
	 */
	public void setClaveValor(String claveValor) {
		this.claveValor = claveValor;
	}

	/**
	 * @return String
	 */
	public String getCuentaVendedor() {
		return cuentaVendedor;
	}

	/**
	 * @param cuentaVendedor
	 */
	public void setCuentaVendedor(String cuentaVendedor) {
		this.cuentaVendedor = cuentaVendedor;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 */
	public void setFolio(BigInteger folio) {
		this.folio = folio;
	}

	/**
	 * @return String
	 */
	public String getFolioBMV() {
		return folioBMV;
	}

	/**
	 * @param folioBMV
	 */
	public void setFolioBMV(String folioBMV) {
		this.folioBMV = folioBMV;
	}

	/**
	 * @return String
	 */
	public String getFolioComprador() {
		return folioComprador;
	}

	/**
	 * @param folioComprador
	 */
	public void setFolioComprador(String folioComprador) {
		this.folioComprador = folioComprador;
	}

	/**
	 * @return String
	 */
	public String getIdComprador() {
		return idComprador;
	}

	/**
	 * @param idComprador
	 */
	public void setIdComprador(String idComprador) {
		this.idComprador = idComprador;
	}

	/**
	 * @return String
	 */
	public String getIdVendedor() {
		return idVendedor;
	}

	/**
	 * @param idVendedor
	 */
	public void setIdVendedor(String idVendedor) {
		this.idVendedor = idVendedor;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param importe
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getPrecio() {
		return precio;
	}

	/**
	 * @param precio
	 */
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	/**
	 * @return String
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

    /**
     * @return String
     */
    public String getFolioVendedor() {
        return folioVendedor;
    }

    /**
     * @param folioVendedor
     */
    public void setFolioVendedor(String folioVendedor) {
        this.folioVendedor = folioVendedor;
    }

    /**
     * @return String
     */
    public String getCuentaComprador() {
        return cuentaComprador;
    }

    /**
     * @param cuentaComprador
     */
    public void setCuentaComprador(String cuentaComprador) {
        this.cuentaComprador = cuentaComprador;
    }

}
