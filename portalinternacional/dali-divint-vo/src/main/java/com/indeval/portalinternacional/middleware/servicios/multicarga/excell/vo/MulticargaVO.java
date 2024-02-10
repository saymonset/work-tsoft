package com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class MulticargaVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String usuarioResponsable;
	
	private String nombreArchivo;
	
	private HSSFWorkbook archivo;
		
	private Long tipoOperacion;
	
	private Long estadoRegistro;
	
	private Date fechaCargaInicio;
	
	private Date fechaCargaFin;
	
	private Date fechaActualizacionInicio;
	
	private Date fechaActualizacionFin;
	
	private Long idInstitucionDestino;
	
	private String claveInstitucionDestino;
	
	

	public String getUsuarioResponsable() {
		return usuarioResponsable;
	}

	public void setUsuarioResponsable(String usuarioResponsable) {
		this.usuarioResponsable = usuarioResponsable;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public HSSFWorkbook getArchivo() {
		return archivo;
	}

	public void setArchivo(HSSFWorkbook archivo) {
		this.archivo = archivo;
	}

	public Long getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(Long tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Long getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoRegistro(Long estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	public Date getFechaCargaInicio() {
		return fechaCargaInicio;
	}

	public void setFechaCargaInicio(Date fechaCargaInicio) {
		this.fechaCargaInicio = fechaCargaInicio;
	}

	public Date getFechaCargaFin() {
		return fechaCargaFin;
	}

	public void setFechaCargaFin(Date fechaCargaFin) {
		this.fechaCargaFin = fechaCargaFin;
	}

	public Date getFechaActualizacionInicio() {
		return fechaActualizacionInicio;
	}

	public void setFechaActualizacionInicio(Date fechaActualizacionInicio) {
		this.fechaActualizacionInicio = fechaActualizacionInicio;
	}

	public Date getFechaActualizacionFin() {
		return fechaActualizacionFin;
	}

	public void setFechaActualizacionFin(Date fechaActualizacionFin) {
		this.fechaActualizacionFin = fechaActualizacionFin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * @return the idInstitucionDestino
	 */
	public Long getIdInstitucionDestino() {
		return idInstitucionDestino;
	}

	/**
	 * @param idInstitucionDestino the idInstitucionDestino to set
	 */
	public void setIdInstitucionDestino(Long idInstitucionDestino) {
		this.idInstitucionDestino = idInstitucionDestino;
	}

	/**
	 * @return the claveInstitucionDestino
	 */
	public String getClaveInstitucionDestino() {
		return claveInstitucionDestino;
	}

	/**
	 * @param claveInstitucionDestino the claveInstitucionDestino to set
	 */
	public void setClaveInstitucionDestino(String claveInstitucionDestino) {
		this.claveInstitucionDestino = claveInstitucionDestino;
	}
}
