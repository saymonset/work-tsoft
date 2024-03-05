package com.indeval.portallegado.middleware.services.tesoreria.liquidaciondecretos;

import org.springframework.validation.Errors;

import com.indeval.portallegado.middleware.services.AbstractBaseDTO;
import com.indeval.sidv.decretos.persistence.model.vo.AgenteVO;

public class LiquidacionDecretosDetalleParams extends AbstractBaseDTO {

    /**
     * Constante de serializacion
     */
    private static final long serialVersionUID = 1L;

    private AgenteVO agente;
    
    private String folioVariable;
    
    private String folioFija;
    
    private Integer idDerecho;

    private Integer idTipoDerecho;

    private Integer idTipoEjercicio;
    
    
    public Integer getIdDerecho() {
        return idDerecho;
    }

    public void setIdDerecho(Integer idDerecho) {
        this.idDerecho = idDerecho;
    }

    public Integer getIdTipoDerecho() {
        return idTipoDerecho;
    }

    public void setIdTipoDerecho(Integer idTipoDerecho) {
        this.idTipoDerecho = idTipoDerecho;
    }

    public Integer getIdTipoEjercicio() {
        return idTipoEjercicio;
    }

    public void setIdTipoEjercicio(Integer idTipoEjercicio) {
        this.idTipoEjercicio = idTipoEjercicio;
    }

    public AgenteVO getAgente() {
        return agente;
    }

    public void setAgente(AgenteVO agente) {
        this.agente = agente;
    }

    public String getFolioFija() {
        return folioFija;
    }

    public void setFolioFija(String folioFija) {
        this.folioFija = folioFija;
    }

    public String getFolioVariable() {
        return folioVariable;
    }

    public void setFolioVariable(String folioVariable) {
        this.folioVariable = folioVariable;
    }

    public void validate(Object target, Errors errors) {
        // TODO Auto-generated method stub

    }

}
