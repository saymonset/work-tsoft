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
public class GetOperacionPendienteIncumplidaParams extends AbstractBaseDTO{

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private AgenteVO agenteFirmado;
    
    private String origen;
    
    private String aplicacion; 
    
    private Boolean compradorVendedor;
    
    private PaginaVO paginaVO;
    
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
    public String getAplicacion() {
        return aplicacion;
    }

    /**
     * @param aplicacion
     */
    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    /**
     * @return Boolean
     */
    public Boolean getCompradorVendedor() {
        return compradorVendedor;
    }

    /**
     * @param compradorVendedor
     */
    public void setCompradorVendedor(Boolean compradorVendedor) {
        this.compradorVendedor = compradorVendedor;
    }

    /** 
     * @return String
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen
     */
    public void setOrigen(String origen) {
        this.origen = origen;
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
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

}
