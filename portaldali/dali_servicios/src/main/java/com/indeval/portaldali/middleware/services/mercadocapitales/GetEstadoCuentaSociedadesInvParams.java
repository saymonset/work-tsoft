/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class GetEstadoCuentaSociedadesInvParams extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private AgenteVO agenteFirmado;

    private String emisora;

    private String tv;

    private PaginaVO paginaVO;

    private boolean exportacion;

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
     * @return String
     */
    public String getEmisora() {
        return emisora;
    }

    /**
     * @param emisora
     */
    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }

    /**
     * @return boolean
     */
    public boolean isExportacion() {
        return exportacion;
    }

    /**
     * @param exportacion
     */
    public void setExportacion(boolean exportacion) {
        this.exportacion = exportacion;
    }

    /**
     * @return PaginaVO
     */
    public PaginaVO getPaginaVO() {
        return paginaVO;
    }

    /**
     * @param paginaVO
     */
    public void setPaginaVO(PaginaVO paginaVO) {
        this.paginaVO = paginaVO;
    }

    /**
     * @return String
     */
    public String getTv() {
        return tv;
    }

    /**
     * @param tv
     */
    public void setTv(String tv) {
        this.tv = tv;
    }
    
    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

}
