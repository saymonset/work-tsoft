package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="T_GRUPOS_RETENCION")
public class GrupoRetencion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3008316955446063755L;
	
	private Long idGrupoRetencion;
	private List<TitulosGrupoRetencion> titulosGrupoRetencion;
	private Derecho derecho;
	private Float porcentajeRetencion;
	
	@Id
	@Column(name = "ID_GRUPO_RETENCION")
	public Long getIdGrupoRetencion() {
		return idGrupoRetencion;
	}
	@OneToMany(fetch= FetchType.LAZY)
	@JoinColumn(name = "ID_GRUPO_RETENCION")
	public List<TitulosGrupoRetencion> getTitulosGrupoRetencion() {
		return titulosGrupoRetencion;
	}	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DERECHO_CAPITAL", updatable = false, insertable = false)
	public Derecho getDerecho() {
		return derecho;
	}	
	@Column(name = "PORCENTAJE_RETENCION", unique = false, nullable = false)
	public Float getPorcentajeRetencion() {
		return porcentajeRetencion;
	}
	public void setIdGrupoRetencion(Long idGrupoRetencion) {
		this.idGrupoRetencion = idGrupoRetencion;
	}
	public void setTitulosGrupoRetencion(
			List<TitulosGrupoRetencion> titulosGrupoRetencion) {
		this.titulosGrupoRetencion = titulosGrupoRetencion;
	}
	public void setDerecho(Derecho derecho) {
		this.derecho = derecho;
	}
	public void setPorcentajeRetencion(Float porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}
}
