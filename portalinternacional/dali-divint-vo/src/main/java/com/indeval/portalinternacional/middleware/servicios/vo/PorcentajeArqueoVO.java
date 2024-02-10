/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;

public class PorcentajeArqueoVO implements Serializable {
    
    /**
     * Constante de serializacion 
     */
    private static final long serialVersionUID = 1L;
    
    /**Porcentaje por Agente */    
    private PorcentajeAgenteVO[] porcentajesAgentes;
    
    /**Porcentaje por Cuentas */
    private PorcentajeCuentaVO[] porcentajesCuentas;

    /**
     * Obtiene el Porcentaje de los Agentes
     * 
     * @return Porcentaje de los Agentes
     */
    public PorcentajeAgenteVO[] getPorcentajesAgentes() {
        return porcentajesAgentes;
    }

    /**
     * Establece el Porcentaje de los Agentes
     * 
     * @param porcentajesAgentes Porcentaje de los Agentes a establecer
     */
    public void setPorcentajesAgentes(PorcentajeAgenteVO[] porcentajesAgentes) {
        this.porcentajesAgentes = porcentajesAgentes;
    }

    /**
     * Obtiene el Porcentaje de las Cuentas
     * 
     * @return Porcentaje de las Cuentas
     */
    public PorcentajeCuentaVO[] getPorcentajesCuentas() {
        return porcentajesCuentas;
    }

    /**
     * Establece el Porcentaje de las Cuentas
     * 
     * @param porcentajesCuentas Porcentaje de las Cuentas a establecer
     */
    public void setPorcentajesCuentas(PorcentajeCuentaVO[] porcentajesCuentas) {
        this.porcentajesCuentas = porcentajesCuentas;
    }

}
