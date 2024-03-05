/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.filetransfer;

import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import org.springframework.validation.Errors;
/**
 * @author Jos&eacute; Avil&eacute;s
 *
 */
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
    
    private String horaCarga;
    

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

    /**
     * @return AgenteVO
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
     * @return Date
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
     * @return String
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
     * @return Integer
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
     * @return Integer
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
     * @return Integer
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
     * @return Integer
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

    /**
     * @return Integer
     */
    public Integer getTotalProtocolo() {
        return totalProtocolo;
    }

    /**
     * @param totalProtocolo
     */
    public void setTotalProtocolo(Integer totalProtocolo) {
        this.totalProtocolo = totalProtocolo;
    }

	public String getHoraCarga() {
		return horaCarga;
	}

	public void setHoraCarga(String horaCarga) {
		this.horaCarga = horaCarga;
	}

}
