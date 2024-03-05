/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.bursatec.seguridad.dto;

import java.io.Serializable;

import com.bursatec.seguridad.vo.InstitucionVO;

/**
 * DTO que representa los datos de un usaurio
 * @author Emigdio
 *
 */
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean activo;
    
    private String apellidoMaterno;
    
    private String apellidoPaterno;
    
    private String categoria;
    
    private String centroTrabajo;
    
    private String claveUsuario;
    
    private String email;
    
    private String folioInstitucion;
    
    private String folioInstitucionOrigen;
    
    private String idCentroTrabajo;
    
    private long idInstitucion;
    
    private String idInstitucionOrigen;
    
    private String institucion;
    
    private InstitucionVO institucionPrimaria;
    
    private Object[] instituciones;
    
    private String nombre;
    
    private String nombreCompleto;
    
    private String nombreInstitucion;
    
    private String numeroSerieCertificado;
    
    private String password;
    
    private String telefono;
    
    private String tipoInstitucion;

	/**
	 * Obtiene el campo activo
	 * @return  activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * Asigna el valor del campo activo
	 * @param activo el valor de activo a asignar
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * Obtiene el campo apellidoMaterno
	 * @return  apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * Asigna el valor del campo apellidoMaterno
	 * @param apellidoMaterno el valor de apellidoMaterno a asignar
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * Obtiene el campo apellidoPaterno
	 * @return  apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * Asigna el valor del campo apellidoPaterno
	 * @param apellidoPaterno el valor de apellidoPaterno a asignar
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * Obtiene el campo categoria
	 * @return  categoria
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * Asigna el valor del campo categoria
	 * @param categoria el valor de categoria a asignar
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	/**
	 * Obtiene el campo centroTrabajo
	 * @return  centroTrabajo
	 */
	public String getCentroTrabajo() {
		return centroTrabajo;
	}

	/**
	 * Asigna el valor del campo centroTrabajo
	 * @param centroTrabajo el valor de centroTrabajo a asignar
	 */
	public void setCentroTrabajo(String centroTrabajo) {
		this.centroTrabajo = centroTrabajo;
	}

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
	 * Obtiene el campo email
	 * @return  email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Asigna el valor del campo email
	 * @param email el valor de email a asignar
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Obtiene el campo folioInstitucion
	 * @return  folioInstitucion
	 */
	public String getFolioInstitucion() {
		return folioInstitucion;
	}

	/**
	 * Asigna el valor del campo folioInstitucion
	 * @param folioInstitucion el valor de folioInstitucion a asignar
	 */
	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}

	/**
	 * Obtiene el campo folioInstitucionOrigen
	 * @return  folioInstitucionOrigen
	 */
	public String getFolioInstitucionOrigen() {
		return folioInstitucionOrigen;
	}

	/**
	 * Asigna el valor del campo folioInstitucionOrigen
	 * @param folioInstitucionOrigen el valor de folioInstitucionOrigen a asignar
	 */
	public void setFolioInstitucionOrigen(String folioInstitucionOrigen) {
		this.folioInstitucionOrigen = folioInstitucionOrigen;
	}

	/**
	 * Obtiene el campo idCentroTrabajo
	 * @return  idCentroTrabajo
	 */
	public String getIdCentroTrabajo() {
		return idCentroTrabajo;
	}

	/**
	 * Asigna el valor del campo idCentroTrabajo
	 * @param idCentroTrabajo el valor de idCentroTrabajo a asignar
	 */
	public void setIdCentroTrabajo(String idCentroTrabajo) {
		this.idCentroTrabajo = idCentroTrabajo;
	}

	/**
	 * Obtiene el campo idInstitucion
	 * @return  idInstitucion
	 */
	public long getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * Asigna el valor del campo idInstitucion
	 * @param idInstitucion el valor de idInstitucion a asignar
	 */
	public void setIdInstitucion(long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * Obtiene el campo idInstitucionOrigen
	 * @return  idInstitucionOrigen
	 */
	public String getIdInstitucionOrigen() {
		return idInstitucionOrigen;
	}

	/**
	 * Asigna el valor del campo idInstitucionOrigen
	 * @param idInstitucionOrigen el valor de idInstitucionOrigen a asignar
	 */
	public void setIdInstitucionOrigen(String idInstitucionOrigen) {
		this.idInstitucionOrigen = idInstitucionOrigen;
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
	 * Obtiene el campo institucionPrimaria
	 * @return  institucionPrimaria
	 */
	public InstitucionVO getInstitucionPrimaria() {
		return institucionPrimaria;
	}

	/**
	 * Asigna el valor del campo institucionPrimaria
	 * @param institucionPrimaria el valor de institucionPrimaria a asignar
	 */
	public void setInstitucionPrimaria(InstitucionVO institucionPrimaria) {
		this.institucionPrimaria = institucionPrimaria;
	}

	/**
	 * Obtiene el campo instituciones
	 * @return  instituciones
	 */
	public Object[] getInstituciones() {
		return instituciones;
	}

	/**
	 * Asigna el valor del campo instituciones
	 * @param instituciones el valor de instituciones a asignar
	 */
	public void setInstituciones(Object[] instituciones) {
		this.instituciones = instituciones;
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
	 * Obtiene el campo nombreCompleto
	 * @return  nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * Asigna el valor del campo nombreCompleto
	 * @param nombreCompleto el valor de nombreCompleto a asignar
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
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
	 * Obtiene el campo numeroSerieCertificado
	 * @return  numeroSerieCertificado
	 */
	public String getNumeroSerieCertificado() {
		return numeroSerieCertificado;
	}

	/**
	 * Asigna el valor del campo numeroSerieCertificado
	 * @param numeroSerieCertificado el valor de numeroSerieCertificado a asignar
	 */
	public void setNumeroSerieCertificado(String numeroSerieCertificado) {
		this.numeroSerieCertificado = numeroSerieCertificado !=null? numeroSerieCertificado.trim():null;
	}

	/**
	 * Obtiene el campo password
	 * @return  password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Asigna el valor del campo password
	 * @param password el valor de password a asignar
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Obtiene el campo telefono
	 * @return  telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Asigna el valor del campo telefono
	 * @param telefono el valor de telefono a asignar
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Obtiene el campo tipoInstitucion
	 * @return  tipoInstitucion
	 */
	public String getTipoInstitucion() {
		return tipoInstitucion;
	}

	/**
	 * Asigna el valor del campo tipoInstitucion
	 * @param tipoInstitucion el valor de tipoInstitucion a asignar
	 */
	public void setTipoInstitucion(String tipoInstitucion) {
		this.tipoInstitucion = tipoInstitucion;
	}

	
}
