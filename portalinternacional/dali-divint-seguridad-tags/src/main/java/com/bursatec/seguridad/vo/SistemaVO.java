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
public class SistemaVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 546464654654L;

	private boolean bloqueaPerfil;
    
    private String descripcion;
    
    private byte[] icono;
    
    private Long id;
    
    private Long idInstitucion;
    
    private Long idLlavePrivada;
    
    private String institucion;
    
    private String nombre;
    
    private boolean permiteMultipleLogin;
    
    private int ticketTimeout;
    
    private int tipoAutentificacion;
    
    private int universoCuentas;
    
    private String url;
    
    private boolean usaRolesPorInstitucion;

	/**
	 * Obtiene el campo bloqueaPerfil
	 * @return  bloqueaPerfil
	 */
	public boolean isBloqueaPerfil() {
		return bloqueaPerfil;
	}

	/**
	 * Asigna el valor del campo bloqueaPerfil
	 * @param bloqueaPerfil el valor de bloqueaPerfil a asignar
	 */
	public void setBloqueaPerfil(boolean bloqueaPerfil) {
		this.bloqueaPerfil = bloqueaPerfil;
	}

	/**
	 * Obtiene el campo descripcion
	 * @return  descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Asigna el valor del campo descripcion
	 * @param descripcion el valor de descripcion a asignar
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el campo icono
	 * @return  icono
	 */
	public byte[] getIcono() {
		return icono;
	}

	/**
	 * Asigna el valor del campo icono
	 * @param icono el valor de icono a asignar
	 */
	public void setIcono(byte[] icono) {
		this.icono = icono;
	}

	/**
	 * Obtiene el campo id
	 * @return  id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Asigna el valor del campo id
	 * @param id el valor de id a asignar
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * Obtiene el campo idLlavePrivada
	 * @return  idLlavePrivada
	 */
	public Long getIdLlavePrivada() {
		return idLlavePrivada;
	}

	/**
	 * Asigna el valor del campo idLlavePrivada
	 * @param idLlavePrivada el valor de idLlavePrivada a asignar
	 */
	public void setIdLlavePrivada(Long idLlavePrivada) {
		this.idLlavePrivada = idLlavePrivada;
	}

	/**
	 * Obtiene el campo institucion
	 * @return  institucion
	 */
	public String getInstitucion() {
		return institucion;
	}

	/**
	 * Asigna el valor del campo institucion
	 * @param institucion el valor de institucion a asignar
	 */
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	/**
	 * Obtiene el campo nombre
	 * @return  nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna el valor del campo nombre
	 * @param nombre el valor de nombre a asignar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el campo permiteMultipleLogin
	 * @return  permiteMultipleLogin
	 */
	public boolean isPermiteMultipleLogin() {
		return permiteMultipleLogin;
	}

	/**
	 * Asigna el valor del campo permiteMultipleLogin
	 * @param permiteMultipleLogin el valor de permiteMultipleLogin a asignar
	 */
	public void setPermiteMultipleLogin(boolean permiteMultipleLogin) {
		this.permiteMultipleLogin = permiteMultipleLogin;
	}

	/**
	 * Obtiene el campo ticketTimeout
	 * @return  ticketTimeout
	 */
	public int getTicketTimeout() {
		return ticketTimeout;
	}

	/**
	 * Asigna el valor del campo ticketTimeout
	 * @param ticketTimeout el valor de ticketTimeout a asignar
	 */
	public void setTicketTimeout(int ticketTimeout) {
		this.ticketTimeout = ticketTimeout;
	}

	/**
	 * Obtiene el campo tipoAutentificacion
	 * @return  tipoAutentificacion
	 */
	public int getTipoAutentificacion() {
		return tipoAutentificacion;
	}

	/**
	 * Asigna el valor del campo tipoAutentificacion
	 * @param tipoAutentificacion el valor de tipoAutentificacion a asignar
	 */
	public void setTipoAutentificacion(int tipoAutentificacion) {
		this.tipoAutentificacion = tipoAutentificacion;
	}

	/**
	 * Obtiene el campo universoCuentas
	 * @return  universoCuentas
	 */
	public int getUniversoCuentas() {
		return universoCuentas;
	}

	/**
	 * Asigna el valor del campo universoCuentas
	 * @param universoCuentas el valor de universoCuentas a asignar
	 */
	public void setUniversoCuentas(int universoCuentas) {
		this.universoCuentas = universoCuentas;
	}

	/**
	 * Obtiene el campo url
	 * @return  url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Asigna el valor del campo url
	 * @param url el valor de url a asignar
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Obtiene el campo usaRolesPorInstitucion
	 * @return  usaRolesPorInstitucion
	 */
	public boolean isUsaRolesPorInstitucion() {
		return usaRolesPorInstitucion;
	}

	/**
	 * Asigna el valor del campo usaRolesPorInstitucion
	 * @param usaRolesPorInstitucion el valor de usaRolesPorInstitucion a asignar
	 */
	public void setUsaRolesPorInstitucion(boolean usaRolesPorInstitucion) {
		this.usaRolesPorInstitucion = usaRolesPorInstitucion;
	}
}
