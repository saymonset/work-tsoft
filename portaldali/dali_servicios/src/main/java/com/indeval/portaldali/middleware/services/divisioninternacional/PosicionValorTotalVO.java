/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class PosicionValorTotalVO extends AbstractBaseDTO {

	
	/**  Constante de Serializacion	 */
	private static final long serialVersionUID = 1L;
	
	private Integer totalTV;
	
    private Integer totalEmisiones;
	
    private BigDecimal posicionTotal;
	
    private String[] cuentas;
	
    private String cuentaActual;
	
    private PaginaVO paginaVO;
	
    private BigDecimal posActualTotalPorCuenta;
	
    private BigDecimal valuacionMercadoPorCuenta;
	
    private BigDecimal valuacionNominalPorCuenta;
    
	/**
	 * @return String
	 */
	public String getCuentaActual() {
        return cuentaActual;
    }

    /**
     * @param cuentaActual
     */
    public void setCuentaActual(String cuentaActual) {
        this.cuentaActual = cuentaActual;
    }

    /**
     * @return String[]
     */
    public String[] getCuentas() {
        return cuentas;
    }

    /**
     * @param cuentas
     */
    public void setCuentas(String[] cuentas) {
        this.cuentas = cuentas;
    }

    /**
     * @return PaginaVO
     */
    public PaginaVO getPaginaVO() {
        return paginaVO;
    }

    /**
     * @param paginaVO
     */
    public void setPaginaVO(PaginaVO paginaVO) {
        this.paginaVO = paginaVO;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getPosActualTotalPorCuenta() {
        return posActualTotalPorCuenta;
    }

    /**
     * @param posActualTotalPorCuenta
     */
    public void setPosActualTotalPorCuenta(BigDecimal posActualTotalPorCuenta) {
        this.posActualTotalPorCuenta = posActualTotalPorCuenta;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getPosicionTotal() {
        return posicionTotal;
    }

    /**
     * @param posicionTotal
     */
    public void setPosicionTotal(BigDecimal posicionTotal) {
        this.posicionTotal = posicionTotal;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getValuacionMercadoPorCuenta() {
        return valuacionMercadoPorCuenta;
    }

    /**
     * @param valuacionMercadoPorCuenta
     */
    public void setValuacionMercadoPorCuenta(BigDecimal valuacionMercadoPorCuenta) {
        this.valuacionMercadoPorCuenta = valuacionMercadoPorCuenta;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getValuacionNominalPorCuenta() {
        return valuacionNominalPorCuenta;
    }

    /**
     * @param valuacionNominalPorCuenta
     */
    public void setValuacionNominalPorCuenta(BigDecimal valuacionNominalPorCuenta) {
        this.valuacionNominalPorCuenta = valuacionNominalPorCuenta;
    }

    /**
     * @return Integer
     */
    public Integer getTotalEmisiones() {
		return totalEmisiones;
	}

	/**
	 * @param totalEmisiones
	 */
	public void setTotalEmisiones(Integer totalEmisiones) {
		this.totalEmisiones = totalEmisiones;
	}

	/**
	 * @return Integer
	 */
	public Integer getTotalTV() {
		return totalTV;
	}

	/**
	 * @param totalTV
	 */
	public void setTotalTV(Integer totalTV) {
		this.totalTV = totalTV;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object arg0, Errors arg1) {
	}
    
}