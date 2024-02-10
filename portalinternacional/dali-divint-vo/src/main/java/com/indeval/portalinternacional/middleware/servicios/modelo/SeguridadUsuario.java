package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SEGU_USUARIO")
public class SeguridadUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String claveUsuario;
	public String nombre;
	public String apellidoPaterno;
	public String apellidoMaterno;
	public String email;
	public String categoria;
	public String telefono;
	public Long idInstitucion;
	public Integer usuarioActivo;
	public String certificado;
	public String institucion;
	
	@Id
	@Column(name = "CLAVE_USUARIO", unique = true, nullable = false)
	public String getClaveUsuario() {
		return claveUsuario;
	}
	
	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}
	
	@Column(name = "NOMBRE", unique = false, nullable = true)
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "APELLIDO_PATERNO", unique = false, nullable = true)
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	
	@Column(name = "APELLIDO_MATERNO", unique = false, nullable = true)
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	
	@Column(name = "EMAIL", unique = false, nullable = true)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "CATEGORIA", unique = false, nullable = true)
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	@Column(name = "TELEFONO", unique = false, nullable = true)
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	@Column(name = "ID_INSTITUCION", unique = false, nullable = false)
	public Long getIdInstitucion() {
		return idInstitucion;
	}
	
	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	@Column(name = "USUARIO_ACTIVO", unique = false, nullable = false)
	public Integer getUsuarioActivo() {
		return usuarioActivo;
	}
	
	public void setUsuarioActivo(Integer usuarioActivo) {
		this.usuarioActivo = usuarioActivo;
	}
	
	@Column(name = "CERTIFICADO", unique = false, nullable = true)
	public String getCertificado() {
		return certificado;
	}
	
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}
	
	@Column(name = "INSTITUCION", unique = false, nullable = true)
	public String getInstitucion() {
		return institucion;
	}
	
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	
}
