/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.io.Serializable;

import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EmisionArqueadaVO implements Serializable {
    /**
     * Constante de serializacion 
     */
    private static final long serialVersionUID = 1L;
    
    /**Emision Arqueda */
    private EmisionVO emision;
    
    /**Lista de los Arqueos de la Emision */
    private ListaArqueoVO listaArqueos;

    /**Porcentajes del Arqueo */
    private PorcentajeArqueoVO porcentajesArqueo;

    /**
     * Obtiene la Lista de Arqueos
     * 
     * @return Lista de Arqueos
     */
    public ListaArqueoVO getListaArqueos() {
        return listaArqueos;
    }

    /**
     * Establece la Lista de Arqueos
     * 
     * @param listaArqueos Lista de Arqueos a Establecer
     */
    public void setListaArqueos(ListaArqueoVO listaArqueos) {
        this.listaArqueos = listaArqueos;
    }

    /**
     * Obtiene el Porcentaje del Arqueo
     * 
     * @return Porcentaje del Arqueo
     */
    public PorcentajeArqueoVO getPorcentajesArqueo() {
        return porcentajesArqueo;
    }

    /**
     * Establece el Porcentaje del Arqueo
     * 
     * @param porcentajesArqueo Porcentaje del Arqueo a establecer
     */
    public void setPorcentajesArqueo(PorcentajeArqueoVO porcentajesArqueo) {
        this.porcentajesArqueo = porcentajesArqueo;
    }

    /**
     * Obtiene la Emision Arqueada
     * 
     * @return Emision Arqueada
     */
    public EmisionVO getEmision() {
        return emision;
    }

    /**
     * Establece la Emision Arqueada
     * 
     * @param emision Emision Arqueada a establecer
     */
    public void setEmision(EmisionVO emision) {
        this.emision = emision;
    }    
    
}
