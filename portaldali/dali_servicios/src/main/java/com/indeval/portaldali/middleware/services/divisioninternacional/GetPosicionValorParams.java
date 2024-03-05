/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class GetPosicionValorParams extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    private String posicion;

    private String altaCatalogo;

    private Date fecha;

    private EmisionVO claveValor;

    private AgenteVO agenteFirmado;

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
    public String getAltaCatalogo() {
        return altaCatalogo;
    }

    /**
     * Asgina la clave de alta. valores de asignacion: 'Todos' 'CPOS', 'ADR',
     * 'ADCP', 'IADC', 'BADC', 'GADC' &oacute; 'VIV' Cualquier valor distinto a los
     * establecidos ser&aacute; ingnorado por el servicio
     * 
     * @param altaCatalogo
     *            Valor de la clave de alta.
     */
    public void setAltaCatalogo(String altaCatalogo) {
        this.altaCatalogo = altaCatalogo;
    }

    /**
     * @return EmisionVO
     */
    public EmisionVO getClaveValor() {
        return claveValor;
    }

    /**
     * @param claveValor
     */
    public void setClaveValor(EmisionVO claveValor) {
        this.claveValor = claveValor;
    }

    /**
     * @return Date
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = clona(fecha);
    }

    /**
     * @return String
     */
    public String getPosicion() {
        return posicion;
    }

    /**
     * Asigna el valor de la posici&oacute;n. Valores de asignacion: 'Globales'
     * 'Extranjera' '54', '94', '97', '98' &oacute; '99' Cualquier valor distinto a los
     * establecidos ser&aacute; ingnorado por el servicio
     * 
     * @param posicion
     *            Valor de la posici&oacute;n
     */
    public void setPosicion(String posicion) {
        this.posicion = posicion;
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
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

}