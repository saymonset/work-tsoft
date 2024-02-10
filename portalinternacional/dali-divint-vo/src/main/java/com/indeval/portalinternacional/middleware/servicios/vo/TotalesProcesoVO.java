/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.servicios.modelo.AbstractBaseDTO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;

public class TotalesProcesoVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private PaginaVO paginaVO;

    private Integer registrosACargar;

    private Integer registrosConError;
    
    private String tipoFileTransfer;
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


	public String getTipoFileTransfer() {
		return tipoFileTransfer;
	}


	public void setTipoFileTransfer(String tipoFileTransfer) {
		this.tipoFileTransfer = tipoFileTransfer;
	}

}
