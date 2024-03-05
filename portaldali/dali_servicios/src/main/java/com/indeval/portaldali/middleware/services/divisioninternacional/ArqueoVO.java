/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.io.Serializable;

import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ArqueoVO implements Serializable {
    /**
     * Constante de serializacion
     */
    private static final long serialVersionUID = 1L;

    /** Emisiones Arqueadas */
    private EmisionArqueadaVO[] DatosEmisiones;

    /** Lista de Emisiones */
    private EmisionVO[] listaEmisiones;
    
    /** Totales del Arqueo */
    private TotalArqueoVO totalArqueoVO;

    /**
     * Obtiene los Totales de las Emisiones Arqueadas
     * 
     * @return Totales de las Emisiones Arqueadas
     */
    public TotalArqueoVO getTotalArqueoVO() {
        return totalArqueoVO;
    }

    /**
     * Establece los Totales de las Emisiones Arquedas
     * 
     * @param totalArqueoVO Totales de las Emisiones Arquedas a establecer
     */
    public void setTotalArqueoVO(TotalArqueoVO totalArqueoVO) {
        this.totalArqueoVO = totalArqueoVO;
    }

    /**
     * Obtiene los datos de las Emisiones Arqueadas
     * 
     * @return Datos de las Emisiones Arqueadas
     */
    public EmisionArqueadaVO[] getDatosEmisiones() {
        return DatosEmisiones;
    }

    /**
     * Establece los datos de las Emisiones Arquedas
     * 
     * @param datosEmisiones
     *            Datos de las Emisiones Arqueadas a establecer
     */
    public void setDatosEmisiones(EmisionArqueadaVO[] datosEmisiones) {
        DatosEmisiones = datosEmisiones;
    }

    /**
     * Obtiene la Lista de Emisiones
     * 
     * @return Lista de Emisiones
     */
    public EmisionVO[] getListaEmisiones() {
        return listaEmisiones;
    }

    /**
     * Establece la Lista de Emisiones
     * 
     * @param listaEmisiones
     *            Lista de Emisiones a establecer
     */
    public void setListaEmisiones(EmisionVO[] listaEmisiones) {
        this.listaEmisiones = listaEmisiones;
    }

}
