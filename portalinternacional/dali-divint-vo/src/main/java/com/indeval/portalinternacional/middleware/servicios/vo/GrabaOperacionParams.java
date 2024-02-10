/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.util.HashMap;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.servicios.modelo.AbstractBaseDTO;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class GrabaOperacionParams extends AbstractBaseDTO {

    /**
     * Constante de serialización
     */
    private static final long serialVersionUID = 1L;
    
    private TraspasoLibrePagoVO traspasoLibrePagoVO;
    
    private TraspasoContraPagoVO traspasoContraPagoVO;
    
    private boolean isRecepcion;
    
    private HashMap datosAdicionales;
    
    private String origenRegistro;
    
    private String isoFirmado;
    

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {}

    /**
     * @return the datosAdicionales
     */
    public HashMap getDatosAdicionales() {
        return datosAdicionales;
    }

    /**
     * @param datosAdicionales the datosAdicionales to set
     */
    public void setDatosAdicionales(HashMap datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
    }

    /**
     * @return the isoFirmado
     */
    public String getIsoFirmado() {
        return isoFirmado;
    }

    /**
     * @param isoFirmado the isoFirmado to set
     */
    public void setIsoFirmado(String isoFirmado) {
        this.isoFirmado = isoFirmado;
    }

    /**
     * @return the isRecepcion
     */
    public boolean isRecepcion() {
        return isRecepcion;
    }

    /**
     * @param isRecepcion the isRecepcion to set
     */
    public void setRecepcion(boolean isRecepcion) {
        this.isRecepcion = isRecepcion;
    }

    /**
     * @return the origenRegistro
     */
    public String getOrigenRegistro() {
        return origenRegistro;
    }

    /**
     * @param origenRegistro the origenRegistro to set
     */
    public void setOrigenRegistro(String origenRegistro) {
        this.origenRegistro = origenRegistro;
    }

    /**
     * @return the traspasoLibrePagoVO
     */
    public TraspasoLibrePagoVO getTraspasoLibrePagoVO() {
        return traspasoLibrePagoVO;
    }

    /**
     * @param traspasoLibrePagoVO the traspasoLibrePagoVO to set
     */
    public void setTraspasoLibrePagoVO(TraspasoLibrePagoVO traspasoLibrePagoVO) {
        this.traspasoLibrePagoVO = traspasoLibrePagoVO;
    }

	public TraspasoContraPagoVO getTraspasoContraPagoVO() {
		return traspasoContraPagoVO;
	}

	public void setTraspasoContraPagoVO(TraspasoContraPagoVO traspasoContraPagoVO) {
		this.traspasoContraPagoVO = traspasoContraPagoVO;
	}
    

}
