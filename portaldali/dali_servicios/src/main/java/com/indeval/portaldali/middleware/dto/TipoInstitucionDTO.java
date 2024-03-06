package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

public class TipoInstitucionDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** El identificador del tipo de institución */
	private long id = 0;
	
	/** Identificador de la clave de la institución*/
	private String claveTipoInstitucion = null;
	
	/** Descripcion del tipo de institución*/
	private String descripcion = null;

	/**
	 * Constructor de la clase
	 */
	public TipoInstitucionDTO() {}
	
	/**
	 * Constructor de la clase
	 * @param idTipoInstitucion Id a asiganar
	 */
	public TipoInstitucionDTO(long idTipoInstitucion) {
		id = idTipoInstitucion;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClaveTipoInstitucion() {
		return claveTipoInstitucion;
	}

	public void setClaveTipoInstitucion(String claveTipoInstitucion) {
		this.claveTipoInstitucion = claveTipoInstitucion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
