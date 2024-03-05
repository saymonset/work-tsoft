/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class GetEjercicioDerechoRentaFijaVariableParams extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private Date fechaPrevioInicio;

    private Date fechaPrevioFin;

    private Date fechaFirmeInicio;

    private Date fechaFirmeFin;

    private EmisionVO emisionVO;

    private String te;

    private BigDecimal tasaInteres;

    private String estatus;

    private PaginaVO paginaVO;

    private Boolean isFija;

    /**
     * @return Returns the fechaFirmeFin.
     */
    public Date getFechaFirmeFin() {
        return fechaFirmeFin;
    }

    /**
     * @param fechaFirmeFin
     *            The fechaFirmeFin to set.
     */
    public void setFechaFirmeFin(Date fechaFirmeFin) {
        this.fechaFirmeFin = clona(fechaFirmeFin);
    }

    /**
     * @return Returns the fechaFirmeInicio.
     */
    public Date getFechaFirmeInicio() {
        return fechaFirmeInicio;
    }

    /**
     * @param fechaFirmeInicio
     *            The fechaFirmeInicio to set.
     */
    public void setFechaFirmeInicio(Date fechaFirmeInicio) {
        this.fechaFirmeInicio = clona(fechaFirmeInicio);
    }

    /**
     * @return Returns the fechaPrevioFin.
     */
    public Date getFechaPrevioFin() {
        return fechaPrevioFin;
    }

    /**
     * @param fechaPrevioFin
     *            The fechaPrevioFin to set.
     */
    public void setFechaPrevioFin(Date fechaPrevioFin) {
        this.fechaPrevioFin = clona(fechaPrevioFin);
    }

    /**
     * @return Returns the fechaPrevioInicio.
     */
    public Date getFechaPrevioInicio() {
        return fechaPrevioInicio;
    }

    /**
     * @param fechaPrevioInicio
     *            The fechaPrevioInicio to set.
     */
    public void setFechaPrevioInicio(Date fechaPrevioInicio) {
        this.fechaPrevioInicio = clona(fechaPrevioInicio);
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
     * @return Boolean
     */
    public Boolean isFija() {
        return isFija;
    }

    /**
     * @param isFija
     */
    public void isFija(Boolean isFija) {
        this.isFija = isFija;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }
    
}