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
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class GetEstadocuentaMercadoParams extends AbstractBaseDTO{
	
	/** Constante de Serializacion */
	private static final long serialVersionUID = 1L;

    /** agente firmado (id, folio), si se envia la cuenta (id, folio, cuenta) */
	private AgenteVO agenteFirmado;
	
    /** Clave Valor (tv, emisora, serie , cupon) */
	private EmisionVO claveValor;
		
    /** 
     * Bandera para indicar que se invoca el metodo para obtener la lista de cuentas 
     * del agente y las emisiones de la cuenta enviada, si no se envio ninguna cuenta, 
     * se  recupera las emisiones de la primera cuenta de la lista de cuentas.
     * (true, para que la pantalla pueda recuperar los arreglos de cuentas y emisiones
     * para armar la navegacion).
     * (false, para que el servicio pueda recuperar las emisiones de la cuenta consultada).
     */
	private boolean obtenerListas;
    
    /**
     * Bandera para indicar que se invoca el metodo para obtener la lista de cuentas 
     * del agente y las emisiones de la cuenta enviada, con filtro.
     * (false, para navegar por emisiones, no toma en cuenta el filtro clave-valor).
     * (true, para navegar por cuentas, toma en cuenta el filtro clave-valor).
     */
    private boolean conFiltro;
    
    /** Fecha de Operacion */
	private Date fechaOperacion;
	
    /** origen */
	private String origen;
	
    /** aplicacion */
	private String aplicacion;
	
    /** Arreglo que indica el/los tipo(s) de operacion (V, T, D, R) que se quiere recuperar */
	private String [] tipoOperacion;
    
    /** */
    private PaginaVO paginaVO;
    
    /** Bandera para indicar que se requiere exportar */
    private boolean export;
    
    /**
     * Constructor por defecto (construye el objeto con la bandera de export en false)
     */
    public GetEstadocuentaMercadoParams(){
        this.export = false;
    }
	
	/**
	 * @return boolean
	 */
	public boolean isExport() {
        return export;
    }

    /**
     * @param export
     */
    public void setExport(boolean export) {
        this.export = export;
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
	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	/**
	 * @param fechaOperacion
	 */
	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = clona(fechaOperacion);
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
	 * @return String[]
	 */
	public String[] getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * @param tipoOperacion
     */
    public void setTipoOperacion(String[] tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    /**
     * Valida que el agente firmado (id, folio) no este nulo
     * y si el origen y la aplicacion no son nulos, elimina los espacios en blanco.
     * @throws BusinessException
     */
    public void validaParams() throws BusinessException {
        
        try {
            
            Assert.notNull(this.getAgenteFirmado(), "No se recibio el agente firmado");
            this.getAgenteFirmado().tieneClaveValida();
            Assert.notNull(this.getFechaOperacion(),
                    "No se recibio la fecha de operacion a consultar");
        }
        catch (IllegalArgumentException e) {
            //Se captura la IllegalArgumentException y se construye la BusinessException a arrojar.
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), e);
        }

        //Se validan el origen y la aplicacion y se retiran los espacios en blanco de estos, si vienen
        if(StringUtils.isNotBlank(this.getOrigen())){
            this.setOrigen(this.getOrigen().trim());
        }
        if(StringUtils.isNotBlank(this.getAplicacion())){
            this.setAplicacion(this.getAplicacion().trim());
        }
        
    }
    
    /**
     * @return the obtenerListas
     */
    public boolean isObtenerListas() {
        return obtenerListas;
    }

    /**
     * @param obtenerListas the obtenerListas to set
     */
    public void setObtenerListas(boolean obtenerListas) {
        this.obtenerListas = obtenerListas;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {

    }

    /**
     * @return the conFiltro
     */
    public boolean isConFiltro() {
        return conFiltro;
    }

    /**
     * @param conFiltro the conFiltro to set
     */
    public void setConFiltro(boolean conFiltro) {
        this.conFiltro = conFiltro;
    }
    
}
