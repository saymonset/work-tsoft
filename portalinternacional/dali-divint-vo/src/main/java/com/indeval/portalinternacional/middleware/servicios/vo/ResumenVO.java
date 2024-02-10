/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.servicios.modelo.AbstractBaseDTO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;

public class ResumenVO extends AbstractBaseDTO {

    /**
     * Constante de serializacion
     */
    private static final long serialVersionUID = 1L;

    private Date fechaCarga;

    private Integer totalNuevos;

    private Integer totalCargados;

    private Integer totalError;

    private Integer totalRegistros;
    
    private Integer totalProtocolo;

    private String nombreUsuario;

    private AgenteVO agenteFirmado;

    private String tipoProceso;
    
    
    

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

    /**
     * @return
     */
    public AgenteVO getAgenteFirmado() {
        return agenteFirmado;
    }

    /**
     * @param agenteFirmado
     */
    public void setAgenteFirmado(AgenteVO agenteFirmado) {
        this.agenteFirmado = agenteFirmado;
    }

    /**
     * @return
     */
    public Date getFechaCarga() {
        return fechaCarga;
    }

    /**
     * @param fechaCarga
     */
    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = clona(fechaCarga);
    }

    /**
     * @return
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
     * @return
     */
    public String getTipoProceso() {
        return tipoProceso;
    }

    /**
     * @param tipoProceso
     */
    public void setTipoProceso(String tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    /**
     * @return
     */
    public Integer getTotalCargados() {
        return totalCargados;
    }

    /**
     * @param totalCargados
     */
    public void setTotalCargados(Integer totalCargados) {
        this.totalCargados = totalCargados;
    }

    /**
     * @return
     */
    public Integer getTotalError() {
        return totalError;
    }

    /**
     * @param totalError
     */
    public void setTotalError(Integer totalError) {
        this.totalError = totalError;
    }

    /**
     * @return
     */
    public Integer getTotalNuevos() {
        return totalNuevos;
    }

    /**
     * @param totalNuevos
     */
    public void setTotalNuevos(Integer totalNuevos) {
        this.totalNuevos = totalNuevos;
    }

    /**
     * @return
     */
    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    /**
     * @param totalRegistros
     */
    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public Integer getTotalProtocolo() {
        return totalProtocolo;
    }

    public void setTotalProtocolo(Integer totalProtocolo) {
        this.totalProtocolo = totalProtocolo;
    }
    
}
