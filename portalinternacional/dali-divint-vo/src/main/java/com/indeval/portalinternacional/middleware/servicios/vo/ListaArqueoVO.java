/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ListaArqueoVO implements Serializable {
    
	/**
     * Constante de serializaci&oacute;n 
     */
    private static final long serialVersionUID = 1L;
   
    /** Lista de Datos del Agente */
    private AgenteArqueoVO[] agentesArqueo;
    
    /** Total del Arqueo */
    private TotalListaArqueoVO totalListaArqueo;

    /**
     * Obtiene la Lista de los Datos de los Agentes
     * 
     * @return Lista de los Datos de los Agentes
     */
    public AgenteArqueoVO[] getAgentesArqueo() {
        return agentesArqueo;
    }

    /**
     * Establece la Lista de los Datos de los Agentes
     * 
     * @param agentesArqueo Lista de los Datos de los Agentes a establecer
     */
    public void setAgentesArqueo(AgenteArqueoVO[] agentesArqueo) {
        this.agentesArqueo = agentesArqueo;
    }

    /**
     * Obtiene los Totales del Arqueo
     * 
     * @return Totales del Arqueo
     */
    public TotalListaArqueoVO getTotalListaArqueo() {
        return totalListaArqueo;
    }

    /**
     * Establece los Totales del Arqueo
     * 
     * @param totalListaArqueo Totales del Arqueo a establecer
     */
    public void setTotalListaArqueo(TotalListaArqueoVO totalListaArqueo) {
        this.totalListaArqueo = totalListaArqueo;
    }
}
