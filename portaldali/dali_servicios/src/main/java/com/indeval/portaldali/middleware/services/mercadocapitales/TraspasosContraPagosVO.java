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
public class TraspasosContraPagosVO extends AbstractBaseDTO {

    /**
     * Variable de serializacion
     */
    private static final long serialVersionUID = 1L;

    private AgenteVO traspasante;

    private AgenteVO receptor;

    private EmisionVO emision;

    private BigDecimal cantidadOperada;

    private Date fechaLiquidacion;

    private Date fechaAquisicion;

    private BigDecimal precioAdquisicion;

    private Integer folio;

    private boolean confirmacion;

    private String sociedadSerie;

    private String error;

    /** Costo promedio actualizado */
    private BigDecimal costoPromedio;
    
    /**
     * @return String
     */
    public String getError() {
        return error;
    }

    /**
     * @param error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return String sociedadSerie
     */
    public String getSociedadSerie() {
        return sociedadSerie;
    }

    /**
     * @param sociedadSerie
     */
    public void setSociedadSerie(String sociedadSerie) {
        this.sociedadSerie = sociedadSerie;
    }

    /**
     * @return boolean confiramcion
     */
    public boolean isConfirmacion() {
        return confirmacion;
    }

    /**
     * @param confirmacion
     */
    public void setConfirmacion(boolean confirmacion) {
        this.confirmacion = confirmacion;
    }

    /**
     * @return Returns the cantidadOperada.
     */
    public BigDecimal getCantidadOperada() {
        return cantidadOperada;
    }

    /**
     * @param cantidadOperada
     *            The cantidadOperada to set.
     */
    public void setCantidadOperada(BigDecimal cantidadOperada) {
        this.cantidadOperada = clonaBigDecimal(cantidadOperada);
    }

    /**
     * @return Returns the emision.
     */
    public EmisionVO getEmision() {
        return emision;
    }

    /**
     * @param emision
     *            The emision to set.
     */
    public void setEmision(EmisionVO emision) {
        this.emision = emision;
    }

    /**
     * @return Returns the fechaAquisicion.
     */
    public Date getFechaAquisicion() {
        return fechaAquisicion;
    }

    /**
     * @param fechaAquisicion
     *            The fechaAquisicion to set.
     */
    public void setFechaAquisicion(Date fechaAquisicion) {
        this.fechaAquisicion = clona(fechaAquisicion);
    }

    /**
     * @return Returns the fechaLiquidacion.
     */
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * @param fechaLiquidacion
     *            The fechaLiquidacion to set.
     */
    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = clona(fechaLiquidacion);
    }

    /**
     * @return Returns the folio.
     */
    public Integer getFolio() {
        return folio;
    }

    /**
     * @param folio
     *            The folio to set.
     */
    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    /**
     * @return Returns the precioAdquisicion.
     */
    public BigDecimal getPrecioAdquisicion() {
        return precioAdquisicion;
    }

    /**
     * @param precioAdquisicion
     *            The precioAdquisicion to set.
     */
    public void setPrecioAdquisicion(BigDecimal precioAdquisicion) {
        this.precioAdquisicion = clonaBigDecimal(precioAdquisicion);
    }

    /**
     * @return Returns the receptor.
     */
    public AgenteVO getReceptor() {
        return receptor;
    }

    /**
     * @param receptor
     *            The receptor to set.
     */
    public void setReceptor(AgenteVO receptor) {
        this.receptor = receptor;
    }

    /**
     * @return Returns the traspasante.
     */
    public AgenteVO getTraspasante() {
        return traspasante;
    }

    /**
     * @param traspasante
     *            The traspasante to set.
     */
    public void setTraspasante(AgenteVO traspasante) {
        this.traspasante = traspasante;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

	/**
	 * @return the costoPromedio
	 */
	public BigDecimal getCostoPromedio() {
		return costoPromedio;
	}

	/**
	 * @param costoPromedio the costoPromedio to set
	 */
	public void setCostoPromedio(BigDecimal costoPromedio) {
		this.costoPromedio = costoPromedio;
	}

}
