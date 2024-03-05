/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

import org.springframework.util.Assert;
import org.springframework.validation.Errors;
/**
 * Objeto de parametros para el estado de cuenta unico
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EstadoCuentaUnicoParams extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    /** */
    private AgenteVO agenteFirmado;

    /** */
    private Date fechaMovimiento;

    /** */
    private EmisionVO emisionVO;

    /** Pagina */
    private PaginaVO paginaVO;

    /** Bandera para indicar la exportacion */
    private boolean isExport;

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
     * @return EmisionVO
     */
    public EmisionVO getEmisionVO() {
        return emisionVO;
    }

    /**
     * @param emisionVO
     */
    public void setEmisionVO(EmisionVO emisionVO) {
        this.emisionVO = emisionVO;
    }

    /**
     * @return Date
     */
    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    /**
     * @param fechaMovimiento
     */
    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    /**
     * @return boolean
     */
    public boolean isExport() {
        return isExport;
    }

    /**
     * @param isExport
     */
    public void setExport(boolean isExport) {
        this.isExport = isExport;
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
     * Valida parametros
     * 
     * @throws BusinessException
     */
    public void validaParams() throws BusinessException {

        try {
            Assert.notNull(this.getAgenteFirmado(),
                    "No se recibieron parametros para el agente firmado");
            this.getAgenteFirmado().tieneClaveValida();
            Assert.notNull(this.getFechaMovimiento(),
                    "El parametro fecha de movimiento es requerido");
        }
        catch (IllegalArgumentException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

}
