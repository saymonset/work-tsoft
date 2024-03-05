/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class OperacionDiaCapitalesParams extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;
    
    private AgenteVO agenteVO; // requerido

    private Date fechaInicial; // requerido

    private Date fechaFinal;

    private EmisionVO emisionVO; // opcional

    private String tipoOperacion; // es el mismo caso si vienen ambos o
                                    // ninguno, la unica diferencia es cuando
                                    // solo viene uno

    private String maneraExtraccion; // requerido

    private String origen; // opcional

    private String aplicacion; // opcional

    private boolean isExport; // bandera para indicar que se trata del caso de
                                // exportacion

    private PaginaVO paginaVO;

    /**
     * @return the agenteVO
     */
    public AgenteVO getAgenteVO() {
        return agenteVO;
    }

    /**
     * @param agenteVO
     *            the agenteVO to set
     */
    public void setAgenteVO(AgenteVO agenteVO) {
        this.agenteVO = agenteVO;
    }

    /**
     * @return the aplicacion
     */
    public String getAplicacion() {
        return aplicacion;
    }

    /**
     * @param aplicacion
     *            the aplicacion to set
     */
    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    /**
     * @return the emisionVO
     */
    public EmisionVO getEmisionVO() {
        return emisionVO;
    }

    /**
     * @param emisionVO
     *            the emisionVO to set
     */
    public void setEmisionVO(EmisionVO emisionVO) {
        this.emisionVO = emisionVO;
    }

    /**
     * @return the fechaOperacion
     */
    public Date getFechaInicial() {
        return fechaInicial;
    }

    /**
     * @param fechaOperacion
     *            the fechaOperacion to set
     */
    public void setFechaInicial(Date fechaOperacion) {
        this.fechaInicial = fechaOperacion;
    }

    /**
     * @return the maneraExtraccion
     */
    public String getManeraExtraccion() {
        return maneraExtraccion;
    }

    /**
     * @param maneraExtraccion
     *            the maneraExtraccion to set
     */
    public void setManeraExtraccion(String maneraExtraccion) {
        this.maneraExtraccion = maneraExtraccion;
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen
     *            the origen to set
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return the tipoOperacion
     */
    public String getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * @param tipoOperacion
     *            the tipoOperacion to set
     */
    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    /**
     * @return the isExport
     */
    public boolean isExport() {
        return isExport;
    }

    /**
     * @param isExport
     *            the isExport to set
     */
    public void setExport(boolean isExport) {
        this.isExport = isExport;
    }

    /**
     * @return the paginaVO
     */
    public PaginaVO getPaginaVO() {
        return paginaVO;
    }

    /**
     * @param paginaVO
     *            the paginaVO to set
     */
    public void setPaginaVO(PaginaVO paginaVO) {
        this.paginaVO = paginaVO;
    }

    /**
     * @return Date
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @param fechaFinal
     */
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }
    
}
