/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;
import java.util.Date;

import com.indeval.portaldali.persistence.vo.AgentePK;
import com.indeval.portaldali.persistence.vo.EmisionPK;

/**
 * Clase de trasporte para los Daos.
 * 
 * @author Sergio Mena
 */
public class BitacoraOperacionDaoParams implements Serializable {

    /**
     * Constante de serializacion
     */
    private static final long serialVersionUID = 1L;

    private AgentePK agenteFirmado;

    private EmisionPK emisionPK;

    private String referenciaMensaje;

    private Date[] fechaLiquidacion;

    private String estatusRegistro;

    private Date[] fechaConcertacion;

    /**
     * @return the agenteFirmado
     */
    public AgentePK getAgenteFirmado() {
        return agenteFirmado;
    }

    /**
     * @param agenteFirmado the agenteFirmado to set
     */
    public void setAgenteFirmado(AgentePK agenteFirmado) {
        this.agenteFirmado = agenteFirmado;
    }

    /**
     * @return the emisionPK
     */
    public EmisionPK getEmisionPK() {
        return emisionPK;
    }

    /**
     * @param emisionPK the emisionPK to set
     */
    public void setEmisionPK(EmisionPK emisionPK) {
        this.emisionPK = emisionPK;
    }

    /**
     * @return the estatusRegistro
     */
    public String getEstatusRegistro() {
        return estatusRegistro;
    }

    /**
     * @param estatusRegistro the estatusRegistro to set
     */
    public void setEstatusRegistro(String estatusRegistro) {
        this.estatusRegistro = estatusRegistro;
    }

    /**
     * @return the fechaConcertacion
     */
    public Date[] getFechaConcertacion() {
        return fechaConcertacion;
    }

    /**
     * @param fechaConcertacion the fechaConcertacion to set
     */
    public void setFechaConcertacion(Date[] fechaConcertacion) {
        this.fechaConcertacion = fechaConcertacion;
    }

    /**
     * @return the fechaLiquidacion
     */
    public Date[] getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * @param fechaLiquidacion the fechaLiquidacion to set
     */
    public void setFechaLiquidacion(Date[] fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    /**
     * @return the referenciaMensaje
     */
    public String getReferenciaMensaje() {
        return referenciaMensaje;
    }

    /**
     * @param referenciaMensaje the referenciaMensaje to set
     */
    public void setReferenciaMensaje(String referenciaMensaje) {
        this.referenciaMensaje = referenciaMensaje;
    }

    
}
