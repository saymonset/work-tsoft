/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class GetTraspazosDivIntParams extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    /** TRASPASANTE = T */
    static final String TRASPASANTE = "T";

    /** RECEPTOR = R */
    static final String RECEPTOR = "R";

    /** AMBOS = A */
    static final String AMBOS = "A";

    private AgenteVO agenteFirmado;

    private String rol;

    private Date traspazosDia;

    private AgenteVO contraparte;

    private EmisionVO claveValor;

    private BigDecimal tituloTraspansante;

    private Integer folioControl;

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
     * @return AgenteVO
     */
    public AgenteVO getContraparte() {
        return contraparte;
    }

    /**
     * @param contraparte
     */
    public void setContraparte(AgenteVO contraparte) {
        this.contraparte = contraparte;
    }

    /**
     * @return Integer
     */
    public Integer getFolioControl() {
        return folioControl;
    }

    /**
     * @param folioControl
     */
    public void setFolioControl(Integer folioControl) {
        this.folioControl = folioControl;
    }

    /**
     * Devuelve el tipo de rol.
     * 
     * @return True si es receptor, false traspasante. Null ambos
     */
    public String getRol() {
        return rol;
    }

    /**
     * Asigna el tipo de rol. True para rol receptor, false para traspasante.
     * Null se consideran ambos.
     * 
     * @param rol
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getTituloTraspansante() {
        return tituloTraspansante;
    }

    /**
     * @param tituloTraspansante
     */
    public void setTituloTraspansante(BigDecimal tituloTraspansante) {
        this.tituloTraspansante = tituloTraspansante;
    }

    /**
     * @return Date
     */
    public Date getTraspazosDia() {
        return traspazosDia;
    }

    /**
     * @param traspazosDia
     */
    public void setTraspazosDia(Date traspazosDia) {
        this.traspazosDia = clona(traspazosDia);
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
     * Valida si el rol es Traspasante, Receptor o Ambos
     * 
     * @throws BusinessException
     */
    public void validaRol() throws BusinessException {
        if (StringUtils.isBlank(this.rol)) {
            throw new BusinessException("El objeto de parametros tiene el atributo requerido "
                    + "rol NULL o VACIO");
        }
        if (!this.rol.equalsIgnoreCase(RECEPTOR) && !this.rol.equalsIgnoreCase(TRASPASANTE)
                && !this.rol.equalsIgnoreCase(AMBOS)) {
            throw new BusinessException("[" + this.rol + "] es un valor invalido para "
                    + "el atributo requerido rol");
        }
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

}