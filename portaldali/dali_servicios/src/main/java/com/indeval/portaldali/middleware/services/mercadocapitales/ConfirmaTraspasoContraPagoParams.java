/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ConfirmaTraspasoContraPagoParams extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private AgenteVO traspasante;

    private Integer folio;

    private Date fechaLiquidacion;

    private String usuario;

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
     * @return Returns the usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario
     *            The usuario to set.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {

    }

}
