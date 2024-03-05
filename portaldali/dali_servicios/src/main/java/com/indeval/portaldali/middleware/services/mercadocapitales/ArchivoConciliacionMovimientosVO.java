/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ArchivoConciliacionMovimientosVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private AgenteVO traspasante;

    private AgenteVO receptor;

    private EmisionVO emision;

    private BigDecimal cantidadOperada;

    private String origen;

    private String tipoOperacion; //clave_reporto en traspasos -- tipo_movimiento en depositos_retiros

    private Integer folioControl; //solo en traspasos

    private String folioDescripcion; //solo en traspasos

    private String llaveFolio;

    /**
     * @return Returns the cantidadOperada.
     */
    public BigDecimal getCantidadOperada() {
        return cantidadOperada;
    }

    /**
     * @param cantidadOperada The cantidadOperada to set.
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
     * @param emision The emision to set.
     */
    public void setEmision(EmisionVO emision) {
        this.emision = emision;
    }

    /**
     * @return Returns the folioControl.
     */
    public Integer getFolioControl() {
        return folioControl;
    }

    /**
     * @param folioControl The folioControl to set.
     */
    public void setFolioControl(Integer folioControl) {
        this.folioControl = folioControl;
    }

    /**
     * @return Returns the folioDescripcion.
     */
    public String getFolioDescripcion() {
        return folioDescripcion;
    }

    /**
     * @param folioDescripcion The folioDescripcion to set.
     */
    public void setFolioDescripcion(String folioDescripcion) {
        this.folioDescripcion = folioDescripcion;
    }

    /**
     * @return Returns the llaveFolio.
     */
    public String getLlaveFolio() {
        return llaveFolio;
    }

    /**
     * @param llaveFolio The llaveFolio to set.
     */
    public void setLlaveFolio(String llaveFolio) {
        this.llaveFolio = llaveFolio;
    }

    /**
     * @return Returns the origen.
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen The origen to set.
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return Returns the receptor.
     */
    public AgenteVO getReceptor() {
        return receptor;
    }

    /**
     * @param receptor The receptor to set.
     */
    public void setReceptor(AgenteVO receptor) {
        this.receptor = receptor;
    }

    /**
     * @return Returns the tipoOperacion.
     */
    public String getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * @param tipoOperacion The tipoOperacion to set.
     */
    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    /**
     * @return Returns the traspasante.
     */
    public AgenteVO getTraspasante() {
        return traspasante;
    }

    /**
     * @param traspasante The traspasante to set.
     */
    public void setTraspasante(AgenteVO traspasante) {
        this.traspasante = traspasante;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

}
