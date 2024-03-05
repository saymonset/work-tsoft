/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ConsultaOperacionesVO extends AbstractBaseDTO {
    
    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private String llaveFolio;
    
    private AgenteVO agenteVO;
    
    private String cuentaContraparte;
    
    private EmisionVO emisionVO;
    
    private BigDecimal cantidadOperada;
    
    private String operacion;
    
    private Date fechaOperacion;
    
    private Date fechaLiquidacion;
    
    private String estatus; 
    
    private String estado;

    private String mensajeEstatus;
    
    /**
     * @return AgenteVO
     */
    public AgenteVO getAgenteVO() {
        return agenteVO;
    }

    /**
     * @param agenteVO
     */
    public void setAgenteVO(AgenteVO agenteVO) {
        this.agenteVO = agenteVO;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getCantidadOperada() {
        return cantidadOperada;
    }

    /**
     * @param cantidadOperada
     */
    public void setCantidadOperada(BigDecimal cantidadOperada) {
        this.cantidadOperada = cantidadOperada;
    }

    /**
     * @return String
     */
    public String getCuentaContraparte() {
        return cuentaContraparte;
    }

    /**
     * @param cuentaContraparte
     */
    public void setCuentaContraparte(String cuentaContraparte) {
        this.cuentaContraparte = cuentaContraparte;
    }

    /**
     * @return EmisionVO
     */
    public EmisionVO getEmisionVO() {
        return emisionVO;
    }

    /**
     * @param emisionVO
     */
    public void setEmisionVO(EmisionVO emisionVO) {
        this.emisionVO = emisionVO;
    }

    /**
     * @return String
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return String
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * @param estatus
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    /**
     * @return String
     */
    public String getMensajeEstatus() {
		return mensajeEstatus;
	}

	/**
	 * @param mensajeEstatus
	 */
	public void setMensajeEstatus(String mensajeEstatus) {
		this.mensajeEstatus = mensajeEstatus;
	}

	/**
	 * @return Date
	 */
	public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * @param fechaLiquidacion
     */
    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = clona(fechaLiquidacion);
    }

    /**
     * @return Date
     */
    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    /**
     * @param fechaOperacion
     */
    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = clona(fechaOperacion);
    }

    /**
     * @return String
     */
    public String getLlaveFolio() {
        return llaveFolio;
    }

    /**
     * @param llaveFolio
     */
    public void setLlaveFolio(String llaveFolio) {
        this.llaveFolio = llaveFolio;
    }

    /**
     * @return String
     */
    public String getOperacion() {
        return operacion;
    }

    /**
     * @param operacion
     */
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }
    
}
