/*
 * Copyright (c) 2020 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;

/**
 * Clase para representaci&oacute;n de la informaci&oacute;n de informaci&oacute;n de un registro de sic emision.
 */
public class SicEmisionVO implements Serializable {

    /** serialVersionUID */
	private static final long serialVersionUID = 23L;

    /**Id del registro*/
	private Long idSicEmision;

    /**Id de la cuenta nombrada*/
    private Long idCuentaNombrada;

    /**Id de la emision*/
    private Long idEmision;
    
    /**Estatus del registro*/
    private String estatusRegistro;
    
    /**Constructor por omisi&oacute;n*/
    public SicEmisionVO() {
        super();
	}

    /**
     * Constructor con todos las propiedades
     * @param pidSicEmision
     * @param pidCuentaNombrada
     * @param pidEmision
     * @param pestatusRegistro
     */
    public SicEmisionVO(Long pidSicEmision, Long pidCuentaNombrada, Long pidEmision, String pestatusRegistro) {
        this.idSicEmision = pidSicEmision;
        this.idCuentaNombrada = pidCuentaNombrada;
        this.idEmision = pidEmision;
        this.estatusRegistro = pestatusRegistro;
    }

    public Long getIdSicEmision() {
        return idSicEmision;
    }

    public void setIdSicEmision(Long idSicEmision) {
        this.idSicEmision = idSicEmision;
    }

    public Long getIdCuentaNombrada() {
        return idCuentaNombrada;
    }

    public void setIdCuentaNombrada(Long idCuentaNombrada) {
        this.idCuentaNombrada = idCuentaNombrada;
    }

    public Long getIdEmision() {
        return idEmision;
    }

    public void setIdEmision(Long idEmision) {
        this.idEmision = idEmision;
    }

    public String getEstatusRegistro() {
        return estatusRegistro;
    }

    public void setEstatusRegistro(String estatusRegistro) {
        this.estatusRegistro = estatusRegistro;
    }

    @Override
    public String toString() {
        return "SicEmisionVO=[idSicEmision=" + idSicEmision + ", idCuentaNombrada=" + idCuentaNombrada + 
                ", idEmision=" + idEmision + ", estatusRegistro=" + estatusRegistro + "]";
    }

}
