/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;
import java.util.Date;


public class ParamsConfirmaTraspasoVsPagoVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private AgentePK traspasante;
    
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
     * @param fechaLiquidacion The fechaLiquidacion to set.
     */
    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    /**
     * @return Returns the folio.
     */
    public Integer getFolio() {
        return folio;
    }

    /**
     * @param folio The folio to set.
     */
    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    /**
     * @return Returns the traspasante.
     */
    public AgentePK getTraspasante() {
        return traspasante;
    }

    /**
     * @param traspasante The traspasante to set.
     */
    public void setTraspasante(AgentePK traspasante) {
        this.traspasante = traspasante;
    }

    /**
     * @return Returns the usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario The usuario to set.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
