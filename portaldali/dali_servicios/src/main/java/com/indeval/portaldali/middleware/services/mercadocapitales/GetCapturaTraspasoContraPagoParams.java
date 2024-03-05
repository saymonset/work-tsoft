/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class GetCapturaTraspasoContraPagoParams extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private AgenteVO traspasante;

    private AgenteVO receptor;

    private EmisionVO emision;

    private BigDecimal cantidad;

    private String cveReporto;

    private BigDecimal precioTitulo;

    private String usuario;

    private String nombreUsuario;

    private Date fechaLiquidacion;
    
    private Date fechaHoraCierreOper;

    private Integer liquidacion;

    /**
     * @return BigDecimal
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad
     */
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return String
     */
    public String getCveReporto() {
        return cveReporto;
    }

    /**
     * @param cveReporto
     */
    public void setCveReporto(String cveReporto) {
        this.cveReporto = cveReporto;
    }

    /**
     * @return EmisionVO
     */
    public EmisionVO getEmision() {
        return emision;
    }

    /**
     * @param emision
     */
    public void setEmision(EmisionVO emision) {
        this.emision = emision;
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
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

	/**
     * @return Integer
     */
    public Integer getLiquidacion() {
        return liquidacion;
    }

    /**
     * @param liquidacion
     */
    public void setLiquidacion(Integer liquidacion) {
        this.liquidacion = liquidacion;
    }

    /**
     * @return String
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getPrecioTitulo() {
        return precioTitulo;
    }

    /**
     * @param precioTitulo
     */
    public void setPrecioTitulo(BigDecimal precioTitulo) {
        this.precioTitulo = precioTitulo;
    }

    /**
     * @return AgenteVO
     */
    public AgenteVO getReceptor() {
        return receptor;
    }

    /**
     * @param receptor
     */
    public void setReceptor(AgenteVO receptor) {
        this.receptor = receptor;
    }

    /**
     * @return AgenteVO
     */
    public AgenteVO getTraspasante() {
        return traspasante;
    }

    /**
     * @param traspasante
     */
    public void setTraspasante(AgenteVO traspasante) {
        this.traspasante = traspasante;
    }

    /**
     * @return String
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }
    
}
