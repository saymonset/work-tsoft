/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.util.Date;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class GetEstadocuentaMercadoVO {
	
	/** Constante de Serializacion */
	private static final long serialVersionUID = 1L;

    /** */
	private AgentePK agenteFirmado;
	
    /** */
	private EmisionPK claveValor;
	
    /** */
    private Date fechaAyer;
    
    /** */
	private Date fechaOperacion;
    
    /** */
    private Date fechaOperacionInicioDia;
    
    /** */
    private Date fechaOperacionFinDia;
	
    /** */
	private String origen;
	
    /** */
	private String aplicacion;
	
    /** */
	private String [] tipoOperacion;
    
    /** */
    private PageVO pagina;
    
    /** */
    private boolean rol;
    
    /** */
    private boolean export;

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
     * @return AgentePK
     */
    public AgentePK getAgenteFirmado() {
        return agenteFirmado;
    }

    /**
     * @param agenteFirmado
     */
    public void setAgenteFirmado(AgentePK agenteFirmado) {
        this.agenteFirmado = agenteFirmado;
    }

    /**
     * @return EmisionPK
     */
    public EmisionPK getClaveValor() {
        return claveValor;
    }

    /**
     * @param claveValor
     */
    public void setClaveValor(EmisionPK claveValor) {
        this.claveValor = claveValor;
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
     * @return the fechaAyer
     */
    public Date getFechaAyer() {
        return fechaAyer;
    }

    /**
     * @param fechaAyer the fechaAyer to set
     */
    public void setFechaAyer(Date fechaAyer) {
        this.fechaAyer = fechaAyer;
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
        this.fechaOperacion = fechaOperacion;
    }

    /**
     * @return the fechaOperacionFinDia
     */
    public Date getFechaOperacionFinDia() {
        return fechaOperacionFinDia;
    }

    /**
     * @param fechaOperacionFinDia the fechaOperacionFinDia to set
     */
    public void setFechaOperacionFinDia(Date fechaOperacionFinDia) {
        this.fechaOperacionFinDia = fechaOperacionFinDia;
    }

    /**
     * @return the fechaOperacionInicioDia
     */
    public Date getFechaOperacionInicioDia() {
        return fechaOperacionInicioDia;
    }

    /**
     * @param fechaOperacionInicioDia the fechaOperacionInicioDia to set
     */
    public void setFechaOperacionInicioDia(Date fechaOperacionInicioDia) {
        this.fechaOperacionInicioDia = fechaOperacionInicioDia;
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
     * @return PageVO
     */
    public PageVO getPagina() {
        return pagina;
    }

    /**
     * @param pagina
     */
    public void setPagina(PageVO pagina) {
        this.pagina = pagina;
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
	 * @return boolean
	 */
	public boolean isRol() {
		return rol;
	}

	/**
	 * @param rol
	 */
	public void setRol(boolean rol) {
		this.rol = rol;
	}
    
}
