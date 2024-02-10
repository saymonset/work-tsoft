/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.util.vo;

import java.math.BigDecimal;

/**
 * Objeto que almacena la informacion del proceso de trasferencia de archivos 
 * 
 * @author Esteban Herrera
 *
 */
public class ProcessInfoVO {
    public static final String CARGANDO = "Cargando";
    public static final String GUARDANDO = "Guardando";
    public static final String VALIDANDO = "Validando";
    public static final String CONFIRMACION = "Confirmar";
    public static final String PROCESANDO = "Procesando";
    public static final String TERMINADO= "Terminado";
    public static final String ERROR = "Error";
    
    private String idProceso;
    private String usuario;
    private BigDecimal porcentajeTerminado;
    private String status;
    private char abort;
    
    /**
	 * @return the idProceso
	 */
	public String getIdProceso() {
		return idProceso;
	}
	/**
	 * @param idProceso the idProceso to set
	 */
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}
	public BigDecimal getPorcentajeTerminado() {
        return porcentajeTerminado;
    }
    public void setPorcentajeTerminado(BigDecimal porcentajeTerminado) {
        this.porcentajeTerminado = porcentajeTerminado;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public char getAbort() {
        return abort;
    }
    public void setAbort(char abort) {
        this.abort = abort;
    } 
}
