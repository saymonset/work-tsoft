package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;

public class InstruccionCapitalesVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
		
	private Long idHistorico;
		
	private Long idTipoDerechoCaev;
	
	private String tipoDerechoCaev;
	
	private Long liga;
	
	private String tagsEliminacion;
	
	private String opcionesMensajes;
	
	public InstruccionCapitalesVO(){}

	public InstruccionCapitalesVO(Long id, Long idHistorico, Long idTipoDerechoCaev, String tipoDerechoCaev, Long liga,
			String tagsEliminacion, String opcionesMensajes) {
		super();
		this.id = id;
		this.idHistorico = idHistorico;
		this.idTipoDerechoCaev = idTipoDerechoCaev;
		this.tipoDerechoCaev = tipoDerechoCaev;
		this.liga = liga;
		this.tagsEliminacion = tagsEliminacion;
		this.opcionesMensajes = opcionesMensajes;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the idHistorico
	 */
	public Long getIdHistorico() {
		return idHistorico;
	}

	/**
	 * @param idHistorico the idHistorico to set
	 */
	public void setIdHistorico(Long idHistorico) {
		this.idHistorico = idHistorico;
	}

	/**
	 * @return the idTipoDerechoCaev
	 */
	public Long getIdTipoDerechoCaev() {
		return idTipoDerechoCaev;
	}

	/**
	 * @param idTipoDerechoCaev the idTipoDerechoCaev to set
	 */
	public void setIdTipoDerechoCaev(Long idTipoDerechoCaev) {
		this.idTipoDerechoCaev = idTipoDerechoCaev;
	}

	/**
	 * @return the tipoDerechoCaev
	 */
	public String getTipoDerechoCaev() {
		return tipoDerechoCaev;
	}

	/**
	 * @param tipoDerechoCaev the tipoDerechoCaev to set
	 */
	public void setTipoDerechoCaev(String tipoDerechoCaev) {
		this.tipoDerechoCaev = tipoDerechoCaev;
	}

	/**
	 * @return the liga
	 */
	public Long getLiga() {
		return liga;
	}

	/**
	 * @param liga the liga to set
	 */
	public void setLiga(Long liga) {
		this.liga = liga;
	}

	/**
	 * @return the tagsEliminacion
	 */
	public String getTagsEliminacion() {
		return tagsEliminacion;
	}

	/**
	 * @param tagsEliminacion the tagsEliminacion to set
	 */
	public void setTagsEliminacion(String tagsEliminacion) {
		this.tagsEliminacion = tagsEliminacion;
	}

	/**
	 * @return the opcionesMensajes
	 */
	public String getOpcionesMensajes() {
		return opcionesMensajes;
	}

	/**
	 * @param opcionesMensajes the opcionesMensajes to set
	 */
	public void setOpcionesMensajes(String opcionesMensajes) {
		this.opcionesMensajes = opcionesMensajes;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;		
		result = prime * result + ((liga == null) ? 0 : liga.hashCode());
		result = prime * result + ((opcionesMensajes == null) ? 0 : opcionesMensajes.hashCode());
		result = prime * result + ((tagsEliminacion == null) ? 0 : tagsEliminacion.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstruccionCapitalesVO other = (InstruccionCapitalesVO) obj;		
		if (liga == null) {
			if (other.liga != null)
				return false;
		} else if (!liga.equals(other.liga))
			return false;
		if (opcionesMensajes == null) {
			if (other.opcionesMensajes != null)
				return false;
		} else if (!opcionesMensajes.equals(other.opcionesMensajes))
			return false;
		if (tagsEliminacion == null) {
			if (other.tagsEliminacion != null)
				return false;
		} else if (!tagsEliminacion.equals(other.tagsEliminacion))
			return false;
		return true;
	}

}
