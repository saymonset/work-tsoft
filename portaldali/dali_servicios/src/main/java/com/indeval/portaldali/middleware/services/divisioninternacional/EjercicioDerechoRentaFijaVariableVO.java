/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EjercicioDerechoRentaFijaVariableVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private EmisionVO claveValorOriginal;

    private EmisionVO claveValorDestino;

    private String te;

    private String estatus;

    private Date fechaPago;

    private BigDecimal tasaInteres;

    private Integer diasNaturales;

    private BigDecimal proporcionCupon;

    private Date fechaCorte;

    private BigDecimal valorNominal;

    private boolean isFija;

    private Date fechaExDerecho;

    private BigInteger folioFija;

    private Integer folioVariable;

    /**
     * @return Returns the folioFija.
     */
    public BigInteger getFolioFija() {
        return folioFija;
    }

    /**
     * @param folioFija
     *            The folioFija to set.
     */
    public void setFolioFija(BigInteger folioFija) {
        this.folioFija = folioFija;
    }

    /**
     * @return Returns the folioVariable.
     */
    public Integer getFolioVariable() {
        return folioVariable;
    }

    /**
     * @param folioVariable
     *            The folioVariable to set.
     */
    public void setFolioVariable(Integer folioVariable) {
        this.folioVariable = folioVariable;
    }

    /**
     * @return EmisionVO
     */
    public EmisionVO getClaveValorDestino() {
        return claveValorDestino;
    }

    /**
     * @param claveValorDestino
     */
    public void setClaveValorDestino(EmisionVO claveValorDestino) {
        this.claveValorDestino = claveValorDestino;
    }

    /**
     * @return EmisionVO
     */
    public EmisionVO getClaveValorOriginal() {
        return claveValorOriginal;
    }

    /**
     * @param claveValorOriginal
     */
    public void setClaveValorOriginal(EmisionVO claveValorOriginal) {
        this.claveValorOriginal = claveValorOriginal;
    }

    /**
     * @return Integer
     */
    public Integer getDiasNaturales() {
        return diasNaturales;
    }

    /**
     * @param diasNaturales
     */
    public void setDiasNaturales(Integer diasNaturales) {
        this.diasNaturales = diasNaturales;
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
     * @return Date
     */
    public Date getFechaPago() {
        return fechaPago;
    }

    /**
     * @param fechaPago
     */
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = clona(fechaPago);
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getProporcionCupon() {
        return proporcionCupon;
    }

    /**
     * @param proporcionCupon
     */
    public void setProporcionCupon(BigDecimal proporcionCupon) {
        this.proporcionCupon = proporcionCupon;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    /**
     * @param tasaInteres
     */
    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    /**
     * @return String
     */
    public String getTe() {
        return te;
    }

    /**
     * @param te
     */
    public void setTe(String te) {
        this.te = te;
    }

    /**
     * @return Date
     */
    public Date getFechaCorte() {
        return fechaCorte;
    }

    /**
     * @param fechaCorte
     */
    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = clona(fechaCorte);
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getValorNominal() {
        return valorNominal;
    }

    /**
     * @param valorNominal
     */
    public void setValorNominal(BigDecimal valorNominal) {
        this.valorNominal = valorNominal;
    }

    /**
     * @return boolean
     */
    public boolean isFija() {
        return isFija;
    }

    /**
     * @param isFija
     */
    public void setFija(boolean isFija) {
        this.isFija = isFija;
    }

    /**
     * @return Date
     */
    public Date getFechaExDerecho() {
        return fechaExDerecho;
    }

    /**
     * @param fechaExDerecho
     */
    public void setFechaExDerecho(Date fechaExDerecho) {
        this.fechaExDerecho = clona(fechaExDerecho);
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

}