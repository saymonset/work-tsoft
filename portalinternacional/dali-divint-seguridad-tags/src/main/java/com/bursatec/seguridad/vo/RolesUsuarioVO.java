/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.bursatec.seguridad.vo;

import java.io.Serializable;

/**
 * @author Emigdio
 *
 */
public class RolesUsuarioVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean administrador;
    
    private boolean auditor;
    
    private boolean consultas;
    
    private boolean ejecutivoCentroBursatil;

	/**
	 * Obtiene el campo administrador
	 * @return  administrador
	 */
	public boolean isAdministrador() {
		return administrador;
	}

	/**
	 * Asigna el valor del campo administrador
	 * @param administrador el valor de administrador a asignar
	 */
	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	/**
	 * Obtiene el campo auditor
	 * @return  auditor
	 */
	public boolean isAuditor() {
		return auditor;
	}

	/**
	 * Asigna el valor del campo auditor
	 * @param auditor el valor de auditor a asignar
	 */
	public void setAuditor(boolean auditor) {
		this.auditor = auditor;
	}

	/**
	 * Obtiene el campo consultas
	 * @return  consultas
	 */
	public boolean isConsultas() {
		return consultas;
	}

	/**
	 * Asigna el valor del campo consultas
	 * @param consultas el valor de consultas a asignar
	 */
	public void setConsultas(boolean consultas) {
		this.consultas = consultas;
	}

	/**
	 * Obtiene el campo ejecutivoCentroBursatil
	 * @return  ejecutivoCentroBursatil
	 */
	public boolean isEjecutivoCentroBursatil() {
		return ejecutivoCentroBursatil;
	}

	/**
	 * Asigna el valor del campo ejecutivoCentroBursatil
	 * @param ejecutivoCentroBursatil el valor de ejecutivoCentroBursatil a asignar
	 */
	public void setEjecutivoCentroBursatil(boolean ejecutivoCentroBursatil) {
		this.ejecutivoCentroBursatil = ejecutivoCentroBursatil;
	}
}
