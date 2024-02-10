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
public class TicketInfoVO implements Serializable { 

	private static final long serialVersionUID = 1L;

	private String claveUsuario;
    
    private String direccionIp;
    
    private Long idInstitucion;
    
    private Long idPerfil;
    
    private Long idSistema;
    
    private String nombreInstitucion;
    
    private String nombreSistema;
    
    private RolesUsuarioVO rolesUsuario;
    
    private String ticket;
    
    private String tipoUsuario;

	/**
	 * Obtiene el campo claveUsuario
	 * @return  claveUsuario
	 */
	public String getClaveUsuario() {
		return claveUsuario;
	}

	/**
	 * Asigna el valor del campo claveUsuario
	 * @param claveUsuario el valor de claveUsuario a asignar
	 */
	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	/**
	 * Obtiene el campo direccionIp
	 * @return  direccionIp
	 */
	public String getDireccionIp() {
		return direccionIp;
	}

	/**
	 * Asigna el valor del campo direccionIp
	 * @param direccionIp el valor de direccionIp a asignar
	 */
	public void setDireccionIp(String direccionIp) {
		this.direccionIp = direccionIp;
	}

	/**
	 * Obtiene el campo idInstitucion
	 * @return  idInstitucion
	 */
	public Long getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * Asigna el valor del campo idInstitucion
	 * @param idInstitucion el valor de idInstitucion a asignar
	 */
	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * Obtiene el campo idPerfil
	 * @return  idPerfil
	 */
	public Long getIdPerfil() {
		return idPerfil;
	}

	/**
	 * Asigna el valor del campo idPerfil
	 * @param idPerfil el valor de idPerfil a asignar
	 */
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	/**
	 * Obtiene el campo idSistema
	 * @return  idSistema
	 */
	public Long getIdSistema() {
		return idSistema;
	}

	/**
	 * Asigna el valor del campo idSistema
	 * @param idSistema el valor de idSistema a asignar
	 */
	public void setIdSistema(Long idSistema) {
		this.idSistema = idSistema;
	}

	/**
	 * Obtiene el campo nombreInstitucion
	 * @return  nombreInstitucion
	 */
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	/**
	 * Asigna el valor del campo nombreInstitucion
	 * @param nombreInstitucion el valor de nombreInstitucion a asignar
	 */
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	/**
	 * Obtiene el campo nombreSistema
	 * @return  nombreSistema
	 */
	public String getNombreSistema() {
		return nombreSistema;
	}

	/**
	 * Asigna el valor del campo nombreSistema
	 * @param nombreSistema el valor de nombreSistema a asignar
	 */
	public void setNombreSistema(String nombreSistema) {
		this.nombreSistema = nombreSistema;
	}

	/**
	 * Obtiene el campo rolesUsuario
	 * @return  rolesUsuario
	 */
	public RolesUsuarioVO getRolesUsuario() {
		return rolesUsuario;
	}

	/**
	 * Asigna el valor del campo rolesUsuario
	 * @param rolesUsuario el valor de rolesUsuario a asignar
	 */
	public void setRolesUsuario(RolesUsuarioVO rolesUsuario) {
		this.rolesUsuario = rolesUsuario;
	}

	/**
	 * Obtiene el campo ticket
	 * @return  ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * Asigna el valor del campo ticket
	 * @param ticket el valor de ticket a asignar
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * Obtiene el campo tipoUsuario
	 * @return  tipoUsuario
	 */
	public String getTipoUsuario() {
		return tipoUsuario;
	}

	/**
	 * Asigna el valor del campo tipoUsuario
	 * @param tipoUsuario el valor de tipoUsuario a asignar
	 */
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
}
