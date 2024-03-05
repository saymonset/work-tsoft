package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.Date;

public class CriterioDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * El identificador de la institucion
	 */
	private int idInstitucion = 0;
	/**
	 * El tipo y folio de la institucion
	 */
	private String folioInstitucion;
	/**
	 * El tipo y folio de la institucion
	 */
	private String institucion;
	/**
	 * Instancia de salida del participante
	 */
	private int instancia;
	/**
	 * Version de la API que utiliza el participante
	 */
	private String versionApi;
	/**
	 * Indica si la entrada y salida del usuario es Sincronizado 
	 * S: SI
	 * N: NO
	 */
	private String sincronizado;
	/**
	 * Indica si el participante se encuentra Activo
	 * S: SI
	 * N: NO
	 */
	private String activo;
	
	
	private int idEvento;
	private String usuario;
	private Date fecha;
	
	public CriterioDTO() {
		super();
	}

	
	@Override
	public String toString() {
		return "CriterioDTO [idInstitucion=" + idInstitucion + ", folioInstitucion=" + folioInstitucion
				+ ", institucion=" + institucion + ", instancia=" + instancia + ", versionApi=" + versionApi
				+ ", sincronizado=" + sincronizado + ", activo=" + activo + ", idEvento=" + idEvento + ", usuario="
				+ usuario + ", fecha=" + fecha + "]";
	}


	public int getInstancia() {
		return instancia;
	}

	public void setInstancia(int instancia) {
		this.instancia = instancia;
	}

	public String getVersionApi() {
		return versionApi;
	}

	public void setVersionApi(String versionApi) {
		this.versionApi = versionApi;
	}

	public String getSincronizado() {
		return sincronizado;
	}

	public void setSincronizado(String sincronizado) {
		this.sincronizado = sincronizado;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public int getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(int idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getFolioInstitucion() {
		return folioInstitucion;
	}

	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
