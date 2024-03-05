/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */

package com.indeval.portallegado.middleware.services.tesoreria.liquidaciondecretos;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

import com.indeval.portallegado.middleware.services.AbstractBaseDTO;
//import com.indeval.portallegado.middleware.services.modelo.AgenteVO;
import com.indeval.portallegado.middleware.services.modelo.BusinessException;
import com.indeval.sidv.decretos.persistence.model.vo.AgenteVO;
import com.indeval.sidv.decretos.persistence.model.vo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class LiquidacionDecretosParams extends AbstractBaseDTO {

    /**
     * Constante de serializacion
     */
    private static final long serialVersionUID = 1L;

	/** Indica que el tipo de operacion es Todos*/
    public static final String TODOS = "TODOS";

    private Date fechaIni;

    private Date fechaFin;

    private AgenteVO agente;

    private String idTipoEjercicio;

    private String idTipoMoneda;

    private EmisionVO emision;

    private String tipoConsulta;

    public AgenteVO getAgente() {
        return agente;
    }
    
    public void setAgente(AgenteVO agente) {
        this.agente = agente;
    }

    public EmisionVO getEmision() {
        return emision;
    }

    public void setEmision(EmisionVO emision) {
        this.emision = emision;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = clona(fechaFin);
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = clona(fechaIni);
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getTipoEjercicio() {
        return idTipoEjercicio;
    }

    public void setTipoEjercicio(String idTipoEjercicio) {
        this.idTipoEjercicio = idTipoEjercicio;
    }

    public String getTipoMoneda() {
        return idTipoMoneda;
    }

    public void setTipoMoneda(String idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }
    
    public void validaIdTipoMoneda() throws BusinessException{
    	if (StringUtils.isBlank(this.idTipoMoneda) || 
    			this.idTipoMoneda.equalsIgnoreCase(TODOS)){
    		throw new BusinessException("No se recibio el tipo de moneda o su valor es invalido");
    	}
    }

    /**
     * Valida que el objeto tenga todos los parametros requeridos
     * @throws BusinessException
     */
    public void esValido() throws BusinessException {
        Assert.notNull(this.getAgente(), "El objeto de parametros tiene el AGENTE NULL");
        
            if (StringUtils.isBlank(this.getAgente().getId())) {
                throw new BusinessException("Error: El agente tiene NULL o VACIO el atributo ID");
            }
            if (StringUtils.isBlank(this.getAgente().getFolio())) {
                throw new BusinessException("Error: El agente tiene NULL o VACIO el atributo FOLIO");
            }
//        this.getAgente().tieneClaveValida();
        if(StringUtils.isBlank(this.getTipoConsulta())){    //Se verifica el tipo de consulta
            throw new BusinessException("El objeto params tiene el tipo de consulta NULL o VACIO");
        }
    }
    
    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object obj, Errors errors) {
    }

}
