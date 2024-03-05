/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.filetransfer;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TotalesProcesoVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private PaginaVO paginaVO;

    private Integer registrosACargar;

    private Integer registrosConError;
    
    private String contenidoArchivo;
    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

    
    /**
     * @return the paginaVO
     */
    public PaginaVO getPaginaVO() {
        return paginaVO;
    }

    /**
     * @param paginaVO
     *            the paginaVO to set
     */
    public void setPaginaVO(PaginaVO paginaVO) {
        this.paginaVO = paginaVO;
    }

    /**
     * @return the registrosACargar
     */
    public Integer getRegistrosACargar() {
        return registrosACargar;
    }

    /**
     * @param registrosACargar
     *            the registrosACargar to set
     */
    public void setRegistrosACargar(Integer registrosACargar) {
        this.registrosACargar = registrosACargar;
    }

    /**
     * @return the registrosConError
     */
    public Integer getRegistrosConError() {
        return registrosConError;
    }

    /**
     * @param registrosConError
     *            the registrosConError to set
     */
    public void setRegistrosConError(Integer registrosConError) {
        this.registrosConError = registrosConError;
    }


	public String getContenidoArchivo() {
		return contenidoArchivo;
	}


	public void setContenidoArchivo(String contenidoArchivo) {
		this.contenidoArchivo = contenidoArchivo;
	}
    
    
}
